/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.converter;

import com.cosmos.acacia.converter.data.CustomsCode;
import com.cosmos.acacia.converter.data.Product;
import com.cosmos.acacia.converter.data.ProductDiscount;
import com.cosmos.acacia.converter.data.ProductName;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Miro
 */
public interface DataConverter {

    /**
     * @return map with ProductName where the the primary key is productNameCode.
     */
    Map<Integer, ProductName> getProductNameMap() throws IOException;

    /**
     * @return map with CustomsCode where the the primary key is customsCode.
     */
    Map<String, CustomsCode> getCustomsCodeMap() throws IOException;

    /**
     * @return map with ProductDiscount where the the primary key is discountCode.
     */
    Map<String, ProductDiscount> getProductDiscountMap() throws IOException;

    /**
     * @return map with Product where the the primary key is productCode.
     */
    Map<String, Product> getProductMap() throws IOException;
}
