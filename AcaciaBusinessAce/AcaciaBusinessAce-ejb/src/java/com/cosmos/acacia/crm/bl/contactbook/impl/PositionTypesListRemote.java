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

    List<PositionType> getPositionTypes(Class ownerClass) throws Exception;
            
    EntityProperties getPositionTypeEntityProperties();

    PositionType newPositionType();

    PositionType savePositionType(PositionType positionType, Class ownerClass);

    int deletePositionType(PositionType positionType);

}
