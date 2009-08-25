package com.cosmos.test.bl;

import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.security.SecureRandom;
import java.util.Random;

import javax.ejb.EJBException;

import org.apache.log4j.Logger;

import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListRemote;
import com.cosmos.acacia.crm.bl.contactbook.PersonsListRemote;
import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.client.LocalSession;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AcaciaPanel;
import java.util.UUID;

/**
 * Created	:	18.04.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 * Some useful methods
 *
 */
public abstract class TestUtils {
    private static Random r = new SecureRandom();

    /**
     * Nobody wants to make it concrete
     */
    private TestUtils(){

    }

    /**
     * Get random string with a given length
     * @param charactersLength
     * @return
     */
    public static String getRandomString(int charactersLength) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < charactersLength; i++) {
            int randInt = r.nextInt(Character.MAX_RADIX);
            b.append(Integer.toString(randInt, Character.MAX_RADIX));
        }
        return b.toString();
    }

    /**
     * Return random positive integer between 0 and mod-1 inclusive
     * @param mod
     * @return
     */
    public static int nextInteger(int mod){
        return r.nextInt(mod);
    }

    /**
     * Extract the validation exception ( @see {@link ValidationException} )
     * from a {@link RemoteException} or any other exception.
     * The {@link ValidationException} is some inner cause
     * @param ex
     * @return
     */
    public static ValidationException extractValidationException(Exception ex) {
        Throwable e = ex;
        while ( e!=null ){
            if ( e instanceof ValidationException ){
                return (ValidationException) e;
            }
            else if ( e instanceof ServerException || e instanceof RemoteException ){
                e = e.getCause();
            }
            else if ( e instanceof EJBException )
                e = ((EJBException)e).getCausedByException();
            else
                break;
        }
        System.out.println("ER: " + (e instanceof EJBException));
        Logger.getLogger(TestUtils.class).error("ERROR: ", e);
        return null;
    }


    /**
     * Creates a random string, which is a valid email
     *
     * @return the generated email
     */
    public static String getRandomEmail() {
        return getRandomString(8) + "@" + getRandomString(7) + ".com";
    }


    /**
     * Method to be used for emulating a login into the system.
     * Important - delete the returned user after the test case(s),
     * using TestUtils.clearLogin(user)
     *
     * @return the result from login, containing user, random organization
     * and random branch from it
     */
    public static LoginResult login() {

        UsersRemote usersSession = AcaciaPanel.getBean(UsersRemote.class, false);
        OrganizationsListRemote orgSession = AcaciaPanel.getBean(OrganizationsListRemote.class, false);
        PersonsListRemote personsSession = AcaciaPanel.getBean(PersonsListRemote.class, false);

        Organization org = null;
        Address branch = null;
        User user = null;

        // subsequent organization, in the search of appropriate one
        int i = 0;
        boolean searchMore = true;
        while (searchMore) {
            try {
                // Must have at least one organization in the database
                org = orgSession.getOrganizations(null).get(i);
                LocalSession.instance().put(LocalSession.ORGANIZATION, org);

                // Must have at least one person for the organization
                Person person = personsSession.getPersons(org.getId()).get(0);

                // Must have at least one branch for the organization
                branch = orgSession.getAddresses(org.getId()).get(0);
                searchMore = false;

                user = usersSession.createUser("");

                user.setUserName(TestUtils.getRandomString(10));
                user.setUserPassword("asd");
                user.setEmailAddress(TestUtils.getRandomEmail());
                //user.setBranchName(branch.getAddressName());
                user.setPerson(person);
                //user.setActive(true);

                user = usersSession.signup(user, org, branch, person);
                usersSession.activateUser(user, org.getId(), true);

                UUID sessionId = usersSession.login(user.getUserName(), "asd".toCharArray());
                AcaciaApplication.setSessionId(sessionId);
                usersSession.setOrganization(org);

            } catch (Exception ex) {
                i ++;
                // Fail after 10 errors
                if (i > 10)
                    searchMore = false;
            }
        }

        LoginResult result = new LoginResult();
        result.setOrganization(org);
        result.setUser(user);
        result.setBranch(branch);

        return result;
    }

    /**
     * Method for clearing the newly created user for login simulation
     *
     * @param user
     */
    public static void clearLogin(User user) {
        UsersRemote usersSession = AcaciaPanel.getBean(UsersRemote.class, false);
        if (user != null)
            usersSession.deleteUser(user);
    }
}
