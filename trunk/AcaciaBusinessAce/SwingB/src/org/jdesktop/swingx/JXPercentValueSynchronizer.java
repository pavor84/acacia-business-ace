/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.swingx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Miro
 */
public class JXPercentValueSynchronizer {

    private static final String PROPERTY_NAME_VALUE = "value";
    private PercentChangeListener percentChangeListener;
    private DecimalChangeListener decimalChangeListener;
    private JXPercentField percentField;
    private JXDecimalField decimalField;
    private boolean syncing;

    public JXPercentValueSynchronizer(JXPercentField percentField, JXDecimalField decimalField) {
        this.percentField = percentField;
        this.decimalField = decimalField;
    }

    public JXDecimalField getDecimalField() {
        return decimalField;
    }

    public JXPercentField getPercentField() {
        return percentField;
    }

    public void bind() {
        percentChangeListener = new PercentChangeListener();
        percentField.getNumericField().
                addPropertyChangeListener(PROPERTY_NAME_VALUE, percentChangeListener);
        decimalChangeListener = new DecimalChangeListener();
        decimalField.getNumericField().
                addPropertyChangeListener(PROPERTY_NAME_VALUE, decimalChangeListener);
        syncing = false;
    }

    public void unbind() {
        if (decimalChangeListener != null) {
            decimalField.getNumericField().
                    removePropertyChangeListener(PROPERTY_NAME_VALUE, decimalChangeListener);
            decimalChangeListener = null;
        }
        if (percentChangeListener != null) {
            percentField.getNumericField().
                    removePropertyChangeListener(PROPERTY_NAME_VALUE, percentChangeListener);
            percentChangeListener = null;
        }
        syncing = false;
    }

    protected void percentChanged(Object newValue, Object oldValue) {
        if (syncing || newValue == oldValue ||
                newValue != null && newValue.equals(oldValue) ||
                oldValue != null && oldValue.equals(newValue)) {
            return;
        }

        syncing = true;
        try {
            setDecimalValue(null);
        } finally {
            syncing = false;
        }
    }

    protected void decimalChanged(Object newValue, Object oldValue) {
        if (syncing || newValue == oldValue ||
                newValue != null && newValue.equals(oldValue) ||
                oldValue != null && oldValue.equals(newValue)) {
            return;
        }

        syncing = true;
        try {
            setPercentValue(null);
        } finally {
            syncing = false;
        }
    }

    protected void setPercentValue(BigDecimal value) {
        percentField.setValue(value);
    }

    protected void setDecimalValue(BigDecimal value) {
        decimalField.setValue(value);
    }

    private class PercentChangeListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent event) {
            if (PROPERTY_NAME_VALUE.equals(event.getPropertyName())) {
                percentChanged(event.getNewValue(), event.getOldValue());
            }
        }
    }

    private class DecimalChangeListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent event) {
            if (PROPERTY_NAME_VALUE.equals(event.getPropertyName())) {
                decimalChanged(event.getNewValue(), event.getOldValue());
            }
        }
    }
}
