package com.cosmos.acacia.crm.data.predicates;

import java.math.BigDecimal;

import org.apache.commons.collections.Predicate;

import com.cosmos.acacia.crm.data.sales.SalesInvoiceItem;

/**
 * Evaluate to true if all available (Ordered-Shipped) Quantities are Due Quantities
 * @author daniel
 *
 */
public class InvoiceItemDuePredicate implements Predicate {
	
	@Override
	public boolean evaluate(Object item) {
		if(item instanceof SalesInvoiceItem){
			SalesInvoiceItem invoiceItem = (SalesInvoiceItem)item;
			BigDecimal dueQuantity = (invoiceItem.getDueQuantity() != null) ? invoiceItem.getDueQuantity() : BigDecimal.ZERO;
			BigDecimal shipedQuantity = (invoiceItem.getShippedQuantity() != null) ? invoiceItem.getShippedQuantity() : BigDecimal.ZERO;
			if( (invoiceItem.getOrderedQuantity().subtract(shipedQuantity) ).compareTo(dueQuantity) == 0){
				return true;
			}
			else{
				return false;
			}
		}else{
			throw new IllegalArgumentException("The type: " + item.getClass().getName() + " is not supported");
		}
	}
}
