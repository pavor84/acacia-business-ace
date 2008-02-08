/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigInteger;
import javax.persistence.Transient;

/**
 *
 * @author Miro
 */
public abstract class DataObjectBean {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public abstract DataObject getDataObject();
    public abstract void setDataObject(DataObject dataObject);

    public abstract BigInteger getId();
    public abstract void setId(BigInteger id);
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if(oldValue == newValue || (oldValue == null && newValue == null))
            return;

        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, int oldValue, int newValue) {
	if (oldValue == newValue)
	    return;

        firePropertyChange(propertyName, new Integer(oldValue), new Integer(newValue));
    }

    protected void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
	if (oldValue == newValue)
	    return;

        firePropertyChange(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
    }


}
