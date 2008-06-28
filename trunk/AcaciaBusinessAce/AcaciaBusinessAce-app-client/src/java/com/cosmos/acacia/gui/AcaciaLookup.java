/*
 * AcaciaLookup.java
 *
 * Created on Сряда, 2008, Април 9, 21:14
 */

package com.cosmos.acacia.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.beansbinding.validation.BaseValidator;
import org.jdesktop.beansbinding.Property;

/**
 * @author jchan
 */
public class AcaciaLookup extends javax.swing.JPanel {

    /** Creates new form AcaciaLookup */
    public AcaciaLookup() {
        initComponents();

        // do this again explicitely - otherwise not working, but why?
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(
            com.cosmos.acacia.crm.gui.AcaciaApplication.class)
                .getContext()
                .getResourceMap(AcaciaLookup.class);
        button.setText(resourceMap.getString("lookup.selectButton.text"));
    }

    private AcaciaLookupProvider lookupProvider;

    private Object selectedItem;

    private ObjectToStringConverter toStringConverter;

    private Application application;
    private ApplicationContext applicationContext;
    private ResourceMap resourceMap;
    
    private BindingValidationListener bindingListener;
    
    private AcaciaLookupListener listener;
    
    @SuppressWarnings("unchecked")
    private Binding binding;

    public Object getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Object selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void updateText() {
        if (toStringConverter == null)
            toStringConverter = new AcaciaToStringConverter();
        
        String text = toStringConverter.getPreferredStringForItem(getSelectedItem());
        field.setText(text);
    }

    /**
     * @param lookupProvider
     * @param bindingGroup
     * @param beanEntity
     * @param propertyDetails
     * @param elPropertyItemDisplay
     * @param updateStrategy
     */
    @SuppressWarnings("unchecked")
    public Binding bind(AcaciaLookupProvider lookupProvider, BindingGroup bindingGroup,
                     Object beanEntity, PropertyDetails propertyDetails,
                     AutoBinding.UpdateStrategy updateStrategy) {
        return
        this.bind(lookupProvider, bindingGroup, beanEntity, propertyDetails, null, updateStrategy);
    }

    /**
     * @param lookupProvider
     * @param bindingGroup
     * @param beanEntity
     * @param propertyDetails
     * @param elPropertyItemDisplay -
     *            specify el property string {@link ELProperty#create(String)},
     *            to be used when displaying the selected item. Optional - may
     *            be null. Example value = "${firstName} ${lastName}". Expect
     *            exception if invalid.
     * @param updateStrategy
     */
    @SuppressWarnings("unchecked")
    public Binding bind(AcaciaLookupProvider lookupProvider, BindingGroup bindingGroup,
                     Object beanEntity, PropertyDetails propertyDetails,
                     String elPropertyItemDisplay, AutoBinding.UpdateStrategy updateStrategy) {
        if (lookupProvider == null)
            throw new IllegalArgumentException("Please specify not null parameter!");
        this.lookupProvider = lookupProvider;

        if (propertyDetails == null || propertyDetails.isHiden()) {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        String propertyName = propertyDetails.getPropertyName();
        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        
        ELProperty targetProperty = ELProperty.create("${selectedItem}");
        binding = Bindings.createAutoBinding(updateStrategy, beanEntity, elProperty, this,
            targetProperty);
//        binding = new AcaciaLookupBinding(updateStrategy, beanEntity, elProperty, this, targetProperty);
        
        Validator validator = propertyDetails.getValidator();
        if(validator != null)
        {
            binding.setValidator(validator);
        }
        bindingListener = new BindingValidationListener();
        binding.addBindingListener(bindingListener);

        bindingGroup.addBinding(binding);

        boolean editable = propertyDetails.isEditable() || !propertyDetails.isReadOnly();

        setEditable(editable);

        // initialize to string converter for displaying the selected item.
        if (elPropertyItemDisplay != null) {
            toStringConverter = new AcaciaToStringConverter(elPropertyItemDisplay);
        } else {
            toStringConverter = new AcaciaToStringConverter();
        }
        
        return binding;
    }
    
    public class BindingValidationListener extends AbstractBindingListener {
        @SuppressWarnings("unchecked")
        @Override
        public void bindingBecameBound(Binding binding) {
            updateText();
            validate(binding);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event) {
            updateText();
            validate(binding);
        }
    }
    
    @SuppressWarnings("unchecked")
    protected void validate(Binding binding) {
        boolean required = false;
        String tooltip = null;
        BaseValidator validator = (BaseValidator) binding.getValidator();
        if (validator != null) {
            tooltip = validator.getTooltip();
            required = validator.isRequired();
        }
        
        if ( !binding.isContentValid() ){
            if (required)
                setStyleRequired(tooltip);
            else
                setStyleInvalid(tooltip);
        } else {
            if (required)
                setStyleValid();
            else
                setStyleNormal();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        field = new com.cosmos.swingb.JBTextField();
        button = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(400, 22));

        field.setEditable(false);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AcaciaLookup.class);
        field.setText(resourceMap.getString("field.text")); // NOI18N
        field.setMargin(new java.awt.Insets(0, 2, 0, 0));
        field.setName("field"); // NOI18N
        field.setPreferredSize(new java.awt.Dimension(273, 20));
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fieldKeyPressed(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(AcaciaLookup.class, this);
        button.setAction(actionMap.get("onSelect")); // NOI18N
        button.setIcon(resourceMap.getIcon("button.icon")); // NOI18N
        button.setAlignmentX(0.5F);
        button.setHideActionText(true);
        button.setIconTextGap(2);
        button.setMargin(new java.awt.Insets(1, 1, 1, 1));
        button.setMaximumSize(new java.awt.Dimension(40, 27));
        button.setName("button"); // NOI18N
        button.setPreferredSize(new java.awt.Dimension(40, 27));
        button.setVerifyInputWhenFocusTarget(false);
        button.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(field, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(field, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addComponent(button, 0, 22, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    private void fieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldKeyPressed
        onKeyCommand(evt);
    }//GEN-LAST:event_fieldKeyPressed

    private void setEditable(boolean b) {
        button.setEnabled(b);
        field.setEnabled(b);
    }
    
    @SuppressWarnings("unchecked")
    private void onKeyCommand(KeyEvent evt){
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            clearSelectedValue();
        }else if ( evt.getKeyCode() == KeyEvent.VK_F5 ){
            onSelect();
        }else if ( field.equals(evt.getSource()) && evt.getKeyCode()==KeyEvent.VK_ENTER ){
            onSelect();
        }
    }
    
    /**
     * Clears the selected value and sets the target property to null.
     */
    @SuppressWarnings("unchecked")
    public void clearSelectedValue(){
        setSelectedItem(null);
        
        try {
            binding.getTargetProperty().setValue(this, null);
        } catch (NullPointerException ex) {
            field.setText(null);
        }
        
        if (listener != null)
            listener.valueCleared();
        
    }

    @SuppressWarnings("unchecked")
    private void buttonKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_buttonKeyPressed
        onKeyCommand(evt);
    }// GEN-LAST:event_buttonKeyPressed

    @SuppressWarnings("unchecked")
    @Action
    public void onSelect() {
        if (lookupProvider != null) {
            Object selectionItem = lookupProvider.showSelectionControl();
            if ( selectionItem!=null ){
                binding.getTargetProperty().setValue(this, selectionItem);
            }
        }
    }
    
    public void setStyleRequired(String tooltip) {
        setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.required.background");
        field.setBackground(color);
    }

    public void setStyleInvalid() {
        setStyleInvalid(null);
    }

    public void setStyleInvalid(String tooltip) {
        setToolTipText(tooltip);
        Color color = getResourceMap().getColor("validation.field.invalid.background");
        field.setBackground(color);
    }
    
    public void setStyleValid() {
        setToolTipText(null);
        Color color = getResourceMap().getColor("validation.field.valid.background");
        field.setBackground(color);
    }
    
    public void setStyleNormal() {
        setToolTipText(null);
        Color color = getResourceMap().getColor("validation.field.normal.background");
        field.setBackground(color);
    }
    
    protected ResourceMap getResourceMap()
    {
        if(resourceMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                resourceMap = context.getResourceMap(this.getClass());
            }
        }

        return resourceMap;
    }
    
    public ApplicationContext getContext()
    {
        if(applicationContext == null)
        {
            Application app = getApplication();
            if(app != null)
            {
                applicationContext = app.getContext();
            }
        }

        return applicationContext;
    }
    
    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        button.setEnabled(enabled);
    }

    public AcaciaLookupProvider getLookupProvider() {
        return lookupProvider;
    }

    public void setLookupProvider(AcaciaLookupProvider lookupProvider) {
        this.lookupProvider = lookupProvider;
    }

    public AcaciaLookupListener getListener() {
        return listener;
    }

    public void setListener(AcaciaLookupListener listener) {
        this.listener = listener;
    }
    
    
    /**
     * 
     * Created	:	13.04.2008
     * @author	Petar Milev
     * @version $Id: $
     *
     */
    @SuppressWarnings("unchecked")
//    private class AcaciaLookupBinding extends AutoBinding{
//
//        /**
//         * @param strategy
//         * @param sourceObject
//         * @param sourceProperty
//         * @param targetObject
//         * @param targetProperty
//         * @param name
//         */
//        @SuppressWarnings("unchecked")
//        protected AcaciaLookupBinding(UpdateStrategy strategy, Object sourceObject,
//                Property sourceProperty, Object targetObject, Property targetProperty) {
//            super(strategy, sourceObject, sourceProperty, targetObject, targetProperty, null);
//        }
//        
//        @Override
//        public boolean isContentValid() {
//            BaseValidator validator = (BaseValidator) binding.getValidator();
//            Result r = validator.validate(getSelectedItem());
//            if ( r==null )
//                return true;
//            else
//                return false;
//        }
//        
//    }
    
    private Application getApplication(){
        if(application == null)
            application = Application.getInstance();

        return application;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton button;
    private com.cosmos.swingb.JBTextField field;
    // End of variables declaration//GEN-END:variables

}
