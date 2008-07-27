/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;

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
    
    int remove(EntityManager entityManager, Object entity);

    /**
     * Returns entity properties for all annotated fields.
     * @param entityClass
     * @return
     */
    EntityProperties getEntityProperties(Class entityClass);
    
    /**
     * Create property details for a single property.
     * The property annotated can be either on a field or on a getter method.
     * @param entityClass
     * @param propertyName
     * @param position - any number. will be used to sort the property details according to the others
     * @return
     */
    PropertyDetails getPropertyDetails(Class entityClass, String propertyName, int position);
}
