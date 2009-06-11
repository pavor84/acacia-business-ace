/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.currency;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import java.util.Date;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface CurrencyRemote {

    CurrencyExchangeRate getCurrencyExchangeRate(Date rateForDate, DbResource fromCurrency, DbResource toCurrency);
}
