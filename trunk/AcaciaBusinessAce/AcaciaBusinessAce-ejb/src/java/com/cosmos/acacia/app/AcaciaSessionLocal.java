package com.cosmos.acacia.app;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Local
public interface AcaciaSessionLocal extends AcaciaSessionRemote{
	/**
	 * Logs in the user, creates new session and returns the session id.
	 * @param user
	 * @param password
	 * @return session id
	 */
    Integer login(User user);

    /**
     * Sets the current login organization for the session.
     * @param organization
     */
	void setOrganization(Organization organization);

	/**
	 * Set the current login branch for the session.
	 * @param branch 
	 */
    void setBranch(Address branch);

    /**
     * Set the currently logged person. 
     * @param person
     */
    void setPerson(Person person);
}
