/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.util.List;
import javax.swing.JComboBox;
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
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;

/**
 *
 * @author Miro
 */
public class JBComboBox
    extends JComboBox
{
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;

    private ObservableList observableData;
    private String propertyName;
    private Object beanEntity;

    public JBComboBox()
    {
        super();
    }

    public JBComboBox(Class<? extends Application> applicationClass)
    {
        this(Application.getInstance(applicationClass));
    }

    public JBComboBox(Application application)
    {
        this();
        this.application = application;
        setRenderer(new BeanListCellRenderer(application.getClass()));
    }

    public JComboBoxBinding bind(BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            String propertyName)
    {
        return bind(bindingGroup, data, beanEntity, propertyName, AutoBinding.UpdateStrategy.READ_WRITE);
    }

    public JComboBoxBinding bind(BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            String propertyName,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        if(!(data instanceof ObservableList))
            observableData = ObservableCollections.observableList(data);
        else
            observableData = (ObservableList)data;
        this.propertyName = propertyName;
        this.beanEntity = beanEntity;

        JComboBoxBinding comboBoxBinding = SwingBindings.createJComboBoxBinding(updateStrategy, observableData, this);
        bindingGroup.addBinding(comboBoxBinding);

        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("selectedItem");
        Binding binding = Bindings.createAutoBinding(updateStrategy, beanEntity, elProperty, this, beanProperty);
        bindingGroup.addBinding(binding);

        return comboBoxBinding;
    }

    public ObservableList getData() {
        return observableData;
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
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

}
