package com.cosmos.acacia.crm.gui.contactbook;

import com.cosmos.acacia.crm.bl.contactbook.impl.BankDetailsListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.gui.LookupRecordDeletionListener;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class BankDetailPanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(BankDetailPanel.class);

    /** Creates new form ContactPersonPanel */
    public BankDetailPanel(BankDetail bankDetail) {
        super(bankDetail.getDataObject().getParentDataObjectId());
        this.bankDetail = bankDetail;
        init();
    }

    /** Creates new form ContactPersonPanel */
    public BankDetailPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
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

        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        ibanLabel = new com.cosmos.swingb.JBLabel();
        ibanTextField = new com.cosmos.swingb.JBTextField();
        bankLabel = new com.cosmos.swingb.JBLabel();
        branchLabel = new com.cosmos.swingb.JBLabel();
        accountLabel = new com.cosmos.swingb.JBLabel();
        accountTextField = new com.cosmos.swingb.JBTextField();
        bicLabel = new com.cosmos.swingb.JBLabel();
        bicTextField = new com.cosmos.swingb.JBTextField();
        swiftLabel = new com.cosmos.swingb.JBLabel();
        swiftTextField = new com.cosmos.swingb.JBTextField();
        contactLabel = new com.cosmos.swingb.JBLabel();
        bankLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        branchLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        defaultCheckBox = new com.cosmos.swingb.JBCheckBox();
        currencyComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        contactLabel1 = new com.cosmos.swingb.JBLabel();
        contactLookup = new com.cosmos.acacia.gui.AcaciaLookup();

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(BankDetailPanel.class);
        ibanLabel.setText(resourceMap.getString("ibanLabel.text")); // NOI18N
        ibanLabel.setName("ibanLabel"); // NOI18N

        ibanTextField.setText(resourceMap.getString("ibanTextField.text")); // NOI18N
        ibanTextField.setName("ibanTextField"); // NOI18N

        bankLabel.setText(resourceMap.getString("bankLabel.text")); // NOI18N
        bankLabel.setName("bankLabel"); // NOI18N

        branchLabel.setText(resourceMap.getString("branchLabel.text")); // NOI18N
        branchLabel.setName("branchLabel"); // NOI18N

        accountLabel.setText(resourceMap.getString("accountLabel.text")); // NOI18N
        accountLabel.setName("accountLabel"); // NOI18N

        accountTextField.setText(resourceMap.getString("accountTextField.text")); // NOI18N
        accountTextField.setName("accountTextField"); // NOI18N

        bicLabel.setText(resourceMap.getString("bicLabel.text")); // NOI18N
        bicLabel.setName("bicLabel"); // NOI18N

        bicTextField.setText(resourceMap.getString("bicTextField.text")); // NOI18N
        bicTextField.setName("bicTextField"); // NOI18N

        swiftLabel.setText(resourceMap.getString("swiftLabel.text")); // NOI18N
        swiftLabel.setName("swiftLabel"); // NOI18N

        swiftTextField.setText(resourceMap.getString("swiftTextField.text")); // NOI18N
        swiftTextField.setName("swiftTextField"); // NOI18N

        contactLabel.setText(resourceMap.getString("contactLabel.text")); // NOI18N
        contactLabel.setName("contactLabel"); // NOI18N

        bankLookup.setName("bankLookup"); // NOI18N

        branchLookup.setName("branchLookup"); // NOI18N

        defaultCheckBox.setText(resourceMap.getString("defaultCheckBox.text")); // NOI18N
        defaultCheckBox.setName("defaultCheckBox"); // NOI18N

        currencyComboBox.setModel(new javax.swing.DefaultComboBoxModel());
        currencyComboBox.setName("currencyComboBox"); // NOI18N

        contactLabel1.setText(resourceMap.getString("contactLabel1.text")); // NOI18N
        contactLabel1.setName("contactLabel1"); // NOI18N

        contactLookup.setName("contactLookup"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bankLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(branchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ibanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(accountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(swiftLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contactLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(branchLookup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bankLookup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ibanTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(accountTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bicTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(swiftTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(currencyComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(defaultCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {accountTextField, bankLookup, bicTextField, branchLookup, currencyComboBox, ibanTextField, swiftTextField});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(bankLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bankLookup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(branchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(branchLookup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ibanTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ibanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(accountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bicTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(swiftLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(swiftTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currencyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contactLookup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(defaultCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel accountLabel;
    private com.cosmos.swingb.JBTextField accountTextField;
    private com.cosmos.swingb.JBLabel bankLabel;
    private com.cosmos.acacia.gui.AcaciaLookup bankLookup;
    private com.cosmos.swingb.JBLabel bicLabel;
    private com.cosmos.swingb.JBTextField bicTextField;
    private com.cosmos.swingb.JBLabel branchLabel;
    private com.cosmos.acacia.gui.AcaciaLookup branchLookup;
    private com.cosmos.swingb.JBLabel contactLabel;
    private com.cosmos.swingb.JBLabel contactLabel1;
    private com.cosmos.acacia.gui.AcaciaLookup contactLookup;
    private com.cosmos.acacia.gui.AcaciaComboBox currencyComboBox;
    private com.cosmos.swingb.JBCheckBox defaultCheckBox;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.swingb.JBLabel ibanLabel;
    private com.cosmos.swingb.JBTextField ibanTextField;
    private com.cosmos.swingb.JBLabel swiftLabel;
    private com.cosmos.swingb.JBTextField swiftTextField;
    // End of variables declaration//GEN-END:variables

    @EJB
    private BankDetailsListRemote formSession;

    private EntityProperties entityProps;

    private BindingGroup bankDetailBindingGroup;
    private BankDetail bankDetail;

    private Binding bankBinding;
    private Binding branchBinding;
    private Binding contactBinding;
    
    @Override
    protected void initData() {
        setResizable(false);

        log.info("initData().bankDetail: " + bankDetail);
        if(bankDetail == null)
        {
            bankDetail = getFormSession().newBankDetail();
        }

        bankDetailBindingGroup = new BindingGroup();

        entityProps = getBankDetailEntityProperties();

        ibanTextField.bind(bankDetailBindingGroup, bankDetail, entityProps.getPropertyDetails("iban"));
        accountTextField.bind(bankDetailBindingGroup, bankDetail, entityProps.getPropertyDetails("bankAccount"));
        bicTextField.bind(bankDetailBindingGroup, bankDetail, entityProps.getPropertyDetails("bic"));
        swiftTextField.bind(bankDetailBindingGroup, bankDetail, entityProps.getPropertyDetails("swiftCode"));
        defaultCheckBox.bind(bankDetailBindingGroup, bankDetail, entityProps.getPropertyDetails("isDefault"));

        currencyComboBox.bind(bankDetailBindingGroup,
                getCurrencies(),
                bankDetail,
                entityProps.getPropertyDetails("currency"));

        bankBinding = bankLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseBank();
                }
            }, bankDetailBindingGroup,
            bankDetail,
            entityProps.getPropertyDetails("bank"),
            "${organizationName}",
            UpdateStrategy.READ_WRITE);
       bankBinding.addBindingListener(new AbstractBindingListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                branchLookup.clearSelectedValue();
                contactLookup.clearSelectedValue();
            }
        });
        
        branchBinding = branchLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseBankBranch();
                }
            }, bankDetailBindingGroup,
            bankDetail,
            entityProps.getPropertyDetails("bankBranch"),
            "${addressName}",
            UpdateStrategy.READ_WRITE);

        branchBinding.addBindingListener(new AbstractBindingListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                contactLookup.clearSelectedValue();
            }
        });
        
        contactBinding = contactLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseContact();
                }
            }, bankDetailBindingGroup,
            bankDetail,
            entityProps.getPropertyDetails("bankContact"),
            "${firstName} ${secondName} ${lastName}",
            UpdateStrategy.READ_WRITE);
        
        bankDetailBindingGroup.bind();
    }

    protected Object onChooseBank() {
        OrganizationsListPanel listPanel = new OrganizationsListPanel(null, new Classifier());
        listPanel.setClassifier(getClassifiersFormSession().getClassifier("bank"));
        
        Organization oldBank = bankDetail.getBank();
        
        LookupRecordDeletionListener deletionListener = new LookupRecordDeletionListener(oldBank);
        deletionListener.addTargetLookup(bankLookup);
        deletionListener.addTargetLookup(branchLookup);
        deletionListener.addTargetLookup(contactLookup);
        listPanel.addTableModificationListener(deletionListener);
        
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            Object selected = listPanel.getSelectedRowObject();
            
            if (selected  == null || !selected.equals(oldBank)){
                branchLookup.clearSelectedValue();
                contactLookup.clearSelectedValue();
            }
            return selected;
        } else {
            return null;
        }
    }

     protected Object onChooseBankBranch() {
        if (!bankBinding.isContentValid()) {
            JOptionPane.showMessageDialog(this, 
                getResourceMap().getString("BankDetailPanel.selectBank"),
                getResourceMap().getString("BankDetailPanel.selectBankTitle"),
                JOptionPane.WARNING_MESSAGE);
            return null;
        }
            
        DataObject parent = null;
        try {
            parent =
                ((Organization) bankBinding.getSourceProperty()
                    .getValue(bankBinding.getSourceObject())).getDataObject();
        } catch (NullPointerException ex) {
            // Ignore
        }

        AddressListPanel listPanel = new AddressListPanel(parent.getDataObjectId());
        
        Address oldBranch = bankDetail.getBankBranch();
        
        LookupRecordDeletionListener deletionListener = new LookupRecordDeletionListener(oldBranch, branchLookup);
        deletionListener.addTargetLookup(contactLookup);
        listPanel.addTableModificationListener(deletionListener);
        
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
           Object selected = listPanel.getSelectedRowObject();
           
           return selected;
        }else{
            return null;
        }
    }

    protected Object onChooseContact() {
        if (!branchBinding.isContentValid()) {
            JOptionPane.showMessageDialog(this, 
                getResourceMap().getString("BankDetailPanel.selectBranch"),
                getResourceMap().getString("BankDetailPanel.selectBranchTitle"),
                JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        DataObject parent = null;
        try {
            parent = ((Address) branchBinding.getSourceProperty()
                    .getValue(branchBinding.getSourceObject())).getDataObject();
        } catch (Exception ex){
            // Ignore
        }
        ContactPersonsListPanel listPanel = new ContactPersonsListPanel(parent.getDataObjectId());
        
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
           ContactPerson selected = (ContactPerson) listPanel.getSelectedRowObject();
           return selected.getContact();
        } else {
            return null;
        }
        
    }
    protected List<Person> getContacts(Object selectedObject)
    {
        if (selectedObject == null)
            return new ArrayList<Person>();

        DataObject parent = ((DataObjectBean) selectedObject).getDataObject();
        List<Person> contacts = getFormSession().getBankContacts(parent);

        log.info("Persons: " + contacts.size());
        return contacts;
    }

    protected BankDetailsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(BankDetailsListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected EntityProperties getBankDetailEntityProperties()
    {
        return getFormSession().getBankDetailEntityProperties();
    }

    private List<DbResource> getCurrencies()
    {
        return getFormSession().getCurrencies();
    }


    @Override
    public BindingGroup getBindingGroup() {
        return bankDetailBindingGroup;
    }

    @Override
    public void performSave(boolean closeAfter) {
        log.info("Save: bankDetail: " + bankDetail);
        bankDetail = getFormSession().saveBankDetail(bankDetail, getParentDataObject());
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(bankDetail);
        if (closeAfter)
            close();
    }

    @Override
    public Object getEntity() {
        return bankDetail;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }
}
