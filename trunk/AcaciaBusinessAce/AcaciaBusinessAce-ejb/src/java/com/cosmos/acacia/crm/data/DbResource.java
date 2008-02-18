/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author miro
 */
@Entity
@Table(name = "resource_bundle")
@NamedQueries(
    {
        @NamedQuery(
            name = "DbResource.findByResourceId",
            query = "SELECT d FROM DbResource d WHERE d.resourceId = :resourceId"),
        @NamedQuery(
            name = "DbResource.findByEnumClassAndName",
            query = "SELECT d FROM DbResource d WHERE d.enumClass = :enumClass and d.enumName = :enumName")
    }
)
public class DbResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="ResourceBundleSequenceGenerator", sequenceName="resource_bundle_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ResourceBundleSequenceGenerator")
    @Column(name = "resource_id", nullable = false)
    private Integer resourceId;

    @Column(name = "enum_name", nullable = false)
    private String enumName;

    @JoinColumn(name = "enum_class_id", referencedColumnName = "enum_class_id")
    @ManyToOne
    private EnumClass enumClass;

    public DbResource() {
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public EnumClass getEnumClass() {
        return enumClass;
    }

    public void setEnumClass(EnumClass enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourceId != null ? resourceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbResource)) {
            return false;
        }
        DbResource other = (DbResource) object;
        if ((this.resourceId == null && other.resourceId != null) || (this.resourceId != null && !this.resourceId.equals(other.resourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DbResource[resourceId=" + resourceId + "]";
    }

}
