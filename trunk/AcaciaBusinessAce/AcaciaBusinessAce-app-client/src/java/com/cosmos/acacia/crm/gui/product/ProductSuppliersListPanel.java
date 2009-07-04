/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.product;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.ProductSupplier;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.gui.ProductSupplierPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.util.List;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Miro
 */
public class ProductSuppliersListPanel extends AbstractTablePanel<ProductSupplier> {

    private BindingGroup bindingGroup;
    private EntityProperties productSuppliersEntityProperties;
    private ProductsListRemote formSession;
    private SimpleProduct product;

    public ProductSuppliersListPanel(ProductPanel productPanel) {
        super(productPanel);
    }

    @Override
    protected void initData() {
        ProductPanel productPanel = (ProductPanel) getParentPanel();
        this.bindingGroup = productPanel.getBindingGroup();
        this.productSuppliersEntityProperties = productPanel.getProductSuppliersEntityProperties();
        this.formSession = productPanel.getFormSession();
        this.product = productPanel.getProduct();

        super.initData();
        setVisible(AbstractTablePanel.Button.Close, false);

        bindingGroup = new BindingGroup();
        AcaciaTable table = getDataTable();
        JTableBinding tableBinding = table.bind(
                bindingGroup,
                getProductSuppliers(),
                productSuppliersEntityProperties,
                UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    @Override
    protected boolean deleteRow(ProductSupplier rowObject) {
        return formSession.deleteProductSupplier((ProductSupplier) rowObject);
    }

    @Override
    protected ProductSupplier modifyRow(ProductSupplier rowObject) {
        return editRow((ProductSupplier) rowObject);
    }

    @Override
    protected ProductSupplier newRow() {
        return editRow(formSession.newProductSupplier(product));
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
        return formSession.getProductSuppliers(product);
    }
}
