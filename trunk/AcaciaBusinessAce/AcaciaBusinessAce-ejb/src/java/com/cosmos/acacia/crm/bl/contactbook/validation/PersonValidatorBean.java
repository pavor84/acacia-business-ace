package com.cosmos.acacia.crm.bl.contactbook.validation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	26.04.2008
 * @author	Bozhdiar Bozhanov
 *
 */
@Stateless
public class PersonValidatorBean implements PersonValidatorLocal {

    @SuppressWarnings("unused")
    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(Person entity) throws ValidationException {
        ValidationException ve = new ValidationException();

        try {
            if (entity.getFirstName().length() < 1 || entity.getLastName().length() < 1)
                throw new Exception();
        } catch (Exception ex) {
            ve.addMessage("Person.err.namesRequired");
        }
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }

}
