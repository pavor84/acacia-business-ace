/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.assembling.ProductAssemblerService;
import com.cosmos.acacia.crm.data.product.ComplexProduct;
import com.cosmos.acacia.crm.data.product.ComplexProductItem;
import com.cosmos.acacia.crm.data.sales.SalesInvoice;
import com.cosmos.acacia.crm.data.sales.SalesInvoiceItem;
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
    List<SalesInvoiceItem> getInvoiceItems(SalesInvoice invoice);
    int getInvoiceItemsCount(SalesInvoice invoice);
}
