/*
 * LoginForm.java
 *
 * Created on 25 June 2008, 19:33
 */

package com.cosmos.acacia.crm.gui.users;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;

import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class LoginForm extends AcaciaPanel {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String USERS_LIST = "password";
    private static final String LOCALE = "locale";
    private static final String ORGANIZATION = "organization";

    @SuppressWarnings("hiding")
    protected static Logger log = Logger.getLogger(LoginForm.class);

    /** Creates new form LoginForm */
    public LoginForm(BigInteger parentId) {
        super(parentId);
        initComponents();
        initData();
    }

    /** Creates new form LoginForm */
    public LoginForm() {
        super((BigInteger) null);
        initComponents();
        initData();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new com.cosmos.swingb.JBLabel();
        passwordLabel = new com.cosmos.swingb.JBLabel();
        loginButton = new com.cosmos.swingb.JBButton();
        rememberMeCheckBox = new com.cosmos.swingb.JBCheckBox();
        rememberPasswordCheckBox = new com.cosmos.swingb.JBCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        forgottenPasswordButton = new com.cosmos.swingb.JBButton();
        newRegistrationButton = new com.cosmos.swingb.JBButton();
        jSeparator2 = new javax.swing.JSeparator();
        localeComboBox = new com.cosmos.swingb.JBComboBox();
        passwordTextField = new com.cosmos.swingb.JBPasswordField();
        usernameComboBox = new com.cosmos.swingb.JBComboBox();
        localeLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(LoginForm.class);
        usernameLabel.setText(resourceMap.getString("usernameLabel.text")); // NOI18N
        usernameLabel.setName("usernameLabel"); // NOI18N

        passwordLabel.setText(resourceMap.getString("passwordLabel.text")); // NOI18N
        passwordLabel.setName("passwordLabel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(LoginForm.class, this);
        loginButton.setAction(actionMap.get("login")); // NOI18N
        loginButton.setText(resourceMap.getString("loginButton.text")); // NOI18N
        loginButton.setName("loginButton"); // NOI18N

        rememberMeCheckBox.setText(resourceMap.getString("rememberMeCheckBox.text")); // NOI18N
        rememberMeCheckBox.setName("rememberMeCheckBox"); // NOI18N

        rememberPasswordCheckBox.setText(resourceMap.getString("rememberPasswordCheckBox.text")); // NOI18N
        rememberPasswordCheckBox.setName("rememberPasswordCheckBox"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        forgottenPasswordButton.setAction(actionMap.get("remindPassword")); // NOI18N
        forgottenPasswordButton.setText(resourceMap.getString("forgottenPasswordButton.text")); // NOI18N
        forgottenPasswordButton.setName("forgottenPasswordButton"); // NOI18N

        newRegistrationButton.setAction(actionMap.get("signup")); // NOI18N
        newRegistrationButton.setText(resourceMap.getString("newRegistrationButton.text")); // NOI18N
        newRegistrationButton.setName("newRegistrationButton"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        localeComboBox.setName("localeComboBox"); // NOI18N

        passwordTextField.setText(resourceMap.getString("passwordTextField.text")); // NOI18N
        passwordTextField.setName("passwordTextField"); // NOI18N

        usernameComboBox.setEditable(true);
        usernameComboBox.setName("usernameComboBox"); // NOI18N

        localeLabel.setText(resourceMap.getString("localeLabel.text")); // NOI18N
        localeLabel.setName("localeLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rememberMeCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rememberPasswordCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                            .addComponent(passwordTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(localeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(localeComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(forgottenPasswordButton, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newRegistrationButton, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rememberPasswordCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rememberMeCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(localeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newRegistrationButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(forgottenPasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(178, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(44, 44, 44)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton forgottenPasswordButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.cosmos.swingb.JBComboBox localeComboBox;
    private com.cosmos.swingb.JBLabel localeLabel;
    private com.cosmos.swingb.JBButton loginButton;
    private com.cosmos.swingb.JBButton newRegistrationButton;
    private com.cosmos.swingb.JBLabel passwordLabel;
    private com.cosmos.swingb.JBPasswordField passwordTextField;
    private com.cosmos.swingb.JBCheckBox rememberMeCheckBox;
    private com.cosmos.swingb.JBCheckBox rememberPasswordCheckBox;
    private com.cosmos.swingb.JBComboBox usernameComboBox;
    private com.cosmos.swingb.JBLabel usernameLabel;
    // End of variables declaration//GEN-END:variables


    private UsersRemote formSession;
    private Preferences prefs = Preferences.systemRoot();
    private String lastUserLoaded = null;

    @Override
    protected void initData() {
        localeComboBox.removeAllItems();
        Locale[] locales = getFormSession().serveLocalesList();
        if (locales != null) {
            for (Locale locale : locales) {
                localeComboBox.addItem(locale);
            }
        }

        // Load the saved preferences for this machine
        loadPreferences();
        AutoCompleteDecorator.decorate(usernameComboBox);

        this.requestFocus();
        addKeyListeners();
    }

    private void loadPreferences() {
        localeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && e.getItem() != null)
                    UserUtils.setLocale(new Locale((String) e.getItem()));
            }
        });

        usernameComboBox.removeAllItems();
        String usernamesString = prefs.get(USERS_LIST, null);
        if (usernamesString != null) {
            String[] usernamesArray = usernamesString.split(",");
            for (String username : usernamesArray) {
                usernameComboBox.addItem(username);
            }
        }
        usernameComboBox.setSelectedIndex(-1);

        usernameComboBox.addItemListener(new ItemListener() {
            private boolean isReal = true;
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String username = (String) usernameComboBox.getSelectedItem();
                    if (isReal && username != null && username.length() > 0) {
                        loadUserSpecificPreferences(username);
                        rememberMeCheckBox.setSelected(true);
                    }
                    isReal = !isReal;
                }
            }
        });

        String username = prefs.get(USERNAME, null);
        if (username != null) {
            rememberMeCheckBox.setSelected(true);
            usernameComboBox.setSelectedItem(username);
        }
    }

    private void loadUserSpecificPreferences(String username) {

        //preventing calls each time a letter is typed in the combo
        if (username.equals(lastUserLoaded))
            return;
        lastUserLoaded = username;

        String password = prefs.get(username + PASSWORD, null);
        if (password != null) {
            rememberPasswordCheckBox.setSelected(true);
            try {
                passwordTextField.setText(new String(getFormSession().decryptPassword(password)));
            } catch (NullPointerException ex){
                passwordTextField.setText("");
                rememberPasswordCheckBox.setSelected(false);
                //prefs.remove(username + PASSWORD);
            }
        } else {
            passwordTextField.setText("");
            rememberPasswordCheckBox.setSelected(false);
        }

        String locale = prefs.get(username + LOCALE, null);
        if (locale != null)
            localeComboBox.setSelectedItem(new Locale(locale));

    }

    @Action
    public void login() {

        String username = (String) usernameComboBox.getSelectedItem();

        char[] password = passwordTextField.getPassword();

        if (rememberPasswordCheckBox.isSelected()) {
            String encryptedPassword = getFormSession().encryptPassword(password);
            prefs.put(username + PASSWORD, encryptedPassword);
        } else
            prefs.remove(username + PASSWORD);

        try {
            prefs.flush();
        } catch (Exception ex) {
            log.error("", ex);
        }

        try {
            Integer sessionid = getFormSession().login(username, password);
            AcaciaApplication.setSessionId(sessionid);

            User user = getAcaciaSession().getUser();

            setPreferences(username);

            /* End of preferences handling */

            List<Organization> organizations = getFormSession().getOrganizationsList(user);
            Organization organization = null;
            if (organizations.size() > 1) {
                String defaultOrganization = prefs.get(username + ORGANIZATION, null);
                OrganizationChoiceForm form = new OrganizationChoiceForm(null);
                form.setDefaultOrganizationString(defaultOrganization);
                form.init(organizations);
                DialogResponse response = form.showDialog();
                if(DialogResponse.SELECT.equals(response))
                {
                    organization = (Organization) form.getSelectedValue();
                    prefs.put(username + ORGANIZATION, organization.getOrganizationName());
                }
            } else if (organizations.size() == 1) {
                organization = organizations.get(0);
            }

            // Initialization of client parameters

            getFormSession().setOrganization(organization);

            // End of initialization

//            try {
//                OrganizationsCallbackHandler handler = new OrganizationsCallbackHandler(defaultOrganization);
//                getFormSession().updateOrganization(user,
//                        GenericCallbackHandler.createCallbackHandler(handler));
//
//            } catch (Exception ex){
//                ex.printStackTrace();
//            }


            //setDialogResponse(DialogResponse.LOGIN);

            if (UsersRemote.CHANGE_PASSWORD.equals(user.getNextActionAfterLogin())) {
                ChangePasswordForm cpf = new ChangePasswordForm(null);
                cpf.setCurrentPassword(new String(password));
                cpf.showDialog(this);

                //if(!DialogResponse.OK.equals(response1))
                //    AcaciaApplication.getApplication().exit();
            }

            getFormSession().init();

            close(false);
            AcaciaApplication.launch(AcaciaApplication.class, null);
        } catch (Exception ex) {
            handleBusinessException(ex);
        }


    }

    private void setPreferences(String username) {
        /* Preferences handling*/
        prefs.put(username + LOCALE, ((Locale) localeComboBox.getSelectedItem()).getLanguage());

        String usersList = prefs.get(USERS_LIST, null);
        if (rememberMeCheckBox.isSelected()) {
            prefs.put(USERNAME, username);
            if (usersList == null || usersList.length() == 0)
                prefs.put(USERS_LIST, username);
            else {
                if (usersList.indexOf(username) == -1) {
                    usersList += "," + username;
                    prefs.put(USERS_LIST, usersList);
                }
            }
        } else {
            prefs.remove(USERNAME);
            prefs.remove(username + PASSWORD);
            prefs.remove(username + LOCALE);
            if (usersList != null && usersList.length() > 0) {
                usersList = usersList.replaceAll("," + username, "");
                usersList = usersList.replaceAll(username + ",", "");
                prefs.put(USERS_LIST, usersList);
            }
        }
    }

    @Action
    public void signup() {
        RequestRegistrationForm reqRegForm = new RequestRegistrationForm();
        reqRegForm.showDialog(this);
    }

    @Action
    public void remindPassword() {
        ForgottenPasswordForm fpf = new ForgottenPasswordForm(null);
        fpf.showDialog();
    }

    protected UsersRemote getFormSession()
    {
        if (formSession == null) {
            try
            {
                formSession = getBean(UsersRemote.class, false);
                UserUtils.updateUserLocale(formSession);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return formSession;
    }

    private void addKeyListeners() {
        Component[] components = this.getComponents();
        EnterPressedListener l = new EnterPressedListener();
        for (Component c : components) {
            c.addKeyListener(l);
        }
    }

    @Override
    public void close() {
        close(true);
    }

    public void close(boolean exit) {
        super.close();
        if (exit)
            AcaciaApplication.getApplication().exit();
    }

    class EnterPressedListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                login();
            }
        }
    }
}