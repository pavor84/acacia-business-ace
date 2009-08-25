package com.cosmos.test.bl.users;

import com.cosmos.acacia.crm.bl.users.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListRemote;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.test.bl.TestUtils;
import java.util.UUID;



/**
*
* Created	:	10.07.2008
* @author	Bozhidar Bozhanov
* @version $Id: $
*
* Business logic test for
* {@link UsersBean}
*
*/

public class UsersTest implements Serializable {

    @EJB
    private UsersRemote formSession;
    private User user;
    String password;
    Organization organization;
    OrganizationsListRemote orgSession;

    @Before
    public void setUp() {
        if (formSession == null)
            formSession = AcaciaPanel.getBean(UsersRemote.class, false);

        user = formSession.createUser("");
        password = TestUtils.getRandomString(15);
        user.setUserName(TestUtils.getRandomString(10));
        user.setUserPassword(password);
        user.setEmailAddress(TestUtils.getRandomEmail());


        if (orgSession == null)
            orgSession = AcaciaPanel.getBean(OrganizationsListRemote.class, false);

        organization = orgSession.newOrganization(null);
        organization.setOrganizationName(TestUtils.getRandomString(10));
        organization = orgSession.saveOrganization(organization);

        formSession.activateOrganization(organization, true);
        user = formSession.signup(user, organization, null, null);

        formSession.activateUser(user, organization.getId(), true);
    }

    @After
    public void tearDown() {
        formSession.activateUser(user, organization.getId(), false);
        formSession.activateOrganization(organization, false);
        orgSession.deleteOrganization(organization);
        formSession.deleteUser(user);
    }

    @Test
    public void entityPropertiesTest() {
        EntityProperties props = formSession.getUserEntityProperties();
        Assert.assertNotNull(props);
    }

    @Test
    public void requestRegistrationTest() {
        String email = TestUtils.getRandomEmail();
        formSession.requestRegistration(email);
    }

    @Test
    public void verifyCodeTest() {
        // Negative test case
        try {
            formSession.verifyCode("123456");
            Assert.fail();
        } catch (Exception ex) {
            ValidationException e = TestUtils.extractValidationException(ex);
            Assert.assertNotNull(e);
            Assert.assertNotNull(e.getMessages());
        }
    }

    @Test
    public void setLocaleTest() {
        formSession.setLocale(new Locale("en"));
    }

    @Test
    public void encryptDecryptPasswordTest() {
        String pass = TestUtils.getRandomString(10);
        char[] password = pass.toCharArray();
        String result = formSession.encryptPassword(password);
        Assert.assertNotNull(result);

        char[] decrypted = formSession.decryptPassword(result);

        Assert.assertEquals("Initial password doesn't match the decrypted",
                pass,
                new String(decrypted));
    }

    @Test
    public void serveLocaleListTest() {
        Locale[] result = formSession.serveLocalesList();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.length > 0);
    }

    @Test
    public void signupExceptionsTest() {
        User newUser = formSession.createUser("");
        newUser.setUserName(user.getUserName());
        newUser.setUserPassword(TestUtils.getRandomString(10));
        try {
            formSession.signup(newUser, organization, null, null);
            Assert.fail();
        } catch (Exception ex) {
            ValidationException ve = TestUtils.extractValidationException(ex);
            Assert.assertNotNull(ve);
            Assert.assertNotNull(ve.getMessages().size() > 0);
        }
    }

    @Test
    public void loginTest() {
        // Negative test first
        try {
            formSession.login(user.getUserName(), (password + "a").toCharArray());
            Assert.fail();
        } catch (Exception ex) {
            ValidationException ve = TestUtils.extractValidationException(ex);
            Assert.assertNotNull(ve);
            Assert.assertNotNull(ve.getMessage());
        }
        UUID sessionId = formSession.login(user.getUserName(), password.toCharArray());
        AcaciaApplication.setSessionId(sessionId);
    }

    @Test
    public void remindPasswordByEmailTest() {
        formSession.remindPasswordByEmail(user.getEmailAddress());
    }

    @Test
    public void remindPasswordByUsernameTest() {
        formSession.remindPasswordByUsername(user.getUserName());
    }

    @Test
    public void changePasswordTest() {
        loginTest();
        String newPassword = TestUtils.getRandomString(10);
        formSession.changePassword(password.toCharArray(), newPassword.toCharArray());
        password = newPassword;
    }

    @Test
    public void getUsersTest() {
        List<User> list = formSession.getUsers(organization);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void organizationsTest() {
        Organization org = orgSession.newOrganization(null);
        org.setOrganizationName(TestUtils.getRandomString(10));
        org = orgSession.saveOrganization(org);
        loginTest();

        formSession.joinOrganization(org, null);
        formSession.updateOrganization(user, null);

        List<Organization> orgs = formSession.getOrganizationsList(user);
        Assert.assertNotNull(orgs);
        Assert.assertTrue(orgs.size() >= 2);

        formSession.leaveOrganization(org);
        orgSession.deleteOrganization(org);
    }
}


class UsersTestCallbackHandler implements CallbackHandler, Serializable {
    Organization org;
    public UsersTestCallbackHandler(Organization org) {
        this.org = org;
    }
    @Override
    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {
        ((OrganizationCallback) callbacks[0]).setOrganization(org);
    }
}