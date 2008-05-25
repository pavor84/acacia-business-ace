package com.cosmos.acacia.crm.bl.contactbook.validation;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import com.cosmos.acacia.crm.data.Passport;
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
public class PassportValidatorBean implements PassportValidatorLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(Passport entity) throws ValidationException {
        ValidationException ve = new ValidationException();

        //unique number
        Query q = em.createNamedQuery("Passport.findByNumber");

        q.setParameter("number", entity.getPassportNumber());

        if ( !checkUnique(q.getResultList(), entity))
            ve.addMessage("passportNumber", "Passport.numberInUse");

        if (entity.getIssueDate().getTime() >= entity.getExpirationDate().getTime())
            ve.addMessage("Passport.incorrectDates");
        
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }

}
