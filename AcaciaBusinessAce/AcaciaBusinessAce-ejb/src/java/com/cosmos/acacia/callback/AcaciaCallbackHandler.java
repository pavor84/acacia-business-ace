/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.callback;

import java.io.IOException;
import java.util.Arrays;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 *
 * @author Miro
 */
public class AcaciaCallbackHandler
    implements CallbackHandler
{

    @Override
    public void handle(Callback[] callbacks)
        throws IOException, UnsupportedCallbackException
    {
        System.out.println("AcaciaCallbackHandler.handle.callbacks: " + Arrays.asList(callbacks));
    }

}
