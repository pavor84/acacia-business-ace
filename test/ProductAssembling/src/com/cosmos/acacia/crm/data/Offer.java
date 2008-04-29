/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "offers")
@NamedQueries({})
public class Offer
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name="DataObjectsSequenceGenerator",
        sequenceName="data_objects_seq",
        allocationSize=1)
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="DataObjectsSequenceGenerator")
    @Column(name = "offer_id", nullable = false)
    private Long offerId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offer")
    private List<OfferItem> offerItems;

    public Offer()
    {
    }

    public Offer(Long offerId)
    {
        this.offerId = offerId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public List<OfferItem> getOfferItems() {
        return offerItems;
    }

    public void setOfferItems(List<OfferItem> offerItems) {
        this.offerItems = offerItems;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (offerId != null ? offerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Offer)) {
            return false;
        }
        Offer other = (Offer) object;
        if ((this.offerId == null && other.offerId != null) || (this.offerId != null && !this.offerId.equals(other.offerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.data.Offer[offerId=" + offerId + "]";
    }

}
