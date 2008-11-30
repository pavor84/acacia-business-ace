package com.cosmos.acacia.crm.data.predicates;

import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;

import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.enums.DeliveryStatus;
import com.cosmos.acacia.crm.enums.InvoiceStatus;

public class ValidDeliveryCertificateAssignmentPredicate implements Predicate{

	protected static Logger log = Logger.getLogger(ValidDeliveryCertificateAssignmentPredicate.class);
	
	@Override
	public boolean evaluate(Object assignment) {
		if(assignment instanceof Invoice){
			Invoice i = (Invoice)assignment;
			Enum<DeliveryStatus> deliveryStatus = i.getDeliveryStatus().getEnumValue();
			Enum<InvoiceStatus> invoiceStatus = i.getStatus().getEnumValue();
			if( !DeliveryStatus.Delivered.equals(deliveryStatus) && 
				(InvoiceStatus.Paid.equals(invoiceStatus) || InvoiceStatus.WaitForPayment.equals(invoiceStatus))){
				
				return true;
			}
		}else{
			log.warn("The object of type " + assignment.getClass().getName() + " is not recognized as a valid delivery certificate assignmnet!");
		}
		
		return false;
	}

}
