/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.ApplicationCallback;
import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class CallbackAssembleResult
    extends AssembleResult
    implements Serializable
{
    private ApplicationCallback[] callbacks;

    public CallbackAssembleResult(ApplicationCallback[] callbacks)
    {
        super(Type.Callback);
        this.callbacks = callbacks;
    }

    @Override
    public ApplicationCallback[] getApplicationCallbacks()
    {
        return callbacks;
    }
}
