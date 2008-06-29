package com.cosmos.acacia.crm.bl.users;

import java.util.Locale;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import javax.security.auth.callback.CallbackHandler;

/**
 * An EJB for handling users - registration, login, passwords
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface UsersRemote {


    /**
     * Performs a login operation. Note that the password must be already hashed
     *
     * @param username
     * @param password
     * @return the user
     * @throws ValidationException if the data is incorrect.
     */
    User login (String username, char[] password);

    /**
     * Registers a new user. Note that the password must be already hashed
     *
     * @param the user to be registered
     * @return the newly registered user
     * @throws ValidationException if the data is incorrect.
     */
    User signup (User user, Organization organization, Address branch);

    /**
     * Sends a request for registration
     *
     * @param email
     */
    void requestRegistration(String email);

    /**
     * Verifies the entered code for proceeding with registration
     *
     * @param code
     * @return the email.
     * @throws ValidationException in case the code is not found
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
    
    /**
     * Sets the locale for messages
     * @param locale
     */
    void setLocale(Locale locale);
    
    /**
     * Encrypts a password for storing locally on the client
     * @return
     */
    String encryptPassword(char[] password);
    
    /**
     * Decrypts a password stored locally on the client
     * @param password
     * @return the encrypted password
     */
    char[] decryptPassword(String base64password);
    
    /**
     * Gets the organization for this user. In case there are more than
     * one organization, a message should be displayed to the user.
     * 
     * @param user
     * @param callbackHandler (for displaying the message)
     * @return organization
     */
    Organization getOrganization(User user, CallbackHandler callbackHandler);
    
}