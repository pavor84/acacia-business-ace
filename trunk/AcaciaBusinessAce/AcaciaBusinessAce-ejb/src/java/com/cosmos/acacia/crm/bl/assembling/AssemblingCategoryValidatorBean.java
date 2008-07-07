/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationUtil;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class AssemblingCategoryValidatorBean
    implements AssemblingCategoryValidatorLocal
{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(AssemblingCategory entity)
        throws ValidationException
    {
        ValidationException ve = new ValidationException();
        
        /*BigInteger parentId = entity.getParentId();
        if(parentId != null)
        {
            //unique code
            Query q = em.createNamedQuery("AssemblingCategory.findByCodeNotDeleted");
            q.setParameter("categoryCode", entity.getCategoryName());
            q.setParameter("parentId", parentId);
            if(!ValidationUtil.checkUnique(q.getResultList(), entity))
                ve.addMessage("categoryCode", "AssemblingCategory.err.codeInUse");

            //unique name 
            q = em.createNamedQuery("AssemblingCategory.findByNameNotDeleted");
            q.setParameter("categoryName", entity.getCategoryName());
            q.setParameter("parentId", parentId);
            if(!ValidationUtil.checkUnique(q.getResultList(), entity))
                ve.addMessage("categoryName", "AssemblingCategory.err.nameInUse");
        }
        else
        {
            //unique code
            Query q = em.createNamedQuery("AssemblingCategory.findByCodeNotDeletedAndParentNull");
            q.setParameter("categoryCode", entity.getCategoryName());
            if(!ValidationUtil.checkUnique(q.getResultList(), entity))
                ve.addMessage("categoryCode", "AssemblingCategory.err.codeInUse");

            //unique name 
            q = em.createNamedQuery("AssemblingCategory.findByNameNotDeletedAndParentNull");
            q.setParameter("categoryName", entity.getCategoryName());
            if(!ValidationUtil.checkUnique(q.getResultList(), entity))
                ve.addMessage("categoryName", "AssemblingCategory.err.nameInUse");
        }
        
        //check cyclic parents
        //AssemblingCategory ancestor = entity.getParentCategory();
        AssemblingCategory ancestor;
        if((parentId = entity.getParentId()) != null)
            ancestor = em.find(AssemblingCategory.class, parentId);
        else
            ancestor = null;
        while(ancestor != null)
        {
            if(ancestor.equals(entity))
            {
                ve.addMessage("parentCategory", "AssemblingCategory.err.cyclicParent");
                break;
            }

            //ancestor = ancestor.getParentCategory();
            if((parentId = ancestor.getParentId()) != null)
                ancestor = em.find(AssemblingCategory.class, parentId);
            else
                ancestor = null;
        }*/
        
        //if we have validation messages - throw the exception since not everything is OK
        if(!ve.getMessages().isEmpty())
            throw ve;
    }


}
