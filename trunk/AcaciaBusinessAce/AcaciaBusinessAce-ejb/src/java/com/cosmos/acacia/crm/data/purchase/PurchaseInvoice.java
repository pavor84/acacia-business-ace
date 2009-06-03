/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.purchase;

import com.cosmos.acacia.annotation.BorderType;
import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.ComponentBorder;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Layout;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.purchase.PurchaseServiceRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.BusinessDocument;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBDecimalField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTextField;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "purchase_invoices", catalog = "acacia", schema = "public")
@DiscriminatorValue(value = BusinessDocument.PURCHASE_INVOICE)
@PrimaryKeyJoinColumn(name="invoice_id",referencedColumnName="document_id")
@NamedQueries({
    @NamedQuery(
        name = PurchaseInvoice.NQ_FIND_ALL,
        query = "SELECT p FROM PurchaseInvoice p" +
            " where" +
            "  p.publisher = :publisher" +
            "  and p.publisherBranch = :publisherBranch" +
            "  and p.dataObject.deleted = false" +
            " order by p.documentNumber")
})
@Form(
    formContainers={
        @FormContainer(
            name="itemList",
            title="Item List",
            depends={"<entityForm>"},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=PurchaseInvoiceItem.class,
            componentIndex=2
        ),
        @FormContainer(
            name="supplierDetails",
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Supplier Details"
                ),
                componentConstraints="span 2, sizegroup publisherSG, growx"
            ),
            layout=@Layout(/*extraConstraints="debug", */columnsPairs=1),
            parentContainerName="primaryInfo"
        ),
        @FormContainer(
            name="supplierInvoiceDetails",
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Invoice Details"
                ),
                componentConstraints="span, growx"
            ),
            layout=@Layout(/*extraConstraints="debug", */columnsPairs=3),
            parentContainerName="primaryInfo"
        ),
        @FormContainer(
            name="finalValues",
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Final Values"
                ),
                componentConstraints="span, growx"
            ),
            parentContainerName="primaryInfo"
        ),
        @FormContainer(
            name="termsAndConditions",
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Terms & Conditions"
                ),
                componentConstraints="span, growx"
            ),
            parentContainerName="primaryInfo"
        )
    },
    serviceClass=PurchaseServiceRemote.class
)
public class PurchaseInvoice extends BusinessDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    public static final String NQ_FIND_ALL = "PurchaseInvoice.findAll";

    @JoinColumn(name = "supplier_id", referencedColumnName = "partner_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Supplier",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.contactbook.BusinessPartnersListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="supplierDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Supplier:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private BusinessPartner supplier;

    @JoinColumn(name = "supplier_branch_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Supplier Branch",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.contactbook.AddressListPanel",
            constructorParameters={@PropertyName(getter="supplier", setter="businessPartner")}
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="supplierDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Branch:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private Address supplierBranch;

    @JoinColumn(name = "supplier_contact_id", referencedColumnName = "contact_person_id")
    @ManyToOne
    @Property(title="Supplier Contact",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.contactbook.ContactPersonsListPanel",
            constructorParameters={@PropertyName(getter="supplierBranch", setter="address")}
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="supplierDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Contact:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private ContactPerson supplierContact;

    @Basic(optional = false)
    @Column(name = "invoice_number", nullable = false, length = 16)
    @Property(title="Invoice No",
        formComponentPair=@FormComponentPair(
            parentContainerName="supplierInvoiceDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Invoice No:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String invoiceNumber;

    @Basic(optional = false)
    @Column(name = "invoice_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @Property(title="Invoice Date",
        formComponentPair=@FormComponentPair(
            parentContainerName="supplierInvoiceDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Date:"
            ),
            secondComponent=@Component(
                componentClass=JBDatePicker.class
            )
        )
    )
    private Date invoiceDate;

    @Column(name = "delivery_note", length = 16)
    @Property(title="Delivery Note",
        formComponentPair=@FormComponentPair(
            parentContainerName="supplierInvoiceDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Delivery Note:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String deliveryNote;

    @Column(name = "total_quantity", precision = 19, scale = 4)
    @Property(title="Total Quantity",
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName="finalValues",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Total Quantity:"
            ),
            secondComponent=@Component(
                componentClass=JBDecimalField.class
            )
        )
    )
    private BigDecimal totalQuantity;

    @Column(name = "total_net_amount", precision = 19, scale = 4)
    @Property(title="Total Net Amount",
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName="finalValues",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Total Net Amount:"
            ),
            secondComponent=@Component(
                componentClass=JBDecimalField.class
            )
        )
    )
    private BigDecimal totalNetAmount;

    @Column(name = "total_tax", precision = 19, scale = 4)
    @Property(title="Total Tax",
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName="finalValues",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Total Tax:",
                componentConstraints="skip 2"
            ),
            secondComponent=@Component(
                componentClass=JBDecimalField.class
            )
        )
    )
    private BigDecimal totalTax;

    @Column(name = "total_gross_amount", precision = 19, scale = 4)
    @Property(title="Total Gross Amount",
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName="finalValues",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Total Gross Amount:",
                componentConstraints="skip 2"
            ),
            secondComponent=@Component(
                componentClass=JBDecimalField.class
            )
        )
    )
    private BigDecimal totalGrossAmount;

    @JoinColumn(name = "payment_terms_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Terms of Payment",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.MeasurementUnit"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="termsAndConditions",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Terms of Payment:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource paymentTerms;

    @Basic(optional = false)
    @Column(name = "payment_deadline", nullable = false)
    @Temporal(TemporalType.DATE)
    @Property(title="Payment Deadline",
        formComponentPair=@FormComponentPair(
            parentContainerName="termsAndConditions",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Payment Deadline:"
            ),
            secondComponent=@Component(
                componentClass=JBDatePicker.class
            )
        )
    )
    private Date paymentDeadline;

    @JoinColumn(name = "bank_detail_id", referencedColumnName = "bank_detail_id")
    @ManyToOne
    @Property(title="Bank Details",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.contactbook.BankDetailsListPanel",
            constructorParameters={@PropertyName(getter="supplierBranch", setter="address")}
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="termsAndConditions",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Bank Details:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private BankDetail bankDetail;

    @JoinColumn(name = "delivery_terms_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Terms of Delivery",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.MeasurementUnit"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="termsAndConditions",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Terms of Delivery:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource deliveryTerms;

    public PurchaseInvoice() {
        super(PURCHASE_INVOICE);
    }

    public PurchaseInvoice(BigInteger invoiceId) {
        super(PURCHASE_INVOICE, invoiceId);
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalNetAmount() {
        return totalNetAmount;
    }

    public void setTotalNetAmount(BigDecimal totalNetAmount) {
        this.totalNetAmount = totalNetAmount;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalGrossAmount() {
        return totalGrossAmount;
    }

    public void setTotalGrossAmount(BigDecimal totalGrossAmount) {
        this.totalGrossAmount = totalGrossAmount;
    }

    public Date getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(Date paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public Address getSupplierBranch() {
        return supplierBranch;
    }

    public void setSupplierBranch(Address supplierBranch) {
        this.supplierBranch = supplierBranch;
    }

    public BankDetail getBankDetail() {
        return bankDetail;
    }

    public void setBankDetail(BankDetail bankDetail) {
        this.bankDetail = bankDetail;
    }

    public BusinessPartner getSupplier() {
        return supplier;
    }

    public void setSupplier(BusinessPartner supplier) {
        this.supplier = supplier;
    }

    public ContactPerson getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(ContactPerson supplierContact) {
        this.supplierContact = supplierContact;
    }

    public DbResource getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setDeliveryTerms(DbResource deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public DbResource getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(DbResource paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

}
