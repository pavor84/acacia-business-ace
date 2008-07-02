package com.cosmos.acacia.app;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.User;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Stateless service to use for session access.
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

    @PersistenceContext
    private EntityManager em;

    /**
     * Temporal functionality to simulate Organization selection when login
     * @return DataObject which instance is {@link Organization} and has the
     * biggest addresses count among.
     */
    @SuppressWarnings("unchecked")
    public DataObject getDataObjectWithAddresses()
    {
        List<Address> allAddresses =
            em.createQuery("select a from Address a where a.dataObject.parentDataObjectId is not null")
            .getResultList();

        //add-hoc temporary logic, to consider the parent data object with most addresses
        Map<BigInteger, Long> addressesCount = new HashMap<BigInteger, Long>();

        for(Address address : allAddresses)
        {
            BigInteger parentId;
            DataObject dataObject;
            if((dataObject = address.getDataObject()) != null &&
                (parentId = dataObject.getParentDataObjectId()) != null)
            {
                Long curValue = addressesCount.get(parentId);
                if(curValue == null)
                    curValue = new Long(1);
                else
                    curValue++;
                addressesCount.put(parentId, curValue);
            }
        }

        //find the one with most addresses
        Long biggestCount = new Long(0);
        BigInteger choosenId = null;
        for(Map.Entry<BigInteger, Long> parentEntry : addressesCount.entrySet())
        {
            if(parentEntry.getValue() > biggestCount)
            {
                biggestCount = parentEntry.getValue();
                choosenId = parentEntry.getKey();
            }
        }

        if(choosenId == null)
            return null;

        return em.find(DataObject.class, choosenId);
    }

    @Override
    public Integer login(User user) {
        //create and register session
        Integer sessionid = registerNewSession();
        
        SessionRegistry.getSession().setValue(SessionContext.USER_KEY, user);
        
        //temporal - TODO remove
        
        
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
    public DataObject getLoginOrganizationDataObject() {
		
		//temoporal TODO - remove
        return getDataObjectWithAddresses();
    }

    public DataObject getDataObject(BigInteger dataObjectId)
    {
        return em.find(DataObject.class, dataObjectId);
    }

	@Override
	public void setOrganization(Organization organization) {
		SessionRegistry.getSession().setValue(SessionContext.ORGANIZATION_KEY, organization);
	}

	@Override
	public Organization getOrganization() {
		return (Organization) SessionRegistry.getSession().getValue(SessionContext.ORGANIZATION_KEY);
	}

	@Override
	public User getUser() {
		return (User) SessionRegistry.getSession().getValue(SessionContext.USER_KEY);
	}
}
