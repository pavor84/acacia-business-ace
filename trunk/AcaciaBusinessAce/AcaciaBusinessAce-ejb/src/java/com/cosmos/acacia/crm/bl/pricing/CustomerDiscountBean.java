package com.cosmos.acacia.crm.bl.pricing;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.crm.bl.impl.BaseBean;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.CustomerDiscount;
import com.cosmos.acacia.crm.data.CustomerDiscountItem;
import com.cosmos.acacia.crm.validation.EntityValidator;
import com.cosmos.acacia.crm.validation.impl.CustomerDiscountItemValidatorLocal;

/**
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class CustomerDiscountBean extends BaseBean<CustomerDiscount, CustomerDiscountItem> implements CustomerDiscountLocal, CustomerDiscountRemote {
    @PersistenceContext
    protected EntityManager em;

    @EJB
    protected EntityStoreManagerLocal esm;
    
    @EJB
    protected CustomerDiscountItemValidatorLocal itemValidatorLocal;
    
    @Override
    public CustomerDiscount getCustomerDiscountForCustomer(BusinessPartner customer) {
        CustomerDiscount result = (CustomerDiscount) 
        getSingleResult("CustomerDiscount.getForCustomer", "customer", customer);
        
        if (result==null){
            result = newEntity(customer.getParentId());
            result.setCustomer(customer);
            result = save(result);
        }
        
        return result;
    }
    
    @Override
    protected EntityValidator<CustomerDiscountItem> getItemValidator() {
        return itemValidatorLocal;
    }
}
