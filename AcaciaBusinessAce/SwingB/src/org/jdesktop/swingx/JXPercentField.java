/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.JLabel;

/**
 *
 * @author Miro
 */
public class JXPercentField extends JXNumericField {

    public JXPercentField(BigDecimal value) {
        super(value);
    }

    public JXPercentField() {
    }

    @Override
    protected void init() {
        DecimalFormat format = (DecimalFormat)getNumberFormat();
        format.setMultiplier(100);
        super.init();
        setSuffixComponent(new JLabel("%"));
    }

    public void setPercent(BigDecimal percent) {
        setValue(percent);
    }

    public BigDecimal getPercent() {
        return (BigDecimal)getValue();
    }
}
