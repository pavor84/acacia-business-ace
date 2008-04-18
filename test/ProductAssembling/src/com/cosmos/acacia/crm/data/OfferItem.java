/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "offer_items")
@NamedQueries({})
public class OfferItem
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "offer_item_id", nullable = false)
    private Long offerItemId;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
    @ManyToOne
    private Offer offer;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private Product product;


    public OfferItem()
    {
    }

    public OfferItem(Long offerItemId)
    {
        this.offerItemId = offerItemId;
    }

    public Long getOfferItemId() {
        return offerItemId;
    }

    public void setOfferItemId(Long offerItemId) {
        this.offerItemId = offerItemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (offerItemId != null ? offerItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OfferItem)) {
            return false;
        }
        OfferItem other = (OfferItem) object;
        if ((this.offerItemId == null && other.offerItemId != null) || (this.offerItemId != null && !this.offerItemId.equals(other.offerItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.data.OfferItem[offerItemId=" + offerItemId + "]";
    }

}
