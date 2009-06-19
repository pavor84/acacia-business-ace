/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MaskTextFieldFrame.java
 *
 * Created on 2009-2-11, 8:13:02
 */

package org.jdesktop.swingx.text.test;

import org.jdesktop.swingx.JXMaskTextField;

/**
 *
 * @author miroslav
 */
public class MaskTextFieldFrame extends javax.swing.JFrame {

    /** Creates new form MaskTextFieldFrame */
    public MaskTextFieldFrame() {
        initComponents();
        initData();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        maskComboBox = new javax.swing.JComboBox();
        maskLabel = new javax.swing.JLabel();
        textField1 = new JXMaskTextField();
        textField2 = new javax.swing.JFormattedTextField();
        field1Label = new javax.swing.JLabel();
        field2Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        maskComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(+###-###) ###-##-##", "ULLLLLLLLLLLLLLL", "***-***-****", "AAAAA-AAAAA" }));
        maskComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                maskComboBoxItemStateChanged(evt);
            }
        });

        maskLabel.setText("Mask:");

        field1Label.setText("Field 1:");

        field2Label.setText("Field 2:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(field1Label)
                    .addComponent(field2Label)
                    .addComponent(maskLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(maskComboBox, 0, 168, Short.MAX_VALUE)
                    .addComponent(textField2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maskComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maskLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field1Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field2Label))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void maskComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_maskComboBoxItemStateChanged
        // TODO add your handling code here:
        onMaskChanged();
    }//GEN-LAST:event_maskComboBoxItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MaskTextFieldFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel field1Label;
    private javax.swing.JLabel field2Label;
    private javax.swing.JComboBox maskComboBox;
    private javax.swing.JLabel maskLabel;
    private javax.swing.JFormattedTextField textField1;
    private javax.swing.JFormattedTextField textField2;
    // End of variables declaration//GEN-END:variables


    private void initData() {
        String mask = (String)maskComboBox.getSelectedItem();
        try {
            ((JXMaskTextField)textField1).setMask(mask);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void onMaskChanged() {
        String mask = (String)maskComboBox.getSelectedItem();
        try {
            ((JXMaskTextField)textField1).setMask(mask);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }


}