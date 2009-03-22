package com.cosmos.acacia.crm.validation.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.ProductPercentValue;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationUtil;

/**
 * 
 * Created	:	05.01.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class ProductPricingValueValidatorBean implements ProductPricingValueValidatorLocal{
    @PersistenceContext  
    private EntityManager em;

    public void validate(ProductPercentValue entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //unique name 
        Query getByName = em.createNamedQuery("ProductPercentValue.findByNameTypeNotDeleted");
        getByName.setParameter("name", entity.getName());
        getByName.setParameter("parentDataObjectId", entity.getParentId());
        getByName.setParameter("deleted", Boolean.FALSE);
        getByName.setParameter("type", entity.getType());
        
        if ( !ValidationUtil.checkUnique(getByName.getResultList(), entity))
            ve.addMessage("name", "ProductPercentValue.err.nameInUse");
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
 
}
