/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import com.cosmos.acacia.crm.data.BankDetail;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling bank details
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface BankDetailsListRemote {

    /**
     * Lists the bank details for a parent (an Address (branch) data object)
     *
     * @param parent
     * @return list of bank details
     */
    List<BankDetail> getBankDetails(DataObject parent);

    /**
     * Gets the EntithyProperties of BankDetail
     *
     * @return the entity properties
     */
    EntityProperties getBankDetailEntityProperties();

    /**
     * Creates a new, empty BankDetail
     *
     * @return the newly created BankDetail
     */
    BankDetail newBankDetail();

    /**
     * Saves a BankDetail belonging to a specified parent
     * (an Address (branch) data object)
     *
     * @param bankDetail
     * @param parentDataObject
     * @return the saved BankDetail
     */
    BankDetail saveBankDetail(BankDetail bankDetail, DataObject parentDataObject);

    /**
     * Deletes a BankDetail
     *
     * @param bankDetail
     * @return the version of the deleted BankDetail
     */
    int deleteBankDetail(BankDetail bankDetail);

    /**
     * Lists all persons for a specified parent (an Address (branch) data object)
     * In fact it lists all the corresponding persons to the retrieved
     * ContactPersons for the parent.
     *
     * @param parentDataObject
     * @return list of persons
     */
    List<Person> getBankContacts(DataObject parentDataObject);

    /**
     * Lists all currencies
     *
     * @return list of currencies
     */
    List<DbResource> getCurrencies();

}