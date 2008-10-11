/**
 * 
 */
package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created	:	06.10.2008
 * @author	Petar Milev
 * 
 * Some of the invoice items are created based on other items ( from other documents ), called
 * template items.
 * This entity represents the link from an invoice item to another document item (template item).
 *
 */
@Entity
@Table(name = "invoice_item_link")
@NamedQueries(
    {
        /**
         * Get links for invoice item.
         * Parameters:
         * - invoiceItem - not null invoice item
         */
        @NamedQuery
        (
            name = "Invoice.getInvoicesItemLinks",
            query = "select itemLink from InvoiceItemLink itemLink where itemLink.invoiceItem = :invoiceItem"
        )
    })
public class InvoiceItemLink implements Serializable{
    @Id
    @SequenceGenerator(name="InvoiceItemLinkSeqGen", sequenceName="invoice_item_link_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="InvoiceItemLinkSeqGen")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    /**
     * This is the item used as template, it can be of different type (sales offer item, invoice item, etc),
     * that's way it's just an id.
     */
    @Column(name = "template_item_id", nullable=false)
    private BigInteger templateItemId;
    
    /**
     * This is reference to the actual invoice item, which is created/modified based on the above
     * template. There can be many links for a given invoice item.
     */
    @JoinColumn(name = "invoice_item_id", nullable=false)
    @OneToOne
    private InvoiceItem invoiceItem;
    
    public InvoiceItemLink(){
    }
    
    public InvoiceItemLink(BigInteger templateItemId, InvoiceItem invoiceItem) {
        super();
        this.templateItemId = templateItemId;
        this.invoiceItem = invoiceItem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public BigInteger getTemplateItemId() {
        return templateItemId;
    }

    public void setTemplateItemId(BigInteger templateItemId) {
        this.templateItemId = templateItemId;
    }
}
