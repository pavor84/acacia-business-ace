/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.currency;

import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import java.awt.BorderLayout;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 *
 * @author Miro
 */
public class CurrencyExchangeRateListPanel extends AbstractTablePanel {

    private Date ratesForDate;
    //
    private JBPanel controlPanel;
    private JBLabel ratesForDateLabel;
    private JBDatePicker ratesForDateDatePicker;

    public CurrencyExchangeRateListPanel(Date ratesForDate) {
        this.ratesForDate = ratesForDate;
    }

    public CurrencyExchangeRateListPanel() {
        this(new Date());
    }

    public Date getRatesForDate() {
        return ratesForDate;
    }

    public void setRatesForDate(Date ratesForDate) {
        this.ratesForDate = ratesForDate;
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        return null;
    }

    @Override
    protected Object newRow() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected JComponent getTopComponent() {
        if(controlPanel == null) {
            controlPanel = new JBPanel(new BorderLayout(10, 0));
            controlPanel.setName("controlPanel");
            controlPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("controlPanel.border.title")));

            ratesForDateLabel = new JBLabel();
            ratesForDateLabel.setName("ratesForDateLabel");
            ratesForDateLabel.setText(getResourceString("ratesForDateLabel.text"));
            controlPanel.add(ratesForDateLabel, BorderLayout.WEST);

            ratesForDateDatePicker = new JBDatePicker();
            ratesForDateDatePicker.setName("ratesForDateDatePicker");
            controlPanel.add(ratesForDateDatePicker, BorderLayout.CENTER);
        }

        return controlPanel;
    }
}
