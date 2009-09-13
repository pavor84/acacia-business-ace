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
public class EntityAction extends SecureAction implements Serializable {

    public static final String ELEMENT_NAME = "entityAction";
    public static final String ENTITY_ELEMENT_NAME = "entity";
    //
    private Class entityClass;

    public EntityAction() {
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
                    if(ENTITY_ELEMENT_NAME.equals(elementName)) {
                        attributes = getAttributes(xmlReader);
                        String className;
                        if((className = attributes.get(ATTR_CLASS_NAME)) == null) {
                            throw new NullPointerException("The attribute 'className' is required.");
                        }
                        try {
                            entityClass = Class.forName(className);
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
                    /*
                    if(xmlReader.hasName()) {
                        System.out.println("xmlReader.getName(): " + xmlReader.getName());
                    }
                    if(xmlReader.hasText()) {
                        System.out.println("xmlReader.getText(): " + xmlReader.getText());
                    }
                    */
            }
        }
    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; entityClass=").append(entityClass);

        return sb.toString();
    }
}

