/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.Product;
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

    List<Product> getProducts(DataObject parent);
    List<ProductCategory> getProductsCategories(DataObject parent);

    EntityProperties getProductEntityProperties();

    Product newProduct();

    Product saveProduct(Product product);

    int deleteProduct(Product product);

    List<DbResource> getMeasureUnits();
    List<DbResource> getMeasureUnits(MeasurementUnit.Category category);
    
    /**
     * The method should return entities that are qualified as producers.
     * {@link Person}
     * (May be of different type - for ex. {@link Person} or {@link Organization}) 
     * @return
     */
    List<?> getProducers();
    
}
