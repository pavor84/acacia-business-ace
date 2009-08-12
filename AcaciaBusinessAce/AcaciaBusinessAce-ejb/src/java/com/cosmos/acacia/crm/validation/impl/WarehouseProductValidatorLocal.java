package com.cosmos.acacia.crm.validation.impl;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.warehouse.WarehouseProduct;
import com.cosmos.acacia.crm.validation.EntityValidator;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Local
public interface WarehouseProductValidatorLocal extends EntityValidator<WarehouseProduct> {

}
