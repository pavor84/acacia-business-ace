/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.beansbinding.EntityProperties;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author miro
 */
@Local
public interface EntityStoreManagerLocal
    extends EntityStoreManagerRemote
{

    void persist(EntityManager entityManager, Object entity);

    void persist(Object entity);
    
    void remove(EntityManager entityManager, Object entity);

    void remove(Object entity);

    EntityProperties getEntityProperties(Class entityClass);
}
