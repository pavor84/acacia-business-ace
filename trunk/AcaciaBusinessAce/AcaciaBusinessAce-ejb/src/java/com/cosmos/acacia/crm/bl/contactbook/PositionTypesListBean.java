/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

import com.cosmos.acacia.crm.bl.contactbook.validation.PositionTypeValidatorLocal;
import com.cosmos.acacia.crm.bl.impl.*;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.beansbinding.EntityProperties;

/**
 * The implementation of handling locations (see interface for more info)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class PositionTypesListBean implements PositionTypesListRemote, PositionTypesListLocal {

    protected static Logger log = Logger.getLogger(PositionTypesListBean.class);

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private PositionTypeValidatorLocal validator;
    
    @SuppressWarnings("unchecked")
    public List<PositionType> getPositionTypes(Class ownerClass) throws Exception
    {
        log.info("Position type owner type: " + ownerClass);
        Query q = null;
        if (ownerClass == Person.class) {
           q = em.createNamedQuery("PositionType.findPersonPositionTypes");
        }

        if (ownerClass == Organization.class) {
           q = em.createNamedQuery("PositionType.findOrganizationPositionTypes");
        }

        if (q == null) {
            throw new Exception("Unsuported parent for position types." +
                    " Only Organization and Person allowed");
        }
        q.setParameter("deleted", false);
        return new ArrayList<PositionType>(q.getResultList());
    }

    public EntityProperties getPositionTypeEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(PositionType.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public PositionType newPositionType() {
        return new PositionType();
    }

    @SuppressWarnings("unchecked")
    public PositionType savePositionType(PositionType positionType, Class ownerClass) {
        validator.validate(positionType);
        
        if (ownerClass != null) {
            if (ownerClass == Person.class)
                positionType.setOwnerType('P');
            if (ownerClass == Organization.class)
                positionType.setOwnerType('O');
        }
        esm.persist(em, positionType);
        return positionType;
    }

    public int deletePositionType(PositionType positionType) {
        return esm.remove(em, positionType);
    }
}