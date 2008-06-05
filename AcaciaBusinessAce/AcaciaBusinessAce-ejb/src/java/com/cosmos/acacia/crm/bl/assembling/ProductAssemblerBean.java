/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.callback.Callback;
import com.cosmos.acacia.callback.CallbackBean;
import com.cosmos.acacia.callback.CallbackImpl;
import com.cosmos.acacia.crm.assembling.AlgorithmException;
import com.cosmos.acacia.crm.assembling.ProductAssembler;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;

/**
 *
 * @author Miro
 */
@Stateless
public class ProductAssemblerBean
    extends CallbackBean
    implements ProductAssemblerRemote
{
    @PersistenceContext
    private EntityManager em;

    private int callbackId;

    @Override
    public ComplexProduct assemble(AssemblingSchema assemblingSchema, Map parameters)
        throws AlgorithmException
    {

        ProductAssembler pa = new ProductAssembler(assemblingSchema, em);

        Callback callback = CallbackImpl.getInstance(callbackId);
        pa.setCallback(callback);

        //pa.setCallbackHandler(callbackHandler);
        ComplexProduct cp = pa.assemble(parameters);

        return cp;
    }

    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")


}