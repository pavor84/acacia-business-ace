/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.data.ui;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.SwingConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Miro
 */
public abstract class AbstractItem implements Serializable {

    public static final String ATTR_NAME = "name";
    public static final String ATTR_ORIENTATION = "orientation";
    public static final String ATTR_CLASS_NAME = "className";
    //
    public static final String ORIENTATION_HORIZONTAL = "HORIZONTAL";
    public static final String ORIENTATION_VERTICAL = "VERTICAL";
    //
    private boolean requiredAttributeName;
    private String name;

    protected AbstractItem() {
        this(true);
    }

    protected AbstractItem(boolean requiredAttributeName) {
        this.requiredAttributeName = requiredAttributeName;
    }

    public String getName() {
        return name;
    }

    public boolean isRequiredAttributeName() {
        return requiredAttributeName;
    }

    public void setRequiredAttributeName(boolean requiredAttributeName) {
        this.requiredAttributeName = requiredAttributeName;
    }

    public abstract String getElementName();

    public void readXML(XMLStreamReader xmlReader) throws XMLStreamException {
        Map<String, String> attributes = getAttributes(xmlReader);
        if(isRequiredAttributeName() && (name = attributes.get(ATTR_NAME)) == null) {
            throw new NullPointerException("The attribute 'name' is required.");
        }
    }

    protected Integer getOrientation(String orientation) {
        if(ORIENTATION_VERTICAL.equalsIgnoreCase(orientation)) {
            return SwingConstants.VERTICAL;
        } else if(ORIENTATION_HORIZONTAL.equalsIgnoreCase(orientation)) {
            return SwingConstants.HORIZONTAL;
        }

        return null;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractItem other = (AbstractItem) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[name=").append(name).append("]@").append(super.hashCode());

        return sb.toString();
    }
}
