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

    List<BankDetail> getBankDetails(DataObject parent);

    EntityProperties getBankDetailEntityProperties();

    BankDetail newBankDetail();

    BankDetail saveBankDetail(BankDetail bankDetail, DataObject parentDataObject);

    int deleteBankDetail(BankDetail bankDetail);
    
    List<Person> getBankContacts(DataObject parentDataObject);
    
    List<DbResource> getCurrencies();
    
}
