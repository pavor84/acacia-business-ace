package com.cosmos.acacia.app;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateful;

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
}
