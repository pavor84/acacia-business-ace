/**
 * 
 */
package com.cosmos.acacia.crm.bl.cash;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cosmos.acacia.crm.data.DbResource;

/**
 * Created	:	04.05.2009
 * @author	Petar Milev
 *
 */
public class EndBalance implements Serializable{
    private DbResource currency;
    private BigDecimal amount;
    private BigDecimal amountDefCurrency;
    public EndBalance() {
        super();
    }
    public EndBalance(DbResource currency, BigDecimal amount, BigDecimal amountDefCurrency) {
        super();
        this.currency = currency;
        this.amount = amount;
        this.amountDefCurrency = amountDefCurrency;
    }
    public DbResource getCurrency() {
        return currency;
    }
    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getAmountDefCurrency() {
        return amountDefCurrency;
    }
    public void setAmountDefCurrency(BigDecimal amountDefCurrency) {
        this.amountDefCurrency = amountDefCurrency;
    }
}
