/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.purchase;

import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBIntegerField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTextField;
import com.cosmos.swingb.JBTextPane;
import com.cosmos.swingb.MigLayoutHelper;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Miro
 */
public class GoodsReceiptPrimaryInfoPanel extends JBPanel {

    private GoodsReceiptPanel goodsReceiptPanel;
    //
    private JBLabel consigneeBranchLabel;
    private JBTextField consigneeBranchTextField;
    private JBLabel consigneeNameLabel;
    private JBTextField consigneeNameTextField;
    private JBPanel consigneePanel;
    private JBLabel consigneeStokekeeperLabel;
    private JBTextField consigneeStokekeeperTextField;
    private JBLabel receiptDateLabel;
    private JBDatePicker receiptDatePicker;
    private JBPanel receiptDetailsPanel;
    private JBIntegerField receiptNumberIntegerField;
    private JBLabel receiptNumberLabel;
    private JBLabel receiptStatusLabel;
    private JBTextField receiptStatusTextField;
    private JBLabel relatedDocDateLabel;
    private JBDatePicker relatedDocDatePicker;
    private JBLabel relatedDocNumberLabel;
    private JBTextField relatedDocNumberTextField;
    private JBLabel relatedDocTypeLabel;
    private JBTextField relatedDocTypeTextField;
    private JBPanel relatedDocumentPanel;
    private JBButton selectButton;
    private AcaciaComboList supplierBranchComboList;
    private JBLabel supplierBranchLabel;
    private JBLabel supplierContactNameLabel;
    private AcaciaComboList supplierContactPersonComboList;
    private AcaciaComboList supplierNameComboList;
    private JBLabel supplierNameLabel;
    private JBPanel supplierPanel;
    private JBPanel notesPanel;
    private JScrollPane notesScrollPane;
    private JBTextPane notesTextPane;

    public GoodsReceiptPrimaryInfoPanel(GoodsReceiptPanel goodsReceiptPanel) {
        super(new MigLayout(
                "wrap 2, fillx",
                "[sg SGY][sg SGY]",
                "[][][][grow 100,fill]"));
        this.goodsReceiptPanel = goodsReceiptPanel;
        initComponents();
        initData();
    }

    private void initComponents() {
        receiptDetailsPanel = new JBPanel();
        receiptNumberIntegerField = new JBIntegerField();
        receiptNumberLabel = new JBLabel();
        receiptDateLabel = new JBLabel();
        receiptStatusLabel = new JBLabel();
        receiptStatusTextField = new JBTextField();
        receiptDatePicker = new JBDatePicker();
        relatedDocumentPanel = new JBPanel();
        relatedDocTypeLabel = new JBLabel();
        relatedDocNumberLabel = new JBLabel();
        selectButton = new JBButton();
        relatedDocDateLabel = new JBLabel();
        relatedDocTypeTextField = new JBTextField();
        relatedDocNumberTextField = new JBTextField();
        relatedDocDatePicker = new JBDatePicker();
        consigneePanel = new JBPanel();
        consigneeNameTextField = new JBTextField();
        consigneeBranchTextField = new JBTextField();
        consigneeStokekeeperTextField = new JBTextField();
        consigneeNameLabel = new JBLabel();
        consigneeBranchLabel = new JBLabel();
        consigneeStokekeeperLabel = new JBLabel();
        supplierPanel = new JBPanel();
        supplierNameLabel = new JBLabel();
        supplierBranchLabel = new JBLabel();
        supplierContactNameLabel = new JBLabel();
        supplierNameComboList = new AcaciaComboList();
        supplierBranchComboList = new AcaciaComboList();
        supplierContactPersonComboList = new AcaciaComboList();
        notesPanel = new JBPanel(new BorderLayout());
        notesScrollPane = new JScrollPane();
        notesTextPane = new JBTextPane();

        //
        receiptDetailsPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("receiptDetailsPanel.border.title"))); // NOI18N
        receiptDetailsPanel.setName("receiptDetailsPanel"); // NOI18N

        receiptNumberIntegerField.setEditable(false);
        receiptNumberIntegerField.setName("receiptNumberIntegerField"); // NOI18N

        receiptNumberLabel.setText(getResourceString("receiptNumberLabel.text")); // NOI18N
        receiptNumberLabel.setName("receiptNumberLabel"); // NOI18N

        receiptDateLabel.setText(getResourceString("receiptDateLabel.text")); // NOI18N
        receiptDateLabel.setName("receiptDateLabel"); // NOI18N

        receiptStatusLabel.setText(getResourceString("receiptStatusLabel.text")); // NOI18N
        receiptStatusLabel.setName("receiptStatusLabel"); // NOI18N

        receiptStatusTextField.setEditable(false);
        receiptStatusTextField.setName("receiptStatusTextField"); // NOI18N

        receiptDatePicker.setEditable(false);
        receiptDatePicker.setName("receiptDatePicker"); // NOI18N

        MigLayoutHelper helper = new MigLayoutHelper(receiptDetailsPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(6);
        helper.columnGap("15", 1, 3);
        helper.columnSizeGroup("SG", 1, 3, 5);
        helper.columnGrow(100, 1, 3, 5);
        helper.columnFill(1, 3, 5);

        receiptDetailsPanel.add(receiptNumberLabel);
        receiptDetailsPanel.add(receiptNumberIntegerField);
        receiptDetailsPanel.add(receiptDateLabel);
        receiptDetailsPanel.add(receiptDatePicker);
        receiptDetailsPanel.add(receiptStatusLabel);
        receiptDetailsPanel.add(receiptStatusTextField);

        //
        consigneePanel.setBorder(BorderFactory.createTitledBorder(getResourceString("consigneePanel.border.title"))); // NOI18N
        consigneePanel.setName("consigneePanel"); // NOI18N

        consigneeNameTextField.setEditable(false);
        consigneeNameTextField.setName("consigneeNameTextField"); // NOI18N

        consigneeBranchTextField.setEditable(false);
        consigneeBranchTextField.setName("consigneeBranchTextField"); // NOI18N

        consigneeStokekeeperTextField.setEditable(false);
        consigneeStokekeeperTextField.setName("consigneeStokekeeperTextField"); // NOI18N

        consigneeNameLabel.setText(getResourceString("consigneeNameLabel.text")); // NOI18N
        consigneeNameLabel.setName("consigneeNameLabel"); // NOI18N

        consigneeBranchLabel.setText(getResourceString("consigneeBranchLabel.text")); // NOI18N
        consigneeBranchLabel.setName("consigneeBranchLabel"); // NOI18N

        consigneeStokekeeperLabel.setText(getResourceString("consigneeStokekeeperLabel.text")); // NOI18N
        consigneeStokekeeperLabel.setName("consigneeStokekeeperLabel"); // NOI18N

        helper = new MigLayoutHelper(consigneePanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(2);
        helper.columnGrow(100, 1);
        helper.columnFill(1);

        consigneePanel.add(consigneeNameLabel);
        consigneePanel.add(consigneeNameTextField);
        consigneePanel.add(consigneeBranchLabel);
        consigneePanel.add(consigneeBranchTextField);
        consigneePanel.add(consigneeStokekeeperLabel);
        consigneePanel.add(consigneeStokekeeperTextField);

        //
        supplierPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("supplierPanel.border.title"))); // NOI18N
        supplierPanel.setName("supplierPanel"); // NOI18N

        supplierNameLabel.setText(getResourceString("supplierNameLabel.text")); // NOI18N
        supplierNameLabel.setName("supplierNameLabel"); // NOI18N

        supplierBranchLabel.setText(getResourceString("supplierBranchLabel.text")); // NOI18N
        supplierBranchLabel.setName("supplierBranchLabel"); // NOI18N

        supplierContactNameLabel.setText(getResourceString("supplierContactNameLabel.text")); // NOI18N
        supplierContactNameLabel.setName("supplierContactNameLabel"); // NOI18N

        supplierNameComboList.setName("supplierNameComboList"); // NOI18N

        supplierBranchComboList.setName("supplierBranchComboList"); // NOI18N

        supplierContactPersonComboList.setName("supplierContactPersonComboList"); // NOI18N

        helper = new MigLayoutHelper(supplierPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(2);
        helper.columnGrow(100, 1);
        helper.columnFill(1);

        supplierPanel.add(supplierNameLabel);
        supplierPanel.add(supplierNameComboList);
        supplierPanel.add(supplierBranchLabel);
        supplierPanel.add(supplierBranchComboList);
        supplierPanel.add(supplierContactNameLabel);
        supplierPanel.add(supplierContactPersonComboList);

        //
        relatedDocumentPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("relatedDocumentPanel.border.title"))); // NOI18N
        relatedDocumentPanel.setName("relatedDocumentPanel"); // NOI18N

        relatedDocTypeLabel.setText(getResourceString("relatedDocTypeLabel.text")); // NOI18N
        relatedDocTypeLabel.setName("relatedDocTypeLabel"); // NOI18N

        relatedDocNumberLabel.setText(getResourceString("relatedDocNumberLabel.text")); // NOI18N
        relatedDocNumberLabel.setName("relatedDocNumberLabel"); // NOI18N

        selectButton.setText(getResourceString("selectButton.text")); // NOI18N
        selectButton.setName("selectButton"); // NOI18N

        relatedDocDateLabel.setText(getResourceString("relatedDocDateLabel.text")); // NOI18N
        relatedDocDateLabel.setName("relatedDocDateLabel"); // NOI18N

        relatedDocTypeTextField.setColumns(7);
        relatedDocTypeTextField.setEditable(false);
        relatedDocTypeTextField.setName("relatedDocTypeTextField"); // NOI18N

        relatedDocNumberTextField.setColumns(7);
        relatedDocNumberTextField.setEditable(false);
        relatedDocNumberTextField.setName("relatedDocNumberTextField"); // NOI18N

        relatedDocDatePicker.setEditable(false);
        relatedDocDatePicker.setName("relatedDocDatePicker"); // NOI18N

        helper = new MigLayoutHelper(relatedDocumentPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(7);
        helper.columnGap("15", 1, 3, 5);
        helper.columnSizeGroup("SG", 1, 3, 5);
        helper.columnGrow(100, 1, 3, 5);
        helper.columnFill(1, 3, 5);

        relatedDocumentPanel.add(relatedDocTypeLabel);
        relatedDocumentPanel.add(relatedDocTypeTextField);
        relatedDocumentPanel.add(relatedDocNumberLabel);
        relatedDocumentPanel.add(relatedDocNumberTextField);
        relatedDocumentPanel.add(relatedDocDateLabel);
        relatedDocumentPanel.add(relatedDocDatePicker);
        relatedDocumentPanel.add(selectButton);

        //
        notesTextPane.setName("notesTextPane"); // NOI18N
        notesScrollPane.setName("notesScrollPane"); // NOI18N
        notesScrollPane.setViewportView(notesTextPane);

        notesPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("notesPanel.border.title"))); // NOI18N
        notesPanel.setName("notesPanel"); // NOI18N
        notesPanel.add(notesScrollPane, BorderLayout.CENTER);

        //
        add(receiptDetailsPanel, "span 2, growx");
        add(consigneePanel, "sizegroup SGY, growx");
        add(supplierPanel, "sizegroup SGY, growx");
        add(relatedDocumentPanel, "span 2, growx");
        add(notesPanel, "span 2, grow 100");
    }

    private String getResourceString(String key) {
        return goodsReceiptPanel.getResourceString(key);
    }

    private void initData() {
        receiptDatePicker.setDate(null);
        relatedDocDatePicker.setDate(null);
    }

}
