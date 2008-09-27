package com.cosmos.acacia.security;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.cosmos.acacia.crm.bl.impl.EntityManagerFacadeRemote;
import com.cosmos.acacia.crm.bl.users.RightsManagerRemote;
import com.cosmos.acacia.crm.bl.users.annotations.Create;
import com.cosmos.acacia.crm.bl.users.annotations.Delete;
import com.cosmos.acacia.crm.bl.users.annotations.Modify;
import com.cosmos.acacia.crm.bl.users.annotations.Read;
import com.cosmos.acacia.crm.client.LocalSession;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.enums.UserRightType;

/**
 * Pseudo-singleton class for managing permissions
 *
 * @author Bozhidar Bozhanov
 *
 */
public class PermissionsManager {

    private static final long CLEAR_PERIOD = 4 * 60 * 60 * 1000;

    private RightsManagerRemote manager;
    private List<DataObjectType> dataObjectTypes;
    private BigInteger branchId;
    private static PermissionsManager instance;

    public PermissionsManager(List<DataObjectType> dataObjectTypes, BigInteger branchId) {
        if (instance != null)
            return;

        try {
            //manager = InitialContext.doLookup(RightsManagerRemote.class.getName());
            EntityManagerFacadeRemote em = InitialContext.doLookup(EntityManagerFacadeRemote.class.getName());
            manager = new RightsManagerBean(em);
            Timer timer = new Timer();
            timer.schedule(new ClearingTask(), CLEAR_PERIOD, CLEAR_PERIOD);
        } catch (NamingException ex) {
            //
        }
        this.dataObjectTypes = dataObjectTypes;
        this.branchId = branchId;
        instance = this;
    }

    public static PermissionsManager get() {
        if (instance == null)
            throw new RuntimeException("Initialize the permissions manager first");

        return instance;
    }

    /**
     * Checks if the user has permissions to call the method
     *
     * @param method
     * @param args
     *
     * @return false if the call is disallowed; true otherwise.
     */
    public boolean isAllowedPreCall(
            Method method,
            Object[] args)
    {
        if (true) return true;

        if (method.isAnnotationPresent(Create.class)) {
            DataObject tmpDataObject = new DataObject();
            tmpDataObject.setDataObjectType(getDataObjectType(method.getReturnType()));
            if (tmpDataObject.getDataObjectType() != null)
                return manager.isAllowed(tmpDataObject, UserRightType.CREATE);
        }

        if (method.isAnnotationPresent(Modify.class)) {
            if (args[0] instanceof DataObjectBean) {
                return manager.isAllowed(
                    ((DataObjectBean) args[0]).getDataObject(),
                    UserRightType.MODIFY);
            }
        }

        if (method.isAnnotationPresent(Delete.class)) {
            if (args[0] instanceof DataObjectBean) {
                return manager.isAllowed(
                    ((DataObjectBean) args[0]).getDataObject(),
                    UserRightType.DELETE);
            }
        }

        if (method.isAnnotationPresent(Read.class)) {
            DataObject tmpDataObject = new DataObject();
            tmpDataObject.setDataObjectType(getDataObjectType(method.getReturnType()));
            if (tmpDataObject.getDataObjectType() != null)
                return manager.isAllowed(tmpDataObject, UserRightType.READ);
        }

        return true;
    }


    /**
     * Checks if the user has permissions to see the result
     *  of the method invocation
     *
     * @param result
     *
     * @return false if the call is disallowed; true otherwise.
     */
    @SuppressWarnings("unchecked")
    public boolean isAllowedPostCall(Object result)
    {
        if (true) return true;
        boolean allowed = true;
        if (result instanceof DataObjectBean) {
            DataObjectBean dob = (DataObjectBean) result;

            // checking if new object
            if (dob.getDataObject() == null || dob.getDataObject().getDataObjectId() == null)
                return true;

            // checking if it is the current organization
            if (dob.equals(LocalSession.instance().getOrganization()))
                return true;

            // Checking if the user is allowed to view data from all branches
            boolean isAllowedToViewAll = false;
            try {
                isAllowedToViewAll = ((Boolean) LocalSession.instance().get(
                    LocalSession.VIEW_DATA_FROM_ALL_BRANCHES)).booleanValue();
            } catch (NullPointerException ex) {
                //Ignored, the value is not set
            }


            allowed = isAllowedToViewAll
            || dob.getDataObject().getOwnerId().equals(branchId)
            || dob.getDataObject().getOwnerId().equals(BigInteger.ZERO);

            if (allowed)
                allowed = manager.isAllowed(
                     dob.getDataObject(),
                     UserRightType.READ);
        }
        return allowed;
    }

    /**
     * Filter a collection of items, hiding those
     * which are not allowed for reading
     *
     * @param result
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object filterResult(Object result) {

        if (true) return result;

        if (result instanceof Collection) {
            Collection tmpCollection = (Collection) result;
            List iterable = new ArrayList(tmpCollection);

            for (Object obj : iterable) {
                if (obj instanceof DataObjectBean) {

                    // checking branch ownership; if valid, check regular permissions
                    // Checking if the user is allowed to view data from all branches
                    boolean isAllowedToViewAll = false;
                    try {
                        isAllowedToViewAll = ((Boolean) LocalSession.instance().get(
                            LocalSession.VIEW_DATA_FROM_ALL_BRANCHES)).booleanValue();
                    } catch (NullPointerException ex) {
                        //Ignored, the value is not set
                    }

                    DataObjectBean dob = ((DataObjectBean) obj);
                    boolean isBranchMember = isAllowedToViewAll
                    || dob.getDataObject().getOwnerId().equals(branchId)
                    || dob.getDataObject().getOwnerId().equals(BigInteger.ZERO);

                    System.out.println("DOID: " + manager.isAllowed(
                            dob.getDataObject(),
                            UserRightType.READ));

                    if (!isBranchMember) {
                        tmpCollection.remove(obj);
                    } else if (!manager.isAllowed(
                            dob.getDataObject(),
                            UserRightType.READ)) {

                        tmpCollection.remove(obj);
                    }
                }
            }

            return tmpCollection;
        }

        return result;
    }

    /**
     * Gets the DataObjectType for the given class
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    private DataObjectType getDataObjectType(Class clazz) {
        for (DataObjectType dot : dataObjectTypes) {
            if (dot.getDataObjectType().equals(clazz.getName())) {
                return dot;
            }
        }
        return null;
    }

    /**
     * Method for client-side checking special permissions
     * @param specialPermission
     * @return
     */
    public boolean isAllowed(DataObject dataObject, SpecialPermission specialPermission) {
        return manager.isAllowed(dataObject, specialPermission);
    }

    public boolean isAllowed(SpecialPermission specialPermission) {
        return manager.isAllowed(specialPermission);
    }

    public BigInteger getBranchId() {
        return branchId;
    }

    public void setBranchId(BigInteger branchId) {
        this.branchId = branchId;
    }

    public void clearCachedRights() {
        manager.setSpecialRights(null);
        manager.setGeneralRights(null);
    }

    class ClearingTask extends TimerTask {
        @Override
        public void run() {
            clearCachedRights();
        }
    }
}