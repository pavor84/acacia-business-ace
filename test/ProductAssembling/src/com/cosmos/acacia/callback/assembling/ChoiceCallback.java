/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.callback.assembling;

import com.cosmos.acacia.crm.assembling.Algorithm;
import com.cosmos.acacia.crm.assembling.Algorithm.Type;
import com.cosmos.acacia.crm.assembling.ConstraintRow;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Miro
 */
public class ChoiceCallback
    implements AssemblingCallback, Serializable
{
    private Algorithm.Type algorithmType;
    private List<ConstraintRow> choices;
    private int defaultChoice;
    private int minSelections;
    private int maxSelections;

    private List<ConstraintRow> selectedRows;

    public ChoiceCallback(
            Algorithm.Type algorithmType,
            List<ConstraintRow> choices,
            int defaultChoice,
            int minSelections,
            int maxSelections)
    {
        if(algorithmType == null)
            throw new IllegalArgumentException("The algorithmType can not be null.");
        this.algorithmType = algorithmType;

        if(choices == null || choices.size() == 0)
            throw new IllegalArgumentException("The choices can not be null or zero length: " + choices);
        this.choices = choices;

        int size;
        if(defaultChoice >= (size = choices.size()))
            throw new IllegalArgumentException("The defaultChoice index (" + defaultChoice + ") can not be great than choices size (" + size + ").");
        this.defaultChoice = defaultChoice;

        if(minSelections > maxSelections)
            throw new IllegalArgumentException("The minSelections (" + minSelections + ") can not be great than maxSelections (" + maxSelections + ").");
        this.minSelections = minSelections;
        this.maxSelections = maxSelections;
    }

    public Type getAlgorithmType() {
        return algorithmType;
    }

    public List<ConstraintRow> getChoices() {
        return choices;
    }

    public int getDefaultChoice() {
        return defaultChoice;
    }

    public int getMaxSelections() {
        return maxSelections;
    }

    public int getMinSelections() {
        return minSelections;
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
