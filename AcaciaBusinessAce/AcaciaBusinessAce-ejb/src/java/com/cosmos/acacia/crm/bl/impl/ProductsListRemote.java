/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author miro
 */
@Remote
public interface ProductsListRemote {

    List<SimpleProduct> getProducts(DataObject parent);
    List<ProductCategory> getProductsCategories(DataObject parent);

    EntityProperties getProductEntityProperties();

    SimpleProduct newProduct();

    SimpleProduct saveProduct(SimpleProduct product);

    int deleteProduct(SimpleProduct product);

    List<DbResource> getMeasureUnits();
    List<DbResource> getMeasureUnits(MeasurementUnit.Category category);
    
    /**
     * The method should return entities that are qualified as producers.
     * {@link Person}
     * (May be of different type - for ex. {@link Person} or {@link Organization}) 
     * @return
     */
    List<BusinessPartner> getProducers();
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
}
