/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.impl.acacia.converter;

import com.cosmos.acacia.converter.DataConverter;
import com.cosmos.acacia.converter.data.CustomsCode;
import com.cosmos.acacia.converter.data.Product;
import com.cosmos.acacia.converter.data.ProductDiscount;
import com.cosmos.acacia.converter.data.ProductName;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Miro
 */
public class DataConverterImpl implements DataConverter {

    private static final String DATA_PATH = "E:/Works.NB/Acacia/data/AS-products/";
    private static final String PRODUCT_NAME_FILE_NAME = DATA_PATH + "details.txt";
    private static final String CUSTOMS_CODE_FILE_NAME = DATA_PATH + "mita.txt";
    private static final String PRODUCT_DISCOUNT_FILE_NAME = DATA_PATH + "discount.txt";
    private static final String PRODUCT_FILE_NAME = DATA_PATH + "parts.txt";

    @Override
    public Map<Integer, ProductName> getProductNameMap() throws IOException {
        DataProcessor<ProductName> dataProcessor = new DataProcessor(PRODUCT_NAME_FILE_NAME, ProductName.class);
        TreeMap<Integer, ProductName> productNameMap = new TreeMap<Integer, ProductName>();
        for(ProductName productName : dataProcessor.processData()) {
            productNameMap.put(productName.getProductNameCode(), productName);
        }

        return productNameMap;
    }

    @Override
    public Map<String, CustomsCode> getCustomsCodeMap() throws IOException {
        DataProcessor<CustomsCode> dataProcessor = new DataProcessor(CUSTOMS_CODE_FILE_NAME, CustomsCode.class);
        TreeMap<String, CustomsCode> customsCodeMap = new TreeMap<String, CustomsCode>();
        for(CustomsCode customsCode : dataProcessor.processData()) {
            customsCodeMap.put(customsCode.getCustomsCode(), customsCode);
        }

        return customsCodeMap;
    }

    @Override
    public Map<String, ProductDiscount> getProductDiscountMap() throws IOException {
        DataProcessor<ProductDiscount> dataProcessor = new DataProcessor(PRODUCT_DISCOUNT_FILE_NAME, ProductDiscount.class);
        TreeMap<String, ProductDiscount> productDiscountMap = new TreeMap<String, ProductDiscount>();
        for(ProductDiscount productDiscount : dataProcessor.processData()) {
            productDiscountMap.put(productDiscount.getDiscountCode(), productDiscount);
        }

        return productDiscountMap;
    }

    @Override
    public Map<String, Product> getProductMap() throws IOException {
        DataProcessor<Product> dataProcessor = new DataProcessor(PRODUCT_FILE_NAME, Product.class);
        TreeMap<String, Product> productMap = new TreeMap<String, Product>();
        for(Product product : dataProcessor.processData()) {
            productMap.put(product.getProductCode(), product);
        }

        return productMap;
    }
}