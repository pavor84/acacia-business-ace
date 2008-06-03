package com.cosmos.acacia.callback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The result of callback operation on the client
 *
 * @author Bozhidar Bozhanov
 */
public class CallbackResult implements Serializable {

    private static final long serialVersionUID = -2258999442638461030L;

    Map<String, Object> params = new HashMap<String, Object>();

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void addParam(String key, Object value) {
        params.put(key, value);
    }

    public Object getParam(String key) {
        return params.get(key);
    }
}
