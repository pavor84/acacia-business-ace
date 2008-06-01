/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.crm.data.ComplexProductItem;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Miro
 */
public class IntermediateAssembleResult
    extends AssembleResult
    implements Serializable
{

    public IntermediateAssembleResult(List<ComplexProductItem> complexProductItems)
    {
        super(Type.Intermediate);
        setComplexProductItems(complexProductItems);
    }
}
