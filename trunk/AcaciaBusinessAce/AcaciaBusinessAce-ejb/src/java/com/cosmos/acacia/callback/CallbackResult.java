package com.cosmos.acacia.callback;

import java.io.Serializable;

public class CallbackResult implements Serializable {

    private static final long serialVersionUID = -2258999442638461030L;

    private Integer id;

    public int getId() {
        return id.intValue();
    }

    public void setId(int id) {
        this.id = new Integer(id);
    }
}
