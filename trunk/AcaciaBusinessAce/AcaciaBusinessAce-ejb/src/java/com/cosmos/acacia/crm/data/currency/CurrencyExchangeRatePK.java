/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.currency;

import java.io.Serializable;
import java.util.UUID;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Embeddable
public class CurrencyExchangeRatePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "organization_id", nullable = false)
    @Type(type="uuid")
    private UUID organizationId;

    @Basic(optional = false)
    @Column(name = "valid_from", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    @Basic(optional = false)
    @Column(name = "from_currency_id", nullable = false)
    private int fromCurrencyId;

    @Basic(optional = false)
    @Column(name = "to_currency_id", nullable = false)
    private int toCurrencyId;

    public CurrencyExchangeRatePK() {
    }

    public CurrencyExchangeRatePK(UUID organizationId, Date validFrom, int fromCurrencyId, int toCurrencyId) {
        this.organizationId = organizationId;
        this.validFrom = validFrom;
        this.fromCurrencyId = fromCurrencyId;
        this.toCurrencyId = toCurrencyId;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public int getFromCurrencyId() {
        return fromCurrencyId;
    }

    public void setFromCurrencyId(int fromCurrencyId) {
        this.fromCurrencyId = fromCurrencyId;
    }

    public int getToCurrencyId() {
        return toCurrencyId;
    }

    public void setToCurrencyId(int toCurrencyId) {
        this.toCurrencyId = toCurrencyId;
    }

    @Override
    public int hashCode() {
        int hash = organizationId != null ? organizationId.hashCode() : 0;
        hash += validFrom != null ? validFrom.hashCode() : 0;
        hash += (int) fromCurrencyId;
        hash += (int) toCurrencyId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CurrencyExchangeRatePK)) {
            return false;
        }

        CurrencyExchangeRatePK other = (CurrencyExchangeRatePK) object;
        if ((organizationId == null && other.organizationId != null) || (organizationId != null && !organizationId.equals(other.organizationId))) {
            return false;
        }

        if ((validFrom == null && other.validFrom != null) || (validFrom != null && !validFrom.equals(other.validFrom))) {
            return false;
        }

        if (fromCurrencyId != other.fromCurrencyId) {
            return false;
        }

        return toCurrencyId == other.toCurrencyId;
    }

    @Override
    public String toString() {
        return "CurrencyExchangeRatePK[organizationId=" + organizationId + ", validFrom=" + validFrom + ", fromCurrencyId=" + fromCurrencyId + ", toCurrencyId=" + toCurrencyId + "]";
    }
}
