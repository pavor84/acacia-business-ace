/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.resource.EnumResource;
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
import org.apache.log4j.Logger;

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
            query = "SELECT d FROM DbResource d WHERE d.enumClass = :enumClass and d.enumName = :enumName"),
        @NamedQuery(
            name = "DbResource.findAllByEnumClass",
            query = "SELECT d FROM DbResource d WHERE d.enumClass = :enumClass")
    }
)
public class DbResource
    implements Serializable, EnumResource
{
    private static final Logger logger = Logger.getLogger(DbResource.class);

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
        if(object == null)
            return false;

        if (!(object instanceof DbResource)) {
            return false;
        }
        DbResource other = (DbResource) object;
        if ((resourceId == null && other.resourceId != null) || (resourceId != null && !resourceId.equals(other.resourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DbResource[resourceId=" + resourceId +
            ", enumName=" + enumName +
            ", enumClass=" + enumClass + "]";
    }

    @Override
    public Enum getEnumValue()
    {
        try
        {
            logger.debug("DbResource: " + toString());
            Class cls = Class.forName(enumClass.getEnumClassName());
            Enum enumValue = Enum.valueOf(cls, enumName);
            logger.debug("enumValue: " + enumValue);
            return enumValue;
        }
        catch(Exception ex)
        {
            logger.fatal(toString(), ex);
            throw new RuntimeException(toString(), ex);
        }
    }
}
