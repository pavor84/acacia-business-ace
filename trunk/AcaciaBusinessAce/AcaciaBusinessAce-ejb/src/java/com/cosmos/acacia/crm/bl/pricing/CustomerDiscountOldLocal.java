package com.cosmos.acacia.crm.bl.pricing;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.CustomerDiscountOld;
import com.cosmos.acacia.crm.data.CustomerDiscountItemOld;
import com.cosmos.acacia.crm.data.SimpleProduct;

/**
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
@Local
public interface CustomerDiscountOldLocal extends CustomerDiscountOldRemote {

    /**
     * Find customer discount item for the specified product and customer discount.
     * Null if not found.
     * @param customerDiscount
     * @param product
     * @return
     */
    CustomerDiscountItemOld getCustomerDiscountItem(CustomerDiscountOld customerDiscount,
                                                 SimpleProduct product);
	
}
