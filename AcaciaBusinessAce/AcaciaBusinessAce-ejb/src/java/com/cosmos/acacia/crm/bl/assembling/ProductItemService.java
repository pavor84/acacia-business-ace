/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.assembling.ProductAssemblerService;
import com.cosmos.acacia.crm.data.product.ComplexProduct;
import com.cosmos.acacia.crm.data.product.ComplexProductItem;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import java.util.List;

/**
 *
 * @author Miro
 */
public interface ProductItemService
    extends ProductAssemblerService
{
    List<ComplexProductItem> getComplexProductItems(ComplexProduct complexProduct);
    int getComplexProductItemsCount(ComplexProduct complexProduct);
    List<InvoiceItem> getInvoiceItems(Invoice invoice);
    int getInvoiceItemsCount(Invoice invoice);
}
