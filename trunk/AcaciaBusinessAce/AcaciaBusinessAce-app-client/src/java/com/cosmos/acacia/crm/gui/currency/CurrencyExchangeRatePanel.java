/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.currency;

import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.swingb.JBCheckBox;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import java.awt.BorderLayout;
import java.text.DecimalFormat;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author Miro
 */
public class CurrencyExchangeRatePanel extends BaseEntityPanel {

    private BindingGroup bindingGroup;
    private EntityFormButtonPanel buttonPanel;
    //
    private JBPanel mainPanel;
    private JBCheckBox fixedExchangeRateCheckBox;
    private JBLabel validFromLabel;
    private JBDatePicker validFromDatePicker;
    private JBLabel validUntilLabel;
    private JBDatePicker validUntilDatePicker;
    private JBLabel fromCurrencyLabel;
    private JBComboBox fromCurrencyComboBox;
    private JBLabel toCurrencyLabel;
    private JBComboBox toCurrencyComboBox;
    private JBLabel exchangeRateLabel;
    private JBDecimalField exchangeRateDecimalField;

    public CurrencyExchangeRatePanel(CurrencyExchangeRate currencyExchangeRate) {
        super(new Object[] {currencyExchangeRate});
        init();
    }

    @Override
    protected void init() {
        initComponents();
        super.init();
    }

    public CurrencyExchangeRate getCurrencyExchangeRate() {
        if(parameters == null || parameters.length == 0) {
            return null;
        }

        return (CurrencyExchangeRate)parameters[0];
    }

    public void setCurrencyExchangeRate(CurrencyExchangeRate currencyExchangeRate) {
        if(parameters == null || parameters.length == 0) {
            parameters = new Object[1];
        }

        parameters[0] = currencyExchangeRate;
    }

    @Override
    public void performSave(boolean closeAfter) {
//        CurrencyExchangeRate cer = getFormSession().saveBasicOrganization(getCurrencyExchangeRate());
//        setDialogResponse(DialogResponse.SAVE);
//        setSelectedValue(organization);
        if (closeAfter) {
            close();
        }
    }

    @Override
    public BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public Object getEntity() {
        return getCurrencyExchangeRate();
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new EntityFormButtonPanel();
            buttonPanel.setName("buttonPanel"); // NOI18N
        }

        return buttonPanel;
    }

    protected void initComponents() {
        setLayout(new BorderLayout());

        String constraints = "wrap 6, fillx";
        String columnsConstraints =
                "[sizegroup labelSG, ::200]" +
                "[sizegroup valueSG, grow 50, fill, :100:]10" +
                "[sizegroup labelSG, ::200]" +
                "[sizegroup valueSG, grow 50, fill, :100:]10" +
                "[sizegroup labelSG, ::200]" +
                "[sizegroup valueSG, grow 50, fill, :100:]";
        mainPanel = new JBPanel(new MigLayout(constraints, columnsConstraints, ""));
        add(mainPanel, BorderLayout.CENTER);

        fixedExchangeRateCheckBox = new JBCheckBox();
        fixedExchangeRateCheckBox.setName("fixedExchangeRateCheckBox");
        fixedExchangeRateCheckBox.setText(getResourceString("fixedExchangeRateCheckBox.text"));
        mainPanel.add(fixedExchangeRateCheckBox, "span 2");

        validFromLabel = new JBLabel();
        validFromLabel.setName("validFromLabel");
        validFromLabel.setText(getResourceString("validFromLabel.text"));
        mainPanel.add(validFromLabel);

        validFromDatePicker = new JBDatePicker();
        validFromDatePicker.setName("validFromDatePicker");
        mainPanel.add(validFromDatePicker);

        validUntilLabel = new JBLabel();
        validUntilLabel.setName("validUntilLabel");
        validUntilLabel.setText(getResourceString("validUntilLabel.text"));
        mainPanel.add(validUntilLabel);

        validUntilDatePicker = new JBDatePicker();
        validUntilDatePicker.setName("validUntilDatePicker");
        mainPanel.add(validUntilDatePicker);

        fromCurrencyLabel = new JBLabel();
        fromCurrencyLabel.setName("fromCurrencyLabel");
        fromCurrencyLabel.setText(getResourceString("fromCurrencyLabel.text"));
        mainPanel.add(fromCurrencyLabel);

        fromCurrencyComboBox = new JBComboBox();
        fromCurrencyComboBox.setName("fromCurrencyComboBox");
        mainPanel.add(fromCurrencyComboBox);

        toCurrencyLabel = new JBLabel();
        toCurrencyLabel.setName("toCurrencyLabel");
        toCurrencyLabel.setText(getResourceString("toCurrencyLabel.text"));
        mainPanel.add(toCurrencyLabel);

        toCurrencyComboBox = new JBComboBox();
        toCurrencyComboBox.setName("toCurrencyComboBox");
        mainPanel.add(toCurrencyComboBox);

        exchangeRateLabel = new JBLabel();
        exchangeRateLabel.setName("exchangeRateLabel");
        exchangeRateLabel.setText(getResourceString("exchangeRateLabel.text"));
        mainPanel.add(exchangeRateLabel);

        exchangeRateDecimalField = new JBDecimalField();
        exchangeRateDecimalField.setName("exchangeRateDecimalField");
        DecimalFormat decimalFormat = (DecimalFormat)exchangeRateDecimalField.getNumberFormat();
        decimalFormat.applyPattern(JBDecimalField.CURRENCY_EXCHANGE_RATE_PATTERN);
        mainPanel.add(exchangeRateDecimalField);

        add(getButtonPanel(), BorderLayout.SOUTH);
    }

    @Override
    protected void initData() {
        super.initData();

    }
}
