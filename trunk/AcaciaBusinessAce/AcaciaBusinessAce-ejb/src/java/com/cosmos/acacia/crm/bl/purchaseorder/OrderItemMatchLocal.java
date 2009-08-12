package com.cosmos.acacia.crm.bl.purchaseorder;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.purchase.OrderItemMatch;

/**
 * 
 * Created	:	14.07.2008
 * @author	Petar Milev
 *
 */
@Local
public interface OrderItemMatchLocal{

    /**
     * Save the match
     * @param itemMatch
     */
    void saveOrderItemMatch(OrderItemMatch itemMatch);

}
