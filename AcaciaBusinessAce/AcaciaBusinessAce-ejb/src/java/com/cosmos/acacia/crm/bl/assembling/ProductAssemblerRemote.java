/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.assembling.AlgorithmException;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface ProductAssemblerRemote
{

    ComplexProduct assemblе(AssemblingSchema assemblingSchema, Map parameters)
        throws AlgorithmException;
    
}
