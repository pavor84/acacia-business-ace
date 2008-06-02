/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import java.util.Map;

import javax.ejb.Remote;

import com.cosmos.acacia.callback.CallbackEnabled;
import com.cosmos.acacia.crm.assembling.AlgorithmException;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;

/**
 *
 * @author Miro
 */
@Remote
public interface ProductAssemblerRemote extends CallbackEnabled
{

    ComplexProduct assemble(AssemblingSchema assemblingSchema, Map parameters)
        throws AlgorithmException;

}
