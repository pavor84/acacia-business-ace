/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb.item;

import java.io.Serializable;
import javax.swing.Icon;

/**
 *
 * @author Miro
 */
public class OptionalItem implements Serializable, Comparable<OptionalItem> {

    private String itemName;
    private Object itemValue;
    private String text;
    private Icon icon;
    private Boolean selected;

    public OptionalItem(Class itemClass) {
        this(itemClass.getSimpleName(), itemClass);
    }

    public OptionalItem(String itemName, Object itemValue) {
        this(itemName, itemValue, null, null, false);
    }

    public OptionalItem(String itemName, Object itemValue, String text, Icon icon, Boolean selected) {
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.text = text;
        this.icon = icon;
        this.selected = selected;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Object getItemValue() {
        return itemValue;
    }

    public void setItemValue(Object itemValue) {
        this.itemValue = itemValue;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OptionalItem other = (OptionalItem) obj;
        if ((this.itemName == null) ? (other.itemName != null) : !this.itemName.equals(other.itemName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (itemName != null) {
            return itemName.hashCode();
        }

        return 0;
    }

    @Override
    public int compareTo(OptionalItem item) {
        if (itemName != null) {
            return itemName.compareTo(item.itemName);
        }

        return -1;
    }
}
