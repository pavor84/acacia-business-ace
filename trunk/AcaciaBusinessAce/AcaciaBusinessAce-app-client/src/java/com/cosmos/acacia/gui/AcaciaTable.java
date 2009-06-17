/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui;

import java.util.Date;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.FormattedCellRenderer;
import com.cosmos.swingb.JBTable;
import com.cosmos.swingb.SelectableListDialog;
import java.text.Format;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 *
 * @author miro
 */
public class AcaciaTable
        extends JBTable {

    private Format defaultFormat = AcaciaUtils.getShortDateFormat();

    public AcaciaTable() {
        super();
    }

    public void bindComboListCellEditor(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            PropertyDetails propertyDetails) {
        bindComboListCellEditor(bindingGroup, selectableListDialog, propertyDetails, (String) null);
    }

    public void bindComboListCellEditor(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            PropertyDetails propertyDetails,
            String elPropertyItemDisplay) {
        ObjectToStringConverter converter;
        if (elPropertyItemDisplay != null) {
            converter = new AcaciaToStringConverter(elPropertyItemDisplay);
        } else {
            converter = new AcaciaToStringConverter();
        }

        bindComboListCellEditor(
                bindingGroup,
                selectableListDialog,
                propertyDetails,
                converter);
    }

    public Format getDefaultFormat() {
        return defaultFormat;
    }

    public ColumnBinding createColumnBinding(
            JTableBinding tableBinding,
            PropertyDetails propertyDetails) {
        ColumnBinding result = super.createColumnBinding(tableBinding, propertyDetails);

        //if the column type is java.util.Date and there is no custom display expression - set up format converter
        if (result.getConverter() == null && result.getColumnClass() != null && result.getColumnClass().isAssignableFrom(java.util.Date.class) && propertyDetails.getCustomDisplay() == null) {
            result.setConverter(new TableCellConverter(propertyDetails));
        }

        return result;
    }

    protected class TableCellConverter extends Converter {

        private PropertyDetails propertyDetails;

        public TableCellConverter(PropertyDetails propertyDetails) {
            this.propertyDetails = propertyDetails;
        }

        @Override
        public Object convertForward(Object value) {
            if (value == null) {
                return "";
            }

            if(getColumn(propertyDetails).getCellRenderer() != null) {
                return value;
            }

            if (value instanceof Date) {
                return getDefaultFormat().format((Date) value);
            }

            return String.valueOf(value);
        }

        @Override
        public Object convertReverse(Object arg0) {
            return null;//no reverse conversion
        }
    }
}
