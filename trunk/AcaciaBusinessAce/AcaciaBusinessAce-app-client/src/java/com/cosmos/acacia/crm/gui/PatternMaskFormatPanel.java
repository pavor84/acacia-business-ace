/*
 * PatternMaskFormatPanel.java
 *
 * Created on РЎСЉР±РѕС‚Р°, 2008, РњР°СЂС‚ 22, 19:39
 */

package com.cosmos.acacia.crm.gui;

import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jdesktop.application.Action;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.error.ErrorInfo;

import com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.crm.gui.ProductPanel.Button;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBErrorPane;
import com.cosmos.swingb.JBTextField;

/**
 *
 * @author  jchan
 */
public class PatternMaskFormatPanel extends AcaciaPanel {
    
    @EJB
    private PatternMaskListRemote formSession;
    
    /** Creates new form PatternMaskFormatPanel */
    public PatternMaskFormatPanel(PatternMaskFormat modelObject) {
        super(null);
        this.format = modelObject;
        init();
    }
    
    private BindingGroup bindGroup;
    private PatternMaskFormat format;
    
    private void init()
    {
        initComponents();
        initData();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBPanel1 = new com.cosmos.swingb.JBPanel();
        jBLabel1 = new com.cosmos.swingb.JBLabel();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        nameField = new com.cosmos.swingb.JBTextField();
        formatField = new com.cosmos.swingb.JBTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionField = new com.cosmos.swingb.JBTextArea();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        ownerField = new com.cosmos.acacia.gui.AcaciaComboBox();
        saveButton = new com.cosmos.swingb.JBButton();
        closeButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PatternMaskFormatPanel.class);
        jBPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("formatterDetailsPanel.border.title"))); // NOI18N
        jBPanel1.setName("formatterDetailsPanel"); // NOI18N
        jBPanel1.setTitle(resourceMap.getString("formatterDetailsPanel.title")); // NOI18N

        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        nameField.setText(resourceMap.getString("nameField.text")); // NOI18N
        nameField.setName("nameField"); // NOI18N

        formatField.setName("formatField"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        descriptionField.setColumns(20);
        descriptionField.setRows(5);
        descriptionField.setFont(resourceMap.getFont("descriptionField.font")); // NOI18N
        descriptionField.setName("descriptionField"); // NOI18N
        jScrollPane1.setViewportView(descriptionField);

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        ownerField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ownerField.setName("ownerField"); // NOI18N

        javax.swing.GroupLayout jBPanel1Layout = new javax.swing.GroupLayout(jBPanel1);
        jBPanel1.setLayout(jBPanel1Layout);
        jBPanel1Layout.setHorizontalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel1Layout.createSequentialGroup()
                        .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                    .addGroup(jBPanel1Layout.createSequentialGroup()
                        .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                            .addComponent(formatField, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                            .addComponent(ownerField, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jBPanel1Layout.setVerticalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ownerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(PatternMaskFormatPanel.class, this);
        saveButton.setAction(actionMap.get("saveAction")); // NOI18N
        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N

        closeButton.setAction(actionMap.get("closeAction")); // NOI18N
        closeButton.setForeground(resourceMap.getColor("closeButton.foreground")); // NOI18N
        closeButton.setIcon(resourceMap.getIcon("closeButton.icon")); // NOI18N
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jBPanel1.getAccessibleContext().setAccessibleName(resourceMap.getString("formatterDetailsPanel.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void initData() {
        bindGroup = new BindingGroup(); 
        
        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(ownerField, resourceToStringConverter);

        EntityProperties entityProps = getFormSession().getPatternMaskEntityProperties();
        
        nameField.bind(bindGroup, format, entityProps.getPropertyDetails("patternName") );
        formatField.bind(bindGroup, format, entityProps.getPropertyDetails("format") );
        descriptionField.bind(bindGroup, format, "description");
        ownerField.bind(bindGroup, getPossibleOwners(), format, entityProps.getPropertyDetails("owner"));
//        ownerField.bind(bindGroup, getPossibleOwners(), format, "ownerId", "partnerId", true,
//            false, false, null, UpdateStrategy.READ_WRITE);

        bindGroup.bind();
        
        setSaveActionState();

        if(bindGroup != null)
        {
            bindGroup.addBindingListener(new AbstractBindingListener()
            {

                @SuppressWarnings("unchecked")
                @Override
                public void targetChanged(Binding binding, PropertyStateEvent event) {
                    setSaveActionState();
                }
            });
        }
    }
    
    protected void setSaveActionState()
    {
        setEnabled(Button.Save, bindGroup.isContentValid());
    }
    
    public void setEnabled(Button button, boolean enabled) {
        ApplicationAction action = (ApplicationAction)getAction(button);
        if(action != null)
        {
            action.setEnabled(enabled);
        }
    }
    
    public javax.swing.Action getAction(Button button)
    {
        ApplicationActionMap actionMap = getApplicationActionMap();
        if(actionMap != null && button != null)
        {
            return actionMap.get(button.getActionName());
        }

        return null;
    }
    
    private List<BusinessPartner> getPossibleOwners() {
        List<BusinessPartner> possibleOwners = getFormSession().getOwnersList();
        return possibleOwners;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton closeButton;
    private com.cosmos.swingb.JBTextArea descriptionField;
    private com.cosmos.swingb.JBTextField formatField;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private com.cosmos.swingb.JBPanel jBPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.cosmos.swingb.JBTextField nameField;
    private com.cosmos.acacia.gui.AcaciaComboBox ownerField;
    private com.cosmos.swingb.JBButton saveButton;
    // End of variables declaration//GEN-END:variables
    
    
    /**
     * Getter for formSession
     * @return PatternMaskListRemote
     */
    public PatternMaskListRemote getFormSession() {
        if ( formSession==null ){
            try {
                formSession = InitialContext.doLookup(PatternMaskListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
        
        return formSession;
    }
    
    @Action
    public void saveAction() {
        try
        {
            format = getFormSession().savePatternMaskFormat(format);
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValue(format);
            close();
        }
        catch (Exception ex) {
            ValidationException ve = extractValidationException(ex);
            if ( ve!=null ){
                updateFieldsStyle(ve.getMessages(), bindGroup);
                String message = getValidationErrorsMessage(ve);
                JBErrorPane.showDialog(this, createSaveErrorInfo(message, null));
            }else{
                ex.printStackTrace();
                // TODO: Log that error
                String basicMessage = getResourceMap().getString("saveAction.Action.error.basicMessage", ex.getMessage());
                ErrorInfo errorInfo = createSaveErrorInfo(basicMessage, ex);
                JBErrorPane.showDialog(this, errorInfo);
            }
        }
    }
    
    /**
     * @param basicMessage
     * @param ex - may be null
     * @return
     */
    private ErrorInfo createSaveErrorInfo(String basicMessage, Exception ex) {
        ResourceMap resource = getResourceMap();
        String title = resource.getString("ValidationException.errorPanel.title");
        
        String detailedMessage = resource.getString("saveAction.Action.error.detailedMessage");
        String category = ProductPanel.class.getName() + ": saveAction.";
        Level errorLevel = Level.WARNING;
        
        Map<String, String> state = new HashMap<String, String>();
        
        ErrorInfo errorInfo = new ErrorInfo(title, basicMessage, detailedMessage, category, ex, errorLevel, state);
        return errorInfo;
    }
    
    /**
     * Iterate over all validation messages and compose one string - message per line.
     * @param ve
     * @return
     */
    private String getValidationErrorsMessage(ValidationException ve) {
        String errorMessagesHeader = getResourceMap().getString("ValidationException.errorsListFollow");
        StringBuilder msg = new StringBuilder(errorMessagesHeader);
        msg.append("\n\n");
        int i = 1;
        for (ValidationMessage validationMessage : ve.getMessages()) {
            String currentMsg = null;
            if ( validationMessage.getArguments()!=null )
                currentMsg = getResourceMap().getString(validationMessage.getMessageKey(), validationMessage.getArguments());
            else
                currentMsg = getResourceMap().getString(validationMessage.getMessageKey());
            
            msg.append(i).append(": ").append(currentMsg).append("\n\n");
            i++;
        }
        return msg.toString();
    }
    
    /**
     * Iterates through all fields and updates there appearance to error state if 
     * some of the messages is related to their respective property.
     * 
     * Note: currently - test support.
     * @param messages
     */
    @SuppressWarnings("unchecked")
    private void updateFieldsStyle(List<ValidationMessage> messages, BindingGroup bindingGroup) {
        //compose a set for easier and faster lookup
        bindingGroup.getBindings();
        Set<String> errorProperties = new HashSet<String>();
        for (ValidationMessage msg : messages) {
            if ( msg.getTarget()!=null ){
                String el = msg.getTarget();
                errorProperties.add(el);
            }
        }
        
        for (Binding binding : bindingGroup.getBindings()) {
            if ( binding.getTargetObject() instanceof JBTextField ){
                JBTextField textField = (JBTextField)binding.getTargetObject();
                if ( errorProperties.contains(textField.getPropertyName()) )
                    textField.setStyleInvalid("");//temporary code, TODO fix
            }else if ( binding.getTargetObject() instanceof JBComboBox){
                JBComboBox comboBox = (JBComboBox)binding.getTargetObject();
                if ( errorProperties.contains(comboBox.getPropertyName()) )
                    comboBox.setStyleInvalid(); 
            }
        }
    }
    
    /**
     * If {@link ValidationException} is thrown by the EJB, it will be set as some inner 'cause' of
     * an EJB exception. That is way it is a little bit tricky to get it. This method implements this
     * logic by checking if some of the causes for the main exception is actually a {@link ValidationException}
     * @param ex
     * @return - the ValidationException if some 'caused by' exception is {@link ValidationException},
     * null otherwise
     */
    private ValidationException extractValidationException(Exception ex) {
        Throwable e = ex;
        while ( e!=null ){
            if ( e instanceof ValidationException ){
                return (ValidationException) e;
            }
            else if ( e instanceof ServerException || e instanceof RemoteException ){
                e = e.getCause();
            }
            else if ( e instanceof EJBException )
                e = ((EJBException)e).getCausedByException();
            else
                break;
        }
        return null;
    }
    
    @Action
    public void closeAction() {
        setDialogResponse(DialogResponse.CLOSE);
        close();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected Class getResourceStopClass() {
        return AcaciaPanel.class;
    }
}
