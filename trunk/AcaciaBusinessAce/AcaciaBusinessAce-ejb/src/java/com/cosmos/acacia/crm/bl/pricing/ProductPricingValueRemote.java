package com.cosmos.acacia.crm.bl.pricing;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.ProductPricingValue;
import com.cosmos.acacia.crm.data.ProductPricingValue.Type;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	10.07.2008
 * @author	Petar Milev
 *
 */
@Remote
public interface ProductPricingValueRemote {

    /**
     * Create entity properties object for listing object from this type.
     * @return not null
     */
    EntityProperties getListingEntityProperties();

    /**
     * Return all values for a given parent.
     * @param parentDataObjectId - mandatory
     * @param type
     * @return not null list
     */
    List<ProductPricingValue> listProductPricingValues(BigInteger parentDataObjectId, Type type);

    /**
     * Deletes the value, - if the integrity is violated, throws an {@link ValidationException} 
     * @param value - not null
     */
    void deleteProductPricingValue(ProductPricingValue value);

    /**
     * Create new instance of {@link ProductPricingValue}.
     * 
     * @param parentDataObjectId - may be null
     * @return not null
     */
    ProductPricingValue newProductPricingValue(BigInteger parentDataObjectId, Type type);

    /**
     * Return entity properties for detailed view
     * @return not null
     */
    EntityProperties getDetailEntityProperties();

    /**
     * Save - {@link ValidationException} on failure
     * @param entity
     * @return
     */
    ProductPricingValue saveProductPricingValue(ProductPricingValue entity);
}
