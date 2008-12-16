/*
 * OrdersMatchingForm.java
 *
 * Created on Сряда, 2008, Юли 16, 13:53
 */

package com.cosmos.acacia.crm.gui.purchaseorders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cosmos.acacia.crm.bl.purchaseorder.OrderConfirmationListRemote;
import com.cosmos.acacia.crm.bl.purchaseorder.PurchaseOrderListRemote;
import com.cosmos.acacia.crm.data.OrderConfirmation;
import com.cosmos.acacia.crm.data.OrderConfirmationItem;
import com.cosmos.acacia.crm.data.PurchaseOrder;
import com.cosmos.acacia.crm.data.PurchaseOrderItem;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AbstractTablePanel.ButtonVisibility;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * Created	:	23.07.2008
 * @author	Petar Milev
 *
 */
public class OrdersMatchingForm extends AcaciaPanel {

    private PurchaseOrderListRemote purchaseOrderListRemote = getBean(PurchaseOrderListRemote.class, false);
    private OrderConfirmationListRemote orderConfirmationListRemote = getBean(OrderConfirmationListRemote.class, false);

    private PurchaseOrderItemListPanel orderItemsListPanel;
    private OrderConfirmationItemListPanel confirmationItemsListPanel;

    /** Creates new form OrdersMatchingForm
     * @param parentId */
    public OrdersMatchingForm(BigInteger parentId) {
        super(parentId);
        initComponents();
        initData();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBLabel3 = new com.cosmos.swingb.JBLabel();
        closeButton = new com.cosmos.swingb.JBButton();
        orderConfirmationField = new com.cosmos.acacia.gui.AcaciaComboList();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        purchaseOrderField = new com.cosmos.acacia.gui.AcaciaComboList();
        autoMatchButton = new com.cosmos.swingb.JBButton();
        matchItemsButton = new com.cosmos.swingb.JBButton();
        matchPartialButton = new com.cosmos.swingb.JBButton();
        jBSeparator1 = new com.cosmos.swingb.JBSeparator();
        confirmationItemsHolderPanel = new com.cosmos.acacia.gui.TableHolderPanel();
        orderItemsHolderPanel = new com.cosmos.acacia.gui.TableHolderPanel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(OrdersMatchingForm.class);
        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        closeButton.setIcon(resourceMap.getIcon("closeButton.icon")); // NOI18N
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        orderConfirmationField.setName("orderConfirmationField"); // NOI18N

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        purchaseOrderField.setName("purchaseOrderField"); // NOI18N

        autoMatchButton.setText(resourceMap.getString("autoMatchButton.text")); // NOI18N
        autoMatchButton.setName("autoMatchButton"); // NOI18N

        matchItemsButton.setText(resourceMap.getString("matchItemsButton.text")); // NOI18N
        matchItemsButton.setName("matchItemsButton"); // NOI18N

        matchPartialButton.setText(resourceMap.getString("matchPartialButton.text")); // NOI18N
        matchPartialButton.setName("matchPartialButton"); // NOI18N

        jBSeparator1.setName("jBSeparator1"); // NOI18N

        confirmationItemsHolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("confirmationItemsHolderPanel.border.title"))); // NOI18N
        confirmationItemsHolderPanel.setName("confirmationItemsHolderPanel"); // NOI18N

        javax.swing.GroupLayout confirmationItemsHolderPanelLayout = new javax.swing.GroupLayout(confirmationItemsHolderPanel);
        confirmationItemsHolderPanel.setLayout(confirmationItemsHolderPanelLayout);
        confirmationItemsHolderPanelLayout.setHorizontalGroup(
            confirmationItemsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 989, Short.MAX_VALUE)
        );
        confirmationItemsHolderPanelLayout.setVerticalGroup(
            confirmationItemsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );

        orderItemsHolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("orderItemsHolderPanel.border.title"))); // NOI18N
        orderItemsHolderPanel.setName("orderItemsHolderPanel"); // NOI18N

        javax.swing.GroupLayout orderItemsHolderPanelLayout = new javax.swing.GroupLayout(orderItemsHolderPanel);
        orderItemsHolderPanel.setLayout(orderItemsHolderPanelLayout);
        orderItemsHolderPanelLayout.setHorizontalGroup(
            orderItemsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 989, Short.MAX_VALUE)
        );
        orderItemsHolderPanelLayout.setVerticalGroup(
            orderItemsHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(confirmationItemsHolderPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(orderConfirmationField, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(matchItemsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(matchPartialButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(autoMatchButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(317, 317, 317))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(purchaseOrderField, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(orderItemsHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(orderConfirmationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmationItemsHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matchPartialButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matchItemsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(autoMatchButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purchaseOrderField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderItemsHolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton autoMatchButton;
    private com.cosmos.swingb.JBButton closeButton;
    private com.cosmos.acacia.gui.TableHolderPanel confirmationItemsHolderPanel;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private com.cosmos.swingb.JBSeparator jBSeparator1;
    private com.cosmos.swingb.JBButton matchItemsButton;
    private com.cosmos.swingb.JBButton matchPartialButton;
    private com.cosmos.acacia.gui.AcaciaComboList orderConfirmationField;
    private com.cosmos.acacia.gui.TableHolderPanel orderItemsHolderPanel;
    private com.cosmos.acacia.gui.AcaciaComboList purchaseOrderField;
    // End of variables declaration//GEN-END:variables


    @Override
    protected void initData() {
        //by default we have close response
        setDialogResponse(DialogResponse.CLOSE);

        matchItemsButton.setMnemonic('M');
        matchPartialButton.setMnemonic('P');
        autoMatchButton.setMnemonic('A');
        matchItemsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onMatchItems();}
        });
        matchPartialButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onMatchPartial();}
        });
        autoMatchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onAutoMatch();}
        });

        //purchase order items panel
        orderItemsListPanel = new PurchaseOrderItemListPanel((BigInteger)null);
        orderItemsListPanel.setVisibleButtons(0);
        orderItemsHolderPanel.add(orderItemsListPanel);
        orderItemsListPanel.getDataTable().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateMatchItemButtons();
            }
        });

        //confirmation items list panel
        confirmationItemsListPanel = new OrderConfirmationItemListPanel((BigInteger)null);
        confirmationItemsListPanel.setVisibleButtons(0);
        confirmationItemsListPanel.setEditable(false);
        confirmationItemsHolderPanel.add(confirmationItemsListPanel);
        confirmationItemsListPanel.getDataTable().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                autoSelectOrderItem();
            }
        });

        //purchase orders
        List<PurchaseOrder> pendingOrders = purchaseOrderListRemote.getPendingOrders(getParentDataObjectId(), getUserBranch());
        bindorderConfirmationField(pendingOrders);

        //order confirmations
        List<OrderConfirmation> pendingConfirmations = orderConfirmationListRemote.getPendingConfirmations(getParentDataObjectId(), getUserBranch());
        OrderConfirmationListPanel confirmationsListPanel = new OrderConfirmationListPanel(getParentDataObjectId(), pendingConfirmations);
        confirmationsListPanel.setVisibleButtons(ButtonVisibility.Select.getVisibilityIndex() |
            ButtonVisibility.Unselect.getVisibilityIndex() | ButtonVisibility.Close.getVisibilityIndex());
        orderConfirmationField.initUnbound(confirmationsListPanel, "${documentNumber} - ${supplier.displayName}");
        orderConfirmationField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                onOrderConfirmationChanged((OrderConfirmation)e.getItem());
            }
        }, true);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }

    private void autoSelectOrderItem() {
        if ( !(purchaseOrderField.getSelectedItem() instanceof PurchaseOrder) )
            return;
        
        List<PurchaseOrderItem> orderItems = orderItemsListPanel.getItems();

        OrderConfirmationItem confirmationItem = (OrderConfirmationItem) confirmationItemsListPanel.getDataTable().getSelectedRowObject();
        //believe me - this also happens
        if ( confirmationItem==null )
            return;

        PurchaseOrderItem targetOrderItem = null;
        SimpleProduct targetProduct = confirmationItem.getProduct();
        for (PurchaseOrderItem purchaseOrderItem : orderItems) {
            if ( purchaseOrderItem.getProduct().equals(targetProduct)){
                //pending > 0
                if ( purchaseOrderItem.getPendingQuantity().compareTo(new BigDecimal(0))>0 ){
                    targetOrderItem = purchaseOrderItem;
                    break;
                //in this case, select if only there is no other selected, and continue searching
                }else if ( targetOrderItem==null ){
                    targetOrderItem = purchaseOrderItem;
                }
            }
        }

        orderItemsListPanel.setSelectedRowObject(targetOrderItem);

        updateMatchItemButtons();
    }

    protected void onAutoMatch() {
        int result = JOptionPane.showConfirmDialog(
                this.getParent(),
                getResourceMap().getString("autoMatch.confirmMessage"),
                getResourceMap().getString("autoMatch.confirmMessage.title"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(JOptionPane.YES_OPTION == result){

            PurchaseOrder purchaseOrder = (PurchaseOrder) purchaseOrderField.getSelectedItem();
            OrderConfirmation orderConfirmation = (OrderConfirmation) orderConfirmationField.getSelectedItem();

            try{
                orderConfirmationListRemote.matchOrderConfirmation(orderConfirmation, purchaseOrder);
                refreshItemTables();
                setDialogResponse(DialogResponse.SAVE);
            }catch ( Exception e){
                handleValidationException(e);
            }
        }
    }

    protected void onMatchPartial() {
        PurchaseOrderItem orderItem = (PurchaseOrderItem) orderItemsListPanel.getDataTable().getSelectedRowObject();
        OrderConfirmationItem confirmationitem = (OrderConfirmationItem) confirmationItemsListPanel.getDataTable().getSelectedRowObject();
        BigDecimal maxQuantityToMatch = null;
        // confirmation free qty > order pending qty
        if ( confirmationitem.getPendingQuantity().compareTo(orderItem.getPendingQuantity())>0 ){
            maxQuantityToMatch = orderItem.getPendingQuantity();
        }else{
            maxQuantityToMatch = confirmationitem.getPendingQuantity();
        }

        PartialQuantityForm quantityForm = new PartialQuantityForm(getParentDataObjectId(), maxQuantityToMatch);
        DialogResponse response = quantityForm.showDialog(this);
        if ( DialogResponse.SAVE.equals(response) ){
            //if 0, don't bother
            if ( new BigDecimal(0).compareTo(quantityForm.getQuantity())==0 )
                return;
            else
                matchItems(quantityForm.getQuantity());
        }
    }

    protected void onMatchItems() {
        matchItems(null);
    }

    private void matchItems(BigDecimal matchQuantity){
        PurchaseOrderItem orderItem = (PurchaseOrderItem) orderItemsListPanel.getDataTable().getSelectedRowObject();
        OrderConfirmationItem confirmationitem = (OrderConfirmationItem) confirmationItemsListPanel.getDataTable().getSelectedRowObject();
        try{
            orderConfirmationListRemote.matchConfirmationItem(confirmationitem, orderItem, matchQuantity);
            refreshItemTables();
            setDialogResponse(DialogResponse.SAVE);
        }catch ( Exception e){
            handleValidationException(e);
        }
    }

    private void refreshItemTables() {
        //update confirmation items
        OrderConfirmation orderConfirmation = (OrderConfirmation) orderConfirmationField.getSelectedItem();
        List<OrderConfirmationItem> items = null;
        if ( orderConfirmation!=null ){
            items = orderConfirmationListRemote.getPendingItems(orderConfirmation.getId());
        }else
            items = new ArrayList<OrderConfirmationItem>();
        confirmationItemsListPanel.refreshList(items);

        //update order items
        PurchaseOrder purchaseOrder = (PurchaseOrder) purchaseOrderField.getSelectedItem();
        List<PurchaseOrderItem> orderItems = getPendingItems(purchaseOrder);
        orderItemsListPanel.refreshList(orderItems);
    }

    protected void updateMatchItemButtons() {
        OrderConfirmationItem confirmationItem = (OrderConfirmationItem) confirmationItemsListPanel.getDataTable().getSelectedRowObject();
        PurchaseOrderItem orderItem = (PurchaseOrderItem) orderItemsListPanel.getDataTable().getSelectedRowObject();

        boolean enabled = false;
        if ( orderItem!=null && confirmationItem!=null && orderItem.getProduct().equals(confirmationItem.getProduct())){
            enabled = true;
        }

        matchItemsButton.setEnabled(enabled);
        matchPartialButton.setEnabled(enabled);
    }

    private void bindorderConfirmationField(List<PurchaseOrder> orders) {
        PurchaseOrderListPanel ordersListPanel = new PurchaseOrderListPanel(getParentDataObjectId(), orders);
        ordersListPanel.setVisibleButtons(ButtonVisibility.Select.getVisibilityIndex() |
            ButtonVisibility.Unselect.getVisibilityIndex() | ButtonVisibility.Close.getVisibilityIndex());
        purchaseOrderField.initUnbound(ordersListPanel, "${orderNumber} - ${supplier.displayName}");
        purchaseOrderField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if ( e.getItem() instanceof String )
                    return;
                onPurchaseOrderChanged((PurchaseOrder)e.getItem());
            }
        }, true);

        updateMatchItemButtons();
        updateAutoMatchButton();
    }

    protected void onOrderConfirmationChanged(OrderConfirmation orderConfirmation) {
        List<OrderConfirmationItem> items = null;
        List<PurchaseOrder> possibleOrders = null;
        if ( orderConfirmation!=null ){
            items = orderConfirmationListRemote.getPendingItems(orderConfirmation.getId());
            possibleOrders = purchaseOrderListRemote.getPendingOrders(getParentDataObjectId(), orderConfirmation.getSupplier(), getUserBranch());
        }
        else{
            items = new ArrayList<OrderConfirmationItem>();
            possibleOrders = purchaseOrderListRemote.getPendingOrders(getParentDataObjectId(), getUserBranch());
        }

        confirmationItemsListPanel.refreshList(items);
        bindorderConfirmationField(possibleOrders);

        if ( possibleOrders.size()==1 && orderConfirmation!=null ){
            purchaseOrderField.setSelectedItem(possibleOrders.get(0));
        }else{
            orderItemsListPanel.refreshList(new ArrayList<PurchaseOrderItem>());
        }

        updateAutoMatchButton();
    }

    private void updateAutoMatchButton() {
        if ( purchaseOrderField.getSelectedItem() instanceof PurchaseOrder &&
                orderConfirmationField.getSelectedItem() instanceof OrderConfirmation ){
            boolean enabled = false;
            List<PurchaseOrderItem> orderItems = orderItemsListPanel.getItems();
            List<OrderConfirmationItem> confItems = confirmationItemsListPanel.getItems();
            for (OrderConfirmationItem orderConfirmationItem : confItems) {
                for (PurchaseOrderItem orderItem : orderItems) {
                    if ( orderItem.getProduct().equals(orderConfirmationItem.getProduct())){
                        enabled = true;
                        break;
                    }
                }
            }

            autoMatchButton.setEnabled(enabled);
        }
        else
            autoMatchButton.setEnabled(false);
    }

    protected void onPurchaseOrderChanged(PurchaseOrder order) {
        List<PurchaseOrderItem> items = null;
        if ( order!=null )
            items = getPendingItems(order);
        else
            items = new ArrayList<PurchaseOrderItem>();
        orderItemsListPanel.refreshList(items);

        autoSelectOrderItem();
        updateAutoMatchButton();
    }

    private List<PurchaseOrderItem> getPendingItems(PurchaseOrder order) {
        List<PurchaseOrderItem> items = purchaseOrderListRemote.getOrderItems(order.getId());
        List<PurchaseOrderItem> result = new ArrayList<PurchaseOrderItem>();

        for (PurchaseOrderItem item : items) {
            //ordered > confirmed
            if ( item.getConfirmedQuantity()==null ||
                    item.getOrderedQuantity().compareTo(item.getConfirmedQuantity())>0 ){
                result.add(item);
            }
        }

        return result;
    }

    public void setSelectedConfirmation(OrderConfirmation entity) {
        orderConfirmationField.setSelectedItem(entity);
    }
}