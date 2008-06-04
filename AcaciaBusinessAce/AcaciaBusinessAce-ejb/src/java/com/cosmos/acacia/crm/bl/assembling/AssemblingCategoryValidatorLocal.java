/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.assembling;

import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.validation.EntityValidator;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface AssemblingCategoryValidatorLocal
     extends EntityValidator<AssemblingCategory>
{
}
