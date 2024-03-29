/*
 * PersonPanel.java
 *
 * Created on 09 March 2008, 16:50
 */
package com.cosmos.acacia.crm.gui.contactbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.ejb.EJB;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.PersonsListRemote;
import com.cosmos.acacia.crm.bl.reports.Report;
import com.cosmos.acacia.crm.data.location.City;
import com.cosmos.acacia.crm.data.location.Country;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.gui.pricing.CustomerDiscountListPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.settings.GeneralSettings;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBButton;

/**
 * A form for adding and editing persons
 *
 * @author  Bozhidar Bozhanov
 */
public class PersonPanel extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(PersonPanel.class);

    /** Creates new form PersonPanel */
    public PersonPanel(Person person) {
        super(person);
        init();
    }

    @Override
    protected void init() {
        initComponents();
        initComponentsCustom();
        super.init();
    }

    private void initComponentsCustom() {
        if (getClassifiersManager().isClassifiedAs(getPerson(), "customer")) {
            JBButton b = new JBButton();
            b.setText(getResourceMap().getString("button.discount"));
            b.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    onDiscount();
                }
            });
            getButtonPanel().addButton(b);
        }
    }

    protected void onDiscount() {
        if (getPerson().getId() != null) {
            CustomerDiscountListPanel customerDiscountForm = new CustomerDiscountListPanel(getPerson());
            customerDiscountForm.showDialog(this);
        }
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
        genderComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        birthDataPanel = new com.cosmos.swingb.JBPanel();
        personalUniqueIdLabel = new javax.swing.JLabel();
        birthdateLabel = new javax.swing.JLabel();
        countryLabel = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();
        personalUniqueIdTextField = new com.cosmos.swingb.JBTextField();
        birthPlaceCountryComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        birthdateDatePicker = new com.cosmos.swingb.JBDatePicker();
        cityLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        addressesPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        passportsPanel = new com.cosmos.acacia.gui.TableHolderPanel();

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
                    .addGroup(personalDataPanelLayout.createSequentialGroup()
                        .addComponent(secondNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(secondNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                    .addGroup(personalDataPanelLayout.createSequentialGroup()
                        .addComponent(lastNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(lastNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                    .addGroup(personalDataPanelLayout.createSequentialGroup()
                        .addComponent(extraNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(extraNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalDataPanelLayout.createSequentialGroup()
                        .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstNameLabel)
                            .addComponent(genderLabel))
                        .addGap(32, 32, 32)
                        .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genderComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                            .addComponent(firstNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))))
                .addContainerGap())
        );

        personalDataPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {extraNameTextField, firstNameTextField, lastNameTextField, secondNameTextField});

        personalDataPanelLayout.setVerticalGroup(
            personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalDataPanelLayout.createSequentialGroup()
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderLabel)
                    .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(extraNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
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
        cityLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                testCallback(evt);
            }
        });

        personalUniqueIdTextField.setName("personalUniqueIdTextField"); // NOI18N

        birthPlaceCountryComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        birthPlaceCountryComboBox.setName("birthPlaceCountryComboBox"); // NOI18N

        birthdateDatePicker.setFormats(GeneralSettings.getDateFormat());
        birthdateDatePicker.setName("birthdateDatePicker"); // NOI18N

        cityLookup.setName("cityLookup"); // NOI18N

        javax.swing.GroupLayout birthDataPanelLayout = new javax.swing.GroupLayout(birthDataPanel);
        birthDataPanel.setLayout(birthDataPanelLayout);
        birthDataPanelLayout.setHorizontalGroup(
            birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(birthDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(countryLabel)
                    .addComponent(birthdateLabel)
                    .addComponent(personalUniqueIdLabel)
                    .addComponent(cityLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(personalUniqueIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(birthdateDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(birthPlaceCountryComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(cityLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                .addContainerGap())
        );
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
                .addGroup(birthDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cityLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cityLookup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
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
            .addGap(0, 153, Short.MAX_VALUE)
        );

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        passportsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("passportsPanel.border.title"))); // NOI18N
        passportsPanel.setName("passportsPanel"); // NOI18N

        javax.swing.GroupLayout passportsPanelLayout = new javax.swing.GroupLayout(passportsPanel);
        passportsPanel.setLayout(passportsPanelLayout);
        passportsPanelLayout.setHorizontalGroup(
            passportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 658, Short.MAX_VALUE)
        );
        passportsPanelLayout.setVerticalGroup(
            passportsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 133, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addressesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passportsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(personalDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(birthDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(birthDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(personalDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addressesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passportsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void testCallback(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testCallback
}//GEN-LAST:event_testCallback
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.TableHolderPanel addressesPanel;
    private com.cosmos.swingb.JBPanel birthDataPanel;
    private com.cosmos.acacia.gui.AcaciaComboBox birthPlaceCountryComboBox;
    private com.cosmos.swingb.JBDatePicker birthdateDatePicker;
    private javax.swing.JLabel birthdateLabel;
    private javax.swing.JLabel cityLabel;
    private com.cosmos.acacia.gui.AcaciaLookup cityLookup;
    private javax.swing.JLabel countryLabel;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private javax.swing.JLabel extraNameLabel;
    private com.cosmos.swingb.JBTextField extraNameTextField;
    private javax.swing.JLabel firstNameLabel;
    private com.cosmos.swingb.JBTextField firstNameTextField;
    private com.cosmos.acacia.gui.AcaciaComboBox genderComboBox;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lastNameLabel;
    private com.cosmos.swingb.JBTextField lastNameTextField;
    private com.cosmos.acacia.gui.TableHolderPanel passportsPanel;
    private com.cosmos.swingb.JBPanel personalDataPanel;
    private javax.swing.JLabel personalUniqueIdLabel;
    private com.cosmos.swingb.JBTextField personalUniqueIdTextField;
    private javax.swing.JLabel secondNameLabel;
    private com.cosmos.swingb.JBTextField secondNameTextField;
    // End of variables declaration//GEN-END:variables
    @EJB
    private PersonsListRemote formSession;
    private AddressListPanel addressesTable;
    private PassportsListPanel passportsTable;
    private BindingGroup bindingGroup;
    private Binding cityBinding;
    private Country country;
    private boolean namesChanged;
    private boolean isUnique;

    @Override
    protected void initData() {
        setResizable(false);
        Person person = getPerson();
        log.info("initData().person: " + person);
        if (person == null) {
            throw new UnsupportedOperationException("ToDo");
        }

        BindingGroup bg = getBindingGroup();

        final EntityProperties entityProps = getPersonEntityProperties();

        genderComboBox.bind(bg, getGenders(), person, entityProps.getEntityProperty("gender"));

        firstNameTextField.bind(bg, person, entityProps.getEntityProperty("firstName"));
        secondNameTextField.bind(bg, person, entityProps.getEntityProperty("secondName"));
        lastNameTextField.bind(bg, person, entityProps.getEntityProperty("lastName"));
        extraNameTextField.bind(bg, person, entityProps.getEntityProperty("extraName"));

        personalUniqueIdTextField.bind(bg, person, entityProps.getEntityProperty("personalUniqueId"));
        birthdateDatePicker.bind(bg, person, entityProps.getEntityProperty("birthDate"), AcaciaUtils.getShortDateFormat());
        birthPlaceCountryComboBox.bind(bg, getCountries(), person, entityProps.getEntityProperty("birthPlaceCountry"));
        birthPlaceCountryComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                country = (Country) birthPlaceCountryComboBox.getSelectedItem();
                cityBinding.getTargetProperty().setValue(cityLookup, null);
            }
        });

        cityBinding = cityLookup.bind(new AcaciaLookupProvider() {

            @Override
            public Object showSelectionControl() {
                return onChooseCity();
            }
        }, bg,
                person,
                entityProps.getEntityProperty("birthPlaceCity"),
                "${cityName}",
                UpdateStrategy.READ_WRITE);

        descriptionTextPane.bind(bg, person, entityProps.getEntityProperty("description"));

        // Using an AbstractTablePanel implementation
        addressesTable = new AddressListPanel(person.getId());
        //addressesTable.setVisibleButtons(14); //Only New, Modify and Delete
        addressesTable.setVisible(AbstractTablePanel.Button.NewModifyDelete);

        // Adding the nested table listener to ensure that person is saved
        // before adding addresses to it
        addNestedFormListener(addressesTable);

        //addressesTable.setButtonsTextVisibility(false);
        addressesPanel.add(addressesTable);


        // Using an AbstractTablePanel implementation
        passportsTable = new PassportsListPanel(person.getId());
        //passportsTable.setVisibleButtons(14); //Only New, Modify and Delete
        passportsTable.setVisible(AbstractTablePanel.Button.NewModifyDelete);

        // Adding the nested table listener to ensure that person is saved
        // before adding addresses to it
        addNestedFormListener(passportsTable);
        passportsPanel.add(passportsTable);

        NameChangeListener nameChangeListener = new NameChangeListener();
        firstNameTextField.addKeyListener(nameChangeListener);
        secondNameTextField.addKeyListener(nameChangeListener);
        lastNameTextField.addKeyListener(nameChangeListener);

        bg.bind();
    }

    private Person getPerson() {
        return (Person) getMainDataObject();
    }

    protected Object onChooseCity() {

        CitiesListPanel listPanel = new CitiesListPanel(country);

        log.info("Displaying cities for country: " + country);

        DialogResponse dResponse = listPanel.showDialog(this);
        if (DialogResponse.SELECT.equals(dResponse)) {
            City selectedCity = (City) listPanel.getSelectedRowObject();
            if (birthPlaceCountryComboBox.getSelectedItem() == null) {
                birthPlaceCountryComboBox.setSelectedItem(selectedCity.getCountry());
            }

            return selectedCity;
        } else {
            return null;
        }
    }

    protected PersonsListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(PersonsListRemote.class);
        }

        return formSession;
    }

    @Override
    public BindingGroup getBindingGroup() {
        if (bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public Object getEntity() {
        return getPerson();
    }

    @Override
    public void performSave(boolean closeAfter) {
        Person person = getPerson();
        log.info("Save: person: " + person);

        isUnique = true;
        Person tmpPerson = null;

        // Checking whether this is a new person or the names were changed
        // in order to invoke the uniquness check server-side
        if ((person.getDataObject() == null || namesChanged) &&
                (tmpPerson = getFormSession().saveIfUnique(person)) == null) {
            isUnique = false;

            int answer = JOptionPane.showConfirmDialog(
                    this, getResourceMap().getString("Person.confirm.uniqueness"), "", JOptionPane.OK_CANCEL_OPTION);

            if (answer == JOptionPane.OK_OPTION) {
                setMainDataObject(getFormSession().savePerson(person));
                isUnique = true;
            }
        }

        if (tmpPerson != null) {
            setMainDataObject(tmpPerson);
        }

        if (isUnique) {
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValue(person);
            if (closeAfter) {
                close();
            } else {
                getBindingGroup().unbind();
                bindingGroup = null;
                initData();
            }
        }
    }

    protected EntityProperties getPersonEntityProperties() {
        return getFormSession().getPersonEntityProperties();
    }

    protected EntityProperties getAddressEntityProperties() {
        return getFormSession().getAddressEntityProperties();
    }

    protected EntityProperties getPassportEntityProperties() {
        return getFormSession().getPassportEntityProperties();
    }

    private List<Country> getCountries() {
        return getFormSession().getCountries();
    }

    private List<City> getCities(Country country) {
        return getFormSession().getCities(country);
    }

    private List<DbResource> getGenders() {
        return getFormSession().getGenders();
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    @Override
    protected boolean isSpecialConditionPresent() {
        return !isUnique;
    }

    class NameChangeListener extends KeyAdapter {

        @Override
        public void keyTyped(KeyEvent e) {
            namesChanged = true;
        }
    }

    @Override
    protected Report getReport() {
        Report report = new Report("person", addressesTable.getAddresses(), null);
        return report;
    }
}
