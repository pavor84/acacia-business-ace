/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.assembling.ProductAssemblerService;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.RealProduct;
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
    extends ProductAssemblerService
{
    //List<AssemblingCategory> getAssemblingCategories(DataObject parent);
    List<AssemblingCategory> getAssemblingCategories(AssemblingCategory parent, Boolean allHeirs);
    int getAssemblingCategoryChildCount(AssemblingCategory parentEntity);
    boolean deleteAssemblingCategories(List<AssemblingCategory> categories);
    boolean deleteAssemblingCategory(AssemblingCategory assemblingCategory);
    boolean deleteAssemblingSchema(AssemblingSchema assemblingSchema);
    boolean deleteAssemblingSchemaItem(AssemblingSchemaItem schemaItem);
    boolean deleteAssemblingSchemaItemValue(AssemblingSchemaItemValue itemValue);
    AssemblingSchema newAssemblingSchema();
    AssemblingSchemaItem newAssemblingSchemaItem(AssemblingSchema assemblingSchema);
    AssemblingSchemaItemValue newAssemblingSchemaItemValue(AssemblingSchemaItem schemaItem);
    AssemblingCategory newAssemblingCategory(AssemblingCategory parentCategory);
    AssemblingCategory saveCategory(AssemblingCategory entity);
    AssemblingSchema saveSchema(AssemblingSchema entity);
    AssemblingSchemaItem saveSchemaItem(AssemblingSchemaItem entity);
    AssemblingSchemaItemValue saveItemValue(AssemblingSchemaItemValue entity);
    AssemblingCategory updateParents(AssemblingCategory newParent, AssemblingCategory newChildren);
    AssemblingCategory getParent(AssemblingCategory child);
    //AssemblingCategory setParent(AssemblingCategory entity, AssemblingCategory parent);
    RealProduct getRealProduct(SimpleProduct simpleProduct);

    List<AssemblingSchema> getAssemblingSchemas(Organization organization);
    List<AssemblingSchema> getAssemblingSchemas(AssemblingCategory assemblingCategory);
    List<VirtualProduct> getVirtualProducts(Organization organization);

    EntityProperties getAssemblingSchemaEntityProperties();
    EntityProperties getAssemblingSchemaItemEntityProperties();
    EntityProperties getAssemblingSchemaItemValueEntityProperties();
    EntityProperties getVirtualProductItemEntityProperties();
    EntityProperties getAssemblingCategoryEntityProperties();

    List<DbResource> getAlgorithms();
    List<DbResource> getDataTypes();

}
