/*
 * organizationPanel.java
 *
 * Created on 09 March 2008, 16:50
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.impl.OrganizationsListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.listeners.TableModificationListener;
import javax.swing.JOptionPane;

/**
 * A form for adding and editing organizations
 *
 * @author  Bozhidar Bozhanov
 */
public class OrganizationPanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(OrganizationPanel.class);

    /** Creates new form organizationPanel */
    public OrganizationPanel(Organization organization) {
        super(organization.getDataObject().getParentDataObject());
        this.organization = organization;
        init();
    }

    /** Creates new form organizationPanel */
    public OrganizationPanel(DataObject parentDataObject) {
        super(parentDataObject);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descriptionPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        branchesPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        generalInfoPanel = new com.cosmos.swingb.JBPanel();
        nameLabel = new com.cosmos.swingb.JBLabel();
        nicknameLabel = new com.cosmos.swingb.JBLabel();
        typeLabel = new com.cosmos.swingb.JBLabel();
        shareCapitalLabel = new com.cosmos.swingb.JBLabel();
        vatNumberLabel = new com.cosmos.swingb.JBLabel();
        nameTextField = new com.cosmos.swingb.JBTextField();
        nicknameTextField = new com.cosmos.swingb.JBTextField();
        shareCapitalTextField = new com.cosmos.swingb.JBTextField();
        vatNumberTextField = new com.cosmos.swingb.JBTextField();
        organizationTypeComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        currencyComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        registrationDetailsPanel = new com.cosmos.swingb.JBPanel();
        registrationDateLabel = new com.cosmos.swingb.JBLabel();
        registrationAddressLabel = new com.cosmos.swingb.JBLabel();
        administrationAddressLabel = new com.cosmos.swingb.JBLabel();
        registeringOrganizationLabel = new com.cosmos.swingb.JBLabel();
        registrationDateDatePicker = new com.cosmos.swingb.JBDatePicker();
        uniqueIdLabel = new com.cosmos.swingb.JBLabel();
        uniqueIdTextField = new com.cosmos.swingb.JBTextField();
        registeringOrganizationLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        administrativeAddressLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        registrationAddressLookup = new com.cosmos.acacia.gui.AcaciaLookup();

        setOpaque(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(OrganizationPanel.class);
        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("descriptionPanel.border.title"))); // NOI18N
        descriptionPanel.setName("descriptionPanel"); // NOI18N

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setName("jScrollPane3"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        jScrollPane3.setViewportView(descriptionTextPane);

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addContainerGap())
        );

        branchesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("branchesPanel.border.title"))); // NOI18N
        branchesPanel.setName("branchesPanel"); // NOI18N

        javax.swing.GroupLayout branchesPanelLayout = new javax.swing.GroupLayout(branchesPanel);
        branchesPanel.setLayout(branchesPanelLayout);
        branchesPanelLayout.setHorizontalGroup(
            branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 745, Short.MAX_VALUE)
        );
        branchesPanelLayout.setVerticalGroup(
            branchesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
        );

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        generalInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("generalInfoPanel.border.title"))); // NOI18N
        generalInfoPanel.setName("generalInfoPanel"); // NOI18N

        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        nicknameLabel.setText(resourceMap.getString("nicknameLabel.text")); // NOI18N
        nicknameLabel.setName("nicknameLabel"); // NOI18N

        typeLabel.setText(resourceMap.getString("typeLabel.text")); // NOI18N
        typeLabel.setName("typeLabel"); // NOI18N

        shareCapitalLabel.setText(resourceMap.getString("shareCapitalLabel.text")); // NOI18N
        shareCapitalLabel.setName("shareCapitalLabel"); // NOI18N

        vatNumberLabel.setText(resourceMap.getString("vatNumberLabel.text")); // NOI18N
        vatNumberLabel.setName("vatNumberLabel"); // NOI18N

        nameTextField.setText(resourceMap.getString("nameTextField.text")); // NOI18N
        nameTextField.setName("nameTextField"); // NOI18N

        nicknameTextField.setText(resourceMap.getString("nicknameTextField.text")); // NOI18N
        nicknameTextField.setName("nicknameTextField"); // NOI18N

        shareCapitalTextField.setText(resourceMap.getString("shareCapitalTextField.text")); // NOI18N
        shareCapitalTextField.setName("shareCapitalTextField"); // NOI18N

        vatNumberTextField.setText(resourceMap.getString("vatNumberTextField.text")); // NOI18N
        vatNumberTextField.setName("vatNumberTextField"); // NOI18N

        organizationTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        organizationTypeComboBox.setName("organizationTypeComboBox"); // NOI18N

        currencyComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        currencyComboBox.setName("currencyComboBox"); // NOI18N

        javax.swing.GroupLayout generalInfoPanelLayout = new javax.swing.GroupLayout(generalInfoPanel);
        generalInfoPanel.setLayout(generalInfoPanelLayout);
        generalInfoPanelLayout.setHorizontalGroup(
            generalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vatNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shareCapitalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, generalInfoPanelLayout.createSequentialGroup()
                        .addComponent(shareCapitalTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currencyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nicknameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(vatNumberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(organizationTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        generalInfoPanelLayout.setVerticalGroup(
            generalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalInfoPanelLayout.createSequentialGroup()
                .addGroup(generalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nicknameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(organizationTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shareCapitalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shareCapitalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currencyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vatNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vatNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        registrationDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("registrationDetailsPanel.border.title"))); // NOI18N
        registrationDetailsPanel.setName("registrationDetailsPanel"); // NOI18N

        registrationDateLabel.setText(resourceMap.getString("registrationDateLabel.text")); // NOI18N
        registrationDateLabel.setName("registrationDateLabel"); // NOI18N

        registrationAddressLabel.setText(resourceMap.getString("registrationAddressLabel.text")); // NOI18N
        registrationAddressLabel.setName("registrationAddressLabel"); // NOI18N

        administrationAddressLabel.setText(resourceMap.getString("administrationAddressLabel.text")); // NOI18N
        administrationAddressLabel.setName("administrationAddressLabel"); // NOI18N

        registeringOrganizationLabel.setText(resourceMap.getString("registeringOrganizationLabel.text")); // NOI18N
        registeringOrganizationLabel.setName("registeringOrganizationLabel"); // NOI18N

        registrationDateDatePicker.setName("registrationDateDatePicker"); // NOI18N

        uniqueIdLabel.setText(resourceMap.getString("uniqueIdLabel.text")); // NOI18N
        uniqueIdLabel.setName("uniqueIdLabel"); // NOI18N

        uniqueIdTextField.setText(resourceMap.getString("uniqueIdTextField.text")); // NOI18N
        uniqueIdTextField.setName("uniqueIdTextField"); // NOI18N

        registeringOrganizationLookup.setName("registeringOrganizationLookup"); // NOI18N

        administrativeAddressLookup.setName("administrativeAddressLookup"); // NOI18N

        registrationAddressLookup.setName("registrationAddressLookup"); // NOI18N

        javax.swing.GroupLayout registrationDetailsPanelLayout = new javax.swing.GroupLayout(registrationDetailsPanel);
        registrationDetailsPanel.setLayout(registrationDetailsPanelLayout);
        registrationDetailsPanelLayout.setHorizontalGroup(
            registrationDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registrationDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(registrationDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(registrationDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registeringOrganizationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(uniqueIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(administrationAddressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registrationAddressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(registrationDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uniqueIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(registeringOrganizationLookup, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(administrativeAddressLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(registrationAddressLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(registrationDateDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                .addContainerGap())
        );
        registrationDetailsPanelLayout.setVerticalGroup(
            registrationDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registrationDetailsPanelLayout.createSequentialGroup()
                .addGroup(registrationDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registrationDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registrationDateDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(registrationDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(registrationAddressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(registrationAddressLookup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(registrationDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(administrationAddressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(administrativeAddressLookup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(registrationDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registeringOrganizationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(registeringOrganizationLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(registrationDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uniqueIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uniqueIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(branchesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(generalInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(registrationDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registrationDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generalInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(branchesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {generalInfoPanel, registrationDetailsPanel});

    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel administrationAddressLabel;
    private com.cosmos.acacia.gui.AcaciaLookup administrativeAddressLookup;
    private com.cosmos.acacia.gui.TableHolderPanel branchesPanel;
    private com.cosmos.acacia.gui.AcaciaComboBox currencyComboBox;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.swingb.JBPanel generalInfoPanel;
    private javax.swing.JScrollPane jScrollPane3;
    private com.cosmos.swingb.JBLabel nameLabel;
    private com.cosmos.swingb.JBTextField nameTextField;
    private com.cosmos.swingb.JBLabel nicknameLabel;
    private com.cosmos.swingb.JBTextField nicknameTextField;
    private com.cosmos.acacia.gui.AcaciaComboBox organizationTypeComboBox;
    private com.cosmos.swingb.JBLabel registeringOrganizationLabel;
    private com.cosmos.acacia.gui.AcaciaLookup registeringOrganizationLookup;
    private com.cosmos.swingb.JBLabel registrationAddressLabel;
    private com.cosmos.acacia.gui.AcaciaLookup registrationAddressLookup;
    private com.cosmos.swingb.JBDatePicker registrationDateDatePicker;
    private com.cosmos.swingb.JBLabel registrationDateLabel;
    private com.cosmos.swingb.JBPanel registrationDetailsPanel;
    private com.cosmos.swingb.JBLabel shareCapitalLabel;
    private com.cosmos.swingb.JBTextField shareCapitalTextField;
    private com.cosmos.swingb.JBLabel typeLabel;
    private com.cosmos.swingb.JBLabel uniqueIdLabel;
    private com.cosmos.swingb.JBTextField uniqueIdTextField;
    private com.cosmos.swingb.JBLabel vatNumberLabel;
    private com.cosmos.swingb.JBTextField vatNumberTextField;
    // End of variables declaration//GEN-END:variables

    @EJB
    private OrganizationsListRemote formSession;

    private AddressListPanel branchesTable;
    private BindingGroup organizationBindingGroup;
    private Organization organization;
    private Binding registeringOrganizationBinding;

    @Override
    protected void initData() {
        setResizable(false);
        log.info("initData().organization: " + organization);

        if(organization == null)
        {
            organization = getFormSession().newOrganization();
        }

        if (organizationBindingGroup == null)
            organizationBindingGroup = new BindingGroup();

        EntityProperties entityProps = getOrganizationEntityProperties();

        nameTextField.bind(organizationBindingGroup, organization, entityProps.getPropertyDetails("organizationName"));
        nicknameTextField.bind(organizationBindingGroup, organization, entityProps.getPropertyDetails("nickname"));
        shareCapitalTextField.bind(organizationBindingGroup, organization, entityProps.getPropertyDetails("shareCapital"));
        vatNumberTextField.bind(organizationBindingGroup, organization, entityProps.getPropertyDetails("vatNumber"));
        uniqueIdTextField.bind(organizationBindingGroup, organization, entityProps.getPropertyDetails("uniqueIdentifierCode"));

        registrationDateDatePicker.bind(organizationBindingGroup, organization, entityProps.getPropertyDetails("registrationDate"));

        organizationTypeComboBox.bind(organizationBindingGroup,
                getOrganizationTypes(),
                organization,
                entityProps.getPropertyDetails("organizationType"));

        currencyComboBox.bind(organizationBindingGroup,
                getCurrencies(),
                organization,
                entityProps.getPropertyDetails("currency"));

        registeringOrganizationBinding = registeringOrganizationLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseRegisteringOrganization();
                }
            }, organizationBindingGroup,
            organization,
            entityProps.getPropertyDetails("registrationOrganization"),
            "${organizationName}",
            UpdateStrategy.READ_WRITE);

        descriptionTextPane.bind(organizationBindingGroup, organization, "description");

        // Using an AbstractTablePanel implementation
        branchesTable = new AddressListPanel(organization.getDataObject());
        branchesTable.setVisibleButtons(14); //Only New, Modify and Delete

        // Adding the nested table listener to ensure that organization is saved
        // before adding branches to it. Also adding a deletion listener for
        // callback when a branch is deleted
        addNestedFormListener(branchesTable);
        branchesTable.addTableModificationListener(new DeletionListener());

        //addressesTable.setButtonsTextVisibility(false);
        branchesPanel.add(branchesTable);

        
        Binding regAddressBinding = registrationAddressLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseAddress();
                }
            }, organizationBindingGroup,
            organization,
            entityProps.getPropertyDetails("registrationAddress"),
            "${addressName}",
            UpdateStrategy.READ_WRITE);
            
            
        Binding adminAddressBinding = administrativeAddressLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseAddress();
                }
            }, organizationBindingGroup,
            organization,
            entityProps.getPropertyDetails("administrationAddress"),
            "${addressName}",
            UpdateStrategy.READ_WRITE);
            
        organizationBindingGroup.bind();
    }

    protected Object onChooseRegisteringOrganization() {
        OrganizationsListPanel listPanel =
                new OrganizationsListPanel(null);
        // TODO: Classifiers

        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            Organization result = (Organization) listPanel.getSelectedRowObject();
            
            if (result.equals(organization)) {
                JOptionPane.showMessageDialog(this, 
                        getResourceMap().getString("Organization.err.registeringOrganization"));
                
                return null;
            }
            
            return result;
        } else {
            return null;
        }
    }

    protected Object onChooseAddress() {
        AddressListPanel listPanel = new AddressListPanel(organization.getDataObject());

        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            Address result = (Address) listPanel.getSelectedRowObject();
            branchesTable.refreshAction();
            return result;
        } else {
            return null;
        }
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

    public BindingGroup getBindingGroup()
    {
        return organizationBindingGroup;
    }

    public Object getEntity()
    {
        return organization;
    }

    public void performSave(boolean closeAfter)
    {
        log.info("Save: organization: " + organization);

        organization = getFormSession().saveOrganization(organization);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(organization);
        if (closeAfter) {
            close();
        } else {
            organizationBindingGroup.unbind();
            initData();
        }
    }

    protected EntityProperties getOrganizationEntityProperties()
    {
        return getFormSession().getOrganizationEntityProperties();
    }

    protected EntityProperties getAddressEntityProperties()
    {
        return getFormSession().getAddressEntityProperties();
    }

    private List<Organization> getOrganizations()
    {
        List<Organization> organizations = getFormSession().getOrganizations(null);
        return organizations;
    }

    private List<DbResource> getCurrencies()
    {
        return getFormSession().getCurrencies();
    }

    private List<DbResource> getOrganizationTypes()
    {
        return getFormSession().getOrganizationTypes();
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
       return entityFormButtonPanel;
    }
    
    class DeletionListener implements TableModificationListener {

        public void rowDeleted(Object row) {
            if (row != null) {
                Address deletedAddress  = (Address) row;
                Address administrationAddress = organization.getAdministrationAddress();
                Address registrationAddress = organization.getRegistrationAddress();

                if (administrationAddress != null
                        && administrationAddress.getAddressId()
                            .equals(deletedAddress.getAddressId()))
                {
                    administrativeAddressLookup.clearSelectedValue();
                }

                if (registrationAddress != null
                        && registrationAddress.getAddressId()
                            .equals(deletedAddress.getAddressId()))
                {
                    registrationAddressLookup.clearSelectedValue();
                }
            }
        }

        public void rowModified(Object row) {

        }

        public void rowAdded(Object row) {

        }

    }
}