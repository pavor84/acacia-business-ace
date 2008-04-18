/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.validation.impl;

import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.validation.EntityValidator;
import javax.ejb.Local;

/**
 *
 * @author jchan
 */
@Local
public interface ProductValidatorLocal extends EntityValidator<SimpleProduct>{
     
}
