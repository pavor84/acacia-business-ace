/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import javax.swing.text.JTextComponent;

/**
 *
 * @author Miro
 */
public class TextEvent {

    private JTextComponent source;

    public TextEvent(JTextComponent source) {
        this.source = source;
    }

    public JTextComponent getSource() {
        return source;
    }

    public String getText() {
        return source.getText();
    }
}
