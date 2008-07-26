/**
 * 
 */
package com.cosmos.acacia.crm.data;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created	:	24.07.2008
 * @author	Petar Milev
 *
 */
@Entity
@Table(name = "order_item_match")
public class OrderItemMatch {
    @Id
    @SequenceGenerator(name="OrderItemMatchSeqGen", sequenceName="order_item_match_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OrderItemMatchSeqGen")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @ManyToOne
    private PurchaseOrderItem purchaseOrderItem;
    
    @ManyToOne
    private OrderConfirmationItem orderConfirmationItem;
    
    private BigDecimal matchQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PurchaseOrderItem getPurchaseOrderItem() {
        return purchaseOrderItem;
    }

    public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        this.purchaseOrderItem = purchaseOrderItem;
    }

    public OrderConfirmationItem getOrderConfirmationItem() {
        return orderConfirmationItem;
    }

    public void setOrderConfirmationItem(OrderConfirmationItem orderConfirmationItem) {
        this.orderConfirmationItem = orderConfirmationItem;
    }

    public BigDecimal getMatchQuantity() {
        return matchQuantity;
    }

    public void setMatchQuantity(BigDecimal matchQuantity) {
        this.matchQuantity = matchQuantity;
    }
}
