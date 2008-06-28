package com.cosmos.acacia.crm.bl.contactbook;

import java.math.BigInteger;
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
     * @param parentDataObjectId
     * @return list of passports
     */
    List<Passport> getPassports(BigInteger parentDataObjectId);

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
     * @param parentDataObjectId
     * @return the saved passport
     */
    Passport savePassport(Passport passport, BigInteger parentDataObjectId);

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
