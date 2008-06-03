package com.cosmos.acacia.callback;

public abstract class CallbackBean {
    protected int callbackId;

    /**
     * Prepares the callback object. Returns an identifier,
     * by which the object should be looked up
     *
     * @return the id
     */
    public int prepareCallback() {
        callbackId = CallbackImpl.prepareCallback();
        return callbackId;
    }
}
