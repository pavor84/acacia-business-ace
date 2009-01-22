package com.cosmos.acacia.crm.bl.pricing;

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
import com.cosmos.acacia.crm.data.Pricelist;
import com.cosmos.acacia.crm.data.PricelistItem;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.validation.impl.PricelistValidatorLocal;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;

/**
 * 
 * Created	:	04.01.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class PricelistBean implements PricelistLocal, PricelistRemote {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    
    @EJB
    private PricelistValidatorLocal pricelistValidator;

    public EntityProperties getListingEntityProperties() {

        EntityProperties entityProperties = esm.getEntityProperties(Pricelist.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        entityProperties.removePropertyDetails("forPeriod");

        return entityProperties;

    }

    @SuppressWarnings("unchecked")
    public List<Pricelist> listPricelists(BigInteger parentDataObjectId) {
        if (parentDataObjectId == null)
            throw new IllegalArgumentException("parentDataObjectId can't be null");

        Query q = em.createNamedQuery("Pricelist.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);

        List<Pricelist> result = q.getResultList();
        return result;
    }

    public void deletePricelist(Pricelist pricelist) {
        if (pricelist == null)
            throw new IllegalArgumentException("null: 'Pricelist'");
        esm.remove(em, pricelist);
    }

    public Pricelist newPricelist(BigInteger parentDataObjectId) {
        Pricelist c = new Pricelist();
        c.setParentId(parentDataObjectId);
        c.setCurrency(Currency.Leva.getDbResource());
        c.setActive(true);
        return c;
    }

    public EntityProperties getDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(Pricelist.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    public Pricelist savePricelist(Pricelist p) {
        
        pricelistValidator.validate(p);
        
        esm.persist(em, p);

        return p;
    }

    @Override
    public void deletePricelistItem(PricelistItem item) {
        esm.remove(em, item);
    }

    public EntityProperties getItemsListEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(PricelistItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        
        PropertyDetails categoryColumn = new PropertyDetails("product.category.categoryName", "Category", String.class.getName());
        categoryColumn.setOrderPosition(12);
        
        entityProperties.addPropertyDetails(categoryColumn);
        
        return entityProperties;
    }

    @SuppressWarnings("unchecked")
    public List<PricelistItem> getPricelistItems(BigInteger parentDataObjectId) {
        if (parentDataObjectId == null)
            throw new IllegalArgumentException("parentDataObjectId can't be null");

        Query q = em.createNamedQuery("PricelistItem.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);

        List<PricelistItem> result = q.getResultList();
        return result;
    }

    public PricelistItem newPricelistItem(BigInteger parentDataObjectId) {
        PricelistItem item = new PricelistItem();
        Pricelist pricelist = em.find(Pricelist.class, parentDataObjectId);
        if (pricelist == null)
            throw new IllegalArgumentException("Invalid parent for id: " + parentDataObjectId);
        item.setParentId(parentDataObjectId);
        return item;
    }

    public PricelistItem savePricelistItem(PricelistItem item) {

        esm.persist(em, item);

        return item;
    }

    public EntityProperties getItemDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(PricelistItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    public Pricelist getPricelistById(BigInteger pricelistId) {
        Query q = em.createNamedQuery("Pricelist.findById");
        q.setParameter("pricelistId", pricelistId);
        List<Pricelist> result = q.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public PricelistItem getPricelistItemById(BigInteger pricelistItemId) {

        Query q = em.createNamedQuery("PricelistItem.findById");
        q.setParameter("pricelistItemId", pricelistItemId);
        List<PricelistItem> result = q.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public List<PricelistItem> savePricelistItems(List<PricelistItem> toSave) {
        List<PricelistItem> result = new ArrayList<PricelistItem>();
        for (PricelistItem pricelistItem : toSave) {
            PricelistItem saved = savePricelistItem(pricelistItem);
            result.add(saved);
        }
        return result;
    }
}
