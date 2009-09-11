/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.data.ui;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.jdesktop.application.Task.BlockingScope;

/**
 *
 * @author Miro
 */
public class SecureAction implements Serializable {

    public enum Show {
        Frame,
        Dialog
    }

    public static final String ATTR_NAME = "name";
    public static final String ATTR_SHOW = "show";
    //
    private String actionName;
    private String enabledProperty;
    private String selectedProperty;
    private BlockingScope block;
    private Show show;

    public SecureAction(XMLStreamReader xmlReader) throws XMLStreamException {
        readXML(xmlReader);
    }

    protected void readXML(XMLStreamReader xmlReader) throws XMLStreamException {
        Map<String, String> attributes = getAttributes(xmlReader);
        if((actionName = attributes.get(ATTR_NAME)) == null) {
            throw new NullPointerException("The attribute 'name' is required.");
        }
        if(Show.Dialog.name().equalsIgnoreCase(attributes.get(ATTR_SHOW))) {
            show = Show.Dialog;
        } else {
            show = Show.Frame;
        }
    }

    protected Map<String, String> getAttributes(XMLStreamReader xmlReader) {
        TreeMap<String, String> map = new TreeMap();
        for(int i = 0, count = xmlReader.getAttributeCount(); i < count; i++) {
            String attrName = xmlReader.getAttributeLocalName(i);
            String attrValue = xmlReader.getAttributeValue(i);
            if(attrValue != null && attrValue.length() > 0) {
                map.put(attrName, attrValue);
            }
        }

        return map;
    }

    public String getActionName() {
        return actionName;
    }

    public BlockingScope getBlock() {
        return block;
    }

    public String getEnabledProperty() {
        return enabledProperty;
    }

    public String getSelectedProperty() {
        return selectedProperty;
    }

    public Show getShow() {
        return show;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SecureAction other = (SecureAction) obj;
        if ((this.actionName == null) ? (other.actionName != null) : !this.actionName.equals(other.actionName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.actionName != null ? this.actionName.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[actionName=").append(actionName).append("]@").append(super.hashCode());

        return sb.toString();
    }
}
