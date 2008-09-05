/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.validation.PositionTypeValidatorLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.validation.ValidationException;
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

    @Override
    @SuppressWarnings("unchecked")
    public List<PositionType> getPositionTypes(Class ownerClass, BigInteger parentId) throws Exception
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
        q.setParameter("parentDataObjectId", parentId);

        return new ArrayList<PositionType>(q.getResultList());
    }

    public EntityProperties getPositionTypeEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(PositionType.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public PositionType newPositionType(BigInteger parentId) {
        PositionType positionType = new PositionType();
        positionType.setParentId(parentId);
        return positionType;
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

    @Override
    public PositionType updateParent(PositionType newParent,
            PositionType newChild) {

        if (newParent!= null){
            ValidationException ve = new ValidationException();

            // check cyclic parents
            PositionType ancestor = newParent;
            while (ancestor != null){
                if ( ancestor.equals(newChild) ){
                    ve.addMessage("parentPositionType", "TreeItem.err.cyclicParent");
                    break;
                }
                ancestor = ancestor.getParentPositionType();
            }

            // if we have validation messages - throw the exception since not everything is OK
            if ( !ve.getMessages().isEmpty() )
                throw ve;

            //merge the parent
            newParent = em.merge(newParent);
        }

        //newParent may be null here - but no problem
        newChild.setParentPositionType(newParent);
        esm.persist(em, newChild);

        return newChild;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PositionType> getInternalOrganizationPositionTypes(
            BigInteger parentId) {

        Query q = em.createNamedQuery("PositionType.findInternalOrganizationPositionTypes");
        q.setParameter("deleted", false);
        q.setParameter("parentDataObjectId", parentId);

        return new ArrayList<PositionType>(q.getResultList());
    }

    @Override
    public void deletePositionTypes(List<PositionType> positionTypes) {
        for (PositionType positionType : positionTypes) {
            esm.remove(em, positionType);
        }
    }
}
