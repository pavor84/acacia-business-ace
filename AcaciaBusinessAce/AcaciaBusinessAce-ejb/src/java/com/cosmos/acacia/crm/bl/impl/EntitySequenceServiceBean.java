/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.EntitySequence;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class EntitySequenceServiceBean implements EntitySequenceServiceLocal {

    @PersistenceContext
    private EntityManager em;

    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public long nextValue(UUID parentEntityId, int dataObjectTypeId, Long initialValue) {
        lock.lock();
        try {
            EntitySequence entitySequence = getEntitySequence(parentEntityId, dataObjectTypeId);
            if(entitySequence == null) {
                entitySequence = new EntitySequence(parentEntityId, dataObjectTypeId);
                if(initialValue != null) {
                    entitySequence.setInitialValue(initialValue);
                }
            }

            Long sequenceValue;
            if ((sequenceValue = entitySequence.getSequenceValue()) == null) {
                Long value;
                if ((value = entitySequence.getInitialValue()) == null) {
                    if(initialValue != null) {
                        entitySequence.setInitialValue(initialValue);
                        sequenceValue = initialValue;
                    } else {
                        sequenceValue = 0L;
                    }
                } else {
                    sequenceValue = value;
                }
            }

            entitySequence.setSequenceValue(++sequenceValue);
            em.persist(entitySequence);

            return sequenceValue;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<EntitySequence> getEntitySequences(UUID entityId) {
        Query q = em.createNamedQuery("EntitySequence.findByEntityId");
        q.setParameter("entityId", entityId);
        return new ArrayList<EntitySequence>(q.getResultList());
    }

    @Override
    public EntitySequence getEntitySequence(UUID entityId, int dataObjectTypeId) {
        Query q = em.createNamedQuery("EntitySequence.findByEntityIdAndDataObjectTypeId");
        q.setParameter("entityId", entityId);
        q.setParameter("dataObjectTypeId", dataObjectTypeId);
        try {
            return (EntitySequence) q.getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    @Override
    public EntitySequence saveEntitySequence(EntitySequence entitySequence) {
        em.persist(entitySequence);
        return entitySequence;
    }

    @Override
    public void deleteEntitySequence(EntitySequence entitySequence) {
        em.remove(entitySequence);
    }


}
