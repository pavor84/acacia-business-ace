/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.swingx;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Miro
 */
public class JXNumberField extends JFormattedTextField {

    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    public static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;
    public static final String WRONG_NUMBER_FORMAT = "The value MUST be Number instace.";
    public static final String DEFAULT_PATTERN = "#,##0.0#";
    protected String pattern;
    protected DecimalFormatSymbols decimalFormatSymbols;
    protected Class valueClass;
    protected NumberFormat numberFormat;
    protected NumberFormatter numberFormatter;
    protected MathContext mathContext;
    private DocumentHandler documentHandler;

    public JXNumberField(Number value) {
        this();
        setValue(value);
    }

    public JXNumberField() {
        init();
    }

    protected void init() {
        setFormatterFactory(new DefaultFormatterFactory(getNumberFormatter()));
        //setHorizontalAlignment(RIGHT);
        setHorizontalAlignment(TRAILING);
    }

    public void addTextListener(TextListener listener) {
        getDocument().addDocumentListener(getDocumentHandler());
        listenerList.add(TextListener.class, listener);
    }

    public void removeTextListener(TextListener listener) {
        listenerList.remove(TextListener.class, listener);
    }

    public TextListener[] getTextListeners() {
        return listenerList.getListeners(TextListener.class);
    }

    public NumberFormatter getNumberFormatter() {
        if (numberFormatter == null) {
            numberFormatter = new NumberFormatter(getNumberFormat());
            numberFormatter.setValueClass(getValueClass());
        }

        return numberFormatter;
    }

    public void setNumberFormatter(NumberFormatter numberFormatter) {
        this.numberFormatter = numberFormatter;
    }

    public Class<?> getValueClass() {
        if (valueClass == null) {
            valueClass = BigDecimal.class;
        }

        return valueClass;
    }

    public void setValueClass(Class<?> valueClass) {
        this.valueClass = valueClass;
    }

    public NumberFormat getNumberFormat() {
        if (numberFormat == null) {
            numberFormat = new DecimalFormat(getPattern(), getDecimalFormatSymbols());
            numberFormat.setRoundingMode(getMathContext().getRoundingMode());
        }

        return numberFormat;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public DecimalFormatSymbols getDecimalFormatSymbols() {
        if (decimalFormatSymbols == null) {
            decimalFormatSymbols = DecimalFormatSymbols.getInstance();
            decimalFormatSymbols.setDecimalSeparator('.');
            decimalFormatSymbols.setMonetaryDecimalSeparator('.');
        }

        return decimalFormatSymbols;
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        this.decimalFormatSymbols = decimalFormatSymbols;
    }

    public String getPattern() {
        if (pattern == null) {
            pattern = DEFAULT_PATTERN;
        }

        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public MathContext getMathContext() {
        if(mathContext == null) {
            mathContext = MATH_CONTEXT;
        }

        return mathContext;
    }

    public void setMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
    }

    protected Number getNumberValue(Object value) {
        if(value == null)
            return null;

        if (value instanceof Number) {
            return (Number) value;
        }

        if (value instanceof String) {
            try {
                return new BigDecimal((String) value);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(WRONG_NUMBER_FORMAT, ex);
            }
        } else if (value instanceof char[]) {
            try {
                return new BigDecimal((char[]) value);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(WRONG_NUMBER_FORMAT, ex);
            }
        }

        throw new IllegalArgumentException(WRONG_NUMBER_FORMAT);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(getNumberValue(value));
    }

    private DocumentHandler getDocumentHandler() {
        if(documentHandler == null) {
            documentHandler = new DocumentHandler();
        }

        return documentHandler;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(documentHandler != null) {
            getDocument().removeDocumentListener(documentHandler);
            documentHandler = null;
        }
    }

    private class DocumentHandler implements DocumentListener {

        public void insertUpdate(DocumentEvent event) {
            textChanged(event);
        }

        public void removeUpdate(DocumentEvent event) {
            textChanged(event);
        }

        public void changedUpdate(DocumentEvent event) {
            textChanged(event);
        }

        public void textChanged(DocumentEvent event) {
            TextEvent textEvent = new TextEvent(JXNumberField.this);
            for(TextListener listener : getTextListeners()) {
                listener.textChanged(textEvent);
            }
        }
    }

    public Number getNumberValue() {
        return (Number)getValue();
    }

    public byte getByteValue() {
        return getNumberValue().byteValue();
    }

    public short getShortValue() {
        return getNumberValue().shortValue();
    }

    public int getIntValue() {
        return getNumberValue().intValue();
    }

    public long getLongValue() {
        return getNumberValue().longValue();
    }

    public float getFloatValue() {
        return getNumberValue().floatValue();
    }

    public double getDoubleValue() {
        return getNumberValue().doubleValue();
    }
}
