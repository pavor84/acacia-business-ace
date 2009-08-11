/*
 * PurchaseOrderItemForm.java
 *
 * Created on Неделя, 2008, Юли 20, 13:53
 */

package com.cosmos.acacia.crm.gui.purchaseorders;

import static com.cosmos.acacia.util.AcaciaUtils.getDecimalFormat;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.List;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.bl.purchaseorder.PurchaseOrderListRemote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.PurchaseOrderItem;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.WarehouseProduct;
import com.cosmos.acacia.crm.gui.ProductsListPanel;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	20.07.2008
 * @author	Petar Milev
 *
 */
public class PurchaseOrderItemForm extends BaseEntityPanel {
    
    private PurchaseOrderItem entity;

    /** Creates new form PurchaseOrderFormDraft */
    public PurchaseOrderItemForm(PurchaseOrderItem item) {
        super(item.getParentId());
        this.entity = item;
        initialize();
    }

    private void initialize() {
        initComponents();
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

        jBLabel1 = new com.cosmos.swingb.JBLabel();
        measureUnitField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        orderedQuantityField = new com.cosmos.swingb.JBFormattedTextField();
        confirmedQuantityField = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        jBLabel5 = new com.cosmos.swingb.JBLabel();
        deliveredQuantityField = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel6 = new com.cosmos.swingb.JBLabel();
        purchasePriceField = new com.cosmos.swingb.JBFormattedTextField();
        currencyField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel7 = new com.cosmos.swingb.JBLabel();
        jBLabel8 = new com.cosmos.swingb.JBLabel();
        shipDateFromField = new com.cosmos.swingb.JBDatePicker();
        formButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        productField = new com.cosmos.acacia.gui.AcaciaComboList();
        shipDateToField = new com.cosmos.swingb.JBDatePicker();
        jBLabel9 = new com.cosmos.swingb.JBLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notesField = new com.cosmos.swingb.JBTextPane();
        jBLabel10 = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PurchaseOrderItemForm.class);
        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        measureUnitField.setName("measureUnitField"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        orderedQuantityField.setName("orderedQuantityField"); // NOI18N

        confirmedQuantityField.setEditable(false);
        confirmedQuantityField.setName("confirmedQuantityField"); // NOI18N

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        jBLabel5.setText(resourceMap.getString("jBLabel5.text")); // NOI18N
        jBLabel5.setName("jBLabel5"); // NOI18N

        deliveredQuantityField.setEditable(false);
        deliveredQuantityField.setName("deliveredQuantityField"); // NOI18N

        jBLabel6.setText(resourceMap.getString("jBLabel6.text")); // NOI18N
        jBLabel6.setName("jBLabel6"); // NOI18N

        purchasePriceField.setName("purchasePriceField"); // NOI18N

        currencyField.setName("currencyField"); // NOI18N

        jBLabel7.setText(resourceMap.getString("jBLabel7.text")); // NOI18N
        jBLabel7.setName("jBLabel7"); // NOI18N

        jBLabel8.setText(resourceMap.getString("jBLabel8.text")); // NOI18N
        jBLabel8.setName("jBLabel8"); // NOI18N

        shipDateFromField.setName("shipDateFromField"); // NOI18N

        formButtonPanel.setName("formButtonPanel"); // NOI18N

        productField.setName("productField"); // NOI18N

        shipDateToField.setName("shipDateToField"); // NOI18N

        jBLabel9.setText(resourceMap.getString("jBLabel9.text")); // NOI18N
        jBLabel9.setName("jBLabel9"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        notesField.setName("notesField"); // NOI18N
        jScrollPane1.setViewportView(notesField);

        jBLabel10.setText(resourceMap.getString("jBLabel10.text")); // NOI18N
        jBLabel10.setName("jBLabel10"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(formButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                    .addComponent(jBLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                    .addComponent(jBLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                    .addComponent(jBLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                    .addComponent(jBLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addComponent(jBLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(jBLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                .addGap(2, 2, 2))
                            .addComponent(jBLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(jBLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(shipDateToField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(shipDateFromField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(purchasePriceField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(deliveredQuantityField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(confirmedQuantityField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(orderedQuantityField, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(measureUnitField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(currencyField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(measureUnitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orderedQuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmedQuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deliveredQuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purchasePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currencyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shipDateFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shipDateToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(formButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBFormattedTextField confirmedQuantityField;
    private com.cosmos.acacia.gui.AcaciaComboBox currencyField;
    private com.cosmos.swingb.JBFormattedTextField deliveredQuantityField;
    private com.cosmos.acacia.gui.EntityFormButtonPanel formButtonPanel;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel10;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private com.cosmos.swingb.JBLabel jBLabel5;
    private com.cosmos.swingb.JBLabel jBLabel6;
    private com.cosmos.swingb.JBLabel jBLabel7;
    private com.cosmos.swingb.JBLabel jBLabel8;
    private com.cosmos.swingb.JBLabel jBLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private com.cosmos.acacia.gui.AcaciaComboBox measureUnitField;
    private com.cosmos.swingb.JBTextPane notesField;
    private com.cosmos.swingb.JBFormattedTextField orderedQuantityField;
    private com.cosmos.acacia.gui.AcaciaComboList productField;
    private com.cosmos.swingb.JBFormattedTextField purchasePriceField;
    private com.cosmos.swingb.JBDatePicker shipDateFromField;
    private com.cosmos.swingb.JBDatePicker shipDateToField;
    // End of variables declaration//GEN-END:variables
    private BindingGroup bindingGroup;
    private PurchaseOrderListRemote formSession;
    private EntityProperties entProps;
    private ProductsListRemote productListRemote;
    
    @Override
    public BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return formButtonPanel;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @Override
    public void performSave(boolean closeAfter) {
        entity = getFormSession().saveOrderItem(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if (closeAfter) {
            close();
        } else {
            getBindingGroup().unbind();
            bindingGroup = null;
            initData();
        }
    }

    private PurchaseOrderListRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(PurchaseOrderListRemote.class);
        return formSession;
    }

    @Override
    protected void initData() {
        entProps = getFormSession().getItemDetailEntityProperties();
        
        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(measureUnitField, resourceToStringConverter);
        
        BindingGroup bg = getBindingGroup();
        
        //product
        PropertyDetails pd = entProps.getPropertyDetails("product");
        pd.setRequired(true);
        ProductsListPanel listPanel = new ProductsListPanel(getOrganizationDataObjectId());
        productField.bind(
            bg,
            listPanel,
            entity,
            pd,
            "${productName}",
            UpdateStrategy.READ_WRITE);
        productField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                onSelectProduct(e.getItem());
            }
        }, true);
        
        measureUnitField.bind(bg, getMeasureUnits(), entity, entProps.getPropertyDetails("measureUnit"));
        //ordered quantity
        orderedQuantityField.bind(bg, entity, entProps.getPropertyDetails("orderedQuantity"), getDecimalFormat());
        
        //confirmed quantity
        confirmedQuantityField.bind(bg, entity, entProps.getPropertyDetails("confirmedQuantity"), getDecimalFormat());
        
        //delivered quantity
        deliveredQuantityField.bind(bg, entity, entProps.getPropertyDetails("deliveredQuantity"), getDecimalFormat());
        
        //purchase price
        purchasePriceField.bind(bg, entity, entProps.getPropertyDetails("purchasePrice"), getDecimalFormat());
        
        //currency
        currencyField.bind(bg, getCurrencies(), entity, entProps.getPropertyDetails("currency"));
        
        //ship date from
        shipDateFromField.bind(bg, entity, entProps.getPropertyDetails("shipDateFrom"), AcaciaUtils.getShortDateFormat());
        
        //ship date to
        shipDateToField.bind(bg, entity, entProps.getPropertyDetails("shipDateTo"), AcaciaUtils.getShortDateFormat());
        
        //notes
        notesField.bind(bg, entity, entProps.getPropertyDetails("notes"));
        
        bg.bind();
    }

    private List<DbResource> getCurrencies() {
        return getFormSession().getCurrencies();
    }

    @SuppressWarnings("unchecked")
    protected void onSelectProduct(Object item) {
        if ( item instanceof SimpleProduct){
            SimpleProduct product = entity.getProduct();
            
            DbResource measureMentUnit = null;
            BigDecimal defaultQty = null;
            BigDecimal purchasePrice = null;
            
            WarehouseProduct warehouseProduct = getFormSession().getWarehouseProduct(product);
            
            measureMentUnit = product.getMeasureUnit();
            defaultQty = product.getDefaultQuantity();
            purchasePrice = product.getPurchasePrice();
            
            if ( warehouseProduct!=null ){
                defaultQty = warehouseProduct.getPrefferedDefaultQuantity();
                if ( warehouseProduct.getPurchasePrice()!=null )
                    purchasePrice = warehouseProduct.getPurchasePrice();
            }
            
            measureUnitField.setSelectedItem(measureMentUnit);
            if ( defaultQty!=null && (orderedQuantityField.getText()==null || orderedQuantityField.getText().equals("")))
                orderedQuantityField.setValue(defaultQty);
            if ( purchasePrice!=null &&(purchasePriceField.getText()==null || purchasePriceField.getText().equals("")))
                purchasePriceField.setValue(purchasePrice);
        }else{
            orderedQuantityField.setValue(null);
            purchasePriceField.setValue(null);
        }
    }
    
    private List<DbResource> getMeasureUnits()
    {
        return getProductListRemote().getMeasureUnits();
    }

    private ProductsListRemote getProductListRemote() {
        if ( productListRemote==null )
            productListRemote = getBean(ProductsListRemote.class);
        return productListRemote;
    }
    
    @Override
    public void setReadonly() {
        super.setReadonly();
        productField.setEditable(false);
    }
}
