/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.purchase;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.product.Product;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "goods_receipt_items", catalog = "acacia", schema = "public")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=2, name="product_type")
@NamedQueries({
    @NamedQuery(name = "GoodsReceiptItem.findAll", query = "SELECT g FROM GoodsReceiptItem g")
})
public class GoodsReceiptItem extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "receipt_item_id", nullable = false)
    @Type(type="uuid")
    private UUID receiptItemId;

    @JoinColumn(name = "goods_receipt_id", referencedColumnName = "goods_receipt_id", nullable = false)
    @ManyToOne(optional = false)
    private GoodsReceipt goodsReceipt;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @ManyToOne(optional = false)
    private Product product;

    @JoinColumn(name = "measure_unit_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    private DbResource measureUnit;

    @Basic(optional = false)
    @Column(name = "received_quantity", nullable = false, precision = 19, scale = 4)
    private BigDecimal receivedQuantity;

    @Basic(optional = false)
    @Column(name = "receipt_item_type", nullable = false, length = 2)
    private String receiptItemType;

    @JoinColumn(name = "receipt_item_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    @Transient
    private String className;

    public GoodsReceiptItem() {
    }

    protected GoodsReceiptItem(String receiptItemType) {
        this.receiptItemType = receiptItemType;
    }

    protected GoodsReceiptItem(String receiptItemType, UUID receiptItemId) {
        this.receiptItemId = receiptItemId;
    }

    public UUID getReceiptItemId() {
        return receiptItemId;
    }

    public void setReceiptItemId(UUID receiptItemId) {
        this.receiptItemId = receiptItemId;
    }

    public BigDecimal getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(BigDecimal receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public String getReceiptItemType() {
        return receiptItemType;
    }

    public void setReceiptItemType(String receiptItemType) {
        this.receiptItemType = receiptItemType;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public GoodsReceipt getGoodsReceipt() {
        return goodsReceipt;
    }

    public void setGoodsReceipt(GoodsReceipt goodsReceipt) {
        this.goodsReceipt = goodsReceipt;
        if(goodsReceipt != null) {
            setParentId(goodsReceipt.getGoodsReceiptId());
        } else {
            setParentId(null);
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
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
        hash += (receiptItemId != null ? receiptItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GoodsReceiptItem)) {
            return false;
        }
        GoodsReceiptItem other = (GoodsReceiptItem) object;
        if ((this.receiptItemId == null && other.receiptItemId != null) || (this.receiptItemId != null && !this.receiptItemId.equals(other.receiptItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(className == null) {
            className = getClass().getSimpleName();
        }

        return className + "[id=" + receiptItemId + "]";
    }

    @Override
    public UUID getId() {
        return getReceiptItemId();
    }

    @Override
    public void setId(UUID id) {
        setReceiptItemId(receiptItemId);
    }

    @Override
    public UUID getParentId() {
        GoodsReceipt receipt;
        if((receipt = getGoodsReceipt()) != null) {
            return receipt.getGoodsReceiptId();
        }

        if(dataObject != null) {
            return dataObject.getParentDataObjectId();
        }

        return null;
    }

    @Override
    public void setParentId(UUID parentId) {
        if(dataObject == null) {
            dataObject = new DataObject();
        }

        dataObject.setParentDataObjectId(parentId);
    }

    @Override
    public String getInfo() {
        return toString();
    }
}
