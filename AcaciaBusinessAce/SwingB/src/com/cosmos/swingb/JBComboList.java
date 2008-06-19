/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.JBComboBox.BindingValidationListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import org.jdesktop.application.Action;
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
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author Miro
 */
public class JBComboList
    extends JXPanel
{
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;

    private JXButton lookupButton;
    private JComboBox comboBox;

    private boolean editable;

    private ObservableList observableData;
    private String propertyName;
    private Object beanEntity;
    private JComboBoxBinding comboBoxBinding;


    public JBComboList()
    {
        initComponents();
    }

    private void initComponents()
    {
        lookupButton = new JXButton();
        comboBox = new JComboBox();

        setName("JBComboList"); // NOI18N
        setLayout(new BorderLayout());

        ApplicationActionMap am = getApplicationActionMap();
        lookupButton.setAction(am.get("lookupButtonAction")); // NOI18N
        lookupButton.setAlignmentX(0.5F);
        lookupButton.setAlignmentY(0.5F);
        lookupButton.setHorizontalAlignment(SwingConstants.CENTER);
        lookupButton.setVerticalAlignment(SwingConstants.CENTER);
        lookupButton.setHorizontalTextPosition(SwingConstants.CENTER);
        lookupButton.setVerticalTextPosition(SwingConstants.CENTER);
        lookupButton.setName("lookupButton"); // NOI18N
        lookupButton.setPreferredSize(new Dimension(25, 25));
        add(lookupButton, BorderLayout.EAST);

        ResourceMap rm = getResourceMap();
        Font font = rm.getFont("comboBox.font");
        if(font != null)
            comboBox.setFont(font); // NOI18N
        comboBox.setName("comboBox"); // NOI18N
        add(comboBox, BorderLayout.CENTER);
    }

    public JComboBox getComboBox()
    {
        return comboBox;
    }

    public JXButton getLookupButton()
    {
        return lookupButton;
    }

    @Action
    public void lookupButtonAction()
    {
    }


    /*
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            List data,
            Object beanEntity,
            PropertyDetails propertyDetails,
            ObjectToStringConverter converter)
    {
        AutoCompleteDecorator.decorate(this, converter);
        return super.bind(bindingGroup, data, beanEntity, propertyDetails, AutoBinding.UpdateStrategy.READ_WRITE);
    }*/

    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        /*if(propertyDetails == null || propertyDetails.isHiden())
        {
            setEditable(false);
            setEnabled(false);
            return null;
        }

        List data = selectableListDialog.getListData();
        if(!(data instanceof ObservableList))
            observableData = ObservableCollections.observableList(data);
        else
            observableData = (ObservableList)data;
        this.propertyName = propertyDetails.getPropertyName();
        this.beanEntity = beanEntity;

        comboBoxBinding = SwingBindings.createJComboBoxBinding(
                updateStrategy,
                observableData,
                this);
        bindingGroup.addBinding(comboBoxBinding);

        ELProperty elProperty = ELProperty.create("${" + propertyName + "}");
        BeanProperty beanProperty = BeanProperty.create("selectedItem");
        Binding binding = Bindings.createAutoBinding(
                updateStrategy,
                beanEntity,
                elProperty,
                this,
                beanProperty);

        Validator validator = propertyDetails.getValidator();
        if(validator != null)
        {
            binding.setValidator(validator);
        }
        binding.addBindingListener(new BindingValidationListener());

        bindingGroup.addBinding(binding);

        setEditable(propertyDetails.isEditable());
        setEnabled(!propertyDetails.isReadOnly());*/

        return comboBoxBinding;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
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

    public JComboBoxBinding getComboBoxBinding()
    {
        return comboBoxBinding;
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

    public void setResourceMap(ResourceMap resourceMap)
    {
        this.resourceMap = resourceMap;
    }

    public Application getApplication()
    {
        if(application == null)
            application = Application.getInstance();

        return application;
    }

    public void setApplication(Application application)
    {
        this.application = application;
    }
}
