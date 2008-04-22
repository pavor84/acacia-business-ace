/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.AppCallback;
import com.cosmos.acacia.callback.AppCallbackHandler;
import com.cosmos.acacia.callback.assembling.ChoiceCallback;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * UnconditionalSelectionAlgorithm,
 * UserSelectionAlgorithm,
 * UserSingleSelectionAlgorithm,
 * UserMultipleSelectionAlgorithm,
 * RangeSelectionAlgorithm,
 * RangeSingleSelectionAlgorithm,
 * RangeMultipleSelectionAlgorithm,
 * EqualsSelectionAlgorithm,
 * EqualsSingleSelectionAlgorithm,
 * EqualsMultipleSelectionAlgorithm;
 */


/**
 *
 * @author Miro
 */
public abstract class AbstractAlgorithm
    implements Algorithm, Serializable
{
    private Type type;
    private List resultList;
    private int minSelections = 0;
    private int maxSelections = Integer.MAX_VALUE;

    private AppCallbackHandler callbackHandler;

    protected AbstractAlgorithm(Type type)
    {
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }

    public int getMaxSelections()
    {
        return maxSelections;
    }

    public void setMaxSelections(int maxSelections) {
        this.maxSelections = maxSelections;
    }

    public int getMinSelections()
    {
        return minSelections;
    }

    public void setMinSelections(int minSelections) {
        this.minSelections = minSelections;
    }

    public AppCallbackHandler getCallbackHandler() {
        return callbackHandler;
    }

    public void setCallbackHandler(AppCallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    public List apply(
            List<ConstraintRow> constraintRows,
            Object valueAgainstConstraints)
        throws AlgorithmException
    {
        if(constraintRows.size() == 0)
            throw new EmptySourceValuesException();

        if(Type.UnconditionalSelection.equals(type))
            return getResultList(constraintRows);

        List<ConstraintRow> resultRows;
        if(Type.RangeAlgorithms.contains(type))
            resultRows = applyRangeCondition(constraintRows, (Comparable)valueAgainstConstraints);
        else if(Type.EqualsAlgorithms.contains(type))
            resultRows = applyEqualsCondition(constraintRows, valueAgainstConstraints);
        else
            resultRows = constraintRows;

        int size;
        if((size = resultRows.size()) == 1 && Type.SingleSelectionAlgorithms.contains(type))
            return getResultList(resultRows);

        if(size == 0 || size < getMinSelections())
            resultRows = constraintRows;

        return applyUserSelection(resultRows);
    }

    protected List<ConstraintRow> applyRangeCondition(
            List<ConstraintRow> constraintRows,
            Comparable valueAgainstConstraints)
        throws AlgorithmException
    {
        List<ConstraintRow> resultRows = new ArrayList<ConstraintRow>(constraintRows.size());
        for(ConstraintRow row : constraintRows)
        {
            ConstraintValues constraintValues = row.getConstraintValues();
            Comparable minValue = constraintValues.getMinConstraint();
            Comparable maxValue = constraintValues.getMaxConstraint();
            if((minValue == null || valueAgainstConstraints.compareTo(minValue) >= 0) &&
               (maxValue == null || valueAgainstConstraints.compareTo(maxValue) <= 0))
            {
                resultRows.add(row);
            }
        }

        if(resultRows.size() > 0)
            return resultRows;

        return Collections.emptyList();
    }

    protected List<ConstraintRow> applyEqualsCondition(
            List<ConstraintRow> constraintRows,
            Object valueAgainstConstraints)
        throws AlgorithmException
    {
        List<ConstraintRow> resultRows = new ArrayList<ConstraintRow>(constraintRows.size());
        for(ConstraintRow row : constraintRows)
        {
            ConstraintValues constraintValues = row.getConstraintValues();
            Object value = constraintValues.getMinConstraint();
            if(value != null && valueAgainstConstraints.equals(value))
            {
                resultRows.add(row);
            }
        }

        if(resultRows.size() > 0)
            return resultRows;

        return Collections.emptyList();
    }

    protected List<ConstraintRow> applyUserSelection(List<ConstraintRow> constraintRows)
        throws AlgorithmException
    {
        if(callbackHandler == null)
            throw new IllegalArgumentException("The callbackHandler can not be null when applyUserSelection is invoked.");
        ChoiceCallback choiceCallback = new ChoiceCallback(
                type,
                constraintRows,
                -1,
                getMinSelections(),
                getMaxSelections());
        try
        {
            callbackHandler.handle(new AppCallback[] {choiceCallback});
        }
        catch(IOException ex)
        {
            throw new AlgorithmException(ex);
        }
        catch(UnsupportedCallbackException ex)
        {
            throw new AlgorithmException(ex);
        }

        return choiceCallback.getSelectedRows();
    }


    protected List getResultList(List<ConstraintRow> constraintRows) {
        if(resultList == null)
        {
            resultList = new ArrayList(constraintRows.size());
        }

        addItems(constraintRows);
        return resultList;
    }

    protected List getResultList() {
        if(resultList == null)
            resultList = new LinkedList();

        return resultList;
    }

    protected void addItem(Object item)
    {
        getResultList().add(item);
    }

    protected void addItem(ConstraintRow constraintRow)
    {
        addItem(constraintRow.getCorrespondingObject());
    }

    protected void addItems(List<ConstraintRow> constraintRows)
    {
        for(ConstraintRow constraintRow : constraintRows)
            addItem(constraintRow);
    }


}
