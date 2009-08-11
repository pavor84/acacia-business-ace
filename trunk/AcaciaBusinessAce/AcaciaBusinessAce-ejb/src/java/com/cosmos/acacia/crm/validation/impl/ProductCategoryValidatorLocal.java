package com.cosmos.acacia.crm.validation.impl;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.product.ProductCategory;
import com.cosmos.acacia.crm.validation.EntityValidator;

/**
 * Created	:	21.04.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Local
public interface ProductCategoryValidatorLocal extends EntityValidator<ProductCategory> {

}
