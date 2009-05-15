package com.cosmos.acacia.crm.bl.cash;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.BanknoteQuantity;
import com.cosmos.acacia.crm.data.CurrencyNominal;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.validation.impl.BanknoteQuantityValidatorLocal;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;

/**
 * 
 * Created	:	02.05.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class BanknoteQuantityBean implements BanknoteQuantityRemote, BanknoteQuantityLocal{
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    
    @EJB
    private BanknoteQuantityValidatorLocal validator;
    
    public EntityProperties getListingEntityProperties() {

        EntityProperties ep = esm.getEntityProperties(BanknoteQuantity.class);
        
        ep.addPropertyDetails(new PropertyDetails("currencyNominal.currency.enumValue.code", "Currency", String.class.getName(), 10));
        ep.getPropertyDetails("quantity").setOrderPosition(20);
        ep.addPropertyDetails(new PropertyDetails("currencyNominal.nominal", "Nominal", BigDecimal.class.getName(), 30));
        
        ep.removePropertyDetails("currencyNominal");
        
        return ep;
    }

    @SuppressWarnings("unchecked")
    public List<BanknoteQuantity> listBanknoteQuantitys(BigInteger parentDataObjectId) {
        if (parentDataObjectId == null)
            return new ArrayList<BanknoteQuantity>();
        
        Query q = em.createNamedQuery(BanknoteQuantity.NQ_BY_PARENT);
        q.setParameter("parentId", parentDataObjectId);

        List<BanknoteQuantity> result = q.getResultList();
        return result;
    }

    public void deleteBanknoteQuantity(BanknoteQuantity banknoteQuantity) {
        if (banknoteQuantity == null)
            throw new IllegalArgumentException("null: 'BanknoteQuantity'");
        esm.remove(em, banknoteQuantity);
    }

    public BanknoteQuantity newBanknoteQuantity(BigInteger parentId) {
        BanknoteQuantity c = new BanknoteQuantity();
        c.setParentId(parentId);
        c.setCurrencyNominal(new CurrencyNominal());
        return c;
    }
    
    public EntityProperties getDetailEntityProperties() {
        EntityProperties ep = esm.getEntityProperties(BanknoteQuantity.class);
        ep.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        
        ep.addPropertyDetails(new PropertyDetails("currencyNominal.currency", "Currency", DbResource.class.getName(), 10));
        ep.getPropertyDetails("quantity").setOrderPosition(20);
        ep.addPropertyDetails(new PropertyDetails("currencyNominal.nominal", "Nominal", BigDecimal.class.getName(), 30));
        
        return ep;
    }

    public BanknoteQuantity saveBanknoteQuantity(BanknoteQuantity entity) {
        validator.validate(entity);
        
        esm.persist(em, entity);

        return entity;
    }
}
