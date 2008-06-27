/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.invoice.impl;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rlozanov
 */
@Local
public interface InvoiceItemsListLocal {

    EntityProperties getInvoiceItemsEntityProperties();

    List<InvoiceItem> getInvoiceItems(Object parentDataObject);

    int deleteInvoiceItem(Object invoiceItem);

    List<DbResource> getMeasureUnits();

    InvoiceItem saveInvoiceItem(InvoiceItem item);

    InvoiceItem newInvoiceItem(BigInteger invoiceId);

}
