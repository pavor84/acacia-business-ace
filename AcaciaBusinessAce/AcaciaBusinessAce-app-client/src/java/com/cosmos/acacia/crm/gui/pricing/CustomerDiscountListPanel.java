/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CustomerDiscountListPanel.java
 *
 * Created on 2009-3-3, 18:25:55
 */

package com.cosmos.acacia.crm.gui.pricing;

import com.cosmos.acacia.crm.bl.pricing.CustomerDiscountRemote;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.customer.CustomerDiscount;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItem;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPercentField;
import com.cosmos.swingb.JBTextField;
import com.cosmos.swingb.MigLayoutHelper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.UUID;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.border.TitledBorder;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Miro
 */
public class CustomerDiscountListPanel extends BaseEntityPanel {

    /** Creates new form CustomerDiscountListPanel */
    public CustomerDiscountListPanel(BusinessPartner customer) {
        super(customer.getId());
        this.customer = customer;
        init();
    }

    public CustomerDiscountListPanel() {
        super((UUID)null);
        init();
    }

    @Override
    protected void init()
    {
        initComponents();
        super.init();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(CustomerDiscountListPanel.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(347, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(271, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    @EJB
    private static CustomerDiscountRemote formSession;
    private EntityFormButtonPanel entityFormButtonPanel;
    private BusinessPartner customer;
    private CustomerDiscount customerDiscount;
    private BindingGroup bindingGroup;
    private CustomerDiscountPanel customerDiscountPanel;
    private CustomerDiscountItemListPanel customerDiscountItemListPanel;

    @Override
    protected void initData() {
        setPreferredSize(new Dimension(640, 480));
        setLayout(new BorderLayout());
        add(getCustomerDiscountPanel(), BorderLayout.NORTH);
        add(getCustomerDiscountItemListPanel(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.SOUTH);
    }

    public CustomerDiscount getCustomerDiscount() {
        if(customerDiscount == null) {
            if((customerDiscount = getFormSession().getCustomerDiscount(customer)) == null) {
                customerDiscount = getFormSession().newCustomerDiscount(customer);
            }
        }

        return customerDiscount;
    }

    @Override
    public void performSave(boolean closeAfter) {
        try {
            customerDiscount = getFormSession().saveCustomerDiscount(getCustomerDiscount());
            if(closeAfter) {
                setDialogResponse(DialogResponse.SAVE);
                setSelectedValue(customerDiscount);
                close();
            }
        } catch (Exception ex) {
            handleException("customerDiscount: " + customerDiscount, ex);
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
        return getCustomerDiscount();
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        if(entityFormButtonPanel == null) {
            entityFormButtonPanel = new EntityFormButtonPanel();
        }

        return entityFormButtonPanel;
    }

    private CustomerDiscountRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getBean(CustomerDiscountRemote.class);
        }

        return formSession;
    }

    private CustomerDiscountPanel getCustomerDiscountPanel() {
        if(customerDiscountPanel == null) {
            customerDiscountPanel = new CustomerDiscountPanel();
        }

        return customerDiscountPanel;
    }

    private CustomerDiscountItemListPanel getCustomerDiscountItemListPanel() {
        if(customerDiscountItemListPanel == null) {
            customerDiscountItemListPanel = new CustomerDiscountItemListPanel();
        }

        return customerDiscountItemListPanel;
    }

    private class CustomerDiscountPanel extends AcaciaPanel {

        private JBLabel customerLabel;
        private JBTextField customerTextField;
        private JBLabel discountLabel;
        private JBPercentField discountPercentField;
        private BindingGroup bindingGroup;

        public CustomerDiscountPanel() {
            initComponents();
            initData();
        }

        private void initComponents() {
            customerLabel = new JBLabel(getResourceString("customerLabel.text"));
            customerTextField = new JBTextField();
            customerTextField.setEditable(false);
            discountLabel = new JBLabel(getResourceString("discountLabel.text"));
            discountPercentField = new JBPercentField();

            MigLayoutHelper helper = new MigLayoutHelper(this);

            add(customerLabel);
            add(customerTextField);
            add(discountLabel);
            add(discountPercentField);

            helper.setLayoutFillX(true);
            helper.setLayoutWrapAfter(1);
            helper.columnGrow(100, 1);
            helper.columnSizeGroup("sg", 1);
            helper.columnFill(1);
            helper.getComponentConstraints(customerLabel).cell(0, 0);
            helper.getComponentConstraints(customerTextField).cell(1, 0);
            helper.getComponentConstraints(discountLabel).cell(0, 1);
            helper.getComponentConstraints(discountPercentField).cell(1, 1);
            invalidate();
        }

        @Override
        protected void initData() {
            customerTextField.setText(customer.getDisplayName());
            BindingGroup bg = getBindingGroup();
            CustomerDiscount discount = getCustomerDiscount();
            EntityProperties entityProperties = getFormSession().getCustomerDiscountEntityProperties();
            PropertyDetails propDetails = entityProperties.getPropertyDetails("discountPercent");
            discountPercentField.bind(bg, discount, propDetails);
            bg.bind();
        }

        public BindingGroup getBindingGroup() {
            if(bindingGroup == null) {
                bindingGroup = new BindingGroup();
            }

            return bindingGroup;
        }
    }

    private class CustomerDiscountItemListPanel extends AbstractTablePanel<CustomerDiscountItem> {

        private Boolean includeProduct;

        public CustomerDiscountItemListPanel() {
        }

        @Override
        protected void initData() {
            super.initData();
            setSpecialButtonBehavior(true);

            setVisible(Button.Special, true);
            setVisible(Button.Classify, false);
            setVisible(Button.Refresh, false);
            setVisible(Button.Close, false);
            TitledBorder border = new TitledBorder(getResourceString("itemsHolderPanel.border.title"));
            setBorder(border);


            JBButton button = getButton(Button.New);
            button.setText(getResourceString("button.includeCategory"));
            button.setToolTipText(getResourceString("button.includeCategory.tooltip"));

            button = getButton(Button.Special);
            button.setText(getResourceString("button.includeProduct"));
            button.setToolTipText(getResourceString("button.includeProduct.tooltip"));

            if (!getRightsManager().isAllowed(SpecialPermission.ProductPricing)) {
                setReadonly();
            }
            CustomerDiscountListPanel.this.addNestedFormListener(this);

            EntityProperties entityProps = getFormSession().getCustomerDiscountItemEntityProperties();
            BindingGroup bg = getBindingGroup();
            AcaciaTable table = getDataTable();
            List<CustomerDiscountItem> items = getFormSession().getCustomerDiscountItems(getCustomerDiscount());
            JTableBinding tableBinding = table.bind(
                    bg,
                    items,
                    entityProps,
                    UpdateStrategy.READ);
            tableBinding.setEditable(false);

            bg.bind();
        }

        @Override
        public boolean canNestedOperationProceed() {
            if(getCustomerDiscount().getCustomerDiscountId() == null) {
                performSave(false);
            }
            return true;
        }

        @Override
        public void specialAction() {
            if(canNestedOperationProceed()) {
                includeProduct = true;
                super.newAction();
            }
        }

        @Override
        public void newAction() {
            if(canNestedOperationProceed()) {
                includeProduct = false;
                super.newAction();
            }
        }

        @Override
        protected boolean deleteRow(CustomerDiscountItem rowObject) {
            if(rowObject == null)
                return false;

            return getFormSession().deleteCustomerDiscountItem(rowObject);
        }

        @Override
        protected CustomerDiscountItem modifyRow(CustomerDiscountItem rowObject) {
            if(rowObject == null)
                return null;

            CustomerDiscountItemPanel itemPanel = new CustomerDiscountItemPanel(rowObject);
            if(DialogResponse.SAVE.equals(itemPanel.showDialog(this))) {
                return (CustomerDiscountItem) itemPanel.getSelectedValue();
            }

            return null;
        }

        @Override
        protected CustomerDiscountItem newRow() {
            if(includeProduct == null)
                return null;

            CustomerDiscountItem item;
            if(includeProduct)
                item = getFormSession().newCustomerDiscountItemByProduct(getCustomerDiscount());
            else
                item = getFormSession().newCustomerDiscountItemByCategory(getCustomerDiscount());
            includeProduct = null;
            CustomerDiscountItemPanel itemPanel = new CustomerDiscountItemPanel(item);
            if(DialogResponse.SAVE.equals(itemPanel.showDialog(this))) {
                return (CustomerDiscountItem) itemPanel.getSelectedValue();
            }

            return null;
        }
    }
}
