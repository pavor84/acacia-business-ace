package com.cosmos.acacia.crm.validation.impl;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.collections.CollectionUtils;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListLocal;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.data.DeliveryCertificateAssignment;
import com.cosmos.acacia.crm.data.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.crm.data.sales.InvoiceItem;
import com.cosmos.acacia.crm.data.predicates.InvoiceItemDuePredicate;
import com.cosmos.acacia.crm.data.predicates.InvoiceItemShippedPredicate;
import com.cosmos.acacia.crm.enums.DeliveryStatus;
import com.cosmos.acacia.crm.validation.ValidationException;

@Stateless
public class DeliveryCertificateValidator implements DeliveryCertificateValidatorLocal{
	
	@EJB
	private InvoiceListLocal invoicesBean;
	
	@Override
	public void validate(DeliveryCertificate entity)
			throws ValidationException {
		
	}
	
	@SuppressWarnings("unchecked")
	public void validate(DeliveryCertificate entity, DeliveryCertificateAssignment assignment, List<DeliveryCertificateItem> items) throws ValidationException {
		
		ValidationException ve = new ValidationException();
		UUID assignmentId = assignment.getDocumentId(); 
    	Invoice invoice = invoicesBean.getInvoiceById(assignmentId);
    	
    	if(DeliveryStatus.Delivered.equals(invoice.getDeliveryStatus().getEnumValue()))
    	{
    		ve.addMessage("DeliveryCertificateItems", "deliver.invoiceAlreadyDelivered");
    		throw ve;
    	}
    	
    	List<InvoiceItem> invoiceItems = invoicesBean.getInvoiceItems(invoice.getInvoiceId());
    	
    	//select InvoiceItems which all available quantities are due quantities
    	Collection<InvoiceItem> dueInvoiceItems = CollectionUtils.select(invoiceItems, new InvoiceItemDuePredicate());
    	if(dueInvoiceItems.size() == invoiceItems.size()){
    		ve.addMessage("DeliveryCertificateItems", "deliver.invoiceItemsAllDue");
    		throw ve;
    	}
    	
    	Collection<InvoiceItem> alreadyInvoiceItems = CollectionUtils.select(invoiceItems, new InvoiceItemShippedPredicate());
    	//double check for delivery of the Invoice
    	if(alreadyInvoiceItems.size() == invoiceItems.size()){
    		ve.addMessage("DeliveryCertificateItems", "deliver.invoiceAlreadyDelivered");
    		throw ve;
    	}
    	
    	//check if some of the positions are already delivered - if so, ask user to refresh the certificate
    	for(DeliveryCertificateItem deliveryCertItem : items){
    		for(InvoiceItem invoiceItem : alreadyInvoiceItems){
    			if(deliveryCertItem.getProduct().getProductId().equals(invoiceItem.getProduct().getId()) && deliveryCertItem.getQuantity().compareTo(BigDecimal.ZERO) > 0){
    				ve.addMessage("InvoiceItems", "deliver.some Items ");
    	    		throw ve;
    			}
    		}
    	}
    	
    	for(DeliveryCertificateItem dcItem : items){
			InvoiceItem iItem = invoicesBean.getInvoiceItemById(dcItem.getReferenceItemId());
			
			//TODO: Add default values for the columns in the database, in order to avoid NULL checking
			BigDecimal dueQuantity = (iItem.getDueQuantity() != null) ? iItem.getDueQuantity() : BigDecimal.ZERO;
			BigDecimal shipedQuantity = (iItem.getShippedQuantity() != null) ? iItem.getShippedQuantity() : BigDecimal.ZERO;
			//free = ordered - (due + shipped)
			BigDecimal freeQuantity = iItem.getOrderedQuantity().subtract(dueQuantity.add(shipedQuantity));
			
			if(freeQuantity.compareTo(dcItem.getQuantity()) < 0){
				ve.addMessage("Quantities", "deliver.unsufficientQuantities");
				throw ve;
			}
		}
	}

}
