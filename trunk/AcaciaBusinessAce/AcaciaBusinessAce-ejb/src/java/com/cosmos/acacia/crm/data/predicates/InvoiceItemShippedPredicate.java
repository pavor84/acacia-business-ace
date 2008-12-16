package com.cosmos.acacia.crm.data.predicates;

import java.math.BigDecimal;
import org.apache.commons.collections.Predicate;
import com.cosmos.acacia.crm.data.InvoiceItem;

/**
 * Evaluate to true if all Ordered Quantities for an Invoice Item are shipped (Ordered == Shipped)
 * @author daniel
 *
 */
public class InvoiceItemShippedPredicate implements Predicate {
	
	@Override
	public boolean evaluate(Object item) {
		if(item instanceof InvoiceItem){
			InvoiceItem invoiceItem = (InvoiceItem)item;
			BigDecimal shipedQuantity = (invoiceItem.getShippedQuantity() != null) ? invoiceItem.getShippedQuantity() : BigDecimal.ZERO;
			
			if(invoiceItem.getOrderedQuantity().compareTo(shipedQuantity) == 0){
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
