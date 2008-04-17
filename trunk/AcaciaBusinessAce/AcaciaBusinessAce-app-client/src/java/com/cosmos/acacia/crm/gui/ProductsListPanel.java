/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author Miro
 */
public class ProductsListPanel
    extends AbstractTablePanel
{
    @EJB
    private ProductsListRemote formSession;

    private BindingGroup productsBindingGroup;
    private List<Product> products;

    public ProductsListPanel(DataObject parentDataObject)
    {
        super(parentDataObject);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initData() {
        super.initData();

        productsBindingGroup = new BindingGroup();
        AcaciaTable productsTable = getDataTable();
        EntityProperties entityProps = getFormSession().getProductEntityProperties();
        List<PropertyDetails> propertyDetails = 
            new ArrayList<PropertyDetails>(entityProps.getValues());
        
        //set custom display for 'patternMaskFormat'
        setCustomDisplay(propertyDetails, "patternMaskFormat", 
            "${patternMaskFormat.patternName} (${patternMaskFormat.format})");
        
        //set custom display for 'producer'
        setCustomDisplay(propertyDetails, "producer", "${producer.displayName}");
        
        //add column
        addColumn(55, getString("ProductList.codeFormatted"), "${codeFormatted}", entityProps);
        
        JTableBinding tableBinding = productsTable.bind(productsBindingGroup, getProducts(), entityProps, UpdateStrategy.READ);
        
        tableBinding.setEditable(false);
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getProductsCategories(), entityProps.getPropertyDetails("category"));
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getMeasureUnits(), entityProps.getPropertyDetails("measureUnit"));
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getMeasureUnits(MeasurementUnit.Category.Volume), entityProps.getPropertyDetails("dimensionUnit"));
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getMeasureUnits(MeasurementUnit.Category.MassWeight), entityProps.getPropertyDetails("weightUnit"));
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getFormSession().getProductColors(), entityProps.getPropertyDetails("productColor"));

        productsBindingGroup.bind();

        productsTable.setEditable(true);
    }
    
    private void addColumn(int orderPosition, String columnName,
                           String customELDisplay, EntityProperties entityProperties) {
        PropertyDetails pd = new PropertyDetails(null, columnName, null);
        pd.setCustomDisplay(customELDisplay);
        pd.setOrderPosition(orderPosition);
        entityProperties.addPropertyDetails(pd);
    }

    private String getString(String key) {
        return getResourceMap().getString(key);
    }

    /**
     * Custom display is EL expression string
     * @param propertyDetails
     * @param propertyName
     * @param customDisplay - provide valid EL expression string. 
     * Otherwise no fail-fast in this method. Will fail when compiled. 
     */
    private void setCustomDisplay(List<PropertyDetails> propertyDetails, String propertyName, String customDisplay) {
        for (PropertyDetails pd : propertyDetails) {
            if ( pd.getPropertyName().equals(propertyName)){
                pd.setCustomDisplay(customDisplay);
                break;
            }
        }
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