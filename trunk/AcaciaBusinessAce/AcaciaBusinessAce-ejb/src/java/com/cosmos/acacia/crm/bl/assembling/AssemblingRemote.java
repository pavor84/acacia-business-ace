/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.beansbinding.EntityProperties;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface AssemblingRemote
{
    List<AssemblingCategory> getAssemblingCategories(DataObject parent);
    EntityProperties getAssemblingCategoryEntityProperties();
    boolean deleteAssemblingCategories(List<AssemblingCategory> categories);
    AssemblingCategory newAssemblingCategory(AssemblingCategory parentCategory);
    AssemblingCategory save(AssemblingCategory entity);
    AssemblingCategory updateParents(AssemblingCategory newParent, AssemblingCategory newChildren);
    AssemblingCategory getParent(AssemblingCategory child);
    //AssemblingCategory setParent(AssemblingCategory entity, AssemblingCategory parent);

    List<AssemblingSchema> getAssemblingSchemas(AssemblingCategory assemblingCategory);

    List<AssemblingSchemaItem> getAssemblingSchemaItems(AssemblingSchema assemblingSchema);

    List<AssemblingSchemaItemValue> getAssemblingSchemaItemValues(AssemblingSchemaItem assemblingSchemaItem);

    EntityProperties getAssemblingSchemaEntityProperties();
}
