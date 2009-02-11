/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.NumberFormatter;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Converter;
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
    private ResourceMap resourceMap;

    @SuppressWarnings("unchecked")
    private Binding binding;
    private String propertyName;
    private Object beanEntity;

    /**
     * @param bindingGroup
     * @param beanEntity
     * @param propertyDetails
     * @return
     */
    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            PropertyDetails propertyDetails)
    {
        return bind(bindingGroup, beanEntity, propertyDetails, null);
    }

    /**
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
     * @param bindingGroup
     * @param beanEntity
     * @param propertyDetails
     * @param updateStrategy
     * @param format - can be null
     * @return
     */
    @SuppressWarnings("unchecked")
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

        binding = bind(bindingGroup, beanEntity, propertyDetails.getPropertyName(), updateStrategy);
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

    @SuppressWarnings("unchecked")
    private Binding bind(
            BindingGroup bindingGroup,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        this.propertyName = propertyName;
        this.beanEntity = beanEntity;

        final ELProperty sourceProperty = ELProperty.create("${" + propertyName + "}");
        ELProperty targetProperty = ELProperty.create("${text}");
        binding = Bindings.createAutoBinding(updateStrategy, beanEntity, sourceProperty, this, targetProperty);
        
        //if format is available - use it to convert, so the text is updated properly.
        //if we don't do this explicitly, the binding will occur after formatting and the values won't be properly displayed
        if ( getFormat()!=null ){
            binding.setConverter(new Converter() {
                //the result is value type (usually from String, but may be other)
                @Override
                public Object convertReverse(Object value) {
                    if ( value==null )
                        return null;
                    //assure that we are going to convert from string, 
                    //otherwise the default converter or binder may fail
                    String stringValue = value.toString();
                    try {
                        //try to have the formatter parse it (this is needed, because the converter is not that smart)
                        Object parsed = getFormat().parseObject(stringValue);
                        //now we have a properly parsed object, 
                        //but again we need its string representation for the default converter.
                        if ( parsed!=null )
                            stringValue = parsed.toString();
                    } catch (ParseException e) {
                        //if the parsing fails - ignore, the converter will try also
                    }
                    Class<?> sourceType = noPrimitiveType(sourceProperty.getWriteType(JBFormattedTextField.this.beanEntity));
                    //at last the default converter will make the recognized value, suitable for the source property
                    return sourceType.cast( 
                        com.cosmos.swingb.convertion.Converter.defaultConvert(stringValue, sourceType));
                }
    
                //the result is presentation type (usually String)
                @Override
                public Object convertForward(Object value) {
                    if ( value==null )
                        return "";
                    return getFormat().format(value);
                }
            });
        }
        bindingGroup.addBinding(binding);

        return binding;
    }
    
    private final Class<?> noPrimitiveType(Class<?> type) {
        if (!type.isPrimitive()) {
            return type;
        }

        if (type == Byte.TYPE) {
            return Byte.class;
        } else if (type == Short.TYPE) {
            return Short.class;
        } else if (type == Integer.TYPE) {
            return Integer.class;
        } else if (type == Long.TYPE) {
            return Long.class;
        } else if (type == Boolean.TYPE) {
            return Boolean.class;
        } else if (type == Character.TYPE) {
            return Character.class;
        } else if (type == Float.TYPE) {
            return Float.class;
        } else if (type == Double.TYPE) {
            return Double.class;
        }

        throw new IllegalArgumentException("Primitive not recognized!");
    }
    
    @Override
    public void setValue(Object value) {
        //forward to setText, because the converter should be engaged
        if ( value==null )
            setText("");
        else if ( getFormat()!=null )
            setText(getFormat().format(value));
        else
            setText(value.toString());
    }
    
    @Override
    public void setText(String t) {
        super.setText(t);
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
    
    @Override
    public Object getValue() {
//        Object value = null;
//        if ( getFormat()!=null ){
//            try {
//                value = getFormat().parseObject(getText());
//            } catch (ParseException e) {
//            }
//        }else{
//            value = super.getValue();
//        }
//        
//        return value;
        Object value = null;
        if ( getFormat()!=null ){
            try {
                value = getFormat().parseObject(getText());
            } catch (ParseException e) {
            }
        }else{
            
            value = super.getValue(); 
            //if the text is not null, but the value is, try to have the value from it
            if ( value==null && getText()!=null && !getText().trim().equals("") && getFormat()!=null ){
                try {
                    value = getFormat().parseObject(getText());
                } catch (ParseException e) {
                }
            }
        }
        return value;
    }

    public Binding getBinding() {
        return binding;
    }
}
