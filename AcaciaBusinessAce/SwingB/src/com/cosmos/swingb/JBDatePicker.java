/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.validation.Validatable;
import java.awt.Color;
import java.text.DateFormat;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Miro
 */
public class JBDatePicker
    extends JXDatePicker
    implements Validatable
{

    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;

    private Binding binding;
    private String propertyName;
    private Object beanEntity;
    
    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE, null);
    }
    
    public Binding bind(BindingGroup bindingGroup,
                        Object beanEntity,
                        PropertyDetails propertyDetails, DateFormat dateFormat)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE, dateFormat);
    }
    
    public Binding bind(BindingGroup bindingGroup,
                        Object beanEntity,
                        PropertyDetails propertyDetails,
                        AutoBinding.UpdateStrategy updateStrategy){
        return bind(bindingGroup, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE, null);
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy, DateFormat dateFormat)
    {
        if(propertyDetails == null || propertyDetails.isHiden())
        {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        binding = bind(bindingGroup, beanEntity, propertyDetails.getPropertyName(), updateStrategy, dateFormat);
        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());

        Validator validator = propertyDetails.getValidator();
        if(validator != null)
        {
            binding.setValidator(validator);
        }

        binding.addBindingListener(new BindingValidationListener(this));

        return binding;
    }

     private Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy, DateFormat dateFormat)
     {
        if ( dateFormat==null )
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        this.setFormats(dateFormat);

        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("date");
        binding = Bindings.createAutoBinding(updateStrategy, beanEntity, elProperty, this, beanProperty);
        bindingGroup.addBinding(binding);

        return binding;

     }
     
     
    public String getPropertyName() {
       return propertyName;
    }

    public Object getBeanEntity() {
        return beanEntity;
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

    public ApplicationActionMap getApplicationActionMap()
    {
        if(applicationActionMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                applicationActionMap = context.getActionMap(this);
            }
        }

        return applicationActionMap;
    }

    public ResourceMap getResourceMap()
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

    public void setResourceMap(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Application getApplication() {
        if(application == null)
            application = Application.getInstance();

        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void setStyleRequired(String tooltip) {
        setToolTipText(tooltip);
        setBackground(getResourceMap().getColor("validation.field.required.background"));
    }

    public void setStyleInvalid(String tooltip) {
        setToolTipText(tooltip);
        setBackground(getResourceMap().getColor("validation.field.invalid.background"));
    }
    
    public void setStyleValid() {
        setToolTipText(null);
        setBackground(getResourceMap().getColor("validation.field.valid.background"));
    }
    
    public void setStyleNormal() {
        setToolTipText(null);
        setBackground(getResourceMap().getColor("validation.field.normal.background"));
    }
    
    public void setBackground(Color color) {
        JFormattedTextField editor = getEditor();
        editor.setBackground(color);
        setEditor(editor);
    }
}
