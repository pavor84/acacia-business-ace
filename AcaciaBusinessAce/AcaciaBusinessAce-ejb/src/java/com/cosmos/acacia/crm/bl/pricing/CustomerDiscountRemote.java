/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.pricing;

import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.data.customer.CustomerDiscount;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItem;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItemByCategory;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItemByProduct;
import com.cosmos.beansbinding.EntityProperties;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface CustomerDiscountRemote {

    /**
     * 
     * @param customer
     * @return
     */
    CustomerDiscount getCustomerDiscount(BusinessPartner customer);

    /**
     * 
     * @param customer
     * @return
     */
    CustomerDiscount newCustomerDiscount(BusinessPartner customer);

    /**
     * 
     * @param customerDiscount
     * @return
     */
    CustomerDiscount saveCustomerDiscount(CustomerDiscount customerDiscount);

    /**
     * 
     * @param customerDiscount
     * @return
     */
    boolean deleteCustomerDiscount(CustomerDiscount customerDiscount);

    EntityProperties getCustomerDiscountEntityProperties();
    EntityProperties getCustomerDiscountItemEntityProperties();
    EntityProperties getCustomerDiscountItemByProductEntityProperties();
    EntityProperties getCustomerDiscountItemByCategoryEntityProperties();

    /**
     * 
     * @param customerDiscount
     * @return
     */
    List<CustomerDiscountItem> getCustomerDiscountItems(CustomerDiscount customerDiscount);

    /**
     *
     * @param customerDiscount
     * @param product
     * @return
     */
    CustomerDiscountItemByProduct getCustomerDiscountItem(CustomerDiscount customerDiscount, Product product);

    /**
     * 
     * @param customerDiscount
     * @param productCategory
     * @return
     */
    CustomerDiscountItemByCategory getCustomerDiscountItem(CustomerDiscount customerDiscount, ProductCategory productCategory);

    /**
     * 
     * @param customerDiscount
     * @return
     */
    CustomerDiscountItemByCategory newCustomerDiscountItem(CustomerDiscount customerDiscount, ProductCategory productCategory);

    /**
     * 
     * @param customerDiscount
     * @return
     */
    CustomerDiscountItemByProduct newCustomerDiscountItem(CustomerDiscount customerDiscount, Product product);

    /**
     * 
     * @param customerDiscountItem
     * @return
     */
    CustomerDiscountItem saveCustomerDiscountItem(CustomerDiscountItem customerDiscountItem);

    /**
     * 
     * @param customerDiscountItem
     * @return
     */
    boolean deleteCustomerDiscountItem(CustomerDiscountItem customerDiscountItem);
}
