/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.permission;

import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.DbResource;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "data_object_type_permissions", catalog = "acacia", schema = "public")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
    @NamedQuery(
        name = DataObjectTypePermission.NQ_FIND_ALL,
        query = "SELECT d FROM " + DataObjectTypePermission.CLASS_NAME + " d" +
                " WHERE" +
                "  d.dataObjectTypePermissionPK.organizationId = :organizationId",
        hints={
            @QueryHint(name="org.hibernate.cacheable", value="true")
        }
    ),
    @NamedQuery(
        name = DataObjectTypePermission.NQ_FIND_BY_DOT,
        query = "SELECT d FROM " + DataObjectTypePermission.CLASS_NAME + " d" +
                " WHERE" +
                "  d.dataObjectTypePermissionPK.organizationId = :organizationId" +
                "  and d.dataObjectTypePermissionPK.dataObjectTypeId = :dataObjectTypeId",
        hints={
            @QueryHint(name="org.hibernate.cacheable", value="true")
        }
    ),
    @NamedQuery(
        name = DataObjectTypePermission.NQ_FIND_BY_DOT_AND_URT,
        query = "SELECT d FROM " + DataObjectTypePermission.CLASS_NAME + " d" +
                " WHERE" +
                "  d.dataObjectTypePermissionPK.organizationId = :organizationId" +
                "  and d.dataObjectTypePermissionPK.dataObjectTypeId = :dataObjectTypeId" +
                "  and d.dataObjectTypePermissionPK.userRightTypeId = :userRightTypeId",
        hints={
            @QueryHint(name="org.hibernate.cacheable", value="true")
        }
    )
})
public class DataObjectTypePermission
        implements Serializable, Comparable<DataObjectTypePermission>, Comparator<DataObjectTypePermission> {

    private static final long serialVersionUID = 1L;
    //
    public static final String CLASS_NAME = "DataObjectTypePermission";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_DOT = CLASS_NAME + ".findByDOT";
    public static final String NQ_FIND_BY_DOT_AND_URT = CLASS_NAME + ".findByDOTandURT";

    @EmbeddedId
    protected DataObjectTypePermissionPK dataObjectTypePermissionPK;

    @JoinColumn(name = "data_object_type_id", referencedColumnName = "data_object_type_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DataObjectType dataObjectType;

    @JoinColumn(name = "permission_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbResource permission;

    @JoinColumn(name = "user_right_type_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbResource userRightType;

    public DataObjectTypePermission() {
    }

    public DataObjectTypePermission(DataObjectTypePermissionPK dataObjectTypePermissionPK) {
        this.dataObjectTypePermissionPK = dataObjectTypePermissionPK;
    }

    public DataObjectTypePermission(BigInteger organizationId, int dataObjectTypeId, int userRightTypeId, int permissionId) {
        this.dataObjectTypePermissionPK = new DataObjectTypePermissionPK(organizationId, dataObjectTypeId, userRightTypeId, permissionId);
    }

    public DataObjectTypePermissionPK getDataObjectTypePermissionPK() {
        if(dataObjectTypePermissionPK == null) {
            dataObjectTypePermissionPK = new DataObjectTypePermissionPK();
        }

        return dataObjectTypePermissionPK;
    }

    public void setDataObjectTypePermissionPK(DataObjectTypePermissionPK dataObjectTypePermissionPK) {
        this.dataObjectTypePermissionPK = dataObjectTypePermissionPK;
    }

    public DataObjectType getDataObjectType() {
        return dataObjectType;
    }

    public void setDataObjectType(DataObjectType dataObjectType) {
        this.dataObjectType = dataObjectType;
        DataObjectTypePermissionPK pk = getDataObjectTypePermissionPK();
        if(dataObjectType != null) {
            pk.setDataObjectTypeId(dataObjectType.getDataObjectTypeId());
        } else {
            pk.setDataObjectTypeId(0);
        }
    }

    public DbResource getPermission() {
        return permission;
    }

    public void setPermission(DbResource permission) {
        this.permission = permission;
        DataObjectTypePermissionPK pk = getDataObjectTypePermissionPK();
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
        DataObjectTypePermissionPK pk = getDataObjectTypePermissionPK();
        if(userRightType != null) {
            pk.setUserRightTypeId(userRightType.getResourceId());
        } else {
            pk.setUserRightTypeId(0);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataObjectTypePermissionPK != null ? dataObjectTypePermissionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataObjectTypePermission)) {
            return false;
        }
        DataObjectTypePermission other = (DataObjectTypePermission) object;
        if ((this.dataObjectTypePermissionPK == null && other.dataObjectTypePermissionPK != null) || (this.dataObjectTypePermissionPK != null && !this.dataObjectTypePermissionPK.equals(other.dataObjectTypePermissionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataObjectTypePermission[dataObjectTypePermissionPK=" + dataObjectTypePermissionPK + "]";
    }

    @Override
    public int compareTo(DataObjectTypePermission o) {
        return compare(this, o);
    }

    @Override
    public int compare(DataObjectTypePermission o1, DataObjectTypePermission o2) {
        return o1.getDataObjectTypePermissionPK().compareTo(o2.getDataObjectTypePermissionPK());
    }
}
