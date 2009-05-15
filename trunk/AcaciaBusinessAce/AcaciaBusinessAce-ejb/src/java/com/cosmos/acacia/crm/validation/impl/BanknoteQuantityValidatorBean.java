package com.cosmos.acacia.crm.validation.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.crm.data.BanknoteQuantity;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationUtil;
import com.cosmos.acacia.util.AcaciaUtils;

/**
 * 
 * Created	:	13.05.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class BanknoteQuantityValidatorBean implements BanknoteQuantityValidatorLocal{
    @PersistenceContext  
    private EntityManager em;

    public void validate(BanknoteQuantity entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //unique name
        List<BanknoteQuantity> banknoteList = (List<BanknoteQuantity>) AcaciaUtils.getResultList(em, 
            BanknoteQuantity.NQ_BY_CURR_NOMINAL, 
            "parentId", entity.getParentId(),
            "currencyNominal", entity.getCurrencyNominal());
        if ( !ValidationUtil.checkUnique(banknoteList, entity))
            ve.addMessage("name", "BanknoteQuantity.err.currencyNominalUsed");
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
 
}
