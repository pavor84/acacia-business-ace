/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.currency;

import com.cosmos.acacia.crm.bl.currency.CurrencyRemote;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.FormattedCellRenderer;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 *
 * @author Miro
 */
public class CurrencyExchangeRateListPanel extends AbstractTablePanel {

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

    public CurrencyExchangeRateListPanel() {
        this(new Date());
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
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null) {
            return editRow((CurrencyExchangeRate)rowObject);
        }

        return null;
    }

    @Override
    protected Object newRow() {
        editRow(getFormSession().newEntity(CurrencyExchangeRate.class));
        return null;
    }

    protected Object editRow(CurrencyExchangeRate currencyExchangeRate) {
        CurrencyExchangeRatePanel panel = new CurrencyExchangeRatePanel(currencyExchangeRate);
        panel.showDialog(this);

        return null;
    }

    @Override
    protected void initData() {
        super.initData();

        BindingGroup bg = getBindingGroup();
        AcaciaTable dataTable = getDataTable();

        EntityProperties ep = getEntityProperties();
        JTableBinding tableBinding = dataTable.bind(bg, getCurrencyExchangeRates(), ep, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        DecimalFormat decimalFormat = new DecimalFormat(JBDecimalField.CURRENCY_EXCHANGE_RATE_PATTERN);
        FormattedCellRenderer cellRenderer = new FormattedCellRenderer(decimalFormat);
        PropertyDetails propDetails = ep.getPropertyDetails("exchangeRate");
        TableColumnExt columnExt = dataTable.getColumn(propDetails);
        columnExt.setCellRenderer(cellRenderer);

        bg.bind();
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
            ratesForDateDatePicker.setName("ratesForDateDatePicker");
            controlPanel.add(ratesForDateDatePicker, BorderLayout.CENTER);
        }

        return controlPanel;
    }

    @Override
    public Task refreshAction() {
        log.debug("refreshAction");
        Task result = new RefreshTask(getApplication());
        fireTableRefreshed();
        return result;
    }

//    protected void refreshTable() {
//        AcaciaTable dataTable = getDataTable();
//        Object selectedRowObject = dataTable.getSelectedRowObject();
//        List dataList = dataTable.getData();
//        dataList.clear();
//        dataList.addAll(getCurrencyExchangeRates());
//        if(selectedRowObject != null && dataList.contains(selectedRowObject)) {
//            dataTable.setSelectedRowObject(selectedRowObject);
//        }
//    }

    protected BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
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

    public class RefreshTask
        extends Task<Object, Void>
    {
        RefreshTask(Application app)
        {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to RefreshActionTask fields, here.
            super(app);
            log.debug("RefreshActionTask()");
        }

        @Override
        protected Object doInBackground()
        {
            log.info("doInBackground().begin");
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            AcaciaTable dataTable = getDataTable();
            Object selectedRowObject = dataTable.getSelectedRowObject();
            List dataList = dataTable.getData();
            dataList.clear();
            dataList.addAll(getCurrencyExchangeRates());
            if(selectedRowObject != null && dataList.contains(selectedRowObject)) {
                dataTable.setSelectedRowObject(selectedRowObject);
            }
            log.info("doInBackground().end");
            return null;  // return your result
        }

        @Override
        protected void succeeded(Object result)
        {
            log.info("succeeded(Result:" + result + ")");
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }
}
