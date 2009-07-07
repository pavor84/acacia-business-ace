package com.cosmos.acacia.app;

import com.cosmos.acacia.crm.data.Expression;
import java.util.Set;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.users.Right;
import com.cosmos.acacia.crm.data.users.User;
import java.util.UUID;

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
    UUID login(User user);

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
    void setGeneralRights(Set<Right> rights);

    /**
     * Gets the general rights
     * @return
     */
    Set<Right> getGeneralRights();


    /**
     * Sets the special permissions
     * @param rights
     */
    void setSpecialPermissions(Set<Right> rights);

    /**
     * Gets the special permissions
     * @return
     */
    Set<Right> getSpecialPermissions();


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

    String getExpressionKey(Class beanClass, String propertyName);

    Expression saveExpression(Expression expression);

    void deleteExpression(Expression expression);
}
