package com.cosmos.acacia.crm.bl.cash;

import java.util.UUID;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.accounting.CashReconcile;
import com.cosmos.acacia.crm.data.accounting.CashReconcilePaymentSummary;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	28.04.2009
 * @author	Petar Milev
 *
 */
@Remote
public interface CashReconcileRemote {

    /**
     * Create entity properties object for listing object from this type.
     * @return not null
     */
    EntityProperties getListingEntityProperties();

    /**
     * Return all cashReconciles for a given parent.
     * @param parentDataObjectId - mandatory
     * @return not null list
     */
    List<CashReconcile> listCashReconciles(UUID parentDataObjectId);

    /**
     * Deletes the cashReconcile, - if the integrity is violated, throws an {@link ValidationException} 
     * @param CashReconcile - not null
     */
    void deleteCashReconcile(CashReconcile cashReconcile);

    /**
     * Create new instance of {@link CashReconcile}.
     * 
     * @param parentDataObjectId - may be null
     * @return not null
     */
    CashReconcile newCashReconcile(UUID parentDataObjectId);

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
    CashReconcile saveCashReconcile(CashReconcile entity);

    /**
     * List<CashReconcile> getConfirmedDocuments();
     * @param entity
     * @return
     */
    CashReconcile completeCashReconcile(CashReconcile entity);

    /**
     * @return
     */
    List<EndBalance> getEndBalances(CashReconcile entity);

    EntityProperties getPaymentSummaryListDetails();

    Set<CashReconcilePaymentSummary> getPaymentSummaries(CashReconcile entity);
}
