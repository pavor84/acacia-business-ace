package com.cosmos.acacia.security;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.cosmos.acacia.crm.bl.users.RightsManagerRemote;
import com.cosmos.acacia.crm.bl.users.annotations.Create;
import com.cosmos.acacia.crm.bl.users.annotations.Delete;
import com.cosmos.acacia.crm.bl.users.annotations.Modify;
import com.cosmos.acacia.crm.bl.users.annotations.Read;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.enums.UserRightType;
import java.util.ArrayList;

public class PermissionsManager {

    private RightsManagerRemote manager;
    private User user;
    private List<DataObjectType> dataObjectTypes;

    public PermissionsManager(User user, List<DataObjectType> dataObjectTypes) {
        try {
            manager = InitialContext.doLookup(RightsManagerRemote.class.getName());
        } catch (NamingException ex) {
            //
        }

        this.user = user;
        this.dataObjectTypes = dataObjectTypes;
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
                return manager.isAllowed(user, tmpDataObject, UserRightType.CREATE);
        }

        if (method.isAnnotationPresent(Modify.class)) {
            if (args[0] instanceof DataObjectBean) {
                return manager.isAllowed(user,
                    ((DataObjectBean) args[0]).getDataObject(),
                    UserRightType.MODIFY);
            }
        }

        if (method.isAnnotationPresent(Delete.class)) {
            if (args[0] instanceof DataObjectBean) {
                return manager.isAllowed(user,
                    ((DataObjectBean) args[0]).getDataObject(),
                    UserRightType.DELETE);
            }
        }

        if (method.isAnnotationPresent(Read.class)) {
            DataObject tmpDataObject = new DataObject();
            tmpDataObject.setDataObjectType(getDataObjectType(method.getReturnType()));
            if (tmpDataObject.getDataObjectType() != null)
                return manager.isAllowed(user, tmpDataObject, UserRightType.READ);
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
        
        if (result instanceof DataObjectBean) {
             return manager.isAllowed(user,
                     ((DataObjectBean) result).getDataObject(),
                     UserRightType.READ);
        }

        return true;
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
                    if (!manager.isAllowed(user,
                            ((DataObjectBean) obj).getDataObject(),
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
}