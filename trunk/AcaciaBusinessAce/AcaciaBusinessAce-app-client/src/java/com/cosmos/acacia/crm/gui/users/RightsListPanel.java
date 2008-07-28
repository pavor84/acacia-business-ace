/*
 * OrganizationsListPanel.java
 *
 */

package com.cosmos.acacia.crm.gui.users;

import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.users.UserRightsRemote;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserRight;
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
public class RightsListPanel extends AbstractTablePanel {

    /** Creates new form OrganizationsListPanel */
    public RightsListPanel(BigInteger parentDataObjectId) {
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
    private Set<UserRight> rights;
    private User user;
    private UserGroup userGroup;
    private Type type;
    
    @Override
    protected void initData() {
        super.initData();

        setVisibleButtons(4 + 16 + 32);
    }


    protected void postInitData() {
        rightsBindingGroup = new BindingGroup();
        AcaciaTable usersTable = getDataTable();
        List<UserRight> initialRights = getRights();



        usersTable.bind(
                rightsBindingGroup,
                initialRights,
                getUserRightEntityProperties());

        rightsBindingGroup.bind();

        usersTable.setEditable(false);
    }

    private UserRightsRemote getFormSession() {
         if(formSession == null)
            formSession = getBean(UserRightsRemote.class);

        return formSession;
    }


    @SuppressWarnings("unchecked")
    protected List<UserRight> getRights()
    {
        if (rights == null) {
            
            if (user != null && user.getId() != null) {
                if (type == Type.GeneralRightsPanel)
                    rights = getFormSession().getUserRights(user);
                
                if (type == Type.SpecialPermissionsPanel)
                    rights = getFormSession().getSpecialPermissions(user);
            }
                

            if (userGroup != null && userGroup.getId() != null) {
                if (type == Type.GeneralRightsPanel)
                    rights = getFormSession().getUserRights(userGroup);
                
                if (type == Type.SpecialPermissionsPanel)
                    rights = getFormSession().getSpecialPermissions(userGroup);
            }
            
            if (rights == null)
                rights = Collections.EMPTY_SET;
            
            List<DataObjectType> dots = 
                    DataObjectTypesListPanel.shortenDataObjectTypeNames(getFormSession().getDataObjectTypes());
            
            for (UserRight right : rights) {
                right.setDataObjectType(dots.get(dots.indexOf(right.getDataObjectType())));
            }
        }
        
        return new LinkedList<UserRight>(rights);
    }

    /**
     * Specific logic for removing certain columns in different cases
     *
     * @return the modified entity properties
     */
    protected EntityProperties getUserRightEntityProperties()
    {
        EntityProperties entityProps = getFormSession().getUserRightEntityProperties();

        if (user == null)
            entityProps.removePropertyDetails("user");

        if (userGroup == null)
            entityProps.removePropertyDetails("userGroup");

        if (type == Type.GeneralRightsPanel)
            entityProps.removePropertyDetails("specialPermission");
        
        if (type == Type.SpecialPermissionsPanel) {
            entityProps.removePropertyDetails("read");
            entityProps.removePropertyDetails("create");
            entityProps.removePropertyDetails("modify");
            entityProps.removePropertyDetails("delete");
        }
        
        return entityProps;
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        rights.remove(rowObject);
        return true;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null && canNestedOperationProceed())
        {
            AcaciaPanel panel = null;
            if (type == Type.GeneralRightsPanel)
                panel = new RightsPanel((UserRight) rowObject);
            
            if (type == Type.SpecialPermissionsPanel)
                panel = new SpecialPermissionPanel((UserRight) rowObject);
            
            DialogResponse response = panel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return panel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed()) {
            UserRight right = new UserRight();
            if (user != null)
                right.setUser(user);

            if (userGroup != null)
                right.setUserGroup(userGroup);

            
            AcaciaPanel panel = null;
            if (type == Type.GeneralRightsPanel)
                panel = new RightsPanel(right);
            
            if (type == Type.SpecialPermissionsPanel)
                panel = new SpecialPermissionPanel(right);
            
            DialogResponse response = panel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                UserRight result  = (UserRight) panel.getSelectedValue();
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

        if (rightsBindingGroup!= null)
            rightsBindingGroup.unbind();

        rights = null;

        postInitData();

        return t;
    }

    public void flushRights() {
        log.info("Flushed");
        if (user != null)
            getFormSession().assignRightsToUser(rights, user);

        if (userGroup != null)
            getFormSession().assignRightsToGroup(rights, userGroup);
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(Object rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(Object rowObject) {
        return true;
    }
}