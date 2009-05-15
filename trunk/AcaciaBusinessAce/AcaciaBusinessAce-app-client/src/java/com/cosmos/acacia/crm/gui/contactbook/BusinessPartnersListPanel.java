/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.contactbook;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.BusinessPartnersListRemote;
import com.cosmos.acacia.crm.bl.pricing.CustomerDiscountRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.ClassifiedObject;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.gui.ClassifiersListPanel;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnerTypeChooser.PartnerType;
import com.cosmos.acacia.crm.gui.pricing.CustomerDiscountListPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBColumn;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;

/**
 *
 * @author Miro
 */
public class BusinessPartnersListPanel extends AbstractTablePanel {

    private static final Logger LOGGER = Logger.getLogger(BusinessPartnersListPanel.class);

    @EJB
    private static BusinessPartnersListRemote formSession;
    @EJB
    private static CustomerDiscountRemote customerDiscountManager;
    private BindingGroup bindingGroup;
    private JBPanel topPanel;
    private AcaciaComboList comboList;
    private ClassifiersListPanel comboListPanel;
    private BindingGroup comboListBindingGroup;
    private ClassifiedObject classifiedObject;
    private Classifier partnerClassifier;
    private Classifier customerClassifier;
    private boolean initialized;
    private List<? extends BusinessPartner> businessPartnersStaticList;

    private List<JBColumn> customColumns;

    public BusinessPartnersListPanel() {
        this((Classifier)null);
    }

    public BusinessPartnersListPanel(Classifier partnerClassifier) {
        super(partnerClassifier);
        bindComponents();
    }

    public BusinessPartnersListPanel(List<? extends BusinessPartner> businessPartnersStaticList, List<JBColumn> customColumns) {
        this.businessPartnersStaticList = businessPartnersStaticList;
        this.customColumns = customColumns;
        bindComponents();
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
            if (rowObject instanceof Organization) {
                formPanel = new OrganizationPanel((Organization) rowObject);
                response = formPanel.showDialog(this);
            } //person
            else if (rowObject instanceof Person) {
                formPanel = new PersonPanel((Person) rowObject);
                response = formPanel.showDialog(this);
            }

            if (DialogResponse.SAVE.equals(response)) {
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

                if (DialogResponse.SAVE.equals(response)) {
                    BusinessPartner businessPartner = (BusinessPartner) formPanel.getSelectedValue();
                    Classifier classifier;
                    if ((classifier = getPartnerClassifier()) != null) {
                        getClassifiersManager().classifyDataObject(businessPartner.getDataObject(), classifier);
                    }

                    Classifier mainClassifier;
                    if((mainClassifier = (Classifier)getMainDataObject()) != null && !mainClassifier.equals(classifier)) {
                        getClassifiersManager().classifyDataObject(businessPartner.getDataObject(), mainClassifier);
                    }

                    return businessPartner;
                }
            }
        }
        return null;
    }

    @Override
    public boolean canSpecial(Object rowObject) {
        if (rowObject != null) {
            BusinessPartner businessPartner = (BusinessPartner)rowObject;
            if (getClassifiersManager().isClassifiedAs(businessPartner, getCustomerClassifier())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public DialogResponse showDialog(Component parentComponent) {
        Classifier classifier;
        if((classifier = (Classifier)getMainDataObject()) != null && !classifier.equals(getPartnerClassifier())) {
            partnerClassifier = classifier;
            comboList.setSelectedItem(partnerClassifier);
        }

        DialogResponse response = super.showDialog(parentComponent);

        if((classifier = (Classifier)getMainDataObject()) != null && !classifier.equals(getPartnerClassifier())) {
            partnerClassifier = classifier;
            comboList.setSelectedItem(partnerClassifier);
        }

        return response;
    }

    private Classifier getCustomerClassifier() {
        if(customerClassifier == null)
            customerClassifier = getClassifier(Classifier.Customer.getClassifierCode());

        return customerClassifier;
    }

    @Override
    protected void initData() {
        
    }
    
    protected void bindComponents(){
        Classifier classifier;
        if((classifier = (Classifier)getMainDataObject()) != null && partnerClassifier == null)
            partnerClassifier = classifier;

        if ((classifier = getPartnerClassifier()) != null) {
            setTitle(classifier.getClassifierName());
        }

        super.initData();


        setTopComponent(getTopPanel());

        bindingGroup = new BindingGroup();
        AcaciaTable citiesTable = getDataTable();
        JTableBinding tableBinding = 
            citiesTable.bind(bindingGroup, getBusinessPartners(), getEntityProperties(), null, customColumns, false);

        bindingGroup.bind();

        initComponentsCustom();

        initialized = true;
    }

    protected JBPanel getTopPanel() {
        if (topPanel == null) {
            topPanel = new JBPanel();
            topPanel.setLayout(new BorderLayout());
            comboList = new AcaciaComboList();
            comboList.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
            topPanel.add(comboList, BorderLayout.CENTER);
            JBLabel partnerClassiferLabel = new JBLabel();
            partnerClassiferLabel.setName("partnerClassifer");
            partnerClassiferLabel.setText(getResourceMap().getString("partnerClassifer.text"));
            partnerClassiferLabel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
            topPanel.add(partnerClassiferLabel, BorderLayout.WEST);

            classifiedObject = new ClassifiedObject();
            comboListBindingGroup = new BindingGroup();
            PropertyDetails propDetails = new PropertyDetails(
                    "classifier", "Classifier", Classifier.class.getName());
            propDetails.setColumnName("classifier");

            if (comboListPanel == null) {
                comboListPanel = new ClassifiersListPanel((BigInteger) null);
            }
            comboList.bind(comboListBindingGroup, comboListPanel, classifiedObject,
                    propDetails, "${classifierName}", UpdateStrategy.READ_WRITE);
            comboList.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    onPartnerClassifier((Classifier) e.getItem());
                }
            }, true);

            comboListBindingGroup.bind();

            Classifier classifier;
            if ((classifier = getPartnerClassifier()) != null) {
                comboList.setSelectedItem(classifier);
            }
        }

        return topPanel;
    }

    public Classifier getPartnerClassifier() {
        return partnerClassifier;
    }

    public void setPartnerClassifier(Classifier partnerClassifier) {
        if(!initialized)
            return;

        Classifier classifier;
        if((classifier = (Classifier)getMainDataObject()) != null) {
            boolean visible = classifier.equals(partnerClassifier);
            setVisible(Button.Select, visible);
            setVisible(Button.Unselect, visible);
        }

        this.partnerClassifier = partnerClassifier;
        refreshTable();
    }

    protected void onPartnerClassifier(Classifier partnerClassifier) {
        setPartnerClassifier(partnerClassifier);
    }

    protected void refreshTable() {
        AcaciaTable table = getDataTable();
        List data = table.getData();
        if (data != null) {
            data.clear();
            data.addAll(getBusinessPartners());
        }
    }

    protected List<? extends BusinessPartner> getBusinessPartners() {
        //if we have static list, don't query the service layer, just use the list
        if (businessPartnersStaticList!=null)
            return businessPartnersStaticList;
        //otherwise, refresh the list from service layer
        else
            return getFormSession().getBusinessPartners(getPartnerClassifier());
    }

    protected EntityProperties getEntityProperties() {
        return getFormSession().getListingEntityProperties();
    }

    protected BusinessPartnersListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(BusinessPartnersListRemote.class);
        }

        return formSession;
    }

    public static CustomerDiscountRemote getCustomerDiscountManager() {
        if (customerDiscountManager == null) {
            customerDiscountManager = getBean(CustomerDiscountRemote.class);
        }

        return customerDiscountManager;
    }

    private void initComponentsCustom() {
        setSpecialButtonBehavior(false);
        JButton button = getButton(Button.Special);
        button.setText(getResourceMap().getString("button.discount"));
        button.setToolTipText(getResourceMap().getString("button.discount.tooltip"));
        setVisible(Button.Special, true);
        setEnabled(Button.Special, false);
        specialBehaviourListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDiscount();
            }
        };
    }
    
    private ActionListener specialBehaviourListener=null;

    @Override
    public void specialAction() {
        if ( specialBehaviourListener!=null )
            specialBehaviourListener.actionPerformed(new ActionEvent(getButtonsMap().get(Button.Special), 1, "specialAction"));
    }
    
    public void setSpecialAction(ActionListener action){
        specialBehaviourListener = action;
    }

    protected void onDiscount() {
        BusinessPartner selected = (BusinessPartner) getDataTable().getSelectedRowObject();
        if (selected == null) {
            return;
        }

        CustomerDiscountListPanel panel = new CustomerDiscountListPanel(selected);
        panel.showDialog(this);
    }

    public List<? extends BusinessPartner> getBusinessPartnersStaticList() {
        return businessPartnersStaticList;
    }

    public void setBusinessPartnersStaticList(List<BusinessPartner> businessPartnersStaticList) {
        this.businessPartnersStaticList = businessPartnersStaticList;
        refreshTable();
    }

    public void setClassifierVisible(boolean visible) {
        if ( topPanel!=null )
            topPanel.setVisible(visible);
    }
}
