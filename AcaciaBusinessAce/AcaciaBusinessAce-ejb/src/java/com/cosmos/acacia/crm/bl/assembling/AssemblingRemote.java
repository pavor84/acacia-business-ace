/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.VirtualProduct;
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
    AssemblingCategory saveCategory(AssemblingCategory entity);
    AssemblingSchema saveSchema(AssemblingSchema entity);
    AssemblingSchemaItem saveSchemaItem(AssemblingSchemaItem entity);
    AssemblingSchemaItemValue saveItemValue(AssemblingSchemaItemValue entity);
    AssemblingCategory updateParents(AssemblingCategory newParent, AssemblingCategory newChildren);
    AssemblingCategory getParent(AssemblingCategory child);
    //AssemblingCategory setParent(AssemblingCategory entity, AssemblingCategory parent);

    List<AssemblingSchema> getAssemblingSchemas(Organization organization);
    List<AssemblingSchema> getAssemblingSchemas(AssemblingCategory assemblingCategory);
    List<AssemblingSchemaItem> getAssemblingSchemaItems(AssemblingSchema assemblingSchema);
    List<AssemblingSchemaItemValue> getAssemblingSchemaItemValues(AssemblingSchemaItem assemblingSchemaItem);
    List<VirtualProduct> getVirtualProducts(Organization organization);

    EntityProperties getAssemblingSchemaEntityProperties();
    EntityProperties getAssemblingSchemaItemEntityProperties();
    EntityProperties getAssemblingSchemaItemValueEntityProperties();
    EntityProperties getVirtualProductItemEntityProperties();

    List<DbResource> getAlgorithms();
    List<DbResource> getDataTypes();

}
