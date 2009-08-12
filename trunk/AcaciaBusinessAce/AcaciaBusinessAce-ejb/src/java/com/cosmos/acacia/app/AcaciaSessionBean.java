package com.cosmos.acacia.app;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListLocal;
import com.cosmos.acacia.crm.bl.impl.ClassifiersLocal;
import static com.cosmos.acacia.app.SessionContext.BRANCH_KEY;
import static com.cosmos.acacia.app.SessionContext.PERSON_KEY;
import static com.cosmos.acacia.app.SessionContext.CONTACT_PERSON_KEY;

import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.Expression;
import com.cosmos.acacia.util.AcaciaProperties;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.bl.users.RightsManagerLocal;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.users.Right;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.properties.DbProperty;
import com.cosmos.acacia.crm.enums.PermissionCategory;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.security.AccessLevel;
import com.cosmos.acacia.util.AcaciaPropertiesImpl;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import javax.ejb.EJB;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 * State-less service to use for session access.
 * Everything related to operating the session goes through here.
 * No other service should use {@link SessionRegistry} or {@link SessionContext}.
 *
 * The work of this service depends also on {@link SessionFacadeBean}. The latter is needed
 * to bind a {@link SessionContext} instance to the current thread of execution.
 * If this is not done, the behavior of {@link AcaciaSessionBean} is not defined.
 *
 */
@Stateless
public class AcaciaSessionBean implements AcaciaSessionRemote, AcaciaSessionLocal {

    private static final Logger logger =
        Logger.getLogger(AcaciaSessionBean.class);

    @PersistenceContext
    private EntityManager em;

    @EJB
    private RightsManagerLocal rightsManager;

    @EJB
    private ClassifiersLocal classifiersManager;
    @EJB
    private AddressesListLocal addressService;

    private final ReentrantLock sublevelLock = new ReentrantLock();

    @Override
    public UUID login(User user) {
        //create and register session
        UUID sessionId = registerNewSession();

        SessionRegistry.getSession().setValue(SessionContext.USER_KEY, user);

        return sessionId;
    }

    private UUID registerNewSession() {
        //create a session
        UUID sessionId = SessionRegistry.getInstance().createNewSession();
        //acquire the instance
        SessionContext session = SessionRegistry.getSession(sessionId);
        //bind to current thread
        SessionRegistry.setLocalSession(session);

        return sessionId;
    }

    @Override
    public DataObject getDataObject(BigInteger dataObjectId)
    {
        return em.find(DataObject.class, dataObjectId);
    }

    @Override
    public void setOrganization(Organization organization) {
        organization.setOwn(true);
        SessionRegistry.getSession().setValue(SessionContext.ORGANIZATION_KEY, organization);
    }

    @Override
    public Organization getOrganization() {
        return (Organization) SessionRegistry.getSession().getValue(SessionContext.ORGANIZATION_KEY);
    }

    @Override
    public User getUser() {
        try {
            return (User) SessionRegistry.getSession().getValue(SessionContext.USER_KEY);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void setBranch(Address branch) {
        getSession().setValue(BRANCH_KEY, branch);
    }

    private SessionContext getSession(){
        return SessionRegistry.getSession();
    }

    @Override
    public Address getBranch() {
        try {
            return (Address) getSession().getValue(BRANCH_KEY);
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public Person getPerson() {
        return (Person) getSession().getValue(PERSON_KEY);
    }

    @Override
    public ContactPerson getContactPerson() {
        ContactPerson contactPerson;
        if((contactPerson = (ContactPerson)getSession().getValue(CONTACT_PERSON_KEY)) != null) {
            return contactPerson;
        }

        contactPerson = addressService.getContactPerson(getBranch(), getPerson());
        getSession().setValue(CONTACT_PERSON_KEY, contactPerson);

        return contactPerson;
    }

    @Override
    public void setPerson(Person person) {
        getSession().setValue(PERSON_KEY, person);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DataObjectType> getDataObjectTypes() {
        Query q = em.createNamedQuery("DataObjectType.listAll");
        return new ArrayList<DataObjectType>(q.getResultList());
    }

    @Override
    public Boolean getViewDataFromAllBranches() {
        return (Boolean) getSession().getValue(SessionContext.VIEW_DATA_FROM_ALL_BRANCHES_KEY);
    }

    @Override
    public void setViewDataFromAllBranches(Boolean value) {
        getSession().setValue(SessionContext.VIEW_DATA_FROM_ALL_BRANCHES_KEY, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Right> getGeneralRights() {
        return (Set<Right>) getSession().getValue(SessionContext.GENERAL_RIGHTS_KEY);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Right> getSpecialPermissions() {
        return (Set<Right>) getSession().getValue(SessionContext.SPECIAL_PERMISSIONS_KEY);
    }

    @Override
    public void setGeneralRights(Set<Right> rights) {
        getSession().setValue(SessionContext.GENERAL_RIGHTS_KEY, rights);
    }

    @Override
    public void setSpecialPermissions(Set<Right> rights) {
        getSession().setValue(SessionContext.SPECIAL_PERMISSIONS_KEY, rights);
    }

    @Override
    public Object get(String key) {
        return SessionRegistry.getSession().getValue(key);
    }

    @Override
    public void put(String key, Object value) {
        SessionRegistry.getSession().setValue(key, value);
    }

    private BigInteger getRelatedObjectId(
            BusinessPartner client,
            AccessLevel level)
    {
        switch(level)
        {
            case System:
            case ParentChildOrganization:
                // ToDo: Put some real object because of FK constraint
                return BigInteger.ZERO;

            case Organization:
                return getOrganization().getId();

            case ParentChildBusinessUnit:
            case BusinessUnit:
                return getBranch().getId();

            case User:
                return getUser().getUserId();

            case Client:
            case ClientContact:
                if(client == null)
                    return null;
                return client.getPartnerId();

            case Session:
            case None:
                return null;

            default:
                throw new UnsupportedOperationException("Unknown level: " + level);
        }
    }

    @Override
    public AcaciaProperties getProperties(BusinessPartner client)
    {
        AcaciaPropertiesImpl properties =
                (AcaciaPropertiesImpl)get(SessionContext.ACACIA_PROPERTIES);
        if(properties == null)
        {
            properties = new AcaciaPropertiesImpl(AccessLevel.Session, BigInteger.ZERO);
            put(SessionContext.ACACIA_PROPERTIES, properties);
        }

        AcaciaPropertiesImpl mainProperties = properties;
        Query q = em.createNamedQuery("DbProperty.findByLevelAndRelatedObjectId");
        for(AccessLevel accessLevel : AccessLevel.PropertyLevels)
        {
            BigInteger relatedObjectId = getRelatedObjectId(client, accessLevel);
            if(relatedObjectId == null)
                continue;
            q.setParameter("accessLevel", accessLevel.name());
            q.setParameter("relatedObjectId", relatedObjectId);
            List<DbProperty> dbProperties = q.getResultList();
            AcaciaPropertiesImpl props = new AcaciaPropertiesImpl(accessLevel, relatedObjectId, dbProperties);
            mainProperties.setParentProperties(props);
            mainProperties = props;
        }

        return properties;
    }

    @Override
    public void saveProperties(AcaciaProperties properties)
    {
        AcaciaPropertiesImpl props = (AcaciaPropertiesImpl)properties;
        while(props != null)
        {
            if(AccessLevel.PropertyLevels.contains(props.getAccessLevel()) && props.isChanged())
            {
                AccessLevel accessLevel = props.getAccessLevel();
                BigInteger relatedObjectId = props.getRelatedObjectId();
                Query q = em.createNamedQuery("DbProperty.removeByLevelAndRelatedObjectIdAndPropertyKeys");
                q.setParameter("accessLevel", accessLevel.name());
                q.setParameter("relatedObjectId", relatedObjectId);
                q.setParameter("propertyKeys", props.getDeletedItems());
                q.executeUpdate();

                DbProperty property;
                for(String key : props.getNewItems())
                {
                    property = new DbProperty(accessLevel.name(), relatedObjectId, key);
                    property.setPropertyValue(props.getProperty(key));
                    em.persist(property);
                }
            }
            props = (AcaciaPropertiesImpl)props.getParentProperties();
        }

        ((AcaciaPropertiesImpl)properties).setParentProperties(null);
        put(SessionContext.ACACIA_PROPERTIES, properties);
    }

    @Override
    public boolean isAdministrator() {
        return rightsManager.isAllowed(PermissionCategory.Administration.getPermissions());
    }

    @Override
    public boolean isSystemAdministrator() {
        return rightsManager.isAllowed(SpecialPermission.SystemAdministrator);
    }

    @Override
    public boolean isOrganizationAdministrator() {
        return rightsManager.isAllowed(SpecialPermission.OrganizationAdministrator);
    }

    @Override
    public boolean isBranchAdministrator() {
        return rightsManager.isAllowed(SpecialPermission.BranchAdministrator);
    }

    @Override
    public Classifier getClassifier(String classifierCode) {
        return classifiersManager.getClassifier(classifierCode);
    }

    @Override
    public String getExpression(String expressionKey) {
        if(expressionKey == null) {
            throw new NullPointerException("The expressionKey can not be null.");
        }

        Query q = em.createNamedQuery("Expression.findByExpressionKey");
        q.setParameter("organizationId", getOrganization().getId());
        q.setParameter("expressionKey", expressionKey);
        try {
            Expression expression = (Expression)q.getSingleResult();
            return expression.getExpressionValue();
        } catch(NoResultException ex) {
            return null;
        }
    }

    @Override
    public String getExpression(Class beanClass, String propertyName) {
        if(propertyName == null) {
            return null;
        }

        return getExpression(getExpressionKey(beanClass, propertyName));
    }

    @Override
    public String getExpression(Object bean, String propertyName) {
        if(bean == null || propertyName == null) {
            return null;
        }

        return getExpression(bean.getClass(), propertyName);
    }

    @Override
    public String getExpressionKey(Class beanClass, String propertyName) {
        if(propertyName == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if(beanClass != null) {
            sb.append(beanClass.getName());
        }
        sb.append(":").append(propertyName);

        return sb.toString();
    }

    @Override
    public Expression saveExpression(Expression expression) {
        if(expression.getExpressionPK().getOrganizationId() == null) {
            expression.getExpressionPK().setOrganizationId(getOrganization().getId());
        }
        em.persist(expression);

        return expression;
    }

    @Override
    public Expression saveExpression(String expressionKey, String expressionValue) {
        if(expressionKey == null) {
            throw new NullPointerException("The expressionKey can not be null: expressionValue=" + expressionValue);
        }

        Expression expression = new Expression(getOrganization().getId(), expressionKey);
        expression.setExpressionValue(expressionValue);
        return saveExpression(expression);
    }

    @Override
    public Expression saveExpression(Class beanClass, String propertyName, String expressionValue) {
        String expressionKey;
        if((expressionKey = getExpressionKey(beanClass, propertyName)) == null) {
            throw new IllegalArgumentException("The expressionKey can not be null: beanClass=" + beanClass +
                    ", propertyName=" + propertyName + ", expressionValue=" + expressionValue);
        }

        return saveExpression(expressionKey, expressionValue);
    }

    @Override
    public void deleteExpression(Expression expression) {
        em.remove(expression);
    }

    @Override
    public void deleteExpression(String expressionKey) {
        if(expressionKey == null) {
            throw new NullPointerException("The expressionKey can not be null.");
        }

        deleteExpression(new Expression(getOrganization().getId(), expressionKey));
    }

    @Override
    public void deleteExpression(Class beanClass, String propertyName) {
        String expressionKey;
        if((expressionKey = getExpressionKey(beanClass, propertyName)) == null) {
            throw new IllegalArgumentException("The expressionKey can not be null: beanClass=" + beanClass +
                    ", propertyName=" + propertyName);
        }

        deleteExpression(expressionKey);
    }
}
