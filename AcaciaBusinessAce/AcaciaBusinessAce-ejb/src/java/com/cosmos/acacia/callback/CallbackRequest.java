package com.cosmos.acacia.callback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CallbackRequest implements Serializable {

    private static final long serialVersionUID = -6305561316822548709L;

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
