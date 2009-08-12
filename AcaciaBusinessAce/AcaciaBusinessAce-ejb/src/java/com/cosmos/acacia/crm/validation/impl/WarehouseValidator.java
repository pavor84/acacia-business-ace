package com.cosmos.acacia.crm.validation.impl;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Stateless
public class WarehouseValidator implements WarehouseValidatorLocal {
    @PersistenceContext  
    private EntityManager em;
    
    /** @see com.cosmos.acacia.crm.validation.EntityValidator#validate(java.lang.Object)
     */
    @Override
    public void validate(Warehouse entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //unique by address 
        Query getByAddress = em.createNamedQuery("Warehouse.findByAddress");
        getByAddress.setParameter("parentDataObjectId", entity.getParentId());
        getByAddress.setParameter("address", entity.getAddress());
        if ( !checkUnique(getByAddress.getResultList(), entity))
            ve.addMessage("address", "Warehouse.err.alreadyWareOnThisAddress");
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
}
