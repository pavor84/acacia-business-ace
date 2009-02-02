/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import java.text.DateFormat;
import java.util.Date;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.JBTable;
import com.cosmos.swingb.SelectableListDialog;

/**
 *
 * @author miro
 */
public class AcaciaTable
    extends JBTable
{
    
    private DateFormat defaultFormat = AcaciaUtils.getShortDateFormat();
    
    public AcaciaTable()
    {
        super();
    }

    public void bindComboListCellEditor(
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
    }

    public DateFormat getDefaultFormat() {
        return defaultFormat;
    }
    
    public ColumnBinding createColumnBinding(
                                             JTableBinding tableBinding,
                                             PropertyDetails propertyDetails){
        ColumnBinding result = super.createColumnBinding(tableBinding, propertyDetails);
        //if the column type is java.util.Date and there is no custom display expression - set up format converter
        if ( result.getConverter()==null && result.getColumnClass()!=null && result.getColumnClass().isAssignableFrom(java.util.Date.class) && propertyDetails.getCustomDisplay()==null ){
            result.setConverter(new Converter() {
                @Override
                public Object convertReverse(Object value) {
                    return null;//no reverse conversion
                }
            
                @Override
                public Object convertForward(Object value) {
                    if (value==null)
                        return "";
                    else if ( value instanceof Date )
                        return getDefaultFormat().format((Date)value);
                    else
                        return value.toString();
                }
            });
        }
        return result;
    }

    
}
