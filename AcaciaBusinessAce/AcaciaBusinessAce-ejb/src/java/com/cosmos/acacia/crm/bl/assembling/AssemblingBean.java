/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import java.util.ArrayList;
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
            q.setParameter("parentDataObject", parent);
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
        newObject.setParentCategory(parentCategory);
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
    public AssemblingCategory updateParents(AssemblingCategory newParent, AssemblingCategory newChildren)
    {
        if(newParent != null)
        {
            ValidationException ve = new ValidationException();

            // check cyclic parents
            AssemblingCategory ancestor = newParent;
            while(ancestor != null)
            {
                if(ancestor.equals(newChildren))
                {
                    ve.addMessage("parentCategory", "AssemblingCategory.err.cyclicParent");
                    break;
                }
                ancestor = ancestor.getParentCategory();
            }

            // if we have validation messages - throw the exception since not everything is OK
            if(!ve.getMessages().isEmpty())
                throw ve;

            //merge the parent
            newParent = em.merge(newParent);
        }

        //newParent may be null here - but no problem
        newChildren.setParentCategory(newParent);
        esm.persist(em, newChildren);

        return newChildren;
    }
}
