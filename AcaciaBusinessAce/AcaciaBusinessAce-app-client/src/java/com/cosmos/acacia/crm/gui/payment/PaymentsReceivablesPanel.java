/*
 * PaymentsReceivablesPanel.java
 *
 * Created on Събота, 2009, Февруари 21, 13:40
 */

package com.cosmos.acacia.crm.gui.payment;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cosmos.acacia.crm.bl.contactbook.BusinessPartnersListRemote;
import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.CustomerPayment;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.enums.CustomerPaymentStatus;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnersListPanel;
import com.cosmos.acacia.crm.gui.invoice.InvoiceListPanel;
import com.cosmos.acacia.gui.AbstractTablePanelListener;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBColumn;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.listeners.TableModificationListener;

/**
 * 
 * Created	:	11.03.2009
 * @author	Petar Milev
 *
 */
public class PaymentsReceivablesPanel extends AcaciaPanel {
    
    protected BusinessPartnersListRemote businessPartnersManager = 
        getBean(BusinessPartnersListRemote.class);
    protected InvoiceListRemote invoicesManager = getBean(InvoiceListRemote.class);
    private Map<BusinessPartner, BigDecimal> liabilityMap;
    private Map<BusinessPartner, BigDecimal> prepaidMap;
    
    /** Creates new form PaymentsReceivablesPanel */
    public PaymentsReceivablesPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        initComponents();
        initComponentsCustom();
    }

    private void initComponentsCustom() {
        //Customer Payments Panel
        CustomerPaymentListPanel customerPaymentListPanel = new CustomerPaymentListPanel(getParentDataObjectId());
        customerPaymentListPanel.addTablePanelListener(new AbstractTablePanelListener() {
            @Override
            public void tablePanelClose() {
                PaymentsReceivablesPanel.this.close();
            }
        });
        addToPanel(paymentsPanel, customerPaymentListPanel);
        
        //Liability Customers Panel
        final BusinessPartnersListPanel liabilityCustomersListPanel = 
            createLiabilityCustomersPanel();
        addToPanel(liabilityClientsPanel, liabilityCustomersListPanel);
        
        //Prepaid Customers Panel
        final BusinessPartnersListPanel prepaidCustomersListPanel = 
            createPrepaidCustomersListPanel();
        addToPanel(prepaidClientsPanel, prepaidCustomersListPanel);
        
        customerPaymentListPanel.addTableModificationListener(new TableModificationListener() {
            public void rowModified(Object row) {
                CustomerPayment cp = (CustomerPayment) row;
                if ( CustomerPaymentStatus.Completed.equals(cp.getStatus().getEnumValue()) )
                    refreshTables(liabilityCustomersListPanel, prepaidCustomersListPanel);
            }
            public void rowDeleted(Object row) {
            }
            public void rowAdded(Object row) {
                CustomerPayment cp = (CustomerPayment) row;
                if ( CustomerPaymentStatus.Completed.equals(cp.getStatus().getEnumValue()) )
                    refreshTables(liabilityCustomersListPanel, prepaidCustomersListPanel);
            }
        });
    }

    protected void refreshTables(BusinessPartnersListPanel liabilityCustomersListPanel, BusinessPartnersListPanel prepaidCustomersListPanel) {
        liabilityCustomersListPanel.setBusinessPartnersStaticList(getLiabilityCustomers());
        prepaidCustomersListPanel.setBusinessPartnersStaticList(getPrepaidCustomers());
    }

    private BusinessPartnersListPanel createPrepaidCustomersListPanel() {
        List<BusinessPartner> prepaidCustomers = getPrepaidCustomers();
        List<JBColumn> customColumns = createAmmountCustomColumns(false, "column.prepaidAmount");
        final BusinessPartnersListPanel prepaidCustomersListPanel = new BusinessPartnersListPanel(prepaidCustomers, customColumns);
        prepaidCustomersListPanel.setClassifierVisible(false);
        setButtonsVisibility(prepaidCustomersListPanel);
        prepaidCustomersListPanel.addTablePanelListener(new AbstractTablePanelListener() {
            @Override
            public void tablePanelClose() {
                PaymentsReceivablesPanel.this.close();
            }
            @Override
            public void tableRefreshed() {
                prepaidCustomersListPanel.setBusinessPartnersStaticList(getPrepaidCustomers());
            }
        });
        return prepaidCustomersListPanel;
    }
    
    private static class AmountColumn extends JBColumn{
        private Map<BusinessPartner, BigDecimal> amounts;
        public AmountColumn(Map<BusinessPartner, BigDecimal> amounts, String columnDisplay, int colIdx){
            super(columnDisplay, BigDecimal.class, colIdx);
            this.amounts = amounts;
        }
        public void setValue(Object arg0, Object arg1) {}
        public Object getValue(Object item) {
            BusinessPartner partner = (BusinessPartner) item;
            BigDecimal balance = amounts.get(partner);
            if (balance!=null && balance.compareTo(BigDecimal.ZERO)<0)
                balance = balance.negate();
            return balance;
        }
        public void setAmounts(Map<BusinessPartner, BigDecimal> amounts) {
            this.amounts = amounts;
        }
    }
    
    private AmountColumn liabilityColumn;
    private AmountColumn prepaidColumn;

    private List<JBColumn> createAmmountCustomColumns(boolean liablity , String columnNameKey) {
        String colDisplay = getResourceMap().getString(columnNameKey);
        List<JBColumn> result = new ArrayList<JBColumn>();
        if ( liablity ){
            liabilityColumn = new AmountColumn(liabilityMap, colDisplay, 1);
            result.add(liabilityColumn);
        }else{
            prepaidColumn = new AmountColumn(prepaidMap, colDisplay, 1);
            result.add(prepaidColumn);
        }
        return result;
    }

    private List<BusinessPartner> getPrepaidCustomers() {
        this.prepaidMap = businessPartnersManager.getPrepaidCustomers();
        if ( prepaidColumn!=null )
            prepaidColumn.setAmounts(prepaidMap);
        return new ArrayList<BusinessPartner>(this.prepaidMap.keySet());
    }

    private BusinessPartnersListPanel createLiabilityCustomersPanel() {
        //Liability Customers Panel
        List<BusinessPartner> liabilityCustomers = getLiabilityCustomers();
        List<JBColumn> customColumns = createAmmountCustomColumns(true, "column.dueAmount");
        final BusinessPartnersListPanel liabilityCustomersListPanel = new BusinessPartnersListPanel(liabilityCustomers, customColumns);
        liabilityCustomersListPanel.setClassifierVisible(false);
        setButtonsVisibility(liabilityCustomersListPanel);
        liabilityCustomersListPanel.addTablePanelListener(new AbstractTablePanelListener() {
            @Override
            public void tablePanelClose() {
                PaymentsReceivablesPanel.this.close();
            }
            @Override
            public void tableRefreshed() {
                liabilityCustomersListPanel.setBusinessPartnersStaticList(getLiabilityCustomers());
            }
        });
        createDueComponents(liabilityCustomersListPanel);
        return liabilityCustomersListPanel;
    }

    private void createDueComponents(final BusinessPartnersListPanel customersListPanel) {
        JBButton dueDocumentsButton = customersListPanel.getButtonsMap().get(Button.Special);
        dueDocumentsButton.setText(getResourceMap().getString("button.dueDocuments"));
        customersListPanel.setSpecialAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onLiabilityDocumentsButton(customersListPanel);
            }
        });
    }

    protected void onLiabilityDocumentsButton(BusinessPartnersListPanel customersListPanel) {
        if ( customersListPanel.getDataTable().getSelectedRowObject() instanceof BusinessPartner ){
            List<Invoice> dueDocuments = 
                invoicesManager.getDueDocuments((BusinessPartner) customersListPanel.getDataTable().getSelectedRowObject());
            InvoiceListPanel dueInvoicesPanel = new InvoiceListPanel(getParentDataObjectId(), dueDocuments, false);
            dueInvoicesPanel.setReadonly();
            dueInvoicesPanel.setVisibleSelectButtons(false);
            dueInvoicesPanel.showDialog(this);
        }
    }

    private void setButtonsVisibility(BusinessPartnersListPanel businessPartnersListPanel) {
        businessPartnersListPanel.setVisible(Button.Classify, false);
        businessPartnersListPanel.setVisible(Button.New, false);
        businessPartnersListPanel.setVisible(Button.Modify, false);
        businessPartnersListPanel.setVisible(Button.Delete, false);
    }

    private List<BusinessPartner> getLiabilityCustomers() {
        this.liabilityMap = businessPartnersManager.getLiabilityCustomers();
        if ( liabilityColumn!=null )
            liabilityColumn.setAmounts(liabilityMap);
        return new ArrayList<BusinessPartner>(this.liabilityMap.keySet());
    }

    private void addToPanel(JBPanel holderPanel,
                            JBPanel targetPanel) {
        if ( !(holderPanel.getLayout() instanceof GridLayout) )
            holderPanel.setLayout(new GridLayout());
            
        holderPanel.add(targetPanel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBTabbedPane1 = new com.cosmos.swingb.JBTabbedPane();
        paymentsPanel = new com.cosmos.swingb.JBPanel();
        liabilityClientsPanel = new com.cosmos.swingb.JBPanel();
        prepaidClientsPanel = new com.cosmos.swingb.JBPanel();

        setName("Form"); // NOI18N

        jBTabbedPane1.setName("jBTabbedPane1"); // NOI18N

        paymentsPanel.setName("paymentsPanel"); // NOI18N

        javax.swing.GroupLayout paymentsPanelLayout = new javax.swing.GroupLayout(paymentsPanel);
        paymentsPanel.setLayout(paymentsPanelLayout);
        paymentsPanelLayout.setHorizontalGroup(
            paymentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 945, Short.MAX_VALUE)
        );
        paymentsPanelLayout.setVerticalGroup(
            paymentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PaymentsReceivablesPanel.class);
        jBTabbedPane1.addTab(resourceMap.getString("paymentsPanel.TabConstraints.tabTitle"), paymentsPanel); // NOI18N

        liabilityClientsPanel.setName("liabilityClientsPanel"); // NOI18N

        javax.swing.GroupLayout liabilityClientsPanelLayout = new javax.swing.GroupLayout(liabilityClientsPanel);
        liabilityClientsPanel.setLayout(liabilityClientsPanelLayout);
        liabilityClientsPanelLayout.setHorizontalGroup(
            liabilityClientsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 945, Short.MAX_VALUE)
        );
        liabilityClientsPanelLayout.setVerticalGroup(
            liabilityClientsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        jBTabbedPane1.addTab(resourceMap.getString("liabilityClientsPanel.TabConstraints.tabTitle"), liabilityClientsPanel); // NOI18N

        prepaidClientsPanel.setName("prepaidClientsPanel"); // NOI18N

        javax.swing.GroupLayout prepaidClientsPanelLayout = new javax.swing.GroupLayout(prepaidClientsPanel);
        prepaidClientsPanel.setLayout(prepaidClientsPanelLayout);
        prepaidClientsPanelLayout.setHorizontalGroup(
            prepaidClientsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 945, Short.MAX_VALUE)
        );
        prepaidClientsPanelLayout.setVerticalGroup(
            prepaidClientsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        jBTabbedPane1.addTab(resourceMap.getString("prepaidClientsPanel.TabConstraints.tabTitle"), prepaidClientsPanel); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );

        jBTabbedPane1.getAccessibleContext().setAccessibleName(resourceMap.getString("jBTabbedPane1.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBTabbedPane jBTabbedPane1;
    private com.cosmos.swingb.JBPanel liabilityClientsPanel;
    private com.cosmos.swingb.JBPanel paymentsPanel;
    private com.cosmos.swingb.JBPanel prepaidClientsPanel;
    // End of variables declaration//GEN-END:variables
    @Override
    protected void initData() {
    }

}
