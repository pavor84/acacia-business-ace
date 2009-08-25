package com.cosmos.acacia.crm.bl.purchaseorder;

import java.util.UUID;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.purchase.PurchaseOrder;
import com.cosmos.acacia.crm.data.purchase.PurchaseOrderItem;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.warehouse.WarehouseProduct;
import com.cosmos.acacia.crm.enums.PurchaseOrderStatus;
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
     * @return not null list
     */
    List<PurchaseOrder> getPurchaseOrders();

    /**
     * Get all orders in status {@link PurchaseOrderStatus#Sent} or {@link PurchaseOrderStatus#PartlyConfirmed}
     * @param parentDataObjectId
     * @param branch for branch
     * @return
     */
    List<PurchaseOrder> getPendingPurchaseOrders();

    /**
     * Get all orders in status {@link PurchaseOrderStatus#Sent} or {@link PurchaseOrderStatus#PartlyConfirmed}
     * for a given supplier in a given branch ( or all branches if null )
     * @param parentDataObjectId - not null
     * @param supplier - not null
     * @param branch - may be null
     * @return
     */
    List<PurchaseOrder> getPendingPurchaseOrders(BusinessPartner supplier);

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
    PurchaseOrder newPurchaseOrder();

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

    /**
     * List possible statuses
     * @return not null list
     */
    List<DbResource> getStatuses();

    /**
     * Returns all contacts associated with this business partner for all his addresses/branches.
     * @param supplier
     * @return
     */
    List<ContactPerson> getSupplierContacts(BusinessPartner supplier);

    /**
     * Persists the current purchase order, fails with {@link ValidationException} if something is
     * not right
     * @param po
     */
    PurchaseOrder savePurchaseOrder(PurchaseOrder po);

    /**
     * Update the status of the order. The order is also validated before save.
     * @param order
     * @param status
     * @return - the updated order
     */
    PurchaseOrder updateOrderStatus(PurchaseOrder order, PurchaseOrderStatus status);

    /**
     * Return the entity properties for listing items.
     * @return
     */
    EntityProperties getItemsListEntityProperties();

    /**
     * List order items for a given order
     * @param parentDataObjectId
     * @return
     */
    List<PurchaseOrderItem> getOrderItems(UUID parentDataObjectId);

    /**
     * Delete order item
     * @param item
     */
    void deleteOrderItem(PurchaseOrderItem item);

    /**
     * Create new order item for a given order
     * @param parentDataObjectId
     * @return
     */
    PurchaseOrderItem newOrderItem(UUID parentDataObjectId);

    /**
     * Save an item
     * @param entity
     * @return
     */
    PurchaseOrderItem saveOrderItem(PurchaseOrderItem entity);

    /**
     * Find warehouse product for a given product. 
     * @param product
     * @return
     */
    WarehouseProduct getWarehouseProduct(SimpleProduct product);

    /**
     * Item details entity properties
     * @return
     */
    EntityProperties getItemDetailEntityProperties();

    /**
     * Currency enum
     * @return
     */
    List<DbResource> getCurrencies();

    /**
     * Persist all items in one transaction. Throws {@link ValidationException} if something fails.
     * @param orderItems
     */
    void saveOrderItems(List<PurchaseOrderItem> orderItems);

    /**
     * Get an order by its id
     * @param parentId
     * @return
     */
    PurchaseOrder getPurchaseOrder(UUID id);
}
