package com.cosmos.acacia.crm.bl.users;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.enums.UserRightType;

@Stateful
public class RightsManagerBean
    implements RightsManagerRemote, RightsManagerLocal {

    @PersistenceContext
    EntityManager em;

    @EJB
    EntityStoreManagerLocal esm;

    @EJB
    UserRightsLocal rightsHelper;

    @EJB
    AcaciaSessionLocal session;

    @EJB
    UsersLocal usersManager;

    private Set<UserRight> generalRights;
    private Set<UserRight> specialRights;


    @Override
    public boolean isAllowed(DataObject dataObject,
            UserRightType rightType) {

        return isAllowed(session.getUser(), dataObject, rightType);

    }

    @Override
    public boolean isAllowed(DataObject dataObject,
            SpecialPermission specialPermission) {

        return isAllowed(session.getUser(), dataObject, specialPermission);

    }

    @Override
    public boolean isAllowed(User user,
            DataObject dataObject,
            UserRightType rightType) {

        Set<UserRight> rights = fetchRights(user, dataObject, false);

        UserRight highestPriorityRegularRight = getHigherPriorityRight(rights, false);
        UserRight highestPriorityExclusionRight = getHigherPriorityRight(rights, true);

        if (highestPriorityRegularRight == null)
            return false;

        UserRight highestPriorityRight =
                compareRights(highestPriorityRegularRight, highestPriorityExclusionRight);

        // Setting excluded rights
        if (highestPriorityRight == highestPriorityExclusionRight) {
            highestPriorityRight.setCreate(
                    highestPriorityRegularRight.canCreate()
                    && !highestPriorityExclusionRight.canCreate());

            highestPriorityRight.setRead(
                    highestPriorityRegularRight.canRead()
                    && !highestPriorityExclusionRight.canRead());

            highestPriorityRight.setDelete(
                    highestPriorityRegularRight.canDelete()
                    && !highestPriorityExclusionRight.canDelete());

            highestPriorityRight.setModify(
                    highestPriorityRegularRight.canModify()
                    && !highestPriorityExclusionRight.canModify());
        }


//        if (highestPriorityRight.isExcluded())
//            return false;

        if (rightType == UserRightType.READ)
            return highestPriorityRight.canRead();

        if (rightType == UserRightType.CREATE)
            return highestPriorityRight.canCreate();

        if (rightType == UserRightType.MODIFY)
            return highestPriorityRight.canModify();

        if (rightType == UserRightType.DELETE)
            return highestPriorityRight.canDelete();

        return false;
    }

    @Override
    public boolean isAllowed(User user, DataObject dataObject,
            SpecialPermission specialPermission) {

        Set<UserRight> rights = fetchRights(user, dataObject, true);

        for (UserRight right : rights) {
            //TODO: this check might be incorrect
            if (right.getSpecialPermission().equals(specialPermission.getDbResource()))
                return true;
        }

        return false;
    }

    private UserRight getHigherPriorityRight(Set<UserRight> rights, boolean countExcluded) {

        UserRight highestPriorityRight = null;
        for (UserRight right : rights) {

            if (right.isExcluded() ^ countExcluded)
                continue;

            if (highestPriorityRight == null) {
                highestPriorityRight = right;
            } else {
                highestPriorityRight = compareRights(right, highestPriorityRight);
            }
        }

        return highestPriorityRight;
    }

    private UserRight compareRights(UserRight right1, UserRight right2) {
        UserRight higherPriorityRight = null;

        if (right1 == right2)
            return right1;

        if (right1 == null)
            return right2;

        if (right2 == null)
            return right1;


        if (right1.getUser() != null && right2.getUser() == null) {
            higherPriorityRight = right1;
        } else if (right2.getUser() != null && right1.getUser() == null) {
            higherPriorityRight = right2;
        } else if (right1.getDataObject() != null && right2.getDataObject() == null){
            higherPriorityRight = right1;
        } else {
            higherPriorityRight = right2;
        }

        return higherPriorityRight;
    }

    private Set<UserRight> fetchRights(User user, DataObject dataObject, boolean isSpecial) {

        Set<UserRight> rights = isSpecial ? specialRights : generalRights;

        if (rights == null) {
            rights = fetchRights(user, isSpecial);
            if (isSpecial)
                specialRights = new HashSet<UserRight>(rights);
            else
                generalRights = new HashSet<UserRight>(rights);
        }


        // Creating a set of applicable rights for the current data object
        // If the set of permissions is empty, do the operation for the parent
        // object, until the parent becomes null

        Set<UserRight> applicableRights = new HashSet<UserRight>();
        DataObject tmpDataObject = dataObject;

        while (applicableRights.size() == 0 && tmpDataObject != null) {
            for (UserRight right : rights) {
                if (right.getDataObjectType() == null) {
                    applicableRights.add(right);
                } else {
                    if (right.getDataObject() != null) {
                        if (right.getDataObject().equals(tmpDataObject))
                            applicableRights.add(right);
                    }
                    else if (right.getDataObjectType().equals(tmpDataObject.getDataObjectType()))
                        applicableRights.add(right);
                }
            }
            if (tmpDataObject.getParentDataObjectId() != null)
                tmpDataObject = em.find(DataObject.class, tmpDataObject.getParentDataObjectId());
            else
                tmpDataObject = null;

        }

        return applicableRights;
    }


    private Set<UserRight> fetchRights(User user, boolean isSpecial) {
        // Getting the rights for this specific user, if any
        Set<UserRight> userSpecificRights = null;

        if (!isSpecial)
            userSpecificRights = rightsHelper.getUserRights(user);
        else
            userSpecificRights = rightsHelper.getSpecialPermissions(user);



        // Getting the directly assigned group for this user
        UserOrganization uo =
            usersManager.getUserOrganization(user, session.getOrganization());

        UserGroup group = uo.getUserGroup();


        // if there is no directly assigned group, get the group
        // via the position type.

        if (group == null) {
            Address branch = uo.getBranch();
            if (branch != null) {
                Query q = em.createNamedQuery("ContactPerson.findByPersonAndParentDataObject");
                q.setParameter("person", user.getPerson());
                q.setParameter("parentDataObjectId", branch.getAddressId());

                try {
                    ContactPerson contact = (ContactPerson) q.getSingleResult();
                    PositionType positionType = contact.getPositionType();
                    if (positionType != null)
                        group = positionType.getUserGroup();
                } catch (Exception ex) {
                    // No match found
                }
            }
        }

        Set<UserRight> groupRights = new HashSet<UserRight>();
        if (group != null) {
            if (!isSpecial)
                groupRights = rightsHelper.getUserRights(group);
            else
                groupRights = rightsHelper.getSpecialPermissions(group);
        }

        // Creating a set of all rights for this user
        Set<UserRight> rights = new HashSet<UserRight>();

        rights.addAll(userSpecificRights);
        rights.addAll(groupRights);

        return rights;
    }

    @Override
    public void setGeneralRights(Set<UserRight> rights) {
        generalRights = rights;
    }

    @Override
    public void setSpecialRights(Set<UserRight> rights) {
        specialRights = rights;
    }


}
