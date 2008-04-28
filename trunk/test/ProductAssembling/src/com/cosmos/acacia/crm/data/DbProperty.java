/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "db_properties")
@NamedQueries({})
public class DbProperty
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "property_key", nullable = false)
    private String propertyKey;

    //@Lob
    @Column(name = "property_value")
    private Serializable propertyValue;

    public DbProperty() {
    }

    public DbProperty(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Serializable propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propertyKey != null ? propertyKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbProperty)) {
            return false;
        }
        DbProperty other = (DbProperty) object;
        if ((this.propertyKey == null && other.propertyKey != null) || (this.propertyKey != null && !this.propertyKey.equals(other.propertyKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DbProperty[propertyKey=" + propertyKey + "]";
    }

}
