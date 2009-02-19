package com.cosmos.acacia.crm.bl.pricing;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.CustomerDiscount;
import com.cosmos.acacia.crm.data.CustomerDiscountItem;
import com.cosmos.acacia.crm.data.SimpleProduct;

/**
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
@Local
public interface CustomerDiscountLocal extends CustomerDiscountRemote{

    /**
     * Find customer discount item for the specified product and customer discount.
     * Null if not found.
     * @param customerDiscount
     * @param product
     * @return
     */
    CustomerDiscountItem getCustomerDiscountItem(CustomerDiscount customerDiscount,
                                                 SimpleProduct product);
	
}
