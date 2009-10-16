package com.cosmos.acacia.converter;

import com.cosmos.acacia.converter.data.CustomsCode;
import com.cosmos.acacia.converter.data.Product;
import com.cosmos.acacia.converter.data.ProductDiscount;
import com.cosmos.acacia.converter.data.ProductName;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.TreeMap;

/**
 * Hello world!
 *
 */
public class App {
public static final int BUFFER_SIZE = 2048;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String inputFileName;
        DataProcessor dataProcessor;

        inputFileName = "E:/Works.NB/Acacia/data/AS-products/details.txt";
        dataProcessor = new DataProcessor(inputFileName, ProductName.class);
        List<ProductName> productNames = dataProcessor.processData();
        TreeMap<Integer, ProductName> productNameMap = new TreeMap<Integer, ProductName>();
        for(ProductName productName : productNames) {
            productNameMap.put(productName.getProductNameCode(), productName);
        }

        inputFileName = "E:/Works.NB/Acacia/data/AS-products/mita.txt";
        dataProcessor = new DataProcessor(inputFileName, CustomsCode.class);
        List<CustomsCode> customsCodes = dataProcessor.processData();
        TreeMap<String, CustomsCode> customsCodeMap = new TreeMap<String, CustomsCode>();
        for(CustomsCode customsCode : customsCodes) {
            customsCodeMap.put(customsCode.getCustomsCode(), customsCode);
        }

        inputFileName = "E:/Works.NB/Acacia/data/AS-products/discount.txt";
        dataProcessor = new DataProcessor(inputFileName, ProductDiscount.class);
        List<ProductDiscount> productDiscounts = dataProcessor.processData();
        TreeMap<String, ProductDiscount> productDiscountMap = new TreeMap<String, ProductDiscount>();
        for(ProductDiscount productDiscount : productDiscounts) {
            productDiscountMap.put(productDiscount.getDiscountCode(), productDiscount);
        }

        inputFileName = "E:/Works.NB/Acacia/data/AS-products/parts.txt";
        dataProcessor = new DataProcessor(inputFileName, Product.class);
        List<Product> products = dataProcessor.processData();
        StringBuilder sb = new StringBuilder();
        for(Product product : products) {
            boolean foundProductNameCode = true;
            boolean foundCustomsCode = true;
            boolean foundDiscountCode = true;
            int intValue;
            if((intValue = product.getProductNameCode()) > 0 && productNameMap.get(intValue) == null) {
                foundProductNameCode = false;
            }

            String strValue;
            if((strValue = product.getCustomsCode()) != null && customsCodeMap.get(strValue) == null) {
                foundCustomsCode = false;
            }

            if((strValue = product.getDiscountCode()) != null && productDiscountMap.get(strValue) == null) {
                foundDiscountCode = false;
            }

            sb.setLength(0);
            if(!foundProductNameCode) {
                sb.append("productName");
            }
            if(!foundCustomsCode) {
                if(sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("customsCode");
            }
            if(!foundDiscountCode) {
                if(sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("discountCode");
            }

            if(sb.length() > 0) {
                sb.insert(0, "Missing ").append(" for product: ").append(product);
                System.out.println(sb.toString());
            }
        }

    }
}
