package com.cosmos.acacia.crm.bl.invoice;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.crm.data.sales.InvoiceItem;
import com.cosmos.acacia.crm.data.sales.InvoiceItemLink;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import com.cosmos.acacia.crm.data.warehouse.WarehouseProduct;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	10.07.2008
 * @author	Petar Milev
 *
 */
@Remote
public interface InvoiceListRemote {

    /**
     * Create entity properties object for listing object from this type.
     * @return not null
     */
    EntityProperties getListingEntityProperties();

    /**
     * Return all invoices for a given parent.
     * @param parentDataObjectId - mandatory
     * @param proform - whether to fetch the invoices or proforma invoices
     * @return not null list
     */
    List<Invoice> listInvoices(UUID parentDataObjectId, Boolean proform);

    /**
     * Deletes the invoice, - if the integrity is violated, throws an {@link ValidationException} 
     * @param Invoice - not null
     */
    void deleteInvoice(Invoice Invoice);

    /**
     * Create new instance of {@link Invoice}.
     * 
     * @param parentDataObjectId - may be null
     * @return not null
     */
    Invoice newInvoice(UUID parentDataObjectId);

    /**
     * Return entity properties for detailed view
     * @return not null
     */
    EntityProperties getDetailEntityProperties();

    /**
     * Persists the current purchase invoice, fails with {@link ValidationException} if something is
     * not right
     * @param po
     */
    Invoice saveInvoice(Invoice po);

    /**
     * Return the entity properties for listing items.
     * @return
     */
    EntityProperties getItemsListEntityProperties();

    /**
     * List invoice items for a given invoice
     * @param parentDataObjectId
     * @return
     */
    List<InvoiceItem> getInvoiceItems(UUID parentDataObjectId);

    /**
     * Delete invoice item.
     * If the item is copied from other document ( {@link #isTemplateItem(InvoiceItem)} ),
     * then all items copied from the same document are removed and returned. Otherwise, just
     * this item is removed and returned one element in the list.
     * 
     * @param item
     */
    List<InvoiceItem> deleteInvoiceItem(InvoiceItem item);

    /**
     * Create new invoice item for a given invoice
     * @param parentDataObjectId
     * @return
     */
    InvoiceItem newInvoiceItem(UUID parentDataObjectId);

    /**
     * Save an item
     * @param entity
     * @return
     */
    InvoiceItem saveInvoiceItem(InvoiceItem entity);

    /**
     * Item details entity properties
     * @return
     */
    EntityProperties getItemDetailEntityProperties();

    /**
     * Document types possibilites
     * @return
     */
    List<DbResource> getDocumentTypes();

    /**
     * Enum currencies
     * @return
     */
    List<DbResource> getCurrencies();

    /**
     * Get the contacts of a given recipient/client
     * @param recipient
     * @return
     */
    List<ContactPerson> getRecipientContacts(BusinessPartner recipient);

    /**
     * Retrieve delivery method values
     * @return
     */
    List<DbResource> getDeliveryMethods();

    /**
     * Retrieve transportation method values
     * @return
     */
    List<DbResource> getTransportationMethods();

    /**
     * Retrieve delivery types values
     * @return
     */
    List<DbResource> getDeliveryTypes();

    /**
     * Retrieve vat condition types
     * @return
     */
    List<DbResource> getVatConditions();

    /**
     * Retrieve payment terms values
     * @return
     */
    List<DbResource> getPaymentTerms();

    /**
     * Retrieve payment types values
     * @return
     */
    List<DbResource> getPaymentTypes();

    /**
     * Retrieve statuses values
     * @return
     */
    List<DbResource> getInvoiceStatuses();

    /**
     * Make an invoice in published state {@link InvoiceStatus#Published} to be in sent state {@link InvoiceStatus#Sent}.
     * @param entity
     * @param sent
     * @return
     */
    Invoice sendInvoice(Invoice entity);

    /**
     * Save invoice items
     * @param newItems
     */
    void saveInvoiceItems(List<InvoiceItem> newItems);

    /**
     * Get warehouse product for a given simple product
     * @param product
     * @return
     */
    WarehouseProduct getWarehouseProduct(SimpleProduct product);

//    /**
//     * Returns list which consist of documents used as template for new invoices. 
//     * Can contain different types of documents (for ex. Sales Offers, Invoices, etc) 
//     * @param includeProformas 
//     * @param includeInvoices 
//     * @param onlyPending - only not finalized documents
//     * @return
//     */
//    List<?> getTemplateDocuments(Boolean onlyPending, Boolean includeInvoices, Boolean includeProformas);

    /**
     * Returns the items present in this template document.
     * @param document
     * @return
     */
    List<?> getDocumentItems(DataObjectBean document);

    /**
     * Saves the item links and also the actual items referenced by the link.
     * (The invoice item links are used to remember the documents items used as templates for
     * a given invoice item). Marks all documents in the links as 'usedAsTemplate'.
     * @param itemLinks
     */
    void addInvoiceItems(List<InvoiceItemLink> itemLinks);

    /**
     * Get invoice items links for a given item
     * @param invoiceItem
     * @return
     */
    List<InvoiceItemLink> getInvoiceItemLinks(InvoiceItem invoiceItem);
    
    /**
     * Perform user operation 'confirm' on the document. The document becomes {@value InvoiceStatus#WaitForPayment}
     * The method is smart and does the necessary depending the type and status of the document.
     * Throws {@link IllegalStateException} if the invoice can not be confirmed.
     * @param invoice
     * @return
     */
    Invoice confirm(Invoice invoice);
    
    /**
     * Perform user operation 'reopen' on the document. The document becomes {@value InvoiceStatus#Reopen}
     * The method is smart and does the necessary depending the type and status of the document.
     * Throws {@link IllegalStateException} if the invoice can not be confirmed.
     * @param invoice
     * @return
     */
    Invoice reopen(Invoice invoice);
    
    /**
     * Perform user operation 'cancel' on the document. The document becomes {@value InvoiceStatus#Cancelled}
     * The method is smart and does the necessary depending the type and status of the document.
     * Throws {@link IllegalStateException} if the invoice can not be confirmed.
     * @param invoice
     * @return
     */
    Invoice cancel(Invoice invoice);

    /**
     * Get the possible template documents for the given document.
     * @param forDocument
     * @param includeProformas - this is irrelevant if the document is not invoice
     * @param includeInvoices - this is irrelevant if the document is not invoice
     * @return
     */
    List<?> getTemplateDocuments(Invoice forDocument, Boolean includeInvoices, Boolean includeProformas);

    /**
     * @param item
     * @return - true if the item is copied from other document
     */
    Boolean isTemplateItem(InvoiceItem item);

    /**
     * Get the documents that are still not paid for the given recipient (customer).
     * @param recipient
     * @return
     */
    List<Invoice> getDueDocuments(BusinessPartner recipient);
    
    /**
     * Get the documents that are still not paid for the current organization.
     * @param recipient
     * @return
     */
    List<Invoice> getDueDocuments();

    /**
     * Returns the invoices that can be cancelled by a credit note
     * @param invoice - should be of type credit note
     * @return
     */
    List<?> getInvoicesToCancel(Invoice invoice);
    
    /**
     * Retrieve the warehouse of the invoice
     * @param invoiceId
     * @return
     */
    Warehouse getInvoiceWarehouse(UUID invoiceId);
    
    /**
     * Get the turnover for a given recipient after a given date (which may be null)
     * @param recipient
     * @param startDate
     * @return
     */
    BigDecimal getRecipientTurnover(BusinessPartner recipient, Date startDate);
    
    /**
     * All documents that are actually confirmed 
     * ( {@link InvoiceStatus#WaitForPayment} or
     * {@link InvoiceStatus#Paid} )
     * @return
     */
    List<Invoice> getConfirmedDocuments();

    /**
     * Get all pending invoices (the ones that are not fully matched)
     * @param includedPartlyPaid - get the invoices that have partial matching
     * @param includeUnpaid - get the payments that have no match amounts
     * @param recipient
     * @return
     */
    List<Invoice> getPendingInvoices(BusinessPartner recipient, Boolean includedPartlyPaid,
                                     Boolean includeUnpaid);
}
