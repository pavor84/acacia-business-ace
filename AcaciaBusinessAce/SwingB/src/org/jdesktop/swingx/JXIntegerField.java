/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.swingx;

import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 *
 * @author Miro
 */
public class JXIntegerField extends JXNumericField {

    public enum NumberType {

        ByteType(Byte.class),
        ShortType(Short.class),
        IntegerType(Integer.class),
        LongType(Long.class),
        BigIntegerType(BigInteger.class),
        ;

        private NumberType(Class numberClass) {
            this.numberClass = numberClass;
        }

        private Class numberClass;

        public Class getNumberClass() {
            return numberClass;
        }
    }

    private NumberType numberType;

    public JXIntegerField(BigInteger value) {
        super(value);
    }

    public JXIntegerField() {
    }

    @Override
    protected void init() {
        DecimalFormat format = (DecimalFormat)getNumberFormat();
        format.setMaximumFractionDigits(0);
        format.setDecimalSeparatorAlwaysShown(false);
        format.setParseIntegerOnly(true);
        setNumberType(NumberType.IntegerType);
        super.init();
    }

    public NumberType getNumberType() {
        return numberType;
    }

    public void setNumberType(NumberType numberType) {
        this.numberType = numberType;
        setValueClass(numberType.getNumberClass());
    }
}
