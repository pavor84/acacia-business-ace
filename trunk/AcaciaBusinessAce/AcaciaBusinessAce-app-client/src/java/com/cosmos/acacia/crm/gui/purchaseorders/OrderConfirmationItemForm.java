/*
 * OrderConfirmationItemForm.java
 *
 * Created on Понеделник, 2008, Юли 21, 16:58
 */

package com.cosmos.acacia.crm.gui.purchaseorders;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.bl.purchaseorder.OrderConfirmationListRemote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.OrderConfirmationItem;
import com.cosmos.acacia.crm.gui.ProductsListPanel;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	22.07.2008
 * @author	Petar Milev
 *
 */
public class OrderConfirmationItemForm extends BaseEntityPanel {

    private OrderConfirmationItem entity;

    /** Creates new form OrderConfirmationFormDraft */
    public OrderConfirmationItemForm(OrderConfirmationItem item) {
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
        productField = new com.cosmos.acacia.gui.AcaciaComboList();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        measureUnitField = new com.cosmos.swingb.JBComboBox();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        confirmedQuantityField = new com.cosmos.swingb.JBTextField();
        unitPriceField = new com.cosmos.swingb.JBTextField();
        jBLabel6 = new com.cosmos.swingb.JBLabel();
        jBLabel8 = new com.cosmos.swingb.JBLabel();
        extendedPriceField = new com.cosmos.swingb.JBTextField();
        jBLabel9 = new com.cosmos.swingb.JBLabel();
        shipDateFromField = new com.cosmos.swingb.JBDatePicker();
        shipDateToField = new com.cosmos.swingb.JBDatePicker();
        jBLabel10 = new com.cosmos.swingb.JBLabel();
        jBLabel11 = new com.cosmos.swingb.JBLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notesField = new com.cosmos.swingb.JBTextPane();
        formButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        currencyField = new com.cosmos.swingb.JBComboBox();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        shipWeekField = new com.cosmos.swingb.JBTextField();
        jBLabel5 = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(OrderConfirmationItemForm.class);
        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        productField.setName("productField"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        measureUnitField.setName("measureUnitField"); // NOI18N

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        confirmedQuantityField.setName("confirmedQuantityField"); // NOI18N

        unitPriceField.setName("unitPriceField"); // NOI18N

        jBLabel6.setText(resourceMap.getString("jBLabel6.text")); // NOI18N
        jBLabel6.setName("jBLabel6"); // NOI18N

        jBLabel8.setText(resourceMap.getString("jBLabel8.text")); // NOI18N
        jBLabel8.setName("jBLabel8"); // NOI18N

        extendedPriceField.setEditable(false);
        extendedPriceField.setName("extendedPriceField"); // NOI18N

        jBLabel9.setText(resourceMap.getString("jBLabel9.text")); // NOI18N
        jBLabel9.setName("jBLabel9"); // NOI18N

        shipDateFromField.setName("shipDateFromField"); // NOI18N

        shipDateToField.setName("shipDateToField"); // NOI18N

        jBLabel10.setText(resourceMap.getString("jBLabel10.text")); // NOI18N
        jBLabel10.setName("jBLabel10"); // NOI18N

        jBLabel11.setText(resourceMap.getString("jBLabel11.text")); // NOI18N
        jBLabel11.setName("jBLabel11"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        notesField.setName("notesField"); // NOI18N
        jScrollPane1.setViewportView(notesField);

        formButtonPanel.setName("formButtonPanel"); // NOI18N

        currencyField.setName("currencyField"); // NOI18N

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        shipWeekField.setText(resourceMap.getString("shipWeekField.text")); // NOI18N
        shipWeekField.setName("shipWeekField"); // NOI18N

        jBLabel5.setText(resourceMap.getString("jBLabel5.text")); // NOI18N
        jBLabel5.setName("jBLabel5"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productField, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(measureUnitField, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmedQuantityField, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                        .addComponent(unitPriceField, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                    .addComponent(formButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jBLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currencyField, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(shipWeekField, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(extendedPriceField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .addGap(2, 2, 2))
                            .addComponent(jBLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(jBLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(shipDateToField, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(shipDateFromField, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))))
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
                    .addComponent(confirmedQuantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currencyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unitPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extendedPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shipWeekField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shipDateFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shipDateToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(formButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBTextField confirmedQuantityField;
    private com.cosmos.swingb.JBComboBox currencyField;
    private com.cosmos.swingb.JBTextField extendedPriceField;
    private com.cosmos.acacia.gui.EntityFormButtonPanel formButtonPanel;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel10;
    private com.cosmos.swingb.JBLabel jBLabel11;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private com.cosmos.swingb.JBLabel jBLabel5;
    private com.cosmos.swingb.JBLabel jBLabel6;
    private com.cosmos.swingb.JBLabel jBLabel8;
    private com.cosmos.swingb.JBLabel jBLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private com.cosmos.swingb.JBComboBox measureUnitField;
    private com.cosmos.swingb.JBTextPane notesField;
    private com.cosmos.acacia.gui.AcaciaComboList productField;
    private com.cosmos.swingb.JBDatePicker shipDateFromField;
    private com.cosmos.swingb.JBDatePicker shipDateToField;
    private com.cosmos.swingb.JBTextField shipWeekField;
    private com.cosmos.swingb.JBTextField unitPriceField;
    // End of variables declaration//GEN-END:variables

    private BindingGroup bindGroup;
    private OrderConfirmationListRemote formSession;
    private EntityProperties entProps;
    private ProductsListRemote productListRemote;
    private boolean updatingShipDates;
    
    @Override
    public BindingGroup getBindingGroup() {
        return bindGroup;
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
            bindGroup.unbind();
            initData();
        }
    }

    private OrderConfirmationListRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(OrderConfirmationListRemote.class);
        return formSession;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initData() {
        entProps = getFormSession().getItemDetailEntityProperties();
        
        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(measureUnitField, resourceToStringConverter);
        AutoCompleteDecorator.decorate(currencyField, resourceToStringConverter);
        
        bindGroup = new BindingGroup();
        
        //product
        PropertyDetails pd = entProps.getPropertyDetails("product");
        pd.setRequired(true);
        ProductsListPanel listPanel = new ProductsListPanel(getOrganizationDataObjectId());
        productField.bind(
            bindGroup, 
            listPanel,
            entity,
            pd,
            "${productName}",
            UpdateStrategy.READ_WRITE);
        productField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                onSelectProduct();
            }
        });
        
        //measure unit 
        measureUnitField.bind(bindGroup, getMeasureUnits(), entity, entProps.getPropertyDetails("measureUnit"));
        
        //confirmed quantity
        Binding confirmedQtyBinding = confirmedQuantityField.bind(bindGroup, entity, entProps.getPropertyDetails("confirmedQuantity"));
        confirmedQtyBinding.addBindingListener(new AbstractBindingListener() {
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                updateExtendedPrice(binding.isContentValid());
            }
        });
        
        //currency
        currencyField.bind(bindGroup, getCurrencies(), entity, entProps.getPropertyDetails("currency"));
        
        //unit price
        Binding unitPriceBinding = unitPriceField.bind(bindGroup, entity, entProps.getPropertyDetails("unitPrice"));
        unitPriceBinding.addBindingListener(new AbstractBindingListener() {
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                updateExtendedPrice(binding.isContentValid());
            }
        });
        
        //extended price
        extendedPriceField.bind(bindGroup, entity, entProps.getPropertyDetails("extendedPrice"));
        
        //ship week
        shipWeekField.bind(bindGroup, entity, entProps.getPropertyDetails("shipWeek"))
            .addBindingListener(new AbstractBindingListener() {
                @Override
                public void targetChanged(Binding binding, PropertyStateEvent event) {
                    calculateShipDates(binding.isContentValid());
                }
            });
        
        //ship date from
        shipDateFromField.bind(bindGroup, entity, entProps.getPropertyDetails("shipDateFrom"))
            .addBindingListener(new AbstractBindingListener() {
                @Override
                public void targetChanged(Binding binding, PropertyStateEvent event) {
                    calculateShipWeek(binding.isContentValid(), true);
                }
            });
        
        //ship date to
        shipDateToField.bind(bindGroup, entity, entProps.getPropertyDetails("shipDateTo"))
            .addBindingListener(new AbstractBindingListener() {
                @Override
                public void targetChanged(Binding binding, PropertyStateEvent event) {
                    calculateShipWeek(binding.isContentValid(), false);
                }
            });
        
        //notes
        notesField.bind(bindGroup, entity, entProps.getPropertyDetails("notes"));
        
        calculateShipWeek(true, true);
        
        bindGroup.bind();   
    }

    protected void calculateShipWeek(boolean contentValid, boolean fromDateChanged) {
        //avoid update cycle
        if ( updatingShipDates )
            return;
        
        updatingShipDates = true;
        
        if ( !contentValid ){
            shipWeekField.setText("");
        }else{

            Date dateToUse = null;
            
            //if no 'to' date - use the 'from'
            if ( entity.getShipDateFrom()==null ){
                dateToUse = entity.getShipDateTo();
            //otherwise use the 'to'
            }else{
                dateToUse = entity.getShipDateFrom();
            }
            
            if ( dateToUse==null ){
                shipWeekField.setText("");
            }else{
                Calendar c = Calendar.getInstance();
                c.setTime(dateToUse);
                Integer week = c.get(Calendar.WEEK_OF_YEAR);
                shipWeekField.setText(""+week);
            }
        }
        
        updatingShipDates = false;
    }

    protected void calculateShipDates(boolean contentValid) {
        if ( updatingShipDates )
            return;
        
        updatingShipDates = true;
        
        if ( !contentValid || "".equals(shipWeekField.getText())){
            shipDateFromField.setDate(null);
            shipDateToField.setDate(null);
        }else{
            Integer week = null;
            
            try{
                week = new Integer(shipWeekField.getText());
                
                Calendar c = Calendar.getInstance();
                c.set(Calendar.WEEK_OF_YEAR, week);
                
                shipDateFromField.setDate(c.getTime());
                c.add(Calendar.DAY_OF_WEEK, 5);
                shipDateToField.setDate(c.getTime());
            }catch (NumberFormatException e){
            }            
        }
        
        updatingShipDates = false;
    }

    protected void updateExtendedPrice(boolean contentValid) {
        if ( !contentValid){
            extendedPriceField.setText("");
            return;
        }
        
        try{
            String price = unitPriceField.getText();
            String qty = confirmedQuantityField.getText();
            BigDecimal priceLong = new BigDecimal(price);
            BigDecimal qtyLong = new BigDecimal(qty);
            BigDecimal result = qtyLong.multiply(priceLong);
            result.setScale(4, RoundingMode.HALF_DOWN);
            extendedPriceField.setText(""+(result));
        }catch (Exception e) {
        }
    }

    private List<DbResource> getCurrencies() {
        return getFormSession().getCurrencies();
    }

    @SuppressWarnings("unchecked")
    protected void onSelectProduct() {
//        SimpleProduct product = entity.getProduct();
//        
//        DbResource measureMentUnit = null;
//        BigDecimal defaultQty = null;
//        BigDecimal purchasePrice = null;
//        
//        if ( product!=null ){
//            WarehouseProduct warehouseProduct = getFormSession().getWarehouseProduct(product);
//            
//            measureMentUnit = product.getMeasureUnit();
//            defaultQty = product.getDefaultQuantity();
//            purchasePrice = product.getPurchasePrice();
//            
//            if ( warehouseProduct!=null ){
//                defaultQty = warehouseProduct.getPrefferedDefaultQuantity();
//                if ( warehouseProduct.getPurchasePrice()!=null )
//                    purchasePrice = warehouseProduct.getPurchasePrice();
//            }
//            
//            measureUnitField.setSelectedItem(measureMentUnit);
//            if ( defaultQty!=null && (orderedQuantityField.getText()==null || orderedQuantityField.getText().equals("")))
//                orderedQuantityField.setText(defaultQty.toString());
//            if ( purchasePrice!=null &&(purchasePriceField.getText()==null || purchasePriceField.getText().equals("")))
//                purchasePriceField.setText(purchasePrice.toString());
//        }
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
}
