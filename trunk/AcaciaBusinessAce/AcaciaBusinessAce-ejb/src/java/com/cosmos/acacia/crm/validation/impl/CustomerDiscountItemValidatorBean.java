package com.cosmos.acacia.crm.validation.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.CustomerDiscountItem;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationUtil;

/**
 * 
 * Created	:	05.01.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class CustomerDiscountItemValidatorBean implements CustomerDiscountItemValidatorLocal{
    @PersistenceContext  
    private EntityManager em;

    public void validate(CustomerDiscountItem entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        if ( entity.getProduct()!=null ){
            //unique product 
            Query q = em.createNamedQuery("CustomerDiscountItem.findByProduct");
            q.setParameter("product", entity.getProduct());
            q.setParameter("parentDataObjectId", entity.getParentId());
            if ( !ValidationUtil.checkUnique(q.getResultList(), entity))
                ve.addMessage("product", "CustomerDiscountItem.err.productInUse");
        }else {
            //unique category 
            Query q = em.createNamedQuery("CustomerDiscountItem.findByCategory");
            q.setParameter("category", entity.getCategory());
            q.setParameter("parentDataObjectId", entity.getParentId());
            if ( !ValidationUtil.checkUnique(q.getResultList(), entity))
                ve.addMessage("product", "CustomerDiscountItem.err.categoryInUse");
        }
        
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
 
}
