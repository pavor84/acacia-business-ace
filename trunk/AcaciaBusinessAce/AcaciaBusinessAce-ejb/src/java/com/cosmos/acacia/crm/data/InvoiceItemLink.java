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
@Table(name = "sales_invoice_item_link")
@NamedQueries(
    {
        /**
         * Returns all InvoiceItemLinks for the given items.
         * Parameters: 
         * - items - InvoiceItem instances, required
         */
        @NamedQuery
            (
                name = "InvoiceItemLink.getInvoicesItemLinksForItems",
                query = "select link from InvoiceItemLink link where link.invoiceItem in (:items)"
            ),
        /**
         * Get links for invoice item.
         * Parameters:
         * - invoiceItem - not null invoice item
         */
        @NamedQuery
        (
            name = "InvoiceItemLink.getInvoicesItemLinks",
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
     * This is the document used as template, it can be of different type (sales offer, invoice, etc),
     */
    @Column(name = "template_doc_id", nullable=false)
    private BigInteger templateDocumentId;
    
    /**
     * This is reference to the actual invoice item, which is created/modified based on the above
     * template. There can be many links for a given invoice item.
     */
    @JoinColumn(name = "invoice_item_id", nullable=false)
    @OneToOne
    private InvoiceItem invoiceItem;
    
    public InvoiceItemLink(BigInteger templateItemId, BigInteger templateDocumentId,
            InvoiceItem invoiceItem) {
        super();
        this.templateItemId = templateItemId;
        this.templateDocumentId = templateDocumentId;
        this.invoiceItem = invoiceItem;
    }

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

    public BigInteger getTemplateDocumentId() {
        return templateDocumentId;
    }

    public void setTemplateDocumentId(BigInteger templateDocumentId) {
        this.templateDocumentId = templateDocumentId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final InvoiceItemLink other = (InvoiceItemLink) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
