package com.cosmos.acacia.crm.bl.contactbook;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.beansbinding.EntityProperties;

/**
 *
 * Created	:	26.07.2008
 * @author	Petar Milev
 *
 */
@Remote
public interface BusinessPartnersListRemote {

    /**
     * List all business partner for parent
     * @param parentDataObjectId
     * @return
     */
    List<BusinessPartner> getBusinessPartners(UUID parentDataObjectId);

    List<BusinessPartner> getBusinessPartners(Classifier classifier);
    
    /**
     * Return the entity properties for showing in tables
     * @return
     */
    EntityProperties getListingEntityProperties();

    /**
     * Delete a business partner
     * @param businessPartner
     *
     */
    void deleteBusinessPartner(BusinessPartner businessPartner);

    /**
     * Get the contact persons of a given business partner.
     * @param customer
     * @return
     */
    List<ContactPerson> getContactPersons(BusinessPartner businessPartner);

    /**
     * Returns all partner classified as {@link Classifier#Customer} with positive laiabilites.
     * Every partner is mapped with his liability.
     * @return
     */
    Map<BusinessPartner, BigDecimal> getLiabilityCustomers();

    /**
     * Returns all partner classified as {@link Classifier#Customer} with prepaid amounts 
     * (negative laiabilites).
     * Every partner is mapped with his prepaid amount.
     * @return
     */
    Map<BusinessPartner, BigDecimal> getPrepaidCustomers();

}
