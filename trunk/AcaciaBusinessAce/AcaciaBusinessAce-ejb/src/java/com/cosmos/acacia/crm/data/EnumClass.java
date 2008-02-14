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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author miro
 */
@Entity
@Table(name = "enum_classes")
@NamedQueries(
    {
        @NamedQuery(name = "EnumClass.findByEnumClassId", query = "SELECT e FROM EnumClass e WHERE e.enumClassId = :enumClassId"),
        @NamedQuery(name = "EnumClass.findByEnumClassName", query = "SELECT e FROM EnumClass e WHERE e.enumClassName = :enumClassName")
    }
)
public class EnumClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "enum_class_id", nullable = false)
    private Integer enumClassId;

    @Column(name = "enum_class_name", nullable = false)
    private String enumClassName;

    public EnumClass() {
    }

    public EnumClass(Integer enumClassId) {
        this.enumClassId = enumClassId;
    }

    public EnumClass(Integer enumClassId, String enumClassName) {
        this.enumClassId = enumClassId;
        this.enumClassName = enumClassName;
    }

    public Integer getEnumClassId() {
        return enumClassId;
    }

    public void setEnumClassId(Integer enumClassId) {
        this.enumClassId = enumClassId;
    }

    public String getEnumClassName() {
        return enumClassName;
    }

    public void setEnumClassName(String enumClassName) {
        this.enumClassName = enumClassName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enumClassId != null ? enumClassId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EnumClass)) {
            return false;
        }
        EnumClass other = (EnumClass) object;
        if ((this.enumClassId == null && other.enumClassId != null) || (this.enumClassId != null && !this.enumClassId.equals(other.enumClassId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.EnumClass[enumClassId=" + enumClassId + "]";
    }

}
