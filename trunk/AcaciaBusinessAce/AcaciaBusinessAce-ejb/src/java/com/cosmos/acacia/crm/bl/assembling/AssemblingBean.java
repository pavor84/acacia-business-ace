/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.assembling.Algorithm;
import com.cosmos.acacia.crm.assembling.ProductSelectionRow;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.ComplexProductItem;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingMessage;
import com.cosmos.acacia.crm.data.assembling.AssemblingParameter;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.RealProduct;
import com.cosmos.acacia.crm.data.assembling.VirtualProduct;
import com.cosmos.acacia.crm.enums.DataType;
import com.cosmos.acacia.crm.enums.DatabaseResource;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author Miro
 */
@Stateless
public class AssemblingBean
    implements AssemblingRemote, AssemblingLocal
{
    Logger logger = Logger.getLogger(AssemblingBean.class);

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private AssemblingCategoryValidatorLocal assemblingCategoryValidator;

    @EJB
    private AssemblingSchemaItemValidatorLocal assemblingSchemaItemValidator;
    
    @EJB
    private AcaciaSessionLocal acaciaSession;


    /*@Override
    public List<AssemblingCategory> getAssemblingCategories(DataObject parent)
    {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("AssemblingCategory.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parent.getDataObjectId());
        }
        else
        {
            q = em.createNamedQuery("AssemblingCategory.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<AssemblingCategory>(q.getResultList());
    }*/

    @Override
    public boolean deleteAssemblingSchema(AssemblingSchema assemblingSchema)
    {
        esm.remove(em, assemblingSchema);
        return true;
    }

    @Override
    public boolean deleteAssemblingSchemaItem(AssemblingSchemaItem schemaItem)
    {
        esm.remove(em, schemaItem);
        return true;
    }

    @Override
    public boolean deleteAssemblingSchemaItemValue(AssemblingSchemaItemValue itemValue)
    {
        esm.remove(em, itemValue);
        return true;
    }

    @Override
    public boolean deleteAssemblingCategory(AssemblingCategory assemblingCategory)
    {
        esm.remove(em, assemblingCategory);
        return true;
    }

    @Override
    public boolean deleteAssemblingCategories(List<AssemblingCategory> categories)
    {
        for(AssemblingCategory category : categories)
        {
            esm.remove(em, category);
        }

        return true;
    }

    @Override
    public boolean deleteAssemblingMessage(AssemblingMessage assemblingMessage)
    {
        esm.remove(em, assemblingMessage);
        return true;
    }

    @Override
    public List<AssemblingCategory> getAssemblingCategories(
        AssemblingCategory parent,
        Boolean allHeirs)
    {
        return getChildrenAssemblingCategories(parent, allHeirs);
    }

    private List<AssemblingCategory> getChildrenAssemblingCategories(
        AssemblingCategory parent,
        boolean allHeirs)
    {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("AssemblingCategory.findByParentCategory");
            q.setParameter("parentCategory", parent);
        }
        else
        {
            q = em.createNamedQuery("AssemblingCategory.findByParentCategoryIsNull");
        }
        q.setParameter("deleted", false);
        Organization organization = acaciaSession.getOrganization();
        q.setParameter("parentId", organization.getId());
        List<AssemblingCategory> result = new ArrayList<AssemblingCategory>(q.getResultList());

        int size;
        if(allHeirs && (size = result.size()) > 0)
        {
            for(int i = 0; i < size; i++)
            {
                AssemblingCategory category = result.get(i);
                result.addAll(getChildrenAssemblingCategories(category, allHeirs));
            }
        }

        return result;
    }

    @Override
    public int getAssemblingCategoryChildCount(AssemblingCategory parent)
    {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("AssemblingCategory.findChildCountByParentCategory");
            q.setParameter("parentCategory", parent);
        }
        else
        {
            q = em.createNamedQuery("AssemblingCategory.findChildCountByParentCategoryIsNull");
        }
        q.setParameter("deleted", false);
        Organization organization = acaciaSession.getOrganization();
        q.setParameter("parentId", organization.getId());
        Long count = (Long)q.getSingleResult();
        return count.intValue();
    }

    @Override
    public AssemblingSchema newAssemblingSchema()
    {
        return newAssemblingSchema(null);
    }

    @Override
    public AssemblingSchema newAssemblingSchema(AssemblingCategory assemblingCategory)
    {
        AssemblingSchema assemblingSchema = new AssemblingSchema();
        assemblingSchema.setParentId(acaciaSession.getOrganization().getId());
        if(assemblingCategory != null)
            assemblingSchema.setAssemblingCategory(assemblingCategory);
        assemblingSchema.setMeasureUnit(MeasurementUnit.Piece.getDbResource());

        return assemblingSchema;
    }

    @Override
    public AssemblingSchemaItem newAssemblingSchemaItem(AssemblingSchema assemblingSchema)
    {
        AssemblingSchemaItem schemaItem = new AssemblingSchemaItem();
        schemaItem.setAssemblingSchema(assemblingSchema);
        schemaItem.setDataType(getDbResource(DataType.IntegerType));
        schemaItem.setQuantity(BigDecimal.ONE);

        return schemaItem;
    }

    @Override
    public AssemblingSchemaItemValue newAssemblingSchemaItemValue(AssemblingSchemaItem schemaItem)
    {
        AssemblingSchemaItemValue itemValue = new AssemblingSchemaItemValue();
        itemValue.setAssemblingSchemaItem(schemaItem);
        return itemValue;
    }

    @Override
    public AssemblingCategory newAssemblingCategory(AssemblingCategory parentCategory)
    {
        AssemblingCategory newObject = new AssemblingCategory();
        newObject.setParentCategory(parentCategory);
        newObject.setParentId(acaciaSession.getOrganization().getId());

        return newObject;
    }

    @Override
    public AssemblingMessage newAssemblingMessage()
    {
        AssemblingMessage newObject = new AssemblingMessage();
        newObject.setOrganization(acaciaSession.getOrganization());

        return newObject;
    }

    @Override
    public AssemblingCategory saveCategory(AssemblingCategory entity)
    {
        assemblingCategoryValidator.validate(entity); 

        esm.persist(em, entity);
        return entity; 
    }

    @Override
    public AssemblingSchema saveSchema(AssemblingSchema entity)
    {
        //assemblingCategoryValidator.validate(entity); 

        esm.persist(em, entity);
        return entity; 
    }

    @Override
    public AssemblingSchemaItem saveSchemaItem(AssemblingSchemaItem entity)
    {
        logger.debug("saveSchemaItem: " + entity.toString(true));
        assemblingSchemaItemValidator.validate(entity);

        esm.persist(em, entity);
        logger.debug("saveSchemaItem: " + entity.toString(true));
        return entity; 
    }

    @Override
    public AssemblingSchemaItemValue saveItemValue(AssemblingSchemaItemValue entity)
    {
        assemblingSchemaItemValidator.validate(entity);

        esm.persist(em, entity);
        return entity;
    }

    @Override
    public ComplexProduct saveComplexProduct(ComplexProduct complexProduct)
    {
        logger.info("saveComplexProduct: " + complexProduct.toString(true));

        esm.persist(em, complexProduct);
        List<ComplexProductItem> items = complexProduct.getComplexProductItems();
        if(items != null && items.size() > 0)
        {
            for(ComplexProductItem item : items)
            {
                saveComplexProductItem(item);
            }
        }

        return complexProduct;
    }

    public ComplexProductItem saveComplexProductItem(ComplexProductItem productItem)
    {
        logger.info("saveComplexProductItem: " + productItem);
        Product product = productItem.getProduct();
        logger.info("product: " + product);
        if(product instanceof ComplexProduct)
        {
            saveComplexProduct((ComplexProduct)product);
        }

        esm.persist(em, productItem);

        return productItem;
    }

    @Override
    public AssemblingMessage saveAssemblingMessage(AssemblingMessage assemblingMessage)
    {
        //assemblingCategoryValidator.validate(entity);

        esm.persist(em, assemblingMessage);
        return assemblingMessage;
    }

    @Override
    public AssemblingCategory updateParents(
        AssemblingCategory newParent,
        AssemblingCategory newChild)
    {
        if(newParent != null)
        {
            ValidationException ve = new ValidationException();

            // check cyclic parents
            AssemblingCategory ancestor = newParent;
            BigInteger parentId;
            while(ancestor != null)
            {
                if(ancestor.equals(newChild))
                {
                    ve.addMessage("parentCategory", "AssemblingCategory.err.cyclicParent");
                    break;
                }

                //ancestor = ancestor.getParentCategory();
                if((parentId = ancestor.getParentId()) != null)
                    ancestor = em.find(AssemblingCategory.class, parentId);
                else
                    ancestor = null;
            }

            // if we have validation messages - throw the exception since not everything is OK
            if(!ve.getMessages().isEmpty())
                throw ve;

            //merge the parent
            newParent = em.merge(newParent);
        }

        //newParent may be null here - but no problem
        //newChildren.setParentCategory(newParent);
        if(newParent != null)
            newChild.setParentId(newParent.getAssemblingCategoryId());
        else
            newChild.setParentId(null);
        esm.persist(em, newChild);

        return newChild;
    }

    @Override
    public AssemblingCategory getParentCategory(AssemblingCategory child)
    {
        BigInteger parentId;
        if((parentId = child.getParentId()) == null)
            return null;

        return em.find(AssemblingCategory.class, parentId);
    }

    /*@Override
    public List<AssemblingSchema> getAssemblingSchemas(boolean applicable)
    {
        Organization organization = acaciaSessionLocal.getOrganization();
        BigInteger parentId;
        if(organization == null || (parentId = organization.getId()) == null)
            return Collections.emptyList();

        Query q = em.createNamedQuery("AssemblingSchema.findByParentId");
        q.setParameter("parentId", parentId);
        q.setParameter("applicable", applicable);
        q.setParameter("deleted", false);
        return new ArrayList<AssemblingSchema>(q.getResultList());
    }*/

    @Override
    public List<AssemblingSchema> getAssemblingSchemas(
            AssemblingCategory assemblingCategory,
            Boolean applicable)
    {
        logger.info("getAssemblingSchemas(assemblingCategory=" + assemblingCategory +
                ", applicable=" + applicable + ")");
        Query q;
        if(assemblingCategory != null)
        {
            if(applicable)
                q = em.createNamedQuery("AssemblingSchema.findByAssemblingCategoryAndApplicable");
            else
                q = em.createNamedQuery("AssemblingSchema.findByAssemblingCategory");
            q.setParameter("assemblingCategory", assemblingCategory);
        }
        else
        {
            if(applicable)
                q = em.createNamedQuery("AssemblingSchema.findByParentIdAndApplicable");
            else
                q = em.createNamedQuery("AssemblingSchema.findByParentId");
            q.setParameter("parentId", acaciaSession.getOrganization().getId());
        }
        q.setParameter("deleted", false);
        return new ArrayList<AssemblingSchema>(q.getResultList());
    }

    @Override
    public List<AssemblingSchemaItem> getAssemblingSchemaItems(AssemblingSchema assemblingSchema)
    {
        if(assemblingSchema == null)
            return Collections.emptyList();

        Query q = em.createNamedQuery("AssemblingSchemaItem.findByAssemblingSchema");
        q.setParameter("assemblingSchema", assemblingSchema);
        q.setParameter("deleted", false);
        List<AssemblingSchemaItem> schemaItems = q.getResultList();
        return new ArrayList<AssemblingSchemaItem>(schemaItems);
    }

    @Override
    public List<AssemblingSchemaItemValue> getAssemblingSchemaItemValues(AssemblingSchemaItem assemblingSchemaItem)
    {
        if(assemblingSchemaItem == null)
            return Collections.emptyList();

        Query q = em.createNamedQuery("AssemblingSchemaItemValue.findByAssemblingSchemaItem");
        q.setParameter("assemblingSchemaItem", assemblingSchemaItem);
        q.setParameter("deleted", false);
        return new ArrayList<AssemblingSchemaItemValue>(q.getResultList());
    }

    @Override
    public List<VirtualProduct> getVirtualProducts()
    {
        logger.info("AssemblingBean.getVirtualProducts()");
        Organization organization = acaciaSession.getOrganization();
        BigInteger parentId;
        if(organization == null || (parentId = organization.getId()) == null)
            return Collections.emptyList();

        synchronizeRealProducts(parentId);
        Query q = em.createNamedQuery("VirtualProduct.findByParentId");
        q.setParameter("parentId", parentId);

        return new ArrayList<VirtualProduct>(q.getResultList());
    }

    private void synchronizeRealProducts(BigInteger parentId)
    {
        logger.info("AssemblingBean.synchronizeRealProducts(" + parentId + ")");
        Query q = em.createNamedQuery("RealProduct.findNewSimpleProducts");
        q.setParameter("parentId", parentId);
        List<SimpleProduct> newSP = q.getResultList();
        if(newSP != null && newSP.size() > 0)
        {
            for(SimpleProduct sp : newSP)
            {
                RealProduct realProduct = new RealProduct(sp);
                realProduct.setParentId(acaciaSession.getOrganization().getId());
                esm.persist(em, realProduct);
            }
        }

        q = em.createNamedQuery("RealProduct.findDeletedSimpleProducts");
        q.setParameter("parentId", parentId);
        List<RealProduct> deletedRP = q.getResultList();
        if(deletedRP != null && deletedRP.size() > 0)
        {
            for(RealProduct rp : deletedRP)
            {
                esm.remove(em, rp);
            }
        }
    }

    @Override
    public List<AssemblingMessage> getAssemblingMessages()
    {
        Organization organization = acaciaSession.getOrganization();
        logger.info("getAssemblingMessages().organization: " + organization);
        if(organization == null || organization.getId() == null)
            return Collections.emptyList();

        Query q = em.createNamedQuery("AssemblingMessage.findByOrganization");
        q.setParameter("organization", organization);

        return new ArrayList<AssemblingMessage>(q.getResultList());
    }

    @Override
    public RealProduct getRealProduct(SimpleProduct simpleProduct)
    {
        logger.info("AssemblingBean.getRealProduct(" + simpleProduct + ")");
        Query q = em.createNamedQuery("RealProduct.findBySimpleProduct");
        q.setParameter("parentId", simpleProduct.getParentId());
        q.setParameter("simpleProduct", simpleProduct);
        try
        {
            return (RealProduct)q.getSingleResult();
        }
        catch(NoResultException ex)
        {
            RealProduct realProduct = new RealProduct(simpleProduct);
            realProduct.setParentId(acaciaSession.getOrganization().getId());
            esm.persist(em, realProduct);
            return realProduct;
        }
    }

    @Override
    public List<ComplexProductItem> getComplexProductItems(ComplexProduct complexProduct)
    {
        logger.info("getComplexProductItems(complexProduct=" + complexProduct + ")");
        Query q = em.createNamedQuery("ComplexProductItem.findItemsByComplexProductAndDeleted");
        q.setParameter("complexProduct", complexProduct);
        q.setParameter("deleted", false);
        return new ArrayList<ComplexProductItem>(q.getResultList());
    }

    @Override
    public int getComplexProductItemsCount(ComplexProduct complexProduct)
    {
        logger.info("getComplexProductItems(complexProduct=" + complexProduct + ")");
        Query q = em.createNamedQuery("ComplexProductItem.findItemsCountByComplexProductAndDeleted");
        q.setParameter("complexProduct", complexProduct);
        q.setParameter("deleted", false);
        Object object = q.getSingleResult();
        Number result = (Number)object;
        return result.intValue();
    }

    @Override
    public List<InvoiceItem> getInvoiceItems(Invoice invoice)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getInvoiceItemsCount(Invoice invoice)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityProperties getAssemblingCategoryEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(AssemblingCategory.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getAssemblingSchemaEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(AssemblingSchema.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getAssemblingSchemaItemEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(AssemblingSchemaItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getAssemblingSchemaItemValueEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(AssemblingSchemaItemValue.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getVirtualProductItemEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(VirtualProduct.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getComplexProductEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(ComplexProduct.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getComplexProductItemEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(ComplexProductItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getAssemblingMessageEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(AssemblingMessage.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getAssemblingParameterEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(AssemblingParameter.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getProductSelectionRowEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(ProductSelectionRow.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ);
        return entityProperties;
    }

    @Override
    public List<DbResource> getAlgorithms()
    {
        return Algorithm.Type.getDbResources();
    }

    @Override
    public List<DbResource> getDataTypes()
    {
        return DataType.getDbResources();
    }

    @Override
    public List<DbResource> getMeasureUnits()
    {
        return MeasurementUnit.getDbResources();
    }

    @Override
    public DbResource getDbResource(DatabaseResource databaseResource)
    {
        return databaseResource.getDbResource();
    }

    @Override
    public DataObjectBean getParentProduct(Product product)
    {
        BigInteger parentId = product.getParentId();
        if(parentId == null)
            return null;

        return esm.getDataObjectBean(em, parentId);
    }

    @Override
    public List<Product> getChildProducts(DataObjectBean parent)
    {
        Query q;
        if(parent == null)
        {
            q = em.createNamedQuery("Product.findByParentDataObjectIsNullAndDeleted");
        }
        else
        {
            q = em.createNamedQuery("Product.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parent.getId());
        }
        q.setParameter("deleted", false);

        return new ArrayList<Product>(q.getResultList());
    }

    @Override
    public List<String> getPropertyKeys(AssemblingSchema schema, AssemblingMessage message) {
        String categoryCode = schema.getAssemblingCategory().getCategoryCode();
        String schemaCode = schema.getSchemaCode();
        String messageCode = message.getMessageCode();

        List<String> propertyKeys = new ArrayList<String>(4);
        propertyKeys.add(categoryCode + "." + schemaCode + "." + messageCode);
        propertyKeys.add(categoryCode + "." + messageCode);
        propertyKeys.add(schemaCode + "." + messageCode);
        propertyKeys.add(messageCode);

        return propertyKeys;
    }


}
