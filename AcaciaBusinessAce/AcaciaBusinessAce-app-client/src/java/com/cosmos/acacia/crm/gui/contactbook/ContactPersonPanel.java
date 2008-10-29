package com.cosmos.acacia.crm.gui.contactbook;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class ContactPersonPanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(ContactPersonPanel.class);

    /** Creates new form ContactPersonPanel */
    public ContactPersonPanel(ContactPerson contactPerson) {
        super(contactPerson.getDataObject().getParentDataObjectId());
        this.contactPerson = contactPerson;
        init();
    }

    /** Creates new form ContactPersonPanel */
    public ContactPersonPanel(BigInteger parentDataObjectId) {
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
        typeLabel = new com.cosmos.swingb.JBLabel();
        personLabel = new com.cosmos.swingb.JBLabel();
        typeLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        personLookup = new com.cosmos.acacia.gui.AcaciaLookup();

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ContactPersonPanel.class);
        typeLabel.setText(resourceMap.getString("typeLabel.text")); // NOI18N
        typeLabel.setName("typeLabel"); // NOI18N

        personLabel.setText(resourceMap.getString("personLabel.text")); // NOI18N
        personLabel.setName("personLabel"); // NOI18N

        typeLookup.setName("typeLookup"); // NOI18N

        personLookup.setName("personLookup"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(typeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(personLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(personLookup, 0, 0, Short.MAX_VALUE)
                            .addComponent(typeLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(typeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(typeLookup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(personLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(personLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.swingb.JBLabel personLabel;
    private com.cosmos.acacia.gui.AcaciaLookup personLookup;
    private com.cosmos.swingb.JBLabel typeLabel;
    private com.cosmos.acacia.gui.AcaciaLookup typeLookup;
    // End of variables declaration//GEN-END:variables

    @EJB
    private AddressesListRemote formSession;

    private BindingGroup contactPersonBindingGroup;
    private ContactPerson contactPerson;
    private Binding typeBinding;
    private Binding personBinding;
    /** Indicates whether the addresses are internal to the organization */
    private boolean isInternal;

    @Override
    protected void initData() {
        setResizable(false);

        System.out.println("initData().contactPerson: " + contactPerson);
        if(contactPerson == null)
        {
            contactPerson = getFormSession().newContactPerson();
        }

        contactPersonBindingGroup = new BindingGroup();

        EntityProperties entityProps = getContactPersonEntityProperties();

        typeBinding = typeLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseType();
                }
            }, contactPersonBindingGroup,
            contactPerson,
            entityProps.getPropertyDetails("positionType"),
            "${positionTypeName}",
            UpdateStrategy.READ_WRITE);

        personBinding = personLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChoosePerson();
                }
            }, contactPersonBindingGroup,
            contactPerson,
            entityProps.getPropertyDetails("contact"),
            entityProps.getPropertyDetails("contact")
                    .getCustomDisplay().replaceAll("contact.", ""),
            UpdateStrategy.READ_WRITE);


        contactPersonBindingGroup.bind();
    }

    protected Object onChooseType()
    {

        if (!isInternal) {
            PositionTypesListPanel listPanel = new PositionTypesListPanel(
                    getParentDataObject().getParentDataObjectId(), false);

            DialogResponse dResponse = listPanel.showDialog(this);
            if ( DialogResponse.SELECT.equals(dResponse) ){
                return listPanel.getSelectedRowObject();
            }

        } else {
            PositionsHierarchyTreePanel listPanel = new PositionsHierarchyTreePanel(
                    getParentDataObject().getParentDataObjectId());

            DialogResponse dResponse = listPanel.showDialog(this);
            if ( DialogResponse.SELECT.equals(dResponse) ){
                return listPanel.getListPanel().getSelectedRowObject();
            }
        }

        return null;
    }

    protected Object onChoosePerson() {
        PersonsListPanel listPanel = new PersonsListPanel(getOrganizationDataObjectId(), isInternal);

        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            return listPanel.getSelectedRowObject();
        }
        return null;
    }

    protected AddressesListRemote getFormSession()
    {
        if(formSession == null)
            formSession = getBean(AddressesListRemote.class);

        return formSession;
    }

    protected EntityProperties getContactPersonEntityProperties()
    {
        return getFormSession().getContactPersonEntityProperties();
    }

    private List<Person> getPersons() {
        return getFormSession().getPersons();
    }

    private List<PositionType> getPositionTypes() {
        return getFormSession().getPositionTypes(getParentDataObject(), getOrganizationDataObjectId());
    }

    @Override
    public BindingGroup getBindingGroup() {
        return contactPersonBindingGroup;
    }

    @Override
    public void performSave(boolean closeAfter) {
        log.info("Save: contactPerson: " + contactPerson);
        contactPerson = getFormSession().saveContactPerson(contactPerson, getParentDataObjectId());
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(contactPerson);
        if (closeAfter)
            close();
    }

    @Override
    public Object getEntity() {
        return contactPerson;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    public void setInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }
}
