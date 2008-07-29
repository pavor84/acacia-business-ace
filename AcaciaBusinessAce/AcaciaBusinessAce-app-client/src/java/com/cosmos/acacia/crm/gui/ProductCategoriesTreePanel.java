/*
 * ProductCategoriesPanel.java
 *
 * Created on Четвъртък, 2008, Февруари 21, 17:01
 */

package com.cosmos.acacia.crm.gui;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.gui.AbstractTreeEnabledTablePanel;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.acacia.gui.BaseTreePanel;
import java.math.BigInteger;

/**
 *
 * @author  Petar Milev
 */
public class ProductCategoriesTreePanel extends BaseTreePanel<ProductCategory> {

    private ProductCategoryListPanel categoryListPanel;

    @EJB
    private ProductsListRemote formSession;


    /** Creates new form ProductCategoriesPanel */
    public ProductCategoriesTreePanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }


    /**
     * Tries to update the model by calling the business logic.
     * On fail - shows error message to the user, and returns null.
     * On success returns the updated category (child)
     *
     * @param newParent
     * @param newChildren
     * @return
     */
    @Override
    protected ProductCategory updateParent(ProductCategory newParent, ProductCategory newChildren) {
        try{
            ProductCategory updatedCategory =
                getFormSession().updateParents(newParent, newChildren);

            return updatedCategory;
        } catch (Exception e){
            handleException(e);
            return null;
        }
    }

    @Override
    protected void initData()
    {
        categoryListPanel = new ProductCategoryListPanel(getParentDataObjectId(), true);

        toStringConverter = new AcaciaToStringConverter("${categoryName}");
        getTree().setToStringConverter(toStringConverter);

        //load all categories and init the tree
        List<ProductCategory> categories = getFormSession().getProductsCategories(null);

        refreshTreeModel(categories);
    }


    @Override
    protected void onTableRefreshed() {
        //load all categories and refresh the tree
        List<ProductCategory> categories = getFormSession().getProductsCategories(null);

        refreshTreeModel(categories);
    }

    protected ProductsListRemote getFormSession() {
        if (formSession == null) {
            try {
                formSession = InitialContext.doLookup(ProductsListRemote.class.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    @Override
    public AbstractTreeEnabledTablePanel<ProductCategory> getListPanel() {
        return categoryListPanel;
    }

    @Override
    public ProductCategory getParent(ProductCategory child) {
        return child.getParentCategory();
    }

    @Override
    public void setParent(ProductCategory entity, ProductCategory parent) {
        entity.setParentCategory(parent);
    }

    @Override
    protected String getRootNodeDisplay() {
        return getResourceMap().getString("CategoryTree.rootNodeDisplay");
    }

}
