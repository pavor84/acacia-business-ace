/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.currency;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRatePK;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class CurrencyBean implements CurrencyRemote, CurrencyLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    @EJB
    private AcaciaSessionLocal session;

    private List<CurrencyExchangeRate> getCurrencyExchangeRates(Date ratesForDate) {
        Query q = em.createNamedQuery(CurrencyExchangeRate.FIND_ALL_BY_VALIDITY);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("validFrom", ratesForDate);
        q.setParameter("validUntil", ratesForDate);
        return new ArrayList<CurrencyExchangeRate>(q.getResultList());
    }

    @Override
    public CurrencyExchangeRate getCurrencyExchangeRate(Date rateForDate, DbResource fromCurrency, DbResource toCurrency) {
        Query q = em.createNamedQuery(CurrencyExchangeRate.FIND_BY_VALIDITY_AND_CURRENCY);
        q.setParameter("organizationId", session.getOrganization().getId());
        q.setParameter("validFrom", rateForDate);
        q.setParameter("fromCurrencyId", fromCurrency.getResourceId());
        q.setParameter("toCurrencyId", toCurrency.getResourceId());
        q.setParameter("validUntil", rateForDate);
        try {
            return (CurrencyExchangeRate)q.getSingleResult();
        } catch(NoResultException ex) {
            q.setParameter("fromCurrencyId", toCurrency.getResourceId());
            q.setParameter("toCurrencyId", fromCurrency.getResourceId());
            try {
                CurrencyExchangeRate cer = (CurrencyExchangeRate)q.getSingleResult();
                cer.setFromCurrency(toCurrency);
                cer.setToCurrency(fromCurrency);
                BigDecimal exchangeRate = BigDecimal.ONE.divide(cer.getExchangeRate(), MathContext.DECIMAL64);
                cer.setExchangeRate(exchangeRate);
                return cer;
            } catch(NoResultException ex1) {
                return null;
            }
        }
    }

    @Override
    public Object newEntity(Class entityClass) {
        if(CurrencyExchangeRate.class == entityClass) {
            CurrencyExchangeRatePK cerPK = new CurrencyExchangeRatePK();
            cerPK.setOrganizationId(session.getOrganization().getId());
            CurrencyExchangeRate cer = new CurrencyExchangeRate(cerPK);
            return cer;
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E, I> I newItem(E entity, Class<I> itemClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List getEntities(Class entityClass, Object... extraParameters) {
        if(CurrencyExchangeRate.class == entityClass) {
            if(extraParameters.length < 1) {
                throw new IllegalArgumentException("The ratesForDate parameter is required.");
            }
            if(extraParameters[0] == null) {
                throw new IllegalArgumentException("The first parameter of Date type can not be NULL.");
            }
            if(!(extraParameters[0] instanceof Date)) {
                throw new IllegalArgumentException("The first parameter MUST be from Date type:" +
                        extraParameters[0]);
            }

            return getCurrencyExchangeRates((Date)extraParameters[0]);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> E save(E entity) {
        if(entity instanceof CurrencyExchangeRate) {
            return (E)saveCurrencyExchangeRate((CurrencyExchangeRate)entity);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    private CurrencyExchangeRate saveCurrencyExchangeRate(CurrencyExchangeRate currencyExchangeRate) {
        Query q = em.createNamedQuery(CurrencyExchangeRate.UPDATE_BY_VALIDITY_AND_CURRENCY);
        q.setParameter("organizationId", session.getOrganization().getId());
        Date validityDate = currencyExchangeRate.getValidFrom();
        q.setParameter("validFrom", validityDate);
        q.setParameter("fromCurrencyId", currencyExchangeRate.getFromCurrency().getResourceId());
        q.setParameter("toCurrencyId", currencyExchangeRate.getToCurrency().getResourceId());
        q.setParameter("validUntil", validityDate);
        q.setParameter("validUntilDate", new Date(validityDate.getTime() - 1));
        q.executeUpdate();

        if(currencyExchangeRate.getValidUntil() == null && !currencyExchangeRate.isFixedExchangeRate()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(validityDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            currencyExchangeRate.setValidUntil(calendar.getTime());
        }

        esm.persist(em, currencyExchangeRate);

        return currencyExchangeRate;
    }

    @Override
    public <E> E confirm(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> E cancel(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> void delete(E entity) {
        if(entity instanceof CurrencyExchangeRate) {
            esm.remove(em, entity);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> EntityProperties getEntityProperties(Class<E> entityClass) {
        return esm.getEntityProperties(entityClass);
    }

    @Override
    public List getResources(Class<? extends Enum> enumClass, Class<? extends Enum>... enumCategoryClasses) {
        return esm.getResources(em, enumClass, enumCategoryClasses);
    }
}
