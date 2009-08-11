/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.product.ProductCategory;
import com.cosmos.acacia.crm.data.product.ProductSupplier;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 *
 * @author miro
 */
@Remote
public interface ProductsListRemote {
    
    List<SimpleProduct> getProducts(BigInteger parentId);
    
    List<ProductCategory> getProductsCategories(DataObject parent);

    EntityProperties getProductEntityProperties();
    
    EntityProperties getProductListingEntityProperties();

    SimpleProduct newProduct();

    SimpleProduct saveProduct(SimpleProduct product);

    int deleteProduct(SimpleProduct product);

    List<DbResource> getMeasureUnits();
    List<DbResource> getMeasureUnits(MeasurementUnit.Category category);
    
    /**
     * Retrieve the possible colors to select from when assigning one
     * for a product. 
     * @return not-null list
     */
    List<DbResource> getProductColors();
    
    /**
     * Entity properties for categories
     * @return
     */
    EntityProperties getProductCategoryEntityProperties();
    
    /**
     * Save a product category instance.
     * @param entity
     * @return
     */
    ProductCategory saveProductCategory(ProductCategory entity);
    
    ProductCategory refreshProductCategory(ProductCategory entity);

    /**
     * Create new instance for this object
     * @param object
     * @return
     */
    ProductCategory newProductCategory(ProductCategory parentCategory);
    
    /**
     * Removes the category.
     * @param rowObject
     * @return - true on success, false otherwise
     */
    boolean deleteProductCategory(ProductCategory category);
    
    /**
     * Set the first category as a parent to the second.
     * First checks if the first category is a child of the second (which
     * will lead to parent-child cycle). In this case throws {@link ValidationException}.
     * On success returns the updated new child object.
     * @param newParent
     * @param newChildren 
     * @return - {@link ProductCategory} - the updated new children category
     */
    ProductCategory updateParents(ProductCategory newParent, ProductCategory newChildren);
    
    /**
     * Deletes all categories from the list in order.
     * @param withSubCategories
     * @return true on success, false if the deletion fails
     */
    boolean deleteProductCategories(List<ProductCategory> categories);

    SimpleProduct refresh(SimpleProduct product);

    /**
     * Get the products in the selected category. If 'includeHiers' - the products in the sub categories
     * will be also shown.
     * @param categoryId
     * @param includeHeirs
     * @return
     */
    List<SimpleProduct> getProductsForCategory(BigInteger categoryId, Boolean includeHeirs);
    
    /**
     * Get the product price based on all available discounts and price-lists.
     * @param client
     * @param quantity
     * @param product
     * @return
     */
    ProductPrice getProductPrice(BusinessPartner customer, BigDecimal quantity, SimpleProduct product);
    
    ProductPriceSummary getProductPriceSummary(BusinessPartner customer, SimpleProduct product);
    
    ProductPriceSummary getProductPriceSummary(BusinessPartner customer, BigDecimal quantity,
                                        SimpleProduct product);
                                        
	EntityProperties getProductSupplierEntityProperties();
    ProductSupplier newProductSupplier(SimpleProduct product);
    ProductSupplier saveProductSupplier(ProductSupplier productSupplier);
    List<ProductSupplier> getProductSuppliers(SimpleProduct product);
    boolean deleteProductSupplier(ProductSupplier productSupplier);

    List<DbResource> getCurrencies();
}
