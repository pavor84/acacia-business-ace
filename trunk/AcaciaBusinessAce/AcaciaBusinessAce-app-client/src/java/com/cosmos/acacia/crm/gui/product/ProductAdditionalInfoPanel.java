/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.product;

import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.gui.AcaciaComboBox;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBIntegerField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTextPane;
import com.cosmos.swingb.MigLayoutHelper;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Miro
 */
public class ProductAdditionalInfoPanel extends JBPanel {

    private ProductPanel productPanel;
    private BindingGroup bindingGroup;
    private EntityProperties entityProps;
    private SimpleProduct product;
    //
    private JBLabel cubatureLabel;
    private JBDecimalField cubatureDecimalField;
    private JBPanel deliveryInfoPanel;
    private JBLabel deliveryTimeLabel;
    private JBIntegerField deliveryTimeIntegerField;
    private JBPanel descriptionPanel;
    private JScrollPane descriptionScrollPane;
    private JBTextPane descriptionTextPane;
    private AcaciaComboBox dimensionUnitComboBox;
    private JBLabel dimensionUnitLabel;
    private JBPanel dimensionsPanel;
    private JBLabel heightLabel;
    private JBDecimalField heightDecimalField;
    private JBLabel lengthLabel;
    private JBDecimalField lengthDecimalField;
    private JBLabel weightLabel;
    private JBDecimalField weightDecimalField;
    private AcaciaComboBox weightUnitComboBox;
    private JBLabel weightUnitLabel;
    private JBLabel widthLabel;
    private JBDecimalField widthDecimalField;

    public ProductAdditionalInfoPanel(ProductPanel productPanel) {
        this.productPanel = productPanel;
        this.bindingGroup = productPanel.getBindingGroup();
        this.entityProps = productPanel.getProductEntityProperties();
        this.product = productPanel.getProduct();
        initComponents();
        initData();
    }

    protected String getResourceString(String key) {
        return productPanel.getResourceString(key);
    }

    private void initComponents() {
        dimensionUnitComboBox = new AcaciaComboBox();
        dimensionUnitLabel = new JBLabel();
        widthLabel = new JBLabel();
        widthDecimalField = new JBDecimalField();
        lengthLabel = new JBLabel();
        lengthDecimalField = new JBDecimalField();
        heightLabel = new JBLabel();
        heightDecimalField = new JBDecimalField();
        cubatureLabel = new JBLabel();
        cubatureDecimalField = new JBDecimalField();
        deliveryTimeIntegerField = new JBIntegerField();
        deliveryTimeLabel = new JBLabel();
        weightDecimalField = new JBDecimalField();
        weightLabel = new JBLabel();
        weightUnitLabel = new JBLabel();
        weightUnitComboBox = new AcaciaComboBox();
        descriptionPanel = new JBPanel();
        descriptionScrollPane = new JScrollPane();
        descriptionTextPane = new JBTextPane();
        dimensionsPanel = new JBPanel();
        deliveryInfoPanel = new JBPanel();

        dimensionUnitComboBox.setName("dimensionUnitComboBox"); // NOI18N

        dimensionUnitLabel.setText(getResourceString("dimensionUnitLabel.text")); // NOI18N
        dimensionUnitLabel.setName("dimensionUnitLabel"); // NOI18N

        widthLabel.setText(getResourceString("widthLabel.text")); // NOI18N
        widthLabel.setName("widthLabel"); // NOI18N

        widthDecimalField.setName("widthTextField"); // NOI18N

        lengthLabel.setText(getResourceString("lengthLabel.text")); // NOI18N
        lengthLabel.setName("lengthLabel"); // NOI18N

        lengthDecimalField.setName("lengthTextField"); // NOI18N

        heightLabel.setText(getResourceString("heightLabel.text")); // NOI18N
        heightLabel.setName("heightLabel"); // NOI18N

        heightDecimalField.setName("heightTextField"); // NOI18N

        cubatureLabel.setText(getResourceString("cubatureLabel.text")); // NOI18N
        cubatureLabel.setName("cubatureLabel"); // NOI18N

        cubatureDecimalField.setName("cubatureTextField"); // NOI18N

        deliveryTimeIntegerField.setName("deliveryTimeTextField"); // NOI18N

        deliveryTimeLabel.setText(getResourceString("deliveryTimeLabel.text")); // NOI18N
        deliveryTimeLabel.setName("deliveryTimeLabel"); // NOI18N

        weightDecimalField.setName("weightTextField"); // NOI18N

        weightLabel.setText(getResourceString("weightLabel.text")); // NOI18N
        weightLabel.setName("weightLabel"); // NOI18N

        weightUnitLabel.setText(getResourceString("weightUnitLabel.text")); // NOI18N
        weightUnitLabel.setName("weightUnitLabel"); // NOI18N

        weightUnitComboBox.setName("weightUnitComboBox"); // NOI18N

        descriptionPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("descriptionPanel.border.title"))); // NOI18N
        descriptionPanel.setName("descriptionPanel"); // NOI18N
        descriptionPanel.setLayout(new java.awt.BorderLayout());

        descriptionScrollPane.setName("descriptionScrollPane"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        descriptionScrollPane.setViewportView(descriptionTextPane);

        descriptionPanel.add(descriptionScrollPane, java.awt.BorderLayout.CENTER);

        dimensionsPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("dimensionsPanel.border.title"))); // NOI18N
        dimensionsPanel.setName("dimensionsPanel"); // NOI18N
        dimensionsPanel.setLayout(new MigLayout());

        deliveryInfoPanel.setBorder(BorderFactory.createTitledBorder(getResourceString("deliveryInfoPanel.border.title"))); // NOI18N
        deliveryInfoPanel.setName("deliveryInfoPanel"); // NOI18N
        deliveryInfoPanel.setLayout(new MigLayout());

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

        helper = new MigLayoutHelper(this);
        helper.setLayoutFillX(true);
        helper.setLayoutWrapAfter(1);
        helper.columnGrow(100, 0);
        helper.columnFill(0);
        add(deliveryInfoPanel);
        helper.getComponentConstraints(deliveryInfoPanel).cell(0, 0);
        add(dimensionsPanel);
        helper.getComponentConstraints(dimensionsPanel).cell(0, 1);
        add(descriptionPanel);
        helper.getComponentConstraints(descriptionPanel).cell(0, 2).height("100%").width("100%");
        invalidate();
    }

    private void initData() {
        AcaciaToStringConverter resourceToStringConverter = new AcaciaToStringConverter();
        AutoCompleteDecorator.decorate(dimensionUnitComboBox, resourceToStringConverter);
        AutoCompleteDecorator.decorate(weightUnitComboBox, resourceToStringConverter);

        EntityProperty propDetails = entityProps.getEntityProperty("weightUnit");
        weightUnitComboBox.bind(
                bindingGroup,
                productPanel.getMeasureUnits(MeasurementUnit.Category.MassWeight),
                product,
                propDetails);

        propDetails = entityProps.getEntityProperty("weight");
        weightDecimalField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getEntityProperty("deliveryTime");
        deliveryTimeIntegerField.bind(bindingGroup, product, propDetails);

        propDetails = entityProps.getEntityProperty("dimensionUnit");
        dimensionUnitComboBox.bind(
                bindingGroup,
                productPanel.getMeasureUnits(MeasurementUnit.Category.Volume),
                product,
                propDetails);

        CubatureBindingListener cubatureBindingListener = new CubatureBindingListener();
        propDetails = entityProps.getEntityProperty("dimensionWidth");
        Binding binding = widthDecimalField.bind(bindingGroup, product, propDetails);
        binding.addBindingListener(cubatureBindingListener);

        propDetails = entityProps.getEntityProperty("dimensionLength");
        binding = lengthDecimalField.bind(bindingGroup, product, propDetails);
        binding.addBindingListener(cubatureBindingListener);

        propDetails = entityProps.getEntityProperty("dimensionHeight");
        binding = heightDecimalField.bind(bindingGroup, product, propDetails);
        binding.addBindingListener(cubatureBindingListener);

        cubatureDecimalField.setEditable(false);

        propDetails = entityProps.getEntityProperty("description");
        binding = descriptionTextPane.bind(bindingGroup, product, propDetails);
    }

    protected void refreshCubature() {
        BigDecimal cubature;
        if ((cubature = product.getDimensionCubature()) == null) {
            cubatureDecimalField.setValue(null);
        } else {
            cubatureDecimalField.setValue(cubature);
        }
    }

    private class CubatureBindingListener extends AbstractBindingListener {

        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event) {
            refreshCubature();
        }
    }
}
