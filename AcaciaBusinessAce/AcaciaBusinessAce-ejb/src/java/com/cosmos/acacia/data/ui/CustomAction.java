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
public class CustomAction extends SecureAction implements Serializable {

    public static final String ELEMENT_NAME = "customAction";
    public static final String FORM_ELEMENT_NAME = "form";
    //
    private Class formClass;

    public CustomAction() {
        super();
    }

    @Override
    public void readXML(XMLStreamReader xmlReader) throws XMLStreamException {
        super.readXML(xmlReader);

        String elementName;
        Map<String, String> attributes;
        while(xmlReader.hasNext()) {
            int parseEventId = xmlReader.next();
            switch(parseEventId) {
                case XMLStreamReader.START_ELEMENT:
                    elementName = xmlReader.getLocalName();
                    if(FORM_ELEMENT_NAME.equals(elementName)) {
                        attributes = getAttributes(xmlReader);
                        String className;
                        if((className = attributes.get(ATTR_CLASS_NAME)) == null) {
                            throw new NullPointerException("The attribute 'className' is required.");
                        }
                        try {
                            formClass = Class.forName(className);
                        } catch(Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    break;

                case XMLStreamReader.END_ELEMENT:
                    elementName = xmlReader.getLocalName();
                    if(ELEMENT_NAME.equals(elementName)) {
                        return;
                    }
                    break;

                default:
            }
        }
    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }

    public Class getFormClass() {
        return formClass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; formClass=").append(formClass);

        return sb.toString();
    }
}

