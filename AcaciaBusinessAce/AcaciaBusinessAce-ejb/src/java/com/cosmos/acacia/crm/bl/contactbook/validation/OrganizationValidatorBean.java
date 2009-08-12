package com.cosmos.acacia.crm.bl.contactbook.validation;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	26.04.2008
 * @author	Bozhdiar Bozhanov
 *
 */
@Stateless
public class OrganizationValidatorBean implements OrganizationValidatorLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(Organization entity) throws ValidationException {
        ValidationException ve = new ValidationException();

        //unique name
        Query q = em.createNamedQuery("Organization.findByName");

        q.setParameter("organizationName", entity.getOrganizationName());

        if ( !checkUnique(q.getResultList(), entity))
            ve.addMessage("organizationName", "Organization.err.nameInUse");

        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }

}
