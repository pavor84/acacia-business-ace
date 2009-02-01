/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.awt.BorderLayout;
import java.text.Format;
import javax.swing.BorderFactory;

/**
 *
 * @author Miro
 */
public class LabeledComboBox extends JBPanel {

    private JBLabel label;
    private JBComboBox comboBox;

    public LabeledComboBox() {
        this("Label:", new String[] {"Item 1", "Item 2", "Item 3", "Item 4"});
    }

    public LabeledComboBox(String labelText, Object[] items) {
        super(new BorderLayout());
        if(labelText != null)
            label = new JBLabel(labelText);
        else
            label = new JBLabel();

        if(items != null)
            comboBox = new JBComboBox(items);
        else
            comboBox = new JBComboBox();

        label.setName("label");
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        add(label, BorderLayout.WEST);

        comboBox.setName("textField");
        add(comboBox, BorderLayout.CENTER);
    }

    public JBLabel getLabel() {
        return label;
    }

    public JBComboBox getComboBox() {
        return comboBox;
    }

    public String getLabelText() {
        return label.getText();
    }
    
    public void setLabelText(String labelText) {
        label.setText(labelText);
    }

}
