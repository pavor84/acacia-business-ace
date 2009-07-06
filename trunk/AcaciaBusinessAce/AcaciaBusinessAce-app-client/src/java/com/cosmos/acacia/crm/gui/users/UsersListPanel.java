/*
 * OrganizationsListPanel.java
 *
 */

package com.cosmos.acacia.crm.gui.users;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.TablePanelListener;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Panel for listing existing organizations, giving CRUD options
 *
 * @author  Bozhidar Bozhanov
 */
public class UsersListPanel extends AbstractTablePanel<User> {

    /** Creates new form OrganizationsListPanel */
    public UsersListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }

    public UsersListPanel(BigInteger parentDataObjectId, Classifier classifier) {
        super(parentDataObjectId);
        setClassifier(classifier);
    }

    @EJB
    private UsersRemote adminSession;

    private BindingGroup usersBindingGroup;
    private List<User> users;
    private User ownUser;

    @Override
    protected void initData() {
        super.initData();

        setVisibleButtons(4 + 16 + 32 + 512);

        ownUser = getAcaciaSession().getUser();
        initAdminView();

        usersBindingGroup = new BindingGroup();
        AcaciaTable usersTable = getDataTable();
        usersTable.bind(usersBindingGroup, getUsers(), getUserEntityProperties());


        usersBindingGroup.bind();

        usersTable.setEditable(false);
    }

    private void initAdminView() {
        setSpecialCaption("activateUser.Action.text");
        setVisible(Button.Special, true);
        addTablePanelListener(new TablePanelListener() {
            @Override
            public void selectionRowChanged() {
                updateButtonCaption();
            }
            @Override public void tablePanelClose() {
                //
            }
            @Override public void selectAction() {
                //
            }
            @Override public void tableRefreshed() {
                //
            }
        });
    }

    @Override
    public void specialAction() {
        User user = (User) getDataTable().getSelectedRowObject();
        user = getAdminSession().activateUser(
                user,
                getAcaciaSession().getOrganization().getId(),
                new Boolean(!user.isActive()));

        getDataTable().replaceSelectedRow(user);
        fireModify(user);
        updateButtonCaption();
    }

    private UsersRemote getAdminSession() {
         if(adminSession == null) {
            try {
                adminSession = getBean(UsersRemote.class);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        return adminSession;
    }

    private void updateButtonCaption() {
        User user = (User) getDataTable().getSelectedRowObject();
        if (user != null) {
            if (user.isActive())
                setSpecialCaption("deactivateUser.Action.text");
            else
                setSpecialCaption("activateUser.Action.text");

            if (user.equals(ownUser))
                setEnabled(Button.Special, false);
        }
    }
    protected List<User> getUsers()
    {
        if(users == null)
        {
            users = getAdminSession().getUsers(getParentDataObjectId());
        }

        return users;
    }

    protected EntityProperties getUserEntityProperties()
    {
        return getAdminSession().getUserEntityProperties();
    }

    @Override
    protected boolean deleteRow(User rowObject) {
        return false;
    }

    @Override
    protected User modifyRow(User rowObject) {
        if(rowObject != null)
        {
            Organization org = getAcaciaSession().getOrganization();
            UserOrganization uo = getAdminSession().getUserOrganization(rowObject, org);
            UserPanel userPanel = new UserPanel(uo);
            DialogResponse response = userPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response)) {
                return (User) userPanel.getSelectedValue();
            } else if(userPanel.getSelectedValue() != null) {
                refreshAction();
            }
        }

        return null;
    }

    @Override
    protected User newRow() {
           return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (usersBindingGroup != null)
            usersBindingGroup.unbind();

        users = null;

        initData();

        return t;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(User rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(User rowObject) {
        return true;
    }
}