/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.swingb.binding.EntityBinder;
import com.cosmos.swingb.validation.Validatable;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task;
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
public class JBDatePicker extends JXDatePicker implements Validatable, EntityBinder {

    public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_DATE_TIME_FORMAT =
            DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;

    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;
    private Binding binding;
    private String propertyName;
    private Object beanEntity;
    private DateFormat dateFormat;

    public JBDatePicker() {
        init();
    }

    protected void init() {
        setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
    }

    @Override
    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails) {
        return bind(bindingGroup, beanEntity, propertyDetails, propertyDetails.getUpdateStrategy(), null);
    }

    protected String getExpression(EntityProperty propertyDetails) {
        String expression;
        if ((expression = propertyDetails.getCustomDisplay()) != null && expression.length() > 0) {
            return expression;
        }

        return "${" + propertyDetails.getPropertyName() + "}";
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails, DateFormat dateFormat) {
        return bind(bindingGroup, beanEntity, propertyDetails, propertyDetails.getUpdateStrategy(), dateFormat);
    }

    @Override
    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy) {
        return bind(bindingGroup, beanEntity, propertyDetails, propertyDetails.getUpdateStrategy(), null);
    }

    public Binding bind(BindingGroup bindingGroup,
            Object beanEntity,
            EntityProperty propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy, DateFormat dateFormat) {
        if (propertyDetails == null || propertyDetails.isHiden()) {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        this.propertyName = propertyDetails.getPropertyName();
        ELProperty elProperty = ELProperty.create(getExpression(propertyDetails));
        this.beanEntity = beanEntity;
        binding = bind(bindingGroup, elProperty, updateStrategy, dateFormat);
        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());

        Validator validator = propertyDetails.getValidator();
        if (validator != null) {
            binding.setValidator(validator);
        }

        binding.addBindingListener(new BindingValidationListener(this));

        return binding;
    }

    private Binding bind(BindingGroup bindingGroup, ELProperty elProperty,
            AutoBinding.UpdateStrategy updateStrategy, DateFormat dateFormat) {
        if (dateFormat == null) {
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        }
        setDateFormat(dateFormat);

        BeanProperty beanProperty = BeanProperty.create("date");
        binding = Bindings.createAutoBinding(updateStrategy, beanEntity, elProperty, this, beanProperty);
        bindingGroup.addBinding(binding);

        return binding;

    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        setFormats(dateFormat);
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Object getBeanEntity() {
        return beanEntity;
    }

    @Override
    public Binding getBinding() {
        return binding;
    }

    protected Date getPropertyValue() {
        try {
            return (Date)PropertyUtils.getProperty(beanEntity, propertyName);
        } catch(Exception ex) {
            throw new RuntimeException("beanEntity=" + beanEntity + ", propertyName=" + propertyName, ex);
        }
    }

    public ApplicationContext getContext() {
        if (applicationContext == null) {
            Application app = getApplication();
            if (app != null) {
                applicationContext = app.getContext();
            }
        }

        return applicationContext;
    }

    public ApplicationActionMap getApplicationActionMap() {
        if (applicationActionMap == null) {
            ApplicationContext context = getContext();
            if (context != null) {
                applicationActionMap = context.getActionMap(this);
            }
        }

        return applicationActionMap;
    }

    @Override
    public ResourceMap getResourceMap() {
        if (resourceMap == null) {
            ApplicationContext context = getContext();
            if (context != null) {
                resourceMap = context.getResourceMap(this.getClass());
            }
        }

        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Application getApplication() {
        if (application == null) {
            application = Application.getInstance();
        }

        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public void setStyleRequired(String tooltip) {
        setToolTipText(tooltip);
        setBackground(getResourceMap().getColor("validation.field.required.background"));
    }

    @Override
    public void setStyleInvalid(String tooltip) {
        setToolTipText(tooltip);
        setBackground(getResourceMap().getColor("validation.field.invalid.background"));
    }

    @Override
    public void setStyleValid() {
        setToolTipText(null);
        setBackground(getResourceMap().getColor("validation.field.valid.background"));
    }

    @Override
    public void setStyleNormal() {
        setToolTipText(null);
        setBackground(getResourceMap().getColor("validation.field.normal.background"));
    }

    @Override
    public void setBackground(Color color) {
        JFormattedTextField editor = getEditor();
        editor.setBackground(color);
        setEditor(editor);
    }

    @Override
    public Task refresh() {
        RefreshTask task = new RefreshTask();
        task.run();
        return task;
    }

    @Override
    public void clear() {
        setDate(null);
    }

    private class RefreshTask extends Task<Object, Void> {

        public RefreshTask() {
            super(Application.getInstance());
        }

        @Override
        protected Object doInBackground() throws Exception {
            Date date = getPropertyValue();
            setDate(date);
            return date;
        }
    }
}
