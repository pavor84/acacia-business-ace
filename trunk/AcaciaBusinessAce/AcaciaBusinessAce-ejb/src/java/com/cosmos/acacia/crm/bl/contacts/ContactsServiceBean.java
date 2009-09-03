/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contacts;

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
}
