/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.JBTreeTable;
import com.cosmos.swingb.SelectableListDialog;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

/**
 *
 * @author Miro
 */
public class AcaciaTreeTable
    extends JBTreeTable
{
    public AcaciaTreeTable()
    {
    }

    /*public void bindComboListCellEditor(
        BindingGroup bindingGroup,
        SelectableListDialog selectableListDialog,
        PropertyDetails propertyDetails)
    {
        bindComboListCellEditor(bindingGroup, selectableListDialog, propertyDetails, (String)null);
    }

    public void bindComboListCellEditor(
        BindingGroup bindingGroup,
        SelectableListDialog selectableListDialog,
        PropertyDetails propertyDetails,
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

        bindComboListCellEditor(
            bindingGroup,
            selectableListDialog,
            propertyDetails,
            converter);
    }*/
}
