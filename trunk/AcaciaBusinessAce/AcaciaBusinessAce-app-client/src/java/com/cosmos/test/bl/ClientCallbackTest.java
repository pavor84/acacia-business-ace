package com.cosmos.test.bl;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.callback.CallbackHandler;
import com.cosmos.acacia.callback.CallbackImpl;
import com.cosmos.acacia.callback.CallbackRequest;
import com.cosmos.acacia.callback.CallbackResult;
import com.cosmos.acacia.crm.assembling.AlgorithmException;
import com.cosmos.acacia.crm.bl.assembling.ProductAssemblerRemote;

public class ClientCallbackTest {

    @EJB
    private ProductAssemblerRemote formSession;

    @Before
    public void setUp() {
        if ( formSession==null ){
            try {
                formSession = InitialContext.doLookup(ProductAssemblerRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
    }

    @Test
    public void testAssembling() throws AlgorithmException {

        int id = formSession.prepareCallback();

        CallbackHandler handler = new CallbackHandler() {

            @Override
            public CallbackResult handle(CallbackRequest req) {
                // do nothing
                return null;
            }

        };
        CallbackImpl client = CallbackImpl.getClientInstance(id, handler);
        formSession.assemble(null, null);


    }
}

