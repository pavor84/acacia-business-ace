package com.cosmos.acacia.crm.validation.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.sales.Pricelist;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationUtil;

/**
 * 
 * Created	:	05.01.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class PricelistValidatorBean implements PricelistValidatorLocal{
    @PersistenceContext  
    private EntityManager em;

    public void validate(Pricelist entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //unique name 
        Query getByName = em.createNamedQuery("Pricelist.findByNameNotDeleted");
        getByName.setParameter("name", entity.getPricelistName());
        getByName.setParameter("parentDataObjectId", entity.getParentId());
        getByName.setParameter("deleted", Boolean.FALSE);
        if ( !ValidationUtil.checkUnique(getByName.getResultList(), entity))
            ve.addMessage("name", "Pricelist.err.nameInUse");
        
        if ( entity.isForPeriod() ){
            //if for period - require period start date
            if ( entity.getActiveFrom()==null ){
                ve.addMessage("activeFrom", "Pricelist.err.activeFromRequired");
            }
            
            //if active to is set - make sure it's after 'active from'
            if ( entity.getActiveTo()!=null && entity.getActiveFrom()!=null ){
                if ( entity.getActiveTo().before(entity.getActiveFrom()) ){
                    ve.addMessage("activeTo", "Pricelist.err.activeToBefore");
                }
            }
        }
        
        if ( entity.getMinTurnover()!=null ){
            //if min. turnover is set, make sure we have the currency and months
            if ( entity.getCurrency()==null ){
                ve.addMessage("currency", "Pricelist.err.currencyNeeded");
            }
            if ( entity.getTurnoverMonths()==null ){
                ve.addMessage("months", "Pricelist.err.monthsNeeded");
            }
        }
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
 
}
