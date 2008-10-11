/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.util.AcaciaUtils;
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
        AcaciaUtils.validateValue(schemaItem);
    }

    @Override
    public void validate(AssemblingSchemaItemValue itemValue)
        throws ValidationException
    {
        AcaciaUtils.validateValue(itemValue);
    }
}
