package com.cosmos.acacia.callback;

import java.io.Serializable;


/**
 * Implement this interface to provide specific callback
 * processing functionality on the client side
 *
 * @author Bozhidar Bozhanov
 *
 */
public interface CallbackHandler extends Serializable {

    /**
     * Handles a callback request and returns the result
     *
     * @param req the request
     * @return the result
     */
    public CallbackResult handle(CallbackRequest req);
}
