package com.cosmos.acacia.app;

import java.util.Set;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserRight;

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
     * Set the currently logged person.
     * @param person
     */
    void setPerson(Person person);

    /**
     * Sets the general rights
     * @param rights
     */
    void setGeneralRights(Set<UserRight> rights);

    /**
     * Gets the general rights
     * @return
     */
    Set<UserRight> getGeneralRights();


    /**
     * Sets the special permissions
     * @param rights
     */
    void setSpecialPermissions(Set<UserRight> rights);

    /**
     * Gets the special permissions
     * @return
     */
    Set<UserRight> getSpecialPermissions();


    /**
     * Puts a value in the session
     * @param key
     * @param value
     */
    void put(String key, Object value);

    /**
     * Gets a value from the session
     * @param key
     * @return
     */
    Object get(String key);
}
