/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTable;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author miro
 */
public abstract class AcaciaPanel
    extends JBPanel
{

    private DataObject parentDataObject;


    AcaciaPanel() {
        super(AcaciaApplication.class);
    }
 
    public AcaciaPanel(DataObject parentDataObject) {
        this();
        this.parentDataObject = parentDataObject;
    }

    public DataObject getParentDataObject() {
        return parentDataObject;
    }

    public void setParentDataObject(DataObject parentDataObject) {
        this.parentDataObject = parentDataObject;
    }

    protected abstract void initData();
    
    protected void setFonts()
    {
        Component[] components = this.getComponents();
        // Get a font from config?
        Font font = new Font("Tahoma", Font.PLAIN, 11);
        setFontToComponents(components, font);
    }

    protected void setFontToComponents(Component[] components, Font font)
    {
        for (Component component : components)
        {
            if (component instanceof Container)
            {
                setFontToComponents(((Container) component).getComponents(), font);
            }
            if (component instanceof JBTable)
            {
                ((JBTable) component).getTableHeader().setFont(font);
            }
            if (component instanceof JBPanel)
            {
                Border border = ((JBPanel) component).getBorder();
                if (border instanceof TitledBorder)
                {
                    ((TitledBorder) border).setTitleFont(font);
                }
            }
            component.setFont(font);
        }
    }
}