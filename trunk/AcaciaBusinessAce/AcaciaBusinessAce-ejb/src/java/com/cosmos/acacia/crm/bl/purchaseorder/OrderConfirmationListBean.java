package com.cosmos.acacia.crm.bl.purchaseorder;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.contactbook.AddressesListLocal;
import com.cosmos.acacia.crm.bl.contactbook.LocationsListLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.purchase.OrderConfirmation;
import com.cosmos.acacia.crm.data.purchase.OrderConfirmationItem;
import com.cosmos.acacia.crm.data.purchase.OrderItemMatch;
import com.cosmos.acacia.crm.data.purchase.PurchaseOrder;
import com.cosmos.acacia.crm.data.purchase.PurchaseOrderItem;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.warehouse.WarehouseProduct;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.OrderConfirmationType;
import com.cosmos.acacia.crm.enums.PurchaseOrderStatus;
import com.cosmos.acacia.crm.validation.impl.OrderConfirmationItemValidatorLocal;
import com.cosmos.acacia.crm.validation.impl.OrderConfirmationValidatorLocal;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 * Implement business logic behind the Purchase Orders Use Cases
 */
@Stateless
public class OrderConfirmationListBean implements OrderConfirmationListLocal, OrderConfirmationListRemote {
    @PersistenceContext
    private EntityManager em; 
 
    @EJB 
    private EntityStoreManagerLocal esm;
    
    @EJB
    private AcaciaSessionLocal acaciaSession;
    
    @EJB 
    private LocationsListLocal locationsList;
    
    @EJB 
    private AddressesListLocal addressesList;
    
    @EJB
    private OrderConfirmationItemValidatorLocal orderConfirmationItemValidator;
    
    @EJB
    private OrderConfirmationValidatorLocal orderConfirmationValidator;

    @EJB
    private PurchaseOrderListLocal purchaseOrderListLocal;

    @EJB
    private OrderItemMatchLocal orderItemMatchLocal;
    
    public EntityProperties getListingEntityProperties() {
        
        EntityProperties entityProperties = esm.getEntityProperties(OrderConfirmation.class);
        
        //let's keep the columns in the table a reasonable count, so remove all not-crucial information
        entityProperties.removePropertyDetails("supplierName");
        entityProperties.removePropertyDetails("supplierContactName");
        entityProperties.removePropertyDetails("notes");
        
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
        
    }

    @SuppressWarnings("unchecked")
    public List<OrderConfirmation> listOrderConfirmations(UUID parentDataObjectId, Address branch) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("OrderConfirmation.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        q.setParameter("branch", branch);
        
        List<OrderConfirmation> result = q.getResultList();
        return result;
    }

    
    public void deleteOrderConfirmation(OrderConfirmation OrderConfirmation) {
        if ( OrderConfirmation==null )
            throw new IllegalArgumentException("null: 'OrderConfirmation'");
        esm.remove(em, OrderConfirmation);
    }

    
    public OrderConfirmation newOrderConfirmation(UUID parentDataObjectId) {
        
        OrderConfirmation c = new OrderConfirmation();
        
        Address branch = acaciaSession.getBranch();
        
        c.setParentId(parentDataObjectId);
        c.setBranch(branch);
        c.setCurrency(Currency.BGN.getDbResource());
        c.setDocumentDate(new Date());
        c.setDocumentType(OrderConfirmationType.VatInvoice.getDbResource());
        c.setVat(new BigDecimal(20));
        c.setDiscountAmount(new BigDecimal(0));
        c.setDiscountPercent(new BigDecimal(0));
        c.setTransportationPrice(new BigDecimal(0));
        c.setInvoiceSubValue(new BigDecimal(0));
        c.setTotalValue(new BigDecimal(0));
        
        return c;
    }

    
    public EntityProperties getDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(OrderConfirmation.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    
    public List<ContactPerson> getSupplierContacts(BusinessPartner supplier) {
        if ( supplier == null )
            throw new IllegalArgumentException("not null supplier please!");
        
        Set<ContactPerson> result = new HashSet<ContactPerson>();
        
        List<Address> addrs = locationsList.getAddresses(supplier.getPartnerId());
        for (Address address : addrs) {
            List<ContactPerson> cPersons = addressesList.getContactPersons(address.getAddressId());
            result.addAll(cPersons);
        }
        
        return new ArrayList<ContactPerson>(result);
    }

    
    public OrderConfirmation saveOrderConfirmation(OrderConfirmation po) {
        orderConfirmationValidator.validate(po);
        
        esm.persist(em, po);
        
        return po;
    }
    
    public void deleteOrderItem(OrderConfirmationItem item) {
        esm.remove(em, item);
    }
    
    public EntityProperties getItemsListEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(OrderConfirmationItem.class);
        entityProperties.removePropertyDetails("notes");
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @SuppressWarnings("unchecked")
    
    public List<OrderConfirmationItem> getOrderItems(UUID parentDataObjectId) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("OrderConfirmationItem.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        
        List<OrderConfirmationItem> result = q.getResultList();
        return result;
    }

    
    public OrderConfirmationItem newOrderItem(UUID parentDataObjectId){
        OrderConfirmationItem item = new OrderConfirmationItem();
        item.setParentId(parentDataObjectId);
        item.setCurrency(Currency.BGN.getDbResource());
        item.setMeasureUnit(MeasurementUnit.Piece.getDbResource());
        return item;
    }
    
    public OrderConfirmationItem saveOrderItem(OrderConfirmationItem item) {
        orderConfirmationItemValidator.validate(item);
        
        esm.persist(em, item);
        
        return item;
    }

    @SuppressWarnings("unchecked")
    
    public WarehouseProduct getWarehouseProduct(SimpleProduct product) {
        Address branch = acaciaSession.getBranch();
        if ( branch==null )
            throw new IllegalArgumentException("Branch of logged user required!");
        if ( product==null )
            throw new IllegalArgumentException("Product parameter missing!");
        
        Query q = em.createNamedQuery("WarehouseProduct.findByProductAndBranch");
        q.setParameter("branch", branch);
        q.setParameter("product", product);
        
        List result = q.getResultList();
        if ( result.isEmpty() )
            return null;
        else
            return (WarehouseProduct) q.getResultList().get(0);
    }

    
    public EntityProperties getItemDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(OrderConfirmationItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public List<DbResource> getDocumentTypes() {
        return OrderConfirmationType.getDbResources();
    }

    @Override
    public List<DbResource> getCurrencies() {
        return Currency.getDbResources();
    }

    @Override
    public List<OrderConfirmationItem> getPendingItems(UUID parentId) {
        List<OrderConfirmationItem> items = getOrderItems(parentId);
        List<OrderConfirmationItem> result = new ArrayList<OrderConfirmationItem>();
        
        for (OrderConfirmationItem item : items) {
            //confirmed > matched
            if ( item.getMatchedQuantity()==null ||
                    item.getConfirmedQuantity().compareTo(item.getMatchedQuantity())>0 ){
                result.add(item);
            }
        }
        
        return result;
    }

    @Override
    public List<OrderConfirmation> getPendingConfirmations(UUID parentDataObjectId, Address branch) {
        return listOrderConfirmations(parentDataObjectId, branch);
    }

    @Override
    public void matchConfirmationItem(OrderConfirmationItem confirmationitem,
                                      PurchaseOrderItem orderItem, BigDecimal maxMatchQuantity) {
        if ( confirmationitem==null )
            throw new IllegalArgumentException("confirmationitem can't be null");
        if ( orderItem==null )
            throw new IllegalArgumentException("orderitem can't be null");
        
        BigDecimal matchedQty = confirmationitem.getMatchedQuantity();
        if ( matchedQty==null )
            matchedQty = new BigDecimal(0);
        BigDecimal confirmationFreeQty = confirmationitem.getConfirmedQuantity().subtract(matchedQty);
        BigDecimal confirmationMatchQty = null;
        BigDecimal orderItemConfirmedQty = orderItem.getConfirmedQuantity();
        if ( orderItemConfirmedQty==null )
            orderItemConfirmedQty = new BigDecimal(0);
        BigDecimal orderItemPendingQty = orderItem.getOrderedQuantity().subtract(orderItemConfirmedQty);
        
        //if freeQty > pendingQty  
        if ( confirmationFreeQty.compareTo(orderItemPendingQty)>0 )
            confirmationMatchQty = orderItemPendingQty;
        //freeQty <= pendingQty
        else
            confirmationMatchQty = confirmationFreeQty; 
        
        //restrict further the match quantity if such parameter is provided
        if ( maxMatchQuantity!=null && confirmationMatchQty.compareTo(maxMatchQuantity)>0 ){
            confirmationMatchQty = maxMatchQuantity;
        }
        
        //create the match instance
        OrderItemMatch itemMatch = new OrderItemMatch();
        itemMatch.setPurchaseOrderItem(orderItem);
        itemMatch.setOrderConfirmationItem(confirmationitem);
        itemMatch.setMatchQuantity(confirmationMatchQty);
        
        //update the purchase order item
        BigDecimal updatedConfirmedQty = orderItemConfirmedQty.add(confirmationMatchQty);
        orderItem.setConfirmedQuantity(updatedConfirmedQty);
        
        //update the order confirmation item
        BigDecimal updatedMatchedQty = matchedQty.add(confirmationMatchQty);
        confirmationitem.setMatchedQuantity(updatedMatchedQty);
        
        //update the status of the order
        PurchaseOrder order = purchaseOrderListLocal.getPurchaseOrder(orderItem.getParentId());
        DbResource oldStatus = order.getStatus();
        List<PurchaseOrderItem> orderItems = purchaseOrderListLocal.getOrderItems(order.getId());
        if ( noPendingItems(orderItems) ){
            order.setStatus(PurchaseOrderStatus.Confirmed.getDbResource());
            purchaseOrderListLocal.savePurchaseOrder(order);
        }else if ( !oldStatus.equals(PurchaseOrderStatus.PartlyConfirmed.getDbResource())){
            order.setStatus(PurchaseOrderStatus.PartlyConfirmed.getDbResource());
            purchaseOrderListLocal.savePurchaseOrder(order);
        }
        
        //persist all changes
        orderItemMatchLocal.saveOrderItemMatch(itemMatch);
        purchaseOrderListLocal.saveOrderItem(orderItem);
        this.saveOrderItem(confirmationitem);
    }

    private boolean noPendingItems(List<PurchaseOrderItem> orderItems) {
        boolean noPendingItems = true;
        for (PurchaseOrderItem purchaseOrderItem : orderItems) {
            if ( purchaseOrderItem.getPendingQuantity().compareTo(new BigDecimal(0))>0 ){
                noPendingItems = false;
            }
        }
        return noPendingItems;
    }

    @Override
    public void matchOrderConfirmation(OrderConfirmation orderConfirmation,
                                       PurchaseOrder purchaseOrder) {
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderListLocal.getOrderItems(purchaseOrder.getId());
        List<OrderConfirmationItem> orderConfirmationItems = this.getOrderItems(orderConfirmation.getId());
        
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
            //if there is pending quantity for this order item - search for matching confirmation item
            if ( purchaseOrderItem.getPendingQuantity().compareTo(new BigDecimal(0))>0 ){
                for (OrderConfirmationItem orderConfirmationItem : orderConfirmationItems) {
                    //if products match and there is pending quantity in the confirmation item
                    if ( purchaseOrderItem.getProduct().equals(orderConfirmationItem.getProduct())
                      && orderConfirmationItem.getPendingQuantity().compareTo(new BigDecimal(0))>0 ){
                        //match the items
                        matchConfirmationItem(orderConfirmationItem, purchaseOrderItem, null);
                        //check again if we need to search more confirmations for this product
                        if ( purchaseOrderItem.getPendingQuantity().compareTo(new BigDecimal(0))<=0 )
                            break;
                    }
                }
            } 
        }
    }

    @Override
    public List<OrderConfirmation> listOrderConfirmations(UUID parentDataObjectId) {
        return getPendingConfirmations(parentDataObjectId, null);
    }
}
