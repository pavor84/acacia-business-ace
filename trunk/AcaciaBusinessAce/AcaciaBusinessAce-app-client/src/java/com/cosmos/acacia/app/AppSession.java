package com.cosmos.acacia.app;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.cosmos.acacia.crm.data.DataObject;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Singleton - wrapper of acacia session stateful bean
 */
public final class AppSession implements AcaciaSession{
    
    //lazy instantiation - at first request
    private static AppSession instance = null;
    
    private AppSession(){
        try {
            acaciaSessionRemote = InitialContext.doLookup(AcaciaSessionRemote.class.getName());
        } catch (NamingException e) {
            throw new IllegalStateException("Remote bean can't be loaded", e);
        }
    }
    
    private AcaciaSessionRemote acaciaSessionRemote;

    @Override
    public Object getValue(String name) {
        return acaciaSessionRemote.getValue(name);
    }

    @Override
    public void setValue(String name, Object value) {
        acaciaSessionRemote.setValue(name, value);
    }
    
    /**
     * Singleton access method. Lazy - the instance is instantiated at first request.
     * @return
     */
    public static AppSession get(){
        if ( instance==null ){
            instance = new AppSession();
        }
        return instance;
    }

    public void login(String user, String password) {
        acaciaSessionRemote.login(user, password);
    }

    public DataObject getLoginOrganizationDataObject() {
        return acaciaSessionRemote.getLoginOrganizationDataObject();
    }
}