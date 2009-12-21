package com.cosmos.acacia.crm.validation.impl;

import java.util.List;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.warehouse.DeliveryCertificate;
import com.cosmos.acacia.crm.data.warehouse.DeliveryCertificateAssignment;
import com.cosmos.acacia.crm.data.warehouse.DeliveryCertificateItem;
import com.cosmos.acacia.crm.validation.EntityValidator;
import com.cosmos.acacia.crm.validation.ValidationException;

@Local
public interface DeliveryCertificateValidatorLocal extends EntityValidator<DeliveryCertificate> {
	void validate(DeliveryCertificate dc, DeliveryCertificateAssignment assignment, List<DeliveryCertificateItem> items) throws ValidationException;
}
