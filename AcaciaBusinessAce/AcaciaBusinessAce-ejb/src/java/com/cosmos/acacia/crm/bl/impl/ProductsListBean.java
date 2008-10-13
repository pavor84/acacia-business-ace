/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

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

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.ProductColor;
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

    public EntityProperties getProductEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(SimpleProduct.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        // TODO: Check which columns to be shown, visible, editable, etc.
        // depending of User Roles, Current Object, etc.
        /*for(Object key : entityProperties.getKeys().toArray())
        {
            if(!("productName".equals(key) || "productId".equals(key)))
            {
                PropertyDetails property = entityProperties.getPropertyDetails((String)key);
                boolean isBoolean = boolean.class.getName().equals(property.getPropertyClassName());
                if(!isBoolean)
                    isBoolean = Boolean.class.getName().equals(property.getPropertyClassName());
                isBoolean = false;
                if(isBoolean || counter++ >= stop)
                    entityProperties.removePropertyDetails((String)key);
                else
                    System.out.println("Property Name: " + key);
            }
        }*/

        return entityProperties;
    }

    public SimpleProduct newProduct(BigInteger parentId) {
        SimpleProduct product = new SimpleProduct();
        product.setParentId(parentId);
        product.setMeasureUnit(MeasurementUnit.Piece.getDbResource());
        product.setPurchasePrice(BigDecimal.valueOf(100.00));
        product.setSalePrice(BigDecimal.valueOf(100.00));
        product.setListPrice(BigDecimal.valueOf(100.00));
        
        return product;
    }

    public SimpleProduct saveProduct(SimpleProduct product) {
         
        productValidator.validate(product); 
           
        esm.persist(em, product);
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
}
