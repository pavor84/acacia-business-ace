/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.currency;

import com.cosmos.acacia.crm.bl.currency.CurrencyRemote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.FormattedCellRenderer;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingx.calendar.DefaultDateSelectionModel;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 *
 * @author Miro
 */
public class CurrencyExchangeRateListPanel extends AbstractTablePanel<CurrencyExchangeRate> {

    @EJB
    private static CurrencyRemote formSession;
    //
    private JBPanel controlPanel;
    private JBLabel ratesForDateLabel;
    private JBDatePicker ratesForDateDatePicker;
    //
    private BindingGroup bindingGroup;
    private EntityProperties entityProperties;

    public CurrencyExchangeRateListPanel(Date ratesForDate) {
        super(new Object[] {ratesForDate});
    }

    public CurrencyExchangeRateListPanel(Date rateForDate, DbResource fromCurrency, DbResource toCurrency) {
        super(new Object[] {rateForDate, fromCurrency, toCurrency});
    }

    public CurrencyExchangeRateListPanel() {
        this(new Date());
    }

    public DbResource getFromCurrency() {
        if(parameters == null || parameters.length < 2) {
            return null;
        }

        return (DbResource)parameters[1];
    }

    public DbResource getToCurrency() {
        if(parameters == null || parameters.length < 3) {
            return null;
        }

        return (DbResource)parameters[2];
    }

    public Date getRatesForDate() {
        if(parameters == null || parameters.length == 0) {
            return null;
        }

        return (Date)parameters[0];
    }

    public void setRatesForDate(Date ratesForDate) {
        if(parameters == null || parameters.length == 0) {
            parameters = new Object[1];
        }
        parameters[0] = ratesForDate;

        refreshAction().run();
    }

    @Override
    protected boolean deleteRow(CurrencyExchangeRate rowObject) {
        getFormSession().delete(rowObject);
        return true;
    }

    @Override
    protected CurrencyExchangeRate modifyRow(CurrencyExchangeRate rowObject) {
        if(rowObject != null) {
            return editRow(rowObject);
        }

        return null;
    }

    @Override
    protected CurrencyExchangeRate newRow() {
        CurrencyExchangeRate cer = getFormSession().newEntity(CurrencyExchangeRate.class);
        cer.setValidFrom(getRatesForDate());
        DbResource fromCurrency;
        DbResource toCurrency;
        if((fromCurrency = getFromCurrency()) != null && (toCurrency = getToCurrency()) != null) {
            cer.setFromCurrency(fromCurrency);
            cer.setToCurrency(toCurrency);
        }
        return editRow(cer);
    }

    protected CurrencyExchangeRate editRow(CurrencyExchangeRate currencyExchangeRate) {
        CurrencyExchangeRatePanel panel = new CurrencyExchangeRatePanel(currencyExchangeRate);
        if (DialogResponse.SAVE.equals(panel.showDialog(this))) {
            Task task = new RefreshTask(getApplication());
            task.run();
            return (CurrencyExchangeRate) panel.getSelectedValue();
        }

        return null;
    }

    @Override
    protected void initData() {
        super.initData();

        setVisible(Button.Delete, false);
        setVisible(Button.Classify, false);
        setVisible(Button.Modify, false);

        DbResource fromCurrency = getFromCurrency();
        DbResource toCurrency = getToCurrency();
        if(fromCurrency != null && toCurrency != null) {
            setVisible(Button.Select, true);
        }

        BindingGroup bg = getBindingGroup();
        AcaciaTable dataTable = getDataTable();

        EntityProperties ep = getEntityProperties();
        JTableBinding tableBinding = dataTable.bind(bg, getCurrencyExchangeRates(), ep, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        DateFormat dateFormat = new SimpleDateFormat(JBDatePicker.DEFAULT_DATE_TIME_FORMAT);
        FormattedCellRenderer cellRenderer = new FormattedCellRenderer(dateFormat);
        PropertyDetails propDetails = ep.getPropertyDetails("validFrom");
        TableColumnExt columnExt = dataTable.getColumn(propDetails);
        columnExt.setCellRenderer(cellRenderer);

        propDetails = ep.getPropertyDetails("validUntil");
        columnExt = dataTable.getColumn(propDetails);
        columnExt.setCellRenderer(cellRenderer);

        DecimalFormat decimalFormat = new DecimalFormat(JBDecimalField.CURRENCY_EXCHANGE_RATE_PATTERN);
        cellRenderer = new FormattedCellRenderer(decimalFormat);
        propDetails = ep.getPropertyDetails("exchangeRate");
        columnExt = dataTable.getColumn(propDetails);
        columnExt.setCellRenderer(cellRenderer);

        bg.bind();

        dataTable.packAll();

        if(fromCurrency != null && toCurrency != null) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    newAction();
                }
            });
        }
    }

    protected List<CurrencyExchangeRate> getCurrencyExchangeRates() {
        return getFormSession().getEntities(CurrencyExchangeRate.class, getRatesForDate());
    }

    @Override
    protected JComponent getTopComponent() {
        if (controlPanel == null) {
            controlPanel = new JBPanel(new BorderLayout(10, 0));
            controlPanel.setName("controlPanel");
            controlPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("controlPanel.border.title")));

            ratesForDateLabel = new JBLabel();
            ratesForDateLabel.setName("ratesForDateLabel");
            ratesForDateLabel.setText(getResourceString("ratesForDateLabel.text"));
            controlPanel.add(ratesForDateLabel, BorderLayout.WEST);

            ratesForDateDatePicker = new JBDatePicker();
            ratesForDateDatePicker.setDateFormat(new SimpleDateFormat(JBDatePicker.DEFAULT_DATE_TIME_FORMAT));
            ratesForDateDatePicker.setName("ratesForDateDatePicker");
            ratesForDateDatePicker.getMonthView().setSelectionModel(new DefaultDateSelectionModel());
            ratesForDateDatePicker.setDate(getRatesForDate());
            controlPanel.add(ratesForDateDatePicker, BorderLayout.CENTER);
            ratesForDateDatePicker.addPropertyChangeListener("date", new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent event) {
                    setRatesForDate(ratesForDateDatePicker.getDate());
                }
            });
        }

        return controlPanel;
    }

    @Override
    public Task refreshAction() {
        return new RefreshTask(getApplication());
    }

    protected EntityProperties getEntityProperties() {
        if(entityProperties == null) {
            entityProperties = getFormSession().getEntityProperties(CurrencyExchangeRate.class);
        }

        return entityProperties;
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

    public class RefreshTask extends Task<Object, Void> {

        public RefreshTask(Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to RefreshActionTask fields, here.
            super(app);
            log.debug("RefreshActionTask()");
        }

        @Override
        protected Object doInBackground() {
            log.debug("doInBackground().begin");
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            AcaciaTable dataTable = getDataTable();
            Object selectedRowObject = dataTable.getSelectedRowObject();
            List dataList = dataTable.getData();
            dataList.clear();
            List<CurrencyExchangeRate> currencyExchangeRates = getCurrencyExchangeRates();
            dataList.addAll(currencyExchangeRates);
            if(selectedRowObject != null && dataList.contains(selectedRowObject)) {
                dataTable.setSelectedRowObject(selectedRowObject);
            }
            dataTable.packAll();
            fireTableRefreshed();
            log.debug("doInBackground().end");
            return currencyExchangeRates;  // return your result
        }

        @Override
        protected void succeeded(Object result) {
            log.debug("succeeded(Result:" + result + ")");
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }
}
