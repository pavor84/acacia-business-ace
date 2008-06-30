package com.cosmos.acacia.app;

import java.util.HashMap;
import java.util.Map;

import com.cosmos.acacia.crm.data.User;

/**
 * Singleton class for holding all the user sessions
 *
 * @author Bozhidar Bozhanov
 *
 */
public class SessionRegistry {

    private static SessionRegistry registry;

    private Map<Integer, AcaciaSession> sessions = new HashMap<Integer, AcaciaSession>();

    private SessionRegistry() {
        if (registry != null)
            throw new RuntimeException("Instantiation not allowed");
    }

    public static SessionRegistry getInstance() {
        if (registry == null)
            registry = new SessionRegistry();

        return registry;
    }

    public Map<Integer, AcaciaSession> getSessions() {
        return sessions;
    }

    public static AcaciaSession getSession(Integer id) {
        return getInstance().getSessions().get(id);
    }

    public static void addSession(Integer id, AcaciaSession session) {
        getInstance().getSessions().put(id, session);
    }
}