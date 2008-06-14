/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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


    @Override
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
    }

    @Override
    public EntityProperties getAssemblingCategoryEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(AssemblingCategory.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
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
    public AssemblingCategory newAssemblingCategory(AssemblingCategory parentCategory)
    {
        AssemblingCategory newObject = new AssemblingCategory();
        //newObject.setParentCategory(parentCategory);
        if(parentCategory != null)
            newObject.setParentId(parentCategory.getAssemblingCategoryId());

        return newObject;
    }

    @Override
    public AssemblingCategory save(AssemblingCategory entity)
    {
        assemblingCategoryValidator.validate(entity); 

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
    public List<AssemblingSchema> getAssemblingSchemas(AssemblingCategory assemblingCategory)
    {
        if(assemblingCategory == null)
            return Collections.emptyList();

        Query q = em.createNamedQuery("AssemblingSchema.findByAssemblingCategory");
        q.setParameter("assemblingCategory", assemblingCategory);
        q.setParameter("deleted", false);
        return new ArrayList<AssemblingSchema>(q.getResultList());
    }

    @Override
    public List<AssemblingSchemaItem> getAssemblingSchemaItems(AssemblingSchema assemblingSchema)
    {
        return null;
    }

    @Override
    public List<AssemblingSchemaItemValue> getAssemblingSchemaItemValues(AssemblingSchemaItem assemblingSchemaItem)
    {
        return null;
    }

    @Override
    public EntityProperties getAssemblingSchemaEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(AssemblingSchema.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }


}
