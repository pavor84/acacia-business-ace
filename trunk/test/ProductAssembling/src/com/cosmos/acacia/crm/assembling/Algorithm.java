/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Miro
 */
public interface Algorithm
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

    Type getType();
    int getMaxSelections();
    int getMinSelections();

    List apply(
            List<ConstraintRow> constraintRows,
            Object valueAgainstConstraints)
        throws AlgorithmException;
}
