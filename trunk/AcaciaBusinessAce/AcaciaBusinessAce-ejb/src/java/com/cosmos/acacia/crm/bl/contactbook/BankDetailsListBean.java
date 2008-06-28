/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigInteger;

/**
 * The implementation of handling locations (see interface for more info)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class BankDetailsListBean implements BankDetailsListRemote, BankDetailsListLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private AddressesListLocal contactPersonsManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<BankDetail> getBankDetails(BigInteger parentId) {
        if (parentId != null) {
            Query query = em.createNamedQuery("BankDetail.findByParentDataObjectAndDeleted");
            query.setParameter("parentDataObjectId", parentId);
            query.setParameter("deleted", false);

            return new ArrayList<BankDetail>(query.getResultList());
        }

        return new ArrayList<BankDetail>();
    }

    public EntityProperties getBankDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(BankDetail.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public BankDetail newBankDetail() {
        return new BankDetail();
    }

    public BankDetail saveBankDetail(BankDetail bankDetail, BigInteger parentDataObjectId)
    {
        bankDetail.setParentId(parentDataObjectId);

        if (bankDetail.getDataObject() == null){
            DataObject dataObject = new DataObject();
            dataObject.setParentDataObjectId(parentDataObjectId);
            bankDetail.setDataObject(dataObject);
        }

        esm.persist(em, bankDetail);
        return bankDetail;
    }

    public int deleteBankDetail(BankDetail bankDetail) {
        return esm.remove(em, bankDetail);
    }

    public List<Person> getBankContacts(BigInteger parentDataObjectId) {
        List<ContactPerson> contactPersons = contactPersonsManager.getContactPersons(parentDataObjectId);

        ArrayList<Person> persons = new ArrayList<Person>(contactPersons.size());

        for (ContactPerson contactPerson: contactPersons) {
            persons.add(contactPerson.getContact());
        }

        return persons;
    }

    public List<DbResource> getCurrencies() {
        return Currency.getDbResources();
    }
}
