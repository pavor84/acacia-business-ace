/*
 * InvoiceTabFormDraft.java
 *
 * Created on Сряда, 2008, Август 20, 21:53
 */
package com.cosmos.acacia.crm.gui.invoice;

import static com.cosmos.acacia.util.AcaciaUtils.getDecimalFormat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.bl.reports.DocumentUtilRemote;
import com.cosmos.acacia.crm.bl.reports.PrintableDocumentHeader;
import com.cosmos.acacia.crm.bl.reports.Report;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.crm.data.sales.InvoiceItem;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.crm.enums.InvoiceType;
import com.cosmos.acacia.crm.enums.PaymentTerm;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnersListPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AbstractTablePanelListener;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel.Button;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.acacia.util.ResourceMapUtil;
import com.cosmos.acacia.util.numbers.NumberNamesAlgorithm;
import com.cosmos.acacia.util.numbers.NumberNamesAlgorithmFactory;
import com.cosmos.acacia.util.numbers.NumberNamesAlgorithmType;
import com.cosmos.acacia.util.numbers.UnsupportedNumberAlgorithmException;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.beansbinding.validation.BaseValidator;
import com.cosmos.beansbinding.validation.RequiredValidator;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.listeners.TableModificationListener;

/**
 * Created : 10.09.2008
 *
 * @author Petar Milev
 */
public class InvoiceForm extends BaseEntityPanel {

    protected static Logger log = Logger.getLogger(InvoiceForm.class);
    private Invoice entity;
    //remember if the current document is proforma invoice or invoice; For easier re-use
    private boolean proforma;
    private EntityProperties entProps;
    private BindingGroup bindingGroup;
    private InvoiceListRemote formSession = getBean(InvoiceListRemote.class);

    /** Creates new form InvoiceFormDraft */
    public InvoiceForm(Invoice confirmation) {
        super(confirmation.getParentId());
        this.entity = confirmation;
        this.proforma = Boolean.TRUE.equals(entity.getProformaInvoice());
        initialize();
    }

    private void initialize() {
        initComponents();
        initComponentsCustom();
        init();
    }

    private void initComponentsCustom() {
        if (Boolean.TRUE.equals(entity.getProformaInvoice())) {
            setTitle(getResourceMap().getString("Form.title.proforma"));
            jBTabbedPane1.setTitleAt(0, getResourceMap().getString("proforma.TabConstraints.tabTitle"));
            jBTabbedPane1.setTitleAt(1, getResourceMap().getString("proformaItems.TabConstraints.tabTitle"));
            jBLabel7.setText(getResourceMap().getString("jBLabel7.text.proform"));
            jBLabel14.setText(getResourceMap().getString("jBLabel14.text.proform"));
            jBLabel26.setText(getResourceMap().getString("jBLabel26.text.proform"));
            ((TitledBorder) itemsTableHolderPanel.getBorder()).setTitle(getResourceMap().getString("itemsTableHolderPanel.border.title.proform"));
        }

        // Using an AbstractTablePanel implementation
        itemsTablePanel = new InvoiceItemListPanel(entity.getInvoiceId(), entity);

        itemsTablePanel.addTableModificationListener(new TableModificationListener() {

            @Override
            public void rowModified(Object row) {
                onItemsTableChange();
            }

            @Override
            public void rowDeleted(Object row) {
                onItemsTableChange();
            }

            @Override
            public void rowAdded(Object row) {
                onItemsTableChange();
            }
        });

        additionalDetailsPanel.setVisible(proforma);

        itemsTablePanel.addTablePanelListener(new AbstractTablePanelListener() {

            @Override
            public void tableRefreshed() {
                onItemsTableChange();
            }
        });

        // Adding the nested table listener to ensure that purchase order is
        // saved before adding
        // items to it.
        addNestedFormListener(itemsTablePanel);

        itemsTableHolderPanel.add(itemsTablePanel);

        dueDocumentsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onDueDocuments();
            }
        });
        recipientDueField.setHorizontalAlignment(JTextField.TRAILING);

        vatValueField.setFormat(getDecimalFormat());
        exciseValueField.setFormat(getDecimalFormat());
    }

    protected void onDueDocuments() {
        InvoiceListPanel dueInvoicesPanel = new InvoiceListPanel(getParentDataObjectId(), recipientDueDocuments, false);
        dueInvoicesPanel.setReadonly();
        dueInvoicesPanel.setVisibleSelectButtons(false);
        dueInvoicesPanel.showDialog(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBTabbedPane1 = new com.cosmos.swingb.JBTabbedPane();
        jBPanel1 = new com.cosmos.swingb.JBPanel();
        jBPanel2 = new com.cosmos.swingb.JBPanel();
        branchField = new com.cosmos.swingb.JBTextField();
        jBLabel6 = new com.cosmos.swingb.JBLabel();
        invoiceNumberField = new com.cosmos.swingb.JBTextField();
        jBLabel7 = new com.cosmos.swingb.JBLabel();
        documentDateField = new com.cosmos.swingb.JBDatePicker();
        jBLabel8 = new com.cosmos.swingb.JBLabel();
        recipientField = new com.cosmos.acacia.gui.AcaciaComboList();
        jBLabel9 = new com.cosmos.swingb.JBLabel();
        recipientContactField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel10 = new com.cosmos.swingb.JBLabel();
        documentTypeField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel14 = new com.cosmos.swingb.JBLabel();
        jBLabel11 = new com.cosmos.swingb.JBLabel();
        createdByField = new com.cosmos.swingb.JBTextField();
        jBLabel12 = new com.cosmos.swingb.JBLabel();
        documentDeliveryField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel15 = new com.cosmos.swingb.JBLabel();
        statusField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel16 = new com.cosmos.swingb.JBLabel();
        jBLabel20 = new com.cosmos.swingb.JBLabel();
        sentByField = new com.cosmos.swingb.JBTextField();
        jBLabel21 = new com.cosmos.swingb.JBLabel();
        jBLabel22 = new com.cosmos.swingb.JBLabel();
        jBLabel13 = new com.cosmos.swingb.JBLabel();
        createdAtField = new com.cosmos.swingb.JBDatePicker();
        sentAtField = new com.cosmos.swingb.JBDatePicker();
        completedAtField = new com.cosmos.swingb.JBDatePicker();
        recipientDueField = new com.cosmos.swingb.JBTextField();
        jBLabel1 = new com.cosmos.swingb.JBLabel();
        dueDocumentsButton = new com.cosmos.swingb.JBButton();
        deliveryStatusField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel29 = new com.cosmos.swingb.JBLabel();
        jBPanel3 = new com.cosmos.swingb.JBPanel();
        shippingAgentField = new com.cosmos.acacia.gui.AcaciaComboList();
        jBLabel17 = new com.cosmos.swingb.JBLabel();
        transportMethodField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel18 = new com.cosmos.swingb.JBLabel();
        deliveryTypeField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel19 = new com.cosmos.swingb.JBLabel();
        jBLabel23 = new com.cosmos.swingb.JBLabel();
        jBLabel24 = new com.cosmos.swingb.JBLabel();
        shipDateFromField = new com.cosmos.swingb.JBDatePicker();
        shipDateToField = new com.cosmos.swingb.JBDatePicker();
        shipWeekField = new com.cosmos.swingb.JBTextField();
        jBLabel32 = new com.cosmos.swingb.JBLabel();
        jBPanel4 = new com.cosmos.swingb.JBPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notesField = new com.cosmos.swingb.JBTextPane();
        additionalDetailsPanel = new com.cosmos.swingb.JBPanel();
        jBLabel35 = new com.cosmos.swingb.JBLabel();
        validToField = new com.cosmos.swingb.JBDatePicker();
        jBLabel44 = new com.cosmos.swingb.JBLabel();
        jBLabel45 = new com.cosmos.swingb.JBLabel();
        attendeeField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        additionalTermsField = new com.cosmos.swingb.JBTextPane();
        jBPanel5 = new com.cosmos.swingb.JBPanel();
        jBPanel6 = new com.cosmos.swingb.JBPanel();
        paymentTypeField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel36 = new com.cosmos.swingb.JBLabel();
        jBLabel37 = new com.cosmos.swingb.JBLabel();
        paymentTermsField = new com.cosmos.acacia.gui.AcaciaComboBox();
        paymentDueField = new com.cosmos.swingb.JBDatePicker();
        jBLabel38 = new com.cosmos.swingb.JBLabel();
        jBLabel40 = new com.cosmos.swingb.JBLabel();
        jBLabel41 = new com.cosmos.swingb.JBLabel();
        jBLabel42 = new com.cosmos.swingb.JBLabel();
        singlePayAmountField = new com.cosmos.swingb.JBFormattedTextField();
        paymentsCountField = new com.cosmos.swingb.JBFormattedTextField();
        daysBetweenPaymentsField = new com.cosmos.swingb.JBFormattedTextField();
        jBPanel8 = new com.cosmos.swingb.JBPanel();
        currencyField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel25 = new com.cosmos.swingb.JBLabel();
        jBLabel26 = new com.cosmos.swingb.JBLabel();
        jBLabel27 = new com.cosmos.swingb.JBLabel();
        jBLabel28 = new com.cosmos.swingb.JBLabel();
        jBLabel30 = new com.cosmos.swingb.JBLabel();
        jBLabel31 = new com.cosmos.swingb.JBLabel();
        jBLabel33 = new com.cosmos.swingb.JBLabel();
        vatConditionField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel34 = new com.cosmos.swingb.JBLabel();
        jBLabel39 = new com.cosmos.swingb.JBLabel();
        jBLabel43 = new com.cosmos.swingb.JBLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        vatConditionNotesField = new com.cosmos.swingb.JBTextPane();
        invoiceSubValueField = new com.cosmos.swingb.JBFormattedTextField();
        transportPriceField = new com.cosmos.swingb.JBFormattedTextField();
        totalValueField = new com.cosmos.swingb.JBFormattedTextField();
        vatPercentField = new com.cosmos.swingb.JBFormattedTextField();
        vatValueField = new com.cosmos.swingb.JBFormattedTextField();
        excisePercentField = new com.cosmos.swingb.JBFormattedTextField();
        exciseValueField = new com.cosmos.swingb.JBFormattedTextField();
        itemsTableHolderPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();

        setName("Form"); // NOI18N

        jBTabbedPane1.setName("jBTabbedPane1"); // NOI18N

        jBPanel1.setName("jBPanel1"); // NOI18N

        jBPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Document Details"));
        jBPanel2.setName("jBPanel2"); // NOI18N

        branchField.setEditable(false);
        branchField.setName("branchField"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(InvoiceForm.class);
        jBLabel6.setText(resourceMap.getString("jBLabel6.text")); // NOI18N
        jBLabel6.setName("jBLabel6"); // NOI18N

        invoiceNumberField.setName("invoiceNumberField"); // NOI18N

        jBLabel7.setText(resourceMap.getString("jBLabel7.text")); // NOI18N
        jBLabel7.setName("jBLabel7"); // NOI18N

        documentDateField.setName("documentDateField"); // NOI18N

        jBLabel8.setName("jBLabel8"); // NOI18N

        recipientField.setName("recipientField"); // NOI18N

        jBLabel9.setText(resourceMap.getString("jBLabel9.text")); // NOI18N
        jBLabel9.setName("jBLabel9"); // NOI18N

        recipientContactField.setName("recipientContactField"); // NOI18N

        jBLabel10.setText(resourceMap.getString("jBLabel10.text")); // NOI18N
        jBLabel10.setName("jBLabel10"); // NOI18N

        documentTypeField.setName("documentTypeField"); // NOI18N

        jBLabel14.setText(resourceMap.getString("jBLabel14.text")); // NOI18N
        jBLabel14.setName("jBLabel14"); // NOI18N

        jBLabel11.setText(resourceMap.getString("jBLabel11.text")); // NOI18N
        jBLabel11.setName("jBLabel11"); // NOI18N

        createdByField.setEditable(false);
        createdByField.setName("createdByField"); // NOI18N
        createdByField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createdByFieldActionPerformed(evt);
            }
        });

        jBLabel12.setText(resourceMap.getString("jBLabel12.text")); // NOI18N
        jBLabel12.setName("jBLabel12"); // NOI18N

        documentDeliveryField.setName("documentDeliveryField"); // NOI18N

        jBLabel15.setText(resourceMap.getString("jBLabel15.text")); // NOI18N
        jBLabel15.setName("jBLabel15"); // NOI18N

        statusField.setEnabled(false);
        statusField.setName("statusField"); // NOI18N

        jBLabel16.setText(resourceMap.getString("jBLabel16.text")); // NOI18N
        jBLabel16.setName("jBLabel16"); // NOI18N

        jBLabel20.setText(resourceMap.getString("jBLabel20.text")); // NOI18N
        jBLabel20.setName("jBLabel20"); // NOI18N

        sentByField.setEditable(false);
        sentByField.setName("sentByField"); // NOI18N
        sentByField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sentByFieldActionPerformed(evt);
            }
        });

        jBLabel21.setText(resourceMap.getString("jBLabel21.text")); // NOI18N
        jBLabel21.setName("jBLabel21"); // NOI18N

        jBLabel22.setText(resourceMap.getString("jBLabel22.text")); // NOI18N
        jBLabel22.setName("jBLabel22"); // NOI18N

        jBLabel13.setText(resourceMap.getString("jBLabel13.text")); // NOI18N
        jBLabel13.setName("jBLabel13"); // NOI18N

        createdAtField.setName("createdAtField"); // NOI18N

        sentAtField.setName("sentAtField"); // NOI18N

        completedAtField.setName("completedAtField"); // NOI18N

        recipientDueField.setEditable(false);
        recipientDueField.setText(resourceMap.getString("recipientDueField.text")); // NOI18N
        recipientDueField.setName("recipientDueField"); // NOI18N

        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        dueDocumentsButton.setIcon(resourceMap.getIcon("dueDocumentsButton.icon")); // NOI18N
        dueDocumentsButton.setMnemonic('u');
        dueDocumentsButton.setText(resourceMap.getString("dueDocumentsButton.text")); // NOI18N
        dueDocumentsButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        dueDocumentsButton.setName("dueDocumentsButton"); // NOI18N

        deliveryStatusField.setEnabled(false);
        deliveryStatusField.setName("deliveryStatusField"); // NOI18N

        jBLabel29.setText(resourceMap.getString("jBLabel29.text")); // NOI18N
        jBLabel29.setName("jBLabel29"); // NOI18N

        javax.swing.GroupLayout jBPanel2Layout = new javax.swing.GroupLayout(jBPanel2);
        jBPanel2.setLayout(jBPanel2Layout);
        jBPanel2Layout.setHorizontalGroup(
            jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel2Layout.createSequentialGroup()
                        .addComponent(jBLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(branchField, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
                    .addGroup(jBPanel2Layout.createSequentialGroup()
                        .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(jBLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(jBLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(jBLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(jBLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(jBLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(jBLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sentAtField, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                            .addComponent(createdAtField, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                            .addComponent(documentDateField, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                            .addComponent(invoiceNumberField, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                            .addComponent(createdByField, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                            .addComponent(sentByField, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))))
                .addGap(10, 10, 10)
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jBPanel2Layout.createSequentialGroup()
                            .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jBLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addComponent(jBLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addGroup(jBPanel2Layout.createSequentialGroup()
                                    .addComponent(jBLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBPanel2Layout.createSequentialGroup()
                                    .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jBLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                        .addComponent(jBLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addGap(5, 5, 5))
                        .addGroup(jBPanel2Layout.createSequentialGroup()
                            .addComponent(jBLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(jBPanel2Layout.createSequentialGroup()
                        .addComponent(jBLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(completedAtField, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(deliveryStatusField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(statusField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBPanel2Layout.createSequentialGroup()
                        .addComponent(recipientDueField, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dueDocumentsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                    .addComponent(recipientField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(documentTypeField, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(documentDeliveryField, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(recipientContactField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
                .addContainerGap())
        );
        jBPanel2Layout.setVerticalGroup(
            jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel2Layout.createSequentialGroup()
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(branchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(recipientField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invoiceNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dueDocumentsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recipientDueField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recipientContactField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createdAtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createdByField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentDeliveryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sentAtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sentByField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deliveryStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(completedAtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jBPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jBPanel3.border.title"))); // NOI18N
        jBPanel3.setName("jBPanel3"); // NOI18N

        shippingAgentField.setName("shippingAgentField"); // NOI18N

        jBLabel17.setText(resourceMap.getString("jBLabel17.text")); // NOI18N
        jBLabel17.setName("jBLabel17"); // NOI18N

        transportMethodField.setName("transportMethodField"); // NOI18N

        jBLabel18.setText(resourceMap.getString("jBLabel18.text")); // NOI18N
        jBLabel18.setName("jBLabel18"); // NOI18N

        deliveryTypeField.setName("deliveryTypeField"); // NOI18N

        jBLabel19.setText(resourceMap.getString("jBLabel19.text")); // NOI18N
        jBLabel19.setName("jBLabel19"); // NOI18N

        jBLabel23.setText(resourceMap.getString("jBLabel23.text")); // NOI18N
        jBLabel23.setName("jBLabel23"); // NOI18N

        jBLabel24.setText(resourceMap.getString("jBLabel24.text")); // NOI18N
        jBLabel24.setName("jBLabel24"); // NOI18N

        shipDateFromField.setName("shipDateFromField"); // NOI18N

        shipDateToField.setName("shipDateToField"); // NOI18N

        shipWeekField.setName("shipWeekField"); // NOI18N

        jBLabel32.setText(resourceMap.getString("jBLabel32.text")); // NOI18N
        jBLabel32.setName("jBLabel32"); // NOI18N

        javax.swing.GroupLayout jBPanel3Layout = new javax.swing.GroupLayout(jBPanel3);
        jBPanel3.setLayout(jBPanel3Layout);
        jBPanel3Layout.setHorizontalGroup(
            jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jBLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jBLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deliveryTypeField, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                    .addComponent(shippingAgentField, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                    .addComponent(transportMethodField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel3Layout.createSequentialGroup()
                        .addComponent(jBLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shipDateFromField, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                    .addGroup(jBPanel3Layout.createSequentialGroup()
                        .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jBLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(shipWeekField, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                            .addComponent(shipDateToField, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jBPanel3Layout.setVerticalGroup(
            jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel3Layout.createSequentialGroup()
                .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(shipDateFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(shippingAgentField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel3Layout.createSequentialGroup()
                        .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(transportMethodField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deliveryTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jBPanel3Layout.createSequentialGroup()
                        .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jBLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(shipDateToField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(shipWeekField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jBPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jBPanel4.border.title"))); // NOI18N
        jBPanel4.setName("jBPanel4"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        notesField.setName("notesField"); // NOI18N
        jScrollPane1.setViewportView(notesField);

        javax.swing.GroupLayout jBPanel4Layout = new javax.swing.GroupLayout(jBPanel4);
        jBPanel4.setLayout(jBPanel4Layout);
        jBPanel4Layout.setHorizontalGroup(
            jBPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                .addContainerGap())
        );
        jBPanel4Layout.setVerticalGroup(
            jBPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addContainerGap())
        );

        additionalDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("additionalDetailsPanel.border.title"))); // NOI18N
        additionalDetailsPanel.setName("additionalDetailsPanel"); // NOI18N

        jBLabel35.setText(resourceMap.getString("jBLabel35.text")); // NOI18N
        jBLabel35.setName("jBLabel35"); // NOI18N

        validToField.setName("validToField"); // NOI18N

        jBLabel44.setText(resourceMap.getString("jBLabel44.text")); // NOI18N
        jBLabel44.setName("jBLabel44"); // NOI18N

        jBLabel45.setText(resourceMap.getString("jBLabel45.text")); // NOI18N
        jBLabel45.setName("jBLabel45"); // NOI18N

        attendeeField.setName("attendeeField"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        additionalTermsField.setName("additionalTermsField"); // NOI18N
        jScrollPane2.setViewportView(additionalTermsField);

        javax.swing.GroupLayout additionalDetailsPanelLayout = new javax.swing.GroupLayout(additionalDetailsPanel);
        additionalDetailsPanel.setLayout(additionalDetailsPanelLayout);
        additionalDetailsPanelLayout.setHorizontalGroup(
            additionalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, additionalDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(additionalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jBLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(additionalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(attendeeField, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(validToField, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jBLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );
        additionalDetailsPanelLayout.setVerticalGroup(
            additionalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(additionalDetailsPanelLayout.createSequentialGroup()
                .addGroup(additionalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(additionalDetailsPanelLayout.createSequentialGroup()
                        .addGroup(additionalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(validToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(additionalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(attendeeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jBPanel1Layout = new javax.swing.GroupLayout(jBPanel1);
        jBPanel1.setLayout(jBPanel1Layout);
        jBPanel1Layout.setHorizontalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(additionalDetailsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jBPanel1Layout.setVerticalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(additionalDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jBTabbedPane1.addTab(resourceMap.getString("jBPanel1.TabConstraints.tabTitle"), jBPanel1); // NOI18N

        jBPanel5.setName("jBPanel5"); // NOI18N

        jBPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Payment"));
        jBPanel6.setName("jBPanel6"); // NOI18N

        paymentTypeField.setName("paymentTypeField"); // NOI18N

        jBLabel36.setText(resourceMap.getString("jBLabel36.text")); // NOI18N
        jBLabel36.setName("jBLabel36"); // NOI18N

        jBLabel37.setText(resourceMap.getString("jBLabel37.text")); // NOI18N
        jBLabel37.setName("jBLabel37"); // NOI18N

        paymentTermsField.setName("paymentTermsField"); // NOI18N

        paymentDueField.setName("paymentDueField"); // NOI18N

        jBLabel38.setText(resourceMap.getString("jBLabel38.text")); // NOI18N
        jBLabel38.setName("jBLabel38"); // NOI18N

        jBLabel40.setText(resourceMap.getString("jBLabel40.text")); // NOI18N
        jBLabel40.setName("jBLabel40"); // NOI18N

        jBLabel41.setText(resourceMap.getString("jBLabel41.text")); // NOI18N
        jBLabel41.setName("jBLabel41"); // NOI18N

        jBLabel42.setText(resourceMap.getString("jBLabel42.text")); // NOI18N
        jBLabel42.setName("jBLabel42"); // NOI18N

        singlePayAmountField.setText(resourceMap.getString("singlePayAmountField.text")); // NOI18N
        singlePayAmountField.setName("singlePayAmountField"); // NOI18N

        paymentsCountField.setText(resourceMap.getString("paymentsCountField.text")); // NOI18N
        paymentsCountField.setName("paymentsCountField"); // NOI18N

        daysBetweenPaymentsField.setText(resourceMap.getString("daysBetweenPaymentsField.text")); // NOI18N
        daysBetweenPaymentsField.setName("daysBetweenPaymentsField"); // NOI18N

        javax.swing.GroupLayout jBPanel6Layout = new javax.swing.GroupLayout(jBPanel6);
        jBPanel6.setLayout(jBPanel6Layout);
        jBPanel6Layout.setHorizontalGroup(
            jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(singlePayAmountField, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(paymentTypeField, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(jBLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paymentsCountField, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(paymentTermsField, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel6Layout.createSequentialGroup()
                        .addComponent(jBLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addComponent(jBLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(daysBetweenPaymentsField, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                    .addComponent(paymentDueField, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                .addContainerGap())
        );
        jBPanel6Layout.setVerticalGroup(
            jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel6Layout.createSequentialGroup()
                .addGroup(jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentTermsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentDueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(singlePayAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(daysBetweenPaymentsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentsCountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jBPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Pricing"));
        jBPanel8.setName("jBPanel8"); // NOI18N

        currencyField.setName("currencyField"); // NOI18N

        jBLabel25.setText(resourceMap.getString("jBLabel25.text")); // NOI18N
        jBLabel25.setName("jBLabel25"); // NOI18N

        jBLabel26.setText(resourceMap.getString("jBLabel26.text")); // NOI18N
        jBLabel26.setName("jBLabel26"); // NOI18N

        jBLabel27.setText(resourceMap.getString("jBLabel27.text")); // NOI18N
        jBLabel27.setName("jBLabel27"); // NOI18N

        jBLabel28.setText(resourceMap.getString("jBLabel28.text")); // NOI18N
        jBLabel28.setName("jBLabel28"); // NOI18N

        jBLabel30.setText(resourceMap.getString("jBLabel30.text")); // NOI18N
        jBLabel30.setName("jBLabel30"); // NOI18N

        jBLabel31.setText(resourceMap.getString("jBLabel31.text")); // NOI18N
        jBLabel31.setFont(resourceMap.getFont("jBLabel31.font")); // NOI18N
        jBLabel31.setName("jBLabel31"); // NOI18N

        jBLabel33.setText(resourceMap.getString("jBLabel33.text")); // NOI18N
        jBLabel33.setName("jBLabel33"); // NOI18N

        vatConditionField.setName("vatConditionField"); // NOI18N

        jBLabel34.setText(resourceMap.getString("jBLabel34.text")); // NOI18N
        jBLabel34.setName("jBLabel34"); // NOI18N

        jBLabel39.setText(resourceMap.getString("jBLabel39.text")); // NOI18N
        jBLabel39.setName("jBLabel39"); // NOI18N

        jBLabel43.setText(resourceMap.getString("jBLabel43.text")); // NOI18N
        jBLabel43.setName("jBLabel43"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        vatConditionNotesField.setName("vatConditionNotesField"); // NOI18N
        jScrollPane3.setViewportView(vatConditionNotesField);

        invoiceSubValueField.setEditable(false);
        invoiceSubValueField.setText(resourceMap.getString("invoiceSubValueField.text")); // NOI18N
        invoiceSubValueField.setName("invoiceSubValueField"); // NOI18N

        transportPriceField.setText(resourceMap.getString("transportPriceField.text")); // NOI18N
        transportPriceField.setName("transportPriceField"); // NOI18N

        totalValueField.setEditable(false);
        totalValueField.setText(resourceMap.getString("totalValueField.text")); // NOI18N
        totalValueField.setName("totalValueField"); // NOI18N

        vatPercentField.setText(resourceMap.getString("vatPercentField.text")); // NOI18N
        vatPercentField.setName("vatPercentField"); // NOI18N

        vatValueField.setEditable(false);
        vatValueField.setText(resourceMap.getString("vatValueField.text")); // NOI18N
        vatValueField.setName("vatValueField"); // NOI18N

        excisePercentField.setName("excisePercentField"); // NOI18N

        exciseValueField.setEditable(false);
        exciseValueField.setName("exciseValueField"); // NOI18N

        javax.swing.GroupLayout jBPanel8Layout = new javax.swing.GroupLayout(jBPanel8);
        jBPanel8.setLayout(jBPanel8Layout);
        jBPanel8Layout.setHorizontalGroup(
            jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBPanel8Layout.createSequentialGroup()
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(jBLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(currencyField, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                            .addComponent(invoiceSubValueField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)))
                    .addGroup(jBPanel8Layout.createSequentialGroup()
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jBLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(jBLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                            .addComponent(vatConditionField, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jBLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jBLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jBLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBPanel8Layout.createSequentialGroup()
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vatPercentField, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(excisePercentField, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(exciseValueField, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(vatValueField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)))
                    .addComponent(transportPriceField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(totalValueField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                .addContainerGap())
        );
        jBPanel8Layout.setVerticalGroup(
            jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel8Layout.createSequentialGroup()
                .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel8Layout.createSequentialGroup()
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(currencyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vatValueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vatPercentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jBLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(invoiceSubValueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jBPanel8Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(vatConditionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(transportPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jBPanel8Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(excisePercentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exciseValueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(totalValueField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );

        itemsTableHolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("itemsTableHolderPanel.border.title"))); // NOI18N
        itemsTableHolderPanel.setName("itemsTableHolderPanel"); // NOI18N

        javax.swing.GroupLayout itemsTableHolderPanelLayout = new javax.swing.GroupLayout(itemsTableHolderPanel);
        itemsTableHolderPanel.setLayout(itemsTableHolderPanelLayout);
        itemsTableHolderPanelLayout.setHorizontalGroup(
            itemsTableHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 906, Short.MAX_VALUE)
        );
        itemsTableHolderPanelLayout.setVerticalGroup(
            itemsTableHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jBPanel5Layout = new javax.swing.GroupLayout(jBPanel5);
        jBPanel5.setLayout(jBPanel5Layout);
        jBPanel5Layout.setHorizontalGroup(
            jBPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(itemsTableHolderPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jBPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jBPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jBPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jBPanel5Layout.setVerticalGroup(
            jBPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBPanel5Layout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(itemsTableHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jBPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jBPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jBPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(454, Short.MAX_VALUE)))
        );

        jBTabbedPane1.addTab(resourceMap.getString("jBPanel5.TabConstraints.tabTitle"), jBPanel5); // NOI18N

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 943, Short.MAX_VALUE)
            .addComponent(jBTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jBTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unused")
    private void createdAtFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_createdAtFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_createdAtFieldActionPerformed

    private void createdByFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_createdByFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_createdByFieldActionPerformed

    @SuppressWarnings("unused")
    private void sentAtFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sentAtFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_sentAtFieldActionPerformed

    private void sentByFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sentByFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_sentByFieldActionPerformed

    @SuppressWarnings("unused")
    private void finalizedAtFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_finalizedAtFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_finalizedAtFieldActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBPanel additionalDetailsPanel;
    private com.cosmos.swingb.JBTextPane additionalTermsField;
    private com.cosmos.acacia.gui.AcaciaComboBox attendeeField;
    private com.cosmos.swingb.JBTextField branchField;
    private com.cosmos.swingb.JBDatePicker completedAtField;
    private com.cosmos.swingb.JBDatePicker createdAtField;
    private com.cosmos.swingb.JBTextField createdByField;
    private com.cosmos.acacia.gui.AcaciaComboBox currencyField;
    private com.cosmos.swingb.JBFormattedTextField daysBetweenPaymentsField;
    private com.cosmos.acacia.gui.AcaciaComboBox deliveryStatusField;
    private com.cosmos.acacia.gui.AcaciaComboBox deliveryTypeField;
    private com.cosmos.swingb.JBDatePicker documentDateField;
    private com.cosmos.acacia.gui.AcaciaComboBox documentDeliveryField;
    private com.cosmos.acacia.gui.AcaciaComboBox documentTypeField;
    private com.cosmos.swingb.JBButton dueDocumentsButton;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.swingb.JBFormattedTextField excisePercentField;
    private com.cosmos.swingb.JBFormattedTextField exciseValueField;
    private com.cosmos.swingb.JBTextField invoiceNumberField;
    private com.cosmos.swingb.JBFormattedTextField invoiceSubValueField;
    private com.cosmos.acacia.gui.TableHolderPanel itemsTableHolderPanel;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel10;
    private com.cosmos.swingb.JBLabel jBLabel11;
    private com.cosmos.swingb.JBLabel jBLabel12;
    private com.cosmos.swingb.JBLabel jBLabel13;
    private com.cosmos.swingb.JBLabel jBLabel14;
    private com.cosmos.swingb.JBLabel jBLabel15;
    private com.cosmos.swingb.JBLabel jBLabel16;
    private com.cosmos.swingb.JBLabel jBLabel17;
    private com.cosmos.swingb.JBLabel jBLabel18;
    private com.cosmos.swingb.JBLabel jBLabel19;
    private com.cosmos.swingb.JBLabel jBLabel20;
    private com.cosmos.swingb.JBLabel jBLabel21;
    private com.cosmos.swingb.JBLabel jBLabel22;
    private com.cosmos.swingb.JBLabel jBLabel23;
    private com.cosmos.swingb.JBLabel jBLabel24;
    private com.cosmos.swingb.JBLabel jBLabel25;
    private com.cosmos.swingb.JBLabel jBLabel26;
    private com.cosmos.swingb.JBLabel jBLabel27;
    private com.cosmos.swingb.JBLabel jBLabel28;
    private com.cosmos.swingb.JBLabel jBLabel29;
    private com.cosmos.swingb.JBLabel jBLabel30;
    private com.cosmos.swingb.JBLabel jBLabel31;
    private com.cosmos.swingb.JBLabel jBLabel32;
    private com.cosmos.swingb.JBLabel jBLabel33;
    private com.cosmos.swingb.JBLabel jBLabel34;
    private com.cosmos.swingb.JBLabel jBLabel35;
    private com.cosmos.swingb.JBLabel jBLabel36;
    private com.cosmos.swingb.JBLabel jBLabel37;
    private com.cosmos.swingb.JBLabel jBLabel38;
    private com.cosmos.swingb.JBLabel jBLabel39;
    private com.cosmos.swingb.JBLabel jBLabel40;
    private com.cosmos.swingb.JBLabel jBLabel41;
    private com.cosmos.swingb.JBLabel jBLabel42;
    private com.cosmos.swingb.JBLabel jBLabel43;
    private com.cosmos.swingb.JBLabel jBLabel44;
    private com.cosmos.swingb.JBLabel jBLabel45;
    private com.cosmos.swingb.JBLabel jBLabel6;
    private com.cosmos.swingb.JBLabel jBLabel7;
    private com.cosmos.swingb.JBLabel jBLabel8;
    private com.cosmos.swingb.JBLabel jBLabel9;
    private com.cosmos.swingb.JBPanel jBPanel1;
    private com.cosmos.swingb.JBPanel jBPanel2;
    private com.cosmos.swingb.JBPanel jBPanel3;
    private com.cosmos.swingb.JBPanel jBPanel4;
    private com.cosmos.swingb.JBPanel jBPanel5;
    private com.cosmos.swingb.JBPanel jBPanel6;
    private com.cosmos.swingb.JBPanel jBPanel8;
    private com.cosmos.swingb.JBTabbedPane jBTabbedPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.cosmos.swingb.JBTextPane notesField;
    private com.cosmos.swingb.JBDatePicker paymentDueField;
    private com.cosmos.acacia.gui.AcaciaComboBox paymentTermsField;
    private com.cosmos.acacia.gui.AcaciaComboBox paymentTypeField;
    private com.cosmos.swingb.JBFormattedTextField paymentsCountField;
    private com.cosmos.acacia.gui.AcaciaComboBox recipientContactField;
    private com.cosmos.swingb.JBTextField recipientDueField;
    private com.cosmos.acacia.gui.AcaciaComboList recipientField;
    private com.cosmos.swingb.JBDatePicker sentAtField;
    private com.cosmos.swingb.JBTextField sentByField;
    private com.cosmos.swingb.JBDatePicker shipDateFromField;
    private com.cosmos.swingb.JBDatePicker shipDateToField;
    private com.cosmos.swingb.JBTextField shipWeekField;
    private com.cosmos.acacia.gui.AcaciaComboList shippingAgentField;
    private com.cosmos.swingb.JBFormattedTextField singlePayAmountField;
    private com.cosmos.acacia.gui.AcaciaComboBox statusField;
    private com.cosmos.swingb.JBFormattedTextField totalValueField;
    private com.cosmos.acacia.gui.AcaciaComboBox transportMethodField;
    private com.cosmos.swingb.JBFormattedTextField transportPriceField;
    private com.cosmos.swingb.JBDatePicker validToField;
    private com.cosmos.acacia.gui.AcaciaComboBox vatConditionField;
    private com.cosmos.swingb.JBTextPane vatConditionNotesField;
    private com.cosmos.swingb.JBFormattedTextField vatPercentField;
    private com.cosmos.swingb.JBFormattedTextField vatValueField;
    // End of variables declaration//GEN-END:variables
    @SuppressWarnings("unchecked")
    JComboBoxBinding recipientContactBinding;
    JComboBoxBinding attendeeBinding;
    private InvoiceItemListPanel itemsTablePanel;
    private Date lastShipDateFrom;
    private boolean updatingShipDates;

    @Override
    public BindingGroup getBindingGroup() {
        if (bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel1;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void performSave(boolean closeAfter) {
        updateRecipientNames();

        entity = getFormSession().saveInvoice(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if (closeAfter) {
            close();
        } else {
            BindingGroup bg = getBindingGroup();
            bg.unbind();
            for (Binding binding : bg.getBindings()) {
                bg.removeBinding(binding);
            }
            bindingGroup = null;
            initData();
            getButtonPanel().setSaveActionState(this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initData() {
        setDialogResponse(null);

        if (entProps == null) {
            entProps = getFormSession().getDetailEntityProperties();
        }

        BindingGroup bg = getBindingGroup();

        bindComponents(bg, entProps);
    }

    @SuppressWarnings("unchecked")
    /**
     * Binds all components to the specified group and entity properties.
     * The group shouldn't be yet bound, or should be 'unbound'.
     * After all components are bound, some additional calculation and initialization is performed.
     */
    protected void bindComponents(BindingGroup bg, EntityProperties entProps) {
        // branch
        // branchField.bind(bindGroup, entity,
        // entProps.getPropertyDetails("branch"));
        if (entity.getBranch() != null) {
            branchField.setText(entity.getBranch().getAddressName());
        }

        // document number
        invoiceNumberField.bind(bg, entity, entProps.getPropertyDetails("invoiceNumber"));

        // doc date
        documentDateField.bind(bg, entity, entProps.getPropertyDetails("invoiceDate"), AcaciaUtils.getShortDateFormat());

        // clear explicitly any items
        recipientContactField.setModel(new DefaultComboBoxModel());

        // creator
        createdByField.bind(bg, entity, entProps.getPropertyDetails("creatorName"));

        // creation time
        createdAtField.bind(bg, entity, entProps.getPropertyDetails("creationTime"), AcaciaUtils.getShortDateFormat());

        // sender
        sentByField.bind(bg, entity, entProps.getPropertyDetails("senderName"));

        // sent time
        sentAtField.bind(bg, entity, entProps.getPropertyDetails("sentTime"), AcaciaUtils.getShortDateFormat());

        // completed at
        completedAtField.bind(bg, entity, entProps.getPropertyDetails("completionDate"), AcaciaUtils.getShortDateFormat());
        if (customerListPanel == null) {
            Classifier classifier = getClassifier(Classifier.Customer.getClassifierCode());
            customerListPanel = new BusinessPartnersListPanel(classifier);
        }
        recipientField.bind(bg, customerListPanel, entity, entProps.getPropertyDetails("recipient"),
                "${displayName}", UpdateStrategy.READ_WRITE);
        recipientField.getBinding().addBindingListener(new AbstractBindingListener() {

            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                if (event.getValueChanged()) {
                    BusinessPartner selected = null;
                    if (binding.isContentValid() && recipientField.getSelectedItem() instanceof BusinessPartner) {
                        selected = (BusinessPartner) recipientField.getSelectedItem();
                    }
                    onSelectRecipient(selected);
                }
            }
        });

        // if once saved - the recipient is not changeable
        if (entity.getId() != null && entity.getId().compareTo(new BigInteger("0")) > 0) {
            recipientField.setEditable(false);
            recipientField.setEnabled(false);
        }

        // recipient contact will be bound inside this method
        onSelectRecipient(entity.getRecipient());
        recipientContactField.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                onRecipientContactChanged(e.getItem());
            }
        }, true);


        if (shippingAgentListPanel == null) {
            Classifier classifier = getClassifier(Classifier.ShippingAgent.getClassifierCode());
            shippingAgentListPanel = new BusinessPartnersListPanel(classifier);
        }
        shippingAgentField.bind(bg, shippingAgentListPanel, entity,
                entProps.getPropertyDetails("shippingAgent"), "${displayName}",
                UpdateStrategy.READ_WRITE);

        // doc type
        documentTypeField.bind(bg, getDocumentTypes(), entity,
                entProps.getPropertyDetails("invoiceType"));
        documentTypeField.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getItem() instanceof DbResource) {
                    onDocumentTypeChanged((DbResource) e.getItem());
                }
            }
        }, true);

        // document delivery
        documentDeliveryField.bind(bg, getDeliveryMethods(), entity,
                entProps.getPropertyDetails("documentDeliveryMethod"));

        // transportation method
        transportMethodField.bind(bg, getTransportationMethods(), entity,
                entProps.getPropertyDetails("transportationMethod"));

        // delivery type
        deliveryTypeField.bind(bg, getDeliveryTypes(), entity,
                entProps.getPropertyDetails("deliveryType"));

        // vat condition
        vatConditionField.bind(bg, getVatConditions(), entity,
                entProps.getPropertyDetails("vatCondition"));

        // status field
        statusField.bind(bg, getStatuses(), entity, entProps.getPropertyDetails("status"));

        // delivery status field
        deliveryStatusField.bind(bg, new ArrayList(), entity, entProps.getPropertyDetails("deliveryStatus"));

        // currency
        currencyField.bind(bg, getCurrencies(), entity,
                entProps.getPropertyDetails("currency"));

        // payment type
        paymentTypeField.bind(bg, getPaymentTypes(), entity,
                entProps.getPropertyDetails("paymentType"));

        // payment terms
        paymentTermsField.bind(bg, getPaymentTerms(), entity,
                entProps.getPropertyDetails("paymentTerms"));
        paymentTermsField.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                updatePaymentFields();
            }
        }, true);

        // payment due date
        Binding paymentDueBinding = paymentDueField.bind(bg, entity, entProps.getPropertyDetails("paymentDueDate"), AcaciaUtils.getShortDateFormat());
        paymentDueBinding.setValidator(new BaseValidator() {

            @Override
            public Result validate(Object value) {
                if (value == null) {
                    return null;
                }

                InvoiceStatus status = (InvoiceStatus) entity.getStatus().getEnumValue();
                if (InvoiceStatus.Open.equals(status) || InvoiceStatus.Reopen.equals(status)) {
                }//ok,
                //in the case the the document is not modifiable - all values are valid
                else {
                    return null;
                }

                if (!(value instanceof Date)) {
                    return new Validator.Result("err_invalid_due_payment", getResourceMap().getString("err_invalid_due_payment"));
                } else {
                    Date now = new Date();
                    now.setTime(now.getTime() - 1000 * 60 * 60 * 24);//substract one day
                    if (now.after((Date) value)) {
                        return new Validator.Result("err_due_payment_old", getResourceMap().getString("err_due_payment_old"));
                    }
                }

                return null;
            }

            ;
        });

        // variable for re-use
        Binding amountsBinding = null;

        // invoice sub value
        amountsBinding = invoiceSubValueField.bind(bg, entity,
                entProps.getPropertyDetails("invoiceSubValue"), getDecimalFormat());
        amountsBinding.addBindingListener(new AbstractBindingListener() {

            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                if (updatingAmounts) {
                    return;
                } else {
                    updatingAmounts = true;
                    updateExciseAmount(binding.isContentValid());
                    updateTotalAmount(binding.isContentValid());
                    updateVatAmount(binding.isContentValid());
                    updatingAmounts = false;
                }
            }
        });

        // vat
        amountsBinding = vatPercentField.bind(bg, entity, entProps.getPropertyDetails("vat"), getDecimalFormat());
        amountsBinding.addBindingListener(new AbstractBindingListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                updateVatAmount(binding.isContentValid());
                updateTotalAmount(binding.isContentValid());
            }
        });

        // excise
        amountsBinding = excisePercentField.bind(bg, entity, entProps.getPropertyDetails("exciseDutyPercent"), getDecimalFormat());
        amountsBinding.addBindingListener(new AbstractBindingListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                updateExciseAmount(binding.isContentValid());
                updateTotalAmount(binding.isContentValid());
            }
        });

        // transport price
        amountsBinding = transportPriceField.bind(bg, entity,
                entProps.getPropertyDetails("transportationPrice"), getDecimalFormat());
        amountsBinding.addBindingListener(new AbstractBindingListener() {

            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                updateTotalAmount(binding.isContentValid());
            }
        });

        // single pay amount
        singlePayAmountField.bind(bg, entity, entProps.getPropertyDetails("singlePayAmount"), getDecimalFormat());

        // payments count
        paymentsCountField.bind(bg, entity, entProps.getPropertyDetails("paymentsCount"), getDecimalFormat());

        // payments count
        daysBetweenPaymentsField.bind(bg, entity,
                entProps.getPropertyDetails("daysBetweenPayments"), getDecimalFormat());

        // ship week
        shipWeekField.bind(bg, entity, entProps.getPropertyDetails("shipWeek")).addBindingListener(new AbstractBindingListener() {

            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                calculateShipDates(binding.isContentValid());
            }
        });

        // ship date from
        shipDateFromField.bind(bg, entity, entProps.getPropertyDetails("shipDateFrom"), AcaciaUtils.getShortDateFormat()).addBindingListener(new AbstractBindingListener() {

            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                calculateShipWeek(binding.isContentValid(), true, true);
            }
        });

        // ship date to
        shipDateToField.bind(bg, entity, entProps.getPropertyDetails("shipDateTo"), AcaciaUtils.getShortDateFormat()).addBindingListener(new AbstractBindingListener() {

            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                calculateShipWeek(binding.isContentValid(), false, true);
            }
        });

        // total value
        totalValueField.bind(bg, entity, entProps.getPropertyDetails("totalValue"), getDecimalFormat());
        totalValueField.setEditable(true);

        // notes
        notesField.bind(bg, entity, entProps.getPropertyDetails("notes"));

        // vat condition notes
        vatConditionNotesField.bind(bg, entity,
                entProps.getPropertyDetails("vatConditionNotes"));

        boolean readonlyState = !InvoiceStatus.Open.equals(entity.getStatus().getEnumValue()) &&
                !InvoiceStatus.Reopen.equals(entity.getStatus().getEnumValue());

        //some fields only for proforma invoice
        if (proforma) {
            // valid to date
            PropertyDetails pd = entProps.getPropertyDetails("validTo");
            pd.setRequired(true);
            Binding validToBinding = validToField.bind(bg, entity, pd, AcaciaUtils.getShortDateFormat());
            if (!readonlyState) {
                validToBinding.setValidator(new BaseValidator() {

                    @Override
                    public boolean isRequired() {
                        return true;
                    }

                    @Override
                    public Result validate(Object value) {
                        if (value == null) {
                            return new Validator.Result("err_invalid_validto", getResourceMap().getString("err_invalid_due_payment"));
                        }

                        InvoiceStatus status = (InvoiceStatus) entity.getStatus().getEnumValue();
                        if (InvoiceStatus.Open.equals(status) || InvoiceStatus.Reopen.equals(status)) {
                        }//ok,
                        //in the case the the document is not modifiable - all values are valid
                        else {
                            return null;
                        }

                        if (!(value instanceof Date)) {
                            return new Validator.Result("err_invalid_validto", getResourceMap().getString("err_invalid_due_payment"));
                        } else {
                            Date now = new Date();
                            now.setTime(now.getTime() - 1000 * 60 * 60 * 24);//substract one day
                            if (now.after((Date) value)) {
                                return new Validator.Result("err_validto_old", getResourceMap().getString("err_due_payment_old"));
                            }
                        }

                        return null;
                    }

                    ;
                });
            }

            // additional terms
            additionalTermsField.bind(bg, entity, entProps.getPropertyDetails("additionalTerms"));
        }

        invoiceSubValueField.setEditable(false);
        totalValueField.setEditable(false);

        bg.bind();

        // additional field updates
        // if new - set up the vat amount
        if (entity.getId() == null) {
            if (entity.getInvoiceType() != null) {
                updateVatPercent(entity.getInvoiceType().getEnumValue());
            } else {
                updateVatPercent(null);
            }
        }
        //refresh again the fields, because otherwise something
        //goes wrong with the formating
        refreshFormattedFields();

        updateVatAmount(true);
        updateExciseAmount(true);
        updatePaymentFields();

        if (readonlyState) {
            setReadonly();
        }
        itemsTablePanel.setReadonly(readonlyState);

        addTransitionButtons();
        updateFormButtons();
        updateItemsTableButtons(readonlyState);
        onItemsTableChange();
    }

    protected void onRecipientContactChanged(Object item) {
        if (proforma) {
            //if there is already a value - don't overwrite it
            if (attendeeField.getSelectedIndex() != -1) {
                return;
            }

            if (item instanceof ContactPerson) {
                attendeeField.setSelectedItem(item);
            }
        }
    }

    private void refreshFormattedFields() {
        invoiceSubValueField.setValue(entity.getInvoiceSubValue());
        vatPercentField.setValue(entity.getVat());
        excisePercentField.setValue(entity.getExciseDutyPercent());
        transportPriceField.setValue(entity.getTransportationPrice());
        totalValueField.setValue(entity.getTotalValue());
    }

    private void updateItemsTableButtons(boolean readonlyState) {
        itemsTablePanel.setVisible(com.cosmos.acacia.gui.AbstractTablePanel.Button.Classify, false);
        itemsTablePanel.setVisible(com.cosmos.acacia.gui.AbstractTablePanel.Button.Refresh, false);
        itemsTablePanel.setVisible(com.cosmos.acacia.gui.AbstractTablePanel.Button.Close, false);
        itemsTablePanel.setEnabled(AbstractTablePanel.Button.Special, true);
        itemsTablePanel.setVisible(AbstractTablePanel.Button.Special, !readonlyState);
    }
    JBButton confirmButton = null;
    JBButton reopenButton = null;
    JBButton sendButton = null;
    JBButton cancelButton = null;

    private void addTransitionButtons() {
        //just check if one of them is added, they come as a pack
        if (confirmButton == null) {
            confirmButton = addButton("button.confirm", "onConfirmButton");
            reopenButton = addButton("button.reopen", "onReopenButton");
            cancelButton = addButton("button.cancel", "onCancelButton");
            sendButton = addButton("button.send", "onSendButton");
        }
    }

    /**
     * Sets up the needed buttons for the possible state transitions of the document.
     * Should be called after {@link #addTransitionButtons()}
     */
    @SuppressWarnings("unchecked")
    private void updateFormButtons() {
        //first hide all, then show the needed buttons
        confirmButton.setVisible(false);
        reopenButton.setVisible(false);
        sendButton.setVisible(false);
        cancelButton.setVisible(false);

        //State operations buttons
        Enum status = entity.getStatus().getEnumValue();
        //for proforma invoice
        if (proforma) {
            if (InvoiceStatus.Open.equals(status) || InvoiceStatus.Reopen.equals(status)) {
                confirmButton.setVisible(true);
            } else if (InvoiceStatus.WaitForPayment.equals(status)) {
                reopenButton.setVisible(true);
                cancelButton.setVisible(true);
            } else if (InvoiceStatus.Cancelled.equals(status)) {
                reopenButton.setVisible(true);
            }
            //for invoice
        } else {
            if (InvoiceStatus.Open.equals(status) || InvoiceStatus.Reopen.equals(status)) {
                confirmButton.setVisible(true);
            }
        }

        //send button is always there
        sendButton.setVisible(true);

        getButtonPanel().getButton(Button.Save).setVisible(InvoiceStatus.Open.equals(status) || InvoiceStatus.Reopen.equals(status));
        getButtonPanel().getButton(Button.Problems).setVisible(InvoiceStatus.Open.equals(status) || InvoiceStatus.Reopen.equals(status));
        //make sure the buttons are properly activated according to the binding state
        getButtonPanel().setSaveActionState(this);
    }

    private JBButton addButton(String textKey, String action) {
        JBButton button = new JBButton();
        button.setAction(getContext().getActionMap(this).get(action));
        button.setText(getResourceMap().getString(textKey));
        entityFormButtonPanel1.addButton(button);
        return button;
    }

    /**
     * Binds again all bindings where the source object is the entity.
     * @param updatedEntity
     */
    @SuppressWarnings("unchecked")
    private void refreshForm(Invoice updatedEntity) {
        //un-bind the group

        BindingGroup bg = getBindingGroup();
        bg.unbind();
        for (Binding binding : bg.getBindings()) {
            bg.removeBinding(binding);
        }
        bindingGroup = null;
        bg = getBindingGroup();

        this.entity = updatedEntity;

        bindComponents(bg, entProps);

        //since we just swap the old entity with a new, updated one, - notify the calling windows,
        //by setting the dialog response
        setDialogResponse(DialogResponse.SAVE);
        setModifiedResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
    }

    @Action
    public void onSendButton() {
        if (confirmTransition("button.send.confirm")) {
            Invoice updatedEntity = getFormSession().sendInvoice(entity);
            refreshForm(updatedEntity);
        }
    }

    @Action
    public void onConfirmButton() {
        if (confirmTransition("button.confirm.confirm")) {
            Invoice updatedEntity = getFormSession().confirm(entity);
            refreshForm(updatedEntity);
        }
    }

    @Action
    public void onCancelButton() {
        if (showConfirmationDialog(getResourceMap().getString("button.cancel.confirm"))) {
            Invoice updatedEntity = getFormSession().cancel(entity);
            refreshForm(updatedEntity);
        }
    }

    @Action
    public void onReopenButton() {
        if (showConfirmationDialog(getResourceMap().getString("button.reopen.confirm"))) {
            Invoice updatedEntity = getFormSession().reopen(entity);
            refreshForm(updatedEntity);
        }
    }

    /**
     * Like {@link #showConfirmationDialog(String)}, but first checks if there are any items present
     * in the document and if not, shows message and stops the operation.
     * @param messageKey
     * @return
     */
    private boolean confirmTransition(String messageKey) {
        if (itemsTablePanel.getListData() == null || itemsTablePanel.getListData().isEmpty()) {
            JOptionPane.showMessageDialog(this.getParent(), getResourceMap().getString(
                    "message.cannotprocessempty"), getResourceMap().getString(
                    "message.cannotprocessempty.title"), JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        return showConfirmationDialog(getResourceMap().getString(messageKey));
    }

    protected void updateVatAmount(boolean contentValid) {
        if (contentValid) {
            try {
                BigDecimal invSubValue = new BigDecimal(invoiceSubValueField.getText());
                BigDecimal vat = new BigDecimal(vatPercentField.getText());
                BigDecimal vatPercentValue = vat.divide(new BigDecimal(100), MathContext.DECIMAL32);
                BigDecimal vatValue = vatPercentValue.multiply(invSubValue);
                if (BigDecimal.ZERO.compareTo(vatValue) == 0) {
                    vatValueField.setValue(new BigDecimal("0"));
                } else {
                    vatValueField.setValue(vatValue);
                }
            } catch (Exception e) {
            }
        } else {
            vatValueField.setValue(new BigDecimal("0"));
        }
    }

    protected void updateExciseAmount(boolean contentValid) {
        if (contentValid) {
            try {
                BigDecimal invSubValue = new BigDecimal(invoiceSubValueField.getText());
                BigDecimal excise = new BigDecimal(excisePercentField.getText());
                BigDecimal excisePercentValue = excise.divide(new BigDecimal(100), MathContext.DECIMAL32);
                BigDecimal esciseValue = excisePercentValue.multiply(invSubValue);
                if (BigDecimal.ZERO.compareTo(esciseValue) == 0) {
                    exciseValueField.setValue(new BigDecimal("0"));
                } else {
                    exciseValueField.setValue(esciseValue);
                }
            } catch (Exception e) {
            }
        } else {
            exciseValueField.setValue(new BigDecimal("0"));
        }
    }

    private List<DbResource> getDeliveryTypes() {
        if (deliveryTypes == null) {
            deliveryTypes = getFormSession().getDeliveryTypes();
        }
        return deliveryTypes;
    }

    private List<DbResource> getTransportationMethods() {
        if (transportationMethods == null) {
            transportationMethods = getFormSession().getTransportationMethods();
        }
        return transportationMethods;
    }

    protected void calculateShipWeek(boolean contentValid, boolean fromDateChanged, boolean event) {
        // avoid update cycle
        if (updatingShipDates) {
            return;
        }

        updatingShipDates = true;

        if (!contentValid) {
            shipWeekField.setText("");
        } else {

            Date dateToUse = null;

            // if no 'from' date - use the 'to'
            if (entity.getShipDateFrom() == null) {
                dateToUse = entity.getShipDateTo();
                // otherwise use the 'from'
            } else {
                dateToUse = entity.getShipDateFrom();
            }

            // auto set the 'to' date, if it is NULL or is the same as 'from'
            // date
            if (event && fromDateChanged && entity.getShipDateFrom() != null && (entity.getShipDateTo() == null // also if the 'to' date is before 'from' date, overwrite
                    // the 'to' with 'from'
                    || (entity.getShipDateFrom() != null && entity.getShipDateFrom().after(
                    entity.getShipDateTo())) // at last if the previous value of 'from' date is the same
                    // as the current 'to', then update 'to' also
                    || (lastShipDateFrom != null && lastShipDateFrom.equals(entity.getShipDateTo())))) {
                shipDateToField.setDate(entity.getShipDateFrom());
            }

            if (dateToUse == null) {
                shipWeekField.setText("");
            } else {
                Calendar c = Calendar.getInstance();
                c.setTime(dateToUse);
                Integer week = c.get(Calendar.WEEK_OF_YEAR);
                shipWeekField.setText("" + week);
            }

            if (event) {
                lastShipDateFrom = entity.getShipDateFrom();
                // lastShipDateTo = entity.getShipDateTo();
            }
        }

        updatingShipDates = false;
    }

    protected void calculateShipDates(boolean contentValid) {
        if (updatingShipDates) {
            return;
        }

        updatingShipDates = true;

        if (!contentValid || "".equals(shipWeekField.getText())) {
            shipDateFromField.setDate(null);
            shipDateToField.setDate(null);
        } else {
            Integer week = null;

            try {
                week = new Integer(shipWeekField.getText());

                Calendar c = Calendar.getInstance();
                c.set(Calendar.WEEK_OF_YEAR, week);

                shipDateFromField.setDate(c.getTime());
                c.add(Calendar.DAY_OF_WEEK, 5);
                shipDateToField.setDate(c.getTime());
            } catch (NumberFormatException e) {
            }
        }

        lastShipDateFrom = entity.getShipDateFrom();
        // lastShipDateTo = entity.getShipDateTo();

        updatingShipDates = false;
    }

    protected void onDocumentTypeChanged(DbResource newValue) {
        updateVatPercent((InvoiceType) newValue.getEnumValue());
    }

    @SuppressWarnings("unchecked")
    private void updateVatPercent(Enum invoiceTypeEnumValue) {
        if (InvoiceType.SimpleInvoice.equals(invoiceTypeEnumValue)) {
            vatPercentField.setValue(new BigDecimal("0"));
        } else {
            vatPercentField.setValue(new BigDecimal("20"));
        }
    }

    protected void onItemsTableChange() {
        calculateSumFromItemsAction();
    }
    boolean updatingAmounts = false;
    // boolean calculatedDiscountIsPercent = false;
    // boolean calculatedExciseIsPercent = false;
    // boolean autoUpdatingInvoiceSubValue = false;
    private List<DbResource> documentTypes;
    private List<DbResource> currencies;
    private List<DbResource> statuses;
    private List<DbResource> paymentTypes;
    private List<DbResource> paymentTerms;
    private List<DbResource> deliveryMethods;
    private List<DbResource> transportationMethods;
    private List<DbResource> deliveryTypes;
    private List<DbResource> vatConditions;
    private BusinessPartnersListPanel customerListPanel;
    private BusinessPartnersListPanel shippingAgentListPanel;

//    protected void updateDiscountPercent(boolean contentValid) {
//        if (!contentValid) {
//            discountPercentField.setText("");
//            return;
//        }
//        if ("".equals(discountValueField.getText())) {
//            discountPercentField.setText("");
//            return;
//        }
//
//        try {
//            calculatedDiscountIsPercent = true;
//            BigDecimal invoiceSubValue = new BigDecimal(invoiceSubValueField.getText());
//            BigDecimal discountAmount = new BigDecimal(discountValueField.getText());
//            BigDecimal result = discountAmount.divide(invoiceSubValue, new MathContext(10))
//                    .multiply(new BigDecimal(100));
//            result.setScale(4, RoundingMode.HALF_UP);
//            discountPercentField.setText("" + result.toPlainString());
//        } catch (Exception e) {
//            discountPercentField.setText("");
//        }
//    }
//
//    protected void updateDiscountAmount(boolean contentValid) {
//        if (!contentValid) {
//            discountValueField.setText("");
//            return;
//        }
//        if ("".equals(discountPercentField.getText())) {
//            discountValueField.setText("");
//            return;
//        }
//
//        try {
//            calculatedDiscountIsPercent = false;
//            BigDecimal invoiceSubValue = new BigDecimal(invoiceSubValueField.getText());
//            BigDecimal discountPercent = new BigDecimal(discountPercentField.getText());
//            BigDecimal result = discountPercent.divide(new BigDecimal(100), new MathContext(10))
//                    .multiply(invoiceSubValue);
//            result.setScale(4, RoundingMode.HALF_EVEN);
//            discountValueField.setText("" + result);
//        } catch (Exception e) {
//            discountValueField.setText("");
//        }
//    }
//    protected void updateExciseDutyPercent(boolean contentValid) {
//        if (!contentValid || "".equals(exciseDutyValueField.getText())) {
//            exciseDutyPercentField.setValue(null);
//            return;
//        }
//
//        try {
//            calculatedExciseIsPercent = true;
//            BigDecimal invoiceSubValue = new BigDecimal(invoiceSubValueField.getText());
//            BigDecimal discountAmount = new BigDecimal(exciseDutyValueField.getText());
//            BigDecimal result = discountAmount.divide(invoiceSubValue, new MathContext(10))
//                    .multiply(new BigDecimal(100));
//            exciseDutyPercentField.setValue(result);
//        } catch (Exception e) {
//            exciseDutyPercentField.setValue(null);
//        }
//    }
//    protected void updateExciseDutyValue(boolean contentValid) {
//        if (!contentValid || "".equals(exciseDutyPercentField.getText())) {
//            exciseDutyValueField.setValue(null);
//            return;
//        }
//
//        try {
//            calculatedExciseIsPercent = false;
//            BigDecimal invoiceSubValue = new BigDecimal(invoiceSubValueField.getText());
//            BigDecimal discountPercent = new BigDecimal(exciseDutyPercentField.getText());
//            BigDecimal result = discountPercent.divide(new BigDecimal(100), new MathContext(10))
//                    .multiply(invoiceSubValue);
//            exciseDutyValueField.setValue(result);
//        } catch (Exception e) {
//            exciseDutyValueField.setValue(null);
//        }
//    }
    protected void updateTotalAmount(boolean contentValid) {
        if (contentValid && allAmountsValid()) {
            try {
                BigDecimal total = new BigDecimal(0);
                total = total.add(new BigDecimal(invoiceSubValueField.getText()));
                // add vat
                BigDecimal vat = new BigDecimal(vatPercentField.getText());
                BigDecimal vatValue = vat.divide(new BigDecimal(100), MathContext.DECIMAL32);
                BigDecimal vatAmount = total.multiply(vatValue);
                total = total.add(vatAmount);
                // add excise duty
                BigDecimal exciseDutyValue = new BigDecimal(exciseValueField.getText());
                total = total.add(exciseDutyValue);
                // subtract discount
//                total = total.subtract(new BigDecimal(discountValueField.getText()));
                // add transport price
                total = total.add(new BigDecimal(transportPriceField.getText()));
                totalValueField.setValue(total);
            } catch (Exception e) {
                totalValueField.setValue(null);
            }
        } else {
            totalValueField.setValue(null);
        }
    }

    private boolean allAmountsValid() {
        if ("".equals(invoiceSubValueField.getText()) || "".equals(vatPercentField.getText()) //                || "".equals(discountValueField.getText())
                || "".equals(transportPriceField.getText()) || "".equals(exciseValueField.getText())) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private List<DbResource> getCurrencies() {
        if (currencies == null) {
            currencies = getFormSession().getCurrencies();
        }
        return currencies;
    }

    private List<DbResource> getStatuses() {
        if (statuses == null) {
            statuses = getFormSession().getInvoiceStatuses();
        }
        return statuses;
    }

    private List<DbResource> getPaymentTerms() {
        if (paymentTerms == null) {
            paymentTerms = getFormSession().getPaymentTerms();
        }
        return paymentTerms;
    }

    private List<DbResource> getPaymentTypes() {
        if (paymentTypes == null) {
            paymentTypes = getFormSession().getPaymentTypes();
        }
        return paymentTypes;
    }

    private List<DbResource> getVatConditions() {
        if (vatConditions == null) {
            vatConditions = getFormSession().getVatConditions();
        }
        return vatConditions;
    }

    private List<DbResource> getDeliveryMethods() {
        if (deliveryMethods == null) {
            deliveryMethods = getFormSession().getDeliveryMethods();
        }
        return deliveryMethods;
    }

    private List<DbResource> getDocumentTypes() {
        if (documentTypes == null) {
            documentTypes = getFormSession().getDocumentTypes();
        }
        return documentTypes;
    }

    private List<ContactPerson> getRecipientContacts(BusinessPartner recipient) {

        List<ContactPerson> result = null;
        if (recipient == null) {
            result = new ArrayList<ContactPerson>();
        } else {
            result = getFormSession().getRecipientContacts(recipient);
        }

        return result;
    }
    private List<Invoice> recipientDueDocuments = null;

    protected void onSelectRecipient(BusinessPartner recipient) {
        BindingGroup bg = getBindingGroup();

        List<ContactPerson> recipientContacts = null;
        if (recipient != null) {
            recipientContacts = getRecipientContacts(recipient);
        } else {
            recipientContacts = new ArrayList<ContactPerson>();
        }

        if (bg.getBindings().contains(recipientContactBinding)) {
            bg.removeBinding(recipientContactBinding);
        }
        recipientContactBinding = recipientContactField.bind(bg, recipientContacts,
                entity, entProps.getPropertyDetails("recipientContact"));
        recipientContactBinding.bind();

        // auto select if one choice is available
        if (recipientContactField.getModel().getSize() == 1) {
            recipientContactField.setSelectedIndex(0);
        } else {
            recipientContactField.setSelectedIndex(-1);
        }

        if (proforma) {
            if (bg.getBindings().contains(attendeeBinding)) {
                bg.removeBinding(attendeeBinding);
            }
            PropertyDetails pd = entProps.getPropertyDetails("attendee");
            pd.setValidator(new RequiredValidator());
            ((RequiredValidator) pd.getValidator()).setRequired(true);
            pd.setRequired(true);
            attendeeBinding = attendeeField.bind(bg, recipientContacts,
                    entity, pd);
            attendeeBinding.bind();
            attendeeField.setSelectedIndex(recipientContactField.getSelectedIndex());
        }

        updateRecipientDueFields(recipient);
    }

    private void updateRecipientDueFields(BusinessPartner recipient) {
        //get recipient's due documents
        if (recipient != null) {
            recipientDueDocuments = getFormSession().getDueDocuments(recipient);
        } else {
            recipientDueDocuments = new ArrayList<Invoice>();
        }

        //update the due field
        if (recipientDueDocuments.isEmpty()) {
            recipientDueField.setText("");
        } else {
            BigDecimal currentDue = new BigDecimal(0);
            for (Invoice invoice : recipientDueDocuments) {
                //if the current document is CREDIT note, decrease the due
                if (InvoiceType.CretidNoteInvoice.equals(invoice.getInvoiceType().getEnumValue())) {
                    currentDue = currentDue.subtract(invoice.getTotalValue());
                } //otherwise increase the due amount
                else {
                    currentDue = currentDue.add(invoice.getTotalValue());
                }
            }
            recipientDueField.setText(AcaciaUtils.getDecimalFormat().format(currentDue));
        }

        dueDocumentsButton.setEnabled(!recipientDueDocuments.isEmpty());
    }

    public InvoiceListRemote getFormSession() {
        return formSession;
    }

    protected void updateRecipientNames() {
        ContactPerson recipientContact = entity.getRecipientContact();
        if (recipientContact == null) {
            entity.setRecipientContactName(null);
        } else {
            entity.setRecipientContactName(recipientContact.getContact().getDisplayName());
        }

        BusinessPartner recipient = entity.getRecipient();
        if (recipient != null) {
            entity.setRecipientName(recipient.getDisplayName());
        } else {
            entity.setRecipientName(null);
        }
    }

    @SuppressWarnings("unchecked")
    @Action
    public void calculateSumFromItemsAction() {
        List<InvoiceItem> items = itemsTablePanel.getListData();
        BigDecimal sum = new BigDecimal(0);
        for (InvoiceItem item : items) {
            sum = sum.add(item.getExtendedPrice());
        }
        // autoUpdatingInvoiceSubValue = true;
        invoiceSubValueField.setValue(sum);
        // autoUpdatingInvoiceSubValue = false;
    }

    protected void updatePaymentFields() {
        if (!(paymentTermsField.getSelectedItem() instanceof DbResource)) {
            return;
        }
        boolean activePaymentFields = false;
        PaymentTerm selTerms = (PaymentTerm) ((DbResource) paymentTermsField.getSelectedItem()).getEnumValue();
        if (PaymentTerm.BeforeAndAfter.equals(selTerms) || PaymentTerm.InstalmentPlan.equals(selTerms) || PaymentTerm.Leasing.equals(selTerms)) {
            activePaymentFields = true;
        }

        singlePayAmountField.setEditable(activePaymentFields);
        paymentsCountField.setEditable(activePaymentFields);
        daysBetweenPaymentsField.setEditable(activePaymentFields);
    }

    @Override
    public void setReadonly() {
        super.setReadonly();
        shippingAgentField.setEnabled(false);
        recipientField.setEnabled(false);
    }

    @Override
    protected Set<Report> getReports() {
        Set<Report> reports = new HashSet<Report>();

        Calendar c = Calendar.getInstance();

        String exportFileName = "";
        if (entity.getInvoiceNumber() != null) {
            exportFileName += entity.getInvoiceNumber() + "-";
        } else {
            exportFileName += "invoice-";
        }

        exportFileName += "" + c.get(Calendar.YEAR) +
                c.get(Calendar.MONTH) +
                c.get(Calendar.DAY_OF_MONTH);

        Report report = new Report("invoice", itemsTablePanel.getItems());
        report.setAutoSubreport1Class(InvoiceItem.class);
        report.setLocalizationKey("report.original");
        report.getParameters().put("TYPE", getResourceMap().getString("report.original"));
        report.setExportFileName(exportFileName);
        reports.add(report);

        //If proforma - no original & copy
        if (!entity.getProformaInvoice()) {
            Report report2 = new Report("invoice", itemsTablePanel.getItems());
            report2.setAutoSubreport1Class(InvoiceItem.class);
            report2.setLocalizationKey("report.copy");
            report2.getParameters().put("TYPE", getResourceMap().getString("report.copy"));
            report2.setExportFileName(exportFileName);
            reports.add(report2);
        }

        return reports;
    }

    @Override
    protected Object getReportHeader() {

        DocumentUtilRemote docUtil = getBean(DocumentUtilRemote.class, false);

        PrintableDocumentHeader header = docUtil.createDocumentHeaderExecutorPart(entity.getBranch());
        header = docUtil.createDocumentHeaderRecipientPart(entity.getRecipient(), entity.getBranch(), entity.getRecipientContact(), header);
        try {
            NumberNamesAlgorithm algorithm =
                    NumberNamesAlgorithmFactory.getNumberNamesAlgorithm(
                    new Locale("bg"), //AcaciaUtils.getLocale(),
                    NumberNamesAlgorithmType.MONEY,
                    entity.getCurrency().getEnumValue(),
                    ResourceMapUtil.getResourceMap());
            header.setTotalPriceInWords(algorithm.getNumberName(entity.getTotalValue()));
        } catch (UnsupportedNumberAlgorithmException ex) {
            log.error(ex);
        }

        return header;
    }
}
