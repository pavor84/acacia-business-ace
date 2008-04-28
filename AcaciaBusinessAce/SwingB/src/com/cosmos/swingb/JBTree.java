/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

/**
 *
 * @author Miro
 */
public class JBTree
    extends JXTree
{
    private ObjectToStringConverter toStringConverter;

    /**
     * Getter for toStringConverter
     * @return ObjectToStringConverter
     */
    public ObjectToStringConverter getToStringConverter() {
        return toStringConverter;
    }

    /**
     * Setter for toStringConverter
     * @param toStringConverter - ObjectToStringConverter
     */
    public void setToStringConverter(ObjectToStringConverter toStringConverter) {
        this.toStringConverter = toStringConverter;
    }
    
    /**
     * Sets {@link ToStringCellRenderer} as cell renderer
     * to be used when rendering the elements.
     */
    public void enableToStringCellRenderer(){
        if ( getToStringConverter()==null ){
            throw new IllegalStateException("ObjectToString converter must be set!");
        }
        setCellRenderer(new ToStringCellRenderer(getToStringConverter()));
    }
    
    /**
     * 
     * Created	:	22.04.2008
     * @author	Petar Milev
     * @version $Id: $
     * 
     * Uses {@link ObjectToStringConverter} to render the string value 
     * for the given element objects
     *
     */
    public static class ToStringCellRenderer extends DefaultTreeCellRenderer{
        private ObjectToStringConverter converter = null;
        
        public ToStringCellRenderer(ObjectToStringConverter converter){
            this.converter = converter;
        }
        
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                      boolean expanded, boolean leaf, int row,
                                                      boolean hasFocus) {
            
            Component result = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            
            //modify the text if the value is recognizable for the converter
            if ( value instanceof DefaultMutableTreeNode ){
                DefaultMutableTreeNode nodeValue = (DefaultMutableTreeNode) value;
                Object userObject = nodeValue.getUserObject();
                
                //don't do anything if the value is string
                if ( userObject instanceof String ){
                //otherwise convert the string
                }else{
                    this.setText(converter.getPreferredStringForItem(nodeValue.getUserObject()));
                }
            }
            
            return result;
        }
    }
}
