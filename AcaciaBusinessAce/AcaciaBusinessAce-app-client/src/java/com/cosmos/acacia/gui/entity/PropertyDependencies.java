/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Miro
 */
public class PropertyDependencies implements Serializable, Comparable<PropertyDependencies>, Comparator<PropertyDependencies> {

    private String propertyName;
    private Set<String> dependencies;

    public PropertyDependencies(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Set<String> getDependencies() {
        if(dependencies == null) {
            dependencies = new TreeSet<String>();
        }

        return dependencies;
    }

    public boolean addDependency(String propertyName) {
        return getDependencies().add(propertyName);
    }

    public boolean removeDependency(String propertyName) {
        return getDependencies().remove(propertyName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropertyDependencies other = (PropertyDependencies) obj;
        if ((this.propertyName == null) ? (other.propertyName != null) : !this.propertyName.equals(other.propertyName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.propertyName != null ? this.propertyName.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(PropertyDependencies pd) {
        return compare(this, pd);
    }

    @Override
    public int compare(PropertyDependencies pd1, PropertyDependencies pd2) {
        return pd1.propertyName.compareTo(pd2.propertyName);
    }
}
