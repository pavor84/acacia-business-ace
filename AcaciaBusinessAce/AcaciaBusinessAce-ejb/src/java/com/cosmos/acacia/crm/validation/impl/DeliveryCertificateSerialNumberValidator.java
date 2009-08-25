package com.cosmos.acacia.crm.validation.impl;

import java.util.UUID;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesLocal;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.data.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber;
import com.cosmos.acacia.crm.enums.DeliveryCertificateStatus;
import com.cosmos.acacia.crm.validation.ValidationException;

@Stateless
public class DeliveryCertificateSerialNumberValidator implements DeliveryCertificateSerialNumberValidatorLocal{
	
	@EJB
	private DeliveryCertificatesLocal certificatesBean;
	
	@Override
	public void validate(DeliveryCertificateSerialNumber entity)
			throws ValidationException {
		
		ValidationException ve = new ValidationException();
		
		DeliveryCertificateItem item = entity.getDeliveryCertificateItem();
		List<DeliveryCertificateSerialNumber> alreadyAssignedSerialNumbers = certificatesBean.getDeliveryCertificateItemSerialNumbers(item.getCertificateItemId());
		if(alreadyAssignedSerialNumbers.contains(entity)){
			ve.addMessage("Serial Number", "serialNumber.alreadyAssigned");
			throw ve;
		}
		
		//check if some other delivery certificate protocol has delivered the product with the same serial number. 
		//we cannot two products with same serial numbers twice
		UUID deliveryCertificateId = item.getParentId();
		DeliveryCertificate certificate = certificatesBean.getDeliveryCertificateById(deliveryCertificateId);
		if(DeliveryCertificateStatus.Delivered.equals(certificate.getStatus().getEnumValue())){
			ve.addMessage("Serial Number", "serialNumber.productAlreadyDelivered");
			throw ve;
		}
		
	}

}
