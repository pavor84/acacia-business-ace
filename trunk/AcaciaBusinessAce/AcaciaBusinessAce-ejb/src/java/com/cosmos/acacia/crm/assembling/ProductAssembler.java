/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.ComplexProductItem;
import com.cosmos.acacia.crm.data.Product;
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

/**
 *
 * @author Miro
 */
public class ProductAssembler
{
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private AssemblingSchema assemblingSchema;
    private ProductAssemblerService productAssemblerService;
    private CallbackHandler callbackHandler;


    public ProductAssembler(
        AssemblingSchema assemblingSchema,
        ProductAssemblerService productAssemblerService,
        CallbackHandler callbackHandler)
    {
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
        int itemCounter = 0;
        List<AssemblingSchemaItem> asiList = getAssemblingSchemaItems();
        ComplexProduct product = new ComplexProduct();
        product.setProductCode(assemblingSchema.getSchemaCode());
        product.setAppliedSchema(assemblingSchema);
        BigDecimal productPrice = BigDecimal.ZERO;
        //List<ComplexProductItem> productItems = new ArrayList<ComplexProductItem>(asiList.size());
        for(AssemblingSchemaItem asi : asiList)
        {
            List<ComplexProductItem> cpiList = assemble(asi, product, parameters);
            if(cpiList == null || cpiList.size() == 0)
                continue;

            for(ComplexProductItem cpi : cpiList)
            {
                cpi.setOrderPosition(++itemCounter);
                //productItems.add(cpi);
                product.addComplexProductItem(cpi);
                productPrice = productPrice.add(cpi.getItemPrice());
            }
        }
        product.setSalePrice(productPrice);

        /*em.persist(product);
        for(ComplexProductItem productItem : productItems)
        {
            em.persist(productItem);
        }*/

        return product;
    }

    protected List<ComplexProductItem> assemble(
            AssemblingSchemaItem asi,
            ComplexProduct product,
            Map parameters)
        throws AlgorithmException
    {
        BigDecimal iQuantity = asi.getQuantity();
        Algorithm algorithm = new Algorithm(asi, productAssemblerService);
        algorithm.setCallbackHandler(getCallbackHandler());
        Object valueAgainstConstraints = getAlgorithmValue(asi, parameters);
        List<AssemblingSchemaItemValue> itemValues = algorithm.apply(valueAgainstConstraints);
        List<ComplexProductItem> productItems = new ArrayList<ComplexProductItem>(itemValues.size());
        for(AssemblingSchemaItemValue itemValue : itemValues)
        {
            BigDecimal ivQuantity = itemValue.getQuantity();
            VirtualProduct virtualProduct = itemValue.getVirtualProduct();
            ComplexProductItem productItem = new ComplexProductItem(product);
            productItem.setAppliedAlgorithm(asi.getAssemblingAlgorithm());
            productItem.setAppliedValue((Serializable)valueAgainstConstraints);

            Product itemProduct;
            if(virtualProduct instanceof AssemblingSchema)
            {
                AssemblingSchema itemSchema = (AssemblingSchema)virtualProduct;
                ProductAssembler assembler = 
                    new ProductAssembler(
                        itemSchema,
                        productAssemblerService,
                        callbackHandler);
                assembler.setCallbackHandler(getCallbackHandler());
                itemProduct = assembler.assemble(parameters);
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
        Algorithm.Type algorithmType;
        algorithmType = (Algorithm.Type)asi.getAssemblingAlgorithm().getEnumValue();
        if(Algorithm.Type.RangeAlgorithms.contains(algorithmType) ||
           Algorithm.Type.EqualsAlgorithms.contains(algorithmType))
        {
            String messageCode = asi.getMessageCode();
            return parameters.get(messageCode);
        }

        return null;
    }

    protected BigDecimal getClientPrice(Product product)
    {
        if(product instanceof ComplexProduct)
            return product.getSalePrice();

        // ToDO: Calculate the price
        BigDecimal productPrice = product.getSalePrice();
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
}
