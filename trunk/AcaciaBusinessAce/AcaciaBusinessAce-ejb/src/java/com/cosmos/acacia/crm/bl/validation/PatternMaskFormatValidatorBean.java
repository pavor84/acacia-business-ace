package com.cosmos.acacia.crm.bl.validation;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	30.03.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Stateless
public class PatternMaskFormatValidatorBean implements PatternMaskFormatValidatorLocal {
    @PersistenceContext  
    private EntityManager em;
    
    @Override
    public void validate(PatternMaskFormat entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //unique name 
        Query getByName = em.createNamedQuery("PatternMaskFormat.findByName");
        getByName.setParameter("patternName", entity.getPatternName());
        if ( !checkUnique(getByName.getResultList(), entity))
            ve.addMessage("patternName", "PatternMaskFormat.err.nameInUse");
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
}
