/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.data.ui;

import java.io.Serializable;
import java.util.Map;
import javax.swing.SwingConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Miro
 */
public class Separator extends Widget implements Serializable {

    public static final String ELEMENT_NAME = "separator";
    //
    private int orientation;

    public Separator() {
        setRequiredAttributeName(false);
    }

    @Override
    public void readXML(XMLStreamReader xmlReader, Map<String, SecureAction> secureActionMap) throws XMLStreamException {
        super.readXML(xmlReader, secureActionMap);

        Integer value;
        if((value = getOrientation(getAttributes(xmlReader).get(ATTR_ORIENTATION))) != null) {
            orientation = value;
        } else {
            orientation = SwingConstants.HORIZONTAL;
        }
    }

    public int getOrientation() {
        return orientation;
    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }
}
