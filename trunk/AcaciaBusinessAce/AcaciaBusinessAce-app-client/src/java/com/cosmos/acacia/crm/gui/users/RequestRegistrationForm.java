/*
 * RequestRegistrationForm.java
 *
 * Created on 25 June 2008, 20:07
 */
package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.validation.ValidationException;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class RequestRegistrationForm extends AcaciaPanel {

    /** Creates new form RequestRegistrationForm */
    public RequestRegistrationForm() {
        super((BigInteger) null);
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

        upperInfoLabel = new com.cosmos.swingb.JBLabel();
        emailLabel = new com.cosmos.swingb.JBLabel();
        jSeparator1 = new javax.swing.JSeparator();
        emailTextField = new com.cosmos.swingb.JBTextField();
        sendCodeButton = new com.cosmos.swingb.JBButton();
        lowerInfoLabel = new com.cosmos.swingb.JBLabel();
        proceedButton = new com.cosmos.swingb.JBButton();
        codeTextField = new com.cosmos.swingb.JBTextField();
        codeLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(RequestRegistrationForm.class);
        upperInfoLabel.setText(resourceMap.getString("upperInfoLabel.text")); // NOI18N
        upperInfoLabel.setName("upperInfoLabel"); // NOI18N

        emailLabel.setText(resourceMap.getString("emailLabel.text")); // NOI18N
        emailLabel.setName("emailLabel"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        emailTextField.setText(resourceMap.getString("emailTextField.text")); // NOI18N
        emailTextField.setName("emailTextField"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(RequestRegistrationForm.class, this);
        sendCodeButton.setAction(actionMap.get("sendCode")); // NOI18N
        sendCodeButton.setName("sendCodeButton"); // NOI18N

        lowerInfoLabel.setText(resourceMap.getString("lowerInfoLabel.text")); // NOI18N
        lowerInfoLabel.setName("lowerInfoLabel"); // NOI18N

        proceedButton.setAction(actionMap.get("proceed")); // NOI18N
        proceedButton.setText(resourceMap.getString("proceedButton.text")); // NOI18N
        proceedButton.setName("proceedButton"); // NOI18N

        codeTextField.setName("codeTextField"); // NOI18N

        codeLabel.setText(resourceMap.getString("codeLabel.text")); // NOI18N
        codeLabel.setName("codeLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendCodeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proceedButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lowerInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(upperInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {proceedButton, sendCodeButton});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {codeTextField, emailTextField});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(upperInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendCodeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowerInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel codeLabel;
    private com.cosmos.swingb.JBTextField codeTextField;
    private com.cosmos.swingb.JBLabel emailLabel;
    private com.cosmos.swingb.JBTextField emailTextField;
    private javax.swing.JSeparator jSeparator1;
    private com.cosmos.swingb.JBLabel lowerInfoLabel;
    private com.cosmos.swingb.JBButton proceedButton;
    private com.cosmos.swingb.JBButton sendCodeButton;
    private com.cosmos.swingb.JBLabel upperInfoLabel;
    // End of variables declaration//GEN-END:variables
    private UsersRemote formSession;

    @Override
    protected void initData() {
        //
    }

    protected UsersRemote getFormSession() {
        if (formSession == null) {
            try {
                formSession = getBean(UsersRemote.class);
                UserUtils.updateUserLocale(formSession);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    @Action
    public void sendCode() {
        getFormSession().requestRegistration(emailTextField.getText());
        if (!exceptionOccurred)
            JOptionPane.showMessageDialog(this, getResourceMap().getString("request.successful"));
        else
            exceptionOccurred = false;
        
    }

    @Action
    public void proceed() {
        String email = getFormSession().verifyCode(codeTextField.getText());
        if (!exceptionOccurred) {
            RegistrationForm regForm = new RegistrationForm(email);
            regForm.showDialog(this.getParent());
            this.close();
        } else {
            exceptionOccurred = false;
        }
    }
}
