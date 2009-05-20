/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Miro
 */
public class PropertyDetail implements Serializable, Comparator<PropertyDetail>, Comparable<PropertyDetail> {

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

    public int compare(PropertyDetail pd1, PropertyDetail pd2) {
        int result;
        if((result = pd1.getGetter().compareTo(pd2.getGetter())) != 0) {
            return result;
        }

        return pd1.getSetter().compareTo(pd2.getSetter());
    }

    public int compareTo(PropertyDetail pd) {
        return compare(this, pd);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropertyDetail other = (PropertyDetail) obj;
        if ((this.getter == null) ? (other.getter != null) : !this.getter.equals(other.getter)) {
            return false;
        }
        if ((this.setter == null) ? (other.setter != null) : !this.setter.equals(other.setter)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.getter != null ? this.getter.hashCode() : 0);
        hash = 97 * hash + (this.setter != null ? this.setter.hashCode() : 0);
        return hash;
    }

}
