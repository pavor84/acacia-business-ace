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
    
}
