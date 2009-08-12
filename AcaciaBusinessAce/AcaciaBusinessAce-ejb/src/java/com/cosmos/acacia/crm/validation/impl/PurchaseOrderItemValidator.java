package com.cosmos.acacia.crm.validation.impl;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.purchase.PurchaseOrderItem;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * 
 * Created	:	19.07.2008
 * @author	Petar Milev
 *
 */
@Stateless
public class PurchaseOrderItemValidator implements PurchaseOrderItemValidatorLocal {
    @PersistenceContext  
    private EntityManager em;
    
    /** @see com.cosmos.acacia.crm.validation.EntityValidator#validate(java.lang.Object)
     */
    @Override
    public void validate(PurchaseOrderItem entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //unique product 
        Query q = em.createNamedQuery("PurchaseOrderItem.findForOrderAndProduct");
        q.setParameter("product", entity.getProduct());
        q.setParameter("parentDataObjectId", entity.getParentId());
        if ( !checkUnique(q.getResultList(), entity))
            ve.addMessage("product", "PurchaseOrderItem.err.alreadyForProduct");
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
}
