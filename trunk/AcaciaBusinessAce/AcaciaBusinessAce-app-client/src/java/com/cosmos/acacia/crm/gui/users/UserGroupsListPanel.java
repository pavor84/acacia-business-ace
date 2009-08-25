/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.users;

import java.util.UUID;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.users.UserRightsRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.users.UserGroup;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class UserGroupsListPanel extends AbstractTablePanel<UserGroup> {

    /** Creates new form BankDetailsListPanel */
    public UserGroupsListPanel(UUID parentDataObjectId) {
        super(parentDataObjectId);
    }

    public UserGroupsListPanel(DataObjectBean parent) {
        super(parent);
    }
    @EJB
    private UserRightsRemote formSession;
    private BindingGroup userGroupsBindingGroup;
    private List<UserGroup> userGroups;

    @Override
    protected void initData() {

        super.initData();

        setVisible(Button.Select, false);
        userGroupsBindingGroup = new BindingGroup();
        AcaciaTable userGroupsTable = getDataTable();
        userGroupsTable.bind(userGroupsBindingGroup, getUserGroups(), getUserGroupEntityProperties());

        userGroupsBindingGroup.bind();

        userGroupsTable.setEditable(false);
    }

    protected List<UserGroup> getUserGroups() {
        if (userGroups == null) {
            userGroups = getFormSession().getUserGroups();
        }

        return userGroups;
    }

    protected EntityProperties getUserGroupEntityProperties() {
        return getFormSession().getUserGroupEntityProperties();
    }

    protected UserRightsRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(UserRightsRemote.class);
        }

        return formSession;
    }

    protected int deleteUserGroup(UserGroup userGroup) {
        return getFormSession().deleteUserGroup(userGroup);
    }

    @Override
    @Action
    public void selectAction() {
        super.selectAction();
        //
    }

    @Override
    protected boolean deleteRow(UserGroup rowObject) {
        if (rowObject != null) {
            deleteUserGroup(rowObject);
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (userGroupsBindingGroup != null) {
            userGroupsBindingGroup.unbind();
        }

        userGroups = null;

        initData();

        return t;
    }

    @Override
    protected UserGroup modifyRow(UserGroup rowObject) {
        if (rowObject != null) {
            UserGroupPanel userGroupPanel = new UserGroupPanel((UserGroup) rowObject);
            DialogResponse response = userGroupPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (UserGroup) userGroupPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected UserGroup newRow() {
        if (canNestedOperationProceed()) {
            UserGroupPanel userGroupPanel = new UserGroupPanel(getParentDataObjectId());

            DialogResponse response = userGroupPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (UserGroup) userGroupPanel.getSelectedValue();
            }
        }
        return null;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(UserGroup rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(UserGroup rowObject) {
        return true;
    }
}
