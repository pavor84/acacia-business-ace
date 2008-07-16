package com.cosmos.acacia.crm.bl.purchaseorder;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.PurchaseOrder;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	14.07.2008
 * @author	Petar Milev
 *
 */
@Remote
public interface PurchaseOrderListRemote {

    /**
     * Create entity properties object for listing object from this type.
     * @return not null
     */
    EntityProperties getListingEntityProperties();

    /**
     * Return all orders for a given parent.
     * @param parentDataObjectId - mandatory
     * @return not null list
     */
    List<PurchaseOrder> listPurchaseOrders(BigInteger parentDataObjectId);

    /**
     * Deletes the order, - if the integrity is violated, throws an {@link ValidationException} 
     * @param purchaseOrder - not null
     */
    void deletePurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * Create new instance of {@link PurchaseOrder}.
     * 
     * @param parentDataObjectId - may be null
     * @return not null
     */
    PurchaseOrder newPurchaseOrder(BigInteger parentDataObjectId);

    /**
     * Return entity properties for detailed view
     * @return not null
     */
    EntityProperties getDetailEntityProperties();

    /**
     * List possible delivery methods 
     * @return not null list
     */
    List<DbResource> getDeliveryMethods();
    
}
