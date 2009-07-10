/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.util;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author Miro
 */
public class MoneyUtils {

    private static InheritableThreadLocal<MoneyUtils> instance =
            new InheritableThreadLocal<MoneyUtils>();
    //
    private MathContext mathContext = MathContext.DECIMAL128;
    private int scale = 2;

    protected MoneyUtils() {
    }

    public static MoneyUtils getInstance() {
        MoneyUtils utils;
        if ((utils = instance.get()) == null) {
            utils = new MoneyUtils();
            instance.set(utils);
        }

        return utils;
    }

    public static void removeInstance() {
        instance.remove();
    }

    public MathContext getMathContext() {
        return mathContext;
    }

    public void setMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public BigDecimal round(BigDecimal amount) {
        if (amount == null) {
            return amount;
        }

        return amount.setScale(scale, mathContext.getRoundingMode());
    }

    public BigDecimal subtract(BigDecimal value, BigDecimal subtrahend) {
        if (subtrahend == null) {
            return round(value);
        }

        if (value != null) {
            return round(value.subtract(subtrahend, mathContext));
        } else {
            return round(BigDecimal.ZERO.subtract(subtrahend, mathContext));
        }
    }

    public BigDecimal add(BigDecimal value, BigDecimal augend) {
        if (augend == null) {
            return round(value);
        }

        if (value == null) {
            return round(augend);
        }

        return round(value.add(augend, mathContext));
    }

    public BigDecimal multiply(BigDecimal value, BigDecimal multiplicand) {
        if (multiplicand == null) {
            return round(value);
        }

        if (value == null || BigDecimal.ZERO.equals(value) || BigDecimal.ZERO.equals(multiplicand)) {
            return round(BigDecimal.ZERO);
        }

        return round(value.multiply(multiplicand, mathContext));
    }

    public BigDecimal divide(BigDecimal value, BigDecimal divisor) {
        if (value == null) {
            return value;
        }

        return round(value.divide(divisor, mathContext));
    }
}
