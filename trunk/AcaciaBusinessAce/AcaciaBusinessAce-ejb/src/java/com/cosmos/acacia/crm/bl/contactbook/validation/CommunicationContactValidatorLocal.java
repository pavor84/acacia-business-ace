package com.cosmos.acacia.crm.bl.contactbook.validation;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.validation.EntityValidator;

/**
 * Created	:	26.04.2008
 * @author	Bozhidar Bozhanov
 */

@Local
public interface CommunicationContactValidatorLocal extends EntityValidator<CommunicationContact>{

}
