/*
 * ForgottenPasswordForm.java
 *
 * Created on 02 July 2008, 08:21
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.gui.AcaciaPanel;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class ForgottenPasswordForm extends AcaciaPanel {

    /** Creates new form ForgottenPasswordForm */
    public ForgottenPasswordForm(BigInteger parentId) {
        super(parentId);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        infoLabel = new com.cosmos.swingb.JBLabel();
        usernameLabel = new com.cosmos.swingb.JBLabel();
        orLabel = new com.cosmos.swingb.JBLabel();
        emailLabel = new com.cosmos.swingb.JBLabel();
        usernameTextField = new com.cosmos.swingb.JBTextField();
        emailTextField = new com.cosmos.swingb.JBTextField();
        sendPasswordButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ForgottenPasswordForm.class);
        infoLabel.setText(resourceMap.getString("infoLabel.text")); // NOI18N
        infoLabel.setName("infoLabel"); // NOI18N

        usernameLabel.setText(resourceMap.getString("usernameLabel.text")); // NOI18N
        usernameLabel.setName("usernameLabel"); // NOI18N

        orLabel.setText(resourceMap.getString("orLabel.text")); // NOI18N
        orLabel.setName("orLabel"); // NOI18N

        emailLabel.setText(resourceMap.getString("emailLabel.text")); // NOI18N
        emailLabel.setName("emailLabel"); // NOI18N

        usernameTextField.setText(resourceMap.getString("usernameTextField.text")); // NOI18N
        usernameTextField.setName("usernameTextField"); // NOI18N

        emailTextField.setText(resourceMap.getString("emailTextField.text")); // NOI18N
        emailTextField.setName("emailTextField"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(ForgottenPasswordForm.class, this);
        sendPasswordButton.setAction(actionMap.get("sendPassword")); // NOI18N
        sendPasswordButton.setText(resourceMap.getString("sendPasswordButton.text")); // NOI18N
        sendPasswordButton.setName("sendPasswordButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(orLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(sendPasswordButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(orLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(sendPasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel emailLabel;
    private com.cosmos.swingb.JBTextField emailTextField;
    private com.cosmos.swingb.JBLabel infoLabel;
    private com.cosmos.swingb.JBLabel orLabel;
    private com.cosmos.swingb.JBButton sendPasswordButton;
    private com.cosmos.swingb.JBLabel usernameLabel;
    private com.cosmos.swingb.JBTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

    private UsersRemote formSession;
    
    @Override
    protected void initData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    protected UsersRemote getFormSession()
    {
        if (formSession == null) {
            try
            {
                formSession = getBean(UsersRemote.class);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return formSession;
    }

    @Action
    public void sendPassword() {
        
        try {
            String username = usernameTextField.getText();
            if (username != null && username.length() > 0)
                getFormSession().remindPasswordByUsername(username);
            else {
                String email = emailTextField.getText();
                if (email != null && email.length() > 0)
                    getFormSession().remindPasswordByEmail(email);
                else
                    JOptionPane.showMessageDialog(this, getResourceMap().getString("ForgottenPasswordForm.no.data"));
            }

            JOptionPane.showMessageDialog(this, getResourceMap().getString("ForgottenPasswordForm.email.sent"));
            close();
        } catch (Exception ex) {
            handleBusinessException(ex);
        }
        
    }
}
