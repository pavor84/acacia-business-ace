package com.cosmos.acacia.crm.bl.contactbook.validation;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.CommunicationContact;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	26.04.2008
 * @author	Bozhdiar Bozhanov
 *
 */
@Stateless
public class CommunicationContactValidatorBean implements CommunicationContactValidatorLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(CommunicationContact entity) throws ValidationException {
        ValidationException ve = new ValidationException();

        //unique name
        Query q = em.createNamedQuery("CommunicationContact.findByTypeAndContactPersonAndParentDataObject");
        q.setParameter("communicationType", entity.getCommunicationType());
        q.setParameter("contactPerson", entity.getContactPerson());
        q.setParameter("parentDataObject", entity.getDataObject().getParentDataObject());

        if ( !checkUnique(q.getResultList(), entity))
            ve.addMessage("CommunicationContact.err.exists");

        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }

}
