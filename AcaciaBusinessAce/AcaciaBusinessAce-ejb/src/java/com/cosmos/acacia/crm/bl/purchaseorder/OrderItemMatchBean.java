package com.cosmos.acacia.crm.bl.purchaseorder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.OrderItemMatch;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 * Implement business logic behind the Purchase Orders Use Cases
 */
@Stateless
public class OrderItemMatchBean implements OrderItemMatchLocal {
    @PersistenceContext
    private EntityManager em; 
 
    @EJB 
    private EntityStoreManagerLocal esm;
    
    @Override
    public void saveOrderItemMatch(OrderItemMatch itemMatch) {
        esm.persist(em, itemMatch);
    }
    
}
