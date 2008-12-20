/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.crm.data.assembling.AssemblingMessage;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import java.util.List;

/**
 *
 * @author Miro
 */
public interface ProductAssemblerService
{
    List<AssemblingSchemaItem> getAssemblingSchemaItems(
        AssemblingSchema assemblingSchema);
    List<AssemblingSchemaItemValue> getAssemblingSchemaItemValues(
        AssemblingSchemaItem assemblingSchemaItem);
    List<String> getPropertyKeys(AssemblingSchema schema, AssemblingMessage message);
}
