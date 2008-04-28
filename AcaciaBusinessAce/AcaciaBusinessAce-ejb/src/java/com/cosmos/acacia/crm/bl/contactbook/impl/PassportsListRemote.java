package com.cosmos.acacia.crm.bl.contactbook.impl;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Passport;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling passports
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface PassportsListRemote {


    /**
     * Lists all passports for a parent (Person data object)
     *
     * @param parentDataObject
     * @return list of passports
     */
    List<Passport> getPassports(DataObject parentDataObject);

    /**
     * Gets the EntityProperties for Passport
     *
     * @return the entity properties
     */
    EntityProperties getPassportEntityProperties();

    /**
     * Creates a new Passport
     *
     * @return the newly created passport
     */
    Passport newPassport();

    /**
     * Saves a Passport for a specific parent (Person data object)
     *
     * @param passport
     * @param parentDataObject
     * @return the saved passport
     */
    Passport savePassport(Passport passport, DataObject parentDataObject);

    /**
     * Deletes a Passport
     * @param passport
     * @return the version of the deleted passport
     */
    int deletePassport(Passport passport);

    /**
     * Lists all passport types
     *
     * @return list of passport types
     */
    List<DbResource> getPassportTypes();
}
