/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.validation.ValidationException;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Miro
 */
@Stateless
public class AssemblingSchemaItemValidatorBean
    implements AssemblingSchemaItemValidatorLocal
{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void validate(AssemblingSchemaItem schemaItem)
        throws ValidationException
    {
        ValidationException ve = new ValidationException();

        DbResource resource = schemaItem.getDataType();
        if(resource != null)
        {
            AssemblingSchemaItem.DataType dataType =
                (AssemblingSchemaItem.DataType)resource.getEnumValue();
            try
            {
                Serializable value = schemaItem.getDefaultValue();
                if(value != null)
                {
                    value = dataType.toDataType(value);
                    schemaItem.setDefaultValue(value);
                }
            }
            catch(Throwable ex)
            {
                ve.addMessage("defaultValue", "AssemblingSchemaItem.err.IllegalDefaultValue", ex.getMessage());
            }
        }
        else
        {
            ve.addMessage("dataType", "AssemblingSchemaItem.err.emptyDataType");
        }

        //AssemblingSchemaItemDataType

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

    @Override
    public void validate(AssemblingSchemaItemValue itemValue)
        throws ValidationException
    {
        ValidationException ve = new ValidationException();

        AssemblingSchemaItem schemaItem = itemValue.getAssemblingSchemaItem();
        DbResource resource = schemaItem.getDataType();
        if(resource != null)
        {
            AssemblingSchemaItem.DataType dataType =
                (AssemblingSchemaItem.DataType)resource.getEnumValue();
            try
            {
                Serializable value = itemValue.getMinConstraint();
                if(value != null)
                {
                    value = dataType.toDataType(value);
                    itemValue.setMinConstraint(value);
                }
            }
            catch(Throwable ex)
            {
                ve.addMessage("minConstraint", "AssemblingSchemaItemValue.err.IllegalMinConstraint", ex.getMessage());
            }

            try
            {
                Serializable value = itemValue.getMaxConstraint();
                if(value != null)
                {
                    value = dataType.toDataType(value);
                    itemValue.setMaxConstraint(value);
                }
            }
            catch(Throwable ex)
            {
                ve.addMessage("maxConstraint", "AssemblingSchemaItemValue.err.IllegalMaxConstraint", ex.getMessage());
            }
        }
        else
        {
            ve.addMessage("dataType", "AssemblingSchemaItem.err.emptyDataType");
        }

        //if we have validation messages - throw the exception since not everything is OK
        if(!ve.getMessages().isEmpty())
            throw ve;
    }
}
