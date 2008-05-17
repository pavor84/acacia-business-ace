/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.assembling.AlgorithmException;
import com.cosmos.acacia.crm.assembling.ProductAssembler;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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


    @Override
    public ComplexProduct assemble(AssemblingSchema assemblingSchema, Map parameters)
        throws AlgorithmException
    {
        
        ProductAssembler pa = new ProductAssembler(assemblingSchema, em);
        //pa.setCallbackHandler(callbackHandler);
        return pa.assemble(parameters);
    }

    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")

    
}
