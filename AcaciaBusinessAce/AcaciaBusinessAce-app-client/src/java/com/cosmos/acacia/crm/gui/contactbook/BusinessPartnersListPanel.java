/*
 * BusinessPartnersListPanel.java
 *
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.contactbook.BusinessPartnersListRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnerTypeChooser.PartnerType;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created : 26.07.2008
 * 
 * @author Petar Milev
 */
public class BusinessPartnersListPanel extends AbstractTablePanel {

    /** Creates new form BusinessPartnersListPanel */
    public BusinessPartnersListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }

    public BusinessPartnersListPanel(BigInteger parentDataObjectId, Classifier classifier) {
        super(parentDataObjectId);
        setClassifier(classifier);
    }

    @EJB
    private BusinessPartnersListRemote formSession;

    private BindingGroup bindGroup;
    private List<BusinessPartner> businessPartners;

    private EntityProperties entityProps;

    @Override
    protected void initData() {
        super.initData();

        setVisible(Button.Select, false);
        bindGroup = new BindingGroup();
        AcaciaTable table = getDataTable();

        if (entityProps == null) {
            entityProps = getFormSession().getListingEntityProperties();
        }
        table.bind(bindGroup, getBusinessPartners(), entityProps);

        bindGroup.bind();

        table.setEditable(false);
    }

    protected List<BusinessPartner> getBusinessPartners() {
        if (businessPartners == null) {
            businessPartners = getFormSession().getBusinessPartners(getParentDataObjectId());
        }

        return businessPartners;
    }

    protected BusinessPartnersListRemote getFormSession() {
        if (formSession == null) {
            try {
                formSession = getBean(BusinessPartnersListRemote.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        if (rowObject != null) {
            getFormSession().deleteBusinessPartner((BusinessPartner) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if (rowObject != null) {
            BaseEntityPanel formPanel = null;
            DialogResponse response = null;
            //organization
            if ( rowObject instanceof Organization ){
                formPanel = new OrganizationPanel((Organization)rowObject);
                response = formPanel.showDialog(this);
            }
            //person
            else if ( rowObject instanceof Person ){
                formPanel = new PersonPanel((Person)rowObject);
                response = formPanel.showDialog(this);
            }
            
            if(DialogResponse.SAVE.equals(response)){
                return formPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed()) {
            BusinessPartnerTypeChooser partnerSelectForm = new BusinessPartnerTypeChooser();
            DialogResponse resp = partnerSelectForm.showDialog(getButton(Button.New));
            if (DialogResponse.OK.equals(resp)) {
                BaseEntityPanel formPanel = null;
                DialogResponse response = null;
                // organization
                if (PartnerType.ORGANIZATION.equals(partnerSelectForm.getSelectedType())) {
                    formPanel = new OrganizationPanel(getParentDataObjectId());
                    response = formPanel.showDialog(this);
                //person
                } else {
                    formPanel = new PersonPanel(getParentDataObjectId());
                    response = formPanel.showDialog(this);
                }
                
                if(DialogResponse.SAVE.equals(response)){
                    return formPanel.getSelectedValue();
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (bindGroup != null)
            bindGroup.unbind();

        businessPartners = null;

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