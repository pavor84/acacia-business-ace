package com.cosmos.acacia.callback;

public interface CallbackEnabled {

    /**
     * Prepares the callback object. Returns an identifier,
     * by which the object should be looked up
     * @return
     */
    public int prepareCallback();
}
