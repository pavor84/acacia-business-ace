/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Miro
 */
public abstract class Algorithm
{
    private Type type;
    private List resultList;
    private int minSelection = 0;
    private int maxSelection = Integer.MAX_VALUE;


    public enum Type
    {
        /**
         * This algorithm includes everything unconditionally
         */
        UnconditionalSelection,

        /**
         * The User can select 0 or more values from
         * the list of possible values
         */
        UserSelection,

        /**
         * The User must select exactly one values
         * from the list of possible values.
         */
        UserSingleSelection,

        /**
         * The User must select between the minimum
         * and maximum possible values from the list
         */
        UserMultipleSelection,

        /**
         * 
         */
        RangeSelection,

        /**
         * The algorithm implementation must return
         * exactly one value between the specified range. 
         * The listed values must implement java.lang.Comparable
         * interface. If the values which match of the criteria 
         * are more than one or are zero then the algorithm 
         * implementation must show Dialog and to ask for
         * User selection.
         */
        RangeSingleSelection,

        /**
         * The algorithm implementation must return
         * all values between the specified range. 
         * The listed values must implement java.lang.Comparable
         * interface. If there are not values which match of the
         * criteria the algorithm implementation must show Dialog
         * and to ask for User selection.
         */
        RangeMultipleSelection,

        EqualsSelection,

        EqualsSingleSelection,

        EqualsMultipleSelection;

        public final static Set RangeAlgorithms = 
                EnumSet.of(RangeSelection, RangeSingleSelection, RangeMultipleSelection);

        public final static Set EqualsAlgorithms = 
                EnumSet.of(EqualsSelection, EqualsSingleSelection, EqualsMultipleSelection);

        public final static Set UserSelectionAlgorithms = 
                EnumSet.of(UserSelection, UserSingleSelection, UserMultipleSelection);

        public final static Set SelectionAlgorithms = 
                EnumSet.of(UserSelection, RangeSelection, EqualsSelection);

        public final static Set SingleSelectionAlgorithms = 
                EnumSet.of(UserSingleSelection, RangeSingleSelection, EqualsSingleSelection);

        public final static Set MultipleSelectionAlgorithms = 
                EnumSet.of(UserMultipleSelection, RangeMultipleSelection, EqualsMultipleSelection);
    }

    protected Algorithm(Type type)
    {
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }

    public int getMaxSelection()
    {
        return maxSelection;
    }

    public void setMaxSelection(int maxSelection) {
        this.maxSelection = maxSelection;
    }

    public int getMinSelection()
    {
        return minSelection;
    }

    public void setMinSelection(int minSelection) {
        this.minSelection = minSelection;
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

        return Collections.emptyList();
    }

    protected List<ConstraintRow> applyRangeCondition(
            List<ConstraintRow> constraintRows,
            Comparable valueAgainstConstraints)
        throws AlgorithmException
    {

        return Collections.emptyList();
    }

    protected List<ConstraintRow> applyEqualsCondition(
            List<ConstraintRow> constraintRows,
            Object valueAgainstConstraints)
        throws AlgorithmException
    {

        return Collections.emptyList();
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
