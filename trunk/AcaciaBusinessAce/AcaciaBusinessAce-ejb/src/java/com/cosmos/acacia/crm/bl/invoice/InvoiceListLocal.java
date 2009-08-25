package com.cosmos.acacia.crm.bl.invoice;

import java.util.UUID;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.crm.data.sales.InvoiceItem;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.warehouse.WarehouseProduct;

/**
 * 
 * Created	:	10.07.2008
 * @author	Petar Milev
 *
 */
@Local
public interface InvoiceListLocal extends InvoiceListRemote{
	
	/**
	 * Get the Invoice by its ID 
	 * @param invoiceId
	 * @return
	 */
	Invoice getInvoiceById(UUID invoiceId);
	
	/**
	 * Get the InvoiceItem by its ID
	 * @param invoiceItemId
	 * @return
	 */
	InvoiceItem getInvoiceItemById(UUID invoiceItemId);
	
	/**
     * Get warehouse product for a given simple product and warehouse
     * @param product
     * @param address
     * @return
     */
    WarehouseProduct getWarehouseProduct(Address address, SimpleProduct product);
}
