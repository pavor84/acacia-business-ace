/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.purchase;

import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.swingb.JBTabbedPane;
import java.awt.BorderLayout;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author Miro
 */
public class GoodsReceiptPanel extends AcaciaPanel {

    private JBTabbedPane mainTabbedPane;
    private GoodsReceiptPrimaryInfoPanel primaryInfoPanel;
    private GoodsReceiptItemListPanel itemListPanel;

    public GoodsReceiptPanel() {
        initComponents();
        initData();
    }

    private void initComponents() {
        setName("Form"); // NOI18N
        setLayout(new BorderLayout());

        mainTabbedPane = new JBTabbedPane();
        primaryInfoPanel = new GoodsReceiptPrimaryInfoPanel(this);
        itemListPanel = new GoodsReceiptItemListPanel(this);

        primaryInfoPanel.setName("primaryInfoPanel"); // NOI18N

        itemListPanel.setName("itemListPanel"); // NOI18N

        mainTabbedPane.setName("mainTabbedPane"); // NOI18N
        mainTabbedPane.addTab(getResourceString("primaryInfoPanel.TabConstraints.tabTitle"), primaryInfoPanel); // NOI18N
        mainTabbedPane.addTab(getResourceString("itemListPanel.TabConstraints.tabTitle"), itemListPanel); // NOI18N
        add(mainTabbedPane, BorderLayout.CENTER);
    }

    @Override
    protected void initData() {
    }

    protected String getResourceString(String key) {
        return getResourceMap().getString(key);
    }
}
