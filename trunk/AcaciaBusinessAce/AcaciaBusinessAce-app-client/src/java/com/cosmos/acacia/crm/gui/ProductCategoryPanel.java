/*
 * ProductCategoryPanel.java
 *
 * Created on Понеделник, 2008, Април 21, 11:20
 */

package com.cosmos.acacia.crm.gui;


import java.math.BigInteger;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * Created	:	21.04.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class ProductCategoryPanel extends BaseEntityPanel {
    private ProductCategory entity;

    @EJB
    private ProductsListRemote formSession;

    private EntityProperties entProps;

    private BindingGroup bindGroup;

    /** Creates new form ProductCategoryPanel */
    public ProductCategoryPanel(ProductCategory category, BigInteger parentId)
    {
        super(parentId);
        this.entity = category;
        init();
    }

    @Override
    protected void init()
    {
        initComponents();
        super.init();
    }

    public ProductCategoryPanel(){
        super(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        productCategoryTabbedPane = new javax.swing.JTabbedPane();
        categoryDetailsPanel = new javax.swing.JPanel();
        parentCategoryComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        categoryNameTextField = new com.cosmos.swingb.JBTextField();
        patternMaskFormatComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        parentCategoryLabel = new com.cosmos.swingb.JBLabel();
        categoryNameLabel = new com.cosmos.swingb.JBLabel();
        patternMaskFormatLabel = new com.cosmos.swingb.JBLabel();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        categoryPricingPanel = new com.cosmos.swingb.JBPanel();
        categoryDiscountPercentField = new com.cosmos.swingb.JBPercentField();
        categoryProfitPercentField = new com.cosmos.swingb.JBPercentField();
        categoryDiscountLabel = new com.cosmos.swingb.JBLabel();
        categoryProfitLabel = new com.cosmos.swingb.JBLabel();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductCategoryPanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("Form.border.title"))); // NOI18N
        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        productCategoryTabbedPane.setName("productCategoryTabbedPane"); // NOI18N

        categoryDetailsPanel.setName("categoryDetailsPanel"); // NOI18N

        parentCategoryComboList.setName("parentCategoryComboList"); // NOI18N

        categoryNameTextField.setName("categoryNameTextField"); // NOI18N

        patternMaskFormatComboList.setName("patternMaskFormatComboList"); // NOI18N

        parentCategoryLabel.setText(resourceMap.getString("parentCategoryLabel.text")); // NOI18N
        parentCategoryLabel.setName("parentCategoryLabel"); // NOI18N

        categoryNameLabel.setText(resourceMap.getString("categoryNameLabel.text")); // NOI18N
        categoryNameLabel.setName("categoryNameLabel"); // NOI18N

        patternMaskFormatLabel.setText(resourceMap.getString("patternMaskFormatLabel.text")); // NOI18N
        patternMaskFormatLabel.setName("patternMaskFormatLabel"); // NOI18N

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("descriptionPanel.border.title"))); // NOI18N
        descriptionPanel.setName("descriptionPanel"); // NOI18N
        descriptionPanel.setLayout(new java.awt.BorderLayout());

        descriptionScrollPane.setName("descriptionScrollPane"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        descriptionScrollPane.setViewportView(descriptionTextPane);

        descriptionPanel.add(descriptionScrollPane, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout categoryDetailsPanelLayout = new javax.swing.GroupLayout(categoryDetailsPanel);
        categoryDetailsPanel.setLayout(categoryDetailsPanelLayout);
        categoryDetailsPanelLayout.setHorizontalGroup(
            categoryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, categoryDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(categoryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, categoryDetailsPanelLayout.createSequentialGroup()
                        .addGroup(categoryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(patternMaskFormatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(categoryNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(parentCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(categoryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(parentCategoryComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                            .addComponent(categoryNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                            .addComponent(patternMaskFormatComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))))
                .addContainerGap())
        );
        categoryDetailsPanelLayout.setVerticalGroup(
            categoryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(categoryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(parentCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(parentCategoryComboList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(categoryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(categoryDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(patternMaskFormatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patternMaskFormatComboList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );

        productCategoryTabbedPane.addTab(resourceMap.getString("categoryDetailsPanel.TabConstraints.tabTitle"), categoryDetailsPanel); // NOI18N

        categoryPricingPanel.setName("categoryPricingPanel"); // NOI18N

        categoryDiscountPercentField.setName("categoryDiscountPercentField"); // NOI18N

        categoryProfitPercentField.setName("categoryProfitPercentField"); // NOI18N

        categoryDiscountLabel.setText(resourceMap.getString("categoryDiscountLabel.text")); // NOI18N
        categoryDiscountLabel.setName("categoryDiscountLabel"); // NOI18N

        categoryProfitLabel.setText(resourceMap.getString("categoryProfitLabel.text")); // NOI18N
        categoryProfitLabel.setName("categoryProfitLabel"); // NOI18N

        javax.swing.GroupLayout categoryPricingPanelLayout = new javax.swing.GroupLayout(categoryPricingPanel);
        categoryPricingPanel.setLayout(categoryPricingPanelLayout);
        categoryPricingPanelLayout.setHorizontalGroup(
            categoryPricingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPricingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(categoryPricingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(categoryDiscountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryProfitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(categoryPricingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(categoryProfitPercentField, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(categoryDiscountPercentField, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))
                .addContainerGap())
        );
        categoryPricingPanelLayout.setVerticalGroup(
            categoryPricingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPricingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(categoryPricingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryDiscountPercentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryDiscountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(categoryPricingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryProfitPercentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryProfitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(217, Short.MAX_VALUE))
        );

        productCategoryTabbedPane.addTab(resourceMap.getString("categoryPricingPanel.TabConstraints.tabTitle"), categoryPricingPanel); // NOI18N

        add(productCategoryTabbedPane, java.awt.BorderLayout.CENTER);

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N
        add(entityFormButtonPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void initData() {
        entProps = getFormSession().getProductCategoryEntityProperties();
        PropertyDetails propDetails;

        bindGroup = new BindingGroup();

        //parent category
        propDetails = entProps.getPropertyDetails("parentCategory");
        ProductCategoriesTreePanel categoryListPanel = new ProductCategoriesTreePanel(getParentDataObjectId());
        parentCategoryComboList.bind(bindGroup, categoryListPanel, entity, propDetails,
            "${categoryName}", UpdateStrategy.READ_WRITE);
        
        //category name
        propDetails = entProps.getPropertyDetails("categoryName");
        categoryNameTextField.bind(bindGroup, entity, propDetails);

        //pattern mask format
        propDetails = entProps.getPropertyDetails("patternMaskFormat");
        PatternMaskFormatListPanel patternListPanel = new PatternMaskFormatListPanel(getParentDataObjectId());
        
        patternMaskFormatComboList.bind(bindGroup, patternListPanel, entity, propDetails,
            "${patternName} (${format})", UpdateStrategy.READ_WRITE);

        if(getRightsManager().isAllowed(SpecialPermission.ProductPricing)) {
            categoryDiscountPercentField.bind(bindGroup, entity, entProps.getPropertyDetails("discountPercent"));
            categoryProfitPercentField.bind(bindGroup, entity, entProps.getPropertyDetails("profitPercent"));
        } else {
            int index;
            if((index = productCategoryTabbedPane.indexOfComponent(categoryPricingPanel)) >= 0)
                productCategoryTabbedPane.removeTabAt(index);
        }

        //description
        descriptionTextPane.bind(bindGroup, entity, entProps.getPropertyDetails("description"));

        bindGroup.bind();
    }

    @SuppressWarnings("unchecked")
    protected Object onChooseCategory() {
        ProductCategoriesTreePanel panel = new ProductCategoriesTreePanel(null);
        panel.getListPanel().setVisible(com.cosmos.acacia.gui.AbstractTablePanel.Button.Select, true);
        panel.getListPanel().setVisible(com.cosmos.acacia.gui.AbstractTablePanel.Button.Unselect, true);
        panel.setModificationsEnabled(false);

        DialogResponse dResponse = panel.showDialog(this);

        if ( DialogResponse.SELECT.equals(dResponse) ){

            ProductCategory category = (ProductCategory)
                panel.getListPanel().getSelectedRowObject();

//            PatternMaskFormat oldFormat =
//                (PatternMaskFormat) patternMaskBinding.getTargetProperty()
//                    .getValue(patternMaskBinding.getTargetObject());
//
//            if ( oldFormat==null && category!=null ){
//                patternMaskBinding.getTargetProperty()
//                    .setValue(patternMaskBinding.getTargetObject(), category.getPatternMaskFormat());
//            }

            return category;
        }else{
            return null;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel categoryDetailsPanel;
    private com.cosmos.swingb.JBLabel categoryDiscountLabel;
    private com.cosmos.swingb.JBPercentField categoryDiscountPercentField;
    private com.cosmos.swingb.JBLabel categoryNameLabel;
    private com.cosmos.swingb.JBTextField categoryNameTextField;
    private com.cosmos.swingb.JBPanel categoryPricingPanel;
    private com.cosmos.swingb.JBLabel categoryProfitLabel;
    private com.cosmos.swingb.JBPercentField categoryProfitPercentField;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.acacia.gui.AcaciaComboList parentCategoryComboList;
    private com.cosmos.swingb.JBLabel parentCategoryLabel;
    private com.cosmos.acacia.gui.AcaciaComboList patternMaskFormatComboList;
    private com.cosmos.swingb.JBLabel patternMaskFormatLabel;
    private javax.swing.JTabbedPane productCategoryTabbedPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void performSave(boolean closeAfter) {
        entity = getFormSession().saveProductCategory(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if (closeAfter)
            close();
    }

    @Override
    public BindingGroup getBindingGroup() {
        return bindGroup;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel1;
    }

    protected ProductsListRemote getFormSession() {
        if (formSession == null)
                formSession = getBean(ProductsListRemote.class);

        return formSession;
    }
}
