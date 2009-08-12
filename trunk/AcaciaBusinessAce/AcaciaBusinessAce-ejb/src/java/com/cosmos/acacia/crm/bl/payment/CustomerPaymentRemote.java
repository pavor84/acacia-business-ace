package com.cosmos.acacia.crm.bl.payment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.CustomerPayment;
import com.cosmos.acacia.crm.data.CustomerPaymentMatch;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	06.03.2009
 * @author	Petar Milev
 *
 */
@Remote
public interface CustomerPaymentRemote {

    /**
     * Create entity properties object for listing object from this type.
     * @return not null
     */
    EntityProperties getListingEntityProperties();

    /**
     * Return all customerPayments for a given parent.
     * @param parentDataObjectId - mandatory
     * @return not null list
     */
    List<CustomerPayment> listCustomerPayments(BigInteger parentDataObjectId);

    /**
     * Deletes the customerPayment, - if the integrity is violated, throws an {@link ValidationException} 
     * @param CustomerPayment - not null
     */
    void deleteCustomerPayment(CustomerPayment customerPayment);

    /**
     * Create new instance of {@link CustomerPayment}.
     * 
     * @param parentDataObjectId - may be null
     * @return not null
     */
    CustomerPayment newCustomerPayment(BigInteger parentDataObjectId);

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
    CustomerPayment saveCustomerPayment(CustomerPayment entity);

    /**
     * List<CustomerPayment> getConfirmedDocuments();
     * @param entity
     * @return
     */
    CustomerPayment completeCustomerPayment(CustomerPayment entity);
    
    /**
     * Get all confirmed documents.
     * @return
     */
    List<CustomerPayment> getConfirmedDocuments();

    /**
     * Get all pending payments (the ones that are not fully matched)
     * @param includePartlyMatched - get the payments that have partial matching
     * @param includeUnmatched - get the payments that have no match amounts
     * @return
     */
    List<CustomerPayment> getPendingPayments(Boolean includePartlyMatched, Boolean includeUnmatched);

    /**
     * Matches the invoice and the payment with the given amount.
     * Updates the invoices and the payment and creates {@link CustomerPaymentMatch} record which returns.
     * @param customerPayment
     * @param invoice
     * @param matchAmount
     * @return
     */
    CustomerPaymentMatch matchPayment(CustomerPayment customerPayment, Invoice invoice, BigDecimal matchAmount);

    /**
     * Returns all {@link CustomerPaymentMatch}s for the given invoice. The list is sorted by match number.
     * @param invoice
     * @return
     */
    List<CustomerPaymentMatch> getPaymentMatchList(Invoice invoice);
}
