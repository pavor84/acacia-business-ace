/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import java.math.BigDecimal;

/**
 *
 * @author Miro
 */
public class JXPercentField extends JXNumberField {

    public JXPercentField(Object value) {
        super(value);
    }

    public JXPercentField() {
    }

    @Override
    protected void init() {
        setPattern("#,##0.0####");
        super.init();
    }

    public void setPercent(BigDecimal percent) {
        if(percent != null)
            super.setValue(new BigDecimal(getNumberValue(percent).toString()).multiply(ONE_HUNDRED, MATH_CONTEXT));
        else
            super.setValue(percent);
    }

    public BigDecimal getPercent() {
        return getPercent((BigDecimal)super.getValue());
    }

    public BigDecimal getPercent(BigDecimal number) {
        if(number != null)
            return number.divide(ONE_HUNDRED, MATH_CONTEXT);

        return null;
    }
}
