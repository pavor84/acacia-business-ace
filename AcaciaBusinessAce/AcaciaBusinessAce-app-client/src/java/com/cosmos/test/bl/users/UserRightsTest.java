package com.cosmos.test.bl.users;

import java.util.HashSet;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.impl.EntityManagerFacadeRemote;
import com.cosmos.acacia.crm.bl.users.RightsManagerBean;
import com.cosmos.acacia.crm.bl.users.RightsManagerRemote;
import com.cosmos.acacia.crm.bl.users.UserRightsRemote;
import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.enums.UserRightType;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.test.bl.LoginResult;
import com.cosmos.test.bl.TestUtils;

public class UserRightsTest {

      private static RightsManagerRemote formSession;

      private static UsersRemote usersSession;
      private static UserRightsRemote rightsSession;

      private static User user;
      private static UserGroup userGroup;
      private static Organization org;
      private static Address branch;

      @BeforeClass
      public static void setUpClass() {
          if (formSession == null)
              formSession = AcaciaPanel.getBean(RightsManagerRemote.class, false);

          usersSession = AcaciaPanel.getBean(UsersRemote.class, false);
          rightsSession = AcaciaPanel.getBean(UserRightsRemote.class, false);

          LoginResult loginResult = TestUtils.login();
          user = loginResult.getUser();
          branch = loginResult.getBranch();
          org = loginResult.getOrganization();

          userGroup = rightsSession.newUserGroup(org.getId());
          userGroup.setName(TestUtils.getRandomString(10));
          userGroup = rightsSession.saveUserGroup(userGroup);

          rightsSession.assignGroupToUser(userGroup, user);
      }

      @AfterClass
      public static void tearDownClass() {
          TestUtils.clearLogin(user);

          if (userGroup != null)
              rightsSession.deleteUserGroup(userGroup);
      }

      @Before
      public void setUp() {
          rightsSession.assignRightsToGroup(new HashSet<UserRight>(), userGroup);
          rightsSession.assignRightsToUser(new HashSet<UserRight>(), user);
          formSession.setGeneralRights(null);
      }

      @Test
      public void groupWithNoRightUserWithRightTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = user.getDataObject();
          UserRight groupRight = createUserRight(null, userGroup, null, dob, false);
          groupRight.setCreate(false);
          rights.add(groupRight);
          rightsSession.assignRightsToGroup(rights, userGroup);

          rights.clear();
          UserRight userRight = createUserRight(user, null, null, dob, false);
          userRight.setCreate(true);
          rights.add(userRight);
          rightsSession.assignRightsToUser(rights, user);

          Assert.assertTrue(
                  "User is expected to have the specified right, but does not",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }


      @Test
      public void groupWithRightUserWithNoRightTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = user.getDataObject();
          UserRight groupRight = createUserRight(null, userGroup, null, dob, false);
          groupRight.setCreate(true);
          rights.add(groupRight);
          rightsSession.assignRightsToGroup(rights, userGroup);

          rights.clear();
          UserRight userRight = createUserRight(user, null, null, dob, false);
          userRight.setCreate(false);
          rights.add(userRight);
          rightsSession.assignRightsToUser(rights, user);

          Assert.assertFalse(
                  "User is expected NOT to have the specified right, but has it",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }

      @Test
      public void groupWithRightUserRightsNotSpecifiedTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = user.getDataObject();
          UserRight groupRight = createUserRight(null, userGroup, null, dob, false);
          groupRight.setCreate(true);
          rights.add(groupRight);
          rightsSession.assignRightsToGroup(rights, userGroup);

          Assert.assertTrue(
                  "User is expected to have the specified right, because a member" +
                  " of a group that has it, but does not",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }


      @Test
      public void groupWithRightOnTypeUserWithNoRightOnObjectTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = user.getDataObject();
          UserRight groupRight = createUserRight(null, userGroup, dob.getDataObjectType(), null, false);
          groupRight.setCreate(true);
          rights.add(groupRight);
          rightsSession.assignRightsToGroup(rights, userGroup);

          rights.clear();
          UserRight userRight = createUserRight(user, null, null, dob, false);
          userRight.setCreate(false);
          rights.add(userRight);
          rightsSession.assignRightsToUser(rights, user);

          Assert.assertFalse(
                  "User is expected NOT to have the specified right, but has it",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }


      @Test
      public void groupWithNoRightOnTypeUserWithRightOnObjectTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = user.getDataObject();
          UserRight groupRight = createUserRight(null, userGroup, dob.getDataObjectType(), null, false);
          groupRight.setCreate(false);
          rights.add(groupRight);
          rightsSession.assignRightsToGroup(rights, userGroup);

          rights.clear();
          UserRight userRight = createUserRight(user, null, null, dob, false);
          userRight.setCreate(true);
          rights.add(userRight);
          rightsSession.assignRightsToUser(rights, user);

          Assert.assertTrue(
                  "User is expected to have the specified right, but does not",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }


      @Test
      public void userWithRightOnTypeObjectExcludedTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = user.getDataObject();

          UserRight userRight = createUserRight(user, null, dob.getDataObjectType(), null, false);
          userRight.setCreate(true);
          rights.add(userRight);

          UserRight userRightExcluded = createUserRight(user, null, null, dob, true);
          userRightExcluded.setCreate(true);
          rights.add(userRightExcluded);

          rightsSession.assignRightsToUser(rights, user);

          Assert.assertFalse(
                  "User is expected NOT to have the specified right" +
                  " (because of exclusion), but has it",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }

      @Test
      public void groupWithRightOnTypeObjectForUserExcludedTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = user.getDataObject();

          UserRight groupRight = createUserRight(null, userGroup, dob.getDataObjectType(), null, false);
          groupRight.setCreate(true);
          rights.add(groupRight);

          UserRight userRightExcluded = createUserRight(user, null, null, dob, true);
          userRightExcluded.setCreate(true);
          rights.add(userRightExcluded);

          rightsSession.assignRightsToUser(rights, user);

          Assert.assertFalse(
                  "User is expected NOT to have the specified right" +
                  " (because of exclusion), but has it",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }


      @Test
      public void userWithRightOnTypeObjectForGroupExcludedTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = user.getDataObject();

          UserRight userRight = createUserRight(user, null, dob.getDataObjectType(), null, false);
          userRight.setCreate(true);
          rights.add(userRight);

          UserRight userGroupRightExcluded = createUserRight(null, userGroup, null, dob, true);
          userGroupRightExcluded.setCreate(true);
          rights.add(userGroupRightExcluded);

          rightsSession.assignRightsToUser(rights, user);

          Assert.assertTrue(
                  "User is expected to have the specified right, but does not",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }

      @Test
      public void userWithNoRightOnParentObjectTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = branch.getDataObject();
          DataObject parent = org.getDataObject();

          UserRight userRight = createUserRight(user, null, null, parent, false);
          userRight.setCreate(false);
          rights.add(userRight);

          rightsSession.assignRightsToUser(rights, user);

          Assert.assertFalse(
                  "User is NOT expected to have the specified right" +
                  " (via inheritance), but has it",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }

      @Test
      public void groupWithRightOnParentObjectTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = branch.getDataObject();
          DataObject parent = org.getDataObject();

          UserRight userRight = createUserRight(null, userGroup, null, parent, false);
          userRight.setCreate(true);
          rights.add(userRight);

          rightsSession.assignRightsToUser(rights, user);

          Assert.assertTrue(
                  "User is expected to have the specified right" +
                  " (via inheritance), but does not",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }

      @Test
      public void userWithRightOnParentObjectTypeTest() {
          Set<UserRight> rights = new HashSet<UserRight>();
          DataObject dob = branch.getDataObject();
          DataObject parent = org.getDataObject();

          UserRight userRight = createUserRight(user, null, parent.getDataObjectType(), null, false);
          userRight.setCreate(true);
          rights.add(userRight);

          rightsSession.assignRightsToUser(rights, user);

          Assert.assertTrue(
                  "User is expected to have the specified right" +
                  " (via inheritance), but does not",
                  formSession.isAllowed(user, dob, UserRightType.CREATE));
      }

      private UserRight createUserRight(User user,
              UserGroup group,
              DataObjectType dataObjectType,
              DataObject dataObject,
              boolean excluded)
      {
          UserRight right = new UserRight();
          right.setUser(user);
          right.setUserGroup(group);
          right.setDataObjectType(dataObjectType);
          right.setDataObject(dataObject);
          right.setExcluded(excluded);

          return right;
      }
}
