package com.cosmos.acacia.crm.validation.impl;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.purchase.PurchaseOrderItem;
import com.cosmos.acacia.crm.validation.EntityValidator;

/**
 * 
 * Created	:	19.07.2008
 * @author	Petar Milev
 *
 */
@Local
public interface PurchaseOrderItemValidatorLocal extends EntityValidator<PurchaseOrderItem> {

}
