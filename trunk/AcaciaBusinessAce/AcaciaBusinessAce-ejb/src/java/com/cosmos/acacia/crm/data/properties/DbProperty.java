/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.properties;

import com.cosmos.acacia.crm.data.DataObject;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Miro
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "db_properties", catalog = "acacia", schema = "public")
@NamedQueries(
    {
        @NamedQuery(
            name = "DbProperty.findByLevelAndRelatedObjectId",
            query = "SELECT d FROM DbProperty d" +
                " WHERE" +
                " d.dbPropertyPK.accessLevel = :accessLevel" +
                " AND" +
                " d.dbPropertyPK.relatedObjectId = :relatedObjectId"/*,
            hints=@QueryHint(name="org.hibernate.cacheable", value="true")*/
            ),
        @NamedQuery(
            name = "DbProperty.findByLevelAndRelatedObjectIdAndPropertyKey",
            query = "SELECT d FROM DbProperty d" +
                " WHERE" +
                " d.dbPropertyPK.accessLevel = :accessLevel" +
                " AND" +
                " d.dbPropertyPK.relatedObjectId = :relatedObjectId" +
                " AND" +
                " d.dbPropertyPK.propertyKey = :propertyKey"/*,
            hints=@QueryHint(name="org.hibernate.cacheable", value="true")*/
            ),
        @NamedQuery(
            name = "DbProperty.removeByLevelAndRelatedObjectIdAndPropertyKeys",
            query = "DELETE FROM DbProperty d" +
                " WHERE" +
                " d.dbPropertyPK.accessLevel = :accessLevel" +
                " AND" +
                " d.dbPropertyPK.relatedObjectId = :relatedObjectId" +
                " AND" +
                " d.dbPropertyPK.propertyKey in (:propertyKeys)"/*,
            hints=@QueryHint(name="org.hibernate.cacheable", value="true")*/
            )
    })
public class DbProperty
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected DbPropertyPK dbPropertyPK;

    //@Lob
    @Column(name = "property_value")
    private Serializable propertyValue;

    @JoinColumn(
        name = "related_object_id",
        referencedColumnName = "data_object_id",
        nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DataObject dataObject;

    public DbProperty()
    {
    }

    public DbProperty(DbPropertyPK dbPropertyPK)
    {
        this.dbPropertyPK = dbPropertyPK;
    }

    public DbProperty(String accessLevel, UUID relatedObjectId, String propertyKey)
    {
        this.dbPropertyPK = new DbPropertyPK(accessLevel, relatedObjectId, propertyKey);
    }

    public DbPropertyPK getDbPropertyPK()
    {
        return dbPropertyPK;
    }

    public void setDbPropertyPK(DbPropertyPK dbPropertyPK)
    {
        this.dbPropertyPK = dbPropertyPK;
    }

    public Serializable getPropertyValue()
    {
        return propertyValue;
    }

    public void setPropertyValue(Serializable propertyValue)
    {
        this.propertyValue = propertyValue;
    }

    public DataObject getDataObject()
    {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject)
    {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode()
    {
        return (dbPropertyPK != null ? dbPropertyPK.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof DbProperty))
        {
            return false;
        }

        DbProperty other = (DbProperty)object;
        if(this.dbPropertyPK == null || other.dbPropertyPK == null ||
           !this.dbPropertyPK.equals(other.dbPropertyPK))
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "com.cosmos.acacia.crm.data.properties.DbProperty[dbPropertyPK=" + dbPropertyPK + "]";
    }

}
