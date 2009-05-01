/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.purchase;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.OrderConfirmationItem;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.PurchaseOrderItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "purchase_invoice_items", catalog = "acacia", schema = "public")
@NamedQueries({
    @NamedQuery(
        name = "PurchaseInvoiceItem.findAll",
        query = "SELECT p FROM PurchaseInvoiceItem p" +
            " where" +
            "  p.invoice = :invoice" +
            "  and p.dataObject.deleted = false" +
            " order by p.dataObject.orderPosition")
})
public class PurchaseInvoiceItem extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "invoice_item_id", nullable = false)
    private BigInteger invoiceItemId;

    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id", nullable = false)
    @ManyToOne(optional = false)
    private PurchaseInvoice invoice;

    @Transient
    @Property(title="Item #")
    private Integer orderPosition;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Product")
    private Product product;

    @JoinColumn(name = "measure_unit_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Measure Unit")
    private DbResource measureUnit;

    @Basic(optional = false)
    @Column(name = "received_quantity", nullable = false, precision = 19, scale = 4)
    @Property(title="Quantity")
    private BigDecimal receivedQuantity;

    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Currency")
    private DbResource currency;

    @Basic(optional = false)
    @Column(name = "received_price", nullable = false, precision = 19, scale = 4)
    @Property(title="Unit Price")
    private BigDecimal receivedPrice;

    @Basic(optional = false)
    @Column(name = "extended_price", nullable = false, precision = 19, scale = 4)
    @Property(title="Extended Price")
    private BigDecimal extendedPrice;

    @Column(name = "tax_value", precision = 19, scale = 4)
    @Property(title="Tax")
    private BigDecimal taxValue;

    @JoinColumn(name = "purchase_order_item_id", referencedColumnName = "order_item_id")
    @ManyToOne
    @Property(title="PO Item")
    private PurchaseOrderItem purchaseOrderItem;

    @JoinColumn(name = "order_confirmation_item_id", referencedColumnName = "confirmation_item_id")
    @ManyToOne
    @Property(title="POC Item")
    private OrderConfirmationItem orderConfirmationItem;

    @Column(name = "customs_tariff_number", length = 16)
    @Property(title="Customs Tariff Number")
    private String customsTariffNumber;

    @JoinColumn(name = "invoice_item_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public PurchaseInvoiceItem() {
    }

    public PurchaseInvoiceItem(BigInteger invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public BigInteger getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(BigInteger invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public BigDecimal getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(BigDecimal receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public BigDecimal getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(BigDecimal receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public BigDecimal getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(BigDecimal extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public String getCustomsTariffNumber() {
        return customsTariffNumber;
    }

    public void setCustomsTariffNumber(String customsTariffNumber) {
        this.customsTariffNumber = customsTariffNumber;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public OrderConfirmationItem getOrderConfirmationItem() {
        return orderConfirmationItem;
    }

    public void setOrderConfirmationItem(OrderConfirmationItem orderConfirmationItem) {
        this.orderConfirmationItem = orderConfirmationItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(PurchaseInvoice invoice) {
        this.invoice = invoice;
        if(invoice != null)
            setParentId(invoice.getId());
        else
            setParentId(null);
    }

    public PurchaseOrderItem getPurchaseOrderItem() {
        return purchaseOrderItem;
    }

    public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        this.purchaseOrderItem = purchaseOrderItem;
    }

    public DbResource getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(DbResource measureUnit) {
        this.measureUnit = measureUnit;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceItemId != null ? invoiceItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchaseInvoiceItem)) {
            return false;
        }
        PurchaseInvoiceItem other = (PurchaseInvoiceItem) object;
        if ((this.invoiceItemId == null && other.invoiceItemId != null) || (this.invoiceItemId != null && !this.invoiceItemId.equals(other.invoiceItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PurchaseInvoiceItem[invoiceItemId=" + invoiceItemId + "]";
    }

    @Override
    public BigInteger getId() {
        return getInvoiceItemId();
    }

    @Override
    public void setId(BigInteger id) {
        setInvoiceItemId(id);
    }

    @Override
    public BigInteger getParentId() {
        if(invoice != null)
            invoice.getId();

        if(dataObject != null) {
            return dataObject.getParentDataObjectId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        return toString();
    }
}
