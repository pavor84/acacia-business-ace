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
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBPercentField;
import com.cosmos.swingb.MigLayoutHelper;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * Created	:	21.04.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class ProductCategoryPanel extends BaseEntityPanel {

    @EJB
    private static ProductsListRemote formSession;

    private ProductCategory productCategory;
    private BindingGroup bindingGroup;

    /** Creates new form ProductCategoryPanel */
    public ProductCategoryPanel(ProductCategory category, BigInteger parentId)
    {
        super(parentId);
        this.productCategory = category;
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
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        productCategoryTabbedPane.addTab(resourceMap.getString("categoryDetailsPanel.TabConstraints.tabTitle"), categoryDetailsPanel); // NOI18N

        add(productCategoryTabbedPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void initData() {
        add(getButtonPanel(), BorderLayout.PAGE_END);
//        productCategoryTabbedPane.addTab(resourceMap.getString("categoryDetailsPanel.TabConstraints.tabTitle"), categoryDetailsPanel); // NOI18N
//        productCategoryTabbedPane.addTab(resourceMap.getString("categoryPricingPanelOld.TabConstraints.tabTitle"), categoryPricingPanelOld); // NOI18N
//        add(productCategoryTabbedPane, java.awt.BorderLayout.CENTER);

        EntityProperties entityProps = getFormSession().getProductCategoryEntityProperties();
        PropertyDetails propDetails;

        BindingGroup bg = getBindingGroup();

        //parent category
        propDetails = entityProps.getPropertyDetails("parentCategory");
        ProductCategoriesTreePanel categoryListPanel = new ProductCategoriesTreePanel(getParentDataObjectId());
        parentCategoryComboList.bind(bg, categoryListPanel, productCategory, propDetails,
            "${categoryName}", UpdateStrategy.READ_WRITE);
        
        //category name
        propDetails = entityProps.getPropertyDetails("categoryName");
        categoryNameTextField.bind(bg, productCategory, propDetails);

        //pattern mask format
        propDetails = entityProps.getPropertyDetails("patternMaskFormat");
        PatternMaskFormatListPanel patternListPanel = new PatternMaskFormatListPanel(getParentDataObjectId());
        
        patternMaskFormatComboList.bind(bg, patternListPanel, productCategory, propDetails,
            "${patternName} (${format})", UpdateStrategy.READ_WRITE);

        if(getRightsManager().isAllowed(SpecialPermission.ProductPricing)) {
            productCategoryTabbedPane.addTab(getResourceString("categoryPricingPanel.TabConstraints.tabTitle"),
                    getCategoryPricingPanel()); // NOI18N
        }

        //description
        descriptionTextPane.bind(bg, productCategory, entityProps.getPropertyDetails("description"));

        bg.bind();
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
    private com.cosmos.swingb.JBLabel categoryNameLabel;
    private com.cosmos.swingb.JBTextField categoryNameTextField;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.AcaciaComboList parentCategoryComboList;
    private com.cosmos.swingb.JBLabel parentCategoryLabel;
    private com.cosmos.acacia.gui.AcaciaComboList patternMaskFormatComboList;
    private com.cosmos.swingb.JBLabel patternMaskFormatLabel;
    private javax.swing.JTabbedPane productCategoryTabbedPane;
    // End of variables declaration//GEN-END:variables

    private EntityFormButtonPanel entityFormButtonPanel;
    private CategoryPricingPanel categoryPricingPanel;

    @Override
    public void performSave(boolean closeAfter) {
        productCategory = getFormSession().saveProductCategory(productCategory);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(productCategory);
        if (closeAfter)
            close();
    }

    @Override
    public BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public Object getEntity() {
        return productCategory;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        if(entityFormButtonPanel == null) {
            entityFormButtonPanel = new EntityFormButtonPanel();
            entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N
        }

        return entityFormButtonPanel;
    }

    private CategoryPricingPanel getCategoryPricingPanel() {
        if(categoryPricingPanel == null) {
            categoryPricingPanel = new CategoryPricingPanel();
        }

        return categoryPricingPanel;
    }

    protected ProductsListRemote getFormSession() {
        if (formSession == null)
                formSession = getBean(ProductsListRemote.class);

        return formSession;
    }

    private String getResourceString(String key) {
        return getResourceMap().getString(key);
    }

    private class CategoryDetailsPanel extends JBPanel {

        public CategoryDetailsPanel() {
            super(new MigLayout());
        }

    }

    private JBDecimalField transportDecimalField;
    private JBPercentField transportPercentField;

    private class CategoryPricingPanel extends JBPanel {

        private JBLabel customsDutyLabel;
        private JBPercentField customsDutyPercentField;
        private JBLabel discountLabel;
        private JBPercentField discountPercentField;
        private JBLabel exciseDutyLabel;
        private JBPercentField exciseDutyPercentField;
        private JBLabel profitLabel;
        private JBPercentField profitPercentField;
        private JBPanel transportPanel;
        private JBLabel transportPercentLabel;
        private JBLabel transportValueLabel;
        private boolean syncing;

        public CategoryPricingPanel() {
            super(new MigLayout());
            initComponents();
            initData();
        }

        private void initComponents() {
            discountPercentField = new JBPercentField();
            profitPercentField = new JBPercentField();
            discountLabel = new JBLabel();
            profitLabel = new JBLabel();
            customsDutyPercentField = new JBPercentField();
            exciseDutyPercentField = new JBPercentField();
            customsDutyLabel = new JBLabel();
            exciseDutyLabel = new JBLabel();
            transportPanel = new JBPanel();
            transportPercentField = new JBPercentField();
            transportPercentLabel = new JBLabel();
            transportValueLabel = new JBLabel();
            transportDecimalField = new JBDecimalField();

            discountLabel.setText(getResourceString("discountLabel.text")); // NOI18N
            discountLabel.setName("discountLabel"); // NOI18N

            profitLabel.setText(getResourceString("profitLabel.text")); // NOI18N
            profitLabel.setName("profitLabel"); // NOI18N

            customsDutyLabel.setText(getResourceString("customsDutyLabel.text")); // NOI18N
            customsDutyLabel.setName("customsDutyLabel"); // NOI18N

            exciseDutyLabel.setText(getResourceString("exciseDutyLabel.text")); // NOI18N
            exciseDutyLabel.setName("exciseDutyLabel"); // NOI18N

            transportPercentLabel.setText(getResourceString("transportPercentLabel.text")); // NOI18N
            transportPercentLabel.setName("transportPercentLabel"); // NOI18N

            transportValueLabel.setText(getResourceString("transportValueLabel.text")); // NOI18N
            transportValueLabel.setName("transportValueLabel"); // NOI18N

            transportPanel.setBorder(BorderFactory.createTitledBorder(
                    getResourceString("transportPanel.border.title"))); // NOI18N
            transportPanel.setName("transportPanel"); // NOI18N

            transportPercentField.setColumns(7);
            transportPercentField.setName("transportPercentField"); // NOI18N

            transportDecimalField.setColumns(7);
            transportDecimalField.setName("transportDecimalField"); // NOI18N

            discountPercentField.setColumns(7);
            discountPercentField.setName("discountPercentField"); // NOI18N

            profitPercentField.setColumns(7);
            profitPercentField.setName("profitPercentField"); // NOI18N

            customsDutyPercentField.setColumns(7);
            customsDutyPercentField.setName("customsDutyPercentField"); // NOI18N

            exciseDutyPercentField.setColumns(7);
            exciseDutyPercentField.setName("exciseDutyPercentField"); // NOI18N

            add(discountLabel);
            add(discountPercentField);
            add(profitLabel);
            add(profitPercentField);
            add(customsDutyLabel);
            add(customsDutyPercentField);
            add(exciseDutyLabel);
            add(exciseDutyPercentField);
            add(transportPanel);
            transportPanel.add(transportPercentLabel);
            transportPanel.add(transportPercentField);
            transportPanel.add(transportValueLabel);
            transportPanel.add(transportDecimalField);

            MigLayoutHelper helper = new MigLayoutHelper(transportPanel);
            helper.setLayoutFillX(true);
            helper.setLayoutWrapAfter(4);
            helper.columnGrow(100, 1, 3);
            helper.columnSizeGroup("sg", 1, 3);
            helper.columnFill(1, 1, 3);
            helper.columnGap("15", 1);
            transportPanel.invalidate();

            helper = new MigLayoutHelper(this);
            helper.setLayoutFillX(true);
            helper.columnGrow(100, 1, 3);
            helper.columnSizeGroup("sg", 1, 3);
            helper.columnFill(1, 1, 3);
            helper.columnGap("15", 1);
            helper.getComponentConstraints(discountLabel).cell(0, 0);
            helper.getComponentConstraints(discountPercentField).cell(1, 0);
            helper.getComponentConstraints(profitLabel).cell(2, 0);
            helper.getComponentConstraints(profitPercentField).cell(3, 0);
            helper.getComponentConstraints(customsDutyLabel).cell(0, 1);
            helper.getComponentConstraints(customsDutyPercentField).cell(1, 1);
            helper.getComponentConstraints(exciseDutyLabel).cell(2, 1);
            helper.getComponentConstraints(exciseDutyPercentField).cell(3, 1);
            helper.getComponentConstraints(transportPanel).cell(0, 2).spanX().grow();
            invalidate();
        }

        private void initData() {
            BindingGroup bg = getBindingGroup();
            EntityProperties entityProps = getFormSession().getProductCategoryEntityProperties();

            PropertyDetails propDetails = entityProps.getPropertyDetails("discountPercent");
            discountPercentField.bind(bg, productCategory, propDetails);

            propDetails = entityProps.getPropertyDetails("profitPercent");
            profitPercentField.bind(bg, productCategory, propDetails);

            propDetails = entityProps.getPropertyDetails("customsDuty");
            customsDutyPercentField.bind(bg, productCategory, propDetails);

            propDetails = entityProps.getPropertyDetails("exciseDuty");
            exciseDutyPercentField.bind(bg, productCategory, propDetails);

            Binding binding;
            propDetails = entityProps.getPropertyDetails("transportPercent");
            binding = transportPercentField.bind(bg, productCategory, propDetails);
            binding.addBindingListener(new AbstractBindingListener() {

                @Override
                public void targetChanged(Binding binding, PropertyStateEvent event) {
                    Object newValue;
                    Object oldValue;
                    if(syncing || !binding.isContentChanged() ||
                            (newValue = event.getNewValue()) == (oldValue = event.getOldValue()) ||
                            newValue != null && newValue.equals(oldValue) ||
                            oldValue != null && oldValue.equals(newValue)) {
                        if(syncing)
                            syncing = false;
                        return;
                    }
                    syncing = true;
                    transportDecimalField.setValue(null);
                }
            });

            propDetails = entityProps.getPropertyDetails("transportValue");
            binding = transportDecimalField.bind(bg, productCategory, propDetails);
            binding.addBindingListener(new AbstractBindingListener() {

                @Override
                public void targetChanged(Binding binding, PropertyStateEvent event) {
                    Object newValue;
                    Object oldValue;
                    if(syncing || !binding.isContentChanged() ||
                            (newValue = event.getNewValue()) == (oldValue = event.getOldValue()) ||
                            newValue != null && newValue.equals(oldValue) ||
                            oldValue != null && oldValue.equals(newValue)) {
                        if(syncing)
                            syncing = false;
                        return;
                    }
                    syncing = true;
                    transportPercentField.setValue(null);
                }
            });
        }
    }

}
