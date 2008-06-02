package com.cosmos.acacia.callback;

import java.io.Serializable;

public class CallbackRequest implements Serializable {

    private static final long serialVersionUID = -6305561316822548709L;

    private Integer id;

    public int getId() {
        return id.intValue();
    }

    public void setId(int id) {
        this.id = new Integer(id);
    }

}
