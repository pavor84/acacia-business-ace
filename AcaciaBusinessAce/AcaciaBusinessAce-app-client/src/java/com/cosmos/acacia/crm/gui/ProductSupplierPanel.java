/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProductSupplierPanel.java
 *
 * Created on 2009-2-15, 23:50:30
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.ProductSupplier;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnersListPanel;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.MigLayoutHelper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;

/*
Product code: [] Product name: []
Supplier border panel
    Supplier: comboList
    Product code: [] Product name: []

    Price per quantity: [] Order price: [] Currency: ComboBox
    Delivery Time: [] Min. order quantity: [] Max. order quantity: []
    [] Deliverable  [] Obsolete;
    Description border panel
*/

/**
 *
 * @author Miro
 */
public class ProductSupplierPanel extends BaseEntityPanel {

    @EJB
    private static ProductsListRemote formSession;

    /** Creates new form ProductSupplierPanel */
    public ProductSupplierPanel() {
        this(null);
    }

    public ProductSupplierPanel(ProductSupplier productSupplier) {
        super((BigInteger)null);
        this.productSupplier = productSupplier;
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

        supplierPanel = new com.cosmos.swingb.JBPanel();
        supplierLabel = new com.cosmos.swingb.JBLabel();
        supplierComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        supplierProductCodeLabel = new com.cosmos.swingb.JBLabel();
        supplierProductNameLabel = new com.cosmos.swingb.JBLabel();
        supplierProductNameTextField = new com.cosmos.swingb.JBTextField();
        pricePerQuantityTextField = new com.cosmos.swingb.JBFormattedTextField();
        pricePerQuantityLabel = new com.cosmos.swingb.JBLabel();
        orderPriceLabel = new com.cosmos.swingb.JBLabel();
        orderPriceTextField = new com.cosmos.swingb.JBFormattedTextField();
        currencyLabel = new com.cosmos.swingb.JBLabel();
        currencyComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        deliveryTimeLabel = new com.cosmos.swingb.JBLabel();
        deliveryTimeTextField = new com.cosmos.swingb.JBFormattedTextField();
        minOrderQuantityLabel = new com.cosmos.swingb.JBLabel();
        minOrderQuantityTextField = new com.cosmos.swingb.JBFormattedTextField();
        maxOrderQuantityLabel = new com.cosmos.swingb.JBLabel();
        maxOrderQuantityTextField = new com.cosmos.swingb.JBFormattedTextField();
        deliverableCheckBox = new com.cosmos.swingb.JBCheckBox();
        obsoleteCheckBox = new com.cosmos.swingb.JBCheckBox();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        measureUnitComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        measureUnitLabel = new com.cosmos.swingb.JBLabel();
        supplierProductCodeTextField = new com.cosmos.swingb.JBTextField();
        simpleProductPanel = new com.cosmos.swingb.JBPanel();
        productCodeLabel = new com.cosmos.swingb.JBLabel();
        productCodeTextField = new com.cosmos.swingb.JBFormattedTextField();
        productNameLabel = new com.cosmos.swingb.JBLabel();
        productNameTextField = new com.cosmos.swingb.JBTextField();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductSupplierPanel.class);
        supplierPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("supplierPanel.border.title"))); // NOI18N
        supplierPanel.setName("supplierPanel"); // NOI18N

        supplierLabel.setText(resourceMap.getString("supplierLabel.text")); // NOI18N
        supplierLabel.setName("supplierLabel"); // NOI18N

        supplierComboList.setName("supplierComboList"); // NOI18N

        supplierProductCodeLabel.setText(resourceMap.getString("supplierProductCodeLabel.text")); // NOI18N
        supplierProductCodeLabel.setName("supplierProductCodeLabel"); // NOI18N

        supplierProductNameLabel.setText(resourceMap.getString("supplierProductNameLabel.text")); // NOI18N
        supplierProductNameLabel.setName("supplierProductNameLabel"); // NOI18N

        supplierProductNameTextField.setName("supplierProductNameTextField"); // NOI18N

        pricePerQuantityTextField.setName("pricePerQuantityTextField"); // NOI18N

        pricePerQuantityLabel.setText(resourceMap.getString("pricePerQuantityLabel.text")); // NOI18N
        pricePerQuantityLabel.setName("pricePerQuantityLabel"); // NOI18N

        orderPriceLabel.setText(resourceMap.getString("orderPriceLabel.text")); // NOI18N
        orderPriceLabel.setName("orderPriceLabel"); // NOI18N

        orderPriceTextField.setName("orderPriceTextField"); // NOI18N

        currencyLabel.setText(resourceMap.getString("currencyLabel.text")); // NOI18N
        currencyLabel.setName("currencyLabel"); // NOI18N

        currencyComboBox.setName("currencyComboBox"); // NOI18N

        deliveryTimeLabel.setText(resourceMap.getString("deliveryTimeLabel.text")); // NOI18N
        deliveryTimeLabel.setName("deliveryTimeLabel"); // NOI18N

        deliveryTimeTextField.setName("deliveryTimeTextField"); // NOI18N

        minOrderQuantityLabel.setText(resourceMap.getString("minOrderQuantityLabel.text")); // NOI18N
        minOrderQuantityLabel.setName("minOrderQuantityLabel"); // NOI18N

        minOrderQuantityTextField.setName("minOrderQuantityTextField"); // NOI18N

        maxOrderQuantityLabel.setText(resourceMap.getString("maxOrderQuantityLabel.text")); // NOI18N
        maxOrderQuantityLabel.setName("maxOrderQuantityLabel"); // NOI18N

        maxOrderQuantityTextField.setName("maxOrderQuantityTextField"); // NOI18N

        deliverableCheckBox.setText(resourceMap.getString("deliverableCheckBox.text")); // NOI18N
        deliverableCheckBox.setName("deliverableCheckBox"); // NOI18N

        obsoleteCheckBox.setText(resourceMap.getString("obsoleteCheckBox.text")); // NOI18N
        obsoleteCheckBox.setName("obsoleteCheckBox"); // NOI18N

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("descriptionPanel.border.title"))); // NOI18N
        descriptionPanel.setName("descriptionPanel"); // NOI18N
        descriptionPanel.setLayout(new java.awt.BorderLayout());

        descriptionScrollPane.setName("descriptionScrollPane"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        descriptionScrollPane.setViewportView(descriptionTextPane);

        descriptionPanel.add(descriptionScrollPane, java.awt.BorderLayout.CENTER);

        measureUnitComboBox.setName("measureUnitComboBox"); // NOI18N

        measureUnitLabel.setText(resourceMap.getString("measureUnitLabel.text")); // NOI18N
        measureUnitLabel.setName("measureUnitLabel"); // NOI18N

        supplierProductCodeTextField.setName("supplierProductCodeTextField"); // NOI18N

        javax.swing.GroupLayout supplierPanelLayout = new javax.swing.GroupLayout(supplierPanel);
        supplierPanel.setLayout(supplierPanelLayout);
        supplierPanelLayout.setHorizontalGroup(
            supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, supplierPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, supplierPanelLayout.createSequentialGroup()
                        .addComponent(deliverableCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(obsoleteCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, supplierPanelLayout.createSequentialGroup()
                        .addComponent(supplierLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplierComboList, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(measureUnitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(measureUnitComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, supplierPanelLayout.createSequentialGroup()
                        .addComponent(supplierProductCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplierProductCodeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(supplierProductNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplierProductNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, supplierPanelLayout.createSequentialGroup()
                        .addComponent(pricePerQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pricePerQuantityTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orderPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orderPriceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(currencyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currencyComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, supplierPanelLayout.createSequentialGroup()
                        .addComponent(deliveryTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deliveryTimeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(minOrderQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(minOrderQuantityTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maxOrderQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maxOrderQuantityTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)))
                .addContainerGap())
        );
        supplierPanelLayout.setVerticalGroup(
            supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(supplierLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(measureUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(measureUnitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(supplierComboList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supplierProductCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supplierProductNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supplierProductNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supplierProductCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pricePerQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pricePerQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(orderPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(orderPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currencyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currencyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deliveryTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deliveryTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minOrderQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minOrderQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxOrderQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxOrderQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deliverableCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(obsoleteCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(supplierPanel, java.awt.BorderLayout.CENTER);

        simpleProductPanel.setName("simpleProductPanel"); // NOI18N

        productCodeLabel.setText(resourceMap.getString("productCodeLabel.text")); // NOI18N
        productCodeLabel.setName("productCodeLabel"); // NOI18N

        productCodeTextField.setName("productCodeTextField"); // NOI18N

        productNameLabel.setText(resourceMap.getString("productNameLabel.text")); // NOI18N
        productNameLabel.setName("productNameLabel"); // NOI18N

        productNameTextField.setName("productNameTextField"); // NOI18N

        javax.swing.GroupLayout simpleProductPanelLayout = new javax.swing.GroupLayout(simpleProductPanel);
        simpleProductPanel.setLayout(simpleProductPanelLayout);
        simpleProductPanelLayout.setHorizontalGroup(
            simpleProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simpleProductPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productCodeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(productNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );
        simpleProductPanelLayout.setVerticalGroup(
            simpleProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simpleProductPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(simpleProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(simpleProductPanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.AcaciaComboBox currencyComboBox;
    private com.cosmos.swingb.JBLabel currencyLabel;
    private com.cosmos.swingb.JBCheckBox deliverableCheckBox;
    private com.cosmos.swingb.JBLabel deliveryTimeLabel;
    private com.cosmos.swingb.JBFormattedTextField deliveryTimeTextField;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.swingb.JBLabel maxOrderQuantityLabel;
    private com.cosmos.swingb.JBFormattedTextField maxOrderQuantityTextField;
    private com.cosmos.acacia.gui.AcaciaComboBox measureUnitComboBox;
    private com.cosmos.swingb.JBLabel measureUnitLabel;
    private com.cosmos.swingb.JBLabel minOrderQuantityLabel;
    private com.cosmos.swingb.JBFormattedTextField minOrderQuantityTextField;
    private com.cosmos.swingb.JBCheckBox obsoleteCheckBox;
    private com.cosmos.swingb.JBLabel orderPriceLabel;
    private com.cosmos.swingb.JBFormattedTextField orderPriceTextField;
    private com.cosmos.swingb.JBLabel pricePerQuantityLabel;
    private com.cosmos.swingb.JBFormattedTextField pricePerQuantityTextField;
    private com.cosmos.swingb.JBLabel productCodeLabel;
    private com.cosmos.swingb.JBFormattedTextField productCodeTextField;
    private com.cosmos.swingb.JBLabel productNameLabel;
    private com.cosmos.swingb.JBTextField productNameTextField;
    private com.cosmos.swingb.JBPanel simpleProductPanel;
    private com.cosmos.acacia.gui.AcaciaComboList supplierComboList;
    private com.cosmos.swingb.JBLabel supplierLabel;
    private com.cosmos.swingb.JBPanel supplierPanel;
    private com.cosmos.swingb.JBLabel supplierProductCodeLabel;
    private com.cosmos.swingb.JBTextField supplierProductCodeTextField;
    private com.cosmos.swingb.JBLabel supplierProductNameLabel;
    private com.cosmos.swingb.JBTextField supplierProductNameTextField;
    // End of variables declaration//GEN-END:variables


    private ProductSupplier productSupplier;
    private EntityFormButtonPanel buttonPanel;
    private BindingGroup bindingGroup;

    @Override
    protected void initData() {
        if(productSupplier == null) {
            productSupplier = new ProductSupplier();
        }

        buttonPanel = new EntityFormButtonPanel();
        buttonPanel.setVisible(EntityFormButtonPanel.Button.Print, false);
        add(buttonPanel, BorderLayout.PAGE_END);

        MigLayoutHelper helper = new MigLayoutHelper(simpleProductPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(6);
        helper.columnGrow(30, 1, 3, 5);
        helper.columnSizeGroup("sg", 1, 3, 5);
        helper.columnFill(1, 3, 5);
        helper.columnGap("15", 1, 3);
        // 1st row
        helper.getComponentConstraints(productCodeLabel).cell(0, 1);
        helper.getComponentConstraints(productCodeTextField).cell(1, 1);
        helper.getComponentConstraints(productNameLabel).cell(2, 1);
        helper.getComponentConstraints(productNameTextField).cell(3, 1).spanX(3);
        simpleProductPanel.invalidate();

        helper = new MigLayoutHelper(supplierPanel);
        helper.setLayoutFillX(true);
        //helper.layoutWidth("640:640:800");
        helper.setLayoutWrapAfter(6);
        helper.columnGrow(30, 1, 3, 5);
        helper.columnSizeGroup("sg", 1, 3, 5);
        helper.columnFill(1, 3, 5);
        helper.columnGap("15", 1, 3);
        // 1st row
        helper.getComponentConstraints(supplierLabel).cell(0, 0);
        helper.getComponentConstraints(supplierComboList).cell(1, 0).spanX(3);
        helper.getComponentConstraints(measureUnitLabel).cell(4, 0);
        helper.getComponentConstraints(measureUnitComboBox).cell(5, 0);
        // 2nd row
        helper.getComponentConstraints(supplierProductCodeLabel).cell(0, 1);
        helper.getComponentConstraints(supplierProductCodeTextField).cell(1, 1);
        helper.getComponentConstraints(supplierProductNameLabel).cell(2, 1);
        helper.getComponentConstraints(supplierProductNameTextField).cell(3, 1).spanX(3);
        // 3rd row
        helper.getComponentConstraints(pricePerQuantityLabel).cell(0, 2);
        helper.getComponentConstraints(pricePerQuantityTextField).cell(1, 2);
        helper.getComponentConstraints(orderPriceLabel).cell(2, 2);
        helper.getComponentConstraints(orderPriceTextField).cell(3, 2);
        helper.getComponentConstraints(currencyLabel).cell(4, 2);
        helper.getComponentConstraints(currencyComboBox).cell(5, 2);
        // 4th row
        helper.getComponentConstraints(deliveryTimeLabel).cell(0, 3);
        helper.getComponentConstraints(deliveryTimeTextField).cell(1, 3);
        helper.getComponentConstraints(minOrderQuantityLabel).cell(2, 3);
        helper.getComponentConstraints(minOrderQuantityTextField).cell(3, 3);
        helper.getComponentConstraints(maxOrderQuantityLabel).cell(4, 3);
        helper.getComponentConstraints(maxOrderQuantityTextField).cell(5, 3);
        // 5th row
        helper.getComponentConstraints(deliverableCheckBox).cell(0, 4).spanX(2);
        helper.getComponentConstraints(obsoleteCheckBox).cell(2, 4).spanX(2);
        // 6th row
        helper.getComponentConstraints(descriptionPanel).cell(0, 5).spanX(6).height("100%").width("100%");
        supplierPanel.invalidate();

        SimpleProduct product = productSupplier.getProduct();
        productCodeTextField.setEditable(false);
        productCodeTextField.setText(product.getProductCode());
        productNameTextField.setEditable(false);
        productNameTextField.setText(product.getProductName());

        BindingGroup bg = getBindingGroup();
        EntityProperties entityProps = getEntityProperties();

        PropertyDetails propDetails = entityProps.getPropertyDetails("supplier");
        Classifier classifier = getClassifier(Classifier.Supplier.getClassifierCode());
        BusinessPartnersListPanel producerListPanel = new BusinessPartnersListPanel(classifier);
        supplierComboList.bind(bindingGroup, producerListPanel, productSupplier, propDetails,
            "${displayName}", UpdateStrategy.READ_WRITE);

        propDetails = entityProps.getPropertyDetails("measureUnit");
        measureUnitComboBox.bind(bindingGroup, getMeasureUnits(), productSupplier, propDetails);

        propDetails = entityProps.getPropertyDetails("productCode");
        supplierProductCodeTextField.bind(bindingGroup, productSupplier, propDetails);

        propDetails = entityProps.getPropertyDetails("productName");
        supplierProductNameTextField.bind(bindingGroup, productSupplier, propDetails);

        propDetails = entityProps.getPropertyDetails("pricePerQuantity");
        pricePerQuantityTextField.bind(bindingGroup, productSupplier, propDetails, AcaciaUtils.getIntegerFormat());

        propDetails = entityProps.getPropertyDetails("orderPrice");
        orderPriceTextField.bind(bindingGroup, productSupplier, propDetails, AcaciaUtils.getDecimalFormat());

        propDetails = entityProps.getPropertyDetails("currency");
        currencyComboBox.bind(bindingGroup, getCurrencies(), productSupplier, propDetails);

        propDetails = entityProps.getPropertyDetails("deliveryTime");
        deliveryTimeTextField.bind(bindingGroup, productSupplier, propDetails, AcaciaUtils.getIntegerFormat());

        propDetails = entityProps.getPropertyDetails("minOrderQuantity");
        minOrderQuantityTextField.bind(bindingGroup, productSupplier, propDetails, AcaciaUtils.getIntegerFormat());

        propDetails = entityProps.getPropertyDetails("maxOrderQuantity");
        maxOrderQuantityTextField.bind(bindingGroup, productSupplier, propDetails, AcaciaUtils.getIntegerFormat());

        propDetails = entityProps.getPropertyDetails("deliverable");
        deliverableCheckBox.bind(bindingGroup, productSupplier, propDetails);

        propDetails = entityProps.getPropertyDetails("obsolete");
        obsoleteCheckBox.bind(bindingGroup, productSupplier, propDetails);

        propDetails = entityProps.getPropertyDetails("description");
        descriptionTextPane.bind(bindingGroup, productSupplier, propDetails);

        bg.bind();
    }

    private List<DbResource> getCurrencies() {
        return getFormSession().getCurrencies();
    }

    private List<DbResource> getMeasureUnits()
    {
        return getFormSession().getMeasureUnits();
    }

    @Override
    public void performSave(boolean closeAfter) {
        getFormSession().saveProductSupplier(productSupplier);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(productSupplier);
        if(closeAfter)
            close();
    }

    @Override
    public BindingGroup getBindingGroup() {
        if(bindingGroup == null)
            bindingGroup = new BindingGroup();

        return bindingGroup;
    }

    @Override
    public Object getEntity() {
        return productSupplier;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    private EntityProperties getEntityProperties() {
        return getFormSession().getProductSupplierEntityProperties();
    }

    protected ProductsListRemote getFormSession() {
        if(formSession == null)
            formSession = getBean(ProductsListRemote.class);

        return formSession;
    }
}