/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Product;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author miro
 */
@Remote
public interface ProductsListRemote {

    List<Product> getProducts(DataObjectBean parent);
    
}
