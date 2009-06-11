/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.currency;

import com.cosmos.acacia.crm.data.DbResource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "currency_exchange_rates", catalog = "acacia", schema = "public")
@NamedQueries({
    @NamedQuery(
        name = "CurrencyExchangeRate.findAll",
        query = "SELECT c FROM CurrencyExchangeRate c"),
    @NamedQuery(
        name = "CurrencyExchangeRate.findByOrganizationId",
        query = "SELECT c FROM CurrencyExchangeRate c" +
                " WHERE" +
                "  c.currencyExchangeRatePK.organizationId = :organizationId"),
    @NamedQuery(
        name = "CurrencyExchangeRate.findByValidityAndCurrency",
        query = "SELECT c FROM CurrencyExchangeRate c" +
                " WHERE" +
                "  c.currencyExchangeRatePK.organizationId = :organizationId" +
                "  and c.currencyExchangeRatePK.validFrom <= :validFrom" +
                "  and c.currencyExchangeRatePK.fromCurrencyId = :fromCurrencyId" +
                "  and c.currencyExchangeRatePK.toCurrencyId = :toCurrencyId" +
                "  and (c.validUntil >= :validUntil or c.validUntil is null)"
    ),
    @NamedQuery(name = "CurrencyExchangeRate.findByFromCurrencyId", query = "SELECT c FROM CurrencyExchangeRate c WHERE c.currencyExchangeRatePK.fromCurrencyId = :fromCurrencyId"),
    @NamedQuery(name = "CurrencyExchangeRate.findByToCurrencyId", query = "SELECT c FROM CurrencyExchangeRate c WHERE c.currencyExchangeRatePK.toCurrencyId = :toCurrencyId"),
    @NamedQuery(name = "CurrencyExchangeRate.findByValidUntil", query = "SELECT c FROM CurrencyExchangeRate c WHERE c.validUntil = :validUntil"),
    @NamedQuery(name = "CurrencyExchangeRate.findByExchangeRate", query = "SELECT c FROM CurrencyExchangeRate c WHERE c.exchangeRate = :exchangeRate"),
    @NamedQuery(name = "CurrencyExchangeRate.findByFixedExchangeRate", query = "SELECT c FROM CurrencyExchangeRate c WHERE c.fixedExchangeRate = :fixedExchangeRate")
})
public class CurrencyExchangeRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected CurrencyExchangeRatePK currencyExchangeRatePK;

    @Column(name = "valid_until")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;

    @Basic(optional = false)
    @Column(name = "exchange_rate", nullable = false, precision = 10, scale = 6)
    private BigDecimal exchangeRate;

    @Basic(optional = false)
    @Column(name = "fixed_exchange_rate", nullable = false)
    private boolean fixedExchangeRate;

    @JoinColumn(name = "from_currency_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbResource fromCurrency;

    @JoinColumn(name = "to_currency_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbResource toCurrency;

    public CurrencyExchangeRate() {
    }

    public CurrencyExchangeRate(CurrencyExchangeRatePK currencyExchangeRatePK) {
        this.currencyExchangeRatePK = currencyExchangeRatePK;
    }

    public CurrencyExchangeRate(CurrencyExchangeRatePK currencyExchangeRatePK, BigDecimal exchangeRate, boolean fixedExchangeRate) {
        this.currencyExchangeRatePK = currencyExchangeRatePK;
        this.exchangeRate = exchangeRate;
        this.fixedExchangeRate = fixedExchangeRate;
    }

    public CurrencyExchangeRate(BigInteger organizationId, Date validFrom, int fromCurrencyId, int toCurrencyId) {
        this.currencyExchangeRatePK = new CurrencyExchangeRatePK(organizationId, validFrom, fromCurrencyId, toCurrencyId);
    }

    public CurrencyExchangeRatePK getCurrencyExchangeRatePK() {
        if(currencyExchangeRatePK == null) {
            currencyExchangeRatePK = new CurrencyExchangeRatePK();
        }

        return currencyExchangeRatePK;
    }

    public void setCurrencyExchangeRatePK(CurrencyExchangeRatePK currencyExchangeRatePK) {
        this.currencyExchangeRatePK = currencyExchangeRatePK;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public boolean getFixedExchangeRate() {
        return fixedExchangeRate;
    }

    public void setFixedExchangeRate(boolean fixedExchangeRate) {
        this.fixedExchangeRate = fixedExchangeRate;
    }

    public DbResource getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(DbResource fromCurrency) {
        this.fromCurrency = fromCurrency;
        if(fromCurrency != null) {
            getCurrencyExchangeRatePK().setFromCurrencyId(fromCurrency.getResourceId());
        } else {
            getCurrencyExchangeRatePK().setFromCurrencyId(0);
        }
    }

    public DbResource getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(DbResource toCurrency) {
        this.toCurrency = toCurrency;
        if(toCurrency != null) {
            getCurrencyExchangeRatePK().setToCurrencyId(toCurrency.getResourceId());
        } else {
            getCurrencyExchangeRatePK().setToCurrencyId(0);
        }
    }

    @Override
    public int hashCode() {
        if(currencyExchangeRatePK != null) {
            return currencyExchangeRatePK.hashCode();
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CurrencyExchangeRate)) {
            return false;
        }

        CurrencyExchangeRate other = (CurrencyExchangeRate) object;
        if (currencyExchangeRatePK == null && other.currencyExchangeRatePK != null) {
            return false;
        }

        return currencyExchangeRatePK != null && !currencyExchangeRatePK.equals(other.currencyExchangeRatePK);
    }

    @Override
    public String toString() {
        return "CurrencyExchangeRate[currencyExchangeRatePK=" + currencyExchangeRatePK + "]";
    }




}
