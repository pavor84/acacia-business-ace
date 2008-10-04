package com.cosmos.acacia.app;

import static com.cosmos.acacia.app.SessionContext.BRANCH_KEY;
import static com.cosmos.acacia.app.SessionContext.PERSON_KEY;

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
}
