/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.gui.product.ProductPanel;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.SpecialPermission;
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
        extends AbstractTablePanel<SimpleProduct> {

    @EJB
    private ProductsListRemote formSession;
    private BindingGroup productsBindingGroup;
    private List<SimpleProduct> products;
    private EntityProperties entityProps;

    public ProductsListPanel() {
        this(getAcaciaSession().getOrganization().getId());
    }

    public ProductsListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initData() {
        super.initData();

        entityProps = getFormSession().getProductListingEntityProperties();

        List<PropertyDetails> propertyDetails =
                new ArrayList<PropertyDetails>(entityProps.getValues());

        //set custom display for 'productCategory'
        setCustomDisplay(propertyDetails, "category",
                "${category.categoryName}");

        //set custom display for 'patternMaskFormat'
        setCustomDisplay(propertyDetails, "patternMaskFormat",
                "${patternMaskFormat.patternName} (${patternMaskFormat.format})");

        //set custom display for 'producer'
        setCustomDisplay(propertyDetails, "producer", "${producer.displayName}");

        //add column
        addColumn(55, "codeFormatted", getString("ProductList.codeFormatted"), "${codeFormatted}", entityProps);

        refreshDataTable(entityProps);
    }

    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps) {
        if (productsBindingGroup != null) {
            productsBindingGroup.unbind();
        }

        productsBindingGroup = new BindingGroup();
        AcaciaTable productsTable = getDataTable();

        JTableBinding tableBinding = productsTable.bind(productsBindingGroup, getProducts(), entityProps, UpdateStrategy.READ);

        tableBinding.setEditable(false);
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getMeasureUnits(), entityProps.getPropertyDetails("measureUnit"));
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getMeasureUnits(MeasurementUnit.Category.Volume), entityProps.getPropertyDetails("dimensionUnit"));
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getMeasureUnits(MeasurementUnit.Category.MassWeight), entityProps.getPropertyDetails("weightUnit"));
        productsTable.bindComboBoxCellEditor(productsBindingGroup, getFormSession().getProductColors(), entityProps.getPropertyDetails("productColor"));

        productsBindingGroup.bind();

    }

    @Override
    protected boolean deleteRow(SimpleProduct rowObject) {
        if (rowObject != null) {
            deleteProduct((SimpleProduct) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected SimpleProduct modifyRow(SimpleProduct rowObject) {
        if (rowObject != null) {
            //ProductPanel productPanel = new ProductPanel((SimpleProduct)rowObject);
            ProductPanel productPanel = new ProductPanel(rowObject);
            DialogResponse response = productPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (SimpleProduct) productPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected void viewRow(SimpleProduct rowObject) {
        if (rowObject != null) {
            //ProductPanel productPanel = new ProductPanel((SimpleProduct)rowObject);
            ProductPanel productPanel = new ProductPanel(rowObject);
            DialogResponse response = productPanel.showDialog(this);
        }
    }

    @Override
    protected SimpleProduct newRow() {
        ProductPanel productPanel = new ProductPanel(getFormSession().newProduct());
        DialogResponse response = productPanel.showDialog(this);
        if (DialogResponse.SAVE.equals(response)) {
            return (SimpleProduct) productPanel.getSelectedValue();
        }

        return null;
    }

    @Override
    public boolean canCreate() {
        return getRightsManager().isAllowed(SpecialPermission.ProductPermissions);
    }

    @Override
    public boolean canModify(SimpleProduct rowObject) {
        return getRightsManager().isAllowed(SpecialPermission.ProductPermissions);
    }

    @Override
    public boolean canDelete(SimpleProduct rowObject) {
        return getRightsManager().isAllowed(SpecialPermission.ProductPermissions);
    }

    @Override
    public boolean canView(SimpleProduct rowObject) {
        //return getRightsManager().isAllowed(SpecialPermission.ProductPermissions);
        return true;
    }

    protected List<SimpleProduct> getProducts() {
        if (products == null) {
            products = getFormSession().getProducts(getParentDataObjectId());
        }

        return products;
    }

    private List<DbResource> getMeasureUnits() {
        return getFormSession().getMeasureUnits();
    }

    private List<DbResource> getMeasureUnits(MeasurementUnit.Category category) {
        return getFormSession().getMeasureUnits(category);
    }

    protected EntityProperties getProductEntityProperties() {
        return getFormSession().getProductEntityProperties();
    }

    protected ProductsListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(ProductsListRemote.class);
        }

        return formSession;
    }

    protected int deleteProduct(SimpleProduct product) {
        return getFormSession().deleteProduct(product);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        //reset the products - they will be reinitialized
        products = null;
        refreshDataTable(entityProps);

        return t;
    }
}
