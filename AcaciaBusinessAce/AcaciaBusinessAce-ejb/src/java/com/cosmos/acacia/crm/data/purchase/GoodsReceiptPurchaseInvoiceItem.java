/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.purchase;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "goods_receipt_pi_items", catalog = "acacia", schema = "public")
@DiscriminatorValue(value=GoodsReceiptPurchaseInvoiceItem.DISCRIMINATOR_VALUE)
@PrimaryKeyJoinColumn(name="receipt_item_id",referencedColumnName="receipt_item_id")
@NamedQueries({
    @NamedQuery(name = "GoodsReceiptPiItem.findAll", query = "SELECT g FROM GoodsReceiptPurchaseInvoiceItem g")
})
public class GoodsReceiptPurchaseInvoiceItem extends GoodsReceiptItem implements Serializable {

    private static final long serialVersionUID = 1L;

    static final String DISCRIMINATOR_VALUE = "PI";

    @JoinColumn(name = "invoice_item_id", referencedColumnName = "invoice_item_id", nullable = false)
    @ManyToOne(optional = false)
    private PurchaseInvoiceItem invoiceItemId;

    public GoodsReceiptPurchaseInvoiceItem() {
        super(DISCRIMINATOR_VALUE);
    }

    public GoodsReceiptPurchaseInvoiceItem(BigInteger receiptItemId) {
        super(DISCRIMINATOR_VALUE, receiptItemId);
    }

    public PurchaseInvoiceItem getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(PurchaseInvoiceItem invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }
}
