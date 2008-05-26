package com.cosmos.acacia.gui;

import com.cosmos.swingb.listeners.TableModificationListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;

/**
 * Listener for the case when a record is deleted from a table opened
 * by an AcaciaLookup control
 * 
 * @author Bozhidar Bozhanov
 */
public class LookupRecordDeletionListener implements TableModificationListener {

    private Object sourceObject;
    private Set<AcaciaLookup> targetLookups = new HashSet();
    private Set<AcaciaComboBox> targetCombos = new HashSet();
    
    public LookupRecordDeletionListener(Object sourceObject, AcaciaLookup targetLookup) {
        this.sourceObject = sourceObject;
        targetLookups.add(targetLookup);
    }
    
    public LookupRecordDeletionListener(Object sourceObject, AcaciaComboBox targetCombo) {
        this.sourceObject = sourceObject;
        targetCombos.add(targetCombo);
    }

    public LookupRecordDeletionListener(Object sourceObject) {
        this.sourceObject = sourceObject;
    }
    public void rowDeleted(Object row) {
        if (sourceObject.equals(row)) {
            for (AcaciaLookup targetLookup : targetLookups)
                targetLookup.clearSelectedValue();
            
            for (AcaciaComboBox targetCombo : targetCombos) {
                targetCombo.setModel(new DefaultComboBoxModel());
                targetCombo.setSelectedIndex(-1);
            }
        }
    }

    public void rowModified(Object row) {
        // Do nothing
    }

    public void rowAdded(Object row) {
        // Do nothing
    }

    public Object getSourceObject() {
        return sourceObject;
    }

    public void setSourceObject(Object sourceObject) {
        this.sourceObject = sourceObject;
    }

    public Set<AcaciaComboBox> getTargetCombos() {
        return targetCombos;
    }

    public void setTargetCombos(Set<AcaciaComboBox> targetCombos) {
        this.targetCombos = targetCombos;
    }

    public Set<AcaciaLookup> getTargetLookups() {
        return targetLookups;
    }

    public void setTargetLookups(Set<AcaciaLookup> targetLookups) {
        this.targetLookups = targetLookups;
    }

    public void addTargetCombo(AcaciaComboBox combo) {
        targetCombos.add(combo);
    }

    public void addTargetLookup(AcaciaLookup lookup) {
        targetLookups.add(lookup);
    }
}