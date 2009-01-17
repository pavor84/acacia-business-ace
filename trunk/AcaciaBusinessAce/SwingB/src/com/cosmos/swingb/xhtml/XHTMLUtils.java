/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.xhtml;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import org.xhtmlrenderer.simple.FSScrollPane;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.simple.extend.XhtmlNamespaceHandler;

/**
 *
 * @author Miro
 */
public class XHTMLUtils {

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 150;

    public static XHTMLPanel getHTMLPanel(String xhtmlSource) {
        XHTMLPanel panel = new XHTMLPanel();
        XhtmlNamespaceHandler handler = new XhtmlNamespaceHandler();
        panel.setDocumentFromString(xhtmlSource, "", handler);
        Color backgroundColor;
        backgroundColor = UIManager.getColor("Panel.background");
        panel.setBackground(backgroundColor);

        return panel;
    }

    public static FSScrollPane getXHTMLScrollPane(
            String xhtmlSource,
            int width, int height) {
        return getXHTMLScrollPane(getHTMLPanel(xhtmlSource), width, height);
    }

    public static FSScrollPane getXHTMLScrollPane(String xhtmlSource) {
        return getXHTMLScrollPane(getHTMLPanel(xhtmlSource));
    }

    public static FSScrollPane getXHTMLScrollPane(XHTMLPanel panel) {
        return getXHTMLScrollPane(panel, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static FSScrollPane getXHTMLScrollPane(
            XHTMLPanel panel,
            int width, int height) {
        Dimension size = new Dimension(width, height);
        FSScrollPane scrollPane = new FSScrollPane(panel);
        scrollPane.setMinimumSize(size);
        scrollPane.setPreferredSize(size);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        return scrollPane;
    }
}
