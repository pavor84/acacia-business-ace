/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.data.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Miro
 */
public abstract class AbstractMenu implements Serializable {

    public static final String ATTR_NAME = "name";

    public enum Type {
        MenuBar,
        Menu,
        MenuItem,
        Separator
    }

    private Type type;
    private String name;
    private List<AbstractMenu> menus = new ArrayList<AbstractMenu>();

    protected AbstractMenu(Type type, XMLStreamReader xmlReader, Map<String, SecureAction> secureActionMap) throws XMLStreamException {
        this.type = type;
        readXML(xmlReader, secureActionMap);
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public abstract String getElementName();

    public void add(AbstractMenu menu) {
        menus.add(menu);
    }

    public List<AbstractMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<AbstractMenu> menus) {
        this.menus = menus;
    }

    protected void readXML(XMLStreamReader xmlReader, Map<String, SecureAction> secureActionMap) throws XMLStreamException {
        if(Type.Separator.equals(type)) {
            return;
        }

        Map<String, String> attributes = getAttributes(xmlReader);
        if((name = attributes.get(ATTR_NAME)) == null) {
            throw new NullPointerException("The attribute 'name' is required.");
        }

        String elementName;
        while(xmlReader.hasNext()) {
            int parseEventId = xmlReader.next();
            switch(parseEventId) {
                case XMLStreamReader.START_ELEMENT:
                    elementName = xmlReader.getLocalName();
                    if(Menu.ELEMENT_NAME.equals(elementName)) {
                        add(new Menu(xmlReader, secureActionMap));
                    } else if(MenuItem.ELEMENT_NAME.equals(elementName)) {
                        MenuItem menuItem = new MenuItem(xmlReader, secureActionMap);
                        if(secureActionMap.containsKey(menuItem.getName())) {
                            add(menuItem);
                        }
                    } else if(Separator.ELEMENT_NAME.equals(elementName)) {
                        add(new Separator(xmlReader, secureActionMap));
                    }
                    break;

                case XMLStreamReader.END_ELEMENT:
                    elementName = xmlReader.getLocalName();
                    if(getElementName().equals(elementName)) {
                        return;
                    }
                    break;

                default:
            }
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractMenu other = (AbstractMenu) obj;
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
        sb.append("; type=").append(type);
        sb.append(", items=").append(menus);

        return sb.toString();
    }
}
