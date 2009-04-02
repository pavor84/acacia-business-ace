/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

/**
 *
 * @author Miro
 */
public class JXPercentValueField extends JPanel {

    public enum Editable {
        None,
        All,
        Percent,
        Value,
        ;

        public static final Set<Editable> PercentSet =
                EnumSet.of(All, Percent);
        public static final Set<Editable> ValueSet =
                EnumSet.of(All, Value);
    }

    private JXPercentField percentField;
    private JXDecimalField percentValueField;
    private JLabel percentLabel;
    private Editable editable;
    private boolean busy;
    private BigDecimal baseValue;
    private ReentrantLock lock;
    /** remember which value was edited the last time */
    boolean freezePercent = true;

    public JXPercentValueField() {
        lock = new ReentrantLock();
        editable = Editable.All;
        freezePercent = true;
        initComponents();
        percentField.addTextListener(new TextListener() {

            public void textChanged(TextEvent event) {
                if(Editable.PercentSet.contains(editable)) {
                    onPercentTextChanged();
                }
            }
        });
        percentField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent event) {
                if(Editable.PercentSet.contains(editable)) {
                    freezePercent = true;
                }
            }
        });

        percentValueField.addTextListener(new TextListener() {

            public void textChanged(TextEvent event) {
                if(Editable.ValueSet.contains(editable)) {
                    onPercentValueTextChanged();
                }
            }
        });
        percentValueField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent event) {
                if(Editable.ValueSet.contains(editable)) {
                    freezePercent = false;
                }
            }
        });
    }

    private void initComponents() {
        percentLabel = new JLabel();
        percentField = new JXPercentField();
        percentValueField = new JXDecimalField();

        percentLabel.setText("%");
        percentField.setColumns(4);
        percentValueField.setColumns(6);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(percentField)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                //.addComponent(percentLabel)
                //.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(percentValueField))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(percentField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(percentLabel)
                    .addComponent(percentValueField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
    }

    public Editable getEditable() {
        return editable;
    }

    public void setEditable(Editable editable) {
        this.editable = editable;
        if(Editable.All.equals(editable)) {
            percentField.setEditable(true);
            percentValueField.setEditable(true);
        } else if(Editable.None.equals(editable)) {
            percentField.setEditable(false);
            percentValueField.setEditable(false);
        } else if(Editable.Percent.equals(editable)) {
            percentField.setEditable(true);
            percentValueField.setEditable(false);
        } else if(Editable.Value.equals(editable)) {
            percentField.setEditable(false);
            percentValueField.setEditable(true);
        }
    }

    public void setEditable(boolean editable) {
        if(editable)
            setEditable(Editable.All);
        else
            setEditable(Editable.None);
    }

    public void setPercent(BigDecimal percent) {
        percentField.setPercent(percent);
        computeValues(true);
    }

    public BigDecimal getPercent() {
        return percentField.getPercent();
    }

    public void setPercentValue(BigDecimal percentValue) {
        percentValueField.setValue(percentValue);
        computeValues(false);
    }

    public BigDecimal getPercentValue() {
        return (BigDecimal)percentValueField.getValue();
    }

    public void setBaseValue(BigDecimal baseValue) {
        this.baseValue = baseValue;
        computeValues();
    }

    public BigDecimal getBaseValue() {
        return baseValue;
    }

    protected void computeValues() {
        computeValues(freezePercent);
    }

    protected void computeValues(boolean computeByPercent) {
        if(computeByPercent)
            onPercentTextChanged();
        else
            onPercentValueTextChanged();
    }

    protected void onPercentTextChanged() {
        if(busy)
            return;

        busy = true;
        lock.lock();

        try {
            String text = percentField.getText();
            BigDecimal value = getBaseValue();
            if(text == null || (text = text.trim()).length() == 0 || value == null) {
                percentValueField.setText("");
                return;
            }

            try {
                JFormattedTextField.AbstractFormatter formatter = percentField.getFormatter();
                BigDecimal percent = (BigDecimal)formatter.stringToValue(text);
                percentValueField.setValue(percent.multiply(value, percentField.getMathContext()));
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            lock.unlock();
            busy = false;
        }
    }

    protected void onPercentValueTextChanged() {
        if(busy)
            return;

        busy = true;
        lock.lock();

        try {
            String text = percentValueField.getText();
            BigDecimal value = getBaseValue();
            if(text == null || (text = text.trim()).length() == 0 || value == null || BigDecimal.ZERO.equals(value)) {
                percentField.setText("");
                return;
            }

            BigDecimal number = null;
            try {
                JFormattedTextField.AbstractFormatter formatter = percentValueField.getFormatter();
                number = (BigDecimal)formatter.stringToValue(text);
                percentField.setValue(number.divide(value, percentField.getMathContext()));
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            lock.unlock();
            busy = false;
        }
    }

    public boolean isFreezePercent() {
        return freezePercent;
    }

    public void setFreezePercent(boolean freezePercent) {
        this.freezePercent = freezePercent;
    }
}
