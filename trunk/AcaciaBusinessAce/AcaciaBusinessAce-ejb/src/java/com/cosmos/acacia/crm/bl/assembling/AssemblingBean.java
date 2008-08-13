/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.assembling.Algorithm;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.RealProduct;
import com.cosmos.acacia.crm.data.assembling.VirtualProduct;
import com.cosmos.acacia.crm.enums.AssemblingSchemaItemDataType;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
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
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author Miro
 */
@Stateless
public class AssemblingBean
    implements AssemblingRemote, AssemblingLocal
{
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private AssemblingCategoryValidatorLocal assemblingCategoryValidator;
    
    @EJB
    private AcaciaSessionLocal acaciaSessionLocal;


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
    public EntityProperties getAssemblingCategoryEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(AssemblingCategory.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
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
        Organization organization = acaciaSessionLocal.getOrganization();
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
        Organization organization = acaciaSessionLocal.getOrganization();
        q.setParameter("parentId", organization.getId());
        Long count = (Long)q.getSingleResult();
        return count.intValue();
    }

    @Override
    public AssemblingCategory newAssemblingCategory(AssemblingCategory parentCategory)
    {
        AssemblingCategory newObject = new AssemblingCategory();
        newObject.setParentCategory(parentCategory);
        newObject.setParentId(acaciaSessionLocal.getOrganization().getId());

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
        //assemblingCategoryValidator.validate(entity); 

        esm.persist(em, entity);
        return entity; 
    }

    @Override
    public AssemblingSchemaItemValue saveItemValue(AssemblingSchemaItemValue entity)
    {
        //assemblingCategoryValidator.validate(entity); 

        esm.persist(em, entity);
        return entity; 
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
    public AssemblingCategory getParent(AssemblingCategory child)
    {
        BigInteger parentId;
        if((parentId = child.getParentId()) == null)
            return null;

        return em.find(AssemblingCategory.class, parentId);
    }

    @Override
    public List<AssemblingSchema> getAssemblingSchemas(Organization organization)
    {
        BigInteger parentId;
        if(organization == null || (parentId = organization.getId()) == null)
            return Collections.emptyList();

        Query q = em.createNamedQuery("AssemblingSchema.findByParentId");
        q.setParameter("parentId", parentId);
        q.setParameter("deleted", false);
        return new ArrayList<AssemblingSchema>(q.getResultList());
    }

    @Override
    public List<AssemblingSchema> getAssemblingSchemas(AssemblingCategory assemblingCategory)
    {
        Query q;
        if(assemblingCategory != null) {
            q = em.createNamedQuery("AssemblingSchema.findByAssemblingCategory");
            q.setParameter("assemblingCategory", assemblingCategory);
        } else {
            q = em.createNamedQuery("AssemblingSchema.findByParentId");
            q.setParameter("parentId", acaciaSessionLocal.getOrganization().getId());
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
        return new ArrayList<AssemblingSchemaItem>(q.getResultList());
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
    public List<VirtualProduct> getVirtualProducts(Organization organization)
    {
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
        Query q = em.createNamedQuery("RealProduct.findNewSimpleProducts");
        q.setParameter("parentId", parentId);
        List<SimpleProduct> newSP = q.getResultList();
        if(newSP != null && newSP.size() > 0)
        {
            for(SimpleProduct sp : newSP)
            {
                esm.persist(em, new RealProduct(sp));
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
    public RealProduct getRealProduct(SimpleProduct simpleProduct)
    {
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
            esm.persist(em, realProduct);
            return realProduct;
        }
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
    public List<DbResource> getAlgorithms()
    {
        return Algorithm.Type.getDbResources();
    }

    @Override
    public List<DbResource> getDataTypes()
    {
        return AssemblingSchemaItemDataType.getDbResources();
    }

}
