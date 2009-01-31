/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BasicOrganizationPanel.java
 *
 * Created on 2009-1-17, 14:19:13
 */
package com.cosmos.acacia.crm.gui.contactbook;

import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListRemote;
import com.cosmos.acacia.crm.data.BasicOrganization;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import javax.ejb.EJB;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author Miro
 */
public class BasicOrganizationPanel
        extends BaseEntityPanel
{

    protected static Logger log = Logger.getLogger(BasicOrganizationPanel.class);

    @EJB
    private static OrganizationsListRemote formSession;

    /** Creates new form organizationPanel */
    public BasicOrganizationPanel(BigInteger parentDataObjectId, Classifier classifier) {
        super(parentDataObjectId);
        this.classifier = classifier;
        init();
    }

    public BasicOrganizationPanel() {
        super(null);
        init();
    }

    @Override
    protected void init()
    {
        initComponents();
        super.init();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        organizationNameLabel = new com.cosmos.swingb.JBLabel();
        organizationNameTextField = new com.cosmos.swingb.JBTextField();
        nicknameTextField = new com.cosmos.swingb.JBTextField();
        nicknameLabel = new com.cosmos.swingb.JBLabel();
        vatNumberLabel = new com.cosmos.swingb.JBLabel();
        vatNumberTextField = new com.cosmos.swingb.JBTextField();
        uniqueIdTextField = new com.cosmos.swingb.JBTextField();
        uniqueIdLabel = new com.cosmos.swingb.JBLabel();
        registrationAddressPanel = new com.cosmos.swingb.JBPanel();
        cityComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        postalCodeTextField = new com.cosmos.swingb.JBTextField();
        postalCodeLabel = new com.cosmos.swingb.JBLabel();
        postalAddressPanel = new com.cosmos.swingb.JBPanel();
        postalAddressScrollPane = new javax.swing.JScrollPane();
        postalAddressTextPane = new com.cosmos.swingb.JBTextPane();
        cityLabel = new com.cosmos.swingb.JBLabel();
        jBPanel1 = new com.cosmos.swingb.JBPanel();
        secondNameTextField = new com.cosmos.swingb.JBTextField();
        secondNameLabel = new com.cosmos.swingb.JBLabel();
        firstNameLabel = new com.cosmos.swingb.JBLabel();
        firstNameTextField = new com.cosmos.swingb.JBTextField();
        lastNameTextField = new com.cosmos.swingb.JBTextField();
        extraNameTextField = new com.cosmos.swingb.JBTextField();
        lastNameLabel = new com.cosmos.swingb.JBLabel();
        extraNameLabel = new com.cosmos.swingb.JBLabel();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        jBPanel2 = new com.cosmos.swingb.JBPanel();
        customerCheckBox = new com.cosmos.swingb.JBCheckBox();
        supplierCheckBox = new com.cosmos.swingb.JBCheckBox();
        producerCheckBox = new com.cosmos.swingb.JBCheckBox();
        shippingAgentCheckBox = new com.cosmos.swingb.JBCheckBox();
        courierCheckBox = new com.cosmos.swingb.JBCheckBox();
        bankCheckBox = new com.cosmos.swingb.JBCheckBox();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(BasicOrganizationPanel.class);
        organizationNameLabel.setText(resourceMap.getString("organizationNameLabel.text")); // NOI18N
        organizationNameLabel.setName("organizationNameLabel"); // NOI18N

        organizationNameTextField.setColumns(10);
        organizationNameTextField.setName("organizationNameTextField"); // NOI18N

        nicknameTextField.setColumns(10);
        nicknameTextField.setName("nicknameTextField"); // NOI18N

        nicknameLabel.setText(resourceMap.getString("nicknameLabel.text")); // NOI18N
        nicknameLabel.setName("nicknameLabel"); // NOI18N

        vatNumberLabel.setText(resourceMap.getString("vatNumberLabel.text")); // NOI18N
        vatNumberLabel.setName("vatNumberLabel"); // NOI18N

        vatNumberTextField.setColumns(10);
        vatNumberTextField.setName("vatNumberTextField"); // NOI18N

        uniqueIdTextField.setColumns(10);
        uniqueIdTextField.setName("uniqueIdTextField"); // NOI18N

        uniqueIdLabel.setText(resourceMap.getString("uniqueIdLabel.text")); // NOI18N
        uniqueIdLabel.setName("uniqueIdLabel"); // NOI18N

        registrationAddressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("registrationAddressPanel.border.title"))); // NOI18N
        registrationAddressPanel.setName("registrationAddressPanel"); // NOI18N

        cityComboList.setName("cityComboList"); // NOI18N

        postalCodeTextField.setColumns(10);
        postalCodeTextField.setName("postalCodeTextField"); // NOI18N

        postalCodeLabel.setText(resourceMap.getString("postalCodeLabel.text")); // NOI18N
        postalCodeLabel.setName("postalCodeLabel"); // NOI18N

        postalAddressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("postalAddressPanel.border.title"))); // NOI18N
        postalAddressPanel.setName("postalAddressPanel"); // NOI18N
        postalAddressPanel.setLayout(new java.awt.BorderLayout());

        postalAddressScrollPane.setName("postalAddressScrollPane"); // NOI18N

        postalAddressTextPane.setName("postalAddressTextPane"); // NOI18N
        postalAddressScrollPane.setViewportView(postalAddressTextPane);

        postalAddressPanel.add(postalAddressScrollPane, java.awt.BorderLayout.CENTER);

        cityLabel.setText(resourceMap.getString("cityLabel.text")); // NOI18N
        cityLabel.setName("cityLabel"); // NOI18N

        javax.swing.GroupLayout registrationAddressPanelLayout = new javax.swing.GroupLayout(registrationAddressPanel);
        registrationAddressPanel.setLayout(registrationAddressPanelLayout);
        registrationAddressPanelLayout.setHorizontalGroup(
            registrationAddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registrationAddressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(registrationAddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(postalAddressPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addGroup(registrationAddressPanelLayout.createSequentialGroup()
                        .addComponent(cityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cityComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(postalCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(postalCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        registrationAddressPanelLayout.setVerticalGroup(
            registrationAddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registrationAddressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(registrationAddressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(postalCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(postalCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityComboList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postalAddressPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );

        jBPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jBPanel1.border.title"))); // NOI18N
        jBPanel1.setName("jBPanel1"); // NOI18N

        secondNameTextField.setColumns(10);
        secondNameTextField.setName("secondNameTextField"); // NOI18N

        secondNameLabel.setText(resourceMap.getString("secondNameLabel.text")); // NOI18N
        secondNameLabel.setName("secondNameLabel"); // NOI18N

        firstNameLabel.setText(resourceMap.getString("firstNameLabel.text")); // NOI18N
        firstNameLabel.setName("firstNameLabel"); // NOI18N

        firstNameTextField.setColumns(10);
        firstNameTextField.setName("firstNameTextField"); // NOI18N

        lastNameTextField.setColumns(10);
        lastNameTextField.setName("lastNameTextField"); // NOI18N

        extraNameTextField.setColumns(10);
        extraNameTextField.setName("extraNameTextField"); // NOI18N

        lastNameLabel.setText(resourceMap.getString("lastNameLabel.text")); // NOI18N
        lastNameLabel.setName("lastNameLabel"); // NOI18N

        extraNameLabel.setText(resourceMap.getString("extraNameLabel.text")); // NOI18N
        extraNameLabel.setName("extraNameLabel"); // NOI18N

        javax.swing.GroupLayout jBPanel1Layout = new javax.swing.GroupLayout(jBPanel1);
        jBPanel1.setLayout(jBPanel1Layout);
        jBPanel1Layout.setHorizontalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(firstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(secondNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extraNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(extraNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(secondNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addContainerGap())
        );
        jBPanel1Layout.setVerticalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secondNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secondNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extraNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extraNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        jBPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jBPanel2.border.title"))); // NOI18N
        jBPanel2.setName("jBPanel2"); // NOI18N
        jBPanel2.setLayout(new java.awt.GridLayout(2, 0));

        customerCheckBox.setText(resourceMap.getString("customerCheckBox.text")); // NOI18N
        customerCheckBox.setName("customerCheckBox"); // NOI18N
        jBPanel2.add(customerCheckBox);

        supplierCheckBox.setText(resourceMap.getString("supplierCheckBox.text")); // NOI18N
        supplierCheckBox.setName("supplierCheckBox"); // NOI18N
        jBPanel2.add(supplierCheckBox);

        producerCheckBox.setText(resourceMap.getString("producerCheckBox.text")); // NOI18N
        producerCheckBox.setName("producerCheckBox"); // NOI18N
        jBPanel2.add(producerCheckBox);

        shippingAgentCheckBox.setText(resourceMap.getString("shippingAgentCheckBox.text")); // NOI18N
        shippingAgentCheckBox.setName("shippingAgentCheckBox"); // NOI18N
        jBPanel2.add(shippingAgentCheckBox);

        courierCheckBox.setText(resourceMap.getString("courierCheckBox.text")); // NOI18N
        courierCheckBox.setName("courierCheckBox"); // NOI18N
        jBPanel2.add(courierCheckBox);

        bankCheckBox.setText(resourceMap.getString("bankCheckBox.text")); // NOI18N
        bankCheckBox.setName("bankCheckBox"); // NOI18N
        jBPanel2.add(bankCheckBox);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                    .addComponent(registrationAddressPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(organizationNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vatNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(organizationNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                            .addComponent(vatNumberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(nicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nicknameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(uniqueIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(uniqueIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(organizationNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nicknameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(organizationNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vatNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uniqueIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uniqueIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vatNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registrationAddressPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBCheckBox bankCheckBox;
    private com.cosmos.acacia.gui.AcaciaComboList cityComboList;
    private com.cosmos.swingb.JBLabel cityLabel;
    private com.cosmos.swingb.JBCheckBox courierCheckBox;
    private com.cosmos.swingb.JBCheckBox customerCheckBox;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.swingb.JBLabel extraNameLabel;
    private com.cosmos.swingb.JBTextField extraNameTextField;
    private com.cosmos.swingb.JBLabel firstNameLabel;
    private com.cosmos.swingb.JBTextField firstNameTextField;
    private com.cosmos.swingb.JBPanel jBPanel1;
    private com.cosmos.swingb.JBPanel jBPanel2;
    private com.cosmos.swingb.JBLabel lastNameLabel;
    private com.cosmos.swingb.JBTextField lastNameTextField;
    private com.cosmos.swingb.JBLabel nicknameLabel;
    private com.cosmos.swingb.JBTextField nicknameTextField;
    private com.cosmos.swingb.JBLabel organizationNameLabel;
    private com.cosmos.swingb.JBTextField organizationNameTextField;
    private com.cosmos.swingb.JBPanel postalAddressPanel;
    private javax.swing.JScrollPane postalAddressScrollPane;
    private com.cosmos.swingb.JBTextPane postalAddressTextPane;
    private com.cosmos.swingb.JBLabel postalCodeLabel;
    private com.cosmos.swingb.JBTextField postalCodeTextField;
    private com.cosmos.swingb.JBCheckBox producerCheckBox;
    private com.cosmos.swingb.JBPanel registrationAddressPanel;
    private com.cosmos.swingb.JBLabel secondNameLabel;
    private com.cosmos.swingb.JBTextField secondNameTextField;
    private com.cosmos.swingb.JBCheckBox shippingAgentCheckBox;
    private com.cosmos.swingb.JBCheckBox supplierCheckBox;
    private com.cosmos.swingb.JBLabel uniqueIdLabel;
    private com.cosmos.swingb.JBTextField uniqueIdTextField;
    private com.cosmos.swingb.JBLabel vatNumberLabel;
    private com.cosmos.swingb.JBTextField vatNumberTextField;
    // End of variables declaration//GEN-END:variables


    private Classifier classifier;
    private BasicOrganization basicOrganization;
    private BindingGroup bindingGroup;
    private CitiesListPanel citiesListPanel;

    @Override
    public void performSave(boolean closeAfter) {
        Organization organization = getFormSession().saveBasicOrganization(basicOrganization);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(organization);
        if(closeAfter)
            close();
    }

    @Override
    public BindingGroup getBindingGroup() {
        return bindingGroup;
    }

    @Override
    public Object getEntity() {
        return basicOrganization;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    @Override
    protected void initData() {
        log.info("initData().parentDataObjectId: " + getParentDataObjectId());

        basicOrganization = new BasicOrganization();

        initClassifier();

        if (bindingGroup == null)
            bindingGroup = new BindingGroup();

        EntityProperties entityProps = getFormSession().getBasicOrganizationEntityProperties();
        PropertyDetails propDetails;

        propDetails = entityProps.getPropertyDetails("organizationName");
        organizationNameTextField.bind(bindingGroup, basicOrganization, propDetails);

        propDetails = entityProps.getPropertyDetails("nickname");
        nicknameTextField.bind(bindingGroup, basicOrganization, propDetails);

        propDetails = entityProps.getPropertyDetails("vatNumber");
        vatNumberTextField.bind(bindingGroup, basicOrganization, propDetails);

        propDetails = entityProps.getPropertyDetails("uniqueIdentifierCode");
        uniqueIdTextField.bind(bindingGroup, basicOrganization, propDetails);

        // City
        if(citiesListPanel == null)
            citiesListPanel = new CitiesListPanel();
        propDetails = entityProps.getPropertyDetails("city");
        cityComboList.bind(bindingGroup, citiesListPanel, basicOrganization,
                propDetails, "${cityName}", UpdateStrategy.READ_WRITE);

        // PostalCode
        propDetails = entityProps.getPropertyDetails("postalCode");
        postalCodeTextField.bind(bindingGroup, basicOrganization, propDetails);

        // PostalAddress
        propDetails = entityProps.getPropertyDetails("postalAddress");
        postalAddressTextPane.bind(bindingGroup, basicOrganization, propDetails);

        // FirstName
        propDetails = entityProps.getPropertyDetails("firstName");
        firstNameTextField.bind(bindingGroup, basicOrganization, propDetails);

        // SecondName
        propDetails = entityProps.getPropertyDetails("secondName");
        secondNameTextField.bind(bindingGroup, basicOrganization, propDetails);

        // LastName
        propDetails = entityProps.getPropertyDetails("lastName");
        lastNameTextField.bind(bindingGroup, basicOrganization, propDetails);

        // ExtraName
        propDetails = entityProps.getPropertyDetails("extraName");
        extraNameTextField.bind(bindingGroup, basicOrganization, propDetails);

        propDetails = entityProps.getPropertyDetails("customer");
        customerCheckBox.bind(bindingGroup, basicOrganization, propDetails);

        propDetails = entityProps.getPropertyDetails("supplier");
        supplierCheckBox.bind(bindingGroup, basicOrganization, propDetails);

        propDetails = entityProps.getPropertyDetails("producer");
        producerCheckBox.bind(bindingGroup, basicOrganization, propDetails);

        propDetails = entityProps.getPropertyDetails("shippingAgent");
        shippingAgentCheckBox.bind(bindingGroup, basicOrganization, propDetails);

        propDetails = entityProps.getPropertyDetails("courier");
        courierCheckBox.bind(bindingGroup, basicOrganization, propDetails);

        propDetails = entityProps.getPropertyDetails("bank");
        bankCheckBox.bind(bindingGroup, basicOrganization, propDetails);

        bindingGroup.bind();
    }

    private void initClassifier() {
        if(classifier == null)
            return;

        String classifierCode = classifier.getClassifierCode();
        if(classifierCode.equals(Classifier.Customer.getClassifierCode()))
            basicOrganization.setCustomer(true);
        else if(classifierCode.equals(Classifier.Supplier.getClassifierCode()))
            basicOrganization.setSupplier(true);
        else if(classifierCode.equals(Classifier.Producer.getClassifierCode()))
            basicOrganization.setProducer(true);
        else if(classifierCode.equals(Classifier.ShippingAgent.getClassifierCode()))
            basicOrganization.setShippingAgent(true);
        else if(classifierCode.equals(Classifier.Courier.getClassifierCode()))
            basicOrganization.setCourier(true);
        else if(classifierCode.equals(Classifier.Bank.getClassifierCode()))
            basicOrganization.setBank(true);
    }

    protected OrganizationsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = getBean(OrganizationsListRemote.class);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }
}