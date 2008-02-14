/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author miro
 */
@Entity
@Table(name = "resource_bundle")
@NamedQueries(
    {
        @NamedQuery(name = "DbResourceBundle.findByResourceId", query = "SELECT d FROM DbResourceBundle d WHERE d.resourceId = :resourceId"),
        @NamedQuery(name = "DbResourceBundle.findByEnumName", query = "SELECT d FROM DbResourceBundle d WHERE d.enumName = :enumName"),
        @NamedQuery(name = "DbResourceBundle.findByLanguageCode", query = "SELECT d FROM DbResourceBundle d WHERE d.languageCode = :languageCode"),
        @NamedQuery(name = "DbResourceBundle.findByCountryCode", query = "SELECT d FROM DbResourceBundle d WHERE d.countryCode = :countryCode"),
        @NamedQuery(name = "DbResourceBundle.findByResourceValue", query = "SELECT d FROM DbResourceBundle d WHERE d.resourceValue = :resourceValue"),
        @NamedQuery(name = "DbResourceBundle.findByToolTip", query = "SELECT d FROM DbResourceBundle d WHERE d.toolTip = :toolTip"),
        @NamedQuery(name = "DbResourceBundle.findByHelp", query = "SELECT d FROM DbResourceBundle d WHERE d.help = :help"),
        @NamedQuery(name = "DbResourceBundle.findByDescription", query = "SELECT d FROM DbResourceBundle d WHERE d.description = :description")
    }
)
public class DbResourceBundle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "resource_id", nullable = false)
    private Integer resourceId;

    @Column(name = "enum_name", nullable = false)
    private String enumName;

    @Column(name = "language_code", nullable = false)
    private String languageCode;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "resource_value", nullable = false)
    private String resourceValue;

    @Column(name = "tool_tip")
    private String toolTip;

    @Column(name = "help")
    private String help;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "enum_class_id", referencedColumnName = "enum_class_id")
    @ManyToOne
    private EnumClass enumClassId;

    public DbResourceBundle() {
    }

    public DbResourceBundle(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public DbResourceBundle(Integer resourceId, String enumName, String languageCode, String countryCode, String resourceValue) {
        this.resourceId = resourceId;
        this.enumName = enumName;
        this.languageCode = languageCode;
        this.countryCode = countryCode;
        this.resourceValue = resourceValue;
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

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EnumClass getEnumClassId() {
        return enumClassId;
    }

    public void setEnumClassId(EnumClass enumClassId) {
        this.enumClassId = enumClassId;
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
        if (!(object instanceof DbResourceBundle)) {
            return false;
        }
        DbResourceBundle other = (DbResourceBundle) object;
        if ((this.resourceId == null && other.resourceId != null) || (this.resourceId != null && !this.resourceId.equals(other.resourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DbResourceBundle[resourceId=" + resourceId + "]";
    }

}
