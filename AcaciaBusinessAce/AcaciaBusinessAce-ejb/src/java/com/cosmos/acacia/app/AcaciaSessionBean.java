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

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.data.properties.DbProperty;
import com.cosmos.acacia.crm.data.properties.DbPropertySublevel;
import java.util.concurrent.locks.ReentrantLock;
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

    private final ReentrantLock sublevelLock = new ReentrantLock();

    @Override
    public Integer login(User user) {
        //create and register session
        Integer sessionid = registerNewSession();

        SessionRegistry.getSession().setValue(SessionContext.USER_KEY, user);

        return sessionid;
    }

    private Integer registerNewSession() {
        //create a session
        Integer sessionid = SessionRegistry.getInstance().createNewSession();
        //acquire the instance
        SessionContext session = SessionRegistry.getSession(sessionid);
        //bind to current thread
        SessionRegistry.setLocalSession(session);

        return sessionid;
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

    private int getSublevelId(int levelId, String sublevelName)
    {
        Query q = em.createNamedQuery("DbPropertySublevel.findByLevelIdAndSublevelName");
        q.setParameter("levelId", levelId);
        q.setParameter("sublevelName", sublevelName);
        try
        {
            return (Integer)q.getSingleResult();
        }
        catch(NoResultException ex) {}

        sublevelLock.lock();
        try
        {
            int sublevelId;
            q = em.createNamedQuery("DbPropertySublevel.maxSublevelIdByLevelId");
            q.setParameter("levelId", levelId);
            try
            {
                sublevelId = (Integer)q.getSingleResult();
            }
            catch(NoResultException ex)
            {
                sublevelId = 0;
            }

            DbPropertySublevel propertySublevel = new DbPropertySublevel(levelId, sublevelName);
            propertySublevel.setSublevelId(++sublevelId);
            em.persist(propertySublevel);
            return sublevelId;
        }
        finally
        {
            sublevelLock.unlock();
        }
    }

    @Override
    public AcaciaProperties getProperties(
            BusinessPartner client,
            AcaciaProperties.Level baseLevel,
            String sublevelName)
    {
        AcaciaProperties properties =
                (AcaciaProperties)get(SessionContext.ACACIA_PROPERTIES);
        if(properties == null)
        {
            properties = new AcaciaProperties();
        }
        else
        {
            properties.removeAllLevels();
        }

        Integer sublevelId = null;
        if(baseLevel != null && (sublevelName = sublevelName.trim()).length() > 0)
        {
            sublevelId = getSublevelId(baseLevel.getPriority(), sublevelName);
        }

        Organization organization = getOrganization();
        Address branch = getBranch();
        User user = getUser();
        int levelId;
        BigInteger relatedObjectId;
        Query q = em.createNamedQuery("DbProperty.findByLevelIdAndRelatedObjectId");

        for(AcaciaProperties.Level level : AcaciaProperties.Level.values())
        {
            levelId = level.getPriority();
            switch(level)
            {
                case System:
                case SystemAdministrator:
                    // ToDo: Put some real object because of FK constraint
                    relatedObjectId = BigInteger.ZERO;
                    break;

                case Organization:
                case OrganizationAdministrator:
                    relatedObjectId = organization.getId();
                    break;

                case Branch:
                case BranchAdministrator:
                    relatedObjectId = branch.getId();
                    break;

                case User:
                    relatedObjectId = user.getUserId();
                    break;

                case Client:
                    if(client == null)
                        continue;

                    relatedObjectId = client.getPartnerId();
                    break;

                case Current:
                    continue;

                default:
                    throw new UnsupportedOperationException("Unknown level: " + level);
            }

            q.setParameter("levelId", levelId);
            q.setParameter("relatedObjectId", relatedObjectId);
            List<DbProperty> dbProperties = q.getResultList();
            properties.putProperties(level, dbProperties);

            if(sublevelId != null && level.equals(baseLevel))
            {
                q.setParameter("levelId", sublevelId);
                dbProperties = q.getResultList();
                properties.putProperties(level, dbProperties);
            }
        }

        return properties;
    }

    @Override
    public AcaciaProperties getProperties(BusinessPartner client)
    {
        return getProperties(client, null, null);
    }

    @Override
    public AcaciaProperties getProperties()
    {
        return getProperties(null);
    }

    @Override
    public void saveProperties(AcaciaProperties properties)
    {
        
    }

    /*private void saveProperties(AcaciaProperties properties)
    {
    }*/
}
