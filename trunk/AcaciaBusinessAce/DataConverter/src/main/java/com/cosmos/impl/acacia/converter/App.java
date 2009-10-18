package com.cosmos.impl.acacia.converter;

import com.cosmos.acacia.converter.DataConverter;
import com.cosmos.acacia.converter.data.CustomsCode;
import com.cosmos.acacia.converter.data.Product;
import com.cosmos.acacia.converter.data.ProductDiscount;
import com.cosmos.acacia.converter.data.ProductName;
import com.cosmos.acacia.service.ServiceManager;
import java.util.Map;

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

        DataConverter dataConverter = ServiceManager.getService(DataConverter.class);
        Map<Integer, ProductName> productNameMap = dataConverter.getProductNameMap();
        Map<String, CustomsCode> customsCodeMap = dataConverter.getCustomsCodeMap();
        Map<String, ProductDiscount> productDiscountMap = dataConverter.getProductDiscountMap();
        Map<String, Product> productMap = dataConverter.getProductMap();

        StringBuilder sb = new StringBuilder();
        for(Product product : productMap.values()) {
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
