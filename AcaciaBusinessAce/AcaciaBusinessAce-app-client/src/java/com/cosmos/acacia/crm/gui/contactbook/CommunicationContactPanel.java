package com.cosmos.acacia.crm.gui.contactbook;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.data.contacts.CommunicationContact;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

import java.util.List;
import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class CommunicationContactPanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(CommunicationContactPanel.class);

    /** Creates new form CommunicationContactPanel */
    public CommunicationContactPanel(CommunicationContact communicationContact) {
        super(communicationContact.getParentId());
        this.communicationContact = communicationContact;
        throw new UnsupportedOperationException("ToDO");
        //this.contactPerson = communicationContact.getContactPerson();
        //init();
    }

    @Override
    protected void init() {
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
        typeComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        typeLabel = new com.cosmos.swingb.JBLabel();
        valueLabel = new com.cosmos.swingb.JBLabel();
        valueTextField = new com.cosmos.swingb.JBTextField();

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeComboBox.setName("typeComboBox"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(CommunicationContactPanel.class);
        typeLabel.setText(resourceMap.getString("typeLabel.text")); // NOI18N
        typeLabel.setName("typeLabel"); // NOI18N

        valueLabel.setText(resourceMap.getString("valueLabel.text")); // NOI18N
        valueLabel.setName("valueLabel"); // NOI18N

        valueTextField.setName("valueTextField"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(valueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(typeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                            .addComponent(valueTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)))
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.acacia.gui.AcaciaComboBox typeComboBox;
    private com.cosmos.swingb.JBLabel typeLabel;
    private com.cosmos.swingb.JBLabel valueLabel;
    private com.cosmos.swingb.JBTextField valueTextField;
    // End of variables declaration//GEN-END:variables
    @EJB
    private AddressesListRemote formSession;
    private BindingGroup bindingGroup;
    private CommunicationContact communicationContact;
    private ContactPerson contactPerson;

    @Override
    protected void initData() {
        setResizable(false);

        log.info("initData().communicationContact: " + communicationContact);

        boolean enabledCommType = communicationContact.getCommunicationContactId() == null && communicationContact.getCommunicationType() != null;

        BindingGroup bg = getBindingGroup();

        EntityProperties entityProps = getCommunicationContactEntityProperties();

        typeComboBox.bind(bg, getCommunicationTypes(), communicationContact, entityProps.getEntityProperty("communicationType"));
        valueTextField.bind(bg, communicationContact, entityProps.getEntityProperty("communicationValue"));

        bg.bind();

        if(enabledCommType) {
            typeComboBox.setEnabled(false);
        }
    }

    protected AddressesListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(AddressesListRemote.class);
        }

        return formSession;
    }

    protected EntityProperties getCommunicationContactEntityProperties() {
        return getFormSession().getCommunicationContactEntityProperties();
    }

    private List<DbResource> getCommunicationTypes() {
        return getFormSession().getCommunicationTypes();
    }

    @Override
    public BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public void performSave(boolean closeAfter) {
        log.info("Save: communicationContact: " + communicationContact);
        communicationContact = getFormSession().saveCommunicationContact(
                communicationContact, getParentDataObjectId(), contactPerson);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(communicationContact);
        if (closeAfter) {
            close();
        }
    }

    @Override
    public Object getEntity() {
        return communicationContact;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }
}
