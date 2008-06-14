package com.cosmos.acacia.crm.bl.contactbook.validation;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	26.04.2008
 * @author	Bozhdiar Bozhanov
 *
 */
@Stateless
public class ContactPersonValidatorBean implements ContactPersonValidatorLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(ContactPerson entity) throws ValidationException {
        ValidationException ve = new ValidationException();

        //unique name
        Query q = em.createNamedQuery("ContactPerson.findByPersonAndTypeAndParentDataObject");
        q.setParameter("person", entity.getContact());
        q.setParameter("parentDataObjectId", entity.getParentId());
        q.setParameter("positionType", entity.getPositionType());

        if ( !checkUnique(q.getResultList(), entity))
            ve.addMessage("ContactPerson.err.exists");

        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }

}
