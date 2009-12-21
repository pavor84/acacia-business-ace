/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.data.converter;

import com.cosmos.acacia.converter.DataConverter;
import com.cosmos.acacia.converter.data.CustomsCode;
import com.cosmos.acacia.converter.data.Product;
import com.cosmos.acacia.converter.data.ProductDiscount;
import com.cosmos.acacia.converter.data.ProductName;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.product.ProductCategory;
import com.cosmos.acacia.crm.data.product.ProductSupplier;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import com.cosmos.acacia.crm.data.warehouse.WarehouseProduct;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.service.ServiceManager;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Miro
 */
public class Converter {

    public static void main(String[] args) throws Exception {
        DataConverter dataConverter = ServiceManager.getService(DataConverter.class);
        Map<Integer, ProductName> productNameMap = dataConverter.getProductNameMap();
        Map<String, CustomsCode> customsCodeMap = dataConverter.getCustomsCodeMap();
        Map<String, ProductDiscount> productDiscountMap = dataConverter.getProductDiscountMap();
        Organization producer = null;
        ProductCategory category = null;
        Warehouse warehouse = null;

        for(Product product : dataConverter.getProductMap().values()) {
            String productCode = product.getProductCode();
            DbResource currency = Currency.valueOf(product.getCurrencyCode()).getDbResource();
            int productNameCode = product.getProductNameCode();
            BigDecimal unitPrice = product.getUnitPrice();
            String customsCode = product.getCustomsCode();
            String discountCode = product.getDiscountCode();
            int quantityPerPackage = product.getQuantityChange();
            BigDecimal minQuantity = BigDecimal.valueOf(product.getMinQuantity());
            BigDecimal quantity = BigDecimal.valueOf(product.getQuantity());
            BigDecimal expectedQuantity = BigDecimal.valueOf(product.getExpectedQuantity());
            BigDecimal reservedQuantity = BigDecimal.valueOf(product.getReservedQuantity());
            BigDecimal quantityChange = BigDecimal.valueOf(product.getQuantityChange());
            BigDecimal notInvoicedQuantity = BigDecimal.valueOf(product.getNotInvoicedQuantity());

            SimpleProduct simpleProduct = new SimpleProduct();
            simpleProduct.setParentId(null);
            simpleProduct.setProductCode(productCode);
            ProductName productName = productNameMap.get(productNameCode);
            simpleProduct.setProductName(productName.getProductName());
            simpleProduct.setCurrency(currency);
            simpleProduct.setListPrice(unitPrice);
            DbResource measureUnit = MeasurementUnit.Piece.getDbResource();
            simpleProduct.setMeasureUnit(measureUnit);
            simpleProduct.setCategory(category);
            simpleProduct.setProducer(producer);
            simpleProduct.setQuantityPerPackage(quantityPerPackage);
            simpleProduct.setMinimumQuantity(minQuantity);

            WarehouseProduct warehouseProduct = new WarehouseProduct();
            warehouseProduct.setWarehouse(warehouse);
            warehouseProduct.setProduct(simpleProduct);
            warehouseProduct.setNotInvoicedQuantity(notInvoicedQuantity);
            warehouseProduct.setMinimumQuantity(minQuantity);
            warehouseProduct.setQuantityInStock(quantity);
            warehouseProduct.setOrderedQuantity(expectedQuantity);
            warehouseProduct.setReservedQuantity(reservedQuantity);

            ProductSupplier productSupplier = new ProductSupplier();
            productSupplier.setProductCode(productCode);
            productSupplier.setProductName(productName.getSupplierProductName());
            productSupplier.setCurrency(currency);
            productSupplier.setMeasureUnit(measureUnit);
            productSupplier.setOrderPrice(unitPrice);
            productSupplier.setProduct(simpleProduct);
            productSupplier.setSupplier(producer);
        }
    }
}
