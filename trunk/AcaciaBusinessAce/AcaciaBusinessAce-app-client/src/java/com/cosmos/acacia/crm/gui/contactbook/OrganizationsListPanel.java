/*
 * OrganizationsListPanel.java
 *
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.impl.OrganizationsListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.settings.GeneralSettings;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Panel for listing existing organizations, giving CRUD options
 *
 * @author  Bozhidar Bozhanov
 */
public class OrganizationsListPanel extends AbstractTablePanel {

    /** Creates new form OrganizationsListPanel */
    public OrganizationsListPanel(DataObject parentDataObject)
    {
    	super(parentDataObject);
    }

    @EJB
    private OrganizationsListRemote formSession;

    private BindingGroup organizationsBindingGroup;
    private List<Organization> organizations;

    @Override
    protected void initData() {
        super.initData();
        setVisible(Button.Select, false);
        organizationsBindingGroup = new BindingGroup();
        AcaciaTable organizationsTable = getDataTable();
        JTableBinding tableBinding = organizationsTable.bind(organizationsBindingGroup, getOrganizations(), getOrganizationEntityProperties());
        organizationsTable.bindDatePickerCellEditor(
                organizationsBindingGroup,
                getOrganizationEntityProperties().getPropertyDetails("registrationDate"),
                GeneralSettings.getDateFormat());
        
        organizationsBindingGroup.bind();

        organizationsTable.setEditable(true);
    }

    protected List<Organization> getOrganizations()
    {
        if(organizations == null)
        {
            organizations = getFormSession().getOrganizations(getParentDataObject());
        }

        return organizations;
    }

    protected EntityProperties getOrganizationEntityProperties()
    {
        return getFormSession().getOrganizationEntityProperties();
    }

    protected OrganizationsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(OrganizationsListRemote.class.getName());
            }
            catch(Exception ex)
            {
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
        }

        return null;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed()) {
            OrganizationPanel organizationPanel = new OrganizationPanel(getParentDataObject());
            DialogResponse response = organizationPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return organizationPanel.getSelectedValue();
            }
        }
           return null;
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