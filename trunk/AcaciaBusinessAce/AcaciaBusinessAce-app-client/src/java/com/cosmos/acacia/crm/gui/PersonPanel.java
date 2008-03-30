/*
 * PersonPanel.java
 *
 * Created on 09 March 2008, 16:50
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.gui.EntityFormButtonPanel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.error.ErrorInfo;

import com.cosmos.acacia.crm.bl.impl.PersonsListRemote;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.Country;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.settings.GeneralSettings;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBErrorPane;
import com.cosmos.swingb.listeners.NestedFormListener;
import javax.swing.JOptionPane;

/**
 * A form for adding and editing persons
 *
 * @author  Bozhidar Bozhanov
 */
public class PersonPanel extends BaseEntityPanel {

    /** Creates new form PersonPanel */
    public PersonPanel(Person person) {
        super(person.getDataObject().getParentDataObject());
        this.person = person;
        init();
    }

    /** Creates new form PersonPanel */
    public PersonPanel(DataObject parentDataObject) {
        super(parentDataObject);
        init();
    }
    
    private void init()
    {
        initComponents();
        initData();
        initSaveStateListener();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        personalDataPanel = new com.cosmos.swingb.JBPanel();
        firstNameLabel = new javax.swing.JLabel();
        secondNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        extraNameLabel = new javax.swing.JLabel();
        firstNameTextField = new com.cosmos.swingb.JBTextField();
        secondNameTextField = new com.cosmos.swingb.JBTextField();
        lastNameTextField = new com.cosmos.swingb.JBTextField();
        extraNameTextField = new com.cosmos.swingb.JBTextField();
        genderLabel = new javax.swing.JLabel();
        genderComboBox = new com.cosmos.swingb.JBComboBox();
        birthDataPanel = new com.cosmos.swingb.JBPanel();
        personalUniqueIdLabel = new javax.swing.JLabel();
        birthdateLabel = new javax.swing.JLabel();
        countryLabel = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();
        personalUniqueIdTextField = new com.cosmos.swingb.JBTextField();
        birthPlaceCountryComboBox = new com.cosmos.swingb.JBComboBox();
        birthPlaceCityComboBox = new com.cosmos.swingb.JBComboBox();
        birthdateDatePicker = new com.cosmos.swingb.JBDatePicker();
        passportsPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        passportsTable = new com.cosmos.acacia.gui.AcaciaTable();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        addressesPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();

        setOpaque(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PersonPanel.class);
        personalDataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("personalDataPanel.border.title"))); // NOI18N
        personalDataPanel.setName("personalDataPanel"); // NOI18N
        personalDataPanel.setTitle(resourceMap.getString("personalDataPanel.title")); // NOI18N

        firstNameLabel.setText(resourceMap.getString("firstNameLabel.text")); // NOI18N
        firstNameLabel.setName("firstNameLabel"); // NOI18N

        secondNameLabel.setText(resourceMap.getString("secondNameLabel.text")); // NOI18N
        secondNameLabel.setName("secondNameLabel"); // NOI18N

        lastNameLabel.setText(resourceMap.getString("lastNameLabel.text")); // NOI18N
        lastNameLabel.setName("lastNameLabel"); // NOI18N

        extraNameLabel.setText(resourceMap.getString("extraNameLabel.text")); // NOI18N
        extraNameLabel.setName("extraNameLabel"); // NOI18N

        firstNameTextField.setName("firstNameTextField"); // NOI18N

        secondNameTextField.setName("secondNameTextField"); // NOI18N

        lastNameTextField.setName("lastNameTextField"); // NOI18N

        extraNameTextField.setName("extraNameTextField"); // NOI18N

        genderLabel.setText(resourceMap.getString("genderLabel.text")); // NOI18N
        genderLabel.setName("genderLabel"); // NOI18N

        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        genderComboBox.setName("genderComboBox"); // NOI18N

        javax.swing.GroupLayout personalDataPanelLayout = new javax.swing.GroupLayout(personalDataPanel);
        personalDataPanel.setLayout(personalDataPanelLayout);
        personalDataPanelLayout.setHorizontalGroup(
            personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(firstNameLabel)
                    .addComponent(secondNameLabel)
                    .addComponent(lastNameLabel)
                    .addComponent(extraNameLabel)
                    .addComponent(genderLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(genderComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(secondNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addComponent(extraNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                .addContainerGap())
        );

        personalDataPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {extraNameTextField, firstNameTextField, lastNameTextField, secondNameTextField});

        personalDataPanelLayout.setVerticalGroup(
            personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalDataPanelLayout.createSequentialGroup()
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderLabel)
                    .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLabel)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secondNameLabel)
                    .addComponent(secondNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extraNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extraNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        birthDataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("birthDataPanel.border.title"))); // NOI18N
        birthDataPanel.setName("birthDataPanel"); // NOI18N
        birthDataPanel.setTitle(resourceMap.getString("birthDataPanel.title")); // NOI18N

        personalUniqueIdLabel.setText(resourceMap.getString("personalUniqueIdLabel.text")); // NOI18N
        personalUniqueIdLabel.setName("personalUniqueIdLabel"); // NOI18N

        birthdateLabel.setText(resourceMap.getString("birthdateLabel.text")); // NOI18N
        birthdateLabel.setName("birthdateLabel"); // NOI18N

        countryLabel.setText(resourceMap.getString("countryLabel.text")); // NOI18N
        countryLabel.setName("countryLabel"); // NOI18N

        cityLabel.setText(resourceMap.getString("cityLabel.text")); // NOI18N
        cityLabel.setName("cityLabel"); // NOI18N

        personalUniqueIdTextField.setName("personalUniqueIdTextField"); // NOI18N

        birthPlaceCountryComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        birthPlaceCountryComboBox.setName("birthPlaceCountryComboBox"); // NOI18N

        birthPlaceCityComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        birthPlaceCityComboBox.setName("birthPlaceCityComboBox"); // NOI18N

        birthdateDatePicker.setFormats(GeneralSettings.getDateFormat());
        birthdateDatePicker.setName("birthdateDatePicker"); // NOI18N

        javax.swing.GroupLayout birthDataPanelLayout = new javax.swing.GroupLayout(birthDataPanel);
        birthDataPanel.setLayout(birthDataPanelLayout);
        birthDataPanelLayout.setHorizontalGroup(
            birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(birthDataPanelLayout.createSequentialGroup()
                .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(birthDataPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(personalUniqueIdLabel)
                            .addComponent(birthdateLabel)))
                    .addGroup(birthDataPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(countryLabel))
                    .addGroup(birthDataPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cityLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(personalUniqueIdTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(birthPlaceCountryComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(birthPlaceCityComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(birthdateDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        birthDataPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {birthPlaceCityComboBox, birthPlaceCountryComboBox, personalUniqueIdTextField});

        birthDataPanelLayout.setVerticalGroup(
            birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(birthDataPanelLayout.createSequentialGroup()
                .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(personalUniqueIdLabel)
                    .addComponent(personalUniqueIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(birthdateLabel)
                    .addComponent(birthdateDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthPlaceCountryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthPlaceCityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        passportsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("passportsPanel.border.title"))); // NOI18N
        passportsPanel.setName("passportsPanel"); // NOI18N
        passportsPanel.setTitle(resourceMap.getString("passportsPanel.title")); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        passportsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        passportsTable.setName("passportsTable"); // NOI18N
        jScrollPane2.setViewportView(passportsTable);

        javax.swing.GroupLayout passportsPanelLayout = new javax.swing.GroupLayout(passportsPanel);
        passportsPanel.setLayout(passportsPanelLayout);
        passportsPanelLayout.setHorizontalGroup(
            passportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(passportsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                .addContainerGap())
        );
        passportsPanelLayout.setVerticalGroup(
            passportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(passportsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addContainerGap())
        );

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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        addressesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("addressesPanel.border.title"))); // NOI18N
        addressesPanel.setName("addressesPanel"); // NOI18N

        javax.swing.GroupLayout addressesPanelLayout = new javax.swing.GroupLayout(addressesPanel);
        addressesPanel.setLayout(addressesPanelLayout);
        addressesPanelLayout.setHorizontalGroup(
            addressesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 658, Short.MAX_VALUE)
        );
        addressesPanelLayout.setVerticalGroup(
            addressesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(personalDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(birthDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addressesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passportsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(personalDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addressesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passportsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.TableHolderPanel addressesPanel;
    private com.cosmos.swingb.JBPanel birthDataPanel;
    private com.cosmos.swingb.JBComboBox birthPlaceCityComboBox;
    private com.cosmos.swingb.JBComboBox birthPlaceCountryComboBox;
    private com.cosmos.swingb.JBDatePicker birthdateDatePicker;
    private javax.swing.JLabel birthdateLabel;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JLabel countryLabel;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private javax.swing.JLabel extraNameLabel;
    private com.cosmos.swingb.JBTextField extraNameTextField;
    private javax.swing.JLabel firstNameLabel;
    private com.cosmos.swingb.JBTextField firstNameTextField;
    private com.cosmos.swingb.JBComboBox genderComboBox;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lastNameLabel;
    private com.cosmos.swingb.JBTextField lastNameTextField;
    private com.cosmos.swingb.JBPanel passportsPanel;
    private com.cosmos.acacia.gui.AcaciaTable passportsTable;
    private com.cosmos.swingb.JBPanel personalDataPanel;
    private javax.swing.JLabel personalUniqueIdLabel;
    private com.cosmos.swingb.JBTextField personalUniqueIdTextField;
    private javax.swing.JLabel secondNameLabel;
    private com.cosmos.swingb.JBTextField secondNameTextField;
    // End of variables declaration//GEN-END:variables

    @EJB
    private PersonsListRemote formSession;

    private AddressListPanel addressesTable;
    private BindingGroup personBindingGroup;
    private Person person;

    @Override
    protected void initData() {
        setResizable(false);
        System.out.println("initData().person: " + person);
        if(person == null)
        {
            person = getFormSession().newPerson();
        }

        personBindingGroup = new BindingGroup();

        EntityProperties entityProps = getPersonEntityProperties();
        
        genderComboBox.bind(personBindingGroup, getGenders(), person, entityProps.getPropertyDetails("gender"));

        firstNameTextField.bind(personBindingGroup, person, entityProps.getPropertyDetails("firstName"));
        secondNameTextField.bind(personBindingGroup, person, entityProps.getPropertyDetails("secondName"));
        lastNameTextField.bind(personBindingGroup, person, entityProps.getPropertyDetails("lastName"));
        extraNameTextField.bind(personBindingGroup, person, entityProps.getPropertyDetails("extraName"));

        personalUniqueIdTextField.bind(personBindingGroup, person, entityProps.getPropertyDetails("personalUniqueId"));
        birthdateDatePicker.bind(personBindingGroup, person, entityProps.getPropertyDetails("birthDate"));
        birthPlaceCountryComboBox.bind(personBindingGroup, getCountries(), person, "birthPlaceCountry");
        birthPlaceCityComboBox.bind(personBindingGroup, getCities(), person, "birthPlaceCity");

        descriptionTextPane.bind(personBindingGroup, person, "description");

        // Using an AbstractTablePanel implementation    
        addressesTable = new AddressListPanel(person.getDataObject());
        addressesTable.setVisibleButtons(14); //Only New, Modify and Delete
        
        // Adding the nested table listener to ensure that person is saved 
        // before adding addresses to it
        addNestedFormListener(addressesTable);
        
        //addressesTable.setButtonsTextVisibility(false);        
        addressesPanel.add(addressesTable);

        //JTableBinding passportsTableBinding = passportsTable.bind(personBindingGroup, getPassports(), getPassportEntityProperties());
        personBindingGroup.bind();
    }

        protected PersonsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(PersonsListRemote.class.getName());
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
        return personBindingGroup;
    }
    
    public DataObject getDataObject()
    {
        return person.getDataObject();
    }

    public Object getEntity()
    {
        return person;
    }
    
    public void performSave(boolean closeAfter)
    {
        System.out.println("Save: person: " + person);
        person = getFormSession().savePerson(person);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(person);
        if (closeAfter) 
            close();
    }
    
    @Action
    @Override
    public void closeAction() {
        setDialogResponse(DialogResponse.CLOSE);
        close();
    }

    protected EntityProperties getPersonEntityProperties()
    {
        return getFormSession().getPersonEntityProperties();
    }

    protected EntityProperties getAddressEntityProperties()
    {
        return getFormSession().getAddressEntityProperties();
    }

    protected EntityProperties getPassportEntityProperties()
    {
        return getFormSession().getPassportEntityProperties();
    }

    private List<Country> getCountries()
    {
        return getFormSession().getCountries();
    }

    private List<City> getCities()
    {
        return getFormSession().getCities();
    }

    private List<DbResource> getGenders()
    {
        return getFormSession().getGenders();
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
       return entityFormButtonPanel;
    }
}
