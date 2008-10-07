package com.cosmos.acacia.crm.security;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.users.RightsManagerLocal;
import com.cosmos.acacia.crm.bl.users.annotations.Create;
import com.cosmos.acacia.crm.bl.users.annotations.Delete;
import com.cosmos.acacia.crm.bl.users.annotations.Modify;
import com.cosmos.acacia.crm.bl.users.annotations.Read;
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
@Stateful
public class PermissionsManager implements PermissionsManagerLocal {

    private static final long CLEAR_PERIOD = 4 * 60 * 60 * 1000;

    @EJB
    private RightsManagerLocal manager;

    @EJB
    private AcaciaSessionLocal session;

    private List<DataObjectType> dataObjectTypes;

    {
        Timer timer = new Timer();
        timer.schedule(new ClearingTask(), CLEAR_PERIOD, CLEAR_PERIOD);
    }

    /**
     * Checks if the user has permissions to call the method
     *
     * @param method
     * @param args
     *
     * @return false if the call is disallowed; true otherwise.
     */
    @Override
    public boolean isAllowedPreCall(
            Method method,
            Object[] args)
    {
//        if (true) return true;

        if (isCreateMethod(method)) {
            DataObject tmpDataObject = new DataObject();
            tmpDataObject.setDataObjectType(getDataObjectType(method.getReturnType()));
            if (tmpDataObject.getDataObjectType() != null)
                return manager.isAllowed(tmpDataObject, UserRightType.CREATE);
        }

        if (isModifyMethod(method)) {
            if (args[0] instanceof DataObjectBean) {
                return manager.isAllowed(
                    ((DataObjectBean) args[0]).getDataObject(),
                    UserRightType.MODIFY);
            }
        }

        if (isDeleteMethod(method)) {
            if (args[0] instanceof DataObjectBean) {
                return manager.isAllowed(
                    ((DataObjectBean) args[0]).getDataObject(),
                    UserRightType.DELETE);
            }
        }

        if (isReadMethod(method)) {
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
    @Override
    public boolean isAllowedPostCall(Object result)
    {
//        if (true) return true;
        boolean allowed = true;
        if (result instanceof DataObjectBean) {
            DataObjectBean dob = (DataObjectBean) result;

            // checking if new object
            if (dob.getDataObject() == null || dob.getDataObject().getDataObjectId() == null)
                return true;

            // checking if it is the current organization
            if (dob.equals(session.getOrganization()))
                return true;

            // Checking if the user is allowed to view data from all branches
            boolean isAllowedToViewAll = false;
            try {
                isAllowedToViewAll = session.getViewDataFromAllBranches().booleanValue();
            } catch (NullPointerException ex) {
                //Ignored, the value is not set
            }

            allowed = isAllowedToViewAll
            || dob.getDataObject().getOwnerId().equals(session.getBranch().getId())
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
    @Override
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
                        isAllowedToViewAll = session.getViewDataFromAllBranches().booleanValue();
                    } catch (NullPointerException ex) {
                        //Ignored, the value is not set
                    }

                    DataObjectBean dob = ((DataObjectBean) obj);
                    boolean isBranchMember = isAllowedToViewAll
                    || dob.getDataObject().getOwnerId().equals(session.getBranch().getId())
                    || dob.getDataObject().getOwnerId().equals(BigInteger.ZERO);

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
        if (dataObjectTypes == null)
            dataObjectTypes = session.getDataObjectTypes();

        for (DataObjectType dot : dataObjectTypes) {
            if (dot.getDataObjectType().equals(clazz.getName())) {
                return dot;
            }
        }
        return null;
    }

    /**
     * Method for checking special permissions
     * @param specialPermission
     * @return
     */
    public boolean isAllowed(DataObject dataObject, SpecialPermission specialPermission) {
        return manager.isAllowed(dataObject, specialPermission);
    }

    public boolean isAllowed(SpecialPermission specialPermission) {
        return manager.isAllowed(specialPermission);
    }

    class ClearingTask extends TimerTask {
        @Override
        public void run() {
            manager.clearCachedRights();
        }
    }

    private static final String CREATE_METHOD_PREFIX = "new";
    private static final String DELETE_METHOD_PREFIX1 = "delete";
    private static final String DELETE_METHOD_PREFIX2 = "remove";
    private static final String MODIFY_METHOD_PREFIX = "save";

    private boolean isCreateMethod(Method method) {
        return method.isAnnotationPresent(Create.class)
        || method.getName().startsWith(CREATE_METHOD_PREFIX);
    }

    private boolean isModifyMethod(Method method) {
        return method.isAnnotationPresent(Modify.class)
        || method.getName().startsWith(MODIFY_METHOD_PREFIX);
    }

    private boolean isDeleteMethod(Method method) {
        return method.isAnnotationPresent(Delete.class)
        || method.getName().startsWith(DELETE_METHOD_PREFIX1)
        || method.getName().startsWith(DELETE_METHOD_PREFIX2);
    }

    private boolean isReadMethod(Method method) {
        return method.isAnnotationPresent(Read.class);
    }
}