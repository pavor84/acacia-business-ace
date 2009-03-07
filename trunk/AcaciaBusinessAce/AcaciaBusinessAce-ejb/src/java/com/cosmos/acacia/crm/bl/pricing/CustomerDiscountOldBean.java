package com.cosmos.acacia.crm.bl.pricing;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.crm.bl.impl.BaseBean;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.CustomerDiscountOld;
import com.cosmos.acacia.crm.data.CustomerDiscountItemOld;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.validation.EntityValidator;
import com.cosmos.acacia.crm.validation.impl.CustomerDiscountItemValidatorLocal;

/**
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class CustomerDiscountOldBean extends BaseBean<CustomerDiscountOld, CustomerDiscountItemOld>
        implements CustomerDiscountOldLocal, CustomerDiscountOldRemote {
    @PersistenceContext
    protected EntityManager em;

    @EJB
    protected EntityStoreManagerLocal esm;
    
    @EJB
    protected CustomerDiscountItemValidatorLocal itemValidatorLocal;

    @Override
    public CustomerDiscountOld getCustomerDiscountForCustomer(BusinessPartner customer) {
        CustomerDiscountOld result = (CustomerDiscountOld) getSingleResult("CustomerDiscount.getForCustomer", "customer", customer);

        if (result == null) {
            result = newEntity(customer.getParentId());
            result.setCustomer(customer);
            result = save(result);
        }

        return result;
    }
    
    @Override
    protected EntityValidator<CustomerDiscountItemOld> getItemValidator() {
        return itemValidatorLocal;
    }
    
    @Override
    public void delete(CustomerDiscountOld entity) {
        if (entity == null)
            throw new IllegalArgumentException("null: 'entity'");
        List<CustomerDiscountItemOld> items = listItems(entity.getId());
        for (CustomerDiscountItemOld item : items) {
            esm.remove(em, item);
        }
        esm.remove(em, entity);
    }

    @Override
    public CustomerDiscountItemOld getCustomerDiscountItem(CustomerDiscountOld customerDiscount,
                                                        SimpleProduct product) {
        CustomerDiscountItemOld result = (CustomerDiscountItemOld)
            getSingleResult("CustomerDiscountItem.findByProduct", "product", product, "parentDataObjectId", customerDiscount.getId());
        return result;
    }

    @Override
    public List<CustomerDiscountItemOld> getCustomerDiscountItems(CustomerDiscountOld customerDiscount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
