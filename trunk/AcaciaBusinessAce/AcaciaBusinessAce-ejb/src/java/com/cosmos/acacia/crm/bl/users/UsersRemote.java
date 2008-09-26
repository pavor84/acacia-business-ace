package com.cosmos.acacia.crm.bl.users;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

import javax.ejb.Remote;

import com.cosmos.acacia.callback.CallbackHandler;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling users - registration, login, passwords
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface UsersRemote {

    /** TODO: decide a better place for this */
    public static final String CHANGE_PASSWORD = "change";

    /**
     * Performs a login operation. Note that the password must be already hashed
     *
     * @param username
     * @param password
     * @return the session id for the login
     * @throws ValidationException if the data is incorrect.
     */
    Integer login (String username, char[] password);

    /**
     * Registers a new user. Note that the password must be already hashed
     *
     * @param the user to be registered
     * @return the newly registered user
     * @throws ValidationException if the data is incorrect.
     */
    User signup (User user, Organization organization, Address branch, Person person);

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
     * Updates organization for the current user. In case there are more than
     * one organization, a message should be displayed to the user.
     *
     * @param user
     * @param callbackHandler (for displaying the message)
     */
    void updateOrganization(User user, CallbackHandler handler);

    /**
     * Makes the specified user active or inactive
     * for the specified parent (i.e. Organization)
     *
     * @param user
     * @param parentId
     * @param active whether the user should be active or inactive
     * @return the updated User
     */
    User activateUser(User user, BigInteger parentId, Boolean active);

    /**
     * Makes the specified organization active or inactive
     *
     * @param organization
     * @param active whether the organization should be active or inactive
     * @return the updated organization
     */
    Organization activateOrganization(Organization organization, Boolean active);

    /**
     * Performs a password change
     *
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(char[] oldPassword, char[] newPassword);


    /**
     * Gets the users with a specified parent id (i.e. organization id)
     *
     * @param parentDataObjectId
     * @return list of users
     */
    List<User> getUsers(BigInteger parentDataObjectId);

    /**
     * Sends a request for the current user to join the specified organization.
     * The current user is the one logged-in (taken from the session)
     *
     * @param organization
     * @param branch
     */
    void joinOrganization (Organization organization, Address branch);


    /**
     * Leaves the specified organization
     *
     * @param organization
     */
    void leaveOrganization (Organization organization);

    /**
     * Lists all organizations for the specified user
     *
     * @param user
     * @return list
     */
    List<Organization> getOrganizationsList(User user);

    /**
     * Deletes the specified user
     *
     * @param user
     * @return version
     */
    int deleteUser(User user);

    /**
     * Sets the organization in the session. May be called only when there is no organization in the session
     *
     * @param organization
     */
    void setOrganization(Organization organization);

    /**
     * Gets the UserOrganization object corresponding to the specified params
     *
     * @param user
     * @param organization
     * @return the UserOrganization object
     */
    UserOrganization getUserOrganization(User user, Organization organization);

    /**
     * Gets the User Organization object for the specified person
     *
     * @param person
     * @return the user organization object
     */
    UserOrganization getUserOrganization(Person person);


    /**
     * Saves changes to a specified UserOrganization objecet
     *
     * @param uo
     * @return the saved entity
     */
    UserOrganization saveUserOrganization(UserOrganization uo);


    /**
     * Gets the entity properties for UserOrganization
     *
     * @return the entity properties
     */
    EntityProperties getUserOrganizationEntityProperties();

    /**
     * Changes the branch of a user
     *
     * @param user
     * @param oldBranch
     * @param newBranch
     */
    void changeBranch(User user, Address oldBranch, Address newBranch);
}