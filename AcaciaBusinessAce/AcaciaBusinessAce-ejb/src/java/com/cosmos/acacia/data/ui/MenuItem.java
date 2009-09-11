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
public class MenuItem extends AbstractMenu implements Serializable {

    public static final String ELEMENT_NAME = "menuItem";

    public MenuItem(XMLStreamReader xmlReader, Map<String, SecureAction> secureActionMap) throws XMLStreamException {
        super(Type.MenuItem, xmlReader, secureActionMap);
    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }
}
