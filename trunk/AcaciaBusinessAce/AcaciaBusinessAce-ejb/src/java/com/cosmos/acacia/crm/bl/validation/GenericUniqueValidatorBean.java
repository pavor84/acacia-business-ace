package com.cosmos.acacia.crm.bl.validation;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import com.cosmos.acacia.crm.validation.ValidationException;
import java.lang.reflect.Method;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;


public class GenericUniqueValidatorBean<E> implements GenericUniqueValidatorLocal<E> {

    protected static Logger log = Logger.getLogger(GenericUniqueValidatorBean.class);
    
    @PersistenceContext  
    private EntityManager em;
     
    @Override
    public void validate(E entity) throws ValidationException {
        log.info("Call the validate method with a (E, String) attributes");
    }

    @Override
    public void validate(E entity, String param) {
         ValidationException ve = new ValidationException();

        //unique name
        String prefix = entity.getClass().getSimpleName();
        String modifiedParam = param.substring(0, 1).toUpperCase() + param.substring(1);
        Query q = em.createNamedQuery(prefix + "." + "findBy" + modifiedParam);

        Object paramValue = null;
        
        try {
            Method m = entity.getClass().getDeclaredMethod("get" + modifiedParam);
            paramValue = m.invoke(entity);
        } catch (Exception ex) {
            log.error("Error in invoking getter method", ex);
        }
        
        q.setParameter(param, paramValue);

        if ( !checkUnique(q.getResultList(), entity))
            ve.addMessage("positionTypeName", "PositionType.err.nameInUse");

        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }

    
}
