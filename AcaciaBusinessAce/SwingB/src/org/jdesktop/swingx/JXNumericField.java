/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.swingx;

import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Document;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Miro
 */
public class JXNumericField extends JXPanel {//JFormattedTextField {

    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    public static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;
    public static final String WRONG_NUMBER_FORMAT = "The value MUST be Number instace.";
    public static final String DEFAULT_PATTERN = "#,##0.0#;-#,##0.0#";
    private JFormattedTextField numericField;
    private Component prefixComponent;
    private Component suffixComponent;
    protected String pattern;
    protected DecimalFormatSymbols decimalFormatSymbols;
    protected Class valueClass;
    protected NumberFormat numberFormat;
    protected NumberFormatter numberFormatter;
    protected MathContext mathContext;
    private DocumentHandler documentHandler;

    public JXNumericField(Number value) {
        this();
        setValue(value);
    }

    public JXNumericField() {
        super(new BorderLayout());
        numericField = new NumericField();
        add(numericField, BorderLayout.CENTER);
        init();
    }

    protected void init() {
        setFormatterFactory(new DefaultFormatterFactory(getNumberFormatter()));
        setHorizontalAlignment(JTextField.TRAILING);
    }

    public JFormattedTextField getNumericField() {
        return numericField;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        numericField.setName(name);
    }

    public Component getPrefixComponent() {
        return prefixComponent;
    }

    public void setPrefixComponent(Component prefixComponent) {
        removePrefixComponent();
        this.prefixComponent = prefixComponent;
        if (prefixComponent != null) {
            add(prefixComponent, BorderLayout.WEST);
        }
    }

    public void removePrefixComponent() {
        if (prefixComponent != null) {
            remove(prefixComponent);
            prefixComponent = null;
        }
    }

    public Component getSuffixComponent() {
        return suffixComponent;
    }

    public void setSuffixComponent(Component suffixComponent) {
        removeSuffixComponent();
        this.suffixComponent = suffixComponent;
        if (suffixComponent != null) {
            add(suffixComponent, BorderLayout.EAST);
        }
    }

    public void removeSuffixComponent() {
        if (suffixComponent != null) {
            remove(suffixComponent);
            suffixComponent = null;
        }
    }

    /**
     * Returns the horizontal alignment of the text.
     * Valid keys are:
     * <ul>
     * <li><code>JTextField.LEFT</code>
     * <li><code>JTextField.CENTER</code>
     * <li><code>JTextField.RIGHT</code>
     * <li><code>JTextField.LEADING</code>
     * <li><code>JTextField.TRAILING</code>
     * </ul>
     *
     * @return the horizontal alignment
     */
    public int getHorizontalAlignment() {
        return numericField.getHorizontalAlignment();
    }

    /**
     * Sets the horizontal alignment of the text.
     * Valid keys are:
     * <ul>
     * <li><code>JTextField.LEFT</code>
     * <li><code>JTextField.CENTER</code>
     * <li><code>JTextField.RIGHT</code>
     * <li><code>JTextField.LEADING</code>
     * <li><code>JTextField.TRAILING</code>
     * </ul>
     * <code>invalidate</code> and <code>repaint</code> are called when the
     * alignment is set,
     * and a <code>PropertyChange</code> event ("horizontalAlignment") is fired.
     *
     * @param alignment the alignment
     * @exception IllegalArgumentException if <code>alignment</code>
     *  is not a valid key
     * @beaninfo
     *   preferred: true
     *       bound: true
     * description: Set the field alignment to LEFT, CENTER, RIGHT,
     *              LEADING (the default) or TRAILING
     *        enum: LEFT JTextField.LEFT CENTER JTextField.CENTER RIGHT JTextField.RIGHT
     *              LEADING JTextField.LEADING TRAILING JTextField.TRAILING
     */
    public void setHorizontalAlignment(int alignment) {
        numericField.setHorizontalAlignment(alignment);
    }

    /**
     * Sets the <code>AbstractFormatterFactory</code>.
     * <code>AbstractFormatterFactory</code> is
     * able to return an instance of <code>AbstractFormatter</code> that is
     * used to format a value for display, as well an enforcing an editing
     * policy.
     * <p>
     * If you have not explicitly set an <code>AbstractFormatterFactory</code>
     * by way of this method (or a constructor) an
     * <code>AbstractFormatterFactory</code> and consequently an
     * <code>AbstractFormatter</code> will be used based on the
     * <code>Class</code> of the value. <code>NumberFormatter</code> will
     * be used for <code>Number</code>s, <code>DateFormatter</code> will
     * be used for <code>Dates</code>, otherwise <code>DefaultFormatter</code>
     * will be used.
     * <p>
     * This is a JavaBeans bound property.
     *
     * @param tf <code>AbstractFormatterFactory</code> used to lookup
     *          instances of <code>AbstractFormatter</code>
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: AbstractFormatterFactory, responsible for returning an
     *              AbstractFormatter that can format the current value.
     */
    public void setFormatterFactory(JFormattedTextField.AbstractFormatterFactory formatterFactory) {
        numericField.setFormatterFactory(formatterFactory);
    }

    /**
     * Returns the current <code>AbstractFormatterFactory</code>.
     *
     * @see #setFormatterFactory
     * @return <code>AbstractFormatterFactory</code> used to determine
     *         <code>AbstractFormatter</code>s
     */
    public JFormattedTextField.AbstractFormatterFactory getFormatterFactory() {
        return numericField.getFormatterFactory();
    }

    /**
     * Sets the current <code>AbstractFormatter</code>.
     * <p>
     * You should not normally invoke this, instead set the
     * <code>AbstractFormatterFactory</code> or set the value.
     * <code>JFormattedTextField</code> will
     * invoke this as the state of the <code>JFormattedTextField</code>
     * changes and requires the value to be reset.
     * <code>JFormattedTextField</code> passes in the
     * <code>AbstractFormatter</code> obtained from the
     * <code>AbstractFormatterFactory</code>.
     * <p>
     * This is a JavaBeans bound property.
     *
     * @see #setFormatterFactory
     * @param format AbstractFormatter to use for formatting
     * @beaninfo
     *       bound: true
     *   attribute: visualUpdate true
     * description: TextFormatter, responsible for formatting the current value
     */
    protected void setFormatter(JFormattedTextField.AbstractFormatter formatter) {
        numericField.setFormatterFactory(null);
    }

    /**
     * Returns the <code>AbstractFormatter</code> that is used to format and
     * parse the current value.
     *
     * @return AbstractFormatter used for formatting
     */
    public JFormattedTextField.AbstractFormatter getFormatter() {
        return numericField.getFormatter();
    }

    /**
     * Associates the editor with a text document.
     * The currently registered factory is used to build a view for
     * the document, which gets displayed by the editor after revalidation.
     * A PropertyChange event ("document") is propagated to each listener.
     *
     * @param doc  the document to display/edit
     * @see #getDocument
     * @beaninfo
     *  description: the text document model
     *        bound: true
     *       expert: true
     */
    public void setDocument(Document document) {
        numericField.setDocument(document);
    }

    /**
     * Fetches the model associated with the editor.  This is
     * primarily for the UI to get at the minimal amount of
     * state required to be a text editor.  Subclasses will
     * return the actual type of the model which will typically
     * be something that extends Document.
     *
     * @return the model
     */
    public Document getDocument() {
        return numericField.getDocument();
    }

    /**
     * Returns the number of columns in this <code>TextField</code>.
     *
     * @return the number of columns >= 0
     */
    public int getColumns() {
        return numericField.getColumns();
    }

    /**
     * Sets the number of columns in this <code>TextField</code>,
     * and then invalidate the layout.
     *
     * @param columns the number of columns >= 0
     * @exception IllegalArgumentException if <code>columns</code>
     *		is less than 0
     * @beaninfo
     * description: the number of columns preferred for display
     */
    public void setColumns(int columns) {
        numericField.setColumns(columns);
    }

    /**
     * Returns the boolean indicating whether this
     * <code>TextComponent</code> is editable or not.
     *
     * @return the boolean value
     * @see #setEditable
     */
    public boolean isEditable() {
        return numericField.isEditable();
    }

    /**
     * Sets the specified boolean to indicate whether or not this
     * <code>TextComponent</code> should be editable.
     * A PropertyChange event ("editable") is fired when the
     * state is changed.
     *
     * @param b the boolean to be set
     * @see #isEditable
     * @beaninfo
     * description: specifies if the text can be edited
     *       bound: true
     */
    public void setEditable(boolean editable) {
        numericField.setEditable(editable);
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
        if (mathContext == null) {
            mathContext = MATH_CONTEXT;
        }

        return mathContext;
    }

    public void setMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
    }

    protected Number getNumberValue(Object value) {
        if (value == null) {
            return null;
        }

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

    public void setValue(Number numberValue) {
        numericField.setValue(getNumberValue(numberValue));
    }

    public Number getValue() {
        return (Number) numericField.getValue();
    }

    /**
     * Sets the text of this <code>TextComponent</code>
     * to the specified text.  If the text is <code>null</code>
     * or empty, has the effect of simply deleting the old text.
     * When text has been inserted, the resulting caret location
     * is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">How
     * to Use Threads</A> for more information.
     *
     * Note that text is not a bound property, so no <code>PropertyChangeEvent
     * </code> is fired when it changes. To listen for changes to the text,
     * use <code>DocumentListener</code>.
     *
     * @param t the new text to be set
     * @see #getText
     * @see DefaultCaret
     * @beaninfo
     * description: the text of this component
     */
    public void setText(String text) {
        numericField.setText(text);
    }

    /**
     * Returns the text contained in this <code>TextComponent</code>.
     * If the underlying document is <code>null</code>,
     * will give a <code>NullPointerException</code>.
     *
     * Note that text is not a bound property, so no <code>PropertyChangeEvent
     * </code> is fired when it changes. To listen for changes to the text,
     * use <code>DocumentListener</code>.
     *
     * @return the text
     * @exception NullPointerException if the document is <code>null</code>
     * @see #setText
     */
    public String getText() {
        return numericField.getText();
    }

    private DocumentHandler getDocumentHandler() {
        if (documentHandler == null) {
            documentHandler = new DocumentHandler();
        }

        return documentHandler;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (documentHandler != null) {
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
            TextEvent textEvent = new TextEvent(numericField);
            for (TextListener listener : getTextListeners()) {
                listener.textChanged(textEvent);
            }
        }
    }

    public Number getNumberValue() {
        return (Number) getValue();
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

    private class NumericField extends JFormattedTextField {

        public NumericField() {
        }
    }
}
