package com.cosmos.test.bl.users;

import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.users.RightsManagerRemote;
import com.cosmos.acacia.crm.bl.users.UserRightsRemote;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.users.Right;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserGroup;
import com.cosmos.acacia.crm.data.users.UserGroupRight;
import com.cosmos.acacia.crm.data.users.UserRight;
import com.cosmos.acacia.security.AccessRight;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.test.bl.LoginResult;
import com.cosmos.test.bl.TestUtils;

public class UserRightsTest {

    private static RightsManagerRemote formSession;
    private static UserRightsRemote rightsSession;
    private static User user;
    private static UserGroup userGroup;
    private static Organization org;
    private static Address branch;

    @BeforeClass
    public static void setUpClass() {
        if (formSession == null) {
            formSession = AcaciaPanel.getBean(RightsManagerRemote.class, false);
        }

        rightsSession = AcaciaPanel.getBean(UserRightsRemote.class, false);

        LoginResult loginResult = TestUtils.login();
        user = loginResult.getUser();
        branch = loginResult.getBranch();
        org = loginResult.getOrganization();

        userGroup = rightsSession.newUserGroup();
        userGroup.setName(TestUtils.getRandomString(10));
        userGroup = rightsSession.saveUserGroup(userGroup);

        rightsSession.assignGroupToUser(userGroup, user);
    }

    @AfterClass
    public static void tearDownClass() {
        TestUtils.clearLogin(user);

        if (userGroup != null) {
            rightsSession.deleteUserGroup(userGroup);
        }
    }

    @Before
    public void setUp() {
        rightsSession.assignRightsToGroup(new HashSet<Right>(), userGroup);
        rightsSession.assignRightsToUser(new HashSet<Right>(), user);
        formSession.setGeneralRights(null);
    }

    @Test
    public void groupWithNoRightUserWithRightTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = user.getDataObject();
        Right groupRight = createRight(userGroup, null, dob, false);
        groupRight.setCreate(false);
        rights.add(groupRight);
        rightsSession.assignRightsToGroup(rights, userGroup);

        Set<Right> rights2 = new HashSet<Right>();
        Right userRight = createRight(user, null, dob, false);
        userRight.setCreate(true);
        rights2.add(userRight);
        rightsSession.assignRightsToUser(rights2, user);

        Assert.assertTrue(
                "User is expected to have the specified right, but does not",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void groupWithRightUserWithNoRightTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = user.getDataObject();
        Right groupRight = createRight(userGroup, null, dob, false);
        groupRight.setCreate(true);
        rights.add(groupRight);
        rightsSession.assignRightsToGroup(rights, userGroup);

        Set<Right> rights2 = new HashSet<Right>();
        Right userRight = createRight(user, null, dob, false);
        userRight.setCreate(false);
        rights2.add(userRight);
        rightsSession.assignRightsToUser(rights2, user);

        Assert.assertFalse(
                "User is expected NOT to have the specified right, but has it",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void groupWithRightUserRightsNotSpecifiedTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = user.getDataObject();
        Right groupRight = createRight(userGroup, null, dob, false);
        groupRight.setCreate(true);
        rights.add(groupRight);
        rightsSession.assignRightsToGroup(rights, userGroup);

        Assert.assertTrue(
                "User is expected to have the specified right, because a member" +
                " of a group that has it, but does not",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void groupWithRightOnTypeUserWithNoRightOnObjectTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = user.getDataObject();
        Right groupRight = createRight(userGroup, dob.getDataObjectType(), null, false);
        groupRight.setCreate(true);
        rights.add(groupRight);
        rightsSession.assignRightsToGroup(rights, userGroup);

        Set<Right> rights2 = new HashSet<Right>();
        Right userRight = createRight(user, null, dob, false);
        userRight.setCreate(false);
        rights2.add(userRight);
        rightsSession.assignRightsToUser(rights2, user);

        Assert.assertFalse(
                "User is expected NOT to have the specified right, but has it",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void groupWithNoRightOnTypeUserWithRightOnObjectTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = user.getDataObject();
        Right groupRight = createRight(userGroup, dob.getDataObjectType(), null, false);
        groupRight.setCreate(false);
        rights.add(groupRight);
        rightsSession.assignRightsToGroup(rights, userGroup);

        Set<Right> rights2 = new HashSet<Right>();
        Right userRight = createRight(user, null, dob, false);
        userRight.setCreate(true);
        rights2.add(userRight);
        rightsSession.assignRightsToUser(rights2, user);

        Assert.assertTrue(
                "User is expected to have the specified right, but does not",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void userWithRightOnTypeObjectExcludedTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = user.getDataObject();

        Right userRight = createRight(user, dob.getDataObjectType(), null, false);
        userRight.setCreate(true);
        rights.add(userRight);

        Right userRightExcluded = createRight(user, null, dob, true);
        userRightExcluded.setCreate(true);
        rights.add(userRightExcluded);

        rightsSession.assignRightsToUser(rights, user);

        Assert.assertTrue(
                "User is expected to have the specified right" +
                " (because of exclusion, and falling back to default value)," +
                " but does not have it",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void groupWithRightOnTypeObjectForUserExcludedTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = user.getDataObject();

        Right groupRight = createRight(userGroup, dob.getDataObjectType(), null, false);
        groupRight.setCreate(true);
        rights.add(groupRight);

        Right userRightExcluded = createRight(user, null, dob, true);
        userRightExcluded.setCreate(true);
        rights.add(userRightExcluded);

        rightsSession.assignRightsToUser(rights, user);

        Assert.assertTrue(
                "User is expected to have the specified right" +
                " (because of exclusion, and falling back to default value)," +
                " but does not haveit",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void userWithRightOnTypeObjectForGroupExcludedTest() {
        Set<Right> rights = new HashSet<Right>();

        DataObject dob = branch.getDataObject();
        DataObject parent = org.getDataObject();

        Right userRight = createRight(user, null, dob, false);
        userRight.setCreate(true);
        rights.add(userRight);

        Right parentRight = createRight(user, null, parent, false);
        userRight.setCreate(false);
        rights.add(parentRight);

        Right userRightExcluded = createRight(user, null, dob, true);
        userRightExcluded.setCreate(true);
        rights.add(userRightExcluded);

        rightsSession.assignRightsToUser(rights, user);

        Assert.assertFalse(
                "User is expected NOT to have the specified right," +
                " becauses the parent is disallowed, but he has it",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void userWithNoRightOnParentObjectTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = branch.getDataObject();
        DataObject parent = org.getDataObject();

        Right userRight = createRight(user, null, parent, false);
        userRight.setCreate(false);
        rights.add(userRight);

        rightsSession.assignRightsToUser(rights, user);

        Assert.assertFalse(
                "User is NOT expected to have the specified right" +
                " (via inheritance), but has it",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void groupWithRightOnParentObjectTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = branch.getDataObject();
        DataObject parent = org.getDataObject();

        Right userRight = createRight(userGroup, null, parent, false);
        userRight.setCreate(true);
        rights.add(userRight);

        rightsSession.assignRightsToUser(rights, user);

        Assert.assertTrue(
                "User is expected to have the specified right" +
                " (via inheritance), but does not",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    @Test
    public void userWithRightOnParentObjectTypeTest() {
        Set<Right> rights = new HashSet<Right>();
        DataObject dob = branch.getDataObject();
        DataObject parent = org.getDataObject();

        Right userRight = createRight(user, parent.getDataObjectType(), null, false);
        userRight.setCreate(true);
        rights.add(userRight);

        rightsSession.assignRightsToUser(rights, user);

        Assert.assertTrue(
                "User is expected to have the specified right" +
                " (via inheritance), but does not",
                formSession.isAllowed(user, dob, AccessRight.Create));
    }

    private Right createRight(User user,
            DataObjectType dataObjectType,
            DataObject dataObject,
            boolean excluded) {
        UserRight right = new UserRight();
        right.setUser(user);
        return createRight(right, dataObjectType, dataObject, excluded);
    }

    private Right createRight(UserGroup group,
            DataObjectType dataObjectType,
            DataObject dataObject,
            boolean excluded) {
        UserGroupRight right = new UserGroupRight();
        right.setUserGroup(userGroup);
        return createRight(right, dataObjectType, dataObject, excluded);
    }

    private Right createRight(Right right,
            DataObjectType dataObjectType,
            DataObject dataObject,
            boolean excluded) {
        right.setDataObjectType(dataObjectType);
        right.setDataObject(dataObject);
        right.setExcluded(excluded);

        return right;
    }
}
