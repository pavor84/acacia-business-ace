/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.callback.assembling;

import com.cosmos.acacia.crm.assembling.ConstraintRow;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.security.auth.callback.Callback;

/**
 *
 * @author Miro
 */
public class ChoiceCallback
    implements Callback, Serializable
{
    private AssemblingSchemaItem assemblingSchemaItem;
    private Object valueAgainstConstraints;
    private List<ConstraintRow> choices;
    private int defaultChoice;

    private List<ConstraintRow> selectedRows;

    public ChoiceCallback(
            AssemblingSchemaItem assemblingSchemaItem,
            Object valueAgainstConstraints,
            List<ConstraintRow> choices,
            int defaultChoice)
    {
        if(assemblingSchemaItem == null)
            throw new IllegalArgumentException("The Assembling Schema Item can not be null.");
        this.assemblingSchemaItem = assemblingSchemaItem;

        if(choices == null || choices.size() == 0)
            throw new IllegalArgumentException("The choices can not be null or zero length: " + choices);
        this.choices = choices;

        int size;
        if(defaultChoice >= (size = choices.size()))
            throw new IllegalArgumentException("The defaultChoice index (" + defaultChoice + ") can not be great than choices size (" + size + ").");
        this.defaultChoice = defaultChoice;

        this.valueAgainstConstraints = valueAgainstConstraints;
    }

    public List<ConstraintRow> getChoices() {
        return choices;
    }

    public int getDefaultChoice() {
        return defaultChoice;
    }

    public AssemblingSchemaItem getAssemblingSchemaItem() {
        return assemblingSchemaItem;
    }

    public Object getValueAgainstConstraints() {
        return valueAgainstConstraints;
    }

    public List<ConstraintRow> getSelectedRows()
    {
        if(selectedRows == null)
            return Collections.emptyList();

        return selectedRows;
    }

    public void setSelectedRows(List<ConstraintRow> selectedRows)
    {
        this.selectedRows = selectedRows;
    }

    public void setSelectedRow(ConstraintRow selectedRow)
    {
        selectedRows = Arrays.asList(selectedRow);
    }

}
