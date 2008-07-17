package com.cosmos.acacia.callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CallbackTransportObject implements Serializable {

    private Map<String, Object> params = new HashMap<String, Object>();

    public void put(String name, Object value) {
        params.put(name, value);
    }

    public Object get(String name) {
        Object result = params.get(name);
        try {
            byte[] bytes = (byte[]) result;
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            result = ois.readObject();
        } catch (ClassCastException ex) {
            //return the result as it is
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Result object is of class: " + result.getClass());
        return result;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
