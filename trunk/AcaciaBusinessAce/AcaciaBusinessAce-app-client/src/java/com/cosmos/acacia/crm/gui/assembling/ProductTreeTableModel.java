/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Product;
import java.math.BigDecimal;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

/**
 *
 * @author Miro
 */
public class ProductTreeTableModel
    extends AbstractTreeTableModel
{
    private static final Logger logger = Logger.getLogger(ProductTreeTableModel.class);

    private AssemblingRemote assembling;


    public ProductTreeTableModel(
        ComplexProduct root,
        AssemblingRemote assembling)
    {
        super(root);
        this.assembling = assembling;
    }

    private boolean isValidNode(Object node)
    {
        boolean result = false;
        
        if(node instanceof Product)
        {
            Product product = (Product)node;
            
            while(!result && product != null)
            {
                result = product.equals(root);
                product = getParentProduct(product);
            }
        }
        
        return result;
    }

    protected Product getParentProduct(Product product)
    {
        return null;
    }

    @Override
    public Product getChild(Object parent, int index)
    {
        /*if(!isValidNode(parent))
        {
            throw new IllegalArgumentException("parent is not a file governed by this model");
        }*/

        List<Product> childProducts = getChildProducts((Product)parent);
        if(childProducts != null && index < childProducts.size())
            return childProducts.get(index);

        return null;
    }

    protected List<Product> getChildProducts(Product parent)
    {
        return null;
    }
    
    @Override
    public int getChildCount(Object parent)
    {
        List<Product> childProducts = getChildProducts((Product)parent);
        if(childProducts != null)
            return childProducts.size();

        return 0;
    }

    @Override
    public Class<?> getColumnClass(int column)
    {
        switch(column)
        {
            case 0:
                return String.class;

            case 1:
                return String.class;

            case 2:
                return DbResource.class;

            case 3:
                return BigDecimal.class;

            default:
                return super.getColumnClass(column);
        }
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0:
                return "Product Code";

            case 1:
                return "Product Name";

            case 2:
                return "Measure Unit";

            case 3:
                return "Sale Price";

            default:
                return super.getColumnName(column);
        }
    }

    @Override
    public int getColumnCount()
    {
        return 4;
    }

    @Override
    public Object getValueAt(Object object, int column)
    {
        Product product = (Product)object;
        switch(column)
        {
            case 0:
                return product.getProductCode();

            case 1:
                return product.getProductName();

            case 2:
                return product.getMeasureUnit();

            case 3:
                return product.getSalePrice();

            default:
                return null;
        }
    }

    @Override
    public int getIndexOfChild(Object parent, Object child)
    {
        List<Product> childProducts = getChildProducts((Product)parent);
        if(childProducts != null)
            return childProducts.indexOf(child);

        return -1;
    }

    @Override
    public Product getRoot()
    {
        return (Product)root;
    }

    @Override
    public boolean isLeaf(Object node)
    {
        if(node instanceof Product)
        {
            return !(node instanceof ComplexProduct);
        }
        
        return true;
    }

}