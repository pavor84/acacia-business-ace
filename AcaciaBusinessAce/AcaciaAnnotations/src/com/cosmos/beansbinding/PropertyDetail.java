/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding;

import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class PropertyDetail implements Serializable {

    private String getter;
    private String setter;

    public PropertyDetail(String getter, String setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public PropertyDetail(String getter) {
        this(getter, getter);
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getSetter() {
        if(setter != null)
            return setter;

        return getter;
    }

    public void setSetter(String setter) {
        this.setter = setter;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PropertyDetail[");
        sb.append("getter=").append(getter);
        sb.append(", setter=").append(setter);
        sb.append("]@").append(super.hashCode());

        return sb.toString();
    }
}
