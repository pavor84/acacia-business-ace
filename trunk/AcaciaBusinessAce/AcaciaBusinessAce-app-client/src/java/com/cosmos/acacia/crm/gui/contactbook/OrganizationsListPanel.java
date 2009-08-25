/*
 * OrganizationsListPanel.java
 *
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListRemote;
import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.client.LocalSession;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.gui.pricing.CustomerDiscountListPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.TablePanelListener;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.util.UUID;
import org.jdesktop.application.Task;

/**
 * Panel for listing existing organizations, giving CRUD options
 *
 * @author  Bozhidar Bozhanov
 */
public class OrganizationsListPanel extends AbstractTablePanel<Organization> {

    public OrganizationsListPanel() {
        this(LocalSession.instance().getOrganization().getId());
    }

    /** Creates new form OrganizationsListPanel */
    public OrganizationsListPanel(UUID parentDataObjectId) {
        super(parentDataObjectId);
        initComponentsCustom();
    }

    public OrganizationsListPanel(UUID parentDataObjectId, Classifier classifier) {
        super(parentDataObjectId);
        setClassifier(classifier);
        initComponentsCustom();
    }
    
    private void initComponentsCustom() {
        setSpecialButtonBehavior(true);
        getButton(Button.Special).setText(getResourceMap().getString("button.discount"));
        getButton(Button.Special).setToolTipText(getResourceMap().getString("button.discount.tooltip"));
        getButton(Button.Special).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDiscount();
            }
        });
    }

    protected void onDiscount() {
        BusinessPartner selected = (BusinessPartner) getDataTable().getSelectedRowObject();
        if (selected == null) {
            return;
        }
        CustomerDiscountListPanel customerDiscountForm = new CustomerDiscountListPanel(selected);
        customerDiscountForm.showFrame(this);
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
    protected boolean deleteRow(Organization rowObject) {
        if(rowObject != null)
        {
            deleteOrganization(rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Organization modifyRow(Organization rowObject) {
        if (rowObject != null) {
            OrganizationPanel organizationPanel = new OrganizationPanel((Organization) rowObject);
            DialogResponse response = organizationPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (Organization) organizationPanel.getSelectedValue();
            }

            if (DialogResponse.CLOSE.equals(response)) {
                refreshAction();
            }
        }

        return null;
    }

    @Override
    protected Organization newRow() {
        if (canNestedOperationProceed()) {
            Classifier classifier =
                    getClassifiersManager().getClassifier(Classifier.Customer.getClassifierCode());
            AcaciaPanel entityPanel = new NewOrganizationDialog(getParentDataObjectId(), classifier);
            DialogResponse response = entityPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response)) {
                return (Organization) entityPanel.getSelectedValue();
            }
        }

        return null;
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
    public boolean canModify(Organization rowObject) {
        if ( rowObject instanceof DataObjectBean ){
            DataObjectBean bean = (DataObjectBean) rowObject;
            if (getClassifiersManager().isClassifiedAs(bean, "customer")){
                setVisible(Button.Special, true);
            }else{
                setVisible(Button.Special, false);
            }
        }
        
        return true;
    }

    @Override
    public boolean canDelete(Organization rowObject) {
        return true;
    }
}