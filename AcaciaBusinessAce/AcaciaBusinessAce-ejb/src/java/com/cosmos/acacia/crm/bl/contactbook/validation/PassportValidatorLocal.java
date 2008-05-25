package com.cosmos.acacia.crm.bl.contactbook.validation;

import com.cosmos.acacia.crm.data.Passport;
import javax.ejb.Local;

import com.cosmos.acacia.crm.validation.EntityValidator;

/**
 * Created	:	26.04.2008
 * @author	Bozhidar Bozhanov
 */

@Local
public interface PassportValidatorLocal extends EntityValidator<Passport>{

}
