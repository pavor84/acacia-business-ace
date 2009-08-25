/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.crm.data.product.SimpleProduct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "receipt_certificate_items")
public class ReceiptCertificateItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "certificate_item_id", nullable = false)
    @Type(type="uuid")
    private UUID certificateItemId;

    @Column(name = "parent_id")
    @Type(type="uuid")
    private UUID parentId;

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


    public ReceiptCertificateItem() {
    }

    public ReceiptCertificateItem(UUID certificateItemId) {
        this.certificateItemId = certificateItemId;
    }

    public UUID getCertificateItemId() {
        return certificateItemId;
    }

    public void setCertificateItemId(UUID certificateItemId) {
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

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
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
        if (!(object instanceof ReceiptCertificateItem)) {
            return false;
        }
        ReceiptCertificateItem other = (ReceiptCertificateItem) object;
        if ((this.certificateItemId == null && other.certificateItemId != null) || (this.certificateItemId != null && !this.certificateItemId.equals(other.certificateItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.ReceiptCertificateItem[certificateItemId=" + certificateItemId + "]";
    }

}
