/*
 * InvoiceItemPanel.java
 *
 * Created on May 5, 2008, 2:45 PM
 */

package com.cosmos.acacia.crm.gui.invoice;

import com.cosmos.acacia.crm.bl.invoice.impl.InvoiceItemsListRemote;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.gui.ProductsListPanel;
import com.cosmos.acacia.crm.gui.warehouse.WarehouseListPanel;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author  rlozanov
 */
public class InvoiceItemPanel extends BaseEntityPanel {
    protected static Logger log = Logger.getLogger(InvoiceItemPanel.class);
    
    public InvoiceItemPanel(BigInteger parentDataobject) {
        super(parentDataobject);
        
        init();
    }
    
    public InvoiceItemPanel(InvoiceItem invoiceItem) {
        super(invoiceItem.getDataObject().getParentDataObjectId());
        
        this.invoiceItem = invoiceItem;
        
        init();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        productPanel = new com.cosmos.swingb.JBPanel();
        productLabel = new com.cosmos.swingb.JBLabel();
        quantityLabel = new com.cosmos.swingb.JBLabel();
        quantityTextField = new com.cosmos.swingb.JBTextField();
        unitPriceLabel = new com.cosmos.swingb.JBLabel();
        unitPriceTextField = new com.cosmos.swingb.JBTextField();
        productComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        measureUnitComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        warehousePanel = new com.cosmos.swingb.JBPanel();
        warehouseLabel = new com.cosmos.swingb.JBLabel();
        warehouseComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(InvoiceItemPanel.class);
        productPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("productPanel.border.title"))); // NOI18N
        productPanel.setName("productPanel"); // NOI18N

        productLabel.setText(resourceMap.getString("productLabel.text")); // NOI18N
        productLabel.setName("productLabel"); // NOI18N

        quantityLabel.setText(resourceMap.getString("quantityLabel.text")); // NOI18N
        quantityLabel.setName("quantityLabel"); // NOI18N

        quantityTextField.setText(resourceMap.getString("quantityTextField.text")); // NOI18N
        quantityTextField.setName("quantityTextField"); // NOI18N

        unitPriceLabel.setText(resourceMap.getString("unitPriceLabel.text")); // NOI18N
        unitPriceLabel.setName("unitPriceLabel"); // NOI18N

        unitPriceTextField.setText(resourceMap.getString("unitPriceTextField.text")); // NOI18N
        unitPriceTextField.setName("unitPriceTextField"); // NOI18N

        productComboList.setName("productComboList"); // NOI18N

        measureUnitComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        measureUnitComboBox.setName("measureUnitComboBox"); // NOI18N

        javax.swing.GroupLayout productPanelLayout = new javax.swing.GroupLayout(productPanel);
        productPanel.setLayout(productPanelLayout);
        productPanelLayout.setHorizontalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unitPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(unitPriceTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(quantityTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(measureUnitComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                    .addComponent(productComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                .addContainerGap())
        );
        productPanelLayout.setVerticalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(productLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productComboList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(measureUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unitPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        warehousePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("warehousePanel.border.title"))); // NOI18N
        warehousePanel.setName("warehousePanel"); // NOI18N

        warehouseLabel.setText(resourceMap.getString("warehouseLabel.text")); // NOI18N
        warehouseLabel.setName("warehouseLabel"); // NOI18N

        warehouseComboList.setName("warehouseComboList"); // NOI18N

        javax.swing.GroupLayout warehousePanelLayout = new javax.swing.GroupLayout(warehousePanel);
        warehousePanel.setLayout(warehousePanelLayout);
        warehousePanelLayout.setHorizontalGroup(
            warehousePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(warehousePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(warehouseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(warehouseComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );
        warehousePanelLayout.setVerticalGroup(
            warehousePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(warehousePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(warehousePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(warehouseComboList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(warehouseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addComponent(warehousePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(productPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(warehousePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void init() {
        initComponents();
        super.init();
    }
    
    @Override
    public void performSave(boolean closeAfter) {
        log.info("Save: invoiceItem: " + invoiceItem);
        
        invoiceItem = getFormSession().saveInvoiceItem(invoiceItem);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(invoiceItem);
        
        if (closeAfter) {
            close();
        }
    }

    @Override
    public BindingGroup getBindingGroup() {
        return invoiceItemBindingGroup;
    }

    @Override
    public Object getEntity() {
        return invoiceItem;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    @Override
    protected void initData() {
        setResizable(false);
        
        if (invoiceItem == null) {
            invoiceItem = getFormSession().newInvoiceItem(getParentDataObjectId());
        }
        
        invoiceItemBindingGroup = new BindingGroup();
        
        EntityProperties entityProps = getFormSession().getInvoiceItemsEntityProperties();
    
        quantityTextField.bind(invoiceItemBindingGroup, invoiceItem, entityProps.getPropertyDetails("orderedQuantity"));
        unitPriceTextField.bind(invoiceItemBindingGroup, invoiceItem, entityProps.getPropertyDetails("unitPrice"));
            
        
        // ComboBoxes
        measureUnitComboBox.bind(invoiceItemBindingGroup, getFormSession().getMeasureUnits(), invoiceItem,
                                 entityProps.getPropertyDetails("measureUnit"));
        
        ProductsListPanel productsTable = new ProductsListPanel(null);
        productComboList.initUnbound(productsTable);
        productComboList.bind(invoiceItemBindingGroup, productsTable,
                              invoiceItem, entityProps.getPropertyDetails("product"));
        
        WarehouseListPanel warehouseTable = new WarehouseListPanel();
        warehouseComboList.initUnbound(warehouseTable);
        warehouseComboList.bind(invoiceItemBindingGroup, warehouseTable,
                              invoiceItem, entityProps.getPropertyDetails("warehouse"));

        invoiceItemBindingGroup.bind();
    }
    
    protected InvoiceItemsListRemote getFormSession() {
        if (this.formSession == null) {
            try {
                this.formSession =
                InitialContext.doLookup(InvoiceItemsListRemote.class.getName());
            } catch (NamingException ex) {
                ex.printStackTrace();
            }
        }
        
        return this.formSession;
    }

    protected Object onChooseProduct() {
        ProductsListPanel listPanel = new ProductsListPanel(null);

        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            return listPanel.getSelectedRowObject();
        }
        return null;
    }

    protected Object onChooseWearhouse() {
        WarehouseListPanel listPanel = new WarehouseListPanel(null);

        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            return listPanel.getSelectedRowObject();
        }
        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.acacia.gui.AcaciaComboBox measureUnitComboBox;
    private com.cosmos.acacia.gui.AcaciaComboList productComboList;
    private com.cosmos.swingb.JBLabel productLabel;
    private com.cosmos.swingb.JBPanel productPanel;
    private com.cosmos.swingb.JBLabel quantityLabel;
    private com.cosmos.swingb.JBTextField quantityTextField;
    private com.cosmos.swingb.JBLabel unitPriceLabel;
    private com.cosmos.swingb.JBTextField unitPriceTextField;
    private com.cosmos.acacia.gui.AcaciaComboList warehouseComboList;
    private com.cosmos.swingb.JBLabel warehouseLabel;
    private com.cosmos.swingb.JBPanel warehousePanel;
    // End of variables declaration//GEN-END:variables

    private InvoiceItem invoiceItem;
    
    @EJB
    private InvoiceItemsListRemote formSession;
    
    private BindingGroup invoiceItemBindingGroup;
}