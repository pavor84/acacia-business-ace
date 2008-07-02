package com.cosmos.acacia.app;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.User;

import java.math.BigInteger;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Remote
public interface AcaciaSessionRemote {

    DataObject getLoginOrganizationDataObject();

    DataObject getDataObject(BigInteger dataObjectId);

	User getUser();

	Organization getOrganization();
}
