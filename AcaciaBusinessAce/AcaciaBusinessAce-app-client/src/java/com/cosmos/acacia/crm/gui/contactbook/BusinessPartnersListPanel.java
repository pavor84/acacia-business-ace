/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.contactbook;

import com.cosmos.acacia.crm.bl.contactbook.BusinessPartnersListRemote;
import com.cosmos.acacia.crm.bl.pricing.CustomerDiscountRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.ClassifiedObject;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.CustomerDiscount;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.gui.ClassifiersListPanel;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnerTypeChooser.PartnerType;
import com.cosmos.acacia.crm.gui.pricing.CustomerDiscountForm;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.BorderFactory;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Miro
 */
public class BusinessPartnersListPanel extends AbstractTablePanel {

    @EJB
    private static BusinessPartnersListRemote formSession;
    @EJB
    private static CustomerDiscountRemote customerDiscountManager;
    private Classifier partnerClassifier;
    private BindingGroup bindingGroup;
    private JBPanel topPanel;
    private AcaciaComboList comboList;
    private ClassifiersListPanel comboListPanel;
    private BindingGroup comboListBindingGroup;
    private ClassifiedObject classifiedObject;
    private Classifier customerClassifier;

    public BusinessPartnersListPanel() {
        this(null);
    }

    public BusinessPartnersListPanel(Classifier partnerClassifier) {
        this.partnerClassifier = partnerClassifier;
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
                    if (partnerClassifier != null) {
                        getClassifiersManager().classifyDataObject(businessPartner.getDataObject(), partnerClassifier);
                    }

                    return businessPartner;
                }
            }
        }
        return null;
    }

    @Override
    public boolean canModify(Object rowObject) {
        if (rowObject != null) {
            BusinessPartner businessPartner = (BusinessPartner)rowObject;
            if (getClassifiersManager().isClassifiedAs(businessPartner, getCustomerClassifier())) {
                setVisible(Button.Special, true);
            } else {
                setVisible(Button.Special, false);
            }
        }

        return true;
    }

    private Classifier getCustomerClassifier() {
        if(customerClassifier == null)
            customerClassifier = getClassifier(Classifier.Customer.getClassifierCode());

        return customerClassifier;
    }

    @Override
    protected void initData() {
        super.initData();

        setTopComponent(getTopPanel());

        if (partnerClassifier != null) {
            setTitle(partnerClassifier.getClassifierName());
        }

        bindingGroup = new BindingGroup();
        AcaciaTable citiesTable = getDataTable();
        JTableBinding tableBinding = citiesTable.bind(bindingGroup, getBusinessPartners(), getEntityProperties());

        bindingGroup.bind();

        initComponentsCustom();
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
        }

        return topPanel;
    }

    public Classifier getPartnerClassifier() {
        return partnerClassifier;
    }

    public void setPartnerClassifier(Classifier partnerClassifier) {
        this.partnerClassifier = partnerClassifier;
        setEnabled(Button.New, partnerClassifier != null);
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

    protected List<BusinessPartner> getBusinessPartners() {
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
        setSpecialButtonBehavior(true);
        getButton(Button.Special).setText(getResourceMap().getString("button.discount"));
        getButton(Button.Special).setToolTipText(getResourceMap().getString("button.discount.tooltip"));
        getButton(Button.Special).addActionListener(new ActionListener() {

            @Override
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

        CustomerDiscount customerDiscount = getCustomerDiscountManager().getCustomerDiscountForCustomer(selected);
        CustomerDiscountForm customerDiscountForm = new CustomerDiscountForm(customerDiscount);
        customerDiscountForm.showFrame(this);
    }
}
