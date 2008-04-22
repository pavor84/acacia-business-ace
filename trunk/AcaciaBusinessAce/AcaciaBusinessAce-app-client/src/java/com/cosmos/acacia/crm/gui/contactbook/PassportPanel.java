package com.cosmos.acacia.crm.gui.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.impl.PassportsListRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Passport;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class PassportPanel extends BaseEntityPanel {

    private Logger log = Logger.getLogger(PassportPanel.class);

    /** Creates new form ContactPersonPanel */
    public PassportPanel(Passport passport) {
        super(passport.getDataObject().getParentDataObject());
        this.passport = passport;
        init();
    }

    /** Creates new form ContactPersonPanel */
    public PassportPanel(DataObject parentDataObject) {
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

        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        numberTextField = new com.cosmos.swingb.JBTextField();
        otherInfoTextField = new com.cosmos.swingb.JBTextField();
        issuerLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        issuerBranchLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        passportTypeComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        issueDateDatePicker = new com.cosmos.swingb.JBDatePicker();
        expirationDateDatePicker = new com.cosmos.swingb.JBDatePicker();
        numberLabel = new com.cosmos.swingb.JBLabel();
        issueDateLabel = new com.cosmos.swingb.JBLabel();
        expirationDateLabel = new com.cosmos.swingb.JBLabel();
        typeLabel = new com.cosmos.swingb.JBLabel();
        issuerLabel = new com.cosmos.swingb.JBLabel();
        issuerBranchLabel = new com.cosmos.swingb.JBLabel();
        otherInfoLabel = new com.cosmos.swingb.JBLabel();

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        numberTextField.setName("numberTextField"); // NOI18N

        otherInfoTextField.setName("otherInfoTextField"); // NOI18N

        issuerLookup.setName("issuerLookup"); // NOI18N

        issuerBranchLookup.setName("issuerBranchLookup"); // NOI18N

        passportTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel());
        passportTypeComboBox.setName("passportTypeComboBox"); // NOI18N

        issueDateDatePicker.setName("issueDateDatePicker"); // NOI18N

        expirationDateDatePicker.setName("expirationDateDatePicker"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PassportPanel.class);
        numberLabel.setText(resourceMap.getString("numberLabel.text")); // NOI18N
        numberLabel.setName("numberLabel"); // NOI18N

        issueDateLabel.setText(resourceMap.getString("issueDateLabel.text")); // NOI18N
        issueDateLabel.setName("issueDateLabel"); // NOI18N

        expirationDateLabel.setText(resourceMap.getString("expirationDateLabel.text")); // NOI18N
        expirationDateLabel.setName("expirationDateLabel"); // NOI18N

        typeLabel.setText(resourceMap.getString("typeLabel.text")); // NOI18N
        typeLabel.setName("typeLabel"); // NOI18N

        issuerLabel.setText(resourceMap.getString("issuerLabel.text")); // NOI18N
        issuerLabel.setName("issuerLabel"); // NOI18N

        issuerBranchLabel.setText(resourceMap.getString("issuerBranchLabel.text")); // NOI18N
        issuerBranchLabel.setName("issuerBranchLabel"); // NOI18N

        otherInfoLabel.setText(resourceMap.getString("otherInfoLabel.text")); // NOI18N
        otherInfoLabel.setName("otherInfoLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(issueDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(expirationDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(issuerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(issuerBranchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(otherInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(passportTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(issuerBranchLookup, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(issuerLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(otherInfoTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(expirationDateDatePicker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(issueDateDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(numberTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)))))
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {issuerBranchLookup, issuerLookup, numberTextField, otherInfoTextField, passportTypeComboBox});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(issueDateDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(issueDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(expirationDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(expirationDateDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passportTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(issuerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(issuerLookup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(issuerBranchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(issuerBranchLookup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otherInfoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(otherInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.swingb.JBDatePicker expirationDateDatePicker;
    private com.cosmos.swingb.JBLabel expirationDateLabel;
    private com.cosmos.swingb.JBDatePicker issueDateDatePicker;
    private com.cosmos.swingb.JBLabel issueDateLabel;
    private com.cosmos.swingb.JBLabel issuerBranchLabel;
    private com.cosmos.acacia.gui.AcaciaLookup issuerBranchLookup;
    private com.cosmos.swingb.JBLabel issuerLabel;
    private com.cosmos.acacia.gui.AcaciaLookup issuerLookup;
    private com.cosmos.swingb.JBLabel numberLabel;
    private com.cosmos.swingb.JBTextField numberTextField;
    private com.cosmos.swingb.JBLabel otherInfoLabel;
    private com.cosmos.swingb.JBTextField otherInfoTextField;
    private com.cosmos.acacia.gui.AcaciaComboBox passportTypeComboBox;
    private com.cosmos.swingb.JBLabel typeLabel;
    // End of variables declaration//GEN-END:variables

    @EJB
    private PassportsListRemote formSession;

    private EntityProperties entityProps;

    private BindingGroup passportBindingGroup;
    private Passport passport;

    private Binding issuerBinding;
    private Binding issuerBranchBinding;

    @Override
    protected void initData() {
        setResizable(false);

        log.info("initData().passport: " + passport);

        if(passport == null)
        {
            passport = getFormSession().newPassport();
        }

        passportBindingGroup = new BindingGroup();

        entityProps = getPassportEntityProperties();

        numberTextField.bind(passportBindingGroup, passport, entityProps.getPropertyDetails("passportNumber"));
        otherInfoTextField.bind(passportBindingGroup, passport, entityProps.getPropertyDetails("additionalInfo"));

        issueDateDatePicker.bind(passportBindingGroup, passport, entityProps.getPropertyDetails("issueDate"));
        expirationDateDatePicker.bind(passportBindingGroup, passport, entityProps.getPropertyDetails("expirationDate"));

        passportTypeComboBox.bind(passportBindingGroup, getPassportTypes(), passport, entityProps.getPropertyDetails("passportType"));

        issuerBinding = issuerLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseIssuer();
                }
            }, passportBindingGroup,
            passport,
            entityProps.getPropertyDetails("issuer"),
            "${organizationName}",
            UpdateStrategy.READ_WRITE);

        issuerBranchBinding = issuerBranchLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseIssuerBranch();
                }
            }, passportBindingGroup,
            passport,
            entityProps.getPropertyDetails("issuerBranch"),
            "${addressName}",
            UpdateStrategy.READ_WRITE);

        issuerBranchLookup.setEnabled(issuerBinding.isContentValid());

        passportBindingGroup.bind();
    }

    protected Object onChooseIssuer() {
        OrganizationsListPanel listPanel = new OrganizationsListPanel(null, new Classifier());
        // TODO : classifiers!

        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            Object selected = listPanel.getSelectedRowObject();
            issuerBranchLookup.setEnabled(selected != null);
            return selected;
        } else {
            return null;
        }
    }

     protected Object onChooseIssuerBranch() {
        DataObject parent = null;
        try {
            parent =
                ((Organization) issuerBinding.getSourceProperty()
                    .getValue(issuerBinding.getSourceObject())).getDataObject();
        } catch (NullPointerException ex) {
            // Ignore
        }

        AddressListPanel listPanel = new AddressListPanel(parent);
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            return listPanel.getSelectedRowObject();
        } else {
            return null;
        }
    }


    protected PassportsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(PassportsListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected List<DbResource> getPassportTypes()
    {
        return getFormSession().getPassportTypes();
    }

    @Action
    @Override
    public void closeAction() {
        setDialogResponse(DialogResponse.CLOSE);
        close();
    }

    protected EntityProperties getPassportEntityProperties()
    {
        return getFormSession().getPassportEntityProperties();
    }

    @Override
    public BindingGroup getBindingGroup() {
        return passportBindingGroup;
    }

    @Override
    public void performSave(boolean closeAfter) {
        log.info("Save: passport: " + passport);
        passport = getFormSession().savePassport(passport, getParentDataObject());
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(passport);
        if (closeAfter)
            close();
    }

    @Override
    public Object getEntity() {
        return passport;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }
}
