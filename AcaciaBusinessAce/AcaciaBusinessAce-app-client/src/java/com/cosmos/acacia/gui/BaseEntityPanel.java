/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBErrorPane;
import com.cosmos.swingb.JBTextField;
import com.cosmos.swingb.listeners.NestedFormListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.ejb.EJBException;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.error.ErrorInfo;

/**
 *
 * @author Bozhidar Bozhanov
 */
public abstract class BaseEntityPanel extends AcaciaPanel {

    public BaseEntityPanel(DataObject dataObject)
    {
        super(dataObject);
    }
    
        
    public abstract void performSave(boolean closeAfter);
    
    public abstract void closeAction();
    
    public abstract BindingGroup getBindingGroup();
    
    public abstract DataObject getDataObject();
    
    public abstract Object getEntity();
    
    public abstract EntityFormButtonPanel getButtonPanel();
        
    protected void initSaveStateListener()
    {
        getButtonPanel().initSaveStateListener();
    }
        
    public boolean checkFormValidity()
    {
        if (getBindingGroup().isContentValid()){
                return true;
        } else {
            // TODO : Detailed message?
            JOptionPane.showMessageDialog(this, getResourceMap().getString("save.invalid"));
            return false;
        }
    }
    
    public void saveAction()
    {
        try {
            performSave(true);
        } catch (Exception ex){
            ValidationException ve = extractValidationException(ex);
            if ( ve!=null ){
                updateFieldsStyle(ve.getMessages());
                String message = getValidationErrorsMessage(ve);
                JBErrorPane.showDialog(this, createSaveErrorInfo(message, null));
            } else {
                ex.printStackTrace();
                // TODO: Log that error
                String basicMessage = getResourceMap().getString("saveAction.Action.error.basicMessage", ex.getMessage());
                ErrorInfo errorInfo = createSaveErrorInfo(basicMessage, ex);
                JBErrorPane.showDialog(this, errorInfo);
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
    protected ValidationException extractValidationException(Exception ex)
    {
        Throwable e = ex;
        while(e != null)
        {
            System.out.println("VException is: " + e.getClass());
            if(e instanceof ValidationException)
            {
                return (ValidationException)e;
            }
            else if(e instanceof ServerException || e instanceof RemoteException)
            {
                e = e.getCause();
            }
            else if(e instanceof EJBException)
                e = ((EJBException)e).getCausedByException();
            else
                break;
        }

        return null;
    }
    
     /**
     * Iterates through all fields and updates there appearance to error state if 
     * some of the messages is related to their respective property.
     * 
     * Note: currently - test support.
     * @param messages
     */
    @SuppressWarnings("unchecked")
    private void updateFieldsStyle(List<ValidationMessage> messages)
    {
        //compose a set for easier and faster lookup
        BindingGroup bindingGroup;
        if((bindingGroup = getBindingGroup()) != null)
        {
            bindingGroup.getBindings();
            Set<String> errorProperties = new HashSet<String>();
            for(ValidationMessage msg : messages)
            {
                if(msg.getTarget() != null)
                {
                    String el = msg.getTarget();
                    errorProperties.add(el);
                }
            }

            for(Binding binding : bindingGroup.getBindings())
            {
                if(binding.getTargetObject() instanceof JBTextField)
                {
                    JBTextField textField = (JBTextField)binding.getTargetObject();
                    if(errorProperties.contains(textField.getPropertyName()))
                        textField.setStyleInvalid("");//temporary code, TODO fix
                }
                else if(binding.getTargetObject() instanceof JBComboBox)
                {
                    JBComboBox comboBox = (JBComboBox)binding.getTargetObject();
                    if(errorProperties.contains(comboBox.getPropertyName()))
                        comboBox.setStyleInvalid(); 
                }
            }
        }
    }

    /**
     * Iterate over all validation messages and compose one string - message per line.
     * @param ve
     * @return
     */
    private String getValidationErrorsMessage(ValidationException ve)
    {
        StringBuilder msg = new StringBuilder();
        for(ValidationMessage validationMessage : ve.getMessages())
        {
            String currentMsg = null;
            if(validationMessage.getArguments() != null)
                currentMsg = getResourceMap().getString(validationMessage.getMessageKey(), validationMessage.getArguments());
            else
                currentMsg = getResourceMap().getString(validationMessage.getMessageKey());
            msg.append(currentMsg+"\n");
        }

        return msg.toString();
    }

    /**
     * @param basicMessage
     * @param ex - may be null
     * @return
     */
    private ErrorInfo createSaveErrorInfo(String basicMessage, Exception ex) {
        ResourceMap resource = getResourceMap();
        String title = resource.getString("saveAction.Action.error.title");
        
        String detailedMessage = resource.getString("saveAction.Action.error.detailedMessage");
        String category = getClass().getName() + ": saveAction.";
        Level errorLevel = Level.WARNING;
        
        Map<String, String> state = populateState();
        
        ErrorInfo errorInfo = new ErrorInfo(title, basicMessage, detailedMessage, category, ex, errorLevel, state);
        return errorInfo;
    }
    
    protected Map<String, String> populateState()
    {
        Map <String, String> state = new HashMap<String, String>();
        Class clazz = getEntity().getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field: fields)
        {
            try {
                field.setAccessible(true);
                if (!Modifier.isTransient(field.getModifiers()))
                    state.put(field.getName(), String.valueOf(field.get(getEntity())));
            } catch (Exception ex) {
                // Log?
            }
        }
        return state;
    }
    
    protected void addNestedFormListener(AbstractTablePanel table)
    {
        table.addNestedFormListener(createNestedFormListener(table));
    }
    
    protected NestedFormListener createNestedFormListener(final AbstractTablePanel table)
    {
        final String dialogMessage = getResourceMap().getString("save.confirm");
        NestedFormListener listener = new NestedFormListener() {
            public boolean actionPerformed() {
                if (getDataObject() == null)
                {
                    int answer = JOptionPane.showConfirmDialog(
                            BaseEntityPanel.this, dialogMessage, "", JOptionPane.OK_CANCEL_OPTION);

                    if (answer == JOptionPane.OK_OPTION && checkFormValidity()) {
                        performSave(false);
                        table.setParentDataObject(getDataObject());
                        return true;
                    }

                    return false;
                }
                return true;
            }
        };
        
        return listener;
    }
    
    
    @Override
    protected Class getResourceStopClass()
    {
        return BaseEntityPanel.class;
    }

    @Override
    protected void dialogWindowClosing(WindowEvent event)
    {
        BindingGroup bindingGroup;
        if((bindingGroup = getBindingGroup()) != null && bindingGroup.isContentChanged())
        {
            if(!closeDialogConfirmation())
                return;
        }

        setDialogResponse(DialogResponse.CLOSE);
        super.dialogWindowClosing(event);
    }

    protected boolean closeDialogConfirmation()
    {
        ResourceMap resource = getResourceMap();
        String title = resource.getString("closeAction.ConfirmDialog.unsavedData.title");
        String message = resource.getString("closeAction.ConfirmDialog.unsavedData.message");
        Icon icon = resource.getImageIcon("closeAction.ConfirmDialog.unsavedData.icon");
        int result = JOptionPane.showConfirmDialog(
                this.getParent(),
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon);
        return JOptionPane.YES_OPTION == result;
    }

}
