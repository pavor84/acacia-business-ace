/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.product;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.data.ProductPercentValue;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.gui.pricing.ProductPricingValueListPanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPercentField;
import com.cosmos.swingb.JBPercentValueSynchronizer;
import com.cosmos.swingb.MigLayoutHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.AbstractAction;
import javax.swing.SwingConstants;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Miro
 */
public class ProductPricingPanel extends AcaciaPanel {

    private ProductPanel productPanel;
    private BindingGroup bindingGroup;
    private EntityProperties entityProps;
    private boolean bindingInit;
    private JBPercentValueSynchronizer percentValueSynchronizer;
    //
    // Row 0
    private JBLabel categoryLabel;
    private JBLabel productLabel;
    private JBLabel percentLabel;
    private JBLabel valueLabel;
    private JBLabel totalLabel;
    // Row 1
    private JBLabel listPriceLabel;
    private JBDecimalField listPriceDecimalField;
    private JBLabel currencyLabel;
    private JBComboBox currencyComboBox;
    // Row 2
    private JBLabel discountLabel;
    private JBPercentField discountCategoryPercentField;
    private JBPercentField discountProductPercentField;
    private JBPercentField discountPercentField;
    private JBDecimalField discountDecimalField;
    private JBButton discountClearButton;
    private JBButton discountSelectButton;
    // Row 3
    private JBLabel purchasePriceLabel;
    private JBDecimalField purchasePriceDecimalField;
    // Row 4
    private JBLabel transportLabel;
    private JBPercentField transportCategoryPercentField;
    private JBPercentField transportProductPercentField;
    private JBDecimalField transportValueField;
    private JBDecimalField transportDecimalField;
    private JBButton transportClearButton;
    private JBButton transportSelectButton;
    // Row 5
    private JBLabel baseDutyPriceLabel;
    private JBDecimalField baseDutyPriceDecimalField;
    // Row 6
    private JBLabel customsDutyLabel;
    private JBPercentField customsDutyCategoryPercentField;
    private JBPercentField customsDutyProductPercentField;
    private JBPercentField customsDutyPercentField;
    private JBDecimalField customsDutyDecimalField;
    private JBButton customsDutyClearButton;
    private JBButton customsDutySelectButton;
    // Row 7
    private JBLabel exciseDutyLabel;
    private JBPercentField exciseDutyCategoryPercentField;
    private JBPercentField exciseDutyProductPercentField;
    private JBPercentField exciseDutyPercentField;
    private JBDecimalField exciseDutyDecimalField;
    private JBButton exciseDutyClearButton;
    private JBButton exciseDutySelectButton;
    // Row 8
    private JBLabel costPriceLabel;
    private JBDecimalField costPriceDecimalField;
    // Row 9
    private JBLabel profitLabel;
    private JBPercentField profitCategoryPercentField;
    private JBPercentField profitProductPercentField;
    private JBPercentField profitPercentField;
    private JBDecimalField profitDecimalField;
    private JBButton profitClearButton;
    private JBButton profitSelectButton;
    // Row 10
    private JBLabel salesPriceLabel;
    private JBDecimalField salesPriceDecimalField;

    public ProductPricingPanel(ProductPanel productPanel) {
        super(productPanel.getProduct());
        this.productPanel = productPanel;
        this.bindingGroup = productPanel.getBindingGroup();
        this.entityProps = productPanel.getProductEntityProperties();
        initComponents();
        initData();
    }

    protected String getResourceString(String key) {
        return productPanel.getResourceString(key);
    }

    private void initComponents() {
        // Row 0
        categoryLabel = new JBLabel();
        productLabel = new JBLabel();
        percentLabel = new JBLabel();
        valueLabel = new JBLabel();
        totalLabel = new JBLabel();
        // Row 1
        listPriceLabel = new JBLabel();
        listPriceDecimalField = new JBDecimalField();
        currencyLabel = new JBLabel();
        currencyComboBox = new JBComboBox();
        // Row 2
        discountLabel = new JBLabel();
        discountCategoryPercentField = new JBPercentField();
        discountProductPercentField = new JBPercentField();
        discountPercentField = new JBPercentField();
        discountDecimalField = new JBDecimalField();
        discountClearButton = new JBButton();
        discountSelectButton = new JBButton();
        // Row 3
        purchasePriceLabel = new JBLabel();
        purchasePriceDecimalField = new JBDecimalField();
        // Row 4
        transportLabel = new JBLabel();
        transportCategoryPercentField = new JBPercentField();
        transportProductPercentField = new JBPercentField();
        transportValueField = new JBDecimalField();
        transportDecimalField = new JBDecimalField();
        transportClearButton = new JBButton();
        transportSelectButton = new JBButton();
        // Row 5
        baseDutyPriceLabel = new JBLabel();
        baseDutyPriceDecimalField = new JBDecimalField();
        // Row 6
        customsDutyLabel = new JBLabel();
        customsDutyCategoryPercentField = new JBPercentField();
        customsDutyProductPercentField = new JBPercentField();
        customsDutyPercentField = new JBPercentField();
        customsDutyDecimalField = new JBDecimalField();
        customsDutyClearButton = new JBButton();
        customsDutySelectButton = new JBButton();
        // Row 7
        exciseDutyLabel = new JBLabel();
        exciseDutyCategoryPercentField = new JBPercentField();
        exciseDutyProductPercentField = new JBPercentField();
        exciseDutyPercentField = new JBPercentField();
        exciseDutyDecimalField = new JBDecimalField();
        exciseDutyClearButton = new JBButton();
        exciseDutySelectButton = new JBButton();
        // Row 8
        costPriceLabel = new JBLabel();
        costPriceDecimalField = new JBDecimalField();
        // Row 9
        profitLabel = new JBLabel();
        profitCategoryPercentField = new JBPercentField();
        profitProductPercentField = new JBPercentField();
        profitPercentField = new JBPercentField();
        profitDecimalField = new JBDecimalField();
        profitClearButton = new JBButton();
        profitSelectButton = new JBButton();
        // Row 10
        salesPriceLabel = new JBLabel();
        salesPriceDecimalField = new JBDecimalField();
        //
        percentValueSynchronizer = new PercentValueSynchronizer(
                transportProductPercentField, transportValueField);

        // Row 0
        categoryLabel.setText(getResourceString("categoryLabel.text"));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        productLabel.setText(getResourceString("productLabel.text"));
        productLabel.setHorizontalAlignment(SwingConstants.CENTER);
        percentLabel.setText(getResourceString("percentLabel.text"));
        percentLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setText(getResourceString("valueLabel.text"));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setText(getResourceString("totalLabel.text"));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Row 1
        listPriceLabel.setText(getResourceString("listPriceLabel.text"));
        currencyLabel.setText(getResourceString("currencyLabel.text"));
        // Row 2
        discountLabel.setText(getResourceString("discountLabel.text"));
        discountClearButton.setText(getResourceString("discountClearButton.text"));
        discountSelectButton.setText(getResourceString("discountSelectButton.text"));
        // Row 3
        purchasePriceLabel.setText(getResourceString("purchasePriceLabel.text"));
        // Row 4
        transportLabel.setText(getResourceString("transportLabel.text"));
        transportClearButton.setText(getResourceString("transportClearButton.text"));
        transportSelectButton.setText(getResourceString("transportSelectButton.text"));
        // Row 5
        baseDutyPriceLabel.setText(getResourceString("baseDutyPriceLabel.text"));
        // Row 6
        customsDutyLabel.setText(getResourceString("customsDutyLabel.text"));
        customsDutyClearButton.setText(getResourceString("customsDutyClearButton.text"));
        customsDutySelectButton.setText(getResourceString("customsDutySelectButton.text"));
        // Row 7
        exciseDutyLabel.setText(getResourceString("exciseDutyLabel.text"));
        exciseDutyClearButton.setText(getResourceString("exciseDutyClearButton.text"));
        exciseDutySelectButton.setText(getResourceString("exciseDutySelectButton.text"));
        // Row 8
        costPriceLabel.setText(getResourceString("costPriceLabel.text"));
        // Row 9
        profitLabel.setText(getResourceString("profitLabel.text"));
        profitClearButton.setText(getResourceString("profitClearButton.text"));
        profitSelectButton.setText(getResourceString("profitSelectButton.text"));
        // Row 10
        salesPriceLabel.setText(getResourceString("salesPriceLabel.text"));

        discountCategoryPercentField.setEditable(false);
        discountProductPercentField.setEditable(false);
        discountPercentField.setEditable(false);
        discountDecimalField.setEditable(false);
        purchasePriceDecimalField.setEditable(false);
        transportCategoryPercentField.setEditable(false);
        transportProductPercentField.setEditable(false);
        transportDecimalField.setEditable(false);
        baseDutyPriceDecimalField.setEditable(false);
        customsDutyCategoryPercentField.setEditable(false);
        customsDutyProductPercentField.setEditable(false);
        customsDutyPercentField.setEditable(false);
        customsDutyDecimalField.setEditable(false);
        exciseDutyCategoryPercentField.setEditable(false);
        exciseDutyProductPercentField.setEditable(false);
        exciseDutyPercentField.setEditable(false);
        exciseDutyDecimalField.setEditable(false);
        costPriceDecimalField.setEditable(false);
        profitCategoryPercentField.setEditable(false);
        profitProductPercentField.setEditable(false);
        profitPercentField.setEditable(false);
        profitDecimalField.setEditable(false);
        salesPriceDecimalField.setEditable(false);

        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(currencyComboBox, resourceToStringConverter);

        // Row 0
        add(categoryLabel);
        add(productLabel);
        add(percentLabel);
        add(valueLabel);
        add(totalLabel);
        // Row 1
        add(listPriceLabel);
        add(listPriceDecimalField);
        add(currencyLabel);
        add(currencyComboBox);
        // Row 2
        add(discountLabel);
        add(discountCategoryPercentField);
        add(discountProductPercentField);
        add(discountPercentField);
        add(discountDecimalField);
        add(discountClearButton);
        add(discountSelectButton);
        // Row 3
        add(purchasePriceLabel);
        add(purchasePriceDecimalField);
        // Row 4
        add(transportLabel);
        add(transportCategoryPercentField);
        add(transportProductPercentField);
        add(transportValueField);
        add(transportDecimalField);
        add(transportClearButton);
        add(transportSelectButton);
        // Row 5
        add(baseDutyPriceLabel);
        add(baseDutyPriceDecimalField);
        // Row 6
        add(customsDutyLabel);
        add(customsDutyCategoryPercentField);
        add(customsDutyProductPercentField);
        add(customsDutyPercentField);
        add(customsDutyDecimalField);
        add(customsDutyClearButton);
        add(customsDutySelectButton);
        // Row 7
        add(exciseDutyLabel);
        add(exciseDutyCategoryPercentField);
        add(exciseDutyProductPercentField);
        add(exciseDutyPercentField);
        add(exciseDutyDecimalField);
        add(exciseDutyClearButton);
        add(exciseDutySelectButton);
        // Row 8
        add(costPriceLabel);
        add(costPriceDecimalField);
        // Row 9
        add(profitLabel);
        add(profitCategoryPercentField);
        add(profitProductPercentField);
        add(profitPercentField);
        add(profitDecimalField);
        add(profitClearButton);
        add(profitSelectButton);
        // Row 10
        add(salesPriceLabel);
        add(salesPriceDecimalField);

        int mainLabelColumn = 0;
        int mainValueColumn = 1;
        int labelColumn = 2;
        int categoryColumn = 3;
        int productColumn = 4;
        int percentColumn = 5;
        int valueColumn = 6;
        int clearColumn = 7;
        int selectColumn = 8;
        MigLayoutHelper helper = new MigLayoutHelper(this);
        helper.setLayoutFillX(true);
        helper.columnGap("15", mainValueColumn, valueColumn);
        //helper.rowGap("15", 1);
        helper.columnSizeGroup("percentSG", categoryColumn, productColumn, percentColumn);
        helper.columnSizeGroup("valueSG", mainValueColumn, valueColumn);
        helper.columnSizeGroup("buttonSG", clearColumn, selectColumn);
        helper.columnGrow(50, mainValueColumn, valueColumn);
        helper.columnGrow(35, categoryColumn, productColumn, percentColumn);
        helper.columnGrow(15, clearColumn, selectColumn);
        helper.columnFill(mainValueColumn, categoryColumn, productColumn, percentColumn,
                valueColumn, clearColumn, selectColumn);

        // Row 0
        int rowNumber = 0;
        helper.getComponentConstraints(listPriceLabel).cell(mainLabelColumn, rowNumber);
        helper.getComponentConstraints(listPriceDecimalField).cell(mainValueColumn, rowNumber);
        helper.getComponentConstraints(currencyLabel).cell(labelColumn, rowNumber);
        helper.getComponentConstraints(currencyComboBox).cell(categoryColumn, rowNumber).spanX(2);
        // Row 1
        rowNumber++;
        helper.getComponentConstraints(salesPriceLabel).cell(mainLabelColumn, rowNumber);
        helper.getComponentConstraints(salesPriceDecimalField).cell(mainValueColumn, rowNumber);
        // Row 2
        rowNumber++;
        helper.getComponentConstraints(categoryLabel).cell(categoryColumn, rowNumber);
        helper.getComponentConstraints(productLabel).cell(productColumn, rowNumber);
        helper.getComponentConstraints(percentLabel).cell(percentColumn, rowNumber);
        helper.getComponentConstraints(valueLabel).cell(valueColumn, rowNumber);
        helper.getComponentConstraints(totalLabel).cell(mainValueColumn, rowNumber);
        // Row 3
        rowNumber++;
        helper.getComponentConstraints(discountLabel).cell(labelColumn, rowNumber);
        helper.getComponentConstraints(discountCategoryPercentField).cell(categoryColumn, rowNumber);
        helper.getComponentConstraints(discountProductPercentField).cell(productColumn, rowNumber);
        helper.getComponentConstraints(discountPercentField).cell(percentColumn, rowNumber);
        helper.getComponentConstraints(discountDecimalField).cell(valueColumn, rowNumber);
        helper.getComponentConstraints(discountClearButton).cell(clearColumn, rowNumber);
        helper.getComponentConstraints(discountSelectButton).cell(selectColumn, rowNumber);
        // Row 4
        rowNumber++;
        helper.getComponentConstraints(purchasePriceLabel).cell(mainLabelColumn, rowNumber);
        helper.getComponentConstraints(purchasePriceDecimalField).cell(mainValueColumn, rowNumber);
        // Row 5
        rowNumber++;
        helper.getComponentConstraints(transportLabel).cell(labelColumn, rowNumber);
        helper.getComponentConstraints(transportCategoryPercentField).cell(categoryColumn, rowNumber);
        helper.getComponentConstraints(transportProductPercentField).cell(productColumn, rowNumber);
        helper.getComponentConstraints(transportValueField).cell(percentColumn, rowNumber);
        helper.getComponentConstraints(transportDecimalField).cell(valueColumn, rowNumber);
        helper.getComponentConstraints(transportClearButton).cell(clearColumn, rowNumber);
        helper.getComponentConstraints(transportSelectButton).cell(selectColumn, rowNumber);
        // Row 6
        rowNumber++;
        helper.getComponentConstraints(baseDutyPriceLabel).cell(mainLabelColumn, rowNumber);
        helper.getComponentConstraints(baseDutyPriceDecimalField).cell(mainValueColumn, rowNumber);
        // Row 7
        rowNumber++;
        helper.getComponentConstraints(customsDutyLabel).cell(labelColumn, rowNumber);
        helper.getComponentConstraints(customsDutyCategoryPercentField).cell(categoryColumn, rowNumber);
        helper.getComponentConstraints(customsDutyProductPercentField).cell(productColumn, rowNumber);
        helper.getComponentConstraints(customsDutyPercentField).cell(percentColumn, rowNumber);
        helper.getComponentConstraints(customsDutyDecimalField).cell(valueColumn, rowNumber);
        helper.getComponentConstraints(customsDutyClearButton).cell(clearColumn, rowNumber);
        helper.getComponentConstraints(customsDutySelectButton).cell(selectColumn, rowNumber);
        // Row 8
        rowNumber++;
        helper.getComponentConstraints(exciseDutyLabel).cell(labelColumn, rowNumber);
        helper.getComponentConstraints(exciseDutyCategoryPercentField).cell(categoryColumn, rowNumber);
        helper.getComponentConstraints(exciseDutyProductPercentField).cell(productColumn, rowNumber);
        helper.getComponentConstraints(exciseDutyPercentField).cell(percentColumn, rowNumber);
        helper.getComponentConstraints(exciseDutyDecimalField).cell(valueColumn, rowNumber);
        helper.getComponentConstraints(exciseDutyClearButton).cell(clearColumn, rowNumber);
        helper.getComponentConstraints(exciseDutySelectButton).cell(selectColumn, rowNumber);
        // Row 9
        rowNumber++;
        helper.getComponentConstraints(costPriceLabel).cell(mainLabelColumn, rowNumber);
        helper.getComponentConstraints(costPriceDecimalField).cell(mainValueColumn, rowNumber);
        // Row 10
        rowNumber++;
        helper.getComponentConstraints(profitLabel).cell(labelColumn, rowNumber);
        helper.getComponentConstraints(profitCategoryPercentField).cell(categoryColumn, rowNumber);
        helper.getComponentConstraints(profitProductPercentField).cell(productColumn, rowNumber);
        helper.getComponentConstraints(profitPercentField).cell(percentColumn, rowNumber);
        helper.getComponentConstraints(profitDecimalField).cell(valueColumn, rowNumber);
        helper.getComponentConstraints(profitClearButton).cell(clearColumn, rowNumber);
        helper.getComponentConstraints(profitSelectButton).cell(selectColumn, rowNumber);

        invalidate();

        discountSelectButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ProductPercentValue pricingValue;
                if ((pricingValue = selectPricingValue(ProductPercentValue.Type.DISCOUNT)) == null) {
                    return;
                }

                getProduct().setDiscountPercentValue(pricingValue);
            }
        });
        discountClearButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                getProduct().setDiscountPercentValue(null);
            }
        });

        transportSelectButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ProductPercentValue pricingValue;
                if ((pricingValue = selectPricingValue(ProductPercentValue.Type.TRANSPORT)) == null) {
                    return;
                }

                getProduct().setTransportPercentValue(pricingValue);
            }
        });
        transportClearButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                percentValueSynchronizer.unbind();
                try {
                    getProduct().setTransportPercentValue(null);
                } finally {
                    percentValueSynchronizer.bind();
                }
            }
        });

        customsDutySelectButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ProductPercentValue pricingValue;
                if ((pricingValue = selectPricingValue(ProductPercentValue.Type.CUSTOMS_DUTY)) == null) {
                    return;
                }

                getProduct().setCustomsDutyPercentValue(pricingValue);
            }
        });
        customsDutyClearButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                getProduct().setCustomsDutyPercentValue(null);
            }
        });

        exciseDutySelectButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ProductPercentValue pricingValue;
                if ((pricingValue = selectPricingValue(ProductPercentValue.Type.EXCISE_DUTY)) == null) {
                    return;
                }

                getProduct().setExciseDutyPercentValue(pricingValue);
            }
        });
        exciseDutyClearButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                getProduct().setExciseDutyPercentValue(null);
            }
        });

        profitSelectButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ProductPercentValue pricingValue;
                if ((pricingValue = selectPricingValue(ProductPercentValue.Type.PROFIT)) == null) {
                    return;
                }

                getProduct().setProfitPercentValue(pricingValue);
            }
        });
        profitClearButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                getProduct().setProfitPercentValue(null);
            }
        });
    }

    private ProductPercentValue selectPricingValue(ProductPercentValue.Type type) {
        ProductPricingValueListPanel valuesPanel =
                new ProductPricingValueListPanel(getOrganizationDataObjectId(), type);
        if (DialogResponse.SELECT.equals(valuesPanel.showDialog(productPanel))) {
            ProductPercentValue pricingValue;
            if ((pricingValue = (ProductPercentValue) valuesPanel.getSelectedRowObject()) != null) {
                return pricingValue;
            }
        }

        return null;
    }

    @Override
    protected void initData() {
        final SimpleProduct product = getProduct();
        product.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent event) {
                onProductPropertyChanged(event);
            }
        });

        //ListPriceBindingListener listPriceBindingListener = new ListPriceBindingListener();
        PropertyDetails propDetails = entityProps.getPropertyDetails("listPrice");
        Binding binding = listPriceDecimalField.bind(bindingGroup, product, propDetails);
        //binding.addBindingListener(listPriceBindingListener);

        propDetails = entityProps.getPropertyDetails("currency");
        binding = currencyComboBox.bind(bindingGroup, getEnumResources(Currency.class), product, propDetails);
        binding.addBindingListener(new AbstractBindingListener() {

            @Override
            public void bindingBecameBound(Binding binding) {
                productPanel.onCurrencyChanged(getProduct().getCurrency());
            }
        });

        propDetails = entityProps.getPropertyDetails("transportValue");
        transportValueField.bind(bindingGroup, product, propDetails).addBindingListener(
                new AbstractBindingListener() {

            @Override
            public void bindingBecameBound(Binding binding) {
                percentValueSynchronizer.bind();
            }

            @Override
            public void bindingBecameUnbound(Binding binding) {
                percentValueSynchronizer.unbind();
            }
        });

        currencyComboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent event) {
                productPanel.onCurrencyChanged((DbResource) event.getItem());
            }
        }, true);

        bindingGroup.addBindingListener(new AbstractBindingListener() {

            @Override
            public void bindingBecameBound(Binding binding) {
                bindingInit();
            }
        });
    }

    private static Set<String> pricingProperties;

    private static Set<String> getPricingProperties() {
        if(pricingProperties == null) {
            pricingProperties = new TreeSet<String>();
            pricingProperties.add("customsDutyPercentValue");
            pricingProperties.add("discountPercentValue");
            pricingProperties.add("exciseDutyPercentValue");
            pricingProperties.add("profitPercentValue");
            pricingProperties.add("transportPercentValue");
            pricingProperties.add("transportValue");
            pricingProperties.add("listPrice");
        }

        return pricingProperties;
    }

    private void onProductPropertyChanged(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        if(!getPricingProperties().contains(propertyName)) {
            return;
        }
        Object newValue = event.getNewValue();
        Object oldValue = event.getOldValue();
        if(newValue == oldValue || newValue == null && oldValue == null ||
                newValue != null && newValue.equals(oldValue) ||
                oldValue != null && oldValue.equals(newValue)) {
            return;
        }

        refreshPrices();
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

    protected void refreshPrices() {
        SimpleProduct product = getProduct();

        ProductPercentValue ppv;
        if((ppv = product.getDiscountPercentValue()) != null)
            discountProductPercentField.setValue(ppv.getPercentValue());
        else
            discountProductPercentField.setValue(null);
        discountPercentField.setValue(product.getTotalDiscountPercent());
        discountDecimalField.setValue(product.getTotalDiscountValue());

        purchasePriceDecimalField.setValue(product.getPurchasePrice());

        if((ppv = product.getTransportPercentValue()) != null)
            transportProductPercentField.setValue(ppv.getPercentValue());
        else
            transportProductPercentField.setValue(null);
        transportDecimalField.setValue(product.getTransportPrice());

        baseDutyPriceDecimalField.setValue(product.getBaseDutyPrice());
        if((ppv = product.getCustomsDutyPercentValue()) != null)
            customsDutyProductPercentField.setValue(ppv.getPercentValue());
        else
            customsDutyProductPercentField.setValue(null);
        customsDutyPercentField.setValue(product.getCustomsDutyPercent());
        customsDutyDecimalField.setValue(product.getCustomsDutyValue());

        if((ppv = product.getExciseDutyPercentValue()) != null)
            exciseDutyProductPercentField.setValue(ppv.getPercentValue());
        else
            exciseDutyProductPercentField.setValue(null);
        exciseDutyPercentField.setValue(product.getExciseDutyPercent());
        exciseDutyDecimalField.setValue(product.getExciseDutyValue());

        costPriceDecimalField.setValue(product.getCostPrice());

        if((ppv = product.getProfitPercentValue()) != null)
            profitProductPercentField.setValue(ppv.getPercentValue());
        else
            profitProductPercentField.setValue(null);
        profitPercentField.setValue(product.getTotalProfitPercent());
        profitDecimalField.setValue(product.getTotalProfitValue());

        salesPriceDecimalField.setValue(product.getSalesPrice());

        ProductCategory category = product.getCategory();
        if((category = product.getCategory()) != null) {
            discountCategoryPercentField.setValue(category.getDiscountPercent());
            transportCategoryPercentField.setValue(category.getTransportPercent());
            customsDutyCategoryPercentField.setValue(category.getCustomsDutyPercent());
            exciseDutyCategoryPercentField.setValue(category.getExciseDutyPercent());
            profitCategoryPercentField.setValue(category.getProfitPercent());
        } else {
            discountCategoryPercentField.setValue(null);
            transportCategoryPercentField.setValue(null);
            customsDutyCategoryPercentField.setValue(null);
            exciseDutyCategoryPercentField.setValue(null);
            profitCategoryPercentField.setValue(null);
        }

        productPanel.refreshPrimaryInfoForm();
    }

    private class PercentValueSynchronizer extends JBPercentValueSynchronizer {

        public PercentValueSynchronizer(JBPercentField percentField, JBDecimalField decimalField) {
            super(percentField, decimalField);
        }

        @Override
        protected void setPercentValue(BigDecimal value) {
            super.setPercentValue(value);
            getProduct().setTransportPercentValue(null);
        }
    }

//    private class ListPriceBindingListener extends AbstractBindingListener {
//
//        @Override
//        public void targetChanged(Binding binding, PropertyStateEvent event) {
//            System.out.println("targetChanged(" + event + ")");
//        }
//    }
}
