/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.SelectableListDialog;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

/**
 *
 * @author Miro
 */
public class AcaciaComboList
    extends JBComboList
{
    private static final long serialVersionUID = 1L;

    public AcaciaComboList()
    {
        super(AcaciaApplication.class);
    }


    public JComboBoxBinding bind(
        BindingGroup bindingGroup,
        SelectableListDialog selectableListDialog,
        Object beanEntity,
        PropertyDetails propertyDetails,
        String elPropertyItemDisplay)
    {
        return bind(bindingGroup,
                selectableListDialog,
                beanEntity,
                propertyDetails,
                elPropertyItemDisplay,
                UpdateStrategy.READ_WRITE);
    }
    
    public JComboBoxBinding bind(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            Object beanEntity,
            PropertyDetails propertyDetails,
            String elPropertyItemDisplay,
            AutoBinding.UpdateStrategy updateStrategy)
    {
        ObjectToStringConverter converter;
        if (elPropertyItemDisplay != null)
        {
            converter = new AcaciaToStringConverter(elPropertyItemDisplay);
        }
        else
        {
            converter = new AcaciaToStringConverter();
        }

        getComboBox().setConverter(converter);
        return super.bind(
            bindingGroup,
            selectableListDialog,
            beanEntity,
            propertyDetails,
            converter,
            updateStrategy);
    }

    @Override
    public void initUnbound(
            SelectableListDialog selectableListDialog,
            ObjectToStringConverter converter)
    {
        if (converter == null)
            converter = new AcaciaToStringConverter();

        getComboBox().setConverter(converter);
        
        super.initUnbound(selectableListDialog, converter);
    }

    @Override
    public void initUnbound(
            SelectableListDialog selectableListDialog)
    {
        initUnbound(selectableListDialog, new AcaciaToStringConverter());
    }

    public void initUnbound(SelectableListDialog selectableListDialog,
            String elPropertyItemDisplay)
    {
        ObjectToStringConverter converter;
        if (elPropertyItemDisplay != null)
        {
            converter = new AcaciaToStringConverter(elPropertyItemDisplay);
        }
        else
        {
            converter = new AcaciaToStringConverter();
        }

        initUnbound(selectableListDialog, converter);
    }
}
