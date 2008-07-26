package com.cosmos.acacia.crm.validation.impl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.crm.data.OrderConfirmationItem;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * 
 * Created	:	19.07.2008
 * @author	Petar Milev
 *
 */
@Stateless
public class OrderConfirmationItemValidator implements OrderConfirmationItemValidatorLocal {
    @PersistenceContext  
    private EntityManager em;
    
    /** @see com.cosmos.acacia.crm.validation.EntityValidator#validate(java.lang.Object)
     */
    @Override
    public void validate(OrderConfirmationItem entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        Date from = entity.getShipDateFrom();
        Date to = entity.getShipDateTo();
        if ( to!=null && from!=null && to.before(from)){
            ve.addMessage("shipDateTo", "OrderConfirmationItem.err.datesNotSequent");
        }
        
        //unique product 
//        Query q = em.createNamedQuery("OrderConfirmationItem.findForOrderAndProduct");
//        q.setParameter("product", entity.getProduct());
//        q.setParameter("parentDataObjectId", entity.getParentId());
//        if ( !checkUnique(q.getResultList(), entity))
//            ve.addMessage("product", "OrderConfirmationItem.err.alreadyForProduct");
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
}
