/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import java.util.Map;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.callback.Callback;
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
    implements ProductAssemblerRemote
{
    @PersistenceContext
    private EntityManager em;

    private Callback callback;

    @Override
    public ComplexProduct assemble(AssemblingSchema assemblingSchema, Map parameters)
        throws AlgorithmException
    {

        ProductAssembler pa = new ProductAssembler(assemblingSchema, em);

        System.out.println(callback);
        printJNDIObject();

        pa.setCallback(callback);

        System.out.println(pa.getCallback());

        //pa.setCallbackHandler(callbackHandler);
        ComplexProduct cp = pa.assemble(parameters);

        return cp;
    }
    @Override
    public void prepareCallback() {
        callback = CallbackImpl.getInstance();
        System.out.println("prepare: " + callback);
        printJNDIObject();
    }

    public void printJNDIObject(){
        try {
            InitialContext ctx = new InitialContext();
            Object o = ctx.lookup(Callback.NAME);
            System.out.println("in jndi: " + o);
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")


}