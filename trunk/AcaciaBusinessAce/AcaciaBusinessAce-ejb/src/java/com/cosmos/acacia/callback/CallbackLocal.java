package com.cosmos.acacia.callback;

import javax.ejb.Local;


@Local
public interface CallbackLocal {

    /**
     * Handles callback requests
     *
     * @param handler
     * @param request
     * @return results, if any (use with caution to blocking server threads!)
     */
    CallbackTransportObject handle(CallbackHandler handler, CallbackTransportObject request);
}
