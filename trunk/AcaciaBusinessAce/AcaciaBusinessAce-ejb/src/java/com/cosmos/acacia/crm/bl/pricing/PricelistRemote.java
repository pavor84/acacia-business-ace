package com.cosmos.acacia.crm.bl.pricing;

import java.util.UUID;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.sales.Pricelist;
import com.cosmos.acacia.crm.data.sales.PricelistItem;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	10.07.2008
 * @author	Petar Milev
 *
 */
@Remote
public interface PricelistRemote {

    /**
     * Create entity properties object for listing object from this type.
     * @return not null
     */
    EntityProperties getListingEntityProperties();

    /**
     * Return all pricelists for a given parent.
     * @param parentDataObjectId - mandatory
     * @return not null list
     */
    List<Pricelist> listPricelists(UUID parentDataObjectId);

    /**
     * Deletes the pricelist, - if the integrity is violated, throws an {@link ValidationException} 
     * @param Pricelist - not null
     */
    void deletePricelist(Pricelist pricelist);

    /**
     * Create new instance of {@link Pricelist}.
     * 
     * @param parentDataObjectId - may be null
     * @return not null
     */
    Pricelist newPricelist(UUID parentDataObjectId);

    /**
     * Return entity properties for detailed view
     * @return not null
     */
    EntityProperties getDetailEntityProperties();

    /**
     * Save - {@link ValidationException} on failure
     * @param list
     * @return
     */
    Pricelist savePricelist(Pricelist list);

    /**
     * Return the entity properties for listing items.
     * @return
     */
    EntityProperties getItemsListEntityProperties();

    /**
     * List pricelist items for a given invoice
     * @param parentDataObjectId
     * @return
     */
    List<PricelistItem> getPricelistItems(UUID parentDataObjectId);

    /**
     * Delete pricelist item.
     * 
     * @param item
     */
    void deletePricelistItem(PricelistItem item);

    /**
     * Create new pricelist item for a given price list
     * @param parentDataObjectId
     * @return
     */
    PricelistItem newPricelistItem(UUID parentDataObjectId);

    /**
     * Save an item
     * @param entity
     * @return
     */
    PricelistItem savePricelistItem(PricelistItem entity);

    /**
     * Item details entity properties
     * @return
     */
    EntityProperties getItemDetailEntityProperties();

    /**
     * Save multiple items.
     * @param toSave
     */
    List<PricelistItem> savePricelistItems(List<PricelistItem> toSave);
}
