/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.currency;

import com.cosmos.acacia.crm.bl.currency.CurrencyRemote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBCheckBox;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.swingx.calendar.CalendarUtils;
import org.jdesktop.swingx.calendar.DefaultDateSelectionModel;

/**
 *
 * @author Miro
 */
public class CurrencyExchangeRatePanel extends BaseEntityPanel {

    @EJB
    private static CurrencyRemote formSession;
    //
    private EntityProperties entityProperties;
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
        CurrencyExchangeRate cer = getFormSession().save(getCurrencyExchangeRate());
        setCurrencyExchangeRate(cer);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(cer);
        if (closeAfter) {
            close();
        }
    }

    protected EntityProperties getEntityProperties() {
        if(entityProperties == null) {
            entityProperties = getFormSession().getEntityProperties(CurrencyExchangeRate.class);
        }

        return entityProperties;
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
        validFromDatePicker.getMonthView().setSelectionModel(new DefaultDateSelectionModel());
        mainPanel.add(validFromDatePicker);

        validUntilLabel = new JBLabel();
        validUntilLabel.setName("validUntilLabel");
        validUntilLabel.setText(getResourceString("validUntilLabel.text"));
        mainPanel.add(validUntilLabel);

        validUntilDatePicker = new JBDatePicker();
        validUntilDatePicker.setName("validUntilDatePicker");
        validUntilDatePicker.getMonthView().setSelectionModel(new DefaultDateSelectionModel());
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

        BindingGroup bg = getBindingGroup();
        EntityProperties ep = getEntityProperties();
        CurrencyExchangeRate cer = getCurrencyExchangeRate();

        PropertyDetails pd = ep.getPropertyDetails("fixedExchangeRate");
        fixedExchangeRateCheckBox.bind(bg, cer, pd);

        pd = ep.getPropertyDetails("validFrom");
        validFromDatePicker.bind(bg, cer, pd, new SimpleDateFormat(AcaciaUtils.DEFAULT_DATE_TIME_FORMAT)).addBindingListener(new AbstractBindingListener() {

            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                CurrencyExchangeRate cer = getCurrencyExchangeRate();
                Date validFrom;
                if((validFrom = cer.getValidFrom()) == null) {
                    return;
                }

                Date validUntil;
                if((validUntil = cer.getValidUntil()) == null || validUntil.getTime() <= validFrom.getTime()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(validFrom);
                    CalendarUtils.endOfDay(calendar);
                    validUntilDatePicker.setDate(calendar.getTime());
                }
            }
        });

        pd = ep.getPropertyDetails("validUntil");
        validUntilDatePicker.bind(bg, cer, pd, new SimpleDateFormat(AcaciaUtils.DEFAULT_DATE_TIME_FORMAT));

        List<DbResource> currencies = getCurrencies();
        pd = ep.getPropertyDetails("fromCurrency");
        fromCurrencyComboBox.bind(bg, currencies, cer, pd);

        pd = ep.getPropertyDetails("toCurrency");
        toCurrencyComboBox.bind(bg, currencies, cer, pd);

        pd = ep.getPropertyDetails("exchangeRate");
        exchangeRateDecimalField.bind(bg, cer, pd);

        bg.bind();
    }

    protected List<DbResource> getCurrencies() {
        return getFormSession().getResources(Currency.class);
    }

    protected CurrencyRemote getFormSession() {
        if (formSession == null) {
            try {
                formSession = getBean(CurrencyRemote.class);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return formSession;
    }
}
