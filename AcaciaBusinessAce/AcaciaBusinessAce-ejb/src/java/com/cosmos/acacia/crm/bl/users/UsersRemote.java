package com.cosmos.acacia.crm.bl.users;

import java.util.Locale;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.User;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling users - registration, login, passwords
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface UsersRemote {


    /**
     * Performs a login operation. A ValidationException
     * should be thrown if the data is incorrect.
     *
     * @param username
     * @param password
     * @return the user
     */
    User login (String username, String password);

    /**
     * Registers a new user. A ValidationException
     * should be thrown if the data is incorrect.
     *
     * @param the user to be registered
     * @return the newly registered user
     */
    User signup (User user);

    /**
     * Sends a request for registration
     *
     * @param email
     */
    void requestRegistration(String email);

    /**
     * Verifies the entered code for proceeding with registration
     * A ValidationException should be thrown in case the code is not found
     *
     * @param code
     * @return the email.
     */
    String verifyCode(String code);

    /**
     * Sends a temporary password
     *
     * @param email
     */
    void remindPasswordByEmail(String email);

    /**
     * Sends a temporary password
     *
     * @param username
     */
    void remindPasswordByUsername(String username);

    /**
     * Creates a new user instance
     *
     * @return the user
     */
    User createUser();


    /**
     * Gets the User EntityProperties
     *
     * @return the entity properties
     */
    EntityProperties getUserEntityProperties();


    /**
     * Gives a list of available locales
     *
     * @return list of locales
     */
    Locale[] serveLocalesList();
}
