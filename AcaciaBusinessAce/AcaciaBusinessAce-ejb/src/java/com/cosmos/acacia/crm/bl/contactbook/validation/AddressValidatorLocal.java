package com.cosmos.acacia.crm.bl.contactbook.validation;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.validation.EntityValidator;

/**
 * Created	:	26.04.2008
 * @author	Bozhidar Bozhanov
 */

@Local
public interface AddressValidatorLocal extends EntityValidator<Address>{

}
