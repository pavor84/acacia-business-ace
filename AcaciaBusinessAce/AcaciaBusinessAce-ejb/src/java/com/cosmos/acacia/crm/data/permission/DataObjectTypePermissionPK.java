/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.permission;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Comparator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Miro
 */
@Embeddable
public class DataObjectTypePermissionPK
        implements Serializable, Comparable<DataObjectTypePermissionPK>, Comparator<DataObjectTypePermissionPK> {

    @Basic(optional = false)
    @Column(name = "organization_id", nullable = false)
    private BigInteger organizationId;

    @Basic(optional = false)
    @Column(name = "data_object_type_id", nullable = false)
    private int dataObjectTypeId;

    @Basic(optional = false)
    @Column(name = "user_right_type_id", nullable = false)
    private int userRightTypeId;

    @Basic(optional = false)
    @Column(name = "permission_id", nullable = false)
    private int permissionId;

    public DataObjectTypePermissionPK() {
    }

    public DataObjectTypePermissionPK(BigInteger organizationId, int dataObjectTypeId, int userRightTypeId, int permissionId) {
        this.organizationId = organizationId;
        this.dataObjectTypeId = dataObjectTypeId;
        this.userRightTypeId = userRightTypeId;
        this.permissionId = permissionId;
    }

    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(BigInteger organizationId) {
        this.organizationId = organizationId;
    }

    public int getDataObjectTypeId() {
        return dataObjectTypeId;
    }

    public void setDataObjectTypeId(int dataObjectTypeId) {
        this.dataObjectTypeId = dataObjectTypeId;
    }

    public int getUserRightTypeId() {
        return userRightTypeId;
    }

    public void setUserRightTypeId(int userRightTypeId) {
        this.userRightTypeId = userRightTypeId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (organizationId != null ? organizationId.hashCode() : 0);
        hash += dataObjectTypeId + userRightTypeId + permissionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataObjectTypePermissionPK)) {
            return false;
        }
        DataObjectTypePermissionPK other = (DataObjectTypePermissionPK) object;
        if ((organizationId == null && other.organizationId != null) || (organizationId != null && !organizationId.equals(other.organizationId))) {
            return false;
        }
        if (dataObjectTypeId != other.dataObjectTypeId) {
            return false;
        }
        if (userRightTypeId != other.userRightTypeId) {
            return false;
        }
        if (permissionId != other.permissionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataObjectTypePermissionPK[dataObjectTypeId=" + dataObjectTypeId + ", userRightTypeId=" + userRightTypeId + ", permissionId=" + permissionId + "]";
    }

    @Override
    public int compareTo(DataObjectTypePermissionPK o) {
        return compare(this, o);
    }

    @Override
    public int compare(DataObjectTypePermissionPK o1, DataObjectTypePermissionPK o2) {
        int result;
        if((result = o1.organizationId.compareTo(o2.organizationId)) != 0) {
            return result;
        }
        if((result = o1.dataObjectTypeId - o2.dataObjectTypeId) != 0) {
            return result;
        }
        if((result = o1.userRightTypeId - o2.userRightTypeId) != 0) {
            return result;
        }
        return o1.permissionId - o2.permissionId;
    }
}
