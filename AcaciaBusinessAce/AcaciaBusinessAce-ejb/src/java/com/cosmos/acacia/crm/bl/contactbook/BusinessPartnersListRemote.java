package com.cosmos.acacia.crm.bl.contactbook;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.BusinessPartner;
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
    List<BusinessPartner> getBusinessPartners(BigInteger parentDataObjectId);

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

}
