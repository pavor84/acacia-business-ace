/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.permission;

import java.io.Serializable;
import java.util.UUID;
import java.util.Comparator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Embeddable
public class DataObjectPermissionPK
        implements Serializable, Comparable<DataObjectPermissionPK>, Comparator<DataObjectPermissionPK> {

    @Basic(optional = false)
    @Column(name = "organization_id", nullable = false)
    @Type(type="uuid")
    private UUID organizationId;

    @Basic(optional = false)
    @Column(name = "data_object_id", nullable = false)
    @Type(type="uuid")
    private UUID dataObjectId;

    @Basic(optional = false)
    @Column(name = "user_right_type_id", nullable = false)
    private int userRightTypeId;

    @Basic(optional = false)
    @Column(name = "permission_id", nullable = false)
    private int permissionId;

    public DataObjectPermissionPK() {
    }

    public DataObjectPermissionPK(UUID organizationId, UUID dataObjectId, int userRightTypeId, int permissionId) {
        this.organizationId = organizationId;
        this.dataObjectId = dataObjectId;
        this.userRightTypeId = userRightTypeId;
        this.permissionId = permissionId;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public UUID getDataObjectId() {
        return dataObjectId;
    }

    public void setDataObjectId(UUID dataObjectId) {
        this.dataObjectId = dataObjectId;
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
        int hash = (organizationId != null ? organizationId.hashCode() : 0);
        hash += dataObjectId != null ? dataObjectId.hashCode() : 0;
        hash += userRightTypeId;
        hash += permissionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataObjectPermissionPK)) {
            return false;
        }
        DataObjectPermissionPK other = (DataObjectPermissionPK) object;
        if ((organizationId == null && other.organizationId != null) || (organizationId != null && !organizationId.equals(other.organizationId))) {
            return false;
        }
        if (this.dataObjectId != other.dataObjectId) {
            return false;
        }
        if (this.userRightTypeId != other.userRightTypeId) {
            return false;
        }
        if (this.permissionId != other.permissionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataObjectPermissionPK[dataObjectId=" + dataObjectId + ", userRightTypeId=" + userRightTypeId + ", permissionId=" + permissionId + "]";
    }

    @Override
    public int compareTo(DataObjectPermissionPK o) {
        return compare(this, o);
    }

    @Override
    public int compare(DataObjectPermissionPK o1, DataObjectPermissionPK o2) {
        int result;
        if((result = o1.organizationId.compareTo(o2.organizationId)) != 0) {
            return result;
        }
        if((result = o1.dataObjectId.compareTo(o2.dataObjectId)) != 0) {
            return result;
        }
        if((result = o1.userRightTypeId - o2.userRightTypeId) != 0) {
            return result;
        }
        return o1.permissionId - o2.permissionId;
    }
}
