/*
 * BusinessPartnersListPanel.java
 *
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.contactbook.BusinessPartnersListRemote;
import com.cosmos.acacia.crm.bl.pricing.CustomerDiscountRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.CustomerDiscount;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnerTypeChooser.PartnerType;
import com.cosmos.acacia.crm.gui.pricing.CustomerDiscountForm;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaPanel;
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
    
    private CustomerDiscountRemote customerDiscountRemote = getBean(CustomerDiscountRemote.class);
    
    /** Creates new form BusinessPartnersListPanel */
    public BusinessPartnersListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        initComponentsCustom();
    }

    public BusinessPartnersListPanel(BigInteger parentDataObjectId, Classifier classifier) {
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
    private BusinessPartnersListRemote formSession;

    private BindingGroup bindGroup;
    private List<BusinessPartner> businessPartners;

    private EntityProperties entityProps;

    @Override
    protected void initData() {
        Classifier classifier = getClassifier(Classifier.Customer.getClassifierCode());
        System.out.println("classifier: " + classifier);
        List<BusinessPartner> businessPartners = getFormSession().getBusinessPartners(classifier);
        System.out.println("businessPartners: " + businessPartners);

        System.out.println("initData().1.getClassifier(): " + getClassifier());
        System.out.println("initData().1.getParentDataObjectId(): " + getParentDataObjectId());
        super.initData();

        System.out.println("initData().2.getClassifier(): " + getClassifier());
        System.out.println("initData().2.getParentDataObjectId(): " + getParentDataObjectId());

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
            System.out.println("getBusinessPartners().getClassifier(): " + getClassifier());
            System.out.println("getBusinessPartners().getParentDataObjectId(): " + getParentDataObjectId());
            businessPartners = getFormSession().getBusinessPartners(getParentDataObjectId());
            System.out.println("getBusinessPartners().businessPartners: " + businessPartners);
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
                AcaciaPanel formPanel = null;
                DialogResponse response = null;
                // organization
                if (PartnerType.ORGANIZATION.equals(partnerSelectForm.getSelectedType())) {
                    formPanel = new NewOrganizationDialog(
                            getParentDataObjectId(), getClassifier());
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
    
    private Classifier customerClassifier;

    @Override
    public boolean canModify(Object rowObject) {
        if ( customerClassifier==null ) {
            customerClassifier = getClassifiersFormSession().getClassifier(Classifier.Customer.getClassifierCode());
        }
        
        if ( rowObject instanceof DataObjectBean ){
            DataObjectBean bean = (DataObjectBean) rowObject;
            if ( getClassifiersFormSession().isClassifiedAs(bean, customerClassifier)){
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
    
    private static Classifier getClassifier(String classifierKey) {
        Classifier theClassifier = getClassifiersFormSession().getClassifier(classifierKey);
        return theClassifier;
    }
    
    private static BusinessPartnersListPanel createPartnerPanel(String classiferKey, BigInteger parentDataObjectId){
        Classifier supplierClassifier = getClassifier(classiferKey);
        BusinessPartnersListPanel listPanel = new BusinessPartnersListPanel(parentDataObjectId, supplierClassifier);
        return listPanel;
    }

    /**
     * Factory method - returns {@link BusinessPartnersListPanel} with applied classifier for 
     * providers
     * @param parentDataObjectId
     * @return
     */
    public static BusinessPartnersListPanel createSuppliersPanel(BigInteger parentDataObjectId) {
        return createPartnerPanel(Classifier.Supplier.getClassifierCode(), parentDataObjectId);
    }
    
    /**
     * Factory method - returns {@link BusinessPartnersListPanel} with applied classifier for 
     * customers ('customer')
     * @param parentDataObjectId
     * @return
     */
    public static BusinessPartnersListPanel createCustomerPanel(BigInteger parentDataObjectId) {
        return createPartnerPanel(Classifier.Customer.getClassifierCode(), parentDataObjectId);
    }
    
    /**
     * Factory method - returns {@link BusinessPartnersListPanel} with applied classifier for 
     * customers ('shippingAgent')
     * @param parentDataObjectId
     * @return
     */
    public static BusinessPartnersListPanel createShippingAgentPanel(BigInteger parentDataObjectId) {
        return createPartnerPanel(Classifier.ShippingAgent.getClassifierCode(), parentDataObjectId);
    }

    /**
     * Factory method - returns {@link BusinessPartnersListPanel} with applied classifier for 
     * producers ('producer')
     * @param parentDataObjectId
     * @return
     */
    public static BusinessPartnersListPanel createProducersPanel(BigInteger parentDataObjectId) {
        return createPartnerPanel(Classifier.Producer.getClassifierCode(), parentDataObjectId);
    }

    /**
     * Factory method - returns {@link BusinessPartnersListPanel} with applied classifier for 
     * employees ('employee')
     * @param parentDataObjectId
     * @return
     */
    public static BusinessPartnersListPanel createEmployeesPanel(BigInteger parentDataObjectId) {
        return createPartnerPanel(Classifier.Employee.getClassifierCode(), parentDataObjectId);
    }
}