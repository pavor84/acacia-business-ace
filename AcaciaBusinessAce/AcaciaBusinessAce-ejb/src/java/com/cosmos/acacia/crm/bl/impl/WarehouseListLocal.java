package com.cosmos.acacia.crm.bl.impl;

import java.math.BigInteger;

import javax.ejb.Local;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Local
public interface WarehouseListLocal extends WarehouseListRemote{
    /**
     * This number is used when generating the first number for a given document.
     * For example the first Purchase Order number issued by a warehouse with index = 7, will 
     * be 7*{@link #documentIndexMulitplicator}
     */
    BigInteger DOCUMENT_INDEX_MULTIPLICATOR = new BigInteger("100000000");
}
