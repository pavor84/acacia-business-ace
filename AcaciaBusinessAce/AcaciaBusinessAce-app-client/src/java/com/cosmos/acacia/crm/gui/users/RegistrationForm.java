/*
 * RegistrationForm.java
 *
 * Created on 25 June 2008, 20:46
 */

package com.cosmos.acacia.crm.gui.users;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.gui.contactbook.AddressListPanel;
import com.cosmos.acacia.crm.gui.contactbook.OrganizationsListPanel;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.beansbinding.EntityProperties;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class RegistrationForm extends BaseEntityPanel {


    /** Creates new form RegistrationForm */
    public RegistrationForm(String email) {
        super(null);
        this.email = email;
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        organizationComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        organizationLabel = new com.cosmos.swingb.JBLabel();
        branchComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        branchLabel = new com.cosmos.swingb.JBLabel();
        usernameLabel = new com.cosmos.swingb.JBLabel();
        usernameTextField = new com.cosmos.swingb.JBTextField();
        passwordLabel = new com.cosmos.swingb.JBLabel();
        passwordLabel2 = new com.cosmos.swingb.JBLabel();
        registerButton = new com.cosmos.swingb.JBButton();
        passwordTextField = new com.cosmos.swingb.JBPasswordField();
        passwordTextField2 = new com.cosmos.swingb.JBPasswordField();
        personalDataPanel = new com.cosmos.swingb.JBPanel();
        firstNameLabel = new javax.swing.JLabel();
        secondNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        extraNameLabel = new javax.swing.JLabel();
        firstNameTextField = new com.cosmos.swingb.JBTextField();
        secondNameTextField = new com.cosmos.swingb.JBTextField();
        lastNameTextField = new com.cosmos.swingb.JBTextField();
        extraNameTextField = new com.cosmos.swingb.JBTextField();

        setName("Form"); // NOI18N

        organizationComboList.setName("organizationComboList"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(RegistrationForm.class);
        organizationLabel.setText(resourceMap.getString("organizationLabel.text")); // NOI18N
        organizationLabel.setName("organizationLabel"); // NOI18N

        branchComboList.setName("branchComboList"); // NOI18N

        branchLabel.setText(resourceMap.getString("branchLabel.text")); // NOI18N
        branchLabel.setName("branchLabel"); // NOI18N

        usernameLabel.setText(resourceMap.getString("usernameLabel.text")); // NOI18N
        usernameLabel.setName("usernameLabel"); // NOI18N

        usernameTextField.setText(resourceMap.getString("usernameTextField.text")); // NOI18N
        usernameTextField.setName("usernameTextField"); // NOI18N

        passwordLabel.setText(resourceMap.getString("passwordLabel.text")); // NOI18N
        passwordLabel.setName("passwordLabel"); // NOI18N

        passwordLabel2.setText(resourceMap.getString("passwordLabel2.text")); // NOI18N
        passwordLabel2.setName("passwordLabel2"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(RegistrationForm.class, this);
        registerButton.setAction(actionMap.get("register")); // NOI18N
        registerButton.setText(resourceMap.getString("registerButton.text")); // NOI18N
        registerButton.setName("registerButton"); // NOI18N

        passwordTextField.setText(resourceMap.getString("passwordTextField.text")); // NOI18N
        passwordTextField.setName("passwordTextField"); // NOI18N

        passwordTextField2.setText(resourceMap.getString("passwordTextField2.text")); // NOI18N
        passwordTextField2.setName("passwordTextField2"); // NOI18N

        personalDataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Personal Info"));
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

        javax.swing.GroupLayout personalDataPanelLayout = new javax.swing.GroupLayout(personalDataPanel);
        personalDataPanel.setLayout(personalDataPanelLayout);
        personalDataPanelLayout.setHorizontalGroup(
            personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalDataPanelLayout.createSequentialGroup()
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(extraNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(lastNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(secondNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(secondNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                    .addComponent(extraNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
                .addContainerGap())
        );
        personalDataPanelLayout.setVerticalGroup(
            personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalDataPanelLayout.createSequentialGroup()
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secondNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secondNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extraNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extraNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(personalDataPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(organizationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(branchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(passwordTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(branchComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                            .addComponent(passwordTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(organizationComboList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)))
                    .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(organizationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(organizationComboList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(branchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(branchComboList, javax.swing.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {branchComboList, organizationComboList, passwordTextField, passwordTextField2, usernameTextField});

    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.AcaciaComboList branchComboList;
    private com.cosmos.swingb.JBLabel branchLabel;
    private javax.swing.JLabel extraNameLabel;
    private com.cosmos.swingb.JBTextField extraNameTextField;
    private javax.swing.JLabel firstNameLabel;
    private com.cosmos.swingb.JBTextField firstNameTextField;
    private javax.swing.JLabel lastNameLabel;
    private com.cosmos.swingb.JBTextField lastNameTextField;
    private com.cosmos.acacia.gui.AcaciaComboList organizationComboList;
    private com.cosmos.swingb.JBLabel organizationLabel;
    private com.cosmos.swingb.JBLabel passwordLabel;
    private com.cosmos.swingb.JBLabel passwordLabel2;
    private com.cosmos.swingb.JBPasswordField passwordTextField;
    private com.cosmos.swingb.JBPasswordField passwordTextField2;
    private com.cosmos.swingb.JBPanel personalDataPanel;
    private com.cosmos.swingb.JBButton registerButton;
    private javax.swing.JLabel secondNameLabel;
    private com.cosmos.swingb.JBTextField secondNameTextField;
    private com.cosmos.swingb.JBLabel usernameLabel;
    private com.cosmos.swingb.JBTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

    private BindingGroup userBindingGroup;
    private User user;
    private UsersRemote formSession;
    private String email;

    @Override
    protected void initData() {
       setResizable(false);

       if (user == null)
           user = getFormSession().createUser();

       userBindingGroup = new BindingGroup();

       // Setting the email address to the verified one from the code-verification step
       user.setEmailAddress(email);

       EntityProperties entityProps = getFormSession().getUserEntityProperties();
       usernameTextField.bind(userBindingGroup, user, entityProps.getPropertyDetails("userName"));
       passwordTextField.bind(userBindingGroup, user, entityProps.getPropertyDetails("userPassword"));

       OrganizationsListPanel organizationsTable = new OrganizationsListPanel(null);
       organizationComboList.initUnbound(organizationsTable, "${organizationName}");

       organizationComboList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Organization organization = (Organization) organizationComboList.getSelectedItem();
                if (organization == null) {
                    branchComboList.getComboBox().removeAllItems();
                    branchComboList.setEnabled(false);
                    return;
                }

                branchComboList.setEnabled(true);
                AddressListPanel branchesTable = new AddressListPanel(organization.getId());
                branchesTable.setVisible(Button.New, false);
                branchComboList.initUnbound(branchesTable, "${addressName}");
            }
       });
       
       branchComboList.setEnabled(false);
       userBindingGroup.bind();
    }

    @Action
    public void register() {
        try {
            if (user.getUserPassword().equals(new String(passwordTextField2.getPassword()))) {
                Person person = new Person();
                person.setFirstName(firstNameTextField.getText());
                person.setSecondName(secondNameTextField.getText());
                person.setLastName(lastNameTextField.getText());
                person.setExtraName(extraNameTextField.getText());
                
                getFormSession().signup(user,
                        (Organization) organizationComboList.getSelectedItem(),
                        (Address) branchComboList.getSelectedItem(),
                        person);

                JOptionPane.showMessageDialog(this, getResourceMap().getString("signup.successful"));
                this.close();
            } else {
                JOptionPane.showMessageDialog(this, getResourceMap().getString("passwords.inconsistent"));
            }
        } catch (Exception ex){
            checkForValidationException(ex);
        }
    }

    protected UsersRemote getFormSession() {
        if (formSession == null) {
             try {
                 formSession = getBean(UsersRemote.class);
             } catch(Exception ex) {
                 ex.printStackTrace();
             }
        }

         return formSession;
    }

    @Override
    public BindingGroup getBindingGroup() {
        return userBindingGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        // Not used
        return null;
    }

    @Override
    public Object getEntity() {
        return user;
    }

    @Override
    public void performSave(boolean closeAfter) {
        // Not used
    }

}
