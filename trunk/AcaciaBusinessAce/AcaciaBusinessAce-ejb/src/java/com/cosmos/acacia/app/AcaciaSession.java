package com.cosmos.acacia.app;

/**
 * Created	:	22.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public interface AcaciaSession {
    
     /* Key constants */

    public static final String ORGANIZATION_KEY = "ORGANIZATION_KEY";
    public static final String USER_KEY = "USER_KEY";
    public static final String USER_LOCALE = "LOCALE_KEY";

    
    public Object getValue(String name);

    public void setValue(String name, Object value);
}
