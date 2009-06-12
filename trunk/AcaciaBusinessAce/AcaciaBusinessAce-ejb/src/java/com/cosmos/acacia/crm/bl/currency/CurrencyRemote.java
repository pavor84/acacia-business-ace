/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.currency;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.entity.EntityService;
import java.util.Date;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface CurrencyRemote extends EntityService {

    CurrencyExchangeRate getCurrencyExchangeRate(Date rateForDate, DbResource fromCurrency, DbResource toCurrency);
}
