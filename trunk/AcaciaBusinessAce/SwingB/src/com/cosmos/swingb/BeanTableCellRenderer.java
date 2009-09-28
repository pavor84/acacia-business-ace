/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.resource.BeanResource;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatter;
import org.jdesktop.application.Application;

/**
 *
 * @author Miro
 */
public class BeanTableCellRenderer
    extends DefaultTableCellRenderer
{
    private EntityProperty propertyDetails;
    private BeanResource beanResource;
    private DefaultFormatter formatter;

    public BeanTableCellRenderer(EntityProperty propertyDetails, Application application)
    {
        this.propertyDetails = propertyDetails;
        beanResource = new BeanResource(application);
        if(propertyDetails != null) {
            formatter = propertyDetails.getFormatter();
            if(formatter == null) {
                if(propertyDetails.isPercent()) {
                    JBPercentField percentField = new JBPercentField();
                    formatter = percentField.getNumberFormatter();
                    setHorizontalAlignment(TRAILING);
                }
            }
        }
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column)
    {
        if(formatter != null) {
            try {
                value = formatter.valueToString(value);
            } catch(Exception ex) {
                value = beanResource.getShortNameValue(value);
            }
        } else {
            value = beanResource.getShortNameValue(value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
