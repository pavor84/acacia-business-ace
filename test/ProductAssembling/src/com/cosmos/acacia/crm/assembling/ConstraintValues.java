/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class ConstraintValues
    implements Serializable
{
    public static final ConstraintValues EMPTY_CONSTRAINT_VALUES = new ConstraintValues();

    private Object minConstraint;
    private Object maxConstraint;

    private ConstraintValues()
    {
    }

    public ConstraintValues(Object minConstraint)
    {
        this(minConstraint, minConstraint);
    }

    public ConstraintValues(Object minConstraint, Object maxConstraint)
    {
        this.minConstraint = minConstraint;
        this.maxConstraint = maxConstraint;
    }

    public Object getMaxConstraint() {
        return maxConstraint;
    }

    public void setMaxConstraint(Object maxConstraint) {
        this.maxConstraint = maxConstraint;
    }

    public Object getMinConstraint() {
        return minConstraint;
    }

    public void setMinConstraint(Object minConstraint) {
        this.minConstraint = minConstraint;
    }

    public boolean isEmpty()
    {
        return minConstraint == null && maxConstraint == null;
    }

}
