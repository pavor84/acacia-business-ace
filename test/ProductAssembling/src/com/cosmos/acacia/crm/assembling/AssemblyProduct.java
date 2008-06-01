/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.ComplexProductItem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miro
 */
public class AssemblyProduct
    extends ComplexProduct
{
    private List<ComplexProductItem> productItems;

    public AssemblyProduct()
    {
    }

    public List<ComplexProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<ComplexProductItem> productItems) {
        this.productItems = productItems;
    }

    public boolean addProductItem(ComplexProductItem productItem)
    {
        if(productItems == null)
            productItems = new ArrayList<ComplexProductItem>();

        return productItems.add(productItem);
    }
}
