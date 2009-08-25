package com.cosmos.acacia.crm.bl.purchaseorder;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.purchase.OrderConfirmation;
import com.cosmos.acacia.crm.data.purchase.OrderConfirmationItem;
import com.cosmos.acacia.crm.data.purchase.PurchaseOrder;
import com.cosmos.acacia.crm.data.purchase.PurchaseOrderItem;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.warehouse.WarehouseProduct;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	14.07.2008
 * @author	Petar Milev
 *
 */
@Remote
public interface OrderConfirmationListRemote {

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
    List<OrderConfirmation> listOrderConfirmations(UUID parentDataObjectId);

    /**
     * Deletes the order, - if the integrity is violated, throws an {@link ValidationException} 
     * @param OrderConfirmation - not null
     */
    void deleteOrderConfirmation(OrderConfirmation OrderConfirmation);

    /**
     * Create new instance of {@link OrderConfirmation}.
     * 
     * @param parentDataObjectId - may be null
     * @return not null
     */
    OrderConfirmation newOrderConfirmation(UUID parentDataObjectId);

    /**
     * Return entity properties for detailed view
     * @return not null
     */
    EntityProperties getDetailEntityProperties();

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
    OrderConfirmation saveOrderConfirmation(OrderConfirmation po);

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
    List<OrderConfirmationItem> getOrderItems(UUID parentDataObjectId);

    /**
     * Delete order item
     * @param item
     */
    void deleteOrderItem(OrderConfirmationItem item);

    /**
     * Create new order item for a given order
     * @param parentDataObjectId
     * @return
     */
    OrderConfirmationItem newOrderItem(UUID parentDataObjectId);

    /**
     * Save an item
     * @param entity
     * @return
     */
    OrderConfirmationItem saveOrderItem(OrderConfirmationItem entity);

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
     * Document types possibilites
     * @return
     */
    List<DbResource> getDocumentTypes();

    /**
     * Enum currencies
     * @return
     */
    List<DbResource> getCurrencies();

    /**
     * Get all items from the specified parent (order confirmation document), which have free quantity for matching
     * @param parentId
     * @return
     */
    List<OrderConfirmationItem> getPendingItems(UUID parentId);

    /**
     * Return all order confirmations that have free items for matching.
     * @param parentDataObjectId
     * @param forBranch 
     * @return
     */
    List<OrderConfirmation> getPendingConfirmations(UUID parentDataObjectId, Address branch);

    /**
     * Match {@link OrderConfirmationItem} with {@link PurchaseOrderItem}
     * @param confirmationitem
     * @param orderItem
     * @param matchQuantity - may be null. Restrict the quantity to match to this value. If not provided, maximum possible quantity is matched. 
     */
    void matchConfirmationItem(OrderConfirmationItem confirmationitem, PurchaseOrderItem orderItem, BigDecimal matchQuantity);

    /**
     * Automatically matches every item from the order confirmation against the purchase order
     * @param orderConfirmation
     * @param purchaseOrder
     */
    void matchOrderConfirmation(OrderConfirmation orderConfirmation, PurchaseOrder purchaseOrder);
}
