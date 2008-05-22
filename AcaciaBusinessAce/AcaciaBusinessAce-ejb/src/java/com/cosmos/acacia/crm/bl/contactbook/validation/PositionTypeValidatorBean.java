package com.cosmos.acacia.crm.bl.contactbook.validation;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import com.cosmos.acacia.crm.data.PositionType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	26.04.2008
 * @author	Bozhdiar Bozhanov
 *
 */
@Stateless
public class PositionTypeValidatorBean implements PositionTypeValidatorLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(PositionType entity) throws ValidationException {
        ValidationException ve = new ValidationException();

        //unique name
        Query q = em.createNamedQuery("PositionType.findByName");

        q.setParameter("positionTypeName", entity.getPositionTypeName());

        if ( !checkUnique(q.getResultList(), entity))
            ve.addMessage("positionTypeName", "PositionType.err.nameInUse");

        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }

}
