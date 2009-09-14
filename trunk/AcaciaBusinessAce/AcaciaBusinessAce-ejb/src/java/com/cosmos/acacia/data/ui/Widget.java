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
public abstract class Widget extends AbstractItem implements Serializable {

    private boolean requiredAction;
    private boolean childrenAllow;
    private List<Widget> widgets = new ArrayList<Widget>();
    private String constraints;

    public Widget() {
        this(false, false);
    }

    public Widget(boolean requiredAction, boolean childrenAllow) {
        this.requiredAction = requiredAction;
        this.childrenAllow = childrenAllow;
    }

    public void add(Widget menu) {
        widgets.add(menu);
    }

    public List<Widget> getWidgets() {
        return widgets;
    }

    public boolean isRequiredAction() {
        return requiredAction;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setRequiredAction(boolean requiredAction) {
        this.requiredAction = requiredAction;
    }

    public boolean isChildrenAllow() {
        return childrenAllow;
    }

    public void setChildrenAllow(boolean childrenAllow) {
        this.childrenAllow = childrenAllow;
    }

    public void readXML(XMLStreamReader xmlReader, Map<String, SecureAction> secureActionMap) throws XMLStreamException {
        Map<String, String> attributes = getAttributes(xmlReader);
        constraints = attributes.get(ATTR_CONSTRAINTS);

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
                    } else if(Label.ELEMENT_NAME.equals(elementName)) {
                        Label label = new Label();
                        label.readXML(xmlReader, secureActionMap);
                        add(label);
                    } else if(ProgressBar.ELEMENT_NAME.equals(elementName)) {
                        ProgressBar progressBar = new ProgressBar();
                        progressBar.readXML(xmlReader, secureActionMap);
                        add(progressBar);
                    } else {
                        throw new UnsupportedOperationException("Unknown elementName: " + elementName);
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
        sb.append("; widgets=").append(widgets);

        return sb.toString();
    }
}
