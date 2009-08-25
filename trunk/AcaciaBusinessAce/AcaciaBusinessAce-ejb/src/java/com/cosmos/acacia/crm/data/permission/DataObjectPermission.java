/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.permission;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import java.io.Serializable;
import java.util.UUID;
import java.util.Comparator;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "data_object_permissions", catalog = "acacia", schema = "public")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
    @NamedQuery(
        name = DataObjectPermission.NQ_FIND_ALL,
        query = "SELECT d FROM " + DataObjectPermission.CLASS_NAME + " d" +
                " WHERE" +
                "  d.dataObjectPermissionPK.organizationId = :organizationId",
        hints={
            @QueryHint(name="org.hibernate.cacheable", value="true")
        }
    ),
    @NamedQuery(
        name = DataObjectPermission.NQ_FIND_BY_DO,
        query = "SELECT d FROM " + DataObjectPermission.CLASS_NAME + " d" +
                " WHERE" +
                "  d.dataObjectPermissionPK.organizationId = :organizationId" +
                "  and d.dataObjectPermissionPK.dataObjectId = :dataObjectId",
        hints={
            @QueryHint(name="org.hibernate.cacheable", value="true")
        }
    ),
    @NamedQuery(
        name = DataObjectPermission.NQ_FIND_BY_DO_AND_URT,
        query = "SELECT d FROM " + DataObjectPermission.CLASS_NAME + " d" +
                " WHERE" +
                "  d.dataObjectPermissionPK.organizationId = :organizationId" +
                "  and d.dataObjectPermissionPK.dataObjectId = :dataObjectId" +
                "  and d.dataObjectPermissionPK.userRightTypeId = :userRightTypeId",
        hints={
            @QueryHint(name="org.hibernate.cacheable", value="true")
        }
    )
})
public class DataObjectPermission
        implements Serializable, Comparable<DataObjectPermission>, Comparator<DataObjectPermission> {

    private static final long serialVersionUID = 1L;
    //
    public static final String CLASS_NAME = "DataObjectPermission";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_DO = CLASS_NAME + ".findByDO";
    public static final String NQ_FIND_BY_DO_AND_URT = CLASS_NAME + ".findByDOandURT";

    @EmbeddedId
    protected DataObjectPermissionPK dataObjectPermissionPK;

    @JoinColumn(name = "data_object_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DataObject dataObject;

    @JoinColumn(name = "permission_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbResource permission;

    @JoinColumn(name = "user_right_type_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbResource userRightType;

    public DataObjectPermission() {
    }

    public DataObjectPermission(DataObjectPermissionPK dataObjectPermissionPK) {
        this.dataObjectPermissionPK = dataObjectPermissionPK;
    }

    public DataObjectPermission(UUID organizationId, UUID dataObjectId, int userRightTypeId, int permissionId) {
        this.dataObjectPermissionPK = new DataObjectPermissionPK(organizationId, dataObjectId, userRightTypeId, permissionId);
    }

    public DataObjectPermissionPK getDataObjectPermissionPK() {
        if(dataObjectPermissionPK == null) {
            dataObjectPermissionPK = new DataObjectPermissionPK();
        }

        return dataObjectPermissionPK;
    }

    public void setDataObjectPermissionPK(DataObjectPermissionPK dataObjectPermissionPK) {
        this.dataObjectPermissionPK = dataObjectPermissionPK;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
        DataObjectPermissionPK pk = getDataObjectPermissionPK();
        if(dataObject != null) {
            pk.setDataObjectId(dataObject.getDataObjectId());
        } else {
            pk.setDataObjectId(null);
        }
    }

    public DbResource getPermission() {
        return permission;
    }

    public void setPermission(DbResource permission) {
        this.permission = permission;
        DataObjectPermissionPK pk = getDataObjectPermissionPK();
        if(permission != null) {
            pk.setPermissionId(permission.getResourceId());
        } else {
            pk.setPermissionId(0);
        }
    }

    public DbResource getUserRightType() {
        return userRightType;
    }

    public void setUserRightType(DbResource userRightType) {
        this.userRightType = userRightType;
        DataObjectPermissionPK pk = getDataObjectPermissionPK();
        if(userRightType != null) {
            pk.setUserRightTypeId(userRightType.getResourceId());
        } else {
            pk.setUserRightTypeId(0);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataObjectPermissionPK != null ? dataObjectPermissionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataObjectPermission)) {
            return false;
        }
        DataObjectPermission other = (DataObjectPermission) object;
        if ((this.dataObjectPermissionPK == null && other.dataObjectPermissionPK != null) || (this.dataObjectPermissionPK != null && !this.dataObjectPermissionPK.equals(other.dataObjectPermissionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataObjectPermission[dataObjectPermissionPK=" + dataObjectPermissionPK + "]";
    }

    @Override
    public int compareTo(DataObjectPermission o) {
        return compare(this, o);
    }

    @Override
    public int compare(DataObjectPermission o1, DataObjectPermission o2) {
        return o1.getDataObjectPermissionPK().compareTo(o2.getDataObjectPermissionPK());
    }
}
