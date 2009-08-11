/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.data.product.ComplexProduct;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.product.Product;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingMessage;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.RealProduct;
import com.cosmos.acacia.crm.data.assembling.VirtualProduct;
import com.cosmos.acacia.crm.enums.DatabaseResource;
import com.cosmos.beansbinding.EntityProperties;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface AssemblingRemote
    extends ProductItemService
{
    //List<AssemblingCategory> getAssemblingCategories(DataObject parent);
    List<AssemblingCategory> getAssemblingCategories(AssemblingCategory parent, Boolean allHeirs);
    int getAssemblingCategoryChildCount(AssemblingCategory parentEntity);

    boolean deleteAssemblingCategories(List<AssemblingCategory> categories);
    boolean deleteAssemblingCategory(AssemblingCategory assemblingCategory);
    boolean deleteAssemblingSchema(AssemblingSchema assemblingSchema);
    boolean deleteAssemblingSchemaItem(AssemblingSchemaItem schemaItem);
    boolean deleteAssemblingSchemaItemValue(AssemblingSchemaItemValue itemValue);
    boolean deleteAssemblingMessage(AssemblingMessage assemblingMessage);

    AssemblingSchema newAssemblingSchema();
    AssemblingSchema newAssemblingSchema(AssemblingCategory assemblingCategory);
    AssemblingSchemaItem newAssemblingSchemaItem(AssemblingSchema assemblingSchema);
    AssemblingSchemaItemValue newAssemblingSchemaItemValue(AssemblingSchemaItem schemaItem);
    AssemblingCategory newAssemblingCategory(AssemblingCategory parentCategory);
    AssemblingMessage newAssemblingMessage();

    AssemblingCategory saveCategory(AssemblingCategory entity);
    AssemblingSchema saveSchema(AssemblingSchema entity);
    AssemblingSchemaItem saveSchemaItem(AssemblingSchemaItem entity);
    AssemblingSchemaItemValue saveItemValue(AssemblingSchemaItemValue entity);
    ComplexProduct saveComplexProduct(ComplexProduct complexProduct);
    AssemblingMessage saveAssemblingMessage(AssemblingMessage assemblingMessage);

    AssemblingCategory updateParents(AssemblingCategory newParent, AssemblingCategory newChildren);
    AssemblingCategory getParentCategory(AssemblingCategory child);
    //AssemblingCategory setParent(AssemblingCategory entity, AssemblingCategory parent);
    RealProduct getRealProduct(SimpleProduct simpleProduct);

    //List<AssemblingSchema> getAssemblingSchemas(boolean applicable);
    List<AssemblingSchema> getAssemblingSchemas(
            AssemblingCategory assemblingCategory, Boolean applicable);
    List<VirtualProduct> getVirtualProducts();
    List<AssemblingMessage> getAssemblingMessages();
    //List<ComplexProductItem> getComplexProductItems(ComplexProduct complexProduct);

    DataObjectBean getParentProduct(Product product);
    List<Product> getChildProducts(DataObjectBean parent);

    EntityProperties getAssemblingSchemaEntityProperties();
    EntityProperties getAssemblingSchemaItemEntityProperties();
    EntityProperties getAssemblingSchemaItemValueEntityProperties();
    EntityProperties getVirtualProductItemEntityProperties();
    EntityProperties getAssemblingCategoryEntityProperties();
    EntityProperties getComplexProductEntityProperties();
    EntityProperties getComplexProductItemEntityProperties();
    EntityProperties getAssemblingMessageEntityProperties();
    EntityProperties getAssemblingParameterEntityProperties();
    EntityProperties getProductSelectionRowEntityProperties();

    List<DbResource> getAlgorithms();
    List<DbResource> getDataTypes();
    List<DbResource> getMeasureUnits();
    DbResource getDbResource(DatabaseResource databaseResource);

}
