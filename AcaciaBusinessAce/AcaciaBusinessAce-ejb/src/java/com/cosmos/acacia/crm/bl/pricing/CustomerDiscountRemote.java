package com.cosmos.acacia.crm.bl.pricing;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.bl.impl.BaseRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.CustomerDiscount;
import com.cosmos.acacia.crm.data.CustomerDiscountItem;

/**
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
@Remote
public interface CustomerDiscountRemote extends BaseRemote<CustomerDiscount, CustomerDiscountItem>{
    /**
     * @param selectedRowObject
     * @return
     */
    CustomerDiscount getCustomerDiscountForCustomer(BusinessPartner customer);
}
