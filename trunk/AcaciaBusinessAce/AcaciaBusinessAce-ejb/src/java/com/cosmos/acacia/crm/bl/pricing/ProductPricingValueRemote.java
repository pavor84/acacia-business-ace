package com.cosmos.acacia.crm.bl.pricing;

import java.util.UUID;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.product.ProductPercentValue;
import com.cosmos.acacia.crm.data.product.ProductPercentValue.Type;
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
    List<ProductPercentValue> listProductPricingValues(UUID parentDataObjectId, Type type);

    /**
     * Deletes the value, - if the integrity is violated, throws an {@link ValidationException} 
     * @param value - not null
     */
    void deleteProductPricingValue(ProductPercentValue value);

    /**
     * Create new instance of {@link ProductPercentValue}.
     * 
     * @param parentDataObjectId - may be null
     * @return not null
     */
    ProductPercentValue newProductPricingValue(UUID parentDataObjectId, Type type);

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
    ProductPercentValue saveProductPricingValue(ProductPercentValue entity);
}
