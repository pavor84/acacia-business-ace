package com.cosmos.acacia.app;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Remote
public interface AcaciaSessionRemote {

//    DataObject getLoginOrganizationDataObject();

    DataObject getDataObject(BigInteger dataObjectId);

    User getUser();

    Organization getOrganization();

    /**
     * Retrieve the login branch of the current user.
     * @return
     */
    Address getBranch();

    /**
     * Get the currently logged person.
     * @return
     */
    Person getPerson();

    /**
     * Gets the list of all data object types
     * @return
     */
    List<DataObjectType> getDataObjectTypes();
    
    /**
    * Set the current login branch for the session.
    * @param branch
    */
    void setBranch(Address branch);
}

