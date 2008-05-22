package com.cosmos.acacia.app;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Remote
public interface AcaciaSessionRemote extends AcaciaSession {

    void login(String user, String password);

    DataObject getLoginOrganizationDataObject();
}
