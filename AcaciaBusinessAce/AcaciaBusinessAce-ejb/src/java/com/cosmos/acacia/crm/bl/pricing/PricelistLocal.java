package com.cosmos.acacia.crm.bl.pricing;

import java.math.BigInteger;

import javax.ejb.Local;

/**
 * 
 * Created	:	10.07.2008
 * @author	Petar Milev
 *
 */
@Local
public interface PricelistLocal extends PricelistRemote{
    /**
     * Create the general price-list if not created.
     * Create entry for every product if some products are not included.
     * @param parentDataObjectId - the parent organization
     */
    public void updateGeneralPricelist(BigInteger parentDataObjectId);
}
