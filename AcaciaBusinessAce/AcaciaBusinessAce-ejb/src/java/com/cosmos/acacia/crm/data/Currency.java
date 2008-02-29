/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "currencies")
@NamedQueries({@NamedQuery(name = "Currency.findByCurrencyId", query = "SELECT c FROM Currency c WHERE c.currencyId = :currencyId"), @NamedQuery(name = "Currency.findByCurrencyName", query = "SELECT c FROM Currency c WHERE c.currencyName = :currencyName"), @NamedQuery(name = "Currency.findByCurrencyCodeA3", query = "SELECT c FROM Currency c WHERE c.currencyCodeA3 = :currencyCodeA3"), @NamedQuery(name = "Currency.findByCurrencyCodeN3", query = "SELECT c FROM Currency c WHERE c.currencyCodeN3 = :currencyCodeN3"), @NamedQuery(name = "Currency.findByDescription", query = "SELECT c FROM Currency c WHERE c.description = :description")})
public class Currency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "currency_id", nullable = false)
    private Integer currencyId;
    @Column(name = "currency_name", nullable = false)
    private String currencyName;
    @Column(name = "currency_code_a3")
    private String currencyCodeA3;
    @Column(name = "currency_code_n3")
    private String currencyCodeN3;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "currencyId")
    private Collection<Organization> organizationCollection;
    @OneToMany(mappedBy = "currencyId")
    private Collection<Country> countryCollection;

    public Currency() {
    }

    public Currency(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Currency(Integer currencyId, String currencyName) {
        this.currencyId = currencyId;
        this.currencyName = currencyName;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCodeA3() {
        return currencyCodeA3;
    }

    public void setCurrencyCodeA3(String currencyCodeA3) {
        this.currencyCodeA3 = currencyCodeA3;
    }

    public String getCurrencyCodeN3() {
        return currencyCodeN3;
    }

    public void setCurrencyCodeN3(String currencyCodeN3) {
        this.currencyCodeN3 = currencyCodeN3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Organization> getOrganizationCollection() {
        return organizationCollection;
    }

    public void setOrganizationCollection(Collection<Organization> organizationCollection) {
        this.organizationCollection = organizationCollection;
    }

    public Collection<Country> getCountryCollection() {
        return countryCollection;
    }

    public void setCountryCollection(Collection<Country> countryCollection) {
        this.countryCollection = countryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currencyId != null ? currencyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currency)) {
            return false;
        }
        Currency other = (Currency) object;
        if ((this.currencyId == null && other.currencyId != null) || (this.currencyId != null && !this.currencyId.equals(other.currencyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Currency[currencyId=" + currencyId + "]";
    }

}
