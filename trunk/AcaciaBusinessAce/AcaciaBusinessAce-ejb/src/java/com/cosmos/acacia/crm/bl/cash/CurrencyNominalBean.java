package com.cosmos.acacia.crm.bl.cash;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.cash.CurrencyNominal;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	01.05.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class CurrencyNominalBean implements CurrencyNominalRemote, CurrencyNominalLocal{
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    
    public EntityProperties getListingEntityProperties() {

        EntityProperties ep = esm.getEntityProperties(CurrencyNominal.class);
        return ep;
    }

    @SuppressWarnings("unchecked")
    public List<CurrencyNominal> listCurrencyNominals() {
        Query q = em.createNamedQuery("CurrencyNominal.findAll");

        List<CurrencyNominal> result = q.getResultList();
        return result;
    }

    public void deleteCurrencyNominal(CurrencyNominal currencyNominal) {
        if (currencyNominal == null)
            throw new IllegalArgumentException("null: 'CurrencyNominal'");
        esm.remove(em, currencyNominal);
    }

    public CurrencyNominal newCurrencyNominal() {
        CurrencyNominal c = new CurrencyNominal();
        return c;
    }
    
    public EntityProperties getDetailEntityProperties() {
        EntityProperties ep = esm.getEntityProperties(CurrencyNominal.class);
        ep.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        
        return ep;
    }

    public CurrencyNominal saveCurrencyNominal(CurrencyNominal entity) {
        esm.persist(em, entity);

        return entity;
    }

    @Override
    public void initCurrencyNominals() {
        try{
            Map<Currency, Collection<? extends CurrencyNominal>> defaultNominals = 
                new HashMap<Currency,Collection<? extends CurrencyNominal>>();
            //BGN
            defaultNominals.put(Currency.BGN, createNominals(Currency.BGN, new BigDecimal("100")));
            //EUR
            defaultNominals.put(Currency.EUR, createNominals(Currency.EUR, new BigDecimal("500")));
            //USD
            defaultNominals.put(Currency.USD, createNominals(Currency.USD, new BigDecimal("100")));
            
            //init nominals
            initNominals(defaultNominals);
        }catch ( Exception e ){
            e.printStackTrace();
        }
    }

//    private void initNominals(Map<Currency, Collection<? extends CurrencyNominal>> defaultNominals) {
//        for (Map.Entry<Currency, Collection<? extends CurrencyNominal>> nominalsEntry : defaultNominals.entrySet()) {
//            DbResource currency = nominalsEntry.getKey().getDbResource();
//            List<CurrencyNominal> existing = (List<CurrencyNominal>) AcaciaUtils.getResultList(em, 
//                CurrencyNominal.NQ_FIND_BY_CUR, 
//                "currency", currency);
//            if ( existing.isEmpty()){
//                for (CurrencyNominal currencyNominal : nominalsEntry.getValue()) {
//                    esm.persist(em, currencyNominal);
//                }
//            }
//        }
//    }
//    
    private void initNominals(Map<Currency, Collection<? extends CurrencyNominal>> defaultNominals) {
        for (Map.Entry<Currency, Collection<? extends CurrencyNominal>> nominalsEntry : defaultNominals.entrySet()) {
            for (CurrencyNominal currencyNominal : nominalsEntry.getValue()) {
                DbResource currency = nominalsEntry.getKey().getDbResource();
                CurrencyNominal existing = (CurrencyNominal) AcaciaUtils.getSingleResult(em, 
                    CurrencyNominal.NQ_FIND_BY_NOM_AND_CURR, 
                    "currency", currency,
                    "nominal", currencyNominal.getNominal());
                if ( existing==null )
                    esm.persist(em, currencyNominal);
            }
            
            
                
        }
    }

    private Collection<? extends CurrencyNominal> createNominals(Currency currency, BigDecimal biggestNominal) {
        List<CurrencyNominal> result = new ArrayList<CurrencyNominal>();
        int[] n = {1, 2, 5};
        double m = 0.01d;
        int i = 0;
        while ( true ){
            BigDecimal nominalValue = new BigDecimal((double)n[i]*m);
            if ( nominalValue.compareTo(biggestNominal)>0 )
                break;
            
            nominalValue = nominalValue.round(MathContext.DECIMAL32);
            CurrencyNominal nominal = new CurrencyNominal(nominalValue, currency.getDbResource()); 
            result.add(nominal);
            
            i++;
            if ( i>2 ){
                i = 0;
                m = m*10;
            }
        }
        return result;
    }

    @Override
    public List<CurrencyNominal> getNominals(Currency currency) {
        List<CurrencyNominal> nominals = (List<CurrencyNominal>) AcaciaUtils.getResultList(em, 
            CurrencyNominal.NQ_FIND_NOMINAL_VALUES, 
            "currency", currency.getDbResource() 
            );
        return nominals;
    }
}
