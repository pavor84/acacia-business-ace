package com.cosmos.acacia.crm.validation.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.crm.data.PurchaseOrder;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * 
 * Created	:	19.07.2008
 * @author	Petar Milev
 *
 */
@Stateless
public class PurchaseOrderValidator implements PurchaseOrderValidatorLocal {
    @PersistenceContext  
    private EntityManager em;
    
    /** @see com.cosmos.acacia.crm.validation.EntityValidator#validate(java.lang.Object)
     */
    @Override
    public void validate(PurchaseOrder entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
}
