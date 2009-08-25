/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.util;

import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author Miro
 */
public class CosmosUtils {

    public static Dimension sameSize(JButton button1, JButton button2) {
        Dimension size = sameSize(button1.getPreferredSize(), button2.getPreferredSize());
        button1.setPreferredSize(size);
        button2.setPreferredSize(size);

        return size;
    }

    public static Dimension sameSize(Dimension size1, Dimension size2) {
        Dimension size = new Dimension();
        size.width = Math.max(size1.width, size2.width);
        size.height = Math.max(size1.height, size2.height);

        return size;
    }
}
