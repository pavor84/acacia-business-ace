/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.JDialog;

/**
 *
 * @author Miro
 */
public class JBDialog
    //extends JXDialog
    extends JDialog
{
    public JBDialog()
    {
        super();
        init();
    }

    public JBDialog(Frame owner, String title)
    {
        super(owner);
        setTitle(title);
        init();
    }

    public JBDialog(Dialog owner, String title)
    {
        super(owner);
        setTitle(title);
        init();
    }

    public JBDialog(Window owner, String title)
    {
        super(owner);
        setTitle(title);
        init();
    }

    protected void init()
    {
    }
}
