/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.purchase;

import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.AcaciaTreeTable;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTabbedPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Miro
 */
public class GoodsReceiptItemListPanel extends JBPanel {

    private GoodsReceiptPanel goodsReceiptPanel;
    //
    private JBButton itemListByOrderIncludeAddButton;
    private JBButton itemListByOrderIncludeDocumentButton;
    private JBButton itemListByOrderModifyButton;
    private JBPanel itemListByOrderPanel;
    private JBPanel buttonsPanel;
    private JBButton itemListByOrderRemoveButton;
    private JScrollPane itemListByOrderScrollPane;
    private JBButton itemListByOrderSerialNumbersButton;
    private AcaciaTreeTable itemListByOrderTreeTable;
    private JBPanel itemListByProductsPanel;
    private JScrollPane itemListByProductsScrollPane;
    private AcaciaTable itemListByProductsTable;
    private JBTabbedPane itemListTabbedPane;

    public GoodsReceiptItemListPanel(GoodsReceiptPanel goodsReceiptPanel) {
        super(new BorderLayout());
        this.goodsReceiptPanel = goodsReceiptPanel;
        initComponents();
        initData();
    }

    private void initComponents() {
        itemListTabbedPane = new JBTabbedPane();
        itemListByOrderPanel = new JBPanel(new BorderLayout());
        buttonsPanel = new JBPanel(new GridLayout(1, 0, 7, 0));
        itemListByOrderScrollPane = new JScrollPane();
        itemListByOrderTreeTable = new AcaciaTreeTable();
        itemListByOrderRemoveButton = new JBButton();
        itemListByOrderIncludeAddButton = new JBButton();
        itemListByOrderIncludeDocumentButton = new JBButton();
        itemListByOrderModifyButton = new JBButton();
        itemListByOrderSerialNumbersButton = new JBButton();
        itemListByProductsPanel = new JBPanel(new BorderLayout());
        itemListByProductsScrollPane = new JScrollPane();
        itemListByProductsTable = new AcaciaTable();

        //
        itemListByOrderScrollPane.setName("itemListByOrderScrollPane"); // NOI18N

        itemListByOrderTreeTable.setName("itemListByOrderTreeTable"); // NOI18N
        itemListByOrderScrollPane.setViewportView(itemListByOrderTreeTable);

        //
        itemListByOrderRemoveButton.setText(getResourceString("itemListByOrderRemoveButton.text")); // NOI18N
        itemListByOrderRemoveButton.setName("itemListByOrderRemoveButton"); // NOI18N

        itemListByOrderIncludeAddButton.setText(getResourceString("itemListByOrderIncludeAddButton.text")); // NOI18N
        itemListByOrderIncludeAddButton.setName("itemListByOrderIncludeAddButton"); // NOI18N

        itemListByOrderIncludeDocumentButton.setText(getResourceString("itemListByOrderIncludeDocumentButton.text")); // NOI18N
        itemListByOrderIncludeDocumentButton.setName("itemListByOrderIncludeDocumentButton"); // NOI18N

        itemListByOrderModifyButton.setText(getResourceString("itemListByOrderModifyButton.text")); // NOI18N
        itemListByOrderModifyButton.setName("itemListByOrderModifyButton"); // NOI18N

        itemListByOrderSerialNumbersButton.setText(getResourceString("itemListByOrderSerialNumbersButton.text")); // NOI18N
        itemListByOrderSerialNumbersButton.setName("itemListByOrderSerialNumbersButton"); // NOI18N

        buttonsPanel.add(itemListByOrderIncludeDocumentButton);
        buttonsPanel.add(itemListByOrderIncludeAddButton);
        buttonsPanel.add(itemListByOrderSerialNumbersButton);
        buttonsPanel.add(itemListByOrderModifyButton);
        buttonsPanel.add(itemListByOrderRemoveButton);

        //
        itemListByOrderPanel.setName("itemListByOrderPanel"); // NOI18N
        itemListByOrderPanel.add(itemListByOrderScrollPane, BorderLayout.CENTER);
        itemListByOrderPanel.add(buttonsPanel, BorderLayout.SOUTH);

        //
        itemListByProductsScrollPane.setName("itemListByProductsScrollPane"); // NOI18N

        itemListByProductsTable.setName("itemListByProductsTable"); // NOI18N
        itemListByProductsScrollPane.setViewportView(itemListByProductsTable);

        //
        itemListByProductsPanel.setName("itemListByProductsPanel"); // NOI18N
        itemListByProductsPanel.add(itemListByProductsScrollPane, BorderLayout.CENTER);

        //
        itemListTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        itemListTabbedPane.setName("itemListTabbedPane"); // NOI18N
        itemListTabbedPane.addTab(getResourceString("itemListByOrderPanel.TabConstraints.tabTitle"), itemListByOrderPanel); // NOI18N
        itemListTabbedPane.addTab(getResourceString("itemListByProductsPanel.TabConstraints.tabTitle"), itemListByProductsPanel); // NOI18N

        add(itemListTabbedPane, BorderLayout.CENTER);
    }

    private void initData() {
    }

    @Override
    protected String getResourceString(String key) {
        return goodsReceiptPanel.getResourceString(key);
    }
}
