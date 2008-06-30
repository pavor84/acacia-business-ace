package com.cosmos.acacia.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Organization;
import java.math.BigInteger;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Stateful
public class AcaciaSessionBean implements AcaciaSessionRemote, AcaciaSessionLocal {


    Map<String, Object> values = new HashMap<String, Object>();

    @Override
    public Object getValue(String name) {
        return
        values.get(name);
    }

    @Override
    public void setValue(String name, Object value) {
        values.put(name, value);
    }

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
    public void login(String user, String password) {
        DataObject loginOrganization = getDataObjectWithAddresses();
        values.put(AcaciaSession.ORGANIZATION_KEY, loginOrganization);
    }

    @Override
    public DataObject getLoginOrganizationDataObject() {
        DataObject result = (DataObject) values.get(AcaciaSession.ORGANIZATION_KEY);
        return result;
    }

    public DataObject getDataObject(BigInteger dataObjectId)
    {
        return em.find(DataObject.class, dataObjectId);
    }

    @Override
    public Integer generateSessionId() {
        Map<Integer, AcaciaSession> sessions = SessionRegistry.getInstance().getSessions();
        
        Integer sessionId;
        while (true) {
            int rand = (int) (100000 + Math.random() * 899999);
            sessionId = new Integer(rand);
            if (!sessions.keySet().contains(sessionId))
                break;
        }
        
        return sessionId;
    }

}
