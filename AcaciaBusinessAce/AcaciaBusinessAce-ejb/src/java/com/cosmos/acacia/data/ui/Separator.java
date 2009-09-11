/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.data.ui;

import java.util.Map;
import javax.swing.SwingConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Miro
 */
public class Separator extends AbstractMenu {

    public static final String ELEMENT_NAME = "separator";
    //
    public static final String ATTR_ORIENTATION = "orientation";
    //
    public static final String ORIENTATION_HORIZONTAL = "HORIZONTAL";
    public static final String ORIENTATION_VERTICAL = "VERTICAL";
    //
    private int orientation;

    public Separator(XMLStreamReader xmlReader, Map<String, SecureAction> secureActionMap) throws XMLStreamException {
        super(Type.Separator, xmlReader, secureActionMap);
    }

    @Override
    protected void readXML(XMLStreamReader xmlReader, Map<String, SecureAction> secureActionMap) throws XMLStreamException {
        super.readXML(xmlReader, secureActionMap);

        if(ORIENTATION_VERTICAL.equals(getAttributes(xmlReader).get(ATTR_ORIENTATION))) {
            orientation = SwingConstants.VERTICAL;
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
