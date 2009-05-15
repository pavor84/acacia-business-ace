package com.cosmos.acacia.crm.bl.cash;

import javax.ejb.Local;

/**
 * 
 * Created	:	01.05.2009
 * @author	Petar Milev
 *
 */
@Local
public interface CurrencyNominalLocal extends CurrencyNominalRemote{

    /**
     * Create or save some default nominals in the database if not existing.
     */
    void initCurrencyNominals();
}
