/**
 * 
 */
package com.cosmos.acacia.gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

/**
 * Created	:	25.07.2008
 * @author	Petar Milev
 *
 */
public class AcaciaCellRenderer extends DefaultListCellRenderer {
    private ObjectToStringConverter toStringConverter;

    public AcaciaCellRenderer(){
        this(new AcaciaToStringConverter());
    }
    
    public AcaciaCellRenderer(ObjectToStringConverter toStringConverter){
        this.toStringConverter = toStringConverter;
    }
    
    public AcaciaCellRenderer(String elPropertyExpression){
        this(new AcaciaToStringConverter(elPropertyExpression));
    }
    
    @Override
    public Component getListCellRendererComponent(JList list,
            Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        value = toStringConverter.getPreferredStringForItem(value);
        
        return super.getListCellRendererComponent(list, value,
                index, isSelected, cellHasFocus);
    }
}
