package com.cosmos.acacia.app;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.util.AcaciaProperties;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Remote
public interface AcaciaSessionRemote {

//    DataObject getLoginOrganizationDataObject();

    DataObject getDataObject(BigInteger dataObjectId);

    User getUser();

    Organization getOrganization();

    /**
     * Retrieve the login branch of the current user.
     * @return
     */
    Address getBranch();

    /**
     * Get the currently logged person.
     * @return
     */
    Person getPerson();

    /**
     * Gets the list of all data object types
     * @return
     */
    List<DataObjectType> getDataObjectTypes();

    /**
    * Set the current login branch for the session.
    * @param branch
    */
    void setBranch(Address branch);

    /**
     * Sets if the user be presented data from all branches
     * @param value
     */
    void setViewDataFromAllBranches(Boolean value);

    /**
     * Gets if the user be presented data from all branches
     *
     * @return
     */
    Boolean getViewDataFromAllBranches();

    /**
     * Retrieves the parameters for all possible levels. If the client is null
     * then the Client Level will not be retreived.
     * @param client
     * @return
     */
    AcaciaProperties getProperties(BusinessPartner client);

    void saveProperties(AcaciaProperties properties);

    boolean isAdministrator();

    boolean isSystemAdministrator();

    boolean isOrganizationAdministrator();

    boolean isBranchAdministrator();

    Classifier getClassifier(String classifierCode);
}

