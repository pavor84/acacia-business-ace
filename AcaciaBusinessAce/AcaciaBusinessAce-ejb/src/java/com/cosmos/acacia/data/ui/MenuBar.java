/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.data.ui;

import java.io.Serializable;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Miro
 */
public class MenuBar extends AbstractMenu implements Serializable {

    public static final String ELEMENT_NAME = "menuBar";

    public MenuBar(XMLStreamReader xmlReader, Map<String, SecureAction> secureActionMap) throws XMLStreamException {
        super(Type.MenuBar, xmlReader, secureActionMap);
    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }
}
