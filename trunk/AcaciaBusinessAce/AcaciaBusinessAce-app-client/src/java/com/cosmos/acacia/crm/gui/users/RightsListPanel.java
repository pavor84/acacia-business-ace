/*
 * OrganizationsListPanel.java
 *
 */
package com.cosmos.acacia.crm.gui.users;

import java.util.UUID;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.users.UserRightsRemote;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.users.Right;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserGroup;
import com.cosmos.acacia.crm.data.users.UserRight;
import com.cosmos.acacia.crm.gui.DataObjectTypesListPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Panel for listing existing organizations, giving CRUD options
 *
 * @author  Bozhidar Bozhanov
 */
public class RightsListPanel extends AbstractTablePanel<Right> {

    /** Creates new form OrganizationsListPanel */
    public RightsListPanel(UUID parentDataObjectId) {
        super(parentDataObjectId);
    }

    public static enum Type {

        GeneralRightsPanel,
        SpecialPermissionsPanel
    }

    public RightsListPanel(User user, Type type) {
        super(user.getId());
        this.user = user;
        this.type = type;
        postInitData();
    }

    public RightsListPanel(UserGroup userGroup, Type type) {
        super(userGroup.getId());
        this.userGroup = userGroup;
        this.type = type;
        postInitData();
    }
    @EJB
    private UserRightsRemote formSession;
    private BindingGroup rightsBindingGroup;
    private Set<Right> rights;
    private User user;
    private UserGroup userGroup;
    private Type type;

    @Override
    protected void initData() {
        super.initData();

        setVisibleButtons(ButtonVisibility.Modify.getVisibilityIndex() |
                ButtonVisibility.Refresh.getVisibilityIndex() |
                ButtonVisibility.Close.getVisibilityIndex());
    }

    protected void postInitData() {
        rightsBindingGroup = new BindingGroup();
        AcaciaTable usersTable = getDataTable();
        List<Right> initialRights = getRights();

        usersTable.bind(
                rightsBindingGroup,
                initialRights,
                getUserRightEntityProperties());

        rightsBindingGroup.bind();

        usersTable.setEditable(false);
    }

    private UserRightsRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(UserRightsRemote.class, false);
        }

        return formSession;
    }

    protected List<Right> getRights() {
        if (rights == null) {
            if (user != null && user.getId() != null) {
                if (type == Type.GeneralRightsPanel) {
                    rights = getFormSession().getRights(user);
                }

                if (type == Type.SpecialPermissionsPanel) {
                    rights = getFormSession().getSpecialPermissions(user);
                }
            }

            if (userGroup != null && userGroup.getId() != null) {
                if (type == Type.GeneralRightsPanel) {
                    rights = getFormSession().getRights(userGroup);
                }

                if (type == Type.SpecialPermissionsPanel) {
                    rights = getFormSession().getSpecialPermissions(userGroup);
                }
            }

            if (rights == null) {
                rights = new HashSet<Right>();
            }

            List<DataObjectType> dots =
                    DataObjectTypesListPanel.shortenDataObjectTypeNames(getFormSession().getDataObjectTypes());

            for (Right right : rights) {
                if (right.getDataObjectType() != null) {
                    right.setDataObjectType(dots.get(dots.indexOf(right.getDataObjectType())));
                }
            }
        }

        return new LinkedList<Right>(rights);
    }

    /**
     * Specific logic for removing certain columns in different cases
     *
     * @return the modified entity properties
     */
    protected EntityProperties getUserRightEntityProperties() {
        EntityProperties entityProps = getFormSession().getUserRightEntityProperties();

        if (user == null) {
            entityProps.removeEntityProperty("user");
        }

        if (userGroup == null) {
            entityProps.removeEntityProperty("userGroup");
        }

        if (type == Type.GeneralRightsPanel) {
            entityProps.removeEntityProperty("specialPermission");
        }

        if (type == Type.SpecialPermissionsPanel) {
            entityProps.removeEntityProperty("read");
            entityProps.removeEntityProperty("create");
            entityProps.removeEntityProperty("modify");
            entityProps.removeEntityProperty("delete");
        }

        return entityProps;
    }

    @Override
    protected boolean deleteRow(Right rowObject) {
        rights.remove(rowObject);
        return true;
    }

    @SuppressWarnings("null")
    @Override
    protected Right modifyRow(Right rowObject) {
        if (rowObject != null && canNestedOperationProceed()) {
            AcaciaPanel panel = null;
            if (type == Type.GeneralRightsPanel) {
                panel = new RightsPanel((UserRight) rowObject);
            }

            if (type == Type.SpecialPermissionsPanel) {
                panel = new SpecialPermissionPanel((UserRight) rowObject);
            }

            DialogResponse response = panel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (Right) panel.getSelectedValue();
            }
        }

        return null;
    }

    @SuppressWarnings("null")
    @Override
    protected Right newRow() {
        if (canNestedOperationProceed()) {
            Right right;
            if (user != null) {
                right = getFormSession().newRight(user);
            } else if (userGroup != null) {
                right = getFormSession().newRight(userGroup);
            } else {
                throw new RuntimeException("Can not create new Right. User or UserGroup must be initialized.");
            }

            AcaciaPanel panel = null;
            if (type == Type.GeneralRightsPanel) {
                panel = new RightsPanel(right);
            }

            if (type == Type.SpecialPermissionsPanel) {
                panel = new SpecialPermissionPanel(right);
            }

            DialogResponse response = panel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                Right result = (Right) panel.getSelectedValue();

                rights.add(result);
                return right;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (rightsBindingGroup != null) {
            rightsBindingGroup.unbind();
        }

        rights = null;

        postInitData();

        return t;
    }

    public void flushRights() {
        log.info(rights.size() + " rights flushed");
        if (type == Type.GeneralRightsPanel) {
            if (user != null) {
                getFormSession().assignRightsToUser(rights, user);
            }

            if (userGroup != null) {
                getFormSession().assignRightsToGroup(rights, userGroup);
            }
        }

        if (type == Type.SpecialPermissionsPanel) {
            if (user != null) {
                getFormSession().assignSpecialPermissionsToUser(rights, user);
            }

            if (userGroup != null) {
                getFormSession().assignSpecialPermissionsToGroup(rights, userGroup);
            }
        }
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(Right rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(Right rowObject) {
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
