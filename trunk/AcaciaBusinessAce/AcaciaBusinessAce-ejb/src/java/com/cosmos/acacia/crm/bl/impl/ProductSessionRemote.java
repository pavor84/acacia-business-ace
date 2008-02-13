/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.Product;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface ProductSessionRemote {

    List<Product> getProducts();

    EntityProperties getProductEntityProperties();

}
