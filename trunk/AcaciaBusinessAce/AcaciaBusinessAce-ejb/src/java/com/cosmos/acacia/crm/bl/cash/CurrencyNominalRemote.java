package com.cosmos.acacia.crm.bl.cash;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.accounting.CurrencyNominal;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	01.05.2009
 * @author	Petar Milev
 *
 */
@Remote
public interface CurrencyNominalRemote {

    /**
     * Create entity properties object for listing object from this type.
     * @return not null
     */
    EntityProperties getListingEntityProperties();

    /**
     * Return all currencyNominals.
     * @return not null list
     */
    List<CurrencyNominal> listCurrencyNominals();

    /**
     * Deletes the currencyNominal, - if the integrity is violated, throws an {@link ValidationException} 
     * @param CurrencyNominal - not null
     */
    void deleteCurrencyNominal(CurrencyNominal currencyNominal);

    /**
     * Create new instance of {@link CurrencyNominal}.
     * 
     * @return not null
     */
    CurrencyNominal newCurrencyNominal();

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
    CurrencyNominal saveCurrencyNominal(CurrencyNominal entity);

    /**
     * Get all available nominal for this currency.
     * @param currency
     * @return
     */
    List<CurrencyNominal> getNominals(Currency currency);
}
