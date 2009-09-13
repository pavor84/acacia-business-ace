/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.data.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Miro
 */
public abstract class AbstractMenu extends AbstractItem implements Serializable {

    private boolean requiredAction;
    private List<AbstractMenu> menus = new ArrayList<AbstractMenu>();

    public AbstractMenu() {
        this(false);
    }

    public AbstractMenu(boolean requiredAction) {
        this.requiredAction = requiredAction;
    }

    public void add(AbstractMenu menu) {
        menus.add(menu);
    }

    public List<AbstractMenu> getMenus() {
        return menus;
    }

    public boolean isRequiredAction() {
        return requiredAction;
    }

    public void setRequiredAction(boolean requiredAction) {
        this.requiredAction = requiredAction;
    }

    public void readXML(XMLStreamReader xmlReader, Map<String, SecureAction> secureActionMap) throws XMLStreamException {
        if(this instanceof Separator) {
            return;
        }

        readXML(xmlReader);

        String elementName;
        while(xmlReader.hasNext()) {
            int parseEventId = xmlReader.next();
            switch(parseEventId) {
                case XMLStreamReader.START_ELEMENT:
                    elementName = xmlReader.getLocalName();
                    if(Menu.ELEMENT_NAME.equals(elementName)) {
                        Menu menu = new Menu();
                        menu.readXML(xmlReader, secureActionMap);
                        add(menu);
                    } else if(MenuItem.ELEMENT_NAME.equals(elementName)) {
                        MenuItem menuItem = new MenuItem();
                        menuItem.readXML(xmlReader, secureActionMap);
                        if(secureActionMap.containsKey(menuItem.getName())) {
                            add(menuItem);
                        }
                    } else if(Separator.ELEMENT_NAME.equals(elementName)) {
                        Separator separator = new Separator();
                        separator.readXML(xmlReader, secureActionMap);
                        add(separator);
                    } else if(Button.ELEMENT_NAME.equals(elementName)) {
                        Button button = new Button();
                        button.readXML(xmlReader, secureActionMap);
                        if(secureActionMap.containsKey(button.getName())) {
                            add(button);
                        }
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("; items=").append(menus);

        return sb.toString();
    }
}
