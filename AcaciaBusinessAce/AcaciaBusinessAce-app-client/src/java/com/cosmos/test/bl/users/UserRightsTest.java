package com.cosmos.test.bl.users;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListRemote;
import com.cosmos.acacia.crm.bl.contactbook.PersonsListRemote;
import com.cosmos.acacia.crm.bl.users.RightsManagerRemote;
import com.cosmos.acacia.crm.bl.users.UserRightsRemote;
import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.enums.UserRightType;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.test.bl.TestUtils;

public class UserRightsTest {

      private RightsManagerRemote formSession;

      private UsersRemote usersSession;
      private UserRightsRemote rightsSession;

      private User user;
      private UserGroup userGroup;
      private Organization org;

      @Before
      public void setUp() {
          if (formSession == null)
              formSession = AcaciaPanel.getRemoteBean(this, RightsManagerRemote.class);

          usersSession = AcaciaPanel.getRemoteBean(this, UsersRemote.class);
          rightsSession = AcaciaPanel.getRemoteBean(this, UserRightsRemote.class);
          OrganizationsListRemote orgSession = AcaciaPanel.getRemoteBean(this, OrganizationsListRemote.class);
          PersonsListRemote personsSession = AcaciaPanel.getRemoteBean(this, PersonsListRemote.class);

          // subsequent organization, in the search of appropriate one
          int i = 0;
          boolean searchMore = true;
          while (searchMore) {
              try {
                  // Must have at least one organization in the database
                  org = orgSession.getOrganizations(null).get(i);

                  // Must have at least one person for the organization
                  Person person = personsSession.getPersons(org.getId()).get(0);

                  // Must have at least one branch for the organization
                  Address branch = orgSession.getAddresses(org.getId()).get(0);
                  searchMore = false;

                  user = usersSession.createUser();

                  user.setUserName(TestUtils.getRandomString(10));
                  user.setUserPassword("asd");
                  user.setEmailAddress(TestUtils.getRandomEmail());
                  user.setBranchName(branch.getAddressName());
                  user.setPerson(person);
                  user.setActive(true);

                  user = usersSession.signup(user, org, branch, person);
                  usersSession.activateUser(user, org.getId(), true);

                  Integer sessionId = usersSession.login(user.getUserName(), "asd".toCharArray());
                  AcaciaApplication.setSessionId(sessionId);
                  usersSession.setOrganization(org);

                  userGroup = rightsSession.newUserGroup(org.getId());
                  userGroup.setName(TestUtils.getRandomString(10));
                  userGroup = rightsSession.saveUserGroup(userGroup);

                  rightsSession.assignGroupToUser(userGroup, user);

              } catch (Exception ex) {
//                  System.out.println("Ignorable error: ");
//                  ex.printStackTrace();
                  i ++;
                  // Fail after 10 errors
                  if (i > 10)
                      searchMore = false;
              }
          }
      }

      @After
      public void tearDown() {
          if (user != null)
              usersSession.deleteUser(user);

          if (userGroup != null)
              rightsSession.deleteUserGroup(userGroup);
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
