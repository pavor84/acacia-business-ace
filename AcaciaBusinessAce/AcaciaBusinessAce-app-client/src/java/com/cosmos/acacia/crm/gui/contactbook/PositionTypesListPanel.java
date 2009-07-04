/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.contactbook;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.PositionTypesListRemote;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AbstractTreeEnabledTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class PositionTypesListPanel extends AbstractTreeEnabledTablePanel<PositionType> {

    protected static Logger log = Logger.getLogger(PositionTypesListPanel.class);

    /** Creates new form AddresssListPanel */
    public PositionTypesListPanel(BigInteger parentDataObjectId, boolean isInternal) {
        super(parentDataObjectId);
        try {
            if (getAcaciaSession().getOrganization().getId().equals(parentDataObjectId)) {
                this.ownerClass = Organization.class;
            } else {
                DataObject parentDataObject = getParentDataObject();
                if (parentDataObject != null) {
                    this.ownerClass = Class.forName(
                            parentDataObject.getDataObjectType().getDataObjectType());
                }
            }
        } catch (Exception ex) {
            log.error("constructor", ex);
        }

        this.isInternal = isInternal;
        postInitData();
    }

    public PositionTypesListPanel(Class ownerClass, BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        this.ownerClass = ownerClass;
        postInitData();
    }
    @EJB
    private PositionTypesListRemote formSession;
    private BindingGroup positionTypesBindingGroup;
    private List<PositionType> positionTypes;
    private ContactPerson contactPerson;
    private Class ownerClass;
    private boolean isInternal;

    @Override
    protected void initData() {

        super.initData();
    }

    protected void postInitData() {
        positionTypesBindingGroup = new BindingGroup();
        String key = ownerClass == Person.class ? "title.positions.person" : "title.positions.organization";

        setTitle(getResourceMap().getString(key));

        AcaciaTable positionTypesTable = getDataTable();
        try {
            JTableBinding tableBinding = positionTypesTable.bind(positionTypesBindingGroup, getPositionTypes(), getPositionTypeEntityProperties());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        positionTypesTable.setEditable(false);
        positionTypesBindingGroup.bind();
    }

    protected List<PositionType> getPositionTypes() throws Exception {
        if (positionTypes == null) {

            if (!isInternal) {
                positionTypes = getFormSession().getPositionTypes(ownerClass, getOrganizationDataObjectId());
            } else {
                positionTypes = getFormSession().getInternalOrganizationPositionTypes(getOrganizationDataObjectId());
            }
        }

        return positionTypes;
    }

    protected EntityProperties getPositionTypeEntityProperties() {
        return getFormSession().getPositionTypeEntityProperties();
    }

    protected PositionTypesListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(PositionTypesListRemote.class);
        }

        return formSession;
    }

    protected int deletePositionType(PositionType positionType) {
        return getFormSession().deletePositionType(positionType);
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Override
    @Action
    public void selectAction() {
        super.selectAction();
        //
    }

    @Override
    protected boolean deleteRow(PositionType rowObject) {
        if (rowObject != null) {
            List<PositionType> withSubPositions = null;
            if (isInternal) {
                withSubPositions = getWithSubCategories(rowObject);
            }

            try {
                if (isInternal) {
                    getFormSession().deletePositionTypes(withSubPositions);
                    removeFromTable(withSubPositions);
                } else {
                    deletePositionType(rowObject);
                }

                return true;
            } catch (Exception e) {
                ValidationException ve = extractValidationException(e);
                if (ve != null) {
                    String messagePrefix = null;
                    if (withSubPositions.size() > 1) {
                        messagePrefix = getResourceMap().getString("TreeItem.err.cantDeleteMany");
                    } else {
                        messagePrefix = getResourceMap().getString("deleteAction.err.referenced");
                    }

                    String message = getTableReferencedMessage(messagePrefix, ve.getMessage());

                    JOptionPane.showMessageDialog(this,
                            message,
                            getResourceMap().getString("ProductCategory.err.cantDeleteTitle"),
                            JOptionPane.DEFAULT_OPTION);
                } else {
                    log.error(e);
                }
            }

        }
        return false;
    }

    @Override
    protected PositionType modifyRow(PositionType rowObject) {
        if (rowObject != null) {
            PositionTypePanel positionTypePanel =
                    new PositionTypePanel(rowObject, ownerClass);
            positionTypePanel.setInternal(isInternal);

            DialogResponse response = positionTypePanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (PositionType) positionTypePanel.getSelectedValue();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (positionTypesBindingGroup != null) {
            positionTypesBindingGroup.unbind();
        }

        refreshDataTable();

        return t;
    }

    @Override
    protected PositionType newRow() {
        PositionTypePanel positionTypePanel =
                new PositionTypePanel(getOrganizationDataObjectId(), ownerClass);

        positionTypePanel.setInternal(isInternal);

        if (isInternal) {
            PositionType autoParent = null;
            TreePath selection = getTree().getSelectionPath();
            if (selection != null) {
                DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) selection.getLastPathComponent();
                Object userObject;
                if ((userObject = selNode.getUserObject()) instanceof PositionType) {
                    autoParent = (PositionType) userObject;
                }

                positionTypePanel.setParentPosition(autoParent);
            }
        }

        DialogResponse response = positionTypePanel.showDialog(this);
        if (DialogResponse.SAVE.equals(response)) {
            return (PositionType) positionTypePanel.getSelectedValue();
        }
        return null;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(PositionType rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(PositionType rowObject) {
        return true;
    }

    @Override
    protected List<PositionType> getItems() {
        return getFormSession().getInternalOrganizationPositionTypes(getOrganizationDataObjectId());
    }

    @Override
    protected PositionType onEditEntity(PositionType entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void refreshDataTable() {
        positionTypes = getLister().getList();
        postInitData();
    }
}
