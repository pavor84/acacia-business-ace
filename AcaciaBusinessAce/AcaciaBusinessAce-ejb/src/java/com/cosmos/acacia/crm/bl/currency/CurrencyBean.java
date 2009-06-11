/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.currency;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Miro
 */
@Stateless
public class CurrencyBean implements CurrencyRemote, CurrencyLocal {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public CurrencyExchangeRate getCurrencyExchangeRate(Date rateForDate, DbResource fromCurrency, DbResource toCurrency) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
