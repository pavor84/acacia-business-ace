/*
 * OrganizationsListPanel.java
 *
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListRemote;
import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.TablePanelListener;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.xhtml.XHTMLDialog;
import com.cosmos.swingb.xhtml.XHTMLUtils;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task;

/**
 * Panel for listing existing organizations, giving CRUD options
 *
 * @author  Bozhidar Bozhanov
 */
public class OrganizationsListPanel extends AbstractTablePanel {

    /** Creates new form OrganizationsListPanel */
    public OrganizationsListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }

    public OrganizationsListPanel(BigInteger parentDataObjectId, Classifier classifier) {
        super(parentDataObjectId);
        setClassifier(classifier);
    }

    @EJB
    private OrganizationsListRemote formSession;
    private UsersRemote adminSession;

    private BindingGroup organizationsBindingGroup;
    private List<Organization> organizations;

    /** Indicates whether the current form is administration form for managing organizations */
    private boolean isAdminView;

    @Override
    protected void initData() {
        
        // show admin options only if it is a frame, and not dialog
        if (getParentDataObjectId() == null && !isVisible(Button.Select))
            initAdminView();
        
        super.initData();
        
        organizationsBindingGroup = new BindingGroup();
        AcaciaTable organizationsTable = getDataTable();
        JTableBinding tableBinding = organizationsTable.bind(organizationsBindingGroup, getOrganizations(), getOrganizationEntityProperties());


        organizationsBindingGroup.bind();

        organizationsTable.setEditable(false);
    }

    private void initAdminView() {
        isAdminView = true;
        setSpecialCaption("activateOrganization.Action.text");
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
        if (isAdminView) {
            Organization org = (Organization) getDataTable().getSelectedRowObject();
            org = getAdminSession().activateOrganization(org, !org.isActive());
            getDataTable().replaceSelectedRow(org);
            fireModify(org);
            updateButtonCaption();
        }
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
        Organization org = (Organization) getDataTable().getSelectedRowObject();
        if (org != null) {
            if (org.isActive())
                setSpecialCaption("deactivateOrganization.Action.text");
            else
                setSpecialCaption("activateOrganization.Action.text");
        }
    }
    protected List<Organization> getOrganizations()
    {
        if(organizations == null)
        {
            organizations = getFormSession().getOrganizations(getParentDataObjectId());
        }

        return organizations;
    }

    protected EntityProperties getOrganizationEntityProperties()
    {
        return getFormSession().getOrganizationEntityProperties();
    }

    protected OrganizationsListRemote getFormSession()
    {
        if(formSession == null) {
            try {
                formSession = getBean(OrganizationsListRemote.class);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deleteOrganization(Organization organization)
    {
        return getFormSession().deleteOrganization(organization);
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        if(rowObject != null)
        {
            deleteOrganization((Organization) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            OrganizationPanel organizationPanel = new OrganizationPanel((Organization)rowObject);
            DialogResponse response = organizationPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return organizationPanel.getSelectedValue();
            }

            if(DialogResponse.CLOSE.equals(response)) {
                refreshAction();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed()) {
            DialogResponse response;
            BaseEntityPanel entityPanel;
            switch(getNewOrganizationType()) {
                case BasicForm:
                    response = null;
                    entityPanel = null;
                    break;

                case DetailedForms:
                    entityPanel = new OrganizationPanel(getParentDataObjectId());
                    response = entityPanel.showDialog(this);
                    break;

                default:
                    response = null;
                    entityPanel = null;
                    break;
            }
            if(entityPanel != null && DialogResponse.SAVE.equals(response))
            {
                return entityPanel.getSelectedValue();
            }
        }

        return null;
    }

    private enum NewOrganizationType {
        BasicForm,
        DetailedForms,
        None,
    }

    private NewOrganizationType getNewOrganizationType() {
        ResourceMap resourceMap = getResourceMap();
        String basicButtonName = resourceMap.getString("basicButton.text");
        String detailedButtonName = resourceMap.getString("detailedButton.text");
        String cancelButtonName = resourceMap.getString("cancelButton.text");
        String message1 = resourceMap.getString("newAction.Action.message1");
        String message2 = resourceMap.getString("newAction.Action.message2");
        String message3 = resourceMap.getString("newAction.Action.message3");
        String title = resourceMap.getString("newAction.Action.title");
        Object[] options = {
            basicButtonName,
            detailedButtonName,
            cancelButtonName};

        String message = XHTMLUtils.toString(
                message1,
                basicButtonName,
                message2,
                detailedButtonName,
                message3);
        int result = XHTMLDialog.showQuestionMessage(
                null,
                message,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                null, options, options[0]);

        switch(result) {
            case 0: return NewOrganizationType.BasicForm;
            case 1: return NewOrganizationType.DetailedForms;
            default: return NewOrganizationType.None;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (organizationsBindingGroup != null)
            organizationsBindingGroup.unbind();

        organizations = null;

        initData();

        return t;
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