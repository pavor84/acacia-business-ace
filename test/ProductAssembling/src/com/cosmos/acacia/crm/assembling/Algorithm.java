/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.ApplicationCallback;
import com.cosmos.acacia.callback.assembling.ChoiceCallback;
import com.cosmos.acacia.callback.assembling.LessSelectedItemsThanAllowed;
import com.cosmos.acacia.callback.assembling.MoreSelectedItemsThanAllowed;
import com.cosmos.acacia.crm.data.assembling.AssemblingAlgorithm;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import java.io.Serializable;
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
public class Algorithm
    implements Serializable
{
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


        private int algorithmId;

        public int getAlgorithmId() {
            return algorithmId;
        }

        public void setAlgorithmId(int algorithmId) {
            this.algorithmId = algorithmId;
        }


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

    private Type type;
    private boolean initialized;
    private List<AssemblingSchemaItemValue> resultList;
    private int minSelections = 0;
    private int maxSelections = Integer.MAX_VALUE;

    private AssemblingSchemaItem assemblingSchemaItem;


    public Algorithm(AssemblingSchemaItem assemblingSchemaItem)
    {
        this.assemblingSchemaItem = assemblingSchemaItem;
        AssemblingAlgorithm assemblingAlgorithm = assemblingSchemaItem.getAssemblingAlgorithm();
        this.type = Type.valueOf(assemblingAlgorithm.getAlgorithmCode());
        Integer intValue = assemblingSchemaItem.getMinSelections();
        if(intValue != null)
            minSelections = intValue;
        intValue = assemblingSchemaItem.getMaxSelections();
        if(intValue != null)
            maxSelections = intValue;
    }

    public Type getType()
    {
        return type;
    }

    public int getMaxSelections()
    {
        return maxSelections;
    }

    public int getMinSelections()
    {
        return minSelections;
    }

    public AssemblingSchemaItem getAssemblingSchemaItem() {
        return assemblingSchemaItem;
    }

    public AlgorithmResult apply(AlgorithmResult algorithmResult)
    {
        AlgorithmResult.Type algorithmResultType = algorithmResult.getType();
        if(AlgorithmResult.Type.Initialization.equals(algorithmResultType))
        {
            if(initialized)
                return new ExceptionAlgorithmResult(new IllegalArgumentException("The algorithm is already initialized."));
            initialized = true;
            return apply(assemblingSchemaItem.getItemValues(), algorithmResult.getValueAgainstConstraints());
        }

        if(AlgorithmResult.Type.Callback.equals(algorithmResultType))
        {
            return applyUserSelection(algorithmResult.getApplicationCallbacks());
        }

        return new ExceptionAlgorithmResult(new IllegalArgumentException("Unsupported AlgorithmResult.Type parameter: " + algorithmResultType));
    }

    protected AlgorithmResult apply(
            List<AssemblingSchemaItemValue> itemValues,
            Object valueAgainstConstraints)
    {
        if(resultList != null && resultList.size() > 0)
            resultList.clear();
        List<ConstraintRow> constraintRows = new ArrayList<ConstraintRow>(itemValues.size());
        for(AssemblingSchemaItemValue itemValue : itemValues)
        {
            ConstraintValues constraintValues = 
                    new ConstraintValues(
                            (Comparable)itemValue.getMinConstraint(),
                            (Comparable)itemValue.getMaxConstraint());
            ConstraintRow row = new ConstraintRow(constraintValues, itemValue);
            constraintRows.add(row);
        }

        if(constraintRows.size() == 0)
            return new ExceptionAlgorithmResult(new EmptySourceValuesException());

        if(Type.UnconditionalSelection.equals(type))
            return getResultList(constraintRows);

        List<ConstraintRow> resultRows = constraintRows;
        if(valueAgainstConstraints != null)
        {
            if(Type.RangeAlgorithms.contains(type))
                resultRows = applyRangeCondition(constraintRows, (Comparable)valueAgainstConstraints);
            else if(Type.EqualsAlgorithms.contains(type))
                resultRows = applyEqualsCondition(constraintRows, valueAgainstConstraints);
        }

        int size = resultRows.size();
        if(Type.SingleSelectionAlgorithms.contains(type) ||
           Type.MultipleSelectionAlgorithms.contains(type))
        {
            if(size > 0 && size == minSelections && size <= maxSelections)
                return getResultList(resultRows);
        }

        if(size == 0 || size < minSelections)
            resultRows = constraintRows;

        return applyUserSelection(resultRows, valueAgainstConstraints);
    }

    protected List<ConstraintRow> applyRangeCondition(
            List<ConstraintRow> constraintRows,
            Comparable valueAgainstConstraints)
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

    protected AlgorithmResult applyUserSelection(
            List<ConstraintRow> constraintRows,
            Object valueAgainstConstraints)
    {
        ChoiceCallback choiceCallback = new ChoiceCallback(
                assemblingSchemaItem,
                valueAgainstConstraints,
                constraintRows,
                -1);
        return new CallbackAlgorithmResult(new ApplicationCallback[] {choiceCallback});
    }

    protected AlgorithmResult applyUserSelection(ApplicationCallback[] callbacks)
    {
        ChoiceCallback choiceCallback = (ChoiceCallback)callbacks[0];
        List<ConstraintRow> selectedRows = choiceCallback.getSelectedRows();
        if(Type.SingleSelectionAlgorithms.contains(type) ||
           Type.MultipleSelectionAlgorithms.contains(type))
        {
            int selected = selectedRows.size();
            int allowed;
            if(selected < (allowed = minSelections))
            {
                return new ExceptionAlgorithmResult(new LessSelectedItemsThanAllowed(selected, allowed));
            }
            else if(selected > maxSelections)
            {
                return new ExceptionAlgorithmResult(new MoreSelectedItemsThanAllowed(selected, allowed));
            }
        }

        return getResultList(selectedRows);
    }

    protected AlgorithmResult getResultList(List<ConstraintRow> constraintRows)
    {
        if(resultList == null)
        {
            resultList = new ArrayList(constraintRows.size());
        }

        addConstraintRows(constraintRows);
        return new FinalAlgorithmResult(resultList);
    }

    protected List<AssemblingSchemaItemValue> getResultList()
    {
        if(resultList == null)
            resultList = new LinkedList();

        return resultList;
    }

    protected void addItem(AssemblingSchemaItemValue item)
    {
        getResultList().add(item);
    }

    protected void addConstraintRow(ConstraintRow constraintRow)
    {
        addItem(constraintRow.getCorrespondingObject());
    }

    protected void addConstraintRows(List<ConstraintRow> constraintRows)
    {
        for(ConstraintRow constraintRow : constraintRows)
            addConstraintRow(constraintRow);
    }
}

/**
 * 
delete from complex_product_items;
delete from complex_products;
delete from assembling_schema_item_values;
delete from assembling_schema_items;
delete from assembling_schemas;
delete from assembling_categories;
delete from real_products;
delete from simple_products;
delete from virtual_products;
delete from products;
delete from offer_items;
delete from offers;
 */
