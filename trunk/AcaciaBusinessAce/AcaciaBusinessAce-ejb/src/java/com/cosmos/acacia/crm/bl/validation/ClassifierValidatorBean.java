package com.cosmos.acacia.crm.bl.validation;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.validation.ValidationException;
import javax.persistence.NoResultException;

/**
 * @author	Bozhidar Bozhanov
 */
@Stateless
public class ClassifierValidatorBean implements ClassifierValidatorLocal {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private AcaciaSessionLocal session;

    @Override
    public void validate(Classifier entity) throws ValidationException {
        ValidationException ve = new ValidationException();

        //unique name
        Query q = em.createNamedQuery("Classifier.findByCode");
        q.setParameter("code", entity.getClassifierCode());
        q.setParameter("deleted", false);
        q.setParameter("parentId", session.getOrganization().getId());

        try {
            q.getSingleResult();
        } catch(NoResultException ex) {
            ve.addMessage("Classifier.err.codeInUse");
        }

        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
}
