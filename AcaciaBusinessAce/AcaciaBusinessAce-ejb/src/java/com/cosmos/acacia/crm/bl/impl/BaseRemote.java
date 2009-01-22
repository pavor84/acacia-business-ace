package com.cosmos.acacia.crm.bl.impl;

import java.math.BigInteger;
import java.util.List;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 * 
 * T - entity type
 * I - entity items type
 *
 */
public interface BaseRemote<T extends DataObjectBean, I extends DataObjectBean>{

    /**
     * Create entity properties object for listing object from this type.
     * @return not null
     */
    EntityProperties getListingEntityProperties();

    /**
     * Return all entities for a given parent.
     * @param parentDataObjectId - mandatory
     * @return not null list
     */
    List<T> list(BigInteger parentDataObjectId);

    /**
     * Deletes the entity, - if the integrity is violated, throws an {@link ValidationException} 
     * @param entity - not null
     */
    void delete(T entity);

    /**
     * Create new instance of {@link T}.
     * 
     * @param parentDataObjectId - may be null
     * @return not null
     */
    T newEntity(BigInteger parentDataObjectId);

    /**
     * Return entity properties for detailed view
     * @return not null
     */
    EntityProperties getDetailEntityProperties();

    /**
     * Save - {@link ValidationException} on failure
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * Return the entity properties for listing items.
     * @return
     */
    EntityProperties getItemsListEntityProperties();

    /**
     * List items for a given entity
     * @param parentDataObjectId
     * @return
     */
    List<I> listItems(BigInteger parentEntityId);

    /**
     * Delete item.
     * 
     * @param item
     */
    void deleteItem(I item);

    /**
     * Create new item for a given parent entity
     * @param parentDataObjectId
     * @return
     */
    I newItem(BigInteger parentEntityId);

    /**
     * Save an item
     * @param entity
     * @return
     */
    I saveItem(I item);

    /**
     * Item details entity properties
     * @return
     */
    EntityProperties getItemDetailEntityProperties();
}
