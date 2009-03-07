package com.cosmos.acacia.crm.bl.pricing;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.bl.impl.BaseRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.CustomerDiscountOld;
import com.cosmos.acacia.crm.data.CustomerDiscountItemOld;
import java.util.List;

/**
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
@Remote
public interface CustomerDiscountOldRemote extends BaseRemote<CustomerDiscountOld, CustomerDiscountItemOld>{
    /**
     * @param selectedRowObject
     * @return
     */
    CustomerDiscountOld getCustomerDiscountForCustomer(BusinessPartner customer);

    List<CustomerDiscountItemOld> getCustomerDiscountItems(CustomerDiscountOld customerDiscount);
}
