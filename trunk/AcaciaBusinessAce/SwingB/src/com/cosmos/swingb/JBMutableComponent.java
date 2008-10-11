/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author Miro
 */
public class JBMutableComponent
    extends JXPanel
{
    private JComponent mutableComponent;

    public JBMutableComponent()
    {
        super(new BorderLayout());
        setName("JBMutableComponent"); // NOI18N
        initComponents();
    }

    private void initComponents()
    {
        add(new JBFormattedTextField(), BorderLayout.CENTER);
    }

    public JComponent getMutableComponent()
    {
        return mutableComponent;
    }

    public void setMutableComponent(JComponent mutableComponent)
    {
        if(this.mutableComponent != null)
            super.remove(this.mutableComponent);

        this.mutableComponent = mutableComponent;
        if(mutableComponent != null)
            add(mutableComponent, BorderLayout.CENTER);
    }

}
