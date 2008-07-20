/*
 * DeliveryCertificatePanel.java
 *
 * Created on Вторник, 2008, Юли 15, 20:43
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListRemote;
import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.enums.DeliveryCertificateReason;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import java.beans.PropertyChangeEvent;
import org.jdesktop.beansbinding.BindingGroup;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.enums.DeliveryCertificateMethodType;
import com.cosmos.acacia.crm.gui.contactbook.AddressListPanel;
import com.cosmos.acacia.crm.gui.contactbook.ContactPersonsListPanel;
import com.cosmos.acacia.crm.gui.contactbook.OrganizationsListPanel;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Binding;

/**
 *
 * @author  daniel
 */
public class DeliveryCertificatePanel extends BaseEntityPanel {

    @EJB
    private DeliveryCertificatesRemote formSession;
    
    @EJB
    private OrganizationsListRemote organizationList;
    
    private DeliveryCertificate entity;
    private Address forwarderBranch;
    private EntityProperties entProps;
    private BindingGroup bindGroup;
    private Binding forwardersBinding;
    private Binding forwarderBranchBinding;
    private Binding forwarderContactPersonsBinding;
    
    public DeliveryCertificatePanel(DeliveryCertificate ds, DataObject parent) {
        super(parent != null ? parent.getDataObjectId() : null);
        this.entity = ds;
        init();
    }
    
     /** Creates new form DeliveryCertificatePanel */
    public DeliveryCertificatePanel(DeliveryCertificate ds) {
        this(ds, null);
        init();
    }
    
    @Override
    protected void init()
    {
        initComponents();
        super.init();
    }

    @Override
    public void performSave(boolean closeAfter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BindingGroup getBindingGroup() {
        return bindGroup;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
         return entityFormButtonPanel1;
    }

    @Override
    protected void initData() {
        if(bindGroup == null){
            bindGroup = new BindingGroup();
        }
        
        if(entity == null){
            entity = getFormSession().newDeliveryCertificate(getParentDataObjectId());
        }
        
        List contactPersons = new ArrayList();
        contactPersons.add("Kiril Bachev");
        contactPersons.add(entity.getRecipientContactName());
        contactPersons.add("Tonny Ball");
        
        
        final EntityProperties entityProps = getFormSession().getDeliveryCertificateEntityProperties();
        
        numberTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("deliveryCertificateNumber"));
        creationDatePicker.bind(bindGroup, entity, entityProps.getPropertyDetails("creationTime"));
        
        recipientNameTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("recipientName"));
        recipientContactPersonComboBox.bind(bindGroup, contactPersons, entity, entityProps.getPropertyDetails("recipientContactName"));
        
        creatorNameTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("creatorName"));
        AcaciaSessionRemote acaciaSessionRemote = getBean(AcaciaSessionRemote.class);
        Organization creatorOrg = acaciaSessionRemote.getOrganization();
        creatorOrganizationTextField.setText(creatorOrg.getOrganizationName());
        reasonComboBox.bind(bindGroup, getFormSession().getReasons(), entity, entityProps.getPropertyDetails("deliveryCertificateReason"));
                
        //BusinessPartner recipient = entity.getRecipient();
        Warehouse warehouse = entity.getWarehouse();
        
        if(warehouse != null){
            String creatorBranch = acaciaSessionRemote.getBranch() != null ? acaciaSessionRemote.getBranch().getAddressName() : warehouse.getAddress().getAddressName();
            creatorBranchTextField.setText(creatorBranch);
        }
        
        deliveryTypeComboBox.bind(bindGroup, getFormSession().getDeliveryTypes(), entity, entityProps.getPropertyDetails("deliveryCertificateMethodType"));
        if(entity.getDeliveryCertificateMethodType().getEnumValue() == DeliveryCertificateMethodType.Forwarder){
            forwardersBinding = forwardersLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseForwarder();
                }
            }, bindGroup,
            entity,
            entityProps.getPropertyDetails("forwarder"),
            "${organizationName}",
            UpdateStrategy.READ_WRITE);
            
            forwarderBranchBinding = forwarderBranchAcaciaLookup.bind(new AcaciaLookupProvider(){
                    @Override
                    public Object showSelectionControl() {
                        return onChooseForwarderBranch();
                    }
                }, 
                bindGroup, 
                entity, 
                entityProps.getPropertyDetails("forwarderBranch"),
                "${addressName}",        
                UpdateStrategy.READ_WRITE);
                
            forwarderContactPersonsBinding = forwarderContactPersonAcaciaLookup.bind( new AcaciaLookupProvider(){
                @Override
                public Object showSelectionControl(){
                    return onChooseForwarderContactPerson();
                }
            }, bindGroup,
            entity,        
            entityProps.getPropertyDetails("forwarderContact"),
            "${firstName}",
            UpdateStrategy.READ_WRITE);
            
        }else{
            //disable forwarder inputs
        }
        
        //TODO, but do not know how
        //documentComboBox.bind(bindGroup, getFormSession().getDocuments(DeliveryCertificateReason.Invoice), entity, entityProps.getPropertyDetails("assignments"));
        
        bindGroup.bind();
         
    }

    protected Object onChooseForwarder() {

        OrganizationsListPanel listPanel = new OrganizationsListPanel(null);
        listPanel.setClassifier(getClassifiersFormSession().getClassifier("forwarder"));
        
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            Organization selectedForwarder = (Organization) listPanel.getSelectedRowObject();
            return selectedForwarder;
        } else {
            return null;
        }
    }
    
    protected Object onChooseForwarderBranch() {

        BigInteger organizationId = entity.getForwarder() != null ?  entity.getForwarder().getId() : null;
        AddressListPanel listPanel = new AddressListPanel(organizationId);
        
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            forwarderBranch = (Address) listPanel.getSelectedRowObject();
            return forwarderBranch;
        } else {
            return null;
        }
    }
    
    protected Object onChooseForwarderContactPerson() {

        ContactPersonsListPanel listPanel = new ContactPersonsListPanel(forwarderBranch.getAddressId());
        
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            ContactPerson selectedContactPerson = (ContactPerson) listPanel.getSelectedRowObject();
            return selectedContactPerson.getContact();
        } else {
            return null;
        }
    }
    
    protected DeliveryCertificatesRemote getFormSession(){
        if(formSession == null){
            try{
                formSession = InitialContext.doLookup(DeliveryCertificatesRemote.class.getName());
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }

        return formSession;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detailsPanel = new com.cosmos.swingb.JBPanel();
        numberLabel = new com.cosmos.swingb.JBLabel();
        numberTextField = new com.cosmos.swingb.JBTextField();
        reasonLabel = new com.cosmos.swingb.JBLabel();
        dateLabel = new com.cosmos.swingb.JBLabel();
        documentLabel = new com.cosmos.swingb.JBLabel();
        reasonComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        documentComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        creationDatePicker = new com.cosmos.swingb.JBDatePicker();
        creatorPanel = new com.cosmos.swingb.JBPanel();
        organizationLabel = new com.cosmos.swingb.JBLabel();
        creatorOrganizationTextField = new com.cosmos.swingb.JBTextField();
        supplierBranchLabel = new com.cosmos.swingb.JBLabel();
        creatorBranchTextField = new com.cosmos.swingb.JBTextField();
        supplerEmployeeLabel = new com.cosmos.swingb.JBLabel();
        creatorNameTextField = new com.cosmos.swingb.JBTextField();
        recipientPanel = new com.cosmos.swingb.JBPanel();
        clientNameLabel = new com.cosmos.swingb.JBLabel();
        recipientNameTextField = new com.cosmos.swingb.JBTextField();
        clientBranchLabel = new com.cosmos.swingb.JBLabel();
        clientBranchTextField = new com.cosmos.swingb.JBTextField();
        clientContactPersonLabel = new com.cosmos.swingb.JBLabel();
        recipientContactPersonComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        deliveryMethodPanel = new com.cosmos.swingb.JBPanel();
        forwardNameLabel = new com.cosmos.swingb.JBLabel();
        forwarderContactNameLabel = new com.cosmos.swingb.JBLabel();
        deliveryTypeComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        forwardersLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        forwarderContactPersonAcaciaLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        forwarderBranchAcaciaLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        forwarderBranchLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(DeliveryCertificatePanel.class);
        detailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("detailsPanel.border.title"))); // NOI18N
        detailsPanel.setName("detailsPanel"); // NOI18N

        numberLabel.setText(resourceMap.getString("numberLabel.text")); // NOI18N
        numberLabel.setName("numberLabel"); // NOI18N

        numberTextField.setText(resourceMap.getString("numberTextField.text")); // NOI18N
        numberTextField.setName("numberTextField"); // NOI18N
        numberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberTextFieldActionPerformed(evt);
            }
        });

        reasonLabel.setText(resourceMap.getString("reasonLabel.text")); // NOI18N
        reasonLabel.setName("reasonLabel"); // NOI18N

        dateLabel.setText(resourceMap.getString("dateLabel.text")); // NOI18N
        dateLabel.setName("dateLabel"); // NOI18N

        documentLabel.setText(resourceMap.getString("documentLabel.text")); // NOI18N
        documentLabel.setName("documentLabel"); // NOI18N

        reasonComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        reasonComboBox.setName("reasonComboBox"); // NOI18N

        documentComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        documentComboBox.setName("documentComboBox"); // NOI18N

        creationDatePicker.setEditable(false);
        creationDatePicker.setName("creationDatePicker"); // NOI18N

        javax.swing.GroupLayout detailsPanelLayout = new javax.swing.GroupLayout(detailsPanel);
        detailsPanel.setLayout(detailsPanelLayout);
        detailsPanelLayout.setHorizontalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reasonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reasonComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(numberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                .addGap(45, 45, 45)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(documentComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(creationDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                .addContainerGap())
        );
        detailsPanelLayout.setVerticalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reasonComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reasonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(documentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(documentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(creationDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        creatorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("creatorPanel.border.title"))); // NOI18N
        creatorPanel.setName("creatorPanel"); // NOI18N

        organizationLabel.setText(resourceMap.getString("organizationLabel.text")); // NOI18N
        organizationLabel.setName("organizationLabel"); // NOI18N

        creatorOrganizationTextField.setText(resourceMap.getString("creatorOrganizationTextField.text")); // NOI18N
        creatorOrganizationTextField.setName("creatorOrganizationTextField"); // NOI18N

        supplierBranchLabel.setText(resourceMap.getString("supplierBranchLabel.text")); // NOI18N
        supplierBranchLabel.setName("supplierBranchLabel"); // NOI18N

        creatorBranchTextField.setText(resourceMap.getString("creatorBranchTextField.text")); // NOI18N
        creatorBranchTextField.setName("creatorBranchTextField"); // NOI18N

        supplerEmployeeLabel.setText(resourceMap.getString("supplerEmployeeLabel.text")); // NOI18N
        supplerEmployeeLabel.setName("supplerEmployeeLabel"); // NOI18N

        creatorNameTextField.setText(resourceMap.getString("text")); // NOI18N
        creatorNameTextField.setName(""); // NOI18N

        javax.swing.GroupLayout creatorPanelLayout = new javax.swing.GroupLayout(creatorPanel);
        creatorPanel.setLayout(creatorPanelLayout);
        creatorPanelLayout.setHorizontalGroup(
            creatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creatorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(creatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(creatorPanelLayout.createSequentialGroup()
                        .addGroup(creatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(organizationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supplierBranchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(creatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(creatorBranchTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                            .addComponent(creatorOrganizationTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)))
                    .addGroup(creatorPanelLayout.createSequentialGroup()
                        .addComponent(supplerEmployeeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(creatorNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)))
                .addContainerGap())
        );
        creatorPanelLayout.setVerticalGroup(
            creatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creatorPanelLayout.createSequentialGroup()
                .addGroup(creatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(organizationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(creatorOrganizationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(creatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supplierBranchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(creatorBranchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(creatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supplerEmployeeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(creatorNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        recipientPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("recipientPanel.border.title"))); // NOI18N
        recipientPanel.setName("recipientPanel"); // NOI18N

        clientNameLabel.setText(resourceMap.getString("clientNameLabel.text")); // NOI18N
        clientNameLabel.setName("clientNameLabel"); // NOI18N

        recipientNameTextField.setText(resourceMap.getString("recipientNameTextField.text")); // NOI18N
        recipientNameTextField.setName("recipientNameTextField"); // NOI18N

        clientBranchLabel.setText(resourceMap.getString("clientBranchLabel.text")); // NOI18N
        clientBranchLabel.setName("clientBranchLabel"); // NOI18N

        clientBranchTextField.setText(resourceMap.getString("clientBranchTextField.text")); // NOI18N
        clientBranchTextField.setName("clientBranchTextField"); // NOI18N

        clientContactPersonLabel.setText(resourceMap.getString("clientContactPersonLabel.text")); // NOI18N
        clientContactPersonLabel.setName("clientContactPersonLabel"); // NOI18N

        recipientContactPersonComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        recipientContactPersonComboBox.setName("recipientContactPersonComboBox"); // NOI18N

        javax.swing.GroupLayout recipientPanelLayout = new javax.swing.GroupLayout(recipientPanel);
        recipientPanel.setLayout(recipientPanelLayout);
        recipientPanelLayout.setHorizontalGroup(
            recipientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recipientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recipientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientBranchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientContactPersonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(recipientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(recipientContactPersonComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientBranchTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(recipientNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                .addContainerGap())
        );
        recipientPanelLayout.setVerticalGroup(
            recipientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recipientPanelLayout.createSequentialGroup()
                .addGroup(recipientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recipientNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(recipientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientBranchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientBranchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(recipientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientContactPersonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recipientContactPersonComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        deliveryMethodPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("deliveryMethodPanel.border.title"))); // NOI18N
        deliveryMethodPanel.setName("deliveryMethodPanel"); // NOI18N

        forwardNameLabel.setText(resourceMap.getString("forwardNameLabel.text")); // NOI18N
        forwardNameLabel.setName("forwardNameLabel"); // NOI18N

        forwarderContactNameLabel.setText(resourceMap.getString("forwarderContactNameLabel.text")); // NOI18N
        forwarderContactNameLabel.setName("forwarderContactNameLabel"); // NOI18N

        deliveryTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        deliveryTypeComboBox.setName("deliveryTypeComboBox"); // NOI18N

        forwardersLookup.setName("forwardersLookup"); // NOI18N

        forwarderContactPersonAcaciaLookup.setName("forwarderContactPersonAcaciaLookup"); // NOI18N

        forwarderBranchAcaciaLookup.setName("forwarderBranchAcaciaLookup"); // NOI18N

        forwarderBranchLabel.setText(resourceMap.getString("forwarderBranchLabel.text")); // NOI18N
        forwarderBranchLabel.setName("forwarderBranchLabel"); // NOI18N

        javax.swing.GroupLayout deliveryMethodPanelLayout = new javax.swing.GroupLayout(deliveryMethodPanel);
        deliveryMethodPanel.setLayout(deliveryMethodPanelLayout);
        deliveryMethodPanelLayout.setHorizontalGroup(
            deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deliveryMethodPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deliveryTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(deliveryMethodPanelLayout.createSequentialGroup()
                            .addComponent(forwarderContactNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(forwarderContactPersonAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, deliveryMethodPanelLayout.createSequentialGroup()
                            .addGroup(deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(forwarderBranchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(forwardNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(40, 40, 40)
                            .addGroup(deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(forwarderBranchAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(forwardersLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        deliveryMethodPanelLayout.setVerticalGroup(
            deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deliveryMethodPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(deliveryTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(forwardersLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forwardNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(deliveryMethodPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(forwarderBranchAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(deliveryMethodPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(forwarderBranchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(forwarderContactPersonAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forwarderContactNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deliveryMethodPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(detailsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(creatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(recipientPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(detailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(creatorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(recipientPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deliveryMethodPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void numberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberTextFieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_numberTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel clientBranchLabel;
    private com.cosmos.swingb.JBTextField clientBranchTextField;
    private com.cosmos.swingb.JBLabel clientContactPersonLabel;
    private com.cosmos.swingb.JBLabel clientNameLabel;
    private com.cosmos.swingb.JBDatePicker creationDatePicker;
    private com.cosmos.swingb.JBTextField creatorBranchTextField;
    private com.cosmos.swingb.JBTextField creatorNameTextField;
    private com.cosmos.swingb.JBTextField creatorOrganizationTextField;
    private com.cosmos.swingb.JBPanel creatorPanel;
    private com.cosmos.swingb.JBLabel dateLabel;
    private com.cosmos.swingb.JBPanel deliveryMethodPanel;
    private com.cosmos.acacia.gui.AcaciaComboBox deliveryTypeComboBox;
    private com.cosmos.swingb.JBPanel detailsPanel;
    private com.cosmos.acacia.gui.AcaciaComboBox documentComboBox;
    private com.cosmos.swingb.JBLabel documentLabel;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.swingb.JBLabel forwardNameLabel;
    private com.cosmos.acacia.gui.AcaciaLookup forwarderBranchAcaciaLookup;
    private com.cosmos.swingb.JBLabel forwarderBranchLabel;
    private com.cosmos.swingb.JBLabel forwarderContactNameLabel;
    private com.cosmos.acacia.gui.AcaciaLookup forwarderContactPersonAcaciaLookup;
    private com.cosmos.acacia.gui.AcaciaLookup forwardersLookup;
    private com.cosmos.swingb.JBLabel numberLabel;
    private com.cosmos.swingb.JBTextField numberTextField;
    private com.cosmos.swingb.JBLabel organizationLabel;
    private com.cosmos.acacia.gui.AcaciaComboBox reasonComboBox;
    private com.cosmos.swingb.JBLabel reasonLabel;
    private com.cosmos.acacia.gui.AcaciaComboBox recipientContactPersonComboBox;
    private com.cosmos.swingb.JBTextField recipientNameTextField;
    private com.cosmos.swingb.JBPanel recipientPanel;
    private com.cosmos.swingb.JBLabel supplerEmployeeLabel;
    private com.cosmos.swingb.JBLabel supplierBranchLabel;
    // End of variables declaration//GEN-END:variables

}
