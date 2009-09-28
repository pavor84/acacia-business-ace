/*
 * PaymentMatchingPanel.java
 *
 * Created on Четвъртък, 2009, Февруари 26, 17:28
 */

package com.cosmos.acacia.crm.gui.payment;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.bl.payment.CustomerPaymentRemote;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.CustomerPayment;
import com.cosmos.acacia.crm.data.CustomerPaymentMatch;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.gui.AbstractTablePanelListener;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;

/**
 * 
 * Created	:	22.03.2009
 * @author	Petar Milev
 *
 */
public class PaymentMatchingPanel extends AcaciaPanel {
    
    private CustomerPaymentRemote formSession = getBean(CustomerPaymentRemote.class);
    private InvoiceListRemote invoiceManager = getBean(InvoiceListRemote.class);

    /** Creates new form PaymentMatchingPanel 
     * @param UUID */
    public PaymentMatchingPanel(UUID parentId) {
        super(parentId);
        initComponents();
        initComponentsCustom();
        initData();
        afterBind();
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
        jBPanel1 = new com.cosmos.swingb.JBPanel();
        jPanel3 = new javax.swing.JPanel();
        pendingPaymentsLabel = new javax.swing.JLabel();
        paymentsParentPanel = new com.cosmos.swingb.JBPanel();
        pendingPaymentsField = new javax.swing.JComboBox();
        jBPanel3 = new com.cosmos.swingb.JBPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pendingDocumentsTable = new com.cosmos.acacia.gui.AcaciaTable();
        matchAmountButton = new com.cosmos.swingb.JBButton();
        pendingDocsLabel = new javax.swing.JLabel();
        pendingDocsField = new javax.swing.JComboBox();
        jBPanel2 = new com.cosmos.swingb.JBPanel();
        jBPanel4 = new com.cosmos.swingb.JBPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        paymentHistoryTable = new com.cosmos.acacia.gui.AcaciaTable();
        viewPaymentButton = new com.cosmos.swingb.JBButton();
        closeButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        jBTabbedPane1.setName("jBTabbedPane1"); // NOI18N

        jBPanel1.setName("jBPanel1"); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Unmatched Payments"));
        jPanel3.setName("jPanel3"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PaymentMatchingPanel.class);
        pendingPaymentsLabel.setText(resourceMap.getString("pendingPaymentsLabel.text")); // NOI18N
        pendingPaymentsLabel.setName("pendingPaymentsLabel"); // NOI18N

        paymentsParentPanel.setName("paymentsParentPanel"); // NOI18N

        javax.swing.GroupLayout paymentsParentPanelLayout = new javax.swing.GroupLayout(paymentsParentPanel);
        paymentsParentPanel.setLayout(paymentsParentPanelLayout);
        paymentsParentPanelLayout.setHorizontalGroup(
            paymentsParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 824, Short.MAX_VALUE)
        );
        paymentsParentPanelLayout.setVerticalGroup(
            paymentsParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
        );

        pendingPaymentsField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pendingPaymentsField.setName("pendingPaymentsField"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paymentsParentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(pendingPaymentsLabel)
                        .addGap(26, 26, 26)
                        .addComponent(pendingPaymentsField, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pendingPaymentsLabel)
                    .addComponent(pendingPaymentsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paymentsParentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jBPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Invoices/Proformas to Match"));
        jBPanel3.setName("jBPanel3"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        pendingDocumentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"12341234234", "19.02.2009", "120", "80", "80"},
                {"234234234", "19.02.2009", "200", "200", "200"},
                {"23423423434", "19.02.2009", "1000", "600", "150"}
            },
            new String [] {
                "Doc. Number", "Doc. Date", "Total Amount", "Paid Amount", "Due Amount"
            }
        ));
        pendingDocumentsTable.setName("pendingDocumentsTable"); // NOI18N
        jScrollPane2.setViewportView(pendingDocumentsTable);
        pendingDocumentsTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("pendingDocumentsTable.columnModel.title0")); // NOI18N
        pendingDocumentsTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("pendingDocumentsTable.columnModel.title1")); // NOI18N
        pendingDocumentsTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("pendingDocumentsTable.columnModel.title2")); // NOI18N
        pendingDocumentsTable.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("pendingDocumentsTable.columnModel.title3")); // NOI18N
        pendingDocumentsTable.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("pendingDocumentsTable.columnModel.title4")); // NOI18N

        matchAmountButton.setText(resourceMap.getString("matchAmountButton.text")); // NOI18N
        matchAmountButton.setName("matchAmountButton"); // NOI18N

        pendingDocsLabel.setText(resourceMap.getString("pendingDocsLabel.text")); // NOI18N
        pendingDocsLabel.setName("pendingDocsLabel"); // NOI18N

        pendingDocsField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pendingDocsField.setName("pendingDocsField"); // NOI18N

        javax.swing.GroupLayout jBPanel3Layout = new javax.swing.GroupLayout(jBPanel3);
        jBPanel3.setLayout(jBPanel3Layout);
        jBPanel3Layout.setHorizontalGroup(
            jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(matchAmountButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jBPanel3Layout.createSequentialGroup()
                        .addComponent(pendingDocsLabel)
                        .addGap(26, 26, 26)
                        .addComponent(pendingDocsField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jBPanel3Layout.setVerticalGroup(
            jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pendingDocsLabel)
                    .addComponent(pendingDocsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(matchAmountButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jBPanel1Layout = new javax.swing.GroupLayout(jBPanel1);
        jBPanel1.setLayout(jBPanel1Layout);
        jBPanel1Layout.setHorizontalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jBPanel1Layout.setVerticalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jBTabbedPane1.addTab(resourceMap.getString("jBPanel1.TabConstraints.tabTitle"), jBPanel1); // NOI18N

        jBPanel2.setName("jBPanel2"); // NOI18N

        jBPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Invioce/Proforma Payment History"));
        jBPanel4.setName("jBPanel4"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        paymentHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Match Amount", "Match Number", "Match Date", "Payment Number", "Payment Date"
            }
        ));
        paymentHistoryTable.setName("paymentHistoryTable"); // NOI18N
        jScrollPane3.setViewportView(paymentHistoryTable);
        paymentHistoryTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("paymentHistoryTable.columnModel.title0")); // NOI18N
        paymentHistoryTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("paymentHistoryTable.columnModel.title1")); // NOI18N
        paymentHistoryTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("paymentHistoryTable.columnModel.title2")); // NOI18N
        paymentHistoryTable.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("paymentHistoryTable.columnModel.title3")); // NOI18N
        paymentHistoryTable.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("paymentHistoryTable.columnModel.title4")); // NOI18N

        javax.swing.GroupLayout jBPanel4Layout = new javax.swing.GroupLayout(jBPanel4);
        jBPanel4.setLayout(jBPanel4Layout);
        jBPanel4Layout.setHorizontalGroup(
            jBPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                .addContainerGap())
        );
        jBPanel4Layout.setVerticalGroup(
            jBPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addContainerGap())
        );

        viewPaymentButton.setText(resourceMap.getString("viewPaymentButton.text")); // NOI18N
        viewPaymentButton.setName("viewPaymentButton"); // NOI18N

        javax.swing.GroupLayout jBPanel2Layout = new javax.swing.GroupLayout(jBPanel2);
        jBPanel2.setLayout(jBPanel2Layout);
        jBPanel2Layout.setHorizontalGroup(
            jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(viewPaymentButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jBPanel2Layout.setVerticalGroup(
            jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewPaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jBTabbedPane1.addTab(resourceMap.getString("jBPanel2.TabConstraints.tabTitle"), jBPanel2); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(PaymentMatchingPanel.class, this);
        closeButton.setAction(actionMap.get("closeAction")); // NOI18N
        closeButton.setIcon(resourceMap.getIcon("closeButton.icon")); // NOI18N
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setToolTipText(resourceMap.getString("closeButton.toolTipText")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(790, Short.MAX_VALUE)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jBTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton closeButton;
    private com.cosmos.swingb.JBPanel jBPanel1;
    private com.cosmos.swingb.JBPanel jBPanel2;
    private com.cosmos.swingb.JBPanel jBPanel3;
    private com.cosmos.swingb.JBPanel jBPanel4;
    private com.cosmos.swingb.JBTabbedPane jBTabbedPane1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.cosmos.swingb.JBButton matchAmountButton;
    private com.cosmos.acacia.gui.AcaciaTable paymentHistoryTable;
    private com.cosmos.swingb.JBPanel paymentsParentPanel;
    private javax.swing.JComboBox pendingDocsField;
    private javax.swing.JLabel pendingDocsLabel;
    private com.cosmos.acacia.gui.AcaciaTable pendingDocumentsTable;
    private javax.swing.JComboBox pendingPaymentsField;
    private javax.swing.JLabel pendingPaymentsLabel;
    private com.cosmos.swingb.JBButton viewPaymentButton;
    // End of variables declaration//GEN-END:variables
    
    private String paymentsAll;
    private String paymentsPartlyMatched;
    private String paymentsUnmatched;
    
    private String docsAll;
    private String docsPartlyPaid;
    private String docsUnpaid;
    private CustomerPaymentListPanel paymentListPanel;
    
    private void initComponentsCustom() {
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }
    
    @Override
    protected void initData() {
        //pending payments field
        paymentsAll = getResourceMap().getString("payments.all");
        paymentsPartlyMatched = getResourceMap().getString("payments.partlymatched");
        paymentsUnmatched = getResourceMap().getString("payments.unmatched");
        pendingPaymentsField.setModel(new DefaultComboBoxModel(new Object[]{paymentsAll, paymentsPartlyMatched, paymentsUnmatched}));
        pendingPaymentsField.setSelectedIndex(0);
        pendingPaymentsField.setEditable(false);
        
        //pending documents field
        docsAll = getResourceMap().getString("docs.all");
        docsPartlyPaid = getResourceMap().getString("docs.partlypaid");
        docsUnpaid = getResourceMap().getString("docs.unpaid");
        pendingDocsField.setModel(new DefaultComboBoxModel(new Object[]{docsAll, docsPartlyPaid, docsUnpaid}));
        pendingDocsField.setSelectedIndex(0);
        pendingDocsField.setEditable(false);
    }

    private List<CustomerPayment> getPaymentsList() {
        String paymentsFilterValue = (String) pendingPaymentsField.getSelectedItem();
        boolean includePartlyMatched = paymentsAll.equals(paymentsFilterValue) 
            || paymentsPartlyMatched.equals(paymentsFilterValue);
        boolean includeUnmatched = paymentsAll.equals(paymentsFilterValue) 
            || paymentsUnmatched.equals(paymentsFilterValue);
        return formSession.getPendingPayments(includePartlyMatched, includeUnmatched);
    }
    
    private List<Invoice> getInvoiceList(BusinessPartner customer) {
        String invFilterValue = (String) pendingDocsField.getSelectedItem();
        boolean includedPartlyPaid = docsAll.equals(invFilterValue) 
            || docsPartlyPaid.equals(invFilterValue);
        boolean includeUnpaid = paymentsAll.equals(invFilterValue) 
            || docsUnpaid.equals(invFilterValue);
        return invoiceManager.getPendingInvoices(customer, includedPartlyPaid, includeUnpaid);
    }

    protected void onPaymentsFilterChanged() {
        paymentListPanel.refreshList(getPaymentsList());
    }
    
    protected void onDocumentsFilterChanged() {
        refreshInvoicesTable(getInvoiceList());
    }
    
    private void afterBind() {
        pendingPaymentsField.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if ( e.getStateChange()==ItemEvent.SELECTED )
                    onPaymentsFilterChanged();
            }
        });
        
        pendingDocsField.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if ( e.getStateChange()==ItemEvent.SELECTED )
                    onDocumentsFilterChanged();
            }
        });
        
        EntityProperties entityProperties = getPaymentListEntityProperties();
        paymentListPanel = new CustomerPaymentListPanel(getParentDataObjectId(), getPaymentsList(), entityProperties);
        paymentListPanel.setVisibleButtons(0);
        paymentsParentPanel.setLayout(new GridLayout());
        paymentsParentPanel.add(paymentListPanel);
        paymentListPanel.addTablePanelListener(new AbstractTablePanelListener() {
            public void selectionRowChanged() {
                onPaymentSelect();
                updateMatchButton();
            }
        });
        
        pendingDocumentsTable.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() ){
                    onInvoiceSelect();
                    updateMatchButton();
                }
            }
        });
        
        refreshInvoicesTable(new ArrayList<Invoice>());
        
        matchAmountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onMatchAmount();
            }
        });
        
        paymentHistoryTable.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() )
                    updateViewPaymentButton();
            }
        });
        
        refreshPaymentHistoryTable(new ArrayList<CustomerPaymentMatch>());
        
        viewPaymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onViewPaymentButton();
            }
        });
        
        updateMatchButton();
        updateViewPaymentButton();
    }

    private EntityProperties getPaymentListEntityProperties() {
        EntityProperties entProps = formSession.getListingEntityProperties();
        entProps.setOrderPosition("creationTime", 1000);
        
        EntityProperty unmatchedAmountPD = EntityProperty.createEntityProperty("unmatchedAmount",
            getResourceMap().getString("column.unmatchedamount"), BigDecimal.class.getName(), 35);
        entProps.addEntityProperty(unmatchedAmountPD);
        return entProps;
    }

    protected void onViewPaymentButton() {
        CustomerPaymentMatch match = (CustomerPaymentMatch) paymentHistoryTable.getSelectedRowObject();
        if ( match==null )
            return;

        CustomerPaymentForm form = new CustomerPaymentForm(match.getCustomerPayment());
        form.setReadonly();
        form.showDialog(this);
    }

    protected void onMatchAmount() {
        Invoice i = (Invoice) pendingDocumentsTable.getSelectedRowObject();
        CustomerPayment p = (CustomerPayment) paymentListPanel.getDataTable().getSelectedRowObject();
        
        BigDecimal minAmount = BigDecimal.ZERO;
        BigDecimal maxAmount = i.getDueAmount();
        if ( maxAmount.compareTo(p.getUnmatchedAmount())>0 ){
            maxAmount = p.getUnmatchedAmount();
        }
        
        MatchAmountForm matchAmountForm = new MatchAmountForm(minAmount, maxAmount, maxAmount);
        DialogResponse resp = matchAmountForm.showDialog(this);
        if ( DialogResponse.SAVE.equals(resp) ){
            BigDecimal matchAmount = matchAmountForm.getMatchAmount();
            if ( matchAmount.compareTo(BigDecimal.ZERO)>0 )
                createMatchingAndRefresh(p,i,matchAmount);
        }
    }

    private void createMatchingAndRefresh(CustomerPayment p, Invoice i, BigDecimal matchAmount) {
        CustomerPaymentMatch match = formSession.matchPayment(p, i, matchAmount);
        refreshTables(match.getCustomerPayment(), match.getInvoice());
    }

    private void refreshTables(CustomerPayment customerPayment, Invoice invoice) {
        paymentListPanel.refreshList(getPaymentsList());
        
        if ( paymentListPanel.getDataTable().getData().contains(customerPayment) ){
            paymentListPanel.getDataTable().setSelectedRowObject(customerPayment);
        }
        
        if ( pendingDocumentsTable.getData().contains(invoice)){
            pendingDocumentsTable.setSelectedRowObject(invoice);
        }
        
        if  (paymentListPanel.getDataTable().getData().contains(customerPayment)){
            paymentListPanel.getDataTable().setSelectedRowObject(customerPayment);
            if ( pendingDocumentsTable.getData().contains(invoice) )
                pendingDocumentsTable.setSelectedRowObject(invoice);
        }
    }

    protected void updateMatchButton() {
        boolean paymentSelected = paymentListPanel.getDataTable().getSelectedRow()>=0;
        boolean invoiceSelected = pendingDocumentsTable.getSelectedRow()>=0;
        matchAmountButton.setEnabled(paymentSelected && invoiceSelected);
    }
    
    protected void updateViewPaymentButton() {
        boolean matchSelected = paymentHistoryTable.getSelectedRow()>=0;
        viewPaymentButton.setEnabled(matchSelected);
    }

    private List<EntityProperty> createInvoiceListColumns() {
        List<EntityProperty> result = new ArrayList<EntityProperty>();
        result.add(EntityProperty.createEntityProperty("invoiceNumber", getResourceMap().getString("column.docnumber"), BigInteger.class.getName(), 10));
        result.add(EntityProperty.createEntityProperty("creationTime", getResourceMap().getString("column.docdate"), Date.class.getName(), 20));
        result.add(EntityProperty.createEntityProperty("totalValue", getResourceMap().getString("column.totalamount"), BigDecimal.class.getName(), 30));
        result.add(EntityProperty.createEntityProperty("paidAmount", getResourceMap().getString("column.paidamount"), BigDecimal.class.getName(), 40));
        result.add(EntityProperty.createEntityProperty("dueAmount", getResourceMap().getString("column.dueamount"), BigDecimal.class.getName(), 50));
//        int position = 0;
//        for (EntityProperty propertyDetails : result) {
//            position +=10;
//            propertyDetails.setOrderPosition(position);
//        }
        return result;
    }
    
    private List<EntityProperty> createPaymentHistoryColumns() {
        List<EntityProperty> result = new ArrayList<EntityProperty>();
        result.add(EntityProperty.createEntityProperty("amount", getResourceMap().getString("column.matchAmount"), BigDecimal.class.getName(),10));
        result.add(EntityProperty.createEntityProperty("matchNumber", getResourceMap().getString("column.matchNumber"), Integer.class.getName(),20));
        result.add(EntityProperty.createEntityProperty("creationTime", getResourceMap().getString("column.matchDate"), Date.class.getName(),30));
        result.add(EntityProperty.createEntityProperty("customerPayment.documentNumber", getResourceMap().getString("column.paymentNumber"), BigInteger.class.getName(),40));
        result.add(EntityProperty.createEntityProperty("customerPayment.completionTime", getResourceMap().getString("column.paymentDate"), Date.class.getName(),50));
        return result;
    }

    protected void onPaymentSelect() {
        //update invoices
        refreshInvoicesTable(getInvoiceList());
    }
    
    protected void onInvoiceSelect() {
        refreshPaymentHistoryTable(getPaymentMatchList());
    }
    
    private List<Invoice> getInvoiceList() {
        List<Invoice> pendingInvoices = null;
        CustomerPayment customerPayment = (CustomerPayment) paymentListPanel.getDataTable().getSelectedRowObject();
        if ( customerPayment==null )
            pendingInvoices = new ArrayList<Invoice>();
        else{
            pendingInvoices = getInvoiceList(customerPayment.getCustomer());
        }
        return pendingInvoices;
    }
    
    private List<CustomerPaymentMatch> getPaymentMatchList() {
        List<CustomerPaymentMatch> paymentMatches = null;
        Invoice invoice = (Invoice) pendingDocumentsTable.getSelectedRowObject();
        if ( invoice==null )
            paymentMatches = new ArrayList<CustomerPaymentMatch>();
        else{
            paymentMatches = formSession.getPaymentMatchList(invoice);
        }
        return paymentMatches;
    }

    private BindingGroup pendingDocumentsBindingGroup;

    private void refreshInvoicesTable(List<Invoice> pendingInvoices) {
        
        if ( pendingDocumentsBindingGroup!=null ){
            pendingDocumentsBindingGroup.unbind();
        }
        pendingDocumentsBindingGroup = new BindingGroup();
        
        List<EntityProperty> pd = createInvoiceListColumns();
        EntityProperties ep = new EntityProperties(Object.class);
        ep.addEntityProperties(pd);
        
        pendingDocumentsTable.bind(pendingDocumentsBindingGroup, pendingInvoices, 
            ep, UpdateStrategy.READ);
        
        pendingDocumentsBindingGroup.bind();
    }
    
    private BindingGroup paymentHistoryBindingGroup;
    
    private void refreshPaymentHistoryTable(List<CustomerPaymentMatch> matchHistory) {
        if ( paymentHistoryBindingGroup!=null ){
            paymentHistoryBindingGroup.unbind();
        }
        paymentHistoryBindingGroup = new BindingGroup();

        EntityProperties entityProperties = new EntityProperties(Object.class);
        entityProperties.addEntityProperties(createPaymentHistoryColumns());
        paymentHistoryTable.bind(paymentHistoryBindingGroup, matchHistory, entityProperties, UpdateStrategy.READ);
        
        paymentHistoryBindingGroup.bind();
    }
}
