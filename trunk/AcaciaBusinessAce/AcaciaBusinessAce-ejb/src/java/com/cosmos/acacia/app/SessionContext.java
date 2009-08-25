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
    static final String ORGANIZATION_KEY = "ORGANIZATION_KEY";
    /** Logged user */
    static final String USER_KEY = "USER_KEY";
    /** Login organization */
    static final String USER_ORGANIZATION_KEY = "USER_ORGANIZATION_KEY";
    /** Current user's locale */
    static final String USER_LOCALE = "LOCALE_KEY";
    /** Current user's login branch */
    static final String BRANCH_KEY = "BRANCH_KEY";
    /** Currently logged person */
    static final String PERSON_KEY = "PERSON_KEY";
    /** Currently logged contact person */
    static final String CONTACT_PERSON_KEY = "CONTACT_PERSON_KEY";
    /** Whether data from all branches should be presented */
    static final String VIEW_DATA_FROM_ALL_BRANCHES_KEY = "VIEW_DATA_FROM_ALL_BRANCHES_KEY";
    /** Currently logged person */
    static final String GENERAL_RIGHTS_KEY = "GENERAL_RIGHTS_KEY";
    /** Currently logged person */
    static final String SPECIAL_PERMISSIONS_KEY = "SPECIAL_PERMISSIONS_KEY";
    /** The System Mail Utils */
    static final String SYSTEM_MAIL_UTILS_KEY = "SYSTEM_MAIL_UTILS_KEY";
    /** The Organization Mail Utils */
    static final String ORGANIZATION_MAIL_UTILS_KEY = "ORGANIZATION_MAIL_UTILS_KEY";
    /** The User Mail Utils */
    static final String USER_MAIL_UTILS_KEY = "USER_MAIL_UTILS_KEY";
    

    /** Acacia Properties */
    static String ACACIA_PROPERTIES = "ACACIA_PROPERTIES";

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
