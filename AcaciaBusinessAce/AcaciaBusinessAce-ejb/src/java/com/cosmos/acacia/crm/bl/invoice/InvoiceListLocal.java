package com.cosmos.acacia.crm.bl.invoice;

import java.math.BigInteger;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.WarehouseProduct;

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
	Invoice getInvoiceById(BigInteger invoiceId);
	
	/**
	 * Get the InvoiceItem by its ID
	 * @param invoiceItemId
	 * @return
	 */
	InvoiceItem getInvoiceItemById(BigInteger invoiceItemId);
	
	/**
     * Get warehouse product for a given simple product and warehouse
     * @param product
     * @param address
     * @return
     */
    WarehouseProduct getWarehouseProduct(Address address, SimpleProduct product);
}
