/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.math.BigDecimal;
import java.math.MathContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miro
 */
public class SimpleProductTest {

    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;
    private static final BigDecimal ONE_THOUSAND = BigDecimal.valueOf(1000L);
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100L);
    private static final BigDecimal NINE_HUNDRED = BigDecimal.valueOf(900L);
    private static final BigDecimal TWENTY = BigDecimal.valueOf(20L);
    private static final BigDecimal TEN_PERCENT = BigDecimal.TEN.divide(ONE_HUNDRED, MATH_CONTEXT);

    private static SimpleProduct product;

    public SimpleProductTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        product = new SimpleProduct();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private static boolean equals(BigDecimal first, BigDecimal second) {
        if(first == null && second == null || first == second)
            return true;

        if(first == null || second == null)
            return false;

        return first.compareTo(second) == 0;
    }

    /**
     * Test of getSalePrice method, of class SimpleProduct.
     */
    @Test
    public void testGetSalePrice() {
        System.out.println("getSalePrice");
        BigDecimal value = product.getSalePrice();
        assertNull(value);

        product.setListPrice(ONE_THOUSAND);
        value = product.getSalePrice();
        assertEquals(ONE_THOUSAND, value);

        ProductPercentValue ppv = new ProductPercentValue();
        ppv.setPercentValue(TEN_PERCENT);
        product.setDiscountPercentValue(ppv);

        value = product.getTotalDiscountPercent();
        assertTrue(equals(TEN_PERCENT, value));
        value = product.getTotalDiscountValue();
        assertTrue(equals(ONE_HUNDRED, value));
        value = product.getSalePrice();
        assertTrue(equals(NINE_HUNDRED, value));

        ProductCategory category = new ProductCategory();
        category.setDiscountPercent(TEN_PERCENT);
        product.setCategory(category);

        value = BigDecimal.ONE.subtract(TEN_PERCENT, MATH_CONTEXT);
        BigDecimal percent = value.multiply(value, MATH_CONTEXT);
        percent = BigDecimal.ONE.subtract(percent);
        value = product.getTotalDiscountPercent();
        assertTrue(equals(percent, value));

        value = product.getTotalDiscountValue();
        assertTrue(equals(BigDecimal.valueOf(190), value));

        value = product.getPurchasePrice();
        assertTrue(equals(BigDecimal.valueOf(810), value));

        ppv = new ProductPercentValue();
        ppv.setPercentValue(TEN_PERCENT);
        product.setTransportPercentValue(ppv);

        category.setTransportValue(TWENTY);

        value = product.getTransportPrice();
        assertTrue(equals(BigDecimal.valueOf(81), value));

        product.setTransportPercentValue(null);
        product.setTransportValue(BigDecimal.TEN);
        value = product.getTransportPrice();
        assertTrue(equals(BigDecimal.TEN, value));

        product.setTransportValue(null);
        value = product.getTransportPrice();
        assertTrue(equals(TWENTY, value));

        category.setTransportPercent(TEN_PERCENT);
        category.setTransportValue(null);
        value = product.getTransportPrice();
        assertTrue(equals(BigDecimal.valueOf(81), value));

        value = product.getBaseDutyPrice();
        assertTrue(equals(BigDecimal.valueOf(891), value));

        ppv = new ProductPercentValue();
        ppv.setPercentValue(TEN_PERCENT);
        product.setCustomsDutyPercentValue(ppv);
        value = product.getCustomsDutyValue();
        assertTrue(equals(BigDecimal.valueOf(891, 1), value));

        ppv = new ProductPercentValue();
        ppv.setPercentValue(TEN_PERCENT);
        product.setExciseDutyPercentValue(ppv);
        value = product.getExciseDutyValue();
        assertTrue(equals(BigDecimal.valueOf(891, 1), value));

        value = product.getCostPrice();
        assertTrue(equals(BigDecimal.valueOf(10692, 1), value));

        ppv = new ProductPercentValue();
        ppv.setPercentValue(TEN_PERCENT);
        product.setProfitPercentValue(ppv);

        value = product.getTotalProfitValue();
        assertTrue(equals(BigDecimal.valueOf(1188, 1), value));

        category.setProfitPercent(TEN_PERCENT);
        value = product.getTotalProfitValue();
        assertTrue(equals(BigDecimal.valueOf(2673, 1), value));

        value = product.getSalePrice();
        assertTrue(equals(BigDecimal.valueOf(13365, 1), value));
    }

}