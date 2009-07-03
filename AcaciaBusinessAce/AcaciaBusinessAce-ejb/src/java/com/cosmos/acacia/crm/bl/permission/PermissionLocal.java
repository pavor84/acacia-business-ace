/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.permission;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.permission.DataObjectPermission;
import com.cosmos.acacia.crm.data.permission.DataObjectTypePermission;
import com.cosmos.acacia.crm.enums.UserRightType;
import java.math.BigInteger;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface PermissionLocal {

    DataObjectPermission newDataObjectPermission();

    DataObjectPermission saveDataObjectPermission(DataObjectPermission dataObjectPermission);

    void deleteDataObjectPermission(DataObjectPermission dataObjectPermission);

    Set<DataObjectPermission> getDataObjectPermissions();

    Set<DataObjectPermission> getDataObjectPermissions(DataObject dataObject);

    Set<DataObjectPermission> getDataObjectPermissions(DataObject dataObject, UserRightType userRightType);

    DataObjectTypePermission newDataObjectTypePermission();

    DataObjectTypePermission saveDataObjectTypePermission(DataObjectTypePermission dataObjectTypePermission);

    void deleteDataObjectTypePermission(DataObjectTypePermission dataObjectTypePermission);

    Set<DataObjectTypePermission> getDataObjectTypePermissions();

    Set<DataObjectTypePermission> getDataObjectTypePermissions(DataObjectType dataObjectType);

    Set<DataObjectTypePermission> getDataObjectTypePermissions(Class<? extends DataObjectBean> entityClass);

    Set<DataObjectTypePermission> getDataObjectTypePermissions(DataObjectType dataObjectType, UserRightType userRightType);

    Set<DataObjectTypePermission> getDataObjectTypePermissions(Class<? extends DataObjectBean> entityClass, UserRightType userRightType);
}
