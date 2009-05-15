package com.cosmos.acacia.crm.bl.cash;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.BanknoteQuantity;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	02.05.2009
 * @author	Petar Milev
 *
 */
@Remote
public interface BanknoteQuantityRemote {

    /**
     * Create entity properties object for listing object from this type.
     * @return not null
     */
    EntityProperties getListingEntityProperties();

    /**
     * Return all banknoteQuantitys.
     * @param parentId 
     * @return not null list
     */
    List<BanknoteQuantity> listBanknoteQuantitys(BigInteger parentId);

    /**
     * Deletes the banknoteQuantity, - if the integrity is violated, throws an {@link ValidationException} 
     * @param BanknoteQuantity - not null
     */
    void deleteBanknoteQuantity(BanknoteQuantity banknoteQuantity);

    /**
     * Create new instance of {@link BanknoteQuantity}.
     * 
     * @return not null
     */
    BanknoteQuantity newBanknoteQuantity(BigInteger parentId);

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
    BanknoteQuantity saveBanknoteQuantity(BanknoteQuantity entity);
}
