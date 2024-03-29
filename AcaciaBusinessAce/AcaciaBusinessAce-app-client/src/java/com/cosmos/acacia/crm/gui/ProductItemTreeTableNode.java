/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.assembling.ProductItemService;
import com.cosmos.acacia.crm.data.product.ComplexProduct;
import com.cosmos.acacia.crm.data.product.ComplexProductItem;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.sales.SalesInvoice;
import com.cosmos.acacia.crm.data.sales.SalesInvoiceItem;
import com.cosmos.acacia.crm.data.product.Product;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
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
        DataObjectBean dataObject,
        ProductItemService service)
    {
        super(dataObject);
        this.service = service;
        synchronizeChildren();
    }

    protected DataObjectBean getDataObjectBean()
    {
        return (DataObjectBean)getUserObject();
    }

    protected List getChildItems()
    {
        ComplexProduct complexProduct = getComplexProduct();
        if(complexProduct != null)
            return getComplexProductItems(complexProduct);

        DataObjectBean dataObjectBean = getDataObjectBean();
        if(dataObjectBean instanceof SalesInvoice)
        {
            return service.getInvoiceItems((SalesInvoice)dataObjectBean);
        }

        return Collections.emptyList();
    }

    protected ComplexProduct getComplexProduct()
    {
        DataObjectBean dataObjectBean = getDataObjectBean();
        if(dataObjectBean instanceof ComplexProduct)
        {
            return (ComplexProduct)dataObjectBean;
        }
        else if(dataObjectBean instanceof ComplexProductItem)
        {
            ComplexProductItem productItem = (ComplexProductItem)dataObjectBean;
            Product product = productItem.getProduct();
            if(product instanceof ComplexProduct)
                return (ComplexProduct)product;
        }
        else if(dataObjectBean instanceof SalesInvoiceItem)
        {
            SalesInvoiceItem invoiceItem = (SalesInvoiceItem)dataObjectBean;
            Product product = invoiceItem.getProduct();
            if(product instanceof ComplexProduct)
                return (ComplexProduct)product;
        }

        return null;
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
                return product.getSalesPrice();

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

    @Override
    public int getChildCount()
    {
        int childCount = 0;
        ComplexProduct complexProduct = getComplexProduct();
        if(complexProduct != null)
        {
//            if(complexProduct.getProductId() != null)
//                childCount = service.getComplexProductItemsCount(complexProduct);
//            else
//                childCount = complexProduct.getComplexProductItems().size();
            
            //Load the children only once, don't query the server on every call for the same information!
            //NOTE: this method is called on every 'repaint' event, which is unacceptable. TODO (owner): please fix.
            if(complexProduct.getComplexProductItems() == null){
                complexProduct.setComplexProductItems(service.getComplexProductItems(complexProduct));
            }
            childCount = complexProduct.getComplexProductItems().size();
        }
        else
        {
            DataObjectBean dataObjectBean = getDataObjectBean();
            if(dataObjectBean instanceof SalesInvoice)
            {
                childCount = service.getInvoiceItemsCount((SalesInvoice)dataObjectBean);
            }
        }

        if(children.size() != childCount)
            synchronizeChildren();

        return childCount;
    }

    public void synchronizeChildren()
    {
        children.clear();
        children.addAll(createChildren());
    }
}
