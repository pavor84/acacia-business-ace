package com.cosmos.acacia.app;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public final class AcaciaSession {
    
    public static Object getValue(String name){
        AcaciaSessionRemote s = getAcaciaSessionRemote();
        return s.getValue(name);
    }
    
    private static AcaciaSessionRemote acaciaSessionRemote;
    
    private static AcaciaSessionRemote getAcaciaSessionRemote() {
        if ( acaciaSessionRemote==null ){
            try {
                acaciaSessionRemote = InitialContext.doLookup(AcaciaSessionRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
        return acaciaSessionRemote;
    }

    public static void setValue(String name, Object value){
        AcaciaSessionRemote s = getAcaciaSessionRemote();
        s.setValue(name, value);
    }
}
