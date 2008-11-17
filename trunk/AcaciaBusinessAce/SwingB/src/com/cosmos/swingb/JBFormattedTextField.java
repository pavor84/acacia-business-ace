/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.NumberFormatter;

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

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.validation.Validatable;

/**
 *
 * @author Miro
 */
public class JBFormattedTextField
    extends JFormattedTextField implements Validatable
{
    private Format format;

    public JBFormattedTextField()
    {
        super();
        init();
    }
    
    public JBFormattedTextField(Format format) {
        super(format);
        init();
    }
    
    private void init() {
        setFocusLostBehavior(JBFormattedTextField.PERSIST);
        setHorizontalAlignment(JBFormattedTextField.TRAILING);
    }

    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;

    private Binding binding;
    private String propertyName;
    private Object beanEntity;

    /**
     * 
     * @param bindingGroup
     * @param beanEntity
     * @param propertyDetails
     * @param format - can be null
     * @return
     */
    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails, Format format)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE, format);
    }

    /**
     * 
     * @param bindingGroup
     * @param beanEntity
     * @param propertyDetails
     * @param updateStrategy
     * @param format - can be null
     * @return
     */
    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy, Format format)
    {
        
        if ( format!=null )
            setFormat(format);
        
        if(propertyDetails == null || propertyDetails.isHiden())
        {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        bind(bindingGroup, beanEntity, propertyDetails.getPropertyName(), updateStrategy);
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
    
    public Application getApplication() {
        if(application == null)
            application = Application.getInstance();

        return application;
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

    private Binding bind(
            BindingGroup bindingGroup,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        this.propertyName = propertyName;
        this.beanEntity = beanEntity;

        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("text");
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
    
    /**
     * Returns an AbstractFormatterFactory suitable for the passed in
     * Object type.
     */
    private AbstractFormatterFactory getDefaultFormatterFactory(Object type) {
        if (type instanceof DateFormat) {
            return new DefaultFormatterFactory(new DateFormatter
                                               ((DateFormat)type));
        }
        if (type instanceof NumberFormat) {
            return new DefaultFormatterFactory(new NumberFormatter(
                                               (NumberFormat)type));
        }
        if (type instanceof Format) {
            return new DefaultFormatterFactory(new InternationalFormatter(
                                               (Format)type));
        }
        if (type instanceof Date) {
            return new DefaultFormatterFactory(new DateFormatter());
        }
        if (type instanceof Number) {
            AbstractFormatter displayFormatter = new NumberFormatter();
        ((NumberFormatter)displayFormatter).setValueClass(type.getClass());
            AbstractFormatter editFormatter = new NumberFormatter(
                                  new DecimalFormat("#.#"));
        ((NumberFormatter)editFormatter).setValueClass(type.getClass());

            return new DefaultFormatterFactory(displayFormatter,
                                               displayFormatter,editFormatter);
        }
        return new DefaultFormatterFactory(new DefaultFormatter());
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
        setFormatterFactory(getDefaultFormatterFactory(format));
    }
}
