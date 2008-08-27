/*
 * ProductAssemblerPanel.java
 *
 * Created on Вторник, 2008, Август 19, 19:19
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.gui.assembling.AssemblingSchemaChoicePanel.Mode;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.swingb.DialogResponse;
import java.awt.Component;

/**
 *
 * @author  Miro
 */
public class ProductAssemblerPanel
    extends AcaciaPanel
{

    /** Creates new form ProductAssemblerPanel */
    public ProductAssemblerPanel() {
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
    }

    @Override
    public DialogResponse showDialog(Component parentComponent)
    {
        //return super.showDialog(parentComponent);
        return productAssemble(parentComponent, true);
    }

    @Override
    public void showFrame(Component parentComponent)
    {
        //super.showFrame(parentComponent);
        productAssemble(parentComponent, false);
    }

    private DialogResponse productAssemble(Component parentComponent, boolean dialog)
    {
        AssemblingSchemaChoicePanel choicePanel = new AssemblingSchemaChoicePanel();
        choicePanel.setMode(Mode.AssembleSchemaSelect);
        AbstractTablePanel tablePanel = choicePanel.getTablePanel();
        AssemblingSchema assemblingSchema = (AssemblingSchema)getSelectedValue();
        tablePanel.setSelectedRowObject(assemblingSchema);
        DialogResponse response = tablePanel.showDialog(parentComponent);
        if(!DialogResponse.SELECT.equals(response))
        {
            return response;
        }

        assemblingSchema = (AssemblingSchema)tablePanel.getSelectedRowObject();
        AssemblingParametersPanel apPanel = new AssemblingParametersPanel(assemblingSchema);
        if(dialog)
        {
            response = apPanel.showDialog(parentComponent);
            setSelectedValue(apPanel.getSelectedValue());
            return response;
        }

        apPanel.showFrame(parentComponent);
        return DialogResponse.CLOSE;
    }


}
