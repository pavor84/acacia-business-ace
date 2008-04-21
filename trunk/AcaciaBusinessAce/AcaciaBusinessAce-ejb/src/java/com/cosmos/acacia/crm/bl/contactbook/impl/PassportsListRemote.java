/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Passport;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling location-related entities,
 * namely Countries, Cities and Addresses
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface PassportsListRemote {

    // Methods for handling countries
    List<Passport> getPassports(DataObject parentDataObject);

    EntityProperties getPassportEntityProperties();

    Passport newPassport();

    Passport savePassport(Passport passport, DataObject parentDataObject);

    int deletePassport(Passport passport);
    
    List<DbResource> getPassportTypes();
}
