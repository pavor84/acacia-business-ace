package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.error.ErrorInfo;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBErrorPane;
import com.cosmos.swingb.JBTextField;
import com.cosmos.swingb.listeners.NestedFormListener;
import javax.ejb.EJB;
import javax.naming.InitialContext;

/**
 * A base class for all panels representing a single entity
 *
 * @author Bozhidar Bozhanov
 */
public abstract class BaseEntityPanel extends AcaciaPanel {

    protected static Logger log = Logger.getLogger(BaseEntityPanel.class);

    protected DialogResponse modifiedResponse = null;

    @EJB
    private ClassifiersRemote classifiersFormSession;
    
    final protected ClassifiersRemote getClassifiersFormSession()
    {
        if(classifiersFormSession == null)
        {
            try
            {
                classifiersFormSession = InitialContext.doLookup(ClassifiersRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return classifiersFormSession;
    }
    
    public BaseEntityPanel(DataObject dataObject)
    {
        super(dataObject);
    }


    public abstract void performSave(boolean closeAfter);

    public abstract BindingGroup getBindingGroup();

    public abstract Object getEntity();

    public abstract EntityFormButtonPanel getButtonPanel();

    protected void init()
    {
        initData();
        initSaveStateListener();
        setFonts();
    }

    protected void initSaveStateListener()
    {
        getButtonPanel().initSaveStateListener();
    }

    public boolean checkFormValidity()
    {
        if (getBindingGroup().isContentValid()){
                return true;
        }
        // TODO : Detailed message?
        JOptionPane.showMessageDialog(this, getResourceMap().getString("save.invalid"));
        return false;
    }


    public void saveAction()
    {
        try {
            performSave(true);
        } catch (Exception ex){
            checkForValidationException(ex);
        }
    }

    public void closeAction() {
        BindingGroup bindingGroup;
        if((bindingGroup = getBindingGroup()) != null && bindingGroup.isContentChanged())
        {
            if(!closeDialogConfirmation())
                return;
        }

        if (modifiedResponse != null)
            setDialogResponse(modifiedResponse);
        else
            setDialogResponse(DialogResponse.CLOSE);
        close();
    }

    public DataObject getDataObject()
    {
        if (getEntity() instanceof DataObjectBean)
        {
            return ((DataObjectBean) getEntity()).getDataObject();
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

    @SuppressWarnings("unchecked")
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
                        try {
                            setModifiedResponse(DialogResponse.SAVE);
                            performSave(false);
                            
                            // Checking is special conditions for disabling new window after save are met
                            if (isSpecialConditionPresent() == true)
                                return false;
                            
                        } catch (Exception ex) {
                            checkForValidationException(ex);
                            return false;
                        }

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
    protected void dialogWindowClosing(WindowEvent event)
    {
        BindingGroup bindingGroup;
        if((bindingGroup = getBindingGroup()) != null && bindingGroup.isContentChanged())
        {
            if(!closeDialogConfirmation())
                return;
        }
        if (modifiedResponse != null)
            setDialogResponse(modifiedResponse);
        else
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

    protected void checkForValidationException(Exception ex)
    {
        ValidationException ve = extractValidationException(ex);
        if ( ve!=null ){
            updateFieldsStyle(ve.getMessages());
            String message = getValidationErrorsMessage(ve);
            JBErrorPane.showDialog(this, createSaveErrorInfo(message, null));
        } else {
            log.error(ex);
            String basicMessage = getResourceMap().getString("saveAction.Action.error.basicMessage", ex.getMessage());
            ErrorInfo errorInfo = createSaveErrorInfo(basicMessage, ex);
            JBErrorPane.showDialog(this, errorInfo);
        }
    }

    protected void setModifiedResponse(DialogResponse modifiedResponse)
    {
        this.modifiedResponse = modifiedResponse;
    }
    
    /**
     * Override this method if you want to add special conditions,
     * which, if met, will disable the opening of nested forms
     * 
     * @return if special conditions are present
     */
    protected boolean isSpecialConditionPresent()
    {
        return false;
    }
}
