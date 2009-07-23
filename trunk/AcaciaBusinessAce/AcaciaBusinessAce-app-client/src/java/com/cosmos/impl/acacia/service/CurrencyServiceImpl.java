/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.impl.acacia.service;

import com.cosmos.acacia.crm.bl.currency.CurrencyRemote;
import com.cosmos.acacia.crm.client.LocalSession;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.crm.gui.currency.CurrencyExchangeRateListPanel;
import com.cosmos.acacia.service.ServiceManager;
import com.cosmos.beansbinding.EntityProperties;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Miro
 */
public class CurrencyServiceImpl implements CurrencyRemote {

    @EJB
    private CurrencyRemote currencyService = ServiceManager.getRemoteService(CurrencyRemote.class);

    @Override
    public CurrencyExchangeRate getCurrencyExchangeRate(Date rateForDate, DbResource fromCurrency, DbResource toCurrency) {
        if(fromCurrency.equals(toCurrency)) {
            return new CurrencyExchangeRate(
                    LocalSession.instance().getOrganization().getId(), rateForDate, fromCurrency);
        }

        CurrencyExchangeRateListPanel listPanel;
        CurrencyExchangeRate cer;
        while((cer = currencyService.getCurrencyExchangeRate(rateForDate, fromCurrency, toCurrency)) == null) {
            listPanel = new CurrencyExchangeRateListPanel(rateForDate, fromCurrency, toCurrency);
            listPanel.showDialog();
            listPanel = null;
        }

        return cer;
    }

    @Override
    public <E> E newEntity(Class<E> entityClass) {
        return currencyService.newEntity(entityClass);
    }

    @Override
    public <E, I> I newItem(E entity, Class<I> itemClass) {
        return currencyService.newItem(entity, itemClass);
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, Object... extraParameters) {
        return currencyService.getEntities(entityClass, extraParameters);
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        return currencyService.getEntityItems(entity, itemClass, extraParameters);
    }

    @Override
    public <E> E save(E entity) {
        return currencyService.save(entity);
    }

    @Override
    public <E> E confirm(E entity) {
        return currencyService.confirm(entity);
    }

    @Override
    public <E> E cancel(E entity) {
        return currencyService.cancel(entity);
    }

    @Override
    public <E> void delete(E entity) {
        currencyService.delete(entity);
    }

    @Override
    public <E> EntityProperties getEntityProperties(Class<E> entityClass) {
        return currencyService.getEntityProperties(entityClass);
    }

    @Override
    public List getResources(Class<? extends Enum> enumClass, Object... categoryClassifiers) {
        return currencyService.getResources(enumClass, categoryClassifiers);
    }
}
