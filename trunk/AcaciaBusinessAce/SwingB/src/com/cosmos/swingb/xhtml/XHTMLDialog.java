/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.xhtml;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import org.xhtmlrenderer.simple.FSScrollPane;

/**
 *
 * @author Miro
 */
public class XHTMLDialog {

    public static int showQuestionMessage(
            Component parentComponent,
            String xhtmlMessage,
            String title,
            int optionType,
            Icon icon,
            Object[] options,
            Object initialValue) {
        FSScrollPane scrollPane = XHTMLUtils.getXHTMLScrollPane(xhtmlMessage);
        return showQuestionMessage(
                parentComponent,
                scrollPane, title, optionType, icon, options, initialValue);
    }

    public static int showQuestionMessage(
            Component parentComponent,
            String xhtmlMessage,
            int width, int height,
            String title,
            int optionType,
            Icon icon,
            Object[] options,
            Object initialValue) {
        FSScrollPane scrollPane = XHTMLUtils.getXHTMLScrollPane(xhtmlMessage, width, height);
        return showQuestionMessage(
                parentComponent,
                scrollPane, title, optionType, icon, options, initialValue);
    }

    public static int showQuestionMessage(
            Component parentComponent,
            FSScrollPane scrollPane,
            String title,
            int optionType,
            Icon icon,
            Object[] options,
            Object initialValue) {
        return JOptionPane.showOptionDialog(
                parentComponent,
                scrollPane,
                title,
                optionType,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                options,
                options[0]);
    }
}
