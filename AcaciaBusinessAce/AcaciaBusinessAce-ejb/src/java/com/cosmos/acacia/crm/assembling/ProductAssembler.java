/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.crm.data.product.ComplexProduct;
import com.cosmos.acacia.crm.data.product.ComplexProductItem;
import com.cosmos.acacia.crm.data.product.Product;
import com.cosmos.acacia.crm.data.assembling.AssemblingMessage;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.RealProduct;
import com.cosmos.acacia.crm.data.assembling.VirtualProduct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.security.auth.callback.CallbackHandler;
import javax.swing.event.EventListenerList;
import org.apache.log4j.Logger;

/**
 *
 * @author Miro
 */
public class ProductAssembler
{
    private static final Logger logger = Logger.getLogger(ProductAssembler.class);

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private AssemblingSchema assemblingSchema;
    private ProductAssemblerService productAssemblerService;
    private CallbackHandler callbackHandler;

    private EventListenerList eventListeners = new EventListenerList();


    public ProductAssembler(
        AssemblingSchema assemblingSchema,
        ProductAssemblerService productAssemblerService,
        CallbackHandler callbackHandler)
    {
        logger.info("ProductAssembler(" +
            "assemblingSchema: " + assemblingSchema +
            ", productAssemblerService: " + productAssemblerService +
            ", callbackHandler: " + callbackHandler + ")");
        this.assemblingSchema = assemblingSchema;
        this.productAssemblerService = productAssemblerService;
        this.callbackHandler = callbackHandler;
    }

    public CallbackHandler getCallbackHandler()
    {
        return callbackHandler;
    }

    public void setCallbackHandler(CallbackHandler callbackHandler)
    {
        this.callbackHandler = callbackHandler;
    }

    public ComplexProduct assemble(Map parameters)
        throws AlgorithmException
    {
        logger.info("assemble(parameters: " + parameters + "), assemblingSchema: " + assemblingSchema);
        int itemCounter = 0;
        List<AssemblingSchemaItem> asiList = getAssemblingSchemaItems();
        logger.info("assemble(Map), List<AssemblingSchemaItem>: " + asiList);
        ComplexProduct product = new ComplexProduct();
        product.setProductCode(assemblingSchema.getProductCode());
        product.setProductName(assemblingSchema.getProductName());
        product.setAppliedSchema(assemblingSchema);
        product.setMeasureUnit(assemblingSchema.getMeasureUnit());
        BigDecimal productPrice = BigDecimal.ZERO;
        for(AssemblingSchemaItem asi : asiList)
        {
            logger.info("assemble(Map), AssemblingSchemaItem: " + asi);
            List<ComplexProductItem> cpiList = assemble(asi, product, parameters);
            logger.info("assemble(Map), List<ComplexProductItem>: " + cpiList);
            if(cpiList == null || cpiList.size() == 0)
                continue;

            for(ComplexProductItem cpi : cpiList)
            {
                cpi.setOrderPosition(++itemCounter);
                product.addComplexProductItem(cpi);
                logger.info("assemble(Map), addComplexProductItem: " + cpi + ", to ComplexProduct: " + product);
                productPrice = productPrice.add(cpi.getItemPrice());
                product.setSalesPrice(productPrice);
                fireComplexProductItemAdded(product, cpi);
            }
        }

        return product;
    }

    protected List<ComplexProductItem> assemble(
            AssemblingSchemaItem asi,
            ComplexProduct product,
            Map parameters)
        throws AlgorithmException
    {
        logger.info("assemble(AssemblingSchemaItem: " + asi +
            ", ComplexProduct: " + product +
            ", parameters: " + parameters + ")");
        BigDecimal iQuantity = asi.getQuantity();
        Algorithm algorithm = new Algorithm(asi, productAssemblerService);
        algorithm.setCallbackHandler(getCallbackHandler());
        Object valueAgainstConstraints = getAlgorithmValue(asi, parameters);
        logger.info("valueAgainstConstraints: " + valueAgainstConstraints);
        Algorithm.Type algorithmType = asi.getAlgorithmType();
        logger.info("algorithmType: " + algorithmType);
        List<AssemblingSchemaItemValue> itemValues = algorithm.apply(valueAgainstConstraints);
        List<ComplexProductItem> productItems = new ArrayList<ComplexProductItem>(itemValues.size());
        logger.info("assemble(...), AssemblingSchemaItemValues: " + itemValues);
        for(AssemblingSchemaItemValue itemValue : itemValues)
        {
            BigDecimal ivQuantity = itemValue.getQuantity();
            VirtualProduct virtualProduct = itemValue.getVirtualProduct();
            logger.info("assemble(...), virtualProduct: " + virtualProduct);
            ComplexProductItem productItem = new ComplexProductItem(product);
            productItem.setAppliedAlgorithm(asi.getAssemblingAlgorithm());
            productItem.setAppliedValue((Serializable)valueAgainstConstraints);

            Product itemProduct;
            if(virtualProduct instanceof AssemblingSchema)
            {
                AssemblingSchema itemSchema = (AssemblingSchema)virtualProduct;
                logger.info("assemble(...), itemSchema: " + itemSchema);
                ProductAssembler assembler = 
                    new ProductAssembler(
                        itemSchema,
                        productAssemblerService,
                        callbackHandler);
                itemProduct = assembler.assemble(parameters);
                logger.info("assemble(...), itemProduct: " + itemProduct);
            }
            else
            {
                RealProduct realProduct = (RealProduct)virtualProduct;
                itemProduct = realProduct.getSimpleProduct();
            }

            BigDecimal productItemQuantity = iQuantity.multiply(ivQuantity);
            productItem.setQuantity(productItemQuantity);

            BigDecimal unitPrice = getClientPrice(itemProduct);
            BigDecimal itemPrice = unitPrice.multiply(productItemQuantity);
            productItem.setUnitPrice(unitPrice);
            productItem.setItemPrice(itemPrice);

            productItem.setProduct(itemProduct);
            productItems.add(productItem);
        }

        return productItems;
    }


    private List<AssemblingSchemaItem> getAssemblingSchemaItems()
    {
        return productAssemblerService.getAssemblingSchemaItems(assemblingSchema);
    }


    /**
     * If the algorithm type is Range or Equals trying to find corresponding
     * value in the lollowing order:
     * 1. From passed parameters;
     * 2. As default value for the current client (recipient);
     * 3. As default value for the applied Assembling Schema Item
     * 4. Ask the operator (seller, client) using Dialog Form and Callback mechanism
     *
     */
    protected Object getAlgorithmValue(
            AssemblingSchemaItem asi,
            Map parameters)
    {
        logger.info("getAlgorithmValue(SchemaItem: " + asi + ", parameters: " + parameters);
        Algorithm.Type algorithmType;
        algorithmType = (Algorithm.Type)asi.getAssemblingAlgorithm().getEnumValue();
        if(Algorithm.Type.RangeAlgorithms.contains(algorithmType) ||
           Algorithm.Type.EqualsAlgorithms.contains(algorithmType))
        {
            List<String> keys = productAssemblerService.getPropertyKeys(
                    assemblingSchema, asi.getAssemblingMessage());
            Object value;
            for(String key : keys)
            {
                if((value = parameters.get(key)) != null)
                    return value;
            }
        }

        return null;
    }

    protected BigDecimal getClientPrice(Product product)
    {
        if(product instanceof ComplexProduct)
            return product.getSalesPrice();

        // ToDO: Calculate the price
        BigDecimal productPrice = product.getSalesPrice();
        BigDecimal discount = getDiscount(product);
        productPrice = productPrice.subtract(productPrice.multiply(discount).divide(ONE_HUNDRED));
        return productPrice;
    }

    protected BigDecimal getDiscount(Product product)
    {
        BigDecimal clientDiscount = getClientDiscount(product);
        BigDecimal campaignDiscount = getCampaignDiscount(product);
        BigDecimal productDiscount = getProductDiscount(product);
        return clientDiscount.max(campaignDiscount).max(productDiscount);
    }

    protected BigDecimal getClientDiscount(Product product)
    {
        return BigDecimal.valueOf(0.0);
    }

    protected BigDecimal getCampaignDiscount(Product product)
    {
        return BigDecimal.valueOf(0.0);
    }

    protected BigDecimal getProductDiscount(Product product)
    {
        return BigDecimal.valueOf(0.0);
    }

    public void addProductAssemblerListener(ProductAssemblerListener listener)
    {
        logger.info("addProductAssemblerListener(" + listener + ")");
        eventListeners.add(ProductAssemblerListener.class, listener);
    }

    public void removeProductAssemblerListener(ProductAssemblerListener listener)
    {
        logger.info("removeProductAssemblerListener(" + listener + ")");
        eventListeners.remove(ProductAssemblerListener.class, listener);
    }

    public ProductAssemblerListener[] getProductAssemblerListeners()
    {
        return eventListeners.getListeners(ProductAssemblerListener.class);
    }

    protected void fireComplexProductItemAdded(
        ComplexProduct product,
        ComplexProductItem item)
    {
        logger.info("fireComplexProductItemAdded(" +
            "ComplexProduct: " + product +
            ", ComplexProductItem: " + item + ")");
        ProductAssemblerListener[] listeners;
        int size;
        if((listeners = getProductAssemblerListeners()) != null &&
            (size = listeners.length) > 0)
        {
            logger.info("fireComplexProductItemAdded().listeners count: " + size);
            ProductAssemblerEvent event = new ProductAssemblerEvent(this, product, item);
            for(ProductAssemblerListener listener : listeners)
            {
                logger.info("fireComplexProductItemAdded().ProductAssemblerListener: " + listener);
                listener.productAssemblerEvent(event);
            }
        }
        else
        {
            logger.info("fireComplexProductItemAdded().ProductAssemblerListeners: " + listeners);
        }
    }
}
