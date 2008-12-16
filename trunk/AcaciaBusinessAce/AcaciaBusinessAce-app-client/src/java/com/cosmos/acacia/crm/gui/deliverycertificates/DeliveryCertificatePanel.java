/*
 * DeliveryCertificatePanel.java
 *
 * Created on ???????, 2008, ??? 15, 20:43
 */

package com.cosmos.acacia.crm.gui.deliverycertificates;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.swing.JOptionPane;

import org.apache.commons.collections.CollectionUtils;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingx.error.ErrorInfo;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.data.DeliveryCertificateAssignment;
import com.cosmos.acacia.crm.data.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.predicates.ValidDeliveryCertificateAssignmentPredicate;
import com.cosmos.acacia.crm.enums.DeliveryCertificateMethodType;
import com.cosmos.acacia.crm.enums.DeliveryCertificateReason;
import com.cosmos.acacia.crm.enums.DeliveryCertificateStatus;
import com.cosmos.acacia.crm.gui.ProductPanel;
import com.cosmos.acacia.crm.gui.contactbook.AddressListPanel;
import com.cosmos.acacia.crm.gui.contactbook.ContactPersonsListPanel;
import com.cosmos.acacia.crm.gui.contactbook.OrganizationsListPanel;
import com.cosmos.acacia.crm.gui.invoice.InvoiceListPanel;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaComboBox;
import com.cosmos.acacia.gui.AcaciaLookup;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBErrorPane;

/**
 *
 * @author  daniel
 */
public class DeliveryCertificatePanel extends BaseEntityPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9063542129569961080L;

	@EJB
    private DeliveryCertificatesRemote formSession;
    
    @EJB
    private InvoiceListRemote invoicesBean;
    
    private DeliveryCertificate entity;
    private DeliveryCertificateAssignment assignment;
    private DeliveryCertificateItemListPanel itemsTablePanel;
    private List<DeliveryCertificateItem> deliveryCertificateItems;
    
    private Address forwarderBranch;
    private EntityProperties entityProps;
    JBButton deliverButton = null;
    JBButton refreshButton = null;
    
    private BindingGroup bindGroup;
    private Binding recipientNameBinding;
    private Binding recipientBranchNameBinding;
    private Binding recipientContactNameBinding;
    
    public DeliveryCertificatePanel(DeliveryCertificate ds, DataObject parent) {
        super(parent != null ? parent.getDataObjectId() : null);
        this.entity = ds;
        this.assignment = ds.getDocumentAssignment();
        init();
    }
    
     /** Creates new form DeliveryCertificatePanel */
    public DeliveryCertificatePanel(DeliveryCertificate ds) {
        this(ds, null);
    }
    
    @Override
    protected void init()
    {
        initComponents();
        initComponentsCustom();
        super.init();
    }

    private void initComponentsCustom() {
    	
    	// Using an AbstractTablePanel implementation
        itemsTablePanel = new DeliveryCertificateItemListPanel(entity.getDeliveryCertificateId());
        itemsTablePanel.setVisible(AbstractTablePanel.Button.SpecialModify);
        itemsTablePanel.setSpecialButtonBehavior(false);
        itemsTablePanel.setSpecialCaption("deliveryCertificateItemsAction.Action.text");
        
        //Adding the nested table listener to ensure that the delivery certificate is saved before adding
        //items to it. 
        addNestedFormListener(itemsTablePanel);
        itemsTableHolderPanel.add(itemsTablePanel);
        
        deliverButton = new JBButton();
        deliverButton.setAction(getContext().getActionMap(this).get("deliver"));
        deliverButton.setText(getResourceMap().getString("deliver.button.text"));
        entityFormButtonPanel1.addButton(deliverButton);
        
        refreshButton = new JBButton();
        refreshButton.setAction(getContext().getActionMap(this).get("refresh"));
        refreshButton.setText(getResourceMap().getString("refresh.button.text"));
        refreshButton.setEnabled(false);
        entityFormButtonPanel1.addButton(refreshButton);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void performSave(boolean closeAfter) {
    	
    	//Wrap the ListData that is from ObservableList type to one (ArrayList) that can be marshalled.
    	List<DeliveryCertificateItem> items = new ArrayList<DeliveryCertificateItem>(itemsTablePanel.getListData());
    	entity = getFormSession().saveDeliveryCertificate(entity, assignment, items);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if (closeAfter) {
            close();
        } else {
            bindGroup.unbind();
            initData();
        }
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
            assignment = getFormSession().newDeliveryCertificateAssignment();
        }
        
        entityProps = getFormSession().getDeliveryCertificateEntityProperties();
        EntityProperties assignmentProps = getFormSession().getDeliveryCertificateAssignmentEntityProperties(); 
        
        //Base Properties Panel
        numberTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("deliveryCertificateNumber"));
        creationDatePicker.bind(bindGroup, entity, entityProps.getPropertyDetails("deliveryCertificateDate"));
        Binding binding1 = reasonComboBox.bind(bindGroup, getFormSession().getReasons(), entity, entityProps.getPropertyDetails("deliveryCertificateReason"));
//        reasonComboBox.addItemListener(new ItemListener(){
//			@Override
//			public void itemStateChanged(ItemEvent item) {
//				if(item.getItem() instanceof DbResource){
//					DbResource reason = (DbResource)item.getItem();
//					if(!DeliveryCertificateReason.Invoice.equals(reason.getEnumValue())){
//						JOptionPane.showMessageDialog(DeliveryCertificatePanel.this, "Not implemented, yet");
//						return;
//					}
//				}
//			}
//        });
        binding1.addBindingListener(new AbstractBindingListener() {
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                if (event.getNewValue() instanceof DbResource){
                	if(!DeliveryCertificateReason.Invoice.equals(((DbResource)event.getNewValue()).getEnumValue())){
						JOptionPane.showMessageDialog(DeliveryCertificatePanel.this, "Not implemented, yet. Right! You here WELL");
						return;
					}
                } 
            }
        });
        
        documentsAcaciaLookup.bind(
            new AcaciaLookupProvider(){
                @Override
                public Object showSelectionControl(){
                    return onChooseAssignment();
                }

            }, bindGroup,
            assignment,
            assignmentProps.getPropertyDetails("documentNumber"),
            UpdateStrategy.READ_WRITE);
        
        //Creator Panel
        creatorNameTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("creatorName"));
        creatorOrganizationTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("creatorOrganizationName"));
        creatorBranchTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("creatorBranchName"));
        
        //Recipient Panel
        recipientNameBinding = recipientNameTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("recipientName"));
        recipientBranchNameBinding = recipientBranchTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("recipientBranchName"));
        recipientContactNameBinding = recipientContactPersonAcaciaLookup.bind(
            new AcaciaLookupProvider(){
                @Override
                public Object showSelectionControl(){
                    return onChooseRecipientContactPerson();
                }
            }, bindGroup,
            entity,        
            entityProps.getPropertyDetails("recipientContact"),
            UpdateStrategy.READ_WRITE);
                
        //Delivery Type Panel
        deliveryTypeComboBox.bind(bindGroup, getFormSession().getDeliveryTypes(), entity, entityProps.getPropertyDetails("deliveryCertificateMethodType"));
        deliveryTypeComboBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                AcaciaComboBox acb = (AcaciaComboBox)e.getSource();
                DbResource selectedDeliveryType = (DbResource)acb.getSelectedItem();
                if(selectedDeliveryType != null){
                    boolean isForwarding = selectedDeliveryType.getEnumValue().equals(DeliveryCertificateMethodType.Forwarder);
                    for(Component c : forwarderPanel.getComponents()){
                        if(c instanceof AcaciaLookup){
                            ((AcaciaLookup)c).setEnabled(isForwarding);
                        }
                    }
                }
            }
        });
        
        //Forwarder Details Panel
        forwardersLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseForwarder();
                }
            }, bindGroup,
            entity,
            entityProps.getPropertyDetails("forwarder"),
            "${organizationName}",
            UpdateStrategy.READ_WRITE);
       forwarderBranchAcaciaLookup.bind(new AcaciaLookupProvider(){
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
       forwarderContactPersonAcaciaLookup.bind( new AcaciaLookupProvider(){
                @Override
                public Object showSelectionControl(){
                    return onChooseForwarderContactPerson();
                }
            }, bindGroup,
            entity,        
            entityProps.getPropertyDetails("forwarderContact"),
            "${displayName}",
            UpdateStrategy.READ_WRITE);
       
	   	if(DeliveryCertificateStatus.Delivered.equals(entity.getStatus().getEnumValue())){
	       	this.setReadonly();
	    }
           
        bindGroup.bind();
         
    }
    
    protected Object onChooseRecipientContactPerson(){
        
        if(entity.getRecipientBranch() == null){
            return null;
        }
        ContactPersonsListPanel listPanel = new ContactPersonsListPanel(entity.getRecipientBranch().getAddressId());
        
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            ContactPerson selectedContactPerson = (ContactPerson) listPanel.getSelectedRowObject();
            return selectedContactPerson;
        } else {
            return null;
        }
    }
    
    //TODO: Based on reason selected, we have to display different list of documents.
    //For now display only InvoicesList, because other documents are not implemented.
    protected String onChooseAssignment() {
        
    	//the organization
    	if(DeliveryCertificateReason.Invoice.equals(entity.getDeliveryCertificateReason().getEnumValue())){
    		
	    	BigInteger invoicesParentId = entity.getCreatorOrganization().getId();
	    	List<Invoice> invoices = getInvoicesSession().listInvoices(invoicesParentId, false);
	    	CollectionUtils.filter(invoices, new ValidDeliveryCertificateAssignmentPredicate());
	
	    	InvoiceListPanel listPanel = new InvoiceListPanel(invoicesParentId, invoices, false);
	        
	        listPanel.setVisibleSelectButtons(true);
	        DialogResponse dResponse = listPanel.showDialog(this);
	        
	        if ( DialogResponse.SELECT.equals(dResponse) ){
	            Invoice selectedDocument = (Invoice) listPanel.getSelectedRowObject();
	             
	            assignment.setDocumentId(selectedDocument.getId());
	            assignment.setDocumentNumber(String.valueOf(selectedDocument.getInvoiceNumber()));
	            
	            entity.setRecipient(selectedDocument.getRecipient());
	            entity.setRecipientBranch(selectedDocument.getBranch());
	            entity.setRecipientContact(selectedDocument.getRecipientContact());
	            
	            bindDeliveryCertificateItems(selectedDocument.getInvoiceId());
	            
	            //populate the components with new values.
	            bindGroup.removeBinding(recipientNameBinding);
	            recipientNameBinding = recipientNameTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("recipientName"));
	            recipientNameBinding.bind();
	            bindGroup.removeBinding(recipientBranchNameBinding);
	            recipientBranchNameBinding = recipientBranchTextField.bind(bindGroup, entity, entityProps.getPropertyDetails("recipientBranchName"));
	            recipientBranchNameBinding.bind();
	            bindGroup.removeBinding(recipientContactNameBinding);
	            recipientContactNameBinding = recipientContactPersonAcaciaLookup.bind(
	                new AcaciaLookupProvider(){
	                    @Override
	                    public Object showSelectionControl(){
	                        return onChooseRecipientContactPerson();
	                    }
	                },  
	                bindGroup,
	                entity,        
	                entityProps.getPropertyDetails("recipientContact"),
	                "${contact.displayName}",
	                UpdateStrategy.READ_WRITE);
	            recipientContactNameBinding.bind();
	            
	            return assignment.getDocumentNumber();
	        } else {
	            return null;
	        }
    	}
    	else{
    		JOptionPane.showMessageDialog(this, "Not implemented, yet. The only reason document can be an invoice.");
    		return null;
    	}
    }
 
    protected void bindDeliveryCertificateItems(BigInteger invoiceId){
    	 List<InvoiceItem> invoiceItems = getInvoicesSession().getInvoiceItems(invoiceId);
         if(invoiceItems != null){
         	deliveryCertificateItems = new java.util.ArrayList<DeliveryCertificateItem>();
         	for(InvoiceItem invoiceItem : invoiceItems){
         		DeliveryCertificateItem dci = getFormSession().newDeliveryCertificateItem(invoiceItem);
         		deliveryCertificateItems.add(dci);
         	}
         	itemsTablePanel.refreshList(deliveryCertificateItems);
         }
    }
    
    protected Object onChooseForwarder() {

        OrganizationsListPanel listPanel = new OrganizationsListPanel(null);
        listPanel.setClassifier(getClassifiersFormSession().getClassifier("forwarder"));
        
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            Organization selectedForwarder = (Organization) listPanel.getSelectedRowObject();
            forwarderBranchAcaciaLookup.clearSelectedValue();
            forwarderContactPersonAcaciaLookup.clearSelectedValue();
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
            forwarderContactPersonAcaciaLookup.clearSelectedValue();
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
    
    @Action
    public void deliver(){
    	try{
	    	List<DeliveryCertificateItem> items = new ArrayList<DeliveryCertificateItem>(itemsTablePanel.getListData());
	    	setDialogResponse(DialogResponse.SAVE);
	        
	    	entity = getFormSession().deliverDeliveryCertificate(entity, assignment, items);
	        setSelectedValue(entity);
	    	close();
    	}catch(Exception ex){
    		ValidationException ve = extractValidationException(ex);
            if ( ve!=null ){
                String message = getValidationErrorsMessage(ve);
                refreshButton.setEnabled(true);
                JBErrorPane.showDialog(this, createSaveErrorInfo(message, null));
            }else{
                ex.printStackTrace();
                // TODO: Log that error
                String basicMessage = getResourceMap().getString("saveAction.Action.error.basicMessage", ex.getMessage());
                JBErrorPane.showDialog(this, createSaveErrorInfo(basicMessage, ex));
            }
    	}
    }
    
    private ErrorInfo createSaveErrorInfo(String basicMessage, Exception ex) {
        ResourceMap resource = getResourceMap();
        String title = resource.getString("saveAction.Action.error.title");

        String detailedMessage = resource.getString("saveAction.Action.error.detailedMessage");
        String category = ProductPanel.class.getName() + ": saveAction.";
        Level errorLevel = Level.WARNING;

        Map<String, String> state = new HashMap<String, String>();
        state.put("deliveryCertificateId", String.valueOf(entity.getDeliveryCertificateId()));
        
        ErrorInfo errorInfo = new ErrorInfo(title, basicMessage, detailedMessage, category, ex, errorLevel, state);
        return errorInfo;
    }
    
    @Action
    public void refresh(){
    	
    	if(entity.getDeliveryCertificateId() == null){
    		return;
    	}
    	
    	List<InvoiceItem> invoiceItems = getInvoicesSession().getInvoiceItems(entity.getDocumentAssignment().getDocumentId());
        if(deliveryCertificateItems == null){
        	deliveryCertificateItems = getFormSession().getDeliveryCertificateItems(entity.getDeliveryCertificateId());
        }
    	for(DeliveryCertificateItem item : deliveryCertificateItems){
        	for(InvoiceItem invoiceItem : invoiceItems){
        		if(invoiceItem.getInvoiceItemId().equals(item.getReferenceItemId())){
        			DeliveryCertificateItem dci = getFormSession().newDeliveryCertificateItem(invoiceItem);
        			item.setQuantity(dci.getQuantity());
        		}
        	}
        }
        itemsTablePanel.refreshList(deliveryCertificateItems);
    }
    
    @Override
    public void setReadonly(){
    	super.setReadonly();
    	this.itemsTablePanel.setReadonly();
    	this.deliverButton.setEnabled(false);
    	this.deliverButton.setVisible(false);
    }
    
    protected DeliveryCertificatesRemote getFormSession(){
        if(formSession == null){
            formSession = getBean(DeliveryCertificatesRemote.class);
        }

        return formSession;
    }
    
    protected InvoiceListRemote getInvoicesSession(){
        if(invoicesBean == null){
        	invoicesBean = getBean(InvoiceListRemote.class);
        }

        return invoicesBean;
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
        creationDatePicker = new com.cosmos.swingb.JBDatePicker();
        documentsAcaciaLookup = new com.cosmos.acacia.gui.AcaciaLookup();
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
        recipientBranchTextField = new com.cosmos.swingb.JBTextField();
        clientContactPersonLabel = new com.cosmos.swingb.JBLabel();
        recipientContactPersonAcaciaLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        deliveryMethodPanel = new com.cosmos.swingb.JBPanel();
        deliveryTypeComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        forwarderPanel = new com.cosmos.swingb.JBPanel();
        forwardNameLabel = new com.cosmos.swingb.JBLabel();
        forwarderBranchLabel = new com.cosmos.swingb.JBLabel();
        forwarderContactNameLabel = new com.cosmos.swingb.JBLabel();
        forwardersLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        forwarderBranchAcaciaLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        forwarderContactPersonAcaciaLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        itemsTableHolderPanel = new com.cosmos.acacia.gui.TableHolderPanel();

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

        creationDatePicker.setEditable(false);
        creationDatePicker.setName("creationDatePicker"); // NOI18N

        documentsAcaciaLookup.setName("documentsAcaciaLookup"); // NOI18N

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
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(reasonComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(numberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(93, 93, 93)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(documentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(documentsAcaciaLookup, 0, 0, Short.MAX_VALUE)
                    .addComponent(creationDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
                .addContainerGap())
        );
        detailsPanelLayout.setVerticalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reasonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reasonComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(creationDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(documentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(documentsAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(46, Short.MAX_VALUE))
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
                        .addGroup(creatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(creatorBranchTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(creatorOrganizationTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)))
                    .addGroup(creatorPanelLayout.createSequentialGroup()
                        .addComponent(supplerEmployeeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(creatorNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
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
                .addContainerGap(40, Short.MAX_VALUE))
        );

        recipientPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("recipientPanel.border.title"))); // NOI18N
        recipientPanel.setName("recipientPanel"); // NOI18N

        clientNameLabel.setText(resourceMap.getString("clientNameLabel.text")); // NOI18N
        clientNameLabel.setName("clientNameLabel"); // NOI18N

        recipientNameTextField.setText(resourceMap.getString("recipientNameTextField.text")); // NOI18N
        recipientNameTextField.setName("recipientNameTextField"); // NOI18N

        clientBranchLabel.setText(resourceMap.getString("clientBranchLabel.text")); // NOI18N
        clientBranchLabel.setName("clientBranchLabel"); // NOI18N

        recipientBranchTextField.setText(resourceMap.getString("recipientBranchTextField.text")); // NOI18N
        recipientBranchTextField.setName("recipientBranchTextField"); // NOI18N

        clientContactPersonLabel.setText(resourceMap.getString("clientContactPersonLabel.text")); // NOI18N
        clientContactPersonLabel.setName("clientContactPersonLabel"); // NOI18N

        recipientContactPersonAcaciaLookup.setName("recipientContactPersonAcaciaLookup"); // NOI18N

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(recipientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(recipientBranchTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(recipientNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                    .addComponent(recipientContactPersonAcaciaLookup, 0, 0, Short.MAX_VALUE))
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
                    .addComponent(recipientBranchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(recipientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientContactPersonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recipientContactPersonAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        deliveryMethodPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("deliveryMethodPanel.border.title"))); // NOI18N
        deliveryMethodPanel.setName("deliveryMethodPanel"); // NOI18N

        deliveryTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        deliveryTypeComboBox.setName("deliveryTypeComboBox"); // NOI18N

        forwarderPanel.setName("forwarderPanel"); // NOI18N

        forwardNameLabel.setText(resourceMap.getString("forwardNameLabel.text")); // NOI18N
        forwardNameLabel.setName("forwardNameLabel"); // NOI18N

        forwarderBranchLabel.setText(resourceMap.getString("forwarderBranchLabel.text")); // NOI18N
        forwarderBranchLabel.setName("forwarderBranchLabel"); // NOI18N

        forwarderContactNameLabel.setText(resourceMap.getString("forwarderContactNameLabel.text")); // NOI18N
        forwarderContactNameLabel.setName("forwarderContactNameLabel"); // NOI18N

        forwardersLookup.setName("forwardersLookup"); // NOI18N

        forwarderBranchAcaciaLookup.setName("forwarderBranchAcaciaLookup"); // NOI18N

        forwarderContactPersonAcaciaLookup.setName("forwarderContactPersonAcaciaLookup"); // NOI18N

        javax.swing.GroupLayout forwarderPanelLayout = new javax.swing.GroupLayout(forwarderPanel);
        forwarderPanel.setLayout(forwarderPanelLayout);
        forwarderPanelLayout.setHorizontalGroup(
            forwarderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(forwarderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(forwarderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(forwardNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forwarderContactNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forwarderBranchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(forwarderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(forwarderBranchAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forwardersLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forwarderContactPersonAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        forwarderPanelLayout.setVerticalGroup(
            forwarderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(forwarderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(forwarderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(forwarderPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(forwardersLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(forwardNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(forwarderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(forwarderBranchAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forwarderBranchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(forwarderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(forwarderContactPersonAcaciaLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forwarderContactNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout deliveryMethodPanelLayout = new javax.swing.GroupLayout(deliveryMethodPanel);
        deliveryMethodPanel.setLayout(deliveryMethodPanelLayout);
        deliveryMethodPanelLayout.setHorizontalGroup(
            deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deliveryMethodPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deliveryTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forwarderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(275, Short.MAX_VALUE))
        );
        deliveryMethodPanelLayout.setVerticalGroup(
            deliveryMethodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deliveryMethodPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(deliveryTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forwarderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        itemsTableHolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("itemsTableHolderPanel.border.title"))); // NOI18N
        itemsTableHolderPanel.setName("itemsTableHolderPanel"); // NOI18N

        javax.swing.GroupLayout itemsTableHolderPanelLayout = new javax.swing.GroupLayout(itemsTableHolderPanel);
        itemsTableHolderPanel.setLayout(itemsTableHolderPanelLayout);
        itemsTableHolderPanelLayout.setHorizontalGroup(
            itemsTableHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );
        itemsTableHolderPanelLayout.setVerticalGroup(
            itemsTableHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(itemsTableHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(creatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recipientPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
                    .addComponent(deliveryMethodPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(detailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(creatorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(recipientPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemsTableHolderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deliveryMethodPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void numberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberTextFieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_numberTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel clientBranchLabel;
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
    private com.cosmos.swingb.JBLabel documentLabel;
    private com.cosmos.acacia.gui.AcaciaLookup documentsAcaciaLookup;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.swingb.JBLabel forwardNameLabel;
    private com.cosmos.acacia.gui.AcaciaLookup forwarderBranchAcaciaLookup;
    private com.cosmos.swingb.JBLabel forwarderBranchLabel;
    private com.cosmos.swingb.JBLabel forwarderContactNameLabel;
    private com.cosmos.acacia.gui.AcaciaLookup forwarderContactPersonAcaciaLookup;
    private com.cosmos.swingb.JBPanel forwarderPanel;
    private com.cosmos.acacia.gui.AcaciaLookup forwardersLookup;
    private com.cosmos.acacia.gui.TableHolderPanel itemsTableHolderPanel;
    private com.cosmos.swingb.JBLabel numberLabel;
    private com.cosmos.swingb.JBTextField numberTextField;
    private com.cosmos.swingb.JBLabel organizationLabel;
    private com.cosmos.acacia.gui.AcaciaComboBox reasonComboBox;
    private com.cosmos.swingb.JBLabel reasonLabel;
    private com.cosmos.swingb.JBTextField recipientBranchTextField;
    private com.cosmos.acacia.gui.AcaciaLookup recipientContactPersonAcaciaLookup;
    private com.cosmos.swingb.JBTextField recipientNameTextField;
    private com.cosmos.swingb.JBPanel recipientPanel;
    private com.cosmos.swingb.JBLabel supplerEmployeeLabel;
    private com.cosmos.swingb.JBLabel supplierBranchLabel;
    // End of variables declaration//GEN-END:variables

}
