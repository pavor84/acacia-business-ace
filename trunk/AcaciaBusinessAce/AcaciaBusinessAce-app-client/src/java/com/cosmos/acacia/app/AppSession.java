package com.cosmos.acacia.app;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.cosmos.acacia.crm.data.DataObject;
import java.math.BigInteger;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Singleton - wrapper of acacia session stateful bean
 */
public final class AppSession
    implements AcaciaSession
{
    
    //lazy instantiation - at first request
    private static AppSession instance = null;
    
    private AppSession()
    {
        try
        {
            acaciaSession = InitialContext.doLookup(AcaciaSessionRemote.class.getName());
        }
        catch(NamingException ex)
        {
            throw new IllegalStateException("Remote bean can't be loaded", ex);
        }
    }
    
    private AcaciaSessionRemote acaciaSession;

    @Override
    public Object getValue(String name) {
        return acaciaSession.getValue(name);
    }

    @Override
    public void setValue(String name, Object value) {
        acaciaSession.setValue(name, value);
    }
    
    /**
     * Singleton access method. Lazy - the instance is instantiated at first request.
     * @return
     */
    public static AppSession get()
    {
        if(instance == null)
        {
            instance = new AppSession();
        }

        return instance;
    }

    public void login(String user, String password)
    {
        acaciaSession.login(user, password);
    }

    public DataObject getLoginOrganizationDataObject()
    {
        return acaciaSession.getLoginOrganizationDataObject();
    }

    public static DataObject getDataObject(BigInteger dataObjectId)
    {
        if(dataObjectId == null)
            return null;

        return get().acaciaSession.getDataObject(dataObjectId);
    }
}