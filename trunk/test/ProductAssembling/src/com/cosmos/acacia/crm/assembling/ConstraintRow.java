/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class ConstraintRow
    implements Serializable
{
    private ConstraintValues constraintValues;
    private AssemblingSchemaItemValue correspondingObject;

    public ConstraintRow(AssemblingSchemaItemValue correspondingObject)
    {
        this(ConstraintValues.EMPTY_CONSTRAINT_VALUES, correspondingObject);
    }

    public ConstraintRow(ConstraintValues constraintValues, AssemblingSchemaItemValue correspondingObject)
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

    public AssemblingSchemaItemValue getCorrespondingObject() {
        return correspondingObject;
    }

    public void setCorrespondingObject(AssemblingSchemaItemValue correspondingObject) {
        this.correspondingObject = correspondingObject;
    }

    @Override
    public String toString()
    {
        return "ConstraintRow[" + constraintValues.toString() + ", correspondingObject=" + correspondingObject + "]";
    }
}
