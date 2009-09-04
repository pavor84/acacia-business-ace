/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contacts;

import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.entity.AbstractEntityService;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Miro
 */
@Stateless
public class ContactsServiceBean extends AbstractEntityService implements ContactsServiceRemote, ContactsServiceLocal {

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, Object... extraParameters) {
        return super.getEntities(entityClass, extraParameters);
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, Object... extraParameters) {
        return super.getEntityItems(entity, itemClass, extraParameters);
    }

    @Override
    public Person newPerson(BusinessPartner parentBusinessPartner) {
        Person person = new Person();
        person.setDefaultCurrency(Currency.EUR.getDbResource());
        person.setParentId(parentBusinessPartner.getBusinessPartnerId());
        return person;
    }
}
