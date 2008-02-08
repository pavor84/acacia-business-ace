/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.Product;
import java.util.List;
import javax.ejb.Remote;
import javax.swing.JTable;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Miro
 */
@Remote
public interface ProductSessionRemote {

    List<Product> getProducts();

    JTableBinding getProductTableBinding(JTable targetTable);
    
}
