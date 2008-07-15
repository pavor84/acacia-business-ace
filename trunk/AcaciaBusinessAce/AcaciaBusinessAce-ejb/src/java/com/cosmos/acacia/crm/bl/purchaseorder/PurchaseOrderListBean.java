package com.cosmos.acacia.crm.bl.purchaseorder;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.PurchaseOrder;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 * Implement business logic behind the Purchase Orders Use Cases
 */
@Stateless
public class PurchaseOrderListBean implements PurchaseOrderListRemote {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

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
        order.setParentId(parentDataObjectId);
        return order;
    }
}
