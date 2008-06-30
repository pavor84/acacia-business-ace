package com.cosmos.acacia.crm.bl.invoice.impl;

import com.cosmos.acacia.crm.data.DataObjectLink;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.beansbinding.EntityProperties;

/**
 * EJB interface for invoices list dialog
 *
 * @author Radoslav Lozanov
 */
@Remote
public interface InvoicesListRemote {

    Invoice newInvoice();

    Invoice saveInvoice(Invoice person);

    int deleteInvoice(Invoice person);

    List<Invoice> getInvoices(DataObject parent);

    List<InvoiceItem> getInvoiceItems(DataObject parent);

    EntityProperties getInvoiceEntityProperties();

    List<DbResource> getCurrencies();

    List<DbResource> getDeliveryTypes();

    List<DbResource> getDocumentDeliveryMethods();

    List<DbResource> getInvoiceStatuses();

    List<DbResource> getInvoiceTypes();

    List<DbResource> getPaymentTerms();

    List<DbResource> getPaymentTypes();

    List<DbResource> getTransportationMethods();

    List<DbResource> getVatConditions();

    DataObjectLink newDataObjectLink(DataObjectBean linkeObject);

    BigDecimal calculateVatValue(Invoice invocie);
}
