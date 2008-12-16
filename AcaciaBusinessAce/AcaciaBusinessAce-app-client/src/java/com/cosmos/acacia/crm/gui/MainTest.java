/**
 * 
 */
package com.cosmos.acacia.crm.gui;

import org.junit.runner.JUnitCore;

import com.cosmos.test.bl.BusinessPartnerListTest;
import com.cosmos.test.bl.OrderConfirmationListTest;
import com.cosmos.test.bl.PatternMaskListTest;
import com.cosmos.test.bl.ProductCategoriesTest;
import com.cosmos.test.bl.ProductsListTest;
import com.cosmos.test.bl.PurchaseOrderListTest;
import com.cosmos.test.bl.WarehouseListTest;
import com.cosmos.test.bl.assembling.ProductAssemblerTest;
import com.cosmos.test.bl.contactbook.ContactBookTest;
import com.cosmos.test.bl.users.UsersTest;

/**
 * Created : 28.07.2008
 * 
 * @author Petar Milev
 */
public class MainTest {

    public static void main(String[] args) {
        mainTest();
    }

    public static void mainTest() {
        String[] tests = new String[] {
           //DataObjectTest.class.getName(),
           PatternMaskListTest.class.getName(),
           ProductsListTest.class.getName(),
           ProductCategoriesTest.class.getName(),
           WarehouseListTest.class.getName(),
           ContactBookTest.class.getName(),
           UsersTest.class.getName(),
           PurchaseOrderListTest.class.getName(),
           OrderConfirmationListTest.class.getName(),
           BusinessPartnerListTest.class.getName(),
           ProductAssemblerTest.class.getName(),
        };
        tests = new String[] {ProductAssemblerTest.class.getName()};

        JUnitCore.main(tests);
    }
}
