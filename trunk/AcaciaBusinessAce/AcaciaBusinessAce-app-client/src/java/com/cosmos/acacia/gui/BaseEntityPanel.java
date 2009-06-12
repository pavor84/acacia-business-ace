package com.cosmos.acacia.gui;

import java.awt.Component;
import java.awt.TextComponent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

//import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.error.ErrorInfo;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
import com.cosmos.acacia.gui.EntityFormButtonPanel.Button;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBErrorPane;
import com.cosmos.swingb.JBTextField;
import com.cosmos.swingb.listeners.NestedFormListener;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 * A base class for all panels representing a single entity
 *
 * @author Bozhidar Bozhanov
 */
public abstract class BaseEntityPanel extends AcaciaPanel {

    //protected static Logger log = Logger.getLogger(BaseEntityPanel.class);
    protected DialogResponse modifiedResponse = null;
    private EntityBindingListener entityBindingListener;
    private boolean editable = true;
    protected Object[] parameters;

    public BaseEntityPanel(Object[] parameters) {
        this.parameters = parameters;
        initConstructor();
    }

    protected BaseEntityPanel(DataObjectBean dataObjectBean) {
        super(dataObjectBean);
        initConstructor();
    }

    public BaseEntityPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        initConstructor();
    }

    private void initConstructor() {
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent evt) {
                onKeyEvent(evt);
            }
        });
    }

    public abstract void performSave(boolean closeAfter);

    public abstract BindingGroup getBindingGroup();

    public abstract Object getEntity();

    public abstract EntityFormButtonPanel getButtonPanel();

    protected void init() {
        entityBindingListener = new EntityBindingListener();
        getBindingGroup().addBindingListener(entityBindingListener);
        initData();
        initSaveStateListener();
        setPrintButtonVisibility();
        setFonts();
    }

    private void setPrintButtonVisibility() {
        boolean printButtonVisible = getReport() != null || getReports().size() > 0;


        getButtonPanel().setVisible(Button.Print, printButtonVisible);
    }

    protected void initSaveStateListener() {
        try {
            getButtonPanel().initSaveStateListener(this);
        } catch (NullPointerException ex) {
            // No button panel
        }
    }

    public boolean checkFormValidity() {
        if (getBindingGroup().isContentValid()) {
            return true;
        }
        // TODO : Detailed message?
        JOptionPane.showMessageDialog(this, getResourceMap().getString("save.invalid"));
        return false;
    }

    public void saveAction() {
        try {
            performSave(true);
        } catch (Exception ex) {
            checkForValidationException(ex);
        }
    }

    final public void closeAction() {
        if (isEditable()) {
            BindingGroup bindingGroup;
            if ((bindingGroup = getBindingGroup()) != null && bindingGroup.isContentChanged()) {
                if (!closeDialogConfirmation()) {
                    return;
                }
            }
        }

        if (modifiedResponse != null) {
            setDialogResponse(modifiedResponse);
        } else {
            setDialogResponse(DialogResponse.CLOSE);
        }
        close();
    }

    public DataObject getDataObject() {
        if (getEntity() instanceof DataObjectBean) {
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
    private void updateFieldsStyle(List<ValidationMessage> messages) {
        //compose a set for easier and faster lookup
        BindingGroup bindingGroup;
        if ((bindingGroup = getBindingGroup()) != null) {
            bindingGroup.getBindings();
            Set<String> errorProperties = new HashSet<String>();
            for (ValidationMessage msg : messages) {
                if (msg.getTarget() != null) {
                    String el = msg.getTarget();
                    errorProperties.add(el);
                }
            }

            for (Binding binding : bindingGroup.getBindings()) {
                if (binding.getTargetObject() instanceof JBTextField) {
                    JBTextField textField = (JBTextField) binding.getTargetObject();
                    if (errorProperties.contains(textField.getPropertyName())) {
                        textField.setStyleInvalid("");//temporary code, TODO fix
                    }
                } else if (binding.getTargetObject() instanceof JBComboBox) {
                    JBComboBox comboBox = (JBComboBox) binding.getTargetObject();
                    if (errorProperties.contains(comboBox.getPropertyName())) {
                        comboBox.setStyleInvalid();
                    }
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
    protected Map<String, String> populateState() {
        Map<String, String> state = new HashMap<String, String>();
        Class clazz = getEntity().getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (!Modifier.isTransient(field.getModifiers())) {
                    state.put(field.getName(), String.valueOf(field.get(getEntity())));
                }
            } catch (Exception ex) {
                // Log?
                ex.printStackTrace();
            }
        }
        return state;
    }

    protected void addNestedFormListener(AbstractTablePanel table) {
        table.addNestedFormListener(createNestedFormListener(table));
    }

    protected NestedFormListener createNestedFormListener(final AbstractTablePanel table) {
        final String dialogMessage = getResourceMap().getString("save.confirm");
        NestedFormListener listener = new NestedFormListener() {

            @Override
            public boolean actionPerformed() {
                if (getDataObject() == null) {
                    int answer = JOptionPane.showConfirmDialog(
                            BaseEntityPanel.this, dialogMessage, "", JOptionPane.OK_CANCEL_OPTION);

                    if (answer == JOptionPane.OK_OPTION && checkFormValidity()) {
                        try {
                            setModifiedResponse(DialogResponse.SAVE);
                            performSave(false);

                            // Checking is special conditions for disabling new window after save are met
                            if (isSpecialConditionPresent() == true) {
                                return false;
                            }

                        } catch (Exception ex) {
                            checkForValidationException(ex);
                            return false;
                        }

                        BigInteger doId = getDataObject().getDataObjectId();
                        table.setParentDataObjectId(doId);
                        table.setParentDataObjectToAssociatedTables(doId);
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
    protected void dialogWindowClosing(WindowEvent event) {
        if (isEditable()) {
            BindingGroup bindingGroup;
            if ((bindingGroup = getBindingGroup()) != null && bindingGroup.isContentChanged()) {
                if (!closeDialogConfirmation()) {
                    return;
                }
            }
        }

        if (modifiedResponse != null) {
            setDialogResponse(modifiedResponse);
        } else {
            setDialogResponse(DialogResponse.CLOSE);
        }

        super.dialogWindowClosing(event);
    }

    protected boolean closeDialogConfirmation() {
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

    protected boolean showConfirmationDialog(String msg) {
        ResourceMap resource = getResourceMap();
        String title = resource.getString("ConfirmDialog.areYouSureTitle");
        int result = JOptionPane.showConfirmDialog(
                this.getParent(),
                msg,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return JOptionPane.YES_OPTION == result;
    }

    protected void checkForValidationException(Exception ex) {
        ValidationException ve = extractValidationException(ex);
        if (ve != null) {
            updateFieldsStyle(ve.getMessages());
            String message = getValidationErrorsMessage(ve);
            JBErrorPane.showDialog(this, createSaveErrorInfo(message, null));
        } else {
            //log.error(ex);
            ex.printStackTrace();
            String basicMessage = getResourceMap().getString("saveAction.Action.error.basicMessage", ex.getMessage());
            ErrorInfo errorInfo = createSaveErrorInfo(basicMessage, ex);
            JBErrorPane.showDialog(this, errorInfo);
        }
    }

    protected void setModifiedResponse(DialogResponse modifiedResponse) {
        this.modifiedResponse = modifiedResponse;
    }

    /**
     * Override this method if you want to add special conditions,
     * which, if met, will disable the opening of nested forms
     *
     * @return if special conditions are present
     */
    protected boolean isSpecialConditionPresent() {
        return false;
    }

    private void onKeyEvent(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            closeAction();
        } else if (evt.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK && evt.getKeyCode() == KeyEvent.VK_S) {
            if (isEditable()) {
                saveAction();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void setReadonly() {
        this.editable = false;
        getButtonPanel().getButton(Button.Save).setVisible(editable);
        getButtonPanel().getButton(Button.Problems).setVisible(editable);

        BindingGroup bindGroup = getBindingGroup();
        if (bindGroup != null) {
            for (Binding binding : bindGroup.getBindings()) {
                Object targetObject = binding.getTargetObject();
                if (targetObject instanceof JBComboList) {
                    JBComboList c = (JBComboList) targetObject;
                    c.setEditable(editable);
                } else if (targetObject instanceof JTextComponent) {
                    JTextComponent c = (JTextComponent) targetObject;
                    c.setEditable(editable);
                } else if (targetObject instanceof TextComponent) {
                    TextComponent c = (TextComponent) targetObject;
                    c.setEditable(editable);
                } else if (targetObject instanceof Component) {
                    Component c = (Component) targetObject;
                    c.setEnabled(editable);
                }
            }
        }
    }

    public boolean isEditable() {
        return editable;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List getEntities() {
        List entities = new ArrayList(1);
        entities.add(getEntity());
        return entities;
    }

    protected void entityChanged(Binding binding, PropertyStateEvent event) {
        getButtonPanel().setSaveActionState(this);
    }

    public boolean isContentValid() {
        return getBindingGroup().isContentValid();
    }

    private class EntityBindingListener extends AbstractBindingListener {

        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event) {
            entityChanged(binding, event);
        }
    }
}
