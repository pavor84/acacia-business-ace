/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.currency;

import com.cosmos.acacia.crm.data.*;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRatePK;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
public class CurrencyTest {

    public static final BigInteger ORGANIZATION_ID = BigInteger.valueOf(1209222047860L);
    public static final int EURO_ID = 48;
    public static final int BGL_ID = 49;
    public static final int USD_ID = 50;

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private DbResource euro;
    private DbResource bgl;
    private DbResource usd;

    public EntityManager getEntityManager() {
        if(entityManager != null && !entityManager.isOpen()) {
            entityManager = null;
        }

        if(entityManager == null) {
            entityManager = getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
        }

        return entityManager;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if(entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("AcaciaDatabasePU");
        }

        return entityManagerFactory;
    }

    public void persist(Object object) {
        EntityManager em = getEntityManager();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public CurrencyExchangeRate getCurrencyExchangeRate(Date rateForDate, DbResource fromCurrency, DbResource toCurrency) {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("CurrencyExchangeRate.findByValidityAndCurrency");
        q.setParameter("organizationId", ORGANIZATION_ID);
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

    // 1.95583
    private void init() {
        CurrencyExchangeRatePK cerPK = new CurrencyExchangeRatePK(
                ORGANIZATION_ID, new Date(99, 6, 5), EURO_ID, BGL_ID);
        EntityManager em = getEntityManager();
        CurrencyExchangeRate cer = em.find(CurrencyExchangeRate.class, cerPK);
        if(cer == null) {
            cer = new CurrencyExchangeRate(cerPK);
            cer.setExchangeRate(BigDecimal.valueOf(195583, 5));
            cer.setFixedExchangeRate(true);
            persist(cer);
        }
        System.out.println("cer: " + cer);
        euro = em.find(DbResource.class, EURO_ID);
        System.out.println("euro: " + euro);
        bgl = em.find(DbResource.class, BGL_ID);
        usd = em.find(DbResource.class, USD_ID);
    }

    public void start() {
        init();
        Date today = new Date();

        CurrencyExchangeRate cer = getCurrencyExchangeRate(today, euro, bgl);
        if(cer != null) {
            System.out.println("exchangeRate: " + cer.getExchangeRate() + ", cer: " + cer);
        } else {
            System.out.println("CER is NULL");
        }

        cer = getCurrencyExchangeRate(today, bgl, euro);
        if(cer != null) {
            System.out.println("exchangeRate: " + cer.getExchangeRate() + ", cer: " + cer);
            System.out.println("BigDecimal.ONE.divide(cer.getExchangeRate(), MathContext.DECIMAL64): " +
                    BigDecimal.ONE.divide(cer.getExchangeRate(), MathContext.DECIMAL64));
        } else {
            System.out.println("CER is NULL");
        }
    }

    public void close() {
        if(entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            entityManager = null;
        }
    }

    public static void main(String[] args) {
        CurrencyTest test = new CurrencyTest();
        test.start();
        test.close();
    }
}
