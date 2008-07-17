package com.cosmos.acacia.callback;

public interface Callbackable {

    /**
     * A method for handling specific callback functionality.
     *
     * @param request
     * @return result
     */
    CallbackTransportObject callback(CallbackTransportObject request);
}
