/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.invoice.InvoiceListLocal;
import com.cosmos.acacia.crm.bl.pricing.CustomerDiscountLocal;
import com.cosmos.acacia.crm.bl.pricing.PricelistLocal;
import com.cosmos.acacia.crm.bl.users.RightsManagerLocal;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Pricelist;
import com.cosmos.acacia.crm.data.PricelistItem;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.data.ProductSupplier;
import com.cosmos.acacia.crm.data.ProductSupplierPK;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.customer.CustomerDiscount;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItem;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.ProductColor;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.impl.ProductCategoryValidatorLocal;
import com.cosmos.acacia.crm.validation.impl.ProductValidatorLocal;
import com.cosmos.beansbinding.EntityProperties;

/**
 *
 * @author miro
 */
@Stateless
public class ProductsListBean implements ProductsListRemote, ProductsListLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    @EJB
    private ProductValidatorLocal productValidator;
    @EJB
    private ProductCategoryValidatorLocal productCategoryValidator;
    @EJB
    private PricelistLocal pricelistLocal;
    @EJB
    private CustomerDiscountLocal customerDiscountLocal;
    @EJB
    private InvoiceListLocal invoiceListLocal;
    @EJB
    private RightsManagerLocal rightsManagerLocal;
    @EJB
    private AcaciaSessionLocal session;

    @SuppressWarnings("unchecked")
    public List<SimpleProduct> getProducts(BigInteger parentId)
    {
        Query q;
        if(parentId != null)
        {
            q = em.createNamedQuery("SimpleProduct.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parentId);
        }
        else
        {
            q = em.createNamedQuery("SimpleProduct.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<SimpleProduct>(q.getResultList());
    }

    @SuppressWarnings("unchecked")
    public List<ProductCategory> getProductsCategories(DataObject parent)
    {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("ProductCategory.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parent.getDataObjectId());
        }
        else
        {
            q = em.createNamedQuery("ProductCategory.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<ProductCategory>(q.getResultList());
    }
    
    public EntityProperties getProductListingEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(SimpleProduct.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        entityProperties.removePropertyDetails("purchasePrice");
        entityProperties.removePropertyDetails("salePrice");
        entityProperties.removePropertyDetails("listPrice");
        entityProperties.removePropertyDetails("discountPercent");
        entityProperties.removePropertyDetails("dutyPercent");
        entityProperties.removePropertyDetails("transportPrice");
        entityProperties.removePropertyDetails("costPrice");
        entityProperties.removePropertyDetails("profitPercent");
        
        return entityProperties;
    }

    public EntityProperties getProductEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(SimpleProduct.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        
        return entityProperties;
    }

    @Override
    public SimpleProduct newProduct() {
        SimpleProduct product = new SimpleProduct();
        product.setParentId(session.getOrganization().getId());
        product.setMeasureUnit(MeasurementUnit.Piece.getDbResource());
        product.setListPrice(BigDecimal.valueOf(0));
        product.setCurrency(Currency.Leva.getDbResource());

        return product;
    }

    public SimpleProduct saveProduct(SimpleProduct product) {
         
        productValidator.validate(product); 
           
        esm.persist(em, product);
        
        pricelistLocal.updateGeneralPricelist(product.getParentId());
        
        return product; 
    }

    public int deleteProduct(SimpleProduct product) {
        return esm.remove(em, product);
    }

    public List<DbResource> getMeasureUnits() {
        return MeasurementUnit.getDbResources();
    }
    
    public List<DbResource> getMeasureUnits(MeasurementUnit.Category category) {
        return MeasurementUnit.getDbResourcesByCategory(category);
    }
    
    public List<DbResource> getProductColors() {
        return ProductColor.getDbResourcesByCategory(ProductColor.Category.DesktopComputer);
    }

    /**
     * @see com.cosmos.acacia.crm.bl.impl.ProductsListRemote#getProductCategoryEntityProperties()
     */
    @Override
    public EntityProperties getProductCategoryEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(ProductCategory.class);
        if ( !rightsManagerLocal.isAllowed(SpecialPermission.ProductPricing)){
            entityProperties.removePropertyDetails("discountPercent");
            entityProperties.removePropertyDetails("profitPercent");
        }
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public ProductCategory saveProductCategory(ProductCategory entity) {
        productCategoryValidator.validate(entity); 
        
        esm.persist(em, entity);
        return entity; 
    }

    @Override
    public ProductCategory refreshProductCategory(ProductCategory entity) {
        return em.find(ProductCategory.class, entity.getId());
    }

    @Override
    public ProductCategory newProductCategory(ProductCategory parentCategory) {
        ProductCategory newObject = new ProductCategory();
        newObject.setParentCategory(parentCategory);
        return newObject;
    }

    @Override
    public boolean deleteProductCategory(ProductCategory category) {
        esm.remove(em, category);
        return true;
    }

    /**
     * @see com.cosmos.acacia.crm.bl.impl.ProductsListRemote#updateParents(com.cosmos.acacia.crm.data.ProductCategory, com.cosmos.acacia.crm.data.ProductCategory)
     */
    @Override
    public ProductCategory updateParents(ProductCategory newParent, ProductCategory newChildren) {
        
        if ( newParent!=null ){
            ValidationException ve = new ValidationException();
            
            // check cyclic parents
            ProductCategory ancestor = newParent;
            while ( ancestor!=null ){
                if ( ancestor.equals(newChildren) ){
                    ve.addMessage("parentCategory", "ProductCategory.err.cyclicParent");
                    break;
                }
                ancestor = ancestor.getParentCategory();
            }
            
            // if we have validation messages - throw the exception since not everything is OK
            if ( !ve.getMessages().isEmpty() )
                throw ve;
            
            //merge the parent
            newParent = em.merge(newParent);
        }
        
        //newParent may be null here - but no problem
        newChildren.setParentCategory(newParent);
        esm.persist(em, newChildren);
        
        return newChildren;
    }

    @Override
    public boolean deleteProductCategories(List<ProductCategory> categories) {
        for (ProductCategory productCategory : categories) {
            esm.remove(em, productCategory);
        }
        return true;
    }

    @Override
    public SimpleProduct refresh(SimpleProduct product) {
        if ( product.getId()!=null )
            product = em.find(SimpleProduct.class, product.getId());
        return product;
    }

    @Override
    public List<SimpleProduct> getProductsForCategory(BigInteger categoryId, Boolean includeHeirs) {
        if ( includeHeirs==null && categoryId==null )
            throw new IllegalArgumentException("Null parameters not allowed ("+categoryId+", "+includeHeirs+")");
        Set<BigInteger> parentIds = new HashSet<BigInteger>();
        parentIds.add(categoryId);
        
        Set<BigInteger> categoriesAllIds = new HashSet<BigInteger>();
        categoriesAllIds.add(categoryId);
        //get all sub categories if includeHeirs
        if (includeHeirs){
            while ( true ){
                Query q = em.createNamedQuery("ProductCategory.findChildCategories");
                q.setParameter("parentIds", parentIds);
                List<ProductCategory> categories = q.getResultList();
                
                parentIds = new HashSet<BigInteger>();
                for (ProductCategory cat : categories) {
                    parentIds.add(cat.getId());
                    categoriesAllIds.add(cat.getId());
                }
                
                if ( categories.isEmpty() )
                    break;
            }
        }
        
        //get all products from these categories
        Query q = em.createNamedQuery("SimpleProduct.findByCategories");
        q.setParameter("categoryIds", categoriesAllIds);
        
        List<SimpleProduct> productsFromCategories = q.getResultList();
        return productsFromCategories;
    }
    
    public ProductPriceSummary getProductPriceSummary(BusinessPartner customer, SimpleProduct product) {
        return getProductPriceSummary(customer, null, product);
    }
    
    public ProductPriceSummary getProductPriceSummary(BusinessPartner customer, BigDecimal quantity,
                                        SimpleProduct product) {
        if ( customer==null )
            throw new IllegalArgumentException("Please supply not null 'customer' parameter!");
        if ( product==null )
            throw new IllegalArgumentException("Please supply not null 'product' parameter!");
        
        if ( quantity==null )
            quantity = new BigDecimal("1");
        
        //Find out the customer discount
        CustomerDiscount customerDiscount = customerDiscountLocal.getCustomerDiscount(customer);
        CustomerDiscountItem customerDiscountItem = null;
        if ( customerDiscount!=null ){
            customerDiscountItem = customerDiscountLocal.getCustomerDiscountItem(customerDiscount, product);
        }
        
        //Get the price-list items that qualify as valid for all requirements. 
        //Later we will choose the one that offers the biggest discount 
        List<PricelistItem> items = getValidPricelistItems(product, customer);
        if ( items==null || items.isEmpty() ){
            pricelistLocal.updateGeneralPricelist(session.getOrganization().getId());
            items = getValidPricelistItems(product, customer);
        }
        
        //Link every price-list item with its price-list
        Map<PricelistItem, Pricelist> itemsMap = new HashMap<PricelistItem, Pricelist>();
        for (PricelistItem pricelistItem : items) {
            Pricelist pricelist = itemsMap.get(pricelistItem);
            if ( pricelist==null ){
                pricelist = em.getReference(Pricelist.class, pricelistItem.getDataObject().getParentDataObjectId());
                itemsMap.put(pricelistItem, pricelist);
            }
        }
        
        ProductPriceSummary result = new ProductPriceSummary(product, customerDiscount, customerDiscountItem, itemsMap, quantity);
        return result;
    }

    @Override
    public ProductPrice getProductPrice(BusinessPartner customer, BigDecimal quantity,
                                        SimpleProduct product) {
        ProductPriceSummary productPriceSummary = getProductPriceSummary(customer, quantity, product);
        
        BigDecimal price = productPriceSummary.getPrice();
        ProductPrice result = new ProductPrice(product, productPriceSummary.getPriceList(), price, productPriceSummary.getPriceListItem());
        return result;
    }

    private List<PricelistItem> getPricelistItemsForProduct(SimpleProduct product) {
        Query q = em.createNamedQuery("PricelistItem.getPricelistItemsForProduct");
        q.setParameter("product", product); 
        q.setParameter("pricelistParentId", session.getOrganization().getId());
        return q.getResultList();
    }

    private BigDecimal getCustomerTurnover(BusinessPartner customer, Integer months) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, 0 - months);
        Date startDate = now.getTime();
        
        BigDecimal result = invoiceListLocal.getRecipientTurnover(customer, startDate);
        return result;
    }
    
    private List<PricelistItem> getValidPricelistItems(SimpleProduct product, BusinessPartner customer) {
        List<PricelistItem> items = getPricelistItemsForProduct(product);
        List<PricelistItem> result = new ArrayList<PricelistItem>();
        Date now = new Date();
        for (PricelistItem item : items) {
            Pricelist pricelist = em.getReference(Pricelist.class, item.getDataObject().getParentDataObjectId());
            //skip if not active, or not in period
            if ( pricelist.isActive() ){
                if ( pricelist.isForPeriod() ){
                    if ( pricelist.getActiveFrom()==null || !pricelist.getActiveFrom().after(now) ){
                        //ok
                    }else{
                        continue;
                    }
                    if ( pricelist.getActiveTo()==null || !pricelist.getActiveTo().before(now )){
                        //ok
                    }else{
                        continue;
                    }
                }else{
                    //ok
                }
            }
            
            //skip if doesn't qualify by turnover
            if ( pricelist.getMinTurnover()!=null ){
                BigDecimal customerTurnover = getCustomerTurnover(customer, pricelist.getMonths());
                if ( pricelist.getMinTurnover().compareTo(customerTurnover)>0 ){
                    continue;
                }
            }
            
            result.add(item);
        }
        
        return result;
    }
    
        @Override
    public EntityProperties getProductSupplierEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(ProductSupplier.class);
        return entityProperties;
    }

    @Override
    public ProductSupplier newProductSupplier(SimpleProduct product) {
        ProductSupplierPK pk = new ProductSupplierPK();
        pk.setProductId(product.getId());
        ProductSupplier productSupplier = new ProductSupplier(pk);
        productSupplier.setProduct(product);
        productSupplier.setDeliverable(true);
        productSupplier.setMeasureUnit(MeasurementUnit.Piece.getDbResource());
        productSupplier.setMinOrderQuantity(BigDecimal.ONE);
        productSupplier.setPricePerQuantity(1);
        return productSupplier;
    }

    @Override
    public ProductSupplier saveProductSupplier(ProductSupplier productSupplier) {
        esm.persist(em, productSupplier);
        return productSupplier;
    }

    @Override
    public List<ProductSupplier> getProductSuppliers(SimpleProduct product) {
        if(product == null || product.getId() == null) {
            return new ArrayList<ProductSupplier>();
        }

        Query q = em.createNamedQuery("ProductSupplier.findByProduct");
        q.setParameter("product", product);
        return new ArrayList<ProductSupplier>(q.getResultList());
    }

    @Override
    public boolean deleteProductSupplier(ProductSupplier productSupplier) {
        esm.remove(em, productSupplier);
        return true;
    }

    @Override
    public List<DbResource> getCurrencies() {
        return Currency.getDbResources();
    }
}