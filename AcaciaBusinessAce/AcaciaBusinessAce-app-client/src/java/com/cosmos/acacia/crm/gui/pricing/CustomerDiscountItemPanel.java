/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.pricing;

import com.cosmos.acacia.crm.bl.pricing.CustomerDiscountRemote;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItem;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItemByProduct;
import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBCheckBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBPercentField;
import com.cosmos.swingb.MigLayoutHelper;
import java.awt.BorderLayout;
import java.math.BigInteger;
import javax.ejb.EJB;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author Miro
 */
public class CustomerDiscountItemPanel extends BaseEntityPanel {

    @EJB
    private static CustomerDiscountRemote formSession;
    private CustomerDiscountItem item;
    private BindingGroup bindingGroup;
    private EntityFormButtonPanel buttonPanel;

    public CustomerDiscountItemPanel(CustomerDiscountItem item) {
        super(null);
        this.item = item;
        init();
    }

    @Override
    protected void init() {
        initComponents();
        super.init();
    }

    private JBPanel itemPanel;
    // By Product
    private JBLabel productLabel;
    private JBComboList productComboList;
    private JBLabel salesPriceLabel;
    private JBDecimalField salesPriceDecimalField;
    private JBLabel discountLabel;
    private JBPercentField productDiscountPercentField;
    private JBLabel defaultDiscountLabel;
    private JBLabel customerPriceLabel;
    private JBDecimalField customerPriceDecimalField;
    // By Category
    private JBLabel categoryLabel;
    private JBComboList categoryComboList;
    private JBLabel categoryDiscountLabel;
    private JBPercentField categoryDiscountPercentField;
    private JBCheckBox includeHeirsCheckBox;

    private void initComponents() {
        itemPanel = new JBPanel(new MigLayout("debug"));
        MigLayoutHelper helper = new MigLayoutHelper(itemPanel);
        helper.setLayoutFillX(true);

        if(isByProduct()) {
            productLabel = new JBLabel(getResourceString("productLabel.text"));
            productComboList = new AcaciaComboList();
            salesPriceLabel = new JBLabel(getResourceString("salesPriceLabel.text"));
            salesPriceDecimalField = new JBDecimalField();
            salesPriceDecimalField.setColumns(10);
            discountLabel = new JBLabel(getResourceString("discountLabel.text"));
            productDiscountPercentField = new JBPercentField();
            productDiscountPercentField.setColumns(7);
            defaultDiscountLabel = new JBLabel(getResourceString("defaultDiscountLabel.text"));
            customerPriceLabel = new JBLabel(getResourceString("customerPriceLabel.text"));
            customerPriceDecimalField = new JBDecimalField();
            customerPriceDecimalField.setColumns(10);

            itemPanel.add(productLabel);
            itemPanel.add(productComboList);
            itemPanel.add(salesPriceLabel);
            itemPanel.add(salesPriceDecimalField);
            itemPanel.add(discountLabel);
            itemPanel.add(productDiscountPercentField);
            itemPanel.add(defaultDiscountLabel);
            itemPanel.add(customerPriceLabel);
            itemPanel.add(customerPriceDecimalField);

            helper.setLayoutWrapAfter(5);
            helper.columnGrow(100, 1, 4);
            helper.columnSizeGroup("sg", 1, 4);
            helper.columnFill(1, 4);
            helper.columnGap("15", 2);
            helper.getComponentConstraints(productLabel).cell(0, 0);
            helper.getComponentConstraints(productComboList).cell(1, 0).spanX(2);
            helper.getComponentConstraints(salesPriceLabel).cell(3, 0);
            helper.getComponentConstraints(salesPriceDecimalField).cell(4, 0);
            helper.getComponentConstraints(discountLabel).cell(0, 1);
            helper.getComponentConstraints(productDiscountPercentField).cell(1, 1);
            helper.getComponentConstraints(defaultDiscountLabel).cell(2, 1);
            helper.getComponentConstraints(customerPriceLabel).cell(3, 1);
            helper.getComponentConstraints(customerPriceDecimalField).cell(4, 1);
        } else {
            categoryLabel = new JBLabel(getResourceString("categoryLabel.text"));
            categoryComboList = new AcaciaComboList();
            categoryDiscountLabel = new JBLabel(getResourceString("categoryDiscountLabel.text"));
            categoryDiscountPercentField = new JBPercentField();
            categoryDiscountPercentField.setColumns(7);
            includeHeirsCheckBox = new JBCheckBox();
            includeHeirsCheckBox.setText(getResourceString("includeHeirsCheckBox.text"));

            itemPanel.add(categoryLabel);
            itemPanel.add(categoryComboList);
            itemPanel.add(categoryDiscountLabel);
            itemPanel.add(categoryDiscountPercentField);
            itemPanel.add(includeHeirsCheckBox);

            helper.setLayoutWrapAfter(3);
            helper.columnGap("15", 1);
            helper.getComponentConstraints(categoryLabel).cell(0, 0);
            helper.getComponentConstraints(categoryComboList).cell(1, 0).spanX(2).grow();
            helper.getComponentConstraints(categoryDiscountLabel).cell(0, 1);
            helper.getComponentConstraints(categoryDiscountPercentField).cell(1, 1).grow();
            helper.getComponentConstraints(includeHeirsCheckBox).cell(2, 1);
        }
        itemPanel.invalidate();

        setLayout(new BorderLayout());
        add(itemPanel, BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private String getResourceString(String key) {
        return getResourceMap().getString(key);
    }

    private boolean isByProduct() {
        return (item instanceof CustomerDiscountItemByProduct);
    }

    @Override
    public void performSave(boolean closeAfter) {
        try {
            item = getFormSession().saveCustomerDiscountItem(item);
            if(closeAfter) {
                setDialogResponse(DialogResponse.SAVE);
                setSelectedValue(item);
                close();
            }
        } catch (Exception ex) {
            handleException("CustomerDiscountItem: " + item, ex);
        }
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
        return item;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        if(buttonPanel == null) {
            buttonPanel = new EntityFormButtonPanel();
        }

        return buttonPanel;
    }

    @Override
    protected void initData() {
    }

    private CustomerDiscountRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getBean(CustomerDiscountRemote.class);
        }

        return formSession;
    }
}
