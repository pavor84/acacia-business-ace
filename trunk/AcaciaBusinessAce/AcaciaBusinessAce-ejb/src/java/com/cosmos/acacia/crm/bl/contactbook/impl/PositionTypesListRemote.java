/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling position types
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface PositionTypesListRemote {

    /**
     * Lists all position types for an owner class (Person or Organization)
     *
     * @param ownerClass
     * @return list of position types
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    List<PositionType> getPositionTypes(Class ownerClass) throws Exception;

    /**
     * Gets the EntityProperties of PositionType
     *
     * @return the entity properties
     */
    EntityProperties getPositionTypeEntityProperties();

    /**
     * Creates a new PositionType
     *
     * @return the newly created position type
     */
    PositionType newPositionType();

    /**
     * Saves a PositionType for a owner class (Person or Organization)
     *
     * @param positionType
     * @param ownerClass
     * @return the saved position type
     */
    @SuppressWarnings("unchecked")
    PositionType savePositionType(PositionType positionType, Class ownerClass);

    /**
     * Deletes a PositionType
     *
     * @param positionType
     * @return the version of the deleted position type
     */
    int deletePositionType(PositionType positionType);

}
