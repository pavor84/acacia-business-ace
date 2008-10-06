/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.validation.EntityValidator;
import com.cosmos.acacia.crm.validation.ValidationException;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface AssemblingSchemaItemValidatorLocal
{
    /**
     * If the validation fails the method is expected to throw ValidationException
     * @see ValidationException.
     * @param entity
     * @throws ValidationException
     */
    void validate(AssemblingSchemaItem schemaItem)
        throws ValidationException;

    void validate(AssemblingSchemaItemValue itemValue)
        throws ValidationException;
}
