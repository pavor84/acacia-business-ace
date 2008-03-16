/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.validation.TextFieldValidator;
import com.cosmos.swingb.validation.Validator;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTextField;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Binding.SyncFailure;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class JBTextField
    extends JTextField
{
    private String propertyName;
    private PropertyDetails propertyDetails;
    private Object beanEntity;
    private Set<Validator> validators = new HashSet<Validator>();

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        if(propertyDetails == null || propertyDetails.isHiden())
        {
            setEditable(false);
            setEnabled(false);
            return null;
        }
        this.propertyDetails = propertyDetails;
        
        Binding binding = bind(bindingGroup, beanEntity, propertyDetails.getPropertyName(), updateStrategy);
        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());
        
        
        return binding;
    }

    private Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        this.propertyName = propertyName;
        this.beanEntity = beanEntity;

        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("text");
        Binding binding = Bindings.createAutoBinding(updateStrategy, beanEntity, elProperty, this, beanProperty);
        bindingGroup.addBinding(binding);
        
        binding.addBindingListener(new BindingListener(){

            // Checking validity after field becomes bound
            public void bindingBecameBound(Binding binding) {
                Validator validator = new TextFieldValidator(propertyDetails, JBTextField.this);
                addValidator(validator);
            }

            public void bindingBecameUnbound(Binding binding) {
               
            }

            public void syncFailed(Binding binding, SyncFailure failure) {
               
            }

            public void synced(Binding binding) {
                
            }

            public void sourceChanged(Binding binding, PropertyStateEvent event) {
               
            }

            public void targetChanged(Binding binding, PropertyStateEvent event) {
                
            }
            
        });

        return binding;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getBeanEntity() {
        return beanEntity;
    }
    
    private void addValidator(Validator validator){
        
        if (validator.isValidationRequired()){
            validators.add(validator);
            checkValidity(validator);
            
            switch (validator.getEvent()){
            
                case Event.KEY_RELEASE:
                    addKeyListener(new KeyAdapter(){
                        @Override
                        public void keyReleased(KeyEvent e){
                          for (Validator validator: validators)
                            checkValidity(validator);
                        }
                    });
                    break;
                
                case Event.LOST_FOCUS:
                    addFocusListener(new FocusAdapter(){
                        @Override
                        public void focusLost(FocusEvent e) {
                           for (Validator validator: validators)
                            checkValidity(validator);
                        }
                    });
                    break;
            }
        }
    }
    
    public boolean isValid(){
        for (Validator validator: validators){
            if (!validator.isValid())
                return false;
        }
        return true;
    }

    protected void setNormal(){
        setBackground(Color.WHITE);
        setToolTipText(null);
    }
    
    protected void setValid(){
        setBackground(Color.GREEN);
        setToolTipText(null);
    }
    
    protected void setInvalid(){
        setBackground(Color.PINK);
        StringBuffer tooltipText = new StringBuffer();
        for (Validator validator: validators){
            if (validator.getTooltipText().length() > 0) {
                tooltipText.append(validator.getTooltipText());
                tooltipText.append("; ");
            }
        }
        if (tooltipText.length() > 0)
            setToolTipText(tooltipText.toString());
        else
            setToolTipText(null);
    }
    
    private void checkValidity(Validator validator){
        if (validator == null) return;
        
        if (!validator.isValid())
            setInvalid();
        else if (!ValidationType.REGEX.equals(validator.getValidationType()))
            setValid();
        else
            setNormal();
    }
}
