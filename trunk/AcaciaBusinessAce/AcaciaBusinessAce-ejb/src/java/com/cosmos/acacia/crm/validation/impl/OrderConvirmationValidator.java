package com.cosmos.acacia.crm.validation.impl;

import java.util.Date;

import javax.ejb.Stateless;

import com.cosmos.acacia.crm.data.OrderConfirmation;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * 
 * Created	:	19.07.2008
 * @author	Petar Milev
 *
 */
@Stateless
public class OrderConvirmationValidator implements OrderConfirmationValidatorLocal {
    /** @see com.cosmos.acacia.crm.validation.EntityValidator#validate(java.lang.Object)
     */
    @Override
    public void validate(OrderConfirmation entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        Date from = entity.getShipDateFrom();
        Date to = entity.getShipDateTo();
//        if ( from!=null && to==null )
//            ve.addMessage("shipDateTo", "OrderConfirmation.err.requiredToDate");
//        else if ( from==null && to!=null )
//            ve.addMessage("shipDateFrom", "OrderConfirmation.err.requiredFromDate");
        
        if ( from!=null && to!=null && to.before(from)){
            ve.addMessage("shipDateTo", "OrderConfirmation.err.datesNotSequent");
        }
//        else if ( from==null && to==null )
//            ve.addMessage("shipDateTo", "OrderConfirmation.err.requiredDates");
        
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
