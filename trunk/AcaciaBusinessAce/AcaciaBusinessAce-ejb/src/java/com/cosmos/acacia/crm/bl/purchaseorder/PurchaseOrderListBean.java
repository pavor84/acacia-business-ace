package com.cosmos.acacia.crm.bl.purchaseorder;

import java.math.BigInteger;
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
import com.cosmos.acacia.crm.bl.impl.WarehouseListLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.PurchaseOrder;
import com.cosmos.acacia.crm.data.PurchaseOrderItem;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.data.WarehouseProduct;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.DocumentDeliveryMethod;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.PurchaseOrderStatus;
import com.cosmos.acacia.crm.validation.impl.PurchaseOrderItemValidatorLocal;
import com.cosmos.acacia.crm.validation.impl.PurchaseOrderValidatorLocal;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 * Implement business logic behind the Purchase Orders Use Cases
 */
@Stateless
public class PurchaseOrderListBean implements PurchaseOrderListRemote, PurchaseOrderListLocal {
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
    private PurchaseOrderValidatorLocal purchaseOrderValidator;
    
    @EJB
    private PurchaseOrderItemValidatorLocal purchaseOrderItemValidator;
    
    @EJB
    private WarehouseListLocal warehouseListLocal;
    
    @Override
    public EntityProperties getListingEntityProperties() {
        
        EntityProperties entityProperties = esm.getEntityProperties(PurchaseOrder.class);
        
        //let's keep the columns in the table a reasonable count, so remove all not-crucial information
        entityProperties.removePropertyDetails("branchName");
        entityProperties.removePropertyDetails("supplierName");
        entityProperties.removePropertyDetails("supplierContactName");
        entityProperties.removePropertyDetails("creatorName");
        entityProperties.removePropertyDetails("sender");
        entityProperties.removePropertyDetails("senderName");
        entityProperties.removePropertyDetails("firstDeliveryTime");
        entityProperties.removePropertyDetails("lastDeliveryTime");
        entityProperties.removePropertyDetails("notes");
        
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PurchaseOrder> listPurchaseOrders(BigInteger parentDataObjectId) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("PurchaseOrder.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        
        List<PurchaseOrder> result = q.getResultList();
        return result;
    }

    @Override
    public void deletePurchaseOrder(PurchaseOrder purchaseOrder) {
        if ( purchaseOrder==null )
            throw new IllegalArgumentException("null: 'purchaseOrder'");
        esm.remove(em, purchaseOrder);
    }

    @Override
    public PurchaseOrder newPurchaseOrder(BigInteger parentDataObjectId) {
        
        PurchaseOrder order = new PurchaseOrder();
        
        Address branch = acaciaSession.getBranch();
        Person person = acaciaSession.getPerson();
        
        if ( branch!=null ){
            order.setBranch(branch);
            order.setBranchName(branch.getAddressName());
        }
        order.setCreationTime(new Date());
        order.setCreator(person);
        order.setCreatorName(person.getDisplayName());
        order.setDocumentDeliveryMethod(DocumentDeliveryMethod.Courier.getDbResource());
        order.setStatus(PurchaseOrderStatus.Open.getDbResource());
        order.setParentId(parentDataObjectId);
        
        return order;
    }

    @Override
    public List<DbResource> getDeliveryMethods() {
        return DocumentDeliveryMethod.getDbResources();
    }

    @Override
    public EntityProperties getDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(PurchaseOrder.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public List<DbResource> getStatuses() {
        return PurchaseOrderStatus.getDbResources();
    }

    @Override
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

    @Override
    public PurchaseOrder savePurchaseOrder(PurchaseOrder po) {
        
        //if new order - set some numbers
        if ( po.getOrderNumber()==null || po.getOrderNumber().equals(new BigInteger("0")) ){
            BigInteger orderNumber = getNextOrderNumber(po);
            po.setOrderNumber(orderNumber);
            BigInteger sequenceNumber = getNextSequenceNumber(po);
            po.setSupplierOrderNumber(sequenceNumber);
        }
        
        purchaseOrderValidator.validate(po);
        
        esm.persist(em, po);
        
        return po;
    }

    private BigInteger getNextSequenceNumber(PurchaseOrder po) {
        Query q = em.createNamedQuery("PurchaseOrder.maxSupplierOrderNumberForSupplier");
        q.setParameter("parentDataObjectId", po.getParentId());
        q.setParameter("supplier", po.getSupplier());
        
        BigInteger n = (BigInteger) q.getSingleResult();
        if ( n==null )
            return new BigInteger("1");
        else
            return n.add(new BigInteger("1"));
    }

    private BigInteger getNextOrderNumber(PurchaseOrder po) {
        Query q = em.createNamedQuery("PurchaseOrder.maxOrderNumberForBranch");
        if ( po.getBranch()==null )
            throw new IllegalArgumentException("PurchaseOrder branch is null!");
        q.setParameter("branch", po.getBranch());
        
        BigInteger result = (BigInteger) q.getSingleResult();
        //no orders for this warehouse
        if ( result==null ){
            Warehouse warehouse = warehouseListLocal.getWarehouseForAddress(po.getBranch());
            if ( warehouse==null ){
                throw new IllegalStateException("No warehouse found for address: "+po.getBranch()==null?"null":po.getBranch().getAddressName());
            }
            
            if ( warehouse.getIndex()==null || warehouse.getIndex().equals(new Long(0))){
                throw new IllegalStateException("No warehouse index set for warehouse: "
                    +warehouse.getAddress().getAddressName()+". This is needed to generate document numbers!");
            }
            
            result = new BigInteger(""+warehouse.getIndex());
            result = result.multiply(WarehouseListLocal.DOCUMENT_INDEX_MULTIPLICATOR);
            
//            q = em.createNamedQuery("PurchaseOrder.maxOrderNumber");
//            q.setParameter("parentDataObjectId", po.getParentId());
//            
//            result = (BigInteger) q.getSingleResult();
//            BigInteger step = new BigInteger("1000000000");
//            //no orders at all
//            if ( result==null ){
//                result = step;
//            //first order for this warehouse - get next step
//            }else{
//                BigInteger newWarehouseNumber = result.divide(step);
//                newWarehouseNumber = newWarehouseNumber.add(new BigInteger("1"));
//                newWarehouseNumber = newWarehouseNumber.multiply(step);
//                result = newWarehouseNumber;
//            }
        //just get the next number
        }else{
            result = result.add(new BigInteger("1"));
        }
        return result;
    }

    @Override
    public PurchaseOrder updateOrderStatus(PurchaseOrder order, PurchaseOrderStatus status) {
        order.setStatus(status.getDbResource());
        
        if ( status.equals(PurchaseOrderStatus.Sent)){
            Person person = acaciaSession.getPerson();
            order.setSender(person);
            order.setSenderName(person.getDisplayName());
            order.setSentTime(new Date());
        }
        
        return savePurchaseOrder(order);
    }

    @Override
    public void deleteOrderItem(PurchaseOrderItem item) {
        esm.remove(em, item);
    }

    @Override
    public EntityProperties getItemsListEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(PurchaseOrderItem.class);
        entityProperties.removePropertyDetails("notes");
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PurchaseOrderItem> getOrderItems(BigInteger parentDataObjectId) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("PurchaseOrderItem.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        
        List<PurchaseOrderItem> result = q.getResultList();
        return result;
    }

    @Override
    public PurchaseOrderItem newOrderItem(BigInteger parentDataObjectId) {
        PurchaseOrderItem item = new PurchaseOrderItem();
        item.setParentId(parentDataObjectId);
        item.setCurrency(Currency.Leva.getDbResource());
        item.setMeasureUnit(MeasurementUnit.Piece.getDbResource());
        return item;
    }

    @Override
    public PurchaseOrderItem saveOrderItem(PurchaseOrderItem item) {
        purchaseOrderItemValidator.validate(item);
        
        esm.persist(em, item);
        
        return item;
    }

    @SuppressWarnings("unchecked")
    @Override
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

    @Override
    public EntityProperties getItemDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(PurchaseOrderItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public List<DbResource> getCurrencies() {
        return Currency.getDbResources();
    }

    @Override
    public void saveOrderItems(List<PurchaseOrderItem> orderItems) {
        if ( orderItems==null )
            throw new IllegalArgumentException("orderItems can't be null!");
        for (PurchaseOrderItem item : orderItems) {
            purchaseOrderItemValidator.validate(item);
            esm.persist(em, item);
        }
        em.flush();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PurchaseOrder> getPendingOrders(BigInteger parentDataObjectId, Address branch) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        List<PurchaseOrder> result = listPendingOrders(parentDataObjectId, branch, null);
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private List<PurchaseOrder> listPendingOrders(BigInteger parentDataObjectId,
                                                Address branch, BusinessPartner supplier) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        Query q = em.createNamedQuery("PurchaseOrder.findPendingForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        q.setParameter("status_sent", PurchaseOrderStatus.Sent.getDbResource());
        q.setParameter("status_partlyConfirmed", PurchaseOrderStatus.PartlyConfirmed.getDbResource());
        q.setParameter("supplier", supplier);
        q.setParameter("branch", branch);
        
        List<PurchaseOrder> result = q.getResultList();
        return result;
    }
    

    @Override
    public List<PurchaseOrder> getPendingOrders(BigInteger parentDataObjectId,
                                                BusinessPartner supplier, Address branch) {
        if ( parentDataObjectId==null )
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        if ( supplier==null )
            throw new IllegalArgumentException("supplier can't be null");
        
        List<PurchaseOrder> result = listPendingOrders(parentDataObjectId, branch, supplier);
        return result;
    }

    @Override
    public PurchaseOrder getPurchaseOrder(BigInteger id) {
        if ( id==null )
            throw new IllegalArgumentException("Please provide 'id' parameter");
        PurchaseOrder result = em.find(PurchaseOrder.class, id);
        return result;
    }
}
