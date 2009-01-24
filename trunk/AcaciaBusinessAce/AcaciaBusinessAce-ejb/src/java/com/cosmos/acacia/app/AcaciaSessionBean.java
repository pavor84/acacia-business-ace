package com.cosmos.acacia.app;

import static com.cosmos.acacia.app.SessionContext.BRANCH_KEY;
import static com.cosmos.acacia.app.SessionContext.PERSON_KEY;

import com.cosmos.acacia.crm.data.BusinessPartner;
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
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.data.properties.DbProperty;
import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.security.AccessLevel;
import com.cosmos.acacia.util.AcaciaPropertiesImpl;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import javax.ejb.EJB;
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
    public Set<UserRight> getGeneralRights() {
        return (Set<UserRight>) getSession().getValue(SessionContext.GENERAL_RIGHTS_KEY);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<UserRight> getSpecialPermissions() {
        return (Set<UserRight>) getSession().getValue(SessionContext.SPECIAL_PERMISSIONS_KEY);
    }

    @Override
    public void setGeneralRights(Set<UserRight> rights) {
        getSession().setValue(SessionContext.GENERAL_RIGHTS_KEY, rights);
    }

    @Override
    public void setSpecialPermissions(Set<UserRight> rights) {
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
        return rightsManager.isAllowed(SpecialPermission.Category.Administration.getCategorizedPermissions());
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
}
