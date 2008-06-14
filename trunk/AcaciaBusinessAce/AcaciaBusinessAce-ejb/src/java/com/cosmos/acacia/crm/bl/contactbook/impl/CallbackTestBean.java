/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import com.cosmos.acacia.callback.CallbackBean;
import com.cosmos.acacia.callback.CallbackRequest;
import com.cosmos.acacia.callback.CallbackResult;
import javax.ejb.Stateful;

/**
 *
 * @author Bozhidar Bozhanov
 */
@Stateful
public class CallbackTestBean
        extends CallbackBean
        implements CallbackTestRemote, CallbackTestLocal 
{

    @Override
    public int calculate(int a, int b) {
        
        if (b == 0) {
            CallbackRequest request = new CallbackRequest();
            request.addParam("message", "wtf");
            try {
                CallbackResult result = getCallbackInstance().askClient(request);
                b = Integer.parseInt(result.getParam("response").toString());
            } catch (Exception ex) {
                b = -10;
                ex.printStackTrace();
            }
        }
        
        return a + b;
    }
}
