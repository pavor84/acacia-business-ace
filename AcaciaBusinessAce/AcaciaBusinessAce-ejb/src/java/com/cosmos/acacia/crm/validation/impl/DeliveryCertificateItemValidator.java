package com.cosmos.acacia.crm.validation.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListLocal;
import com.cosmos.acacia.crm.data.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.sales.InvoiceItem;
import com.cosmos.acacia.crm.validation.ValidationException;

@Stateless
public class DeliveryCertificateItemValidator implements DeliveryCertificateItemValidatorLocal{
	
	@EJB
	private InvoiceListLocal invoices;
	
	@Override
	public void validate(DeliveryCertificateItem entity)
			throws ValidationException {
		
		ValidationException ve = new ValidationException();
		
		BigInteger invoiceItemId = entity.getReferenceItemId();
		InvoiceItem invoiceItem = invoices.getInvoiceItemById(invoiceItemId);
		
		BigDecimal dueQuantity = (invoiceItem.getDueQuantity() != null) ? invoiceItem.getDueQuantity() : BigDecimal.ZERO;
		BigDecimal shipedQuantity = (invoiceItem.getShippedQuantity() != null) ? invoiceItem.getShippedQuantity() : BigDecimal.ZERO;
		
		//free = ordered - (due + shipped)
		BigDecimal freeQuantity = invoiceItem.getOrderedQuantity().subtract(dueQuantity.add(shipedQuantity));
		
		if(freeQuantity.compareTo(entity.getQuantity()) < 0){
			String messageKey = (freeQuantity.compareTo(invoiceItem.getOrderedQuantity()) == 0)? "maxQuantityExceed" : "quantityUnsufficient";
			ve.addMessage("Quantity", messageKey, freeQuantity);
			throw ve;
		}
	}

}
