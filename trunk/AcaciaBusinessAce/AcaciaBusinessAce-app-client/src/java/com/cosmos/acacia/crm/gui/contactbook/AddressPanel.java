/*
 * AddressPanel.java
 *
 * Created on 29 March 2008, 09:05
 */

package com.cosmos.acacia.crm.gui.contactbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Country;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.listeners.TableModificationListener;
import java.awt.Component;
import java.math.BigInteger;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class AddressPanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(AddressPanel.class);

    /** Creates new form AddressPanel */
    public AddressPanel(Address address) {
        super(address.getDataObject().getParentDataObjectId());
        this.address = address;
        init();
    }

    /** Creates new form AddressPanel */
    public AddressPanel(BigInteger parentDataObjectId) {
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

        nameLabel = new com.cosmos.swingb.JBLabel();
        countryLabel = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();
        countryComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        postalCodeLabel = new com.cosmos.swingb.JBLabel();
        postalCodeTextField = new com.cosmos.swingb.JBTextField();
        addressPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        postalAddressTextPane = new com.cosmos.swingb.JBTextPane();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        contactPersonsPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        communicationContactsPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        addressNameTextField = new com.cosmos.swingb.JBTextField();
        bankDetailsPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        cityLookup = new com.cosmos.acacia.gui.AcaciaLookup();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AddressPanel.class);
        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        countryLabel.setText(resourceMap.getString("countryLabel.text")); // NOI18N
        countryLabel.setName("countryLabel"); // NOI18N

        cityLabel.setText(resourceMap.getString("cityLabel.text")); // NOI18N
        cityLabel.setName("cityLabel"); // NOI18N

        countryComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        countryComboBox.setName("countryComboBox"); // NOI18N

        postalCodeLabel.setText(resourceMap.getString("postalCodeLabel.text")); // NOI18N
        postalCodeLabel.setName("postalCodeLabel"); // NOI18N

        postalCodeTextField.setText(resourceMap.getString("postalCodeTextField.text")); // NOI18N
        postalCodeTextField.setName("postalCodeTextField"); // NOI18N

        addressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("addressPanel.border.title"))); // NOI18N
        addressPanel.setName("addressPanel"); // NOI18N

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setName("jScrollPane3"); // NOI18N

        postalAddressTextPane.setName("postalAddressTextPane"); // NOI18N
        jScrollPane3.setViewportView(postalAddressTextPane);

        javax.swing.GroupLayout addressPanelLayout = new javax.swing.GroupLayout(addressPanel);
        addressPanel.setLayout(addressPanelLayout);
        addressPanelLayout.setHorizontalGroup(
            addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
                .addContainerGap())
        );
        addressPanelLayout.setVerticalGroup(
            addressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addressPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("descriptionPanel.border.title"))); // NOI18N
        descriptionPanel.setName("descriptionPanel"); // NOI18N

        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setName("jScrollPane4"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        jScrollPane4.setViewportView(descriptionTextPane);

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap())
        );

        contactPersonsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("contactPersonsPanel.border.title"))); // NOI18N
        contactPersonsPanel.setName("contactPersonsPanel"); // NOI18N

        javax.swing.GroupLayout contactPersonsPanelLayout = new javax.swing.GroupLayout(contactPersonsPanel);
        contactPersonsPanel.setLayout(contactPersonsPanelLayout);
        contactPersonsPanelLayout.setHorizontalGroup(
            contactPersonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        contactPersonsPanelLayout.setVerticalGroup(
            contactPersonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 223, Short.MAX_VALUE)
        );

        communicationContactsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("communicationContactsPanel.border.title"))); // NOI18N
        communicationContactsPanel.setName("communicationContactsPanel"); // NOI18N

        javax.swing.GroupLayout communicationContactsPanelLayout = new javax.swing.GroupLayout(communicationContactsPanel);
        communicationContactsPanel.setLayout(communicationContactsPanelLayout);
        communicationContactsPanelLayout.setHorizontalGroup(
            communicationContactsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );
        communicationContactsPanelLayout.setVerticalGroup(
            communicationContactsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 223, Short.MAX_VALUE)
        );

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        addressNameTextField.setText(resourceMap.getString("addressNameTextField.text")); // NOI18N
        addressNameTextField.setName("addressNameTextField"); // NOI18N

        bankDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("bankDetailsPanel.border.title"))); // NOI18N
        bankDetailsPanel.setName("bankDetailsPanel"); // NOI18N

        javax.swing.GroupLayout bankDetailsPanelLayout = new javax.swing.GroupLayout(bankDetailsPanel);
        bankDetailsPanel.setLayout(bankDetailsPanelLayout);
        bankDetailsPanelLayout.setHorizontalGroup(
            bankDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 852, Short.MAX_VALUE)
        );
        bankDetailsPanelLayout.setVerticalGroup(
            bankDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        cityLookup.setName("cityLookup"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bankDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addressNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(countryLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(countryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cityLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cityLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(postalCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(postalCodeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(contactPersonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(communicationContactsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(addressPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(countryComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityLookup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(postalCodeTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(addressPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contactPersonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(communicationContactsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bankDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(cityLabel)
                .addContainerGap(686, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(686, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(countryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(687, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(postalCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(686, 686, 686))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBTextField addressNameTextField;
    private com.cosmos.swingb.JBPanel addressPanel;
    private com.cosmos.acacia.gui.TableHolderPanel bankDetailsPanel;
    private javax.swing.JLabel cityLabel;
    private com.cosmos.acacia.gui.AcaciaLookup cityLookup;
    private com.cosmos.acacia.gui.TableHolderPanel communicationContactsPanel;
    private com.cosmos.acacia.gui.TableHolderPanel contactPersonsPanel;
    private com.cosmos.acacia.gui.AcaciaComboBox countryComboBox;
    private javax.swing.JLabel countryLabel;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private com.cosmos.swingb.JBLabel nameLabel;
    private com.cosmos.swingb.JBTextPane postalAddressTextPane;
    private com.cosmos.swingb.JBLabel postalCodeLabel;
    private com.cosmos.swingb.JBTextField postalCodeTextField;
    // End of variables declaration//GEN-END:variables

    @EJB
    private AddressesListRemote formSession;

    private BindingGroup addressBindingGroup;
    private Address address;
    private CommunicationContactsListPanel communicationContactsTable;
    private ContactPersonsListPanel contactPersonsTable;
    private BankDetailsListPanel bankDetailsTable;
    private Binding cityBinding;
    private Country country;
    /** Indicates whether the addresses are internal to the organization */
    private boolean isInternal;

    @Override
    protected void initData() {
        setResizable(true);

      String defaultAddressName = "";
      String defaultMainAddressName = "";
      if (getParentDataObject() != null){
            String parentObjectType = getParentDataObject().getDataObjectType().getDataObjectType();
            ResourceMap resourceMap = getResourceMap();

            if (parentObjectType.indexOf("Person") != -1){
                setTitle(resourceMap.getString("address.text"));
                defaultMainAddressName = resourceMap.getString("defaultMainAddress");
                defaultAddressName = resourceMap.getString("defaultAddress");
            } else if (parentObjectType.indexOf("Organization") != -1) {
                setTitle(resourceMap.getString("branch.text"));
                defaultMainAddressName = resourceMap.getString("defaultMainBranch");
                defaultAddressName = resourceMap.getString("defaultBranch");

            }
        }

      log.info("initData().address: " + address);
        if(address == null)
        {
            address = getFormSession().newAddress();
        }

        if (addressBindingGroup == null)
            addressBindingGroup = new BindingGroup();

        final EntityProperties entityProps = getAddressEntityProperties();

        addressNameTextField.bind(addressBindingGroup, address, entityProps.getPropertyDetails("addressName"));
        postalCodeTextField.bind(addressBindingGroup, address, entityProps.getPropertyDetails("postalCode"));
        postalAddressTextPane.bind(addressBindingGroup, address, entityProps.getPropertyDetails("postalAddress"));

        countryComboBox.bind(addressBindingGroup, getCountries(), address, entityProps.getPropertyDetails("country"));
        countryComboBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                country = (Country) countryComboBox.getSelectedItem();
                cityBinding.getTargetProperty().setValue(cityLookup, null);
            }
        });

        cityBinding = cityLookup.bind(new AcaciaLookupProvider() {
                @Override
                public Object showSelectionControl() {
                    return onChooseCity();
                }
            }, addressBindingGroup,
            address,
            entityProps.getPropertyDetails("city"),
            "${cityName}",
            UpdateStrategy.READ_WRITE);


        descriptionTextPane.bind(addressBindingGroup, address, "description");

        BigInteger dataObjectId;
        DataObject dataObject;
        if(address != null && (dataObject = address.getDataObject()) != null)
            dataObjectId = dataObject.getDataObjectId();
        else
            dataObjectId = null;
        contactPersonsTable = new ContactPersonsListPanel(dataObjectId);
        //contactPersonsTable.setVisibleButtons(2 + 4 + 8 + 64);
        contactPersonsTable.setVisible(AbstractTablePanel.Button.NewModifyDeleteUnselect);
        contactPersonsTable.getDataTable().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if(!event.getValueIsAdjusting()) {
                    ListSelectionModel selectionModel = (ListSelectionModel) event.getSource();
                    if (selectionModel.isSelectionEmpty()) {
                        updateCommunicationContacts(getDataObject().getDataObjectId());
                    } else {
                        ContactPerson contactPerson = (ContactPerson)
                                contactPersonsTable.getDataTable().getSelectedRowObject();
                        updateCommunicationContacts(contactPerson);
                    }
                }
            }

        });
        contactPersonsTable.addTableModificationListener(new TableModificationListener() {
            public void rowDeleted(Object row) {
                updateCommunicationContacts((ContactPerson) row);
                contactPersonsTable.unselectAction();
            }

            public void rowModified(Object row) {
                updateCommunicationContacts((ContactPerson) row);
            }

            public void rowAdded(Object row) {
                updateCommunicationContacts((ContactPerson) row);
                contactPersonsTable.unselectAction();
            }
        });

        contactPersonsPanel.add(contactPersonsTable);

        communicationContactsTable = new CommunicationContactsListPanel(dataObjectId);
        //communicationContactsTable.setVisibleButtons(2 + 4 + 8);
        communicationContactsTable.setVisible(AbstractTablePanel.Button.NewModifyDelete);

        communicationContactsPanel.add(communicationContactsTable);


        bankDetailsTable = new BankDetailsListPanel(dataObjectId);
        //bankDetailsTable.setVisibleButtons(2 + 4 + 8);
        bankDetailsTable.setVisible(AbstractTablePanel.Button.NewModifyDelete);

        bankDetailsPanel.add(bankDetailsTable);

        // Adding nested form listeners for all tables
        addNestedFormListener(communicationContactsTable);
        addNestedFormListener(contactPersonsTable);
        addNestedFormListener(bankDetailsTable);

        // Associating the three tables (the association is bidirectional)
        communicationContactsTable.associateWithTable(bankDetailsTable);
        communicationContactsTable.associateWithTable(contactPersonsTable);
        contactPersonsTable.associateWithTable(bankDetailsTable);

        addressBindingGroup.bind();

        if (address.getParentId() == null) {
            int nextAddressNumber = getNewAddressNumber();
            if (nextAddressNumber == 0)
                addressNameTextField.setText(defaultMainAddressName);
            else
                addressNameTextField.setText(defaultAddressName + " " + nextAddressNumber);
        }
    }

     protected Object onChooseCity() {

        CitiesListPanel listPanel = new CitiesListPanel(country);

        log.info("Displaying cities for country: " + country);

        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            City selectedCity = (City) listPanel.getSelectedRowObject();
            if (countryComboBox.getSelectedItem() == null)
                countryComboBox.setSelectedItem(selectedCity.getCountry());

            return selectedCity;
        }
        return null;
    }

    private void updateCommunicationContacts(BigInteger parentId)
    {
        List<CommunicationContact> communicationContacts =
                getFormSession().getCommunicationContacts(parentId);
        communicationContactsTable.setContactPerson(null);
        updateCommunicationContactsTable(communicationContacts);
    }

    private int getNewAddressNumber() {
        List<Address> addresses = getFormSession().getAddresses(getParentDataObjectId());
        if (addresses == null)
            return 0;

        return addresses.size();
    }

    private void updateCommunicationContacts(ContactPerson contactPerson)
    {
        List<CommunicationContact> communicationContacts =
                getFormSession().getCommunicationContacts(contactPerson);
        communicationContactsTable.setContactPerson(contactPerson);
        updateCommunicationContactsTable(communicationContacts);
    }

    private void updateCommunicationContactsTable(List<CommunicationContact> data)
    {
        communicationContactsTable.getDataTable().setData(data);
    }

    protected AddressesListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(AddressesListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected EntityProperties getAddressEntityProperties()
    {
        return getFormSession().getAddressEntityProperties();
    }

    private List<Country> getCountries()
    {
        return getFormSession().getCountries();
    }

    @Override
    public BindingGroup getBindingGroup() {
        return addressBindingGroup;
    }

    @Override
    public void performSave(boolean closeAfter) {
        log.info("Save: address: " + address);

        address = getFormSession().saveAddress(address, getParentDataObjectId());
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(address);
        if (closeAfter) {
            close();
        } else {
            addressBindingGroup.unbind();
            initData();
        }

    }

    @Override
    public Object getEntity() {
        return address;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    @Override
    public DialogResponse showDialog(Component parentComponent) {
        return super.showDialog(parentComponent);
    }

    public void setInternal(boolean isInternal) {
        this.isInternal = isInternal;
        contactPersonsTable.setInternal(isInternal);
    }
}








