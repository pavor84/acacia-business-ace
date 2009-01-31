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
import com.cosmos.acacia.crm.bl.pricing.CustomerDiscountRemote;
import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.CustomerDiscount;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.gui.pricing.CustomerDiscountForm;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaPanel;
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
    
    private CustomerDiscountRemote customerDiscountRemote = getBean(CustomerDiscountRemote.class);

    /** Creates new form OrganizationsListPanel */
    public OrganizationsListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        initComponentsCustom();
    }

    public OrganizationsListPanel(BigInteger parentDataObjectId, Classifier classifier) {
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
        if ( selected==null )
            return;
        CustomerDiscount customerDiscount = customerDiscountRemote.getCustomerDiscountForCustomer(selected);
        CustomerDiscountForm customerDiscountForm = new CustomerDiscountForm(customerDiscount);
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
            Classifier classifier =
                    getClassifiersManager().getClassifier(Classifier.Customer.getClassifierCode());
            AcaciaPanel entityPanel = new NewOrganizationDialog(getParentDataObjectId(), classifier);
            DialogResponse response = entityPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response)) {
                return entityPanel.getSelectedValue();
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
    public boolean canModify(Object rowObject) {
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
    public boolean canDelete(Object rowObject) {
        return true;
    }
}