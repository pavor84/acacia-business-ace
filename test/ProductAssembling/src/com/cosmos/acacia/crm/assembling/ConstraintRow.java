/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

/**
 *
 * @author Miro
 */
public class ConstraintRow
{
    private ConstraintValues constraintValues;
    private Object correspondingObject;

    public ConstraintRow(Object correspondingObject)
    {
        this(ConstraintValues.EMPTY_CONSTRAINT_VALUES, correspondingObject);
    }

    public ConstraintRow(ConstraintValues constraintValues, Object correspondingObject)
    {
        this.constraintValues = constraintValues;
        this.correspondingObject = correspondingObject;
    }

    public ConstraintValues getConstraintValues() {
        return constraintValues;
    }

    public void setConstraintValues(ConstraintValues constraintValues) {
        this.constraintValues = constraintValues;
    }

    public Object getCorrespondingObject() {
        return correspondingObject;
    }

    public void setCorrespondingObject(Object correspondingObject) {
        this.correspondingObject = correspondingObject;
    }

}
