/*
 * ProductPanelNew.java
 *
 * Created on Неделя, 2009, Февруари 1, 15:20
 */
package com.cosmos.acacia.crm.gui.product;

import com.cosmos.acacia.crm.gui.*;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.data.ProductPercentValue;
import com.cosmos.acacia.crm.data.ProductSupplier;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnersListPanel;
import com.cosmos.acacia.crm.gui.pricing.ProductPricingValueListPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.resource.TextResource;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBIntegerField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.MigLayoutHelper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingx.JXPercentValueField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author  Miro
 */
public class ProductPanel extends BaseEntityPanel {

    @EJB
    private static ProductsListRemote formSession;

    public ProductPanel(SimpleProduct product) {
        super(product.getParentId());
        this.product = product;
        System.out.println("product: " + product);
        init();
    }

    public ProductPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        init();
    }

    /** Creates new form ProductPanelNew */
    public ProductPanel() {
        this((BigInteger) null);
    }

    @Override
    protected void init() {
        initComponents();
        super.init();
    }

    private void initComponents() {

        dimensionUnitComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        dimensionUnitLabel = new com.cosmos.swingb.JBLabel();
        widthLabel = new com.cosmos.swingb.JBLabel();
        widthDecimalField = new JBDecimalField();
        lengthLabel = new com.cosmos.swingb.JBLabel();
        lengthDecimalField = new JBDecimalField();
        heightLabel = new com.cosmos.swingb.JBLabel();
        heightDecimalField = new JBDecimalField();
        cubatureLabel = new com.cosmos.swingb.JBLabel();
        cubatureDecimalField = new JBDecimalField();
        salableCheckBox = new com.cosmos.swingb.JBCheckBox();
        obsoleteCheckBox = new com.cosmos.swingb.JBCheckBox();
        purchasedCheckBox = new com.cosmos.swingb.JBCheckBox();
        deliveryTimeIntegerField = new JBIntegerField();
        deliveryTimeLabel = new com.cosmos.swingb.JBLabel();
        weightDecimalField = new JBDecimalField();
        weightLabel = new com.cosmos.swingb.JBLabel();
        weightUnitLabel = new com.cosmos.swingb.JBLabel();
        weightUnitComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        maxDecimalField = new JBDecimalField();
        maxLabel = new com.cosmos.swingb.JBLabel();
        defaultDecimalField = new JBDecimalField();
        defaultLabel = new com.cosmos.swingb.JBLabel();
        minLabel = new com.cosmos.swingb.JBLabel();
        minDecimalField = new JBDecimalField();
        salesPriceLabel = new com.cosmos.swingb.JBLabel();
        pricePerQuantityLabel = new com.cosmos.swingb.JBLabel();
        salesPriceDecimalField = new JBDecimalField();
        pricePerQuantityDecimalField = new JBDecimalField();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        dimensionsPanel = new com.cosmos.swingb.JBPanel();
        purchaseOptionsPanel = new com.cosmos.swingb.JBPanel();
        deliveryInfoPanel = new com.cosmos.swingb.JBPanel();
        productCodeTextField = new com.cosmos.swingb.JBMaskTextField();
        productCodeFormatComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        categoryCodeFormatLabel = new com.cosmos.swingb.JBLabel();
        categoryCodeFormatTextField = new com.cosmos.swingb.JBTextField();
        quantitiesOnStockPanel = new com.cosmos.swingb.JBPanel();
        pricePanel = new com.cosmos.swingb.JBPanel();
        quantityPerPackageLabel = new com.cosmos.swingb.JBLabel();
        quantityPerPackageIntegerField = new JBIntegerField();
        producerLabel = new com.cosmos.swingb.JBLabel();
        producerComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        productColorLabel = new com.cosmos.swingb.JBLabel();
        productColorComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        measureUnitLabel = new com.cosmos.swingb.JBLabel();
        measureUnitComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        productCodeLabel = new com.cosmos.swingb.JBLabel();
        productCodeFormatLabel = new com.cosmos.swingb.JBLabel();
        productCategoryLabel = new com.cosmos.swingb.JBLabel();
        productCategoryComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        productNameLabel = new com.cosmos.swingb.JBLabel();
        productNameTextField = new com.cosmos.swingb.JBTextField();
        productTabbedPane = new com.cosmos.swingb.JBTabbedPane();
        primaryInfoPanel = new com.cosmos.swingb.JBPanel();
        additionalInfoPanel = new com.cosmos.swingb.JBPanel();
        productPricingPanel = new com.cosmos.swingb.JBPanel();
        suppliersPanel = new com.cosmos.swingb.JBPanel();
        currencyValueLabel = new com.cosmos.swingb.JBLabel();

        dimensionUnitComboBox.setName("dimensionUnitComboBox"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductPanel.class);
        dimensionUnitLabel.setText(resourceMap.getString("dimensionUnitLabel.text")); // NOI18N
        dimensionUnitLabel.setName("dimensionUnitLabel"); // NOI18N

        widthLabel.setText(resourceMap.getString("widthLabel.text")); // NOI18N
        widthLabel.setName("widthLabel"); // NOI18N

        widthDecimalField.setName("widthTextField"); // NOI18N

        lengthLabel.setText(resourceMap.getString("lengthLabel.text")); // NOI18N
        lengthLabel.setName("lengthLabel"); // NOI18N

        lengthDecimalField.setName("lengthTextField"); // NOI18N

        heightLabel.setText(resourceMap.getString("heightLabel.text")); // NOI18N
        heightLabel.setName("heightLabel"); // NOI18N

        heightDecimalField.setName("heightTextField"); // NOI18N

        cubatureLabel.setText(resourceMap.getString("cubatureLabel.text")); // NOI18N
        cubatureLabel.setName("cubatureLabel"); // NOI18N

        cubatureDecimalField.setName("cubatureTextField"); // NOI18N

        salableCheckBox.setText(resourceMap.getString("salableCheckBox.text")); // NOI18N
        salableCheckBox.setName("salableCheckBox"); // NOI18N

        obsoleteCheckBox.setText(resourceMap.getString("obsoleteCheckBox.text")); // NOI18N
        obsoleteCheckBox.setName("obsoleteCheckBox"); // NOI18N

        purchasedCheckBox.setText(resourceMap.getString("purchasedCheckBox.text")); // NOI18N
        purchasedCheckBox.setName("purchasedCheckBox"); // NOI18N

        deliveryTimeIntegerField.setName("deliveryTimeTextField"); // NOI18N

        deliveryTimeLabel.setText(resourceMap.getString("deliveryTimeLabel.text")); // NOI18N
        deliveryTimeLabel.setName("deliveryTimeLabel"); // NOI18N

        weightDecimalField.setName("weightTextField"); // NOI18N

        weightLabel.setText(resourceMap.getString("weightLabel.text")); // NOI18N
        weightLabel.setName("weightLabel"); // NOI18N

        weightUnitLabel.setText(resourceMap.getString("weightUnitLabel.text")); // NOI18N
        weightUnitLabel.setName("weightUnitLabel"); // NOI18N

        weightUnitComboBox.setName("weightUnitComboBox"); // NOI18N

        maxDecimalField.setName("maxTextField"); // NOI18N

        maxLabel.setText(resourceMap.getString("maxLabel.text")); // NOI18N
        maxLabel.setName("maxLabel"); // NOI18N

        defaultDecimalField.setName("defaultTextField"); // NOI18N

        defaultLabel.setText(resourceMap.getString("defaultLabel.text")); // NOI18N
        defaultLabel.setName("defaultLabel"); // NOI18N

        minLabel.setText(resourceMap.getString("minLabel.text")); // NOI18N
        minLabel.setName("minLabel"); // NOI18N

        minDecimalField.setName("minTextField"); // NOI18N

        salesPriceLabel.setText(resourceMap.getString("salesPriceLabel.text")); // NOI18N
        salesPriceLabel.setName("salesPriceLabel"); // NOI18N

        pricePerQuantityLabel.setText(resourceMap.getString("pricePerQuantityLabel.text")); // NOI18N
        pricePerQuantityLabel.setName("pricePerQuantityLabel"); // NOI18N

        salesPriceDecimalField.setName("salesPriceTextField"); // NOI18N

        pricePerQuantityDecimalField.setName("pricePerQuantityTextField"); // NOI18N

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("descriptionPanel.border.title"))); // NOI18N
        descriptionPanel.setName("descriptionPanel"); // NOI18N
        descriptionPanel.setLayout(new java.awt.BorderLayout());

        descriptionScrollPane.setName("descriptionScrollPane"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        descriptionScrollPane.setViewportView(descriptionTextPane);

        descriptionPanel.add(descriptionScrollPane, java.awt.BorderLayout.CENTER);

        dimensionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("dimensionsPanel.border.title"))); // NOI18N
        dimensionsPanel.setName("dimensionsPanel"); // NOI18N
        dimensionsPanel.setLayout(new net.miginfocom.swing.MigLayout());

        purchaseOptionsPanel.setName("purchaseOptionsPanel"); // NOI18N
        purchaseOptionsPanel.setLayout(new net.miginfocom.swing.MigLayout());

        deliveryInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("deliveryInfoPanel.border.title"))); // NOI18N
        deliveryInfoPanel.setName("deliveryInfoPanel"); // NOI18N
        deliveryInfoPanel.setLayout(new net.miginfocom.swing.MigLayout());

        productCodeTextField.setText(resourceMap.getString("productCodeTextField.text")); // NOI18N
        productCodeTextField.setName("productCodeTextField"); // NOI18N

        productCodeFormatComboList.setName("productCodeFormatComboList"); // NOI18N

        categoryCodeFormatLabel.setText(resourceMap.getString("categoryCodeFormatLabel.text")); // NOI18N
        categoryCodeFormatLabel.setName("categoryCodeFormatLabel"); // NOI18N

        categoryCodeFormatTextField.setEditable(false);
        categoryCodeFormatTextField.setName("categoryCodeFormatTextField"); // NOI18N

        quantitiesOnStockPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("quantitiesOnStockPanel.border.title"))); // NOI18N
        quantitiesOnStockPanel.setName("quantitiesOnStockPanel"); // NOI18N
        quantitiesOnStockPanel.setLayout(new net.miginfocom.swing.MigLayout());

        pricePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("pricePanel.border.title"))); // NOI18N
        pricePanel.setName("pricePanel"); // NOI18N
        pricePanel.setLayout(new net.miginfocom.swing.MigLayout());

        quantityPerPackageLabel.setText(resourceMap.getString("quantityPerPackageLabel.text")); // NOI18N
        quantityPerPackageLabel.setName("quantityPerPackageLabel"); // NOI18N

        quantityPerPackageIntegerField.setName("quantityPerPackageTextField"); // NOI18N

        producerLabel.setText(resourceMap.getString("producerLabel.text")); // NOI18N
        producerLabel.setName("producerLabel"); // NOI18N

        producerComboList.setName("producerComboList"); // NOI18N

        productColorLabel.setText(resourceMap.getString("productColorLabel.text")); // NOI18N
        productColorLabel.setName("productColorLabel"); // NOI18N

        productColorComboBox.setName("productColorComboBox"); // NOI18N

        measureUnitLabel.setText(resourceMap.getString("measureUnitLabel.text")); // NOI18N
        measureUnitLabel.setName("measureUnitLabel"); // NOI18N

        measureUnitComboBox.setName("measureUnitComboBox"); // NOI18N

        productCodeLabel.setText(resourceMap.getString("productCodeLabel.text")); // NOI18N
        productCodeLabel.setName("productCodeLabel"); // NOI18N

        productCodeFormatLabel.setText(resourceMap.getString("productCodeFormatLabel.text")); // NOI18N
        productCodeFormatLabel.setName("productCodeFormatLabel"); // NOI18N

        productCategoryLabel.setText(resourceMap.getString("productCategoryLabel.text")); // NOI18N
        productCategoryLabel.setName("productCategoryLabel"); // NOI18N

        productCategoryComboList.setName("productCategoryComboList"); // NOI18N

        productNameLabel.setText(resourceMap.getString("productNameLabel.text")); // NOI18N
        productNameLabel.setName("productNameLabel"); // NOI18N

        productNameTextField.setName("productNameTextField"); // NOI18N

        productTabbedPane.setName("productTabbedPane"); // NOI18N

        primaryInfoPanel.setName("primaryInfoPanel"); // NOI18N
        primaryInfoPanel.setLayout(new net.miginfocom.swing.MigLayout());
        productTabbedPane.addTab(resourceMap.getString("primaryInfoPanel.TabConstraints.tabTitle"), primaryInfoPanel); // NOI18N

        additionalInfoPanel.setName("additionalInfoPanel"); // NOI18N
        additionalInfoPanel.setLayout(new net.miginfocom.swing.MigLayout());
        productTabbedPane.addTab(resourceMap.getString("additionalInfoPanel.TabConstraints.tabTitle"), additionalInfoPanel); // NOI18N

        productPricingPanel.setName("productPricingPanel"); // NOI18N
        productPricingPanel.setLayout(new net.miginfocom.swing.MigLayout());
        productTabbedPane.addTab(resourceMap.getString("productPricingPanel.TabConstraints.tabTitle"), productPricingPanel); // NOI18N

        suppliersPanel.setName("suppliersPanel"); // NOI18N
        suppliersPanel.setLayout(new java.awt.BorderLayout());
        productTabbedPane.addTab(resourceMap.getString("suppliersPanel.TabConstraints.tabTitle"), suppliersPanel); // NOI18N

        currencyValueLabel.setText(resourceMap.getString("currencyValueLabel.text")); // NOI18N
        currencyValueLabel.setName("currencyValueLabel"); // NOI18N

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());
    }
    private com.cosmos.swingb.JBPanel additionalInfoPanel;
    private com.cosmos.swingb.JBLabel categoryCodeFormatLabel;
    private com.cosmos.swingb.JBTextField categoryCodeFormatTextField;
    private com.cosmos.swingb.JBLabel cubatureLabel;
    private JBDecimalField cubatureDecimalField;
    private com.cosmos.swingb.JBLabel currencyValueLabel;
    private com.cosmos.swingb.JBLabel defaultLabel;
    private JBDecimalField defaultDecimalField;
    private com.cosmos.swingb.JBPanel deliveryInfoPanel;
    private com.cosmos.swingb.JBLabel deliveryTimeLabel;
    private JBIntegerField deliveryTimeIntegerField;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.AcaciaComboBox dimensionUnitComboBox;
    private com.cosmos.swingb.JBLabel dimensionUnitLabel;
    private com.cosmos.swingb.JBPanel dimensionsPanel;
    private com.cosmos.swingb.JBLabel heightLabel;
    private JBDecimalField heightDecimalField;
    private com.cosmos.swingb.JBLabel lengthLabel;
    private JBDecimalField lengthDecimalField;
    private com.cosmos.swingb.JBLabel maxLabel;
    private JBDecimalField maxDecimalField;
    private com.cosmos.acacia.gui.AcaciaComboBox measureUnitComboBox;
    private com.cosmos.swingb.JBLabel measureUnitLabel;
    private com.cosmos.swingb.JBLabel minLabel;
    private JBDecimalField minDecimalField;
    private com.cosmos.swingb.JBCheckBox obsoleteCheckBox;
    private com.cosmos.swingb.JBPanel pricePanel;
    private com.cosmos.swingb.JBLabel pricePerQuantityLabel;
    private JBDecimalField pricePerQuantityDecimalField;
    private com.cosmos.swingb.JBPanel primaryInfoPanel;
    private com.cosmos.acacia.gui.AcaciaComboList producerComboList;
    private com.cosmos.swingb.JBLabel producerLabel;
    private com.cosmos.acacia.gui.AcaciaComboList productCategoryComboList;
    private com.cosmos.swingb.JBLabel productCategoryLabel;
    private com.cosmos.acacia.gui.AcaciaComboList productCodeFormatComboList;
    private com.cosmos.swingb.JBLabel productCodeFormatLabel;
    private com.cosmos.swingb.JBLabel productCodeLabel;
    private com.cosmos.swingb.JBMaskTextField productCodeTextField;
    private com.cosmos.acacia.gui.AcaciaComboBox productColorComboBox;
    private com.cosmos.swingb.JBLabel productColorLabel;
    private com.cosmos.swingb.JBLabel productNameLabel;
    private com.cosmos.swingb.JBTextField productNameTextField;
    private com.cosmos.swingb.JBPanel productPricingPanel;
    private com.cosmos.swingb.JBTabbedPane productTabbedPane;
    private com.cosmos.swingb.JBPanel purchaseOptionsPanel;
    private com.cosmos.swingb.JBCheckBox purchasedCheckBox;
    private com.cosmos.swingb.JBPanel quantitiesOnStockPanel;
    private com.cosmos.swingb.JBLabel quantityPerPackageLabel;
    private JBIntegerField quantityPerPackageIntegerField;
    private com.cosmos.swingb.JBCheckBox salableCheckBox;
    private com.cosmos.swingb.JBLabel salesPriceLabel;
    private JBDecimalField salesPriceDecimalField;
    private com.cosmos.swingb.JBPanel suppliersPanel;
    private com.cosmos.swingb.JBLabel weightLabel;
    private JBDecimalField weightDecimalField;
    private com.cosmos.acacia.gui.AcaciaComboBox weightUnitComboBox;
    private com.cosmos.swingb.JBLabel weightUnitLabel;
    private com.cosmos.swingb.JBLabel widthLabel;
    private JBDecimalField widthDecimalField;
    private SimpleProduct product;
    private SuppliersListPanel suppliersListPanel;
    private BindingGroup productBindingGroup;
    private EntityFormButtonPanel entityFormButtonPanel;

    private EntityProperties productEntityProperties;
    private EntityProperties productSuppliersEntityProperties;

    @Override
    protected void initData() {
        setPreferredSize(new Dimension(920, 480));

        add(productTabbedPane, BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.PAGE_END);

        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(dimensionUnitComboBox, resourceToStringConverter);
        AutoCompleteDecorator.decorate(measureUnitComboBox, resourceToStringConverter);
        AutoCompleteDecorator.decorate(productColorComboBox, resourceToStringConverter);
        AutoCompleteDecorator.decorate(weightUnitComboBox, resourceToStringConverter);

        if (product == null) {
            product = getFormSession().newProduct();
        }
        /* else {
            product = getFormSession().refresh(product);
        }*/

        BindingGroup bindingGroup = getBindingGroup();
        EntityProperties entityProps = getProductEntityProperties();

        initPrimaryInfo(bindingGroup, entityProps);
        initAdditionalInfo(bindingGroup, entityProps);

        if (!getRightsManager().isAllowed(SpecialPermission.ProductPricing)) {
            int index;
            if ((index = productTabbedPane.indexOfComponent(productPricingPanel)) >= 0) {
                productTabbedPane.removeTabAt(index);
            }
        } else {
            //initProductPricing(bindingGroup, entityProps);
        }

        if (!getRightsManager().isAllowed(SpecialPermission.Product)) {
            int index;
            if ((index = productTabbedPane.indexOfComponent(suppliersPanel)) >= 0) {
                productTabbedPane.removeTabAt(index);
            }
        } else {
            initSuppliers();
        }

        bindingGroup.bind();

        refreshCubature();

        setProductCodeMaskFormat();
    }

    private JBPanel getPrimaryInfoPanel() {
        if(primaryInfoPanel == null) {
            
        }

        return primaryInfoPanel;
    }

    @Override
    public BindingGroup getBindingGroup() {
        if (productBindingGroup == null) {
            productBindingGroup = new BindingGroup();
        }

        return productBindingGroup;
    }

    private void initPrimaryInfo(BindingGroup bindingGroup, EntityProperties entityProps) {
        MigLayoutHelper helper = new MigLayoutHelper(purchaseOptionsPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(3);
        helper.columnGrow(100, 0, 1, 2);
        helper.columnSizeGroup("sg", 0, 1, 2);
        helper.columnFill(0, 1, 2);
        helper.columnGap("15", 0, 1);
        purchaseOptionsPanel.add(purchasedCheckBox);
        helper.getComponentConstraints(purchasedCheckBox).cell(0, 0);
        purchaseOptionsPanel.add(salableCheckBox);
        helper.getComponentConstraints(salableCheckBox).cell(1, 0);
        purchaseOptionsPanel.add(obsoleteCheckBox);
        helper.getComponentConstraints(obsoleteCheckBox).cell(2, 0);
        purchaseOptionsPanel.invalidate();

        helper = new MigLayoutHelper(pricePanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(5);
        helper.columnGrow(100, 1, 3);
        helper.columnSizeGroup("sg", 1, 3);
        helper.columnFill(1, 3);
        helper.columnGap("15", 1);
        pricePanel.add(pricePerQuantityLabel);
        helper.getComponentConstraints(pricePerQuantityLabel).cell(0, 0);
        pricePanel.add(pricePerQuantityDecimalField);
        helper.getComponentConstraints(pricePerQuantityDecimalField).cell(1, 0);
        pricePanel.add(salesPriceLabel);
        helper.getComponentConstraints(salesPriceLabel).cell(2, 0);
        pricePanel.add(salesPriceDecimalField);
        helper.getComponentConstraints(salesPriceDecimalField).cell(3, 0);
        pricePanel.add(currencyValueLabel);
        helper.getComponentConstraints(currencyValueLabel).cell(4, 0);
        pricePanel.invalidate();

        helper = new MigLayoutHelper(quantitiesOnStockPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(6);
        helper.columnGrow(100, 1, 3, 5);
        helper.columnSizeGroup("sg", 1, 3, 5);
        helper.columnFill(1, 3, 5);
        helper.columnGap("15", 1, 3);
        quantitiesOnStockPanel.add(minLabel);
        helper.getComponentConstraints(minLabel).cell(0, 0);
        quantitiesOnStockPanel.add(minDecimalField);
        helper.getComponentConstraints(minDecimalField).cell(1, 0);
        quantitiesOnStockPanel.add(defaultLabel);
        helper.getComponentConstraints(defaultLabel).cell(2, 0);
        quantitiesOnStockPanel.add(defaultDecimalField);
        helper.getComponentConstraints(defaultDecimalField).cell(3, 0);
        quantitiesOnStockPanel.add(maxLabel);
        helper.getComponentConstraints(maxLabel).cell(4, 0);
        quantitiesOnStockPanel.add(maxDecimalField);
        helper.getComponentConstraints(maxDecimalField).cell(5, 0);
        quantitiesOnStockPanel.invalidate();

        helper = new MigLayoutHelper(primaryInfoPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(4);
        helper.columnGrow(100, 1, 3);
        helper.columnSizeGroup("sg", 1, 3);
        helper.columnFill(1, 3);
        helper.columnGap("15", 1);
        primaryInfoPanel.add(productNameLabel);
        helper.getComponentConstraints(productNameLabel).cell(0, 0);
        primaryInfoPanel.add(productNameTextField);
        helper.getComponentConstraints(productNameTextField).cell(1, 0).spanX(3);
        primaryInfoPanel.add(productCategoryLabel);
        helper.getComponentConstraints(productCategoryLabel).cell(0, 1);
        primaryInfoPanel.add(productCategoryComboList);
        helper.getComponentConstraints(productCategoryComboList).cell(1, 1);
        primaryInfoPanel.add(categoryCodeFormatLabel);
        helper.getComponentConstraints(categoryCodeFormatLabel).cell(2, 1);
        primaryInfoPanel.add(categoryCodeFormatTextField);
        helper.getComponentConstraints(categoryCodeFormatTextField).cell(3, 1);
        primaryInfoPanel.add(productCodeFormatLabel);
        helper.getComponentConstraints(productCodeFormatLabel).cell(0, 2);
        primaryInfoPanel.add(productCodeFormatComboList);
        helper.getComponentConstraints(productCodeFormatComboList).cell(1, 2);
        primaryInfoPanel.add(productCodeLabel);
        helper.getComponentConstraints(productCodeLabel).cell(2, 2);
        primaryInfoPanel.add(productCodeTextField);
        helper.getComponentConstraints(productCodeTextField).cell(3, 2);
        primaryInfoPanel.add(measureUnitLabel);
        helper.getComponentConstraints(measureUnitLabel).cell(0, 3);
        primaryInfoPanel.add(measureUnitComboBox);
        helper.getComponentConstraints(measureUnitComboBox).cell(1, 3);
        primaryInfoPanel.add(productColorLabel);
        helper.getComponentConstraints(productColorLabel).cell(2, 3);
        primaryInfoPanel.add(productColorComboBox);
        helper.getComponentConstraints(productColorComboBox).cell(3, 3);
        primaryInfoPanel.add(producerLabel);
        helper.getComponentConstraints(producerLabel).cell(0, 4);
        primaryInfoPanel.add(producerComboList);
        helper.getComponentConstraints(producerComboList).cell(1, 4);
        primaryInfoPanel.add(quantityPerPackageLabel);
        helper.getComponentConstraints(quantityPerPackageLabel).cell(2, 4);
        primaryInfoPanel.add(quantityPerPackageIntegerField);
        helper.getComponentConstraints(quantityPerPackageIntegerField).cell(3, 4);
        primaryInfoPanel.add(purchaseOptionsPanel);
        helper.getComponentConstraints(purchaseOptionsPanel).width("100%").cell(0, 5).spanX(4);
        primaryInfoPanel.add(pricePanel);
        helper.getComponentConstraints(pricePanel).width("100%").cell(0, 6).spanX(4);
        primaryInfoPanel.add(quantitiesOnStockPanel);
        helper.getComponentConstraints(quantitiesOnStockPanel).width("100%").cell(0, 7).spanX(4);
        primaryInfoPanel.invalidate();


        PropertyDetails propDetails = entityProps.getPropertyDetails("productName");
        productNameTextField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("category");
        ProductCategoriesTreePanel categoryListPanel = new ProductCategoriesTreePanel(getParentDataObjectId());
        productCategoryComboList.bind(bindingGroup, categoryListPanel, product, propDetails,
                "${categoryName}", UpdateStrategy.READ_WRITE);
        productCategoryComboList.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent event) {
                onProductCategoryChanged((ProductCategory) event.getItem());
            }
        }, true);
        //categoryCodeFormatTextField

        propDetails = entityProps.getPropertyDetails("patternMaskFormat");
        PatternMaskFormatListPanel formatsListPanel = new PatternMaskFormatListPanel(getParentDataObjectId());
        productCodeFormatComboList.bind(bindingGroup, formatsListPanel, product, propDetails,
                "${patternName} (${format})", UpdateStrategy.READ_WRITE);
        productCodeFormatComboList.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent event) {
                onProductCodeFormatChanged((PatternMaskFormat) event.getItem());
            }
        }, true);

        propDetails = entityProps.getPropertyDetails("productCode");
        productCodeTextField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("measureUnit");
        measureUnitComboBox.bind(bindingGroup, getMeasureUnits(), product, propDetails);

        propDetails = entityProps.getPropertyDetails("productColor");
        productColorComboBox.bind(bindingGroup, getProductColors(), product, propDetails);

        propDetails = entityProps.getPropertyDetails("producer");
        Classifier producerClassifier = getClassifier(Classifier.Producer.getClassifierCode());
        BusinessPartnersListPanel producerListPanel = new BusinessPartnersListPanel(producerClassifier);
        producerComboList.bind(bindingGroup, producerListPanel, product, propDetails,
                "${displayName}", UpdateStrategy.READ_WRITE);

        propDetails = entityProps.getPropertyDetails("quantityPerPackage");
        quantityPerPackageIntegerField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("purchased");
        purchasedCheckBox.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("salable");
        salableCheckBox.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("obsolete");
        obsoleteCheckBox.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("pricePerQuantity");
        pricePerQuantityDecimalField.bind(bindingGroup, product, propDetails);

        salesPriceDecimalField.setEditable(false);
        propDetails = entityProps.getPropertyDetails("salePrice");
        salesPriceDecimalField.bind(bindingGroup, product, propDetails);

//        propDetails = entityProps.getPropertyDetails("currency");
//        currencyValueLabel.bind(bindingGroup, product, propDetails, "enumValue");

        propDetails = entityProps.getPropertyDetails("minimumQuantity");
        minDecimalField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("defaultQuantity");
        defaultDecimalField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("maximumQuantity");
        maxDecimalField.bind(bindingGroup, product, propDetails);
    }

    private void initAdditionalInfo(BindingGroup bindingGroup, EntityProperties entityProps) {
        MigLayoutHelper helper = new MigLayoutHelper(deliveryInfoPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(6);
        helper.columnGrow(100, 1, 3, 5);
        helper.columnSizeGroup("sg", 1, 3, 5);
        helper.columnFill(1, 3, 5);
        helper.columnGap("15", 1, 3);
        deliveryInfoPanel.add(weightUnitLabel);
        helper.getComponentConstraints(weightUnitLabel).cell(0, 0);
        deliveryInfoPanel.add(weightUnitComboBox);
        helper.getComponentConstraints(weightUnitComboBox).cell(1, 0);
        deliveryInfoPanel.add(weightLabel);
        helper.getComponentConstraints(weightLabel).cell(2, 0);
        deliveryInfoPanel.add(weightDecimalField);
        helper.getComponentConstraints(weightDecimalField).cell(3, 0);
        deliveryInfoPanel.add(deliveryTimeLabel);
        helper.getComponentConstraints(deliveryTimeLabel).cell(4, 0);
        deliveryInfoPanel.add(deliveryTimeIntegerField);
        helper.getComponentConstraints(deliveryTimeIntegerField).cell(5, 0);
        deliveryInfoPanel.invalidate();

        helper = new MigLayoutHelper(dimensionsPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(6);
        helper.columnGrow(100, 1, 3, 5);
        helper.columnSizeGroup("sg", 1, 3, 5);
        helper.columnFill(1, 3, 5);
        helper.columnGap("15", 1, 3);
        dimensionsPanel.add(dimensionUnitLabel);
        helper.getComponentConstraints(dimensionUnitLabel).cell(0, 0);
        dimensionsPanel.add(dimensionUnitComboBox);
        helper.getComponentConstraints(dimensionUnitComboBox).cell(1, 0).spanX(3);
        dimensionsPanel.add(cubatureLabel);
        helper.getComponentConstraints(cubatureLabel).cell(4, 0);
        dimensionsPanel.add(cubatureDecimalField);
        helper.getComponentConstraints(cubatureDecimalField).cell(5, 0);
        dimensionsPanel.add(widthLabel);
        helper.getComponentConstraints(widthLabel).cell(0, 1);
        dimensionsPanel.add(widthDecimalField);
        helper.getComponentConstraints(widthDecimalField).cell(1, 1);
        dimensionsPanel.add(lengthLabel);
        helper.getComponentConstraints(lengthLabel).cell(2, 1);
        dimensionsPanel.add(lengthDecimalField);
        helper.getComponentConstraints(lengthDecimalField).cell(3, 1);
        dimensionsPanel.add(heightLabel);
        helper.getComponentConstraints(heightLabel).cell(4, 1);
        dimensionsPanel.add(heightDecimalField);
        helper.getComponentConstraints(heightDecimalField).cell(5, 1);
        dimensionsPanel.invalidate();

        helper = new MigLayoutHelper(additionalInfoPanel);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(1);
        helper.columnGrow(100, 0);
        helper.columnFill(0);
        additionalInfoPanel.add(deliveryInfoPanel);
        helper.getComponentConstraints(deliveryInfoPanel).cell(0, 0);
        additionalInfoPanel.add(dimensionsPanel);
        helper.getComponentConstraints(dimensionsPanel).cell(0, 1);
        additionalInfoPanel.add(descriptionPanel);
        helper.getComponentConstraints(descriptionPanel).cell(0, 2).height("100%").width("100%");
        additionalInfoPanel.invalidate();

        PropertyDetails propDetails = entityProps.getPropertyDetails("weightUnit");
        weightUnitComboBox.bind(
                bindingGroup,
                getMeasureUnits(MeasurementUnit.Category.MassWeight),
                product,
                propDetails);

        propDetails = entityProps.getPropertyDetails("weight");
        weightDecimalField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("deliveryTime");
        deliveryTimeIntegerField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("dimensionUnit");
        dimensionUnitComboBox.bind(
                bindingGroup,
                getMeasureUnits(MeasurementUnit.Category.Volume),
                product,
                propDetails);

        CubatureBindingListener cubatureBindingListener = new CubatureBindingListener();
        propDetails = entityProps.getPropertyDetails("dimensionWidth");
        Binding binding = widthDecimalField.bind(bindingGroup, product, propDetails);
        binding.addBindingListener(cubatureBindingListener);

        propDetails = entityProps.getPropertyDetails("dimensionLength");
        binding = lengthDecimalField.bind(bindingGroup, product, propDetails);
        binding.addBindingListener(cubatureBindingListener);

        propDetails = entityProps.getPropertyDetails("dimensionHeight");
        binding = heightDecimalField.bind(bindingGroup, product, propDetails);
        binding.addBindingListener(cubatureBindingListener);

        cubatureDecimalField.setEditable(false);

        propDetails = entityProps.getPropertyDetails("description");
        binding = descriptionTextPane.bind(bindingGroup, product, propDetails);
    }

    private void initProductPricing(BindingGroup bindingGroup, EntityProperties entityProps) {
        productPricingPanel.setLayout(new BorderLayout());
        //ProductPricingPanel pricingPanel = new ProductPricingPanel(product);
        PricingPanel pricingPanel = new PricingPanel(product, bindingGroup, entityProps);
        productPricingPanel.add(pricingPanel, BorderLayout.CENTER);
    }

    private void initSuppliers() {
        suppliersListPanel = new SuppliersListPanel();
        suppliersPanel.add(suppliersListPanel, BorderLayout.CENTER);
    }

    private void refreshCubature() {
        System.out.println("refreshCubature().product: " + product);
        BigDecimal cubature;
        if ((cubature = product.getDimensionCubature()) == null) {
            cubatureDecimalField.setText("");
        } else {
            cubatureDecimalField.setText(cubature.toString());
        }
    }

    private void onProductCategoryChanged(ProductCategory productCategory) {
        PatternMaskFormat patternMaskFormat;
        if (productCategory == null || (patternMaskFormat = productCategory.getPatternMaskFormat()) == null) {
            categoryCodeFormatTextField.setText("");
        } else {
            categoryCodeFormatTextField.setText(patternMaskFormat.getFormat());
        }

        setProductCodeMaskFormat();
    }

    private void onProductCodeFormatChanged(PatternMaskFormat patternMaskFormat) {
        setProductCodeMaskFormat();
    }

    private void setProductCodeMaskFormat() {
        PatternMaskFormat patternMaskFormat = null;
        if ((patternMaskFormat = product.getPatternMaskFormat()) == null) {
            ProductCategory productCategory;
            if ((productCategory = product.getCategory()) != null) {
                patternMaskFormat = productCategory.getPatternMaskFormat();
            }
        }

        String maskFormat;
        if (patternMaskFormat != null) {
            maskFormat = patternMaskFormat.getFormat();
        } else {
            maskFormat = null;
        }

        try {
            productCodeTextField.setMask(maskFormat);
        } catch (Exception ex) {
            handleException("maskFormat: " + maskFormat, ex);
        }
    }

    protected EntityProperties getProductEntityProperties() {
        if(productEntityProperties == null) {
            productEntityProperties = getFormSession().getProductEntityProperties();
        }

        return productEntityProperties;
    }

    private EntityProperties getProductSuppliersEntityProperties() {
        if(productSuppliersEntityProperties == null) {
            productSuppliersEntityProperties = getFormSession().getProductSupplierEntityProperties();
        }

        return productSuppliersEntityProperties;
    }

    private List<DbResource> getMeasureUnits() {
        return getFormSession().getMeasureUnits();
    }

    private List<DbResource> getMeasureUnits(MeasurementUnit.Category category) {
        return getFormSession().getMeasureUnits(category);
    }

    private List<DbResource> getProductColors() {
        return getFormSession().getProductColors();
    }

    @Override
    public void performSave(boolean closeAfter) {
        try {
            product = getFormSession().saveProduct(product);
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValue(product);
            if (closeAfter) {
                close();
            }
        } catch (Exception ex) {
            handleException("product: " + product, ex);
        }
    }

    @Override
    public Object getEntity() {
        return product;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        if (entityFormButtonPanel == null) {
            entityFormButtonPanel = new EntityFormButtonPanel();
        }

        return entityFormButtonPanel;
    }

    protected ProductsListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(ProductsListRemote.class);
        }

        return formSession;
    }

    private class CubatureBindingListener extends AbstractBindingListener {

        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event) {
            refreshCubature();
        }
    }

    private class SuppliersListPanel extends AbstractTablePanel {

        private BindingGroup bindingGroup;

        @Override
        protected void initData() {
            super.initData();
            setVisible(AbstractTablePanel.Button.Close, false);

            bindingGroup = new BindingGroup();
            AcaciaTable table = getDataTable();
            JTableBinding tableBinding = table.bind(
                    bindingGroup,
                    getProductSuppliers(),
                    getProductSuppliersEntityProperties(),
                    UpdateStrategy.READ);
            tableBinding.setEditable(false);

            bindingGroup.bind();
        }

        @Override
        protected boolean deleteRow(Object rowObject) {
            return getFormSession().deleteProductSupplier((ProductSupplier) rowObject);
        }

        @Override
        protected Object modifyRow(Object rowObject) {
            return editRow((ProductSupplier) rowObject);
        }

        @Override
        protected Object newRow() {
            return editRow(getFormSession().newProductSupplier(product));
        }

        protected ProductSupplier editRow(ProductSupplier productSupplier) {
            if (productSupplier != null) {
                ProductSupplierPanel entityPanel = new ProductSupplierPanel(productSupplier);
                DialogResponse response = entityPanel.showDialog(this);
                if (DialogResponse.SAVE.equals(response)) {
                    return (ProductSupplier) entityPanel.getSelectedValue();
                }
            }

            return null;
        }

        private List<ProductSupplier> getProductSuppliers() {
            return getFormSession().getProductSuppliers(product);
        }
    }

    private String getResourceString(String key) {
        return getResourceMap().getString(key);
    }

    private void onCurrencyChanged(DbResource dbResource) {
        if (dbResource == null) {
            currencyValueLabel.setText(getResourceString("currencyValueLabel.text"));
            return;
        }

        TextResource textResource = (TextResource) dbResource.getEnumValue();
        currencyValueLabel.setText(textResource.toText());
    }

    private class PricingPanel extends AcaciaPanel {

        private BindingGroup bindingGroup;
        private EntityProperties entityProps;
        private boolean bindingInit;
        private JBButton additionalDiscountButton;
        private JXPercentValueField additionalDiscountPercentField;
        private JBLabel additionalDiscountLabel;
        private JBButton additionalProfitButton;
        private JXPercentValueField additionalProfitPercentField;
        private JBLabel additionalProfitLabel;
        private JXPercentValueField categoryDiscountPercentField;
        private JBLabel categoryDiscountLabel;
        private JXPercentValueField categoryProfitPercentField;
        private JBLabel categoryProfitLabel;
        private JBDecimalField costPriceTextField;
        private JBLabel costPriceLabel;
        private JBComboBox currencyComboBox;
        private JBButton dutyButton;
        private JXPercentValueField dutyPercentField;
        private JBLabel dutyLabel;
        private JBDecimalField listPriceTextField;
        private JBLabel listPriceLabel;
        private JBDecimalField purchasePriceTextField;
        private JBLabel purchasePriceLabel;
        private JBDecimalField salesPriceDecimalField;
        private JBLabel salesPriceLabel;
        private JXPercentValueField totalDiscountPercentField;
        private JBLabel totalDiscountLabel;
        private JXPercentValueField totalProfitPercentField;
        private JBLabel totalProfitLabel;
        private JBButton transportButton;
        private JXPercentValueField transportPricePercentField;
        private JBLabel transportPriceLabel;

        public PricingPanel(SimpleProduct product,
                BindingGroup bindingGroup, EntityProperties entityProps) {
            super(product);
            this.bindingGroup = bindingGroup;
            this.entityProps = entityProps;
            initComponents();
            initData();
        }

        private void initComponents() {
            listPriceLabel = new JBLabel();
            listPriceTextField = new JBDecimalField();
            currencyComboBox = new JBComboBox();
            additionalDiscountPercentField = new JXPercentValueField();
            additionalDiscountButton = new JBButton();
            additionalDiscountLabel = new JBLabel();
            purchasePriceTextField = new JBDecimalField();
            purchasePriceLabel = new JBLabel();
            transportPricePercentField = new JXPercentValueField();
            transportButton = new JBButton();
            transportPriceLabel = new JBLabel();
            dutyPercentField = new JXPercentValueField();
            dutyButton = new JBButton();
            dutyLabel = new JBLabel();
            costPriceTextField = new JBDecimalField();
            costPriceLabel = new JBLabel();
            additionalProfitPercentField = new JXPercentValueField();
            additionalProfitButton = new JBButton();
            additionalProfitLabel = new JBLabel();
            salesPriceDecimalField = new JBDecimalField();
            salesPriceLabel = new JBLabel();
            categoryDiscountPercentField = new JXPercentValueField();
            categoryDiscountLabel = new JBLabel();
            totalDiscountPercentField = new JXPercentValueField();
            totalDiscountLabel = new JBLabel();
            categoryProfitPercentField = new JXPercentValueField();
            categoryProfitLabel = new JBLabel();
            totalProfitPercentField = new JXPercentValueField();
            totalProfitLabel = new JBLabel();

            listPriceLabel.setText(getResourceString("listPriceLabel.text"));
            categoryDiscountLabel.setText(getResourceString("categoryDiscountLabel.text"));
            additionalDiscountLabel.setText(getResourceString("additionalDiscountLabel.text"));
            additionalDiscountButton.setText(getResourceString("additionalDiscountButton.text"));
            totalDiscountLabel.setText(getResourceString("totalDiscountLabel.text"));
            purchasePriceLabel.setText(getResourceString("purchasePriceLabel.text"));
            transportPriceLabel.setText(getResourceString("transportPriceLabel.text"));
            transportButton.setText(getResourceString("transportButton.text"));
            dutyLabel.setText(getResourceString("dutyLabel.text"));
            dutyButton.setText(getResourceString("dutyButton.text"));
            costPriceLabel.setText(getResourceString("costPriceLabel.text"));
            categoryProfitLabel.setText(getResourceString("categoryProfitLabel.text"));
            additionalProfitLabel.setText(getResourceString("additionalProfitLabel.text"));
            additionalProfitButton.setText(getResourceString("additionalProfitButton.text"));
            totalProfitLabel.setText(getResourceString("totalProfitLabel.text"));
            salesPriceLabel.setText(getResourceString("salesPriceLabel.text"));

            purchasePriceTextField.setEditable(false);
            costPriceTextField.setEditable(false);
            salesPriceDecimalField.setEditable(false);

            AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
            AutoCompleteDecorator.decorate(currencyComboBox, resourceToStringConverter);

            add(listPriceLabel);
            add(listPriceTextField);
            add(currencyComboBox);
            add(categoryDiscountLabel);
            add(categoryDiscountPercentField);
            add(additionalDiscountLabel);
            add(additionalDiscountPercentField);
            add(additionalDiscountButton);
            add(totalDiscountLabel);
            add(totalDiscountPercentField);
            add(purchasePriceLabel);
            add(purchasePriceTextField);
            add(transportPriceLabel);
            add(transportPricePercentField);
            add(transportButton);
            add(dutyLabel);
            add(dutyPercentField);
            add(dutyButton);
            add(costPriceLabel);
            add(costPriceTextField);
            add(categoryProfitLabel);
            add(categoryProfitPercentField);
            add(additionalProfitLabel);
            add(additionalProfitPercentField);
            add(additionalProfitButton);
            add(totalProfitLabel);
            add(totalProfitPercentField);
            add(salesPriceLabel);
            add(salesPriceDecimalField);

            MigLayoutHelper helper = new MigLayoutHelper(this);
            helper.setLayoutFillX(true);
            helper.setLayoutWrapAfter(4);
            helper.columnGrow(100, 1, 2);
            helper.columnFill(1, 2, 3);
            helper.columnGap("15", 1, 2);

            int rowNumber = 0;
            helper.getComponentConstraints(listPriceLabel).cell(0, rowNumber);
            helper.getComponentConstraints(listPriceTextField).cell(2, rowNumber);
            helper.getComponentConstraints(currencyComboBox).cell(3, rowNumber);

            helper.getComponentConstraints(categoryDiscountLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(categoryDiscountPercentField).cell(1, rowNumber).spanX(2);

            helper.getComponentConstraints(additionalDiscountLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(additionalDiscountPercentField).cell(1, rowNumber).spanX(2);
            helper.getComponentConstraints(additionalDiscountButton).cell(3, rowNumber);

            helper.getComponentConstraints(totalDiscountLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(totalDiscountPercentField).cell(1, rowNumber).spanX(2);

            helper.getComponentConstraints(purchasePriceLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(purchasePriceTextField).cell(2, rowNumber);

            helper.getComponentConstraints(transportPriceLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(transportPricePercentField).cell(1, rowNumber).spanX(2);
            helper.getComponentConstraints(transportButton).cell(3, rowNumber);

            helper.getComponentConstraints(dutyLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(dutyPercentField).cell(1, rowNumber).spanX(2);
            helper.getComponentConstraints(dutyButton).cell(3, rowNumber);

            helper.getComponentConstraints(costPriceLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(costPriceTextField).cell(2, rowNumber);

            helper.getComponentConstraints(categoryProfitLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(categoryProfitPercentField).cell(1, rowNumber).spanX(2);

            helper.getComponentConstraints(additionalProfitLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(additionalProfitPercentField).cell(1, rowNumber).spanX(2);
            helper.getComponentConstraints(additionalProfitButton).cell(3, rowNumber);

            helper.getComponentConstraints(totalProfitLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(totalProfitPercentField).cell(1, rowNumber).spanX(2);

            helper.getComponentConstraints(salesPriceLabel).cell(0, ++rowNumber);
            helper.getComponentConstraints(salesPriceDecimalField).cell(2, rowNumber);

            invalidate();

            additionalDiscountButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    ProductPercentValue pricingValue;
                    if((pricingValue = selectPricingValue(ProductPercentValue.Type.DISCOUNT)) == null) {
                        return;
                    }

                    additionalDiscountPercentField.setPercent(pricingValue.getPercentValue());
                    product.setDiscountPercentValue(pricingValue);
                    refreshPrices();
                }
            });
            transportButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    ProductPercentValue pricingValue;
                    if((pricingValue = selectPricingValue(ProductPercentValue.Type.TRANSPORT)) == null) {
                        return;
                    }

                    transportPricePercentField.setPercent(pricingValue.getPercentValue());
                    product.setTransportPercentValue(pricingValue);
                    refreshPrices();
                }
            });
            dutyButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    ProductPercentValue pricingValue;
                    if((pricingValue = selectPricingValue(ProductPercentValue.Type.CUSTOMS_DUTY)) == null) {
                        return;
                    }

                    dutyPercentField.setPercent(pricingValue.getPercentValue());
                    product.setCustomsDutyPercentValue(pricingValue);
                    refreshPrices();
                }
            });
            additionalProfitButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    ProductPercentValue pricingValue;
                    if((pricingValue = selectPricingValue(ProductPercentValue.Type.PROFIT)) == null) {
                        return;
                    }

                    additionalProfitPercentField.setPercent(pricingValue.getPercentValue());
                    product.setProfitPercentValue(pricingValue);
                    refreshPrices();
                }
            });
        }

        private ProductPercentValue selectPricingValue(ProductPercentValue.Type type) {
            ProductPricingValueListPanel valuesPanel =
                    new ProductPricingValueListPanel(getOrganizationDataObjectId(), type);
            if (DialogResponse.SELECT.equals(valuesPanel.showDialog(ProductPanel.this))) {
                ProductPercentValue pricingValue;
                if((pricingValue = (ProductPercentValue) valuesPanel.getSelectedRowObject()) != null) {
                    return pricingValue;
                }
            }

            return null;
        }

        @Override
        protected void initData() {
            final SimpleProduct product = getProduct();

            ListPriceBindingListener listPriceBindingListener = new ListPriceBindingListener();
            PropertyDetails propDetails = entityProps.getPropertyDetails("listPrice");
            Binding binding = listPriceTextField.bind(bindingGroup, product, propDetails);
            binding.addBindingListener(listPriceBindingListener);

            propDetails = entityProps.getPropertyDetails("currency");
            binding = currencyComboBox.bind(bindingGroup, getEnumResources(Currency.class), product, propDetails);
            binding.addBindingListener(new AbstractBindingListener() {

                @Override
                public void bindingBecameBound(Binding binding) {
                    onCurrencyChanged(product.getCurrency());
                }
            });
            currencyComboBox.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent event) {
                    onCurrencyChanged((DbResource) event.getItem());
                }
            }, true);

            categoryDiscountPercentField.setEditable(false);
            additionalDiscountPercentField.setEditable(false);
            totalDiscountPercentField.setEditable(false);
            transportPricePercentField.setEditable(JXPercentValueField.Editable.Value);
            transportPricePercentField.setFreezePercent(false);
            dutyPercentField.setEditable(false);
            categoryProfitPercentField.setEditable(false);
            additionalProfitPercentField.setEditable(false);
            totalProfitPercentField.setEditable(false);

            bindingGroup.addBindingListener(new AbstractBindingListener() {

                @Override
                public void bindingBecameBound(Binding binding) {
                    bindingInit();
                }
            });
        }

        private void bindingInit() {
            if (bindingInit) {
                return;
            }
            bindingInit = true;

            refreshPrices();
        }

        private SimpleProduct getProduct() {
            return (SimpleProduct) getMainDataObject();
        }

        private void refreshPrices() {
            if(true)
                return;

            SimpleProduct product = getProduct();
            ProductCategory category = product.getCategory();
            BigDecimal listPrice = product.getListPrice();
            BigDecimal purchasePrice = product.getPurchasePrice();
            BigDecimal costPrice = product.getCostPrice();
            BigDecimal salesPrice = product.getSalePrice();

            if (category != null) {
                categoryDiscountPercentField.setPercent(category.getDiscountPercent());
                categoryProfitPercentField.setPercent(category.getProfitPercent());
            }
//            additionalDiscountPercentField.setPercent(product.getTotalDiscountPercent());
//            totalDiscountPercentField.setPercent(product.getTotalDiscount());

            ProductPercentValue ppv;
//            if ((ppv = product.getTransportPricingValue()) == null) {
//                transportPricePercentField.setPercent(product.getTransportPrice());
//            } else {
//                transportPricePercentField.setPercentValue(ppv.getPercentValue());
//            }
//
//            if ((ppv = product.getDutyPricingValue()) == null) {
//                dutyPercentField.setPercent(product.getDutyPercent());
//            } else {
//                dutyPercentField.setPercentValue(ppv.getPercentValue());
//            }
//
//            additionalProfitPercentField.setPercent(product.getProfitPercent());
//            totalProfitPercentField.setPercent(product.getTotalProfit());

            categoryDiscountPercentField.setBaseValue(listPrice);
            additionalDiscountPercentField.setBaseValue(listPrice);
            totalDiscountPercentField.setBaseValue(listPrice);
            transportPricePercentField.setBaseValue(purchasePrice);
            dutyPercentField.setBaseValue(purchasePrice);
            categoryProfitPercentField.setBaseValue(costPrice);
            additionalProfitPercentField.setBaseValue(costPrice);
            totalProfitPercentField.setBaseValue(costPrice);

            purchasePriceTextField.setValue(purchasePrice);
            costPriceTextField.setValue(costPrice);
            salesPriceDecimalField.setValue(salesPrice);
        }
    }

    private class ListPriceBindingListener extends AbstractBindingListener {

        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event) {
            System.out.println("targetChanged(" + event + ")");
        }
    }
}
