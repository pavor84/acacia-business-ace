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
import com.cosmos.swingb.MigLayoutHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.swingx.JXPercentValueField;
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
    //
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
                if ((pricingValue = selectPricingValue(ProductPercentValue.Type.DISCOUNT)) == null) {
                    return;
                }

                additionalDiscountPercentField.setPercent(pricingValue.getPercentValue());
                getProduct().setDiscountPercentValue(pricingValue);
                refreshPrices();
            }
        });
        transportButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ProductPercentValue pricingValue;
                if ((pricingValue = selectPricingValue(ProductPercentValue.Type.TRANSPORT)) == null) {
                    return;
                }

                transportPricePercentField.setPercent(pricingValue.getPercentValue());
                getProduct().setTransportPercentValue(pricingValue);
                refreshPrices();
            }
        });
        dutyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ProductPercentValue pricingValue;
                if ((pricingValue = selectPricingValue(ProductPercentValue.Type.CUSTOMS_DUTY)) == null) {
                    return;
                }

                dutyPercentField.setPercent(pricingValue.getPercentValue());
                getProduct().setCustomsDutyPercentValue(pricingValue);
                refreshPrices();
            }
        });
        additionalProfitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ProductPercentValue pricingValue;
                if ((pricingValue = selectPricingValue(ProductPercentValue.Type.PROFIT)) == null) {
                    return;
                }

                additionalProfitPercentField.setPercent(pricingValue.getPercentValue());
                getProduct().setProfitPercentValue(pricingValue);
                refreshPrices();
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

        ListPriceBindingListener listPriceBindingListener = new ListPriceBindingListener();
        PropertyDetails propDetails = entityProps.getPropertyDetails("listPrice");
        Binding binding = listPriceTextField.bind(bindingGroup, product, propDetails);
        binding.addBindingListener(listPriceBindingListener);

        propDetails = entityProps.getPropertyDetails("currency");
        binding = currencyComboBox.bind(bindingGroup, getEnumResources(Currency.class), product, propDetails);
        binding.addBindingListener(new AbstractBindingListener() {

            @Override
            public void bindingBecameBound(Binding binding) {
                productPanel.onCurrencyChanged(getProduct().getCurrency());
            }
        });
        currencyComboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent event) {
                productPanel.onCurrencyChanged((DbResource) event.getItem());
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
        if (true) {
            return;
        }

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

    private class ListPriceBindingListener extends AbstractBindingListener {

        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event) {
            System.out.println("targetChanged(" + event + ")");
        }
    }
}
