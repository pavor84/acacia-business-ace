/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.validation.EntityValidator;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface AssemblingSchemaItemValidatorLocal
     extends EntityValidator<AssemblingSchemaItem>
{
}
