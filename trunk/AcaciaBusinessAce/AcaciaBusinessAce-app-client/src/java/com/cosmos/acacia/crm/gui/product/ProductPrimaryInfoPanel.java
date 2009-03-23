/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.product;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.gui.PatternMaskFormatListPanel;
import com.cosmos.acacia.crm.gui.ProductCategoriesTreePanel;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnersListPanel;
import com.cosmos.acacia.gui.AcaciaComboBox;
import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.JBCheckBox;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBIntegerField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBMaskTextField;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTextField;
import com.cosmos.swingb.MigLayoutHelper;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Miro
 */
public class ProductPrimaryInfoPanel extends JBPanel {

    private ProductPanel productPanel;
    private BindingGroup bindingGroup;
    private EntityProperties entityProps;
    private SimpleProduct product;
    //
    private JBLabel categoryCodeFormatLabel;
    private JBTextField categoryCodeFormatTextField;
    private JBLabel currencyValueLabel;
    private JBLabel defaultLabel;
    private JBDecimalField defaultDecimalField;
    private JBLabel maxLabel;
    private JBDecimalField maxDecimalField;
    private AcaciaComboBox measureUnitComboBox;
    private JBLabel measureUnitLabel;
    private JBLabel minLabel;
    private JBDecimalField minDecimalField;
    private JBCheckBox obsoleteCheckBox;
    private JBPanel pricePanel;
    private JBLabel pricePerQuantityLabel;
    private JBDecimalField pricePerQuantityDecimalField;
    private AcaciaComboList producerComboList;
    private JBLabel producerLabel;
    private AcaciaComboList productCategoryComboList;
    private JBLabel productCategoryLabel;
    private AcaciaComboList productCodeFormatComboList;
    private JBLabel productCodeFormatLabel;
    private JBLabel productCodeLabel;
    private JBMaskTextField productCodeTextField;
    private AcaciaComboBox productColorComboBox;
    private JBLabel productColorLabel;
    private JBLabel productNameLabel;
    private JBTextField productNameTextField;
    private JBPanel purchaseOptionsPanel;
    private JBCheckBox purchasedCheckBox;
    private JBPanel quantitiesOnStockPanel;
    private JBLabel quantityPerPackageLabel;
    private JBIntegerField quantityPerPackageIntegerField;
    private JBCheckBox salableCheckBox;
    private JBLabel salesPriceLabel;
    private JBDecimalField salesPriceDecimalField;

    public ProductPrimaryInfoPanel(ProductPanel productPanel) {
        this.productPanel = productPanel;
        this.bindingGroup = productPanel.getBindingGroup();
        this.entityProps = productPanel.getProductEntityProperties();
        this.product = productPanel.getProduct();
        initComponents();
        initData();
    }

    protected JBLabel getCurrencyValueLabel() {
        return currencyValueLabel;
    }

    protected String getResourceString(String key) {
        return productPanel.getResourceString(key);
    }

    private void initComponents() {
        setLayout(new MigLayout());

        salableCheckBox = new JBCheckBox();
        obsoleteCheckBox = new JBCheckBox();
        purchasedCheckBox = new JBCheckBox();
        maxDecimalField = new JBDecimalField();
        maxLabel = new JBLabel();
        defaultDecimalField = new JBDecimalField();
        defaultLabel = new JBLabel();
        minLabel = new JBLabel();
        minDecimalField = new JBDecimalField();
        salesPriceLabel = new JBLabel();
        pricePerQuantityLabel = new JBLabel();
        salesPriceDecimalField = new JBDecimalField();
        pricePerQuantityDecimalField = new JBDecimalField();
        purchaseOptionsPanel = new JBPanel();
        productCodeTextField = new JBMaskTextField();
        productCodeFormatComboList = new AcaciaComboList();
        categoryCodeFormatLabel = new JBLabel();
        categoryCodeFormatTextField = new JBTextField();
        quantitiesOnStockPanel = new JBPanel();
        pricePanel = new JBPanel();
        quantityPerPackageLabel = new JBLabel();
        quantityPerPackageIntegerField = new JBIntegerField();
        producerLabel = new JBLabel();
        producerComboList = new AcaciaComboList();
        productColorLabel = new JBLabel();
        productColorComboBox = new AcaciaComboBox();
        measureUnitLabel = new JBLabel();
        measureUnitComboBox = new AcaciaComboBox();
        productCodeLabel = new JBLabel();
        productCodeFormatLabel = new JBLabel();
        productCategoryLabel = new JBLabel();
        productCategoryComboList = new AcaciaComboList();
        productNameLabel = new JBLabel();
        productNameTextField = new JBTextField();
        currencyValueLabel = new JBLabel();

        salableCheckBox.setText(getResourceString("salableCheckBox.text")); // NOI18N
        salableCheckBox.setName("salableCheckBox"); // NOI18N

        obsoleteCheckBox.setText(getResourceString("obsoleteCheckBox.text")); // NOI18N
        obsoleteCheckBox.setName("obsoleteCheckBox"); // NOI18N

        purchasedCheckBox.setText(getResourceString("purchasedCheckBox.text")); // NOI18N
        purchasedCheckBox.setName("purchasedCheckBox"); // NOI18N

        maxDecimalField.setName("maxTextField"); // NOI18N

        maxLabel.setText(getResourceString("maxLabel.text")); // NOI18N
        maxLabel.setName("maxLabel"); // NOI18N

        defaultDecimalField.setName("defaultTextField"); // NOI18N

        defaultLabel.setText(getResourceString("defaultLabel.text")); // NOI18N
        defaultLabel.setName("defaultLabel"); // NOI18N

        minLabel.setText(getResourceString("minLabel.text")); // NOI18N
        minLabel.setName("minLabel"); // NOI18N

        minDecimalField.setName("minTextField"); // NOI18N

        salesPriceLabel.setText(getResourceString("salesPriceLabel.text")); // NOI18N
        salesPriceLabel.setName("salesPriceLabel"); // NOI18N

        pricePerQuantityLabel.setText(getResourceString("pricePerQuantityLabel.text")); // NOI18N
        pricePerQuantityLabel.setName("pricePerQuantityLabel"); // NOI18N

        salesPriceDecimalField.setName("salesPriceTextField"); // NOI18N

        pricePerQuantityDecimalField.setName("pricePerQuantityTextField"); // NOI18N

        purchaseOptionsPanel.setName("purchaseOptionsPanel"); // NOI18N
        purchaseOptionsPanel.setLayout(new MigLayout());

        productCodeTextField.setText(getResourceString("productCodeTextField.text")); // NOI18N
        productCodeTextField.setName("productCodeTextField"); // NOI18N

        productCodeFormatComboList.setName("productCodeFormatComboList"); // NOI18N

        categoryCodeFormatLabel.setText(getResourceString("categoryCodeFormatLabel.text")); // NOI18N
        categoryCodeFormatLabel.setName("categoryCodeFormatLabel"); // NOI18N

        categoryCodeFormatTextField.setEditable(false);
        categoryCodeFormatTextField.setName("categoryCodeFormatTextField"); // NOI18N

        quantitiesOnStockPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("quantitiesOnStockPanel.border.title"))); // NOI18N
        quantitiesOnStockPanel.setName("quantitiesOnStockPanel"); // NOI18N
        quantitiesOnStockPanel.setLayout(new MigLayout());

        pricePanel.setBorder(BorderFactory.createTitledBorder(getResourceString("pricePanel.border.title"))); // NOI18N
        pricePanel.setName("pricePanel"); // NOI18N
        pricePanel.setLayout(new MigLayout());

        quantityPerPackageLabel.setText(getResourceString("quantityPerPackageLabel.text")); // NOI18N
        quantityPerPackageLabel.setName("quantityPerPackageLabel"); // NOI18N

        quantityPerPackageIntegerField.setName("quantityPerPackageTextField"); // NOI18N

        producerLabel.setText(getResourceString("producerLabel.text")); // NOI18N
        producerLabel.setName("producerLabel"); // NOI18N

        producerComboList.setName("producerComboList"); // NOI18N

        productColorLabel.setText(getResourceString("productColorLabel.text")); // NOI18N
        productColorLabel.setName("productColorLabel"); // NOI18N

        productColorComboBox.setName("productColorComboBox"); // NOI18N

        measureUnitLabel.setText(getResourceString("measureUnitLabel.text")); // NOI18N
        measureUnitLabel.setName("measureUnitLabel"); // NOI18N

        measureUnitComboBox.setName("measureUnitComboBox"); // NOI18N

        productCodeLabel.setText(getResourceString("productCodeLabel.text")); // NOI18N
        productCodeLabel.setName("productCodeLabel"); // NOI18N

        productCodeFormatLabel.setText(getResourceString("productCodeFormatLabel.text")); // NOI18N
        productCodeFormatLabel.setName("productCodeFormatLabel"); // NOI18N

        productCategoryLabel.setText(getResourceString("productCategoryLabel.text")); // NOI18N
        productCategoryLabel.setName("productCategoryLabel"); // NOI18N

        productCategoryComboList.setName("productCategoryComboList"); // NOI18N

        productNameLabel.setText(getResourceString("productNameLabel.text")); // NOI18N
        productNameLabel.setName("productNameLabel"); // NOI18N

        productNameTextField.setName("productNameTextField"); // NOI18N

        currencyValueLabel.setText(getResourceString("currencyValueLabel.text")); // NOI18N
        currencyValueLabel.setName("currencyValueLabel"); // NOI18N

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

        helper = new MigLayoutHelper(this);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(4);
        helper.columnGrow(100, 1, 3);
        helper.columnSizeGroup("sg", 1, 3);
        helper.columnFill(1, 3);
        helper.columnGap("15", 1);
        add(productNameLabel);
        helper.getComponentConstraints(productNameLabel).cell(0, 0);
        add(productNameTextField);
        helper.getComponentConstraints(productNameTextField).cell(1, 0).spanX(3);
        add(productCategoryLabel);
        helper.getComponentConstraints(productCategoryLabel).cell(0, 1);
        add(productCategoryComboList);
        helper.getComponentConstraints(productCategoryComboList).cell(1, 1);
        add(categoryCodeFormatLabel);
        helper.getComponentConstraints(categoryCodeFormatLabel).cell(2, 1);
        add(categoryCodeFormatTextField);
        helper.getComponentConstraints(categoryCodeFormatTextField).cell(3, 1);
        add(productCodeFormatLabel);
        helper.getComponentConstraints(productCodeFormatLabel).cell(0, 2);
        add(productCodeFormatComboList);
        helper.getComponentConstraints(productCodeFormatComboList).cell(1, 2);
        add(productCodeLabel);
        helper.getComponentConstraints(productCodeLabel).cell(2, 2);
        add(productCodeTextField);
        helper.getComponentConstraints(productCodeTextField).cell(3, 2);
        add(measureUnitLabel);
        helper.getComponentConstraints(measureUnitLabel).cell(0, 3);
        add(measureUnitComboBox);
        helper.getComponentConstraints(measureUnitComboBox).cell(1, 3);
        add(productColorLabel);
        helper.getComponentConstraints(productColorLabel).cell(2, 3);
        add(productColorComboBox);
        helper.getComponentConstraints(productColorComboBox).cell(3, 3);
        add(producerLabel);
        helper.getComponentConstraints(producerLabel).cell(0, 4);
        add(producerComboList);
        helper.getComponentConstraints(producerComboList).cell(1, 4);
        add(quantityPerPackageLabel);
        helper.getComponentConstraints(quantityPerPackageLabel).cell(2, 4);
        add(quantityPerPackageIntegerField);
        helper.getComponentConstraints(quantityPerPackageIntegerField).cell(3, 4);
        add(purchaseOptionsPanel);
        helper.getComponentConstraints(purchaseOptionsPanel).width("100%").cell(0, 5).spanX(4);
        add(pricePanel);
        helper.getComponentConstraints(pricePanel).width("100%").cell(0, 6).spanX(4);
        add(quantitiesOnStockPanel);
        helper.getComponentConstraints(quantitiesOnStockPanel).width("100%").cell(0, 7).spanX(4);
        invalidate();
    }

    private void initData() {
        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(measureUnitComboBox, resourceToStringConverter);
        AutoCompleteDecorator.decorate(productColorComboBox, resourceToStringConverter);

        PropertyDetails propDetails = entityProps.getPropertyDetails("productName");
        productNameTextField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getPropertyDetails("category");
        ProductCategoriesTreePanel categoryListPanel = new ProductCategoriesTreePanel(
                productPanel.getParentDataObjectId());
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
        PatternMaskFormatListPanel formatsListPanel = new PatternMaskFormatListPanel(
                productPanel.getParentDataObjectId());
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
        measureUnitComboBox.bind(bindingGroup, productPanel.getMeasureUnits(), product, propDetails);

        propDetails = entityProps.getPropertyDetails("productColor");
        productColorComboBox.bind(bindingGroup, productPanel.getProductColors(), product, propDetails);

        propDetails = entityProps.getPropertyDetails("producer");
        Classifier producerClassifier = productPanel.getProducerClassifier();
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

    protected void setProductCodeMaskFormat() {
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
}
