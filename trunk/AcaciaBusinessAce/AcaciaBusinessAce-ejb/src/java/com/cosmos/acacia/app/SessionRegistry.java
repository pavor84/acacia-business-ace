package com.cosmos.acacia.app;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Singleton class for holding all the user sessions.
 * Also provides access to a session bound to the current thread.
 * 	(this is actually the current session of execution).
 *
 * @author Bozhidar Bozhanov
 *
 */
public final class SessionRegistry {
	
	private static ThreadLocal<SessionContext> localSessionContext = new ThreadLocal<SessionContext>();

    private static SessionRegistry registry;

    private Map<UUID, SessionContext> sessions =
            Collections.synchronizedMap(new TreeMap<UUID, SessionContext>());

    private SessionRegistry() {
        if (registry != null)
            throw new RuntimeException("Instantiation not allowed");
    }

    //All methods are with package access. We don't want the registry exposed.
    //It's for internal use for the AcaciaSession and SessionFacade.
    //Everything that is to be exposed (as local or remote) goes through AcaciaSessionBean.
    
    /**
     * Singleton access.
     */
    static SessionRegistry getInstance() {
        if (registry == null)
            registry = new SessionRegistry();

        return registry;
    }
    
    /**
     * Access all registered sessions.
     * @return map
     */
    Map<UUID, SessionContext> getSessions() {
        return sessions;
    }

    /**
     * Returns the session context with this id, or null.
     * @param id
     * @return session context or null
     */
    static SessionContext getSession(UUID id) {
        if(id == null)
            return null;
        return getInstance().getSessions().get(id);
    }

    /**
     * Registers new session context for with a given id.
     * @param id
     * @param session
     */
    static void addSession(UUID id, SessionContext session) {
        getInstance().getSessions().put(id, session);
    }
    
    /**
     * Returns the current session (the session bound to the current thread).
     * Null if not set up.
     * @return {@link SessionContext}
     */
    static SessionContext getSession() {
        return localSessionContext.get();
    }
    
    /**
     * Sets the given context as the current session for this thread.
     * @param context
     */
    static void setLocalSession(SessionContext context){
    	localSessionContext.set(context);
    }
    
    /**
     * Creates new session and returns it's id.
     * @return Integer
     */
    UUID createNewSession(){
    	UUID id = UUID.randomUUID();
    	while ( sessions.containsKey(id) ){
    		id = UUID.randomUUID();
    	}
    	SessionContextImpl context = new SessionContextImpl();
    	addSession(id, context);
    	return id;
    }
}