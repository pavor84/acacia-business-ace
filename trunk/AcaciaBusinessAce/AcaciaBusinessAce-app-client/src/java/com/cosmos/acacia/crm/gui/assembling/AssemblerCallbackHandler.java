/*
 * AssemblerCallbackHandler.java
 *
 * Created on Неделя, 2008, Септември 14, 23:13
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.gui.AcaciaPanel;
import java.io.IOException;
import java.util.Arrays;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 *
 * @author  Miro
 */
public class AssemblerCallbackHandler
    extends AcaciaPanel
    implements CallbackHandler
{

    /** Creates new form AssemblerCallbackHandler */
    public AssemblerCallbackHandler() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


    @Override
    protected void initData()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void handle(Callback[] callbacks)
        throws IOException,
        UnsupportedCallbackException
    {
        System.out.println("AssemblerCallbackHandler.handle(" + Arrays.asList(callbacks) + ")");
        throw new UnsupportedOperationException("Not supported yet.");
    }


}