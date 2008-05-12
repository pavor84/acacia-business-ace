package com.cosmos.acacia.crm.validation.impl;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.WarehouseProduct;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	05.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Stateless
public class WarehouseProductValidator implements WarehouseProductValidatorLocal {
    @PersistenceContext  
    private EntityManager em;

    /** @see com.cosmos.acacia.crm.validation.EntityValidator#validate(java.lang.Object)
     */
    @Override
    public void validate(WarehouseProduct entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //unique by address 
        Query getByProductAndWarehouse = em.createNamedQuery("WarehouseProduct.findByProductAndWarehouse");
        getByProductAndWarehouse.setParameter("product", entity.getProduct());
        getByProductAndWarehouse.setParameter("warehouse", entity.getWarehouse());
        
        if ( !checkUnique(getByProductAndWarehouse.getResultList(), entity))
            ve.addMessage("product", "WarehouseProduct.err.existingForProduct");
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }


}
