package com.cosmos.test.bl.users;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;

import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.users.RightsManagerRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.gui.AcaciaPanel;

public class UserRightsTest {

      @EJB
      private RightsManagerRemote formSession;

      private User user;
      private UserGroup userGroup;

      @Before
      public void setUp() {
          if (formSession == null)
              formSession = AcaciaPanel.getRemoteBean(this, RightsManagerRemote.class);

          // Creating detached instances. Will never be merged with hibernate session

          if (user == null) {
              user = new User();
              user.setUserId(BigInteger.valueOf(5));
          }

          if (userGroup == null) {
              userGroup = new UserGroup();
              userGroup.setUserGroupId(BigInteger.valueOf(6));
          }
      }

      @Test
      public void groupWithNoRightUserWithRightTest() {
          Set<UserRight> rights = new HashSet<UserRight>();

          //rights.add(createUserRight(user, userGroup, dataObjectType, dataObject, false))

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
