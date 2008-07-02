/**
 * 
 */
package com.cosmos.acacia.app;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jchan
 *
 */
public class SessionContextImpl implements SessionContext {

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
}
