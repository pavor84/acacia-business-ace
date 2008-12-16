/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.properties;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Miro
 */
@Embeddable
public class DbPropertyPK
    implements Serializable
{
    @Basic(optional = false)
    @Column(name = "access_level", nullable = false)
    private String accessLevel;

    @Basic(optional = false)
    @Column(name = "related_object_id", nullable = false)
    private BigInteger relatedObjectId;

    @Basic(optional = false)
    @Column(name = "property_key", nullable = false, length = 64)
    private String propertyKey;

    public DbPropertyPK()
    {
    }

    public DbPropertyPK(String accessLevel, BigInteger relatedObjectId, String propertyKey)
    {
        this.accessLevel = accessLevel;
        this.relatedObjectId = relatedObjectId;
        this.propertyKey = propertyKey;
    }

    public String getAccessLevel()
    {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel)
    {
        this.accessLevel = accessLevel;
    }

    public BigInteger getRelatedObjectId()
    {
        return relatedObjectId;
    }

    public void setRelatedObjectId(BigInteger relatedObjectId)
    {
        this.relatedObjectId = relatedObjectId;
    }

    public String getPropertyKey()
    {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey)
    {
        this.propertyKey = propertyKey;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (accessLevel != null ? accessLevel.hashCode() : 0);
        hash += (relatedObjectId != null ? relatedObjectId.hashCode() : 0);
        hash += (propertyKey != null ? propertyKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof DbPropertyPK))
        {
            return false;
        }

        DbPropertyPK other = (DbPropertyPK) object;
        if(this.accessLevel == null || other.accessLevel == null ||
           !this.accessLevel.equals(other.accessLevel))
        {
            return false;
        }

        if(this.relatedObjectId == null || other.relatedObjectId == null ||
           !this.relatedObjectId.equals(other.relatedObjectId))
        {
            return false;
        }

        if(this.propertyKey == null || other.propertyKey == null ||
           !this.propertyKey.equals(other.propertyKey))
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "com.cosmos.acacia.crm.data.properties.DbPropertyPK[accessLevel=" + accessLevel + ", relatedObjectId=" + relatedObjectId + ", propertyKey=" + propertyKey + "]";
    }

}
