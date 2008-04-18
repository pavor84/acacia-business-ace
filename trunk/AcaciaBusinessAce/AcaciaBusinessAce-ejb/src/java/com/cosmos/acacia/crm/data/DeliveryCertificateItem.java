/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "delivery_certificate_items")
public class DeliveryCertificateItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "certificate_item_id", nullable = false)
    private BigInteger certificateItemId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private SimpleProduct product;

    @JoinColumn(name = "measure_unit_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource measureUnit;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @JoinColumn(name = "certificate_item_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public DeliveryCertificateItem() {
    }

    public DeliveryCertificateItem(BigInteger certificateItemId) {
        this.certificateItemId = certificateItemId;
    }

    public BigInteger getCertificateItemId() {
        return certificateItemId;
    }

    public void setCertificateItemId(BigInteger certificateItemId) {
        this.certificateItemId = certificateItemId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public SimpleProduct getProduct() {
        return product;
    }

    public void setProduct(SimpleProduct product) {
        this.product = product;
    }

    public DbResource getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(DbResource measureUnit) {
        this.measureUnit = measureUnit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (certificateItemId != null ? certificateItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryCertificateItem)) {
            return false;
        }
        DeliveryCertificateItem other = (DeliveryCertificateItem) object;
        if ((this.certificateItemId == null && other.certificateItemId != null) || (this.certificateItemId != null && !this.certificateItemId.equals(other.certificateItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DeliveryCertificateItem[certificateItemId=" + certificateItemId + "]";
    }

}
