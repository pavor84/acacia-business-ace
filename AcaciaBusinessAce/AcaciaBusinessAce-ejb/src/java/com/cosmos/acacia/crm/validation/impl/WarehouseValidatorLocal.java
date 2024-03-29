package com.cosmos.acacia.crm.validation.impl;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import com.cosmos.acacia.crm.validation.EntityValidator;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Local
public interface WarehouseValidatorLocal extends EntityValidator<Warehouse> {

}
