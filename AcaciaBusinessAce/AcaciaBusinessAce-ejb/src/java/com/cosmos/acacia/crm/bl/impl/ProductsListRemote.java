/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Product;
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

    EntityProperties getProductEntityProperties();

    Product newProduct();

    Product saveProduct(Product product);

    int deleteProduct(Product product);
    
}
