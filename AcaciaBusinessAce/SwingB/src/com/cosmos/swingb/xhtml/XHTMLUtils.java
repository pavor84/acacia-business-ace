/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.xhtml;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
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

    public static final String XHTML_DOCUMENT_PREFIX =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<!--<!DOCTYPE html" +
            "    PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"" +
            "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">-->";

    public static final String XHTML_TAG_BEGIN =
            "<xhtml xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">";

    public static final String XHTML_TAG_END =
                "</xhtml>";

    private static final int EXTRA_LENGTH =
            XHTML_DOCUMENT_PREFIX.length() +
            XHTML_TAG_BEGIN.length() +
            XHTML_TAG_END.length();

    public static String toString(String firstMessageRow, String... nextMessageRows) {
        StringBuilder sb = new StringBuilder();
        sb.append(firstMessageRow);
        for(String row : nextMessageRows) {
            sb.append(row);
        }

        return sb.toString();
    }

    public static String toString(List<String> messageRows) {
        StringBuilder sb = new StringBuilder();
        for(String messageRow : messageRows) {
            sb.append(messageRow);
        }

        return sb.toString();
    }

    public static XHTMLPanel getHTMLPanel(String firstMessageRow, String... nextMessageRows) {
        return getHTMLPanel(toString(firstMessageRow, nextMessageRows));
    }

    public static XHTMLPanel getHTMLPanel(List<String> messageRows) {
        return getHTMLPanel(toString(messageRows));
    }

    public static XHTMLPanel getHTMLPanel(String message) {
        XHTMLPanel panel = new XHTMLPanel();
        XhtmlNamespaceHandler handler = new XhtmlNamespaceHandler();
        panel.setDocumentFromString(getFormattedXHTML(message), "", handler);
//        Color backgroundColor;
//        backgroundColor = UIManager.getColor("Panel.background");
//        int red = backgroundColor.getRed();
//        int green = backgroundColor.getGreen();
//        int blue = backgroundColor.getBlue();
//        backgroundColor = new Color(red, green, blue, 0);
//        backgroundColor = new Color(255, 255, 255, 0);
//        panel.setBackground(backgroundColor);
        panel.setBackground(new Color(255, 255, 255, 0));

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

    public static String getFormattedXHTML(String xhtmlBody) {
        String body = xhtmlBody.trim();
        if(body.endsWith("</xhtml>") || body.endsWith("</html>")) {
            return body;
        }

        StringBuilder sb = new StringBuilder(body.length() + EXTRA_LENGTH);
        sb.append(XHTML_DOCUMENT_PREFIX).append(XHTML_TAG_BEGIN);
        sb.append(body);
        sb.append(XHTML_TAG_END);

        return sb.toString();
    }
}
