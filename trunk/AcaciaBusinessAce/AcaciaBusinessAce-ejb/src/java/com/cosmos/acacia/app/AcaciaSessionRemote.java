package com.cosmos.acacia.app;

import java.util.UUID;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Expression;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.util.AcaciaProperties;
import java.util.Set;
import org.jdesktop.application.ApplicationAction;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Remote
public interface AcaciaSessionRemote {

    Set<ApplicationAction> getApplicationActions();
//    DataObject getLoginOrganizationDataObject();

    DataObject getDataObject(UUID dataObjectId);

    User getUser();

    Organization getOrganization();

    UserOrganization getUserOrganization();

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
     * Get the currently logged contact person
     * @return
     */
    ContactPerson getContactPerson();

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

    String getExpression(String expressionKey);

    String getExpression(Class beanClass, String propertyName);

    String getExpression(Object bean, String propertyName);

    Expression saveExpression(String expressionKey, String expressionValue);

    Expression saveExpression(Class beanClass, String propertyName, String expressionValue);

    void deleteExpression(String expressionKey);

    void deleteExpression(Class beanClass, String propertyName);
}

