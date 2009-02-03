package com.cosmos.acacia.crm.gui.pricing;

import static com.cosmos.acacia.util.AcaciaUtils.getDecimalFormat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.ListSelectionModel;

import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.pricing.PricelistRemote;
import com.cosmos.acacia.crm.data.Pricelist;
import com.cosmos.acacia.crm.data.PricelistItem;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.gui.pricing.ExistingProductDialog.Response;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel.Button;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	13.01.2009
 * @author	Petar Milev
 *
 */
public class PricelistItemForm extends BaseEntityPanel {

    private PricelistItem entity;
    private Pricelist pricelist;
    private List<PricelistItem> includedItems;
    
    private BindingGroup bindGroup;
    private PricelistRemote formSession = getBean(PricelistRemote.class);
    private EntityProperties entProps;
    
    boolean multipleMode;
    
    /** used when in multiple mode */
    private List<SimpleProduct> products;
    
    public PricelistItemForm(PricelistItem pricelistItem, Pricelist pricelist, List<PricelistItem> includedItems) {
        super(pricelist.getPricelistId());
        this.entity = pricelistItem;
        this.pricelist = pricelist;
        this.includedItems = includedItems;
        initialize();
    }
    
    public PricelistItemForm(List<SimpleProduct> products, Pricelist pricelist, List<PricelistItem> includedItems) {
        super(pricelist.getPricelistId());
        this.products = products;
        this.entity = createPricelistItem();
        this.pricelist = pricelist;
        this.includedItems = includedItems;
        this.multipleMode = true;
        initialize();
    }

    private PricelistItem createPricelistItem() {
        PricelistItem item = new PricelistItem();
        return item;
    }

    private void initialize() {
        initComponents();
        initComponentsCustom();
        init();
    }

    private void initComponentsCustom() {
        if ( multipleMode ){
            jBLabel5.setVisible(false);
            productField.setVisible(false);
            jBLabel1.setVisible(false);
            salePriceField.setVisible(false);
            jBLabel6.setVisible(false);
            priceField.setVisible(false);
            
            getButtonPanel().getButton(Button.Save).setText(getResourceMap().getString("button.include"));
            getButtonPanel().getButton(Button.Close).setText(getResourceMap().getString("button.cancel"));
        }else{
            productsPanel.setVisible(false);
        }
        
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemove();
            }
        });
    }

    protected void onRemove() {
        List<SimpleProduct> simpleProducts = productsTable.getSelectedRowObjects();
        for (SimpleProduct simpleProduct : simpleProducts) {
            productsTable.removeRow(simpleProduct);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        applyDiscountField = new com.cosmos.swingb.JBCheckBox();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        minimalQtyField = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        discountField = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        jBLabel5 = new com.cosmos.swingb.JBLabel();
        productField = new com.cosmos.swingb.JBTextField();
        salePriceField = new com.cosmos.swingb.JBTextField();
        jBLabel1 = new com.cosmos.swingb.JBLabel();
        priceField = new com.cosmos.swingb.JBTextField();
        jBLabel6 = new com.cosmos.swingb.JBLabel();
        categoryField = new com.cosmos.swingb.JBTextField();
        jBLabel9 = new com.cosmos.swingb.JBLabel();
        productsPanel = new com.cosmos.swingb.JBPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productsTable = new com.cosmos.acacia.gui.AcaciaTable();
        removeButton = new com.cosmos.swingb.JBButton();
        defaultPercentLabel = new com.cosmos.swingb.JBLabel();
        marginLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PricelistItemForm.class);
        applyDiscountField.setText(resourceMap.getString("applyDiscountField.text")); // NOI18N
        applyDiscountField.setName("applyDiscountField"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        minimalQtyField.setText(resourceMap.getString("minimalQtyField.text")); // NOI18N
        minimalQtyField.setName("minimalQtyField"); // NOI18N

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        discountField.setText(resourceMap.getString("discountField.text")); // NOI18N
        discountField.setName("discountField"); // NOI18N

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        jBLabel5.setText(resourceMap.getString("jBLabel5.text")); // NOI18N
        jBLabel5.setName("jBLabel5"); // NOI18N

        productField.setEditable(false);
        productField.setText(resourceMap.getString("productField.text")); // NOI18N
        productField.setName("productField"); // NOI18N

        salePriceField.setEditable(false);
        salePriceField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        salePriceField.setName("salePriceField"); // NOI18N

        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        priceField.setEditable(false);
        priceField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        priceField.setName("priceField"); // NOI18N

        jBLabel6.setText(resourceMap.getString("jBLabel6.text")); // NOI18N
        jBLabel6.setName("jBLabel6"); // NOI18N

        categoryField.setEditable(false);
        categoryField.setName("categoryField"); // NOI18N

        jBLabel9.setText(resourceMap.getString("jBLabel9.text")); // NOI18N
        jBLabel9.setName("jBLabel9"); // NOI18N

        productsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("productsPanel.border.title"))); // NOI18N
        productsPanel.setName("productsPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        productsTable.setName("productsTable"); // NOI18N
        jScrollPane1.setViewportView(productsTable);

        removeButton.setMnemonic('r');
        removeButton.setText(resourceMap.getString("removeButton.text")); // NOI18N
        removeButton.setName("removeButton"); // NOI18N

        javax.swing.GroupLayout productsPanelLayout = new javax.swing.GroupLayout(productsPanel);
        productsPanel.setLayout(productsPanelLayout);
        productsPanelLayout.setHorizontalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(removeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        productsPanelLayout.setVerticalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        defaultPercentLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        defaultPercentLabel.setText(resourceMap.getString("defaultPercentLabel.text")); // NOI18N
        defaultPercentLabel.setName("defaultPercentLabel"); // NOI18N

        marginLabel.setText(resourceMap.getString("marginLabel.text")); // NOI18N
        marginLabel.setName("marginLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                    .addComponent(jBLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                    .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                    .addComponent(jBLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                    .addComponent(jBLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                    .addComponent(jBLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                    .addComponent(jBLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(productField, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(discountField, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(defaultPercentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                                    .addComponent(salePriceField, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                    .addComponent(minimalQtyField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                    .addComponent(applyDiscountField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(categoryField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                    .addComponent(priceField, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)))
                            .addComponent(productsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(marginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(marginLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(productField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(applyDiscountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minimalQtyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salePriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(discountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(defaultPercentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBCheckBox applyDiscountField;
    private com.cosmos.swingb.JBTextField categoryField;
    private com.cosmos.swingb.JBLabel defaultPercentLabel;
    private com.cosmos.swingb.JBFormattedTextField discountField;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private com.cosmos.swingb.JBLabel jBLabel5;
    private com.cosmos.swingb.JBLabel jBLabel6;
    private com.cosmos.swingb.JBLabel jBLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private com.cosmos.swingb.JBLabel marginLabel;
    private com.cosmos.swingb.JBFormattedTextField minimalQtyField;
    private com.cosmos.swingb.JBTextField priceField;
    private com.cosmos.swingb.JBTextField productField;
    private com.cosmos.swingb.JBPanel productsPanel;
    private com.cosmos.acacia.gui.AcaciaTable productsTable;
    private com.cosmos.swingb.JBButton removeButton;
    private com.cosmos.swingb.JBTextField salePriceField;
    // End of variables declaration//GEN-END:variables

    @Override
    public BindingGroup getBindingGroup() {
        return bindGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel1;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void performSave(boolean closeAfter) {
        if ( multipleMode ){
            List<PricelistItem> toSave = getItemsToSave();
            toSave = formSession.savePricelistItems(toSave);
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValues(toSave);
            checkForNotProfitableItems(toSave);
            close();
        }else{
            boolean duplication = checkForDuplication(entity);
            if ( !duplication ){
                entity = formSession.savePricelistItem(entity);
                setDialogResponse(DialogResponse.SAVE);
                setSelectedValue(entity);
                checkForNotProfitableItems(Arrays.asList(new PricelistItem[]{entity}));
            }else{
                return;
            }
        }
        
        if (closeAfter) {
            close();
        } else {
            bindGroup.unbind();
            initData();
        }
    }

    private void checkForNotProfitableItems(List<PricelistItem> asList) {
        StringBuilder msg = new StringBuilder();
        msg.append(getResourceMap().getString("msg.noprofit.prefix"));
        boolean found = false;
        for (PricelistItem pricelistItem : asList) {
            BigDecimal priceAfterDiscount = getPrice(pricelistItem).round(MathContext.DECIMAL64);
            if ( priceAfterDiscount==null )
                continue;
            SimpleProduct product = pricelistItem.getProduct();
            BigDecimal costPrice = product.getCostPrice().round(MathContext.DECIMAL64);
            if ( priceAfterDiscount.compareTo(costPrice)<0 ){
                BigDecimal loss = costPrice.subtract(priceAfterDiscount);
                String itemMsg = getResourceMap().getString("msg.noprofit.item");
                String currency = "";
                if ( product.getCurrency()!=null ){
                    currency = ((Currency)product.getCurrency().getEnumValue()).getCode();
                }
                itemMsg = MessageFormat.format(itemMsg, product.getProductDisplay(), AcaciaUtils.getDecimalFormat().format(loss),
                    currency);
                msg.append(itemMsg);
                found = true;
            }
        }
        
        if (found)
            showMessageBox(msg.toString());
    }

    private boolean checkForDuplication(PricelistItem current) {
        SimpleProduct product = current.getProduct();
        BigDecimal minQuantity = current.getMinQuantity();
        for (PricelistItem included : includedItems) {
            //ignore the same item
            if ( current.equals(included) )
                continue;
            if ( included.getProduct().equals(product) &&
                    //both null or equal
                    ((included.getMinQuantity()==null&&minQuantity==null) ||
                            (minQuantity!=null&&minQuantity.compareTo(included.getMinQuantity())==0)) ){
                showMessageDialog("error.existingProduct");
                return true;
            }
        }
        return false;
    }

    private List<PricelistItem> getItemsToSave() {
        List<SimpleProduct> toInclude = productsTable.getData();
        List<PricelistItem> toSave = new ArrayList<PricelistItem>();
        
        Response applyToAllResponse = null;
        for (Iterator<SimpleProduct> iterator = toInclude.iterator(); iterator.hasNext();) {
            SimpleProduct product = iterator.next();
            PricelistItem alreadyIncluded = getAlreadyIncluded(product, entity.getMinQuantity());
            if ( alreadyIncluded!=null ){
                String productDisplay = product.getCodeFormatted()+" "+product.getProductName()+", "
                                +product.getCategory().getCategoryName();
                String minQuantityDisplay = "-";
                if ( entity.getMinQuantity()!=null )
                    minQuantityDisplay = getDecimalFormat().format(entity.getMinQuantity());
                String oldDiscountDisplay = "-";
                if ( alreadyIncluded.getDiscountPercent()!=null )
                    oldDiscountDisplay = getDecimalFormat().format(alreadyIncluded.getDiscountPercent());
                else if ( pricelist.getDefaultDiscount()!=null )
                    oldDiscountDisplay = getDecimalFormat().format(pricelist.getDefaultDiscount())+" (default)";
                String newDiscountDisplay = "-";
                if ( entity.getDiscountPercent()!=null )
                    newDiscountDisplay = getDecimalFormat().format(entity.getDiscountPercent());
                else if ( pricelist.getDefaultDiscount()!=null )
                    newDiscountDisplay = getDecimalFormat().format(pricelist.getDefaultDiscount())+" (default)";
                
                //if the discount are the same - this is duplication, don't bother
                if ( newDiscountDisplay.equals(oldDiscountDisplay) ){
                    //nothing
                }else{
                    Response response = null;
                    if ( applyToAllResponse!=null ){
                        response = applyToAllResponse;
                    }else{
                        ExistingProductDialog dialog = new ExistingProductDialog(productDisplay, minQuantityDisplay, oldDiscountDisplay, newDiscountDisplay);
                        dialog.getAddNewButton().setVisible(false);
                        dialog.showDialog(this);
                        response = dialog.getResponse();
                        
                        if ( dialog.getApplyForAllCheckbox().isSelected() )
                            applyToAllResponse = dialog.getResponse();
                    }
                    
                    if ( Response.REPLACE.equals(response) ){
                        updateItem(alreadyIncluded, entity);
                        toSave.add(alreadyIncluded);
                    }else if ( Response.SKIP.equals(response) ){
                        //nothing
                    }
                }
            }else{
                PricelistItem newItem = createForProduct(product);
                toSave.add(newItem);
            }
        }
        return toSave;
    }

    private PricelistItem createForProduct(SimpleProduct product) {
        PricelistItem newItem = new PricelistItem();
        newItem.setProduct(product);
        newItem.setApplyClientDiscount(entity.isApplyClientDiscount());
        newItem.setDiscountPercent(entity.getDiscountPercent());
        newItem.setMinQuantity(entity.getMinQuantity());
        newItem.setParentId(pricelist.getId());
        return newItem;
    }

    private PricelistItem getAlreadyIncluded(SimpleProduct product, BigDecimal minQuantity) {
        for (PricelistItem included : includedItems) {
            if ( included.getProduct().equals(product) &&
                    //both null or equal
                    ((included.getMinQuantity()==null&&minQuantity==null) ||
                    (minQuantity!=null&& minQuantity.compareTo(included.getMinQuantity())==0)) )
                return included;
        }
        return null;
    }

    private void updateItem(PricelistItem toUpdate, PricelistItem newData) {
        toUpdate.setApplyClientDiscount(newData.isApplyClientDiscount());
        toUpdate.setDiscountPercent(newData.getDiscountPercent());
    }

    @Override
    protected void initData() {
        
        if (entProps == null)
            entProps = getFormSession().getItemDetailEntityProperties();

        if (bindGroup == null)
            bindGroup = new BindingGroup();

        bindComponents(bindGroup, entProps);
    }
    
    /**
     * Binds all components to the specified group and entity properties.
     * The group shouldn't be yet bound, or should be 'unbound'.
     * After all components are bound, some additional calculation and initialization is performed.
     */
    @SuppressWarnings("deprecation")
    protected void bindComponents(BindingGroup bindGroup, EntityProperties entProps) {
        
        if ( multipleMode ){
            //products table
            BindingGroup tableBindGroup = new BindingGroup();
            sortProducts(products);
            productsTable.bind(tableBindGroup, products, createProductsTableDetails(), UpdateStrategy.READ);
            tableBindGroup.bind();
            productsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            marginLabel.setVisible(false);
        }else{
            // product
            SimpleProduct p = entity.getProduct();
            productField.setText(p.getCodeFormatted()+" "+p.getProductName()+", "+p.getCategory().getCategoryName());
            
            //category
            categoryField.setText(p.getCategory().getCategoryName());
        }
        
        //temporary hide the category field
        jBLabel9.setVisible(false);
        categoryField.setVisible(false);
        
        // apply client disc.
        applyDiscountField.bind(bindGroup, entity, entProps.getPropertyDetails("applyClientDiscount"));
        if ( !multipleMode )
            applyDiscountField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onApplyClientDiscount();
                }
            });
        
        // min qty.
        minimalQtyField.bind(bindGroup, entity, entProps.getPropertyDetails("minQuantity"), getDecimalFormat());
        
        // discount
        discountField.bind(bindGroup, entity, entProps.getPropertyDetails("discountPercent"), getDecimalFormat());
        if ( !multipleMode )
            discountField.getBinding().addBindingListener(new AbstractBindingListener() {
                public void targetChanged(Binding binding, PropertyStateEvent event) {
                    onDiscount();
                }
            });
        
        bindGroup.bind();
        
        afterBind();
    }

    public static void sortProducts(List<SimpleProduct> prods) {
        java.util.Collections.sort(prods, new Comparator<SimpleProduct>() {
            public int compare(SimpleProduct o1, SimpleProduct o2) {
                return PricelistItemForm.compare(o1, o2);
            }
        });
    }
    
    public static int compare(SimpleProduct o1, SimpleProduct o2){
        int res = o1.getCategory().getCategoryName().compareTo(o2.getCategory().getCategoryName());
        if ( res==0 ){
            res = o1.getProductName().compareTo(o2.getProductName());
        }
        return res;
    }

    private Collection<PropertyDetails> createProductsTableDetails() {
        PropertyDetails product = new PropertyDetails("productName", "Product Name", String.class.getName());
        PropertyDetails code = new PropertyDetails("codeFormatted", "Product Code", String.class.getName());
        PropertyDetails category = new PropertyDetails("category.categoryName", "Category", String.class.getName());
        return Arrays.asList(product, code, category);
    }

    protected void onDiscount() {
        updatePrice();
    }

    private void updatePrice() {
        if ( !discountField.getBinding().isContentValid() )
            priceField.setText("");
        else{
            BigDecimal price = getPrice(entity);
            priceField.setText(getDecimalFormat().format(price));
        }
    }

    protected void onApplyClientDiscount() {
        updatePrice();
    }

    private void afterBind() {
        // default discount
        String defaultDiscountString = "0";
        if ( pricelist.getDefaultDiscount()!=null )
            defaultDiscountString = getDecimalFormat().format(pricelist.getDefaultDiscount());
        defaultPercentLabel.setText(MessageFormat.format(getResourceMap().getString("defaultPercentLabel.text"), defaultDiscountString));
        
        if ( !multipleMode ){
            // sale price
            String salePrice = "";
            if ( entity.getProduct().getSalePrice()!=null )
                salePrice = getDecimalFormat().format(entity.getProduct().getSalePrice());
            salePriceField.setText((salePrice));
            
            // price
            BigDecimal price = getPrice(entity);
            priceField.setText(getDecimalFormat().format(price));
        }
    }

    private BigDecimal getPrice(PricelistItem item) {
        //sale price
        BigDecimal salePrice = item.getProduct().getSalePrice();
        if ( salePrice==null )
            return null;
        
        BigDecimal result = salePrice;
        
        //discount
        BigDecimal discountPercent = item.getDiscountPercent();
        if (discountPercent==null)
            discountPercent = pricelist.getDefaultDiscount();
        if ( discountPercent!=null ){
            BigDecimal discountPercentDec = discountPercent.divide(new BigDecimal(100), MathContext.DECIMAL64);
            result = result.subtract(result.multiply(discountPercentDec));
        }
        
        return result;
    }
    
    @Override
    public void setReadonly() {
        super.setReadonly();
    }

    public PricelistRemote getFormSession() {
        return formSession;
    }

    public List<PricelistItem> getSavedItems() {
        return new ArrayList<PricelistItem>();//TODO impl
    }
}
