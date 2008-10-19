/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.assembling.ProductItemService;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.ComplexProductItem;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.SimpleProduct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

/**
 *
 * @author Miro
 */
public class ProductItemTreeTableNode
    extends DefaultMutableTreeTableNode
{
    private static final Logger logger = Logger.getLogger(ProductItemTreeTableNode.class);

    private ProductItemService service;

    public ProductItemTreeTableNode(
        ComplexProduct complexProduct,
        ProductItemService service)
    {
        this((DataObjectBean)complexProduct, service);
    }

    public ProductItemTreeTableNode(
        Invoice invoice,
        ProductItemService service)
    {
        //this((DataObjectBean)invoice, service);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected ProductItemTreeTableNode(
        DataObjectBean dataObject,
        ProductItemService service)
    {
        super(dataObject);
        this.service = service;
        children.addAll(createChildren());
    }

    protected DataObjectBean getDataObjectBean()
    {
        return (DataObjectBean)getUserObject();
    }

    protected List getChildItems()
    {
        DataObjectBean dataObjectBean = getDataObjectBean();
        if(dataObjectBean instanceof ComplexProduct)
        {
            return getComplexProductItems((ComplexProduct)dataObjectBean);
        }
        else if(dataObjectBean instanceof ComplexProductItem)
        {
            ComplexProductItem productItem = (ComplexProductItem)dataObjectBean;
            Product product = productItem.getProduct();
            if(product instanceof ComplexProduct)
                return getComplexProductItems((ComplexProduct)product);
        }
        else if(dataObjectBean instanceof InvoiceItem)
        {
            InvoiceItem invoiceItem = (InvoiceItem)dataObjectBean;
            Product product = invoiceItem.getProduct();
            if(product instanceof ComplexProduct)
                return getComplexProductItems((ComplexProduct)product);
        }
        else if(dataObjectBean instanceof Invoice)
        {
            return service.getInvoiceItems((Invoice)dataObjectBean);
        }

        return Collections.emptyList();
    }

    protected List<ComplexProductItem> getComplexProductItems(ComplexProduct product)
    {
        if(product.getProductId() != null)
        {
            return service.getComplexProductItems(product);
        }
        else
        {
            List<ComplexProductItem> productItems;
            if((productItems = product.getComplexProductItems()) != null)
            {
                return productItems;
            }
        }

        return Collections.emptyList();
    }

    protected List<MutableTreeTableNode> createChildren()
    {
        if(getUserObject() instanceof SimpleProduct)
            return Collections.emptyList();

        int size;
        List items;
        if((items = getChildItems()) != null &&
            (size = items.size()) > 0)
        {
            List<MutableTreeTableNode> childProducts =
                new ArrayList<MutableTreeTableNode>(size);
            for(int i = 0; i < size; i++)
            {
                DataObjectBean item = (DataObjectBean)items.get(i);
                ProductItemTreeTableNode node =
                    new ProductItemTreeTableNode((DataObjectBean)item, service);
                node.parent = this;
                childProducts.add(node);
            }

            return childProducts;
        }

        return Collections.emptyList();
    }

    @Override
    public Object getValueAt(int column)
    {
        DataObjectBean dataObjectBean = getDataObjectBean();

        if(dataObjectBean instanceof ComplexProductItem)
            return getProductItemValueAt((ComplexProductItem)dataObjectBean, column);

        return super.getValueAt(column);
    }

    @Override
    public int getColumnCount()
    {
        return 9;
    }

    protected Object getProductValueAt(Product product, int column)
    {
        switch(column)
        {
            case 0:
                return product.getProductCode();

            case 1:
                return product.getProductName();

            case 2:
                return product.getMeasureUnit().getEnumName();

            case 3:
                return product.getSalePrice();

            default:
                return null;
        }
    }

    protected Object getProductItemValueAt(ComplexProductItem productItem, int column)
    {
        switch(column)
        {
            case 0:
            case 1:
            case 2:
            case 3:
                return getProductValueAt(productItem.getProduct(), column);

            case 4:
                return productItem.getQuantity();

            case 5:
                return productItem.getUnitPrice();

            case 6:
                return productItem.getItemPrice();

            case 7:
                return productItem.getAppliedAlgorithm().getEnumName();

            case 8:
                return productItem.getAppliedValue();

            default:
                return null;
        }
    }

    public void refresh()
    {
        refresh(false);
    }

    public void refresh(boolean deep)
    {
        children.clear();
        children.addAll(createChildren());
    }
}
