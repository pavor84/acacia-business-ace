package com.cosmos.acacia.crm.bl.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.users.RightsManagerLocal;
import com.cosmos.acacia.crm.data.Pricelist;
import com.cosmos.acacia.crm.data.PricelistItem;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
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
    @EJB
    private RightsManagerLocal rightsManagerLocal;

    public EntityProperties getListingEntityProperties() {

        EntityProperties entityProperties = esm.getEntityProperties(Pricelist.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        entityProperties.removePropertyDetails("forPeriod");
        entityProperties.removePropertyDetails("generalPricelist");
        
        return entityProperties;
    }

    @SuppressWarnings("unchecked")
    public List<Pricelist> listPricelists(BigInteger parentDataObjectId) {
        if (parentDataObjectId == null)
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        updateGeneralPricelist(parentDataObjectId);

        Query q = em.createNamedQuery("Pricelist.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);

        List<Pricelist> result = q.getResultList();
        return result;
    }

    /**
     * Create the general price-list if not created.
     * Create entry for every product if some products are not included.
     * @param parentDataObjectId - the parent organization
     */
    public void updateGeneralPricelist(BigInteger parentDataObjectId) {
        Pricelist pricelist = checkGeneralPricelist(parentDataObjectId);
        List<SimpleProduct> notIncluded = getMissingGeneralPricelistProducts(pricelist);
        createGeneralPricelistItems(pricelist, notIncluded);
        em.flush();//make sure the general price-list is updated successfully before continue
    }

    private void createGeneralPricelistItems(Pricelist pricelist, List<SimpleProduct> notIncluded) {
        for (SimpleProduct simpleProduct : notIncluded) {
            PricelistItem item = newPricelistItem(pricelist.getId());
            item.setProduct(simpleProduct);
            savePricelistItem(item);
        }
    }

    private List<SimpleProduct> getMissingGeneralPricelistProducts(Pricelist pricelist) {
        Query q = em.createNamedQuery("SimpleProduct.findNotIncludedProductsInPricelist");
        q.setParameter("pricelistId", pricelist.getId());
        
        List<SimpleProduct> result = q.getResultList();
        return result;
    }

    /**
     * Find or create the general pricelist.
     * @param parentDataObjectId 
     * @return
     */
    private Pricelist checkGeneralPricelist(BigInteger parentDataObjectId) {
        Query q = em.createNamedQuery("Pricelist.findGeneralPricelistForParent");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        
        Pricelist genPricelist = null;
        List<Pricelist> result = q.getResultList();
        if ( result.isEmpty() ){
            genPricelist = newPricelist(parentDataObjectId);
            genPricelist.setGeneralPricelist(true);
            genPricelist.setName("General Pricelist");
            while ( true ){
                genPricelist.setName(genPricelist.getName()+new Random().nextInt(1000));
                try{
                    genPricelist = savePricelist(genPricelist);
                    break;//if saved - leave
                }catch (ValidationException ve){
                    //if the name is reserved - continue with new one
                    if ( ve.getMessages()!=null && ve.getMessages().size()==1 ){
                        ValidationMessage msg = ve.getMessages().get(0);
                        if ( "name".equals(msg.getTarget()) )
                                continue;
                    }
                    //if the validation error is other - dump it and leave
                    ve.printStackTrace();
                    break;
                }
            }
        }else{
            genPricelist = result.get(0);
        }
        return genPricelist;
        
    }

    public void deletePricelist(Pricelist pricelist) {
        if (pricelist == null)
            throw new IllegalArgumentException("null: 'Pricelist'");
        List<PricelistItem> items = getPricelistItems(pricelist.getId());
        for (PricelistItem pricelistItem : items) {
            esm.remove(em, pricelistItem);
        }
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
        
        if ( rightsManagerLocal.isAllowed(SpecialPermission.ProductPricing) ){
            PropertyDetails pd = new PropertyDetails("sourcePrice", "Source Price", BigDecimal.class.getName());
            pd.setReadOnly(true);
            pd.setEditable(false);
            pd.setOrderPosition(35);
            entityProperties.addPropertyDetails(pd);
        }else{
            entityProperties.removePropertyDetails("discountPercent");
        }
        
        PropertyDetails pd = new PropertyDetails("salesPrice", "Sales Price", BigDecimal.class.getName() );
        pd.setReadOnly(true);
        pd.setEditable(false);
        pd.setOrderPosition(45);
        entityProperties.addPropertyDetails(pd);
        
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
