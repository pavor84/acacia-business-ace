/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Miro
 */
public class ClassifierGroupsPanel
    extends AbstractTablePanel
{
    @EJB
    private ProductsListRemote formSession;

    private BindingGroup productsBindingGroup;
    private List<Product> products;

    public ClassifierGroupsPanel(DataObject parentDataObject)
    {
        super(parentDataObject);
    }

    @Override
    protected void initData() {
        super.initData();

        setVisible(Button.Select, false);

        productsBindingGroup = new BindingGroup();
        AcaciaTable productsTable = getDataTable();
        JTableBinding tableBinding = productsTable.bind(productsBindingGroup, getProducts(), getProductEntityProperties());
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getProductsCategories(), "category");
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getMeasureUnits(), "measureUnit");

        productsBindingGroup.bind();

        productsTable.setEditable(true);
    }

    protected boolean deleteRow(Object rowObject)
    {
        if(rowObject != null)
        {
            deleteProduct((Product)rowObject);
            return true;
        }

        return false;
    }

    protected Object modifyRow(Object rowObject)
    {
        if(rowObject != null)
        {
            ProductPanel productPanel = new ProductPanel((Product)rowObject);
            DialogResponse response = productPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return productPanel.getSelectedValue();
            }
        }

        return null;
    }

    protected Object newRow()
    {
        ProductPanel productPanel = new ProductPanel(getParentDataObject());
        DialogResponse response = productPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return productPanel.getSelectedValue();
        }

        return null;
    }

    public boolean canCreate()
    {
        return true;
    }

    public boolean canModify(Object rowObject)
    {
        return true;
    }

    public boolean canDelete(Object rowObject)
    {
        return true;
    }


    protected List<Product> getProducts()
    {
        if(products == null)
        {
            products = getFormSession().getProducts(getParentDataObject());
        }

        return products;
    }

    private List<ProductCategory> getProductsCategories()
    {
        return getFormSession().getProductsCategories(getParentDataObject());
    }

    private List<DbResource> getMeasureUnits()
    {
        return getFormSession().getMeasureUnits();
    }

    private List<DbResource> getMeasureUnits(MeasurementUnit.Category category)
    {
        return getFormSession().getMeasureUnits(category);
    }

    protected EntityProperties getProductEntityProperties()
    {
        return getFormSession().getProductEntityProperties();
    }

    protected ProductsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(ProductsListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deleteProduct(Product product)
    {
        return getFormSession().deleteProduct(product);
    }

}
