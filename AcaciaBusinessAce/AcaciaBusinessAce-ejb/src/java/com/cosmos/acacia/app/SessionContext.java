package com.cosmos.acacia.app;

import java.util.Map;

/**
 * Created	:	22.05.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Just a map, with defined constants
 */
public interface SessionContext {
    
    /** Login organization */
    static String ORGANIZATION_KEY = "ORGANIZATION_KEY";
    /** Logged user */
    static String USER_KEY = "USER_KEY";
    /** Current user's locale */
    static String USER_LOCALE = "LOCALE_KEY";
    /** Current user's login branch */
    static String BRANCH_KEY = "BRANCH_KEY";
    /** Currently logged person */
    static String PERSON_KEY = "PERSON_KEY";

    /**
     * Value read.
     * Behavior similar to {@link Map#get(Object)}
     * @param name
     * @return null if value is not found
     */
    Object getValue(String name);
    
    /**
     * Value write.
     * Behavior similar to {@link Map#put(Object, Object)}
     * @param name
     * @param value
     */
    void setValue(String name, Object value);
}
