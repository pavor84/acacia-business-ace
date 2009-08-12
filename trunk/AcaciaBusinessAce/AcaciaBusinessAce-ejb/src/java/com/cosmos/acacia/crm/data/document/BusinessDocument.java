/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.document;

import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.annotation.BorderType;
import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.ComponentBorder;
import com.cosmos.acacia.annotation.ComponentProperty;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Layout;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBIntegerField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTabbedPane;
import com.cosmos.swingb.JBTextField;
import java.awt.BorderLayout;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author Miro
 */
@Entity
@Table(
    name = "business_documents", catalog = "acacia", schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"publisher_id", "document_type_id", "document_number"})
    }
)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=4, name="discriminator_id")
@NamedQueries({
})
@Form(
    mainContainer=@FormContainer(
        name="BusinessDocument",
        container=@Component(componentClass=JBTabbedPane.class)/*,
        layout=@Layout(extraConstraints="debug")*/
    ),
    formContainers={
        @FormContainer(
            name="primaryInfo",
            title="Primary Info",
            container=@Component(
                componentClass=JBPanel.class
            ),
            componentIndex=1
        ),
        @FormContainer(
            name="notes",
            title="Notes",
            container=@Component(
                componentClass=JBPanel.class
            ),
            layout=@Layout(layoutClass=BorderLayout.class)
        ),
        @FormContainer(
            name="documentDetails",
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Document Details"
                ),
                componentConstraints="span, growx"
            ),
            layout=@Layout(/*extraConstraints="debug", */columnsPairs=3),
            componentIndex=101,
            parentContainerName="primaryInfo"
        ),
        @FormContainer(
            name="publisherDetails",
            container=@Component(
                componentClass=JBPanel.class,
                componentBorder=@ComponentBorder(
                    borderType=BorderType.TitledBorder, title="Publisher Details"
                ),
                componentConstraints="span 2, sizegroup publisherSG, growx"
            ),
            layout=@Layout(/*extraConstraints="debug", */columnsPairs=1),
            componentIndex=102,
            parentContainerName="primaryInfo"
        )
    }
)
public abstract class BusinessDocument extends DataObjectBean implements StatusEnabledDocument, Serializable {

    private static final long serialVersionUID = 1L;
    //
    public static final String DELIVERY_CERTIFICATE = "DC";
    public static final String SALES_OFFER = "SO";
    public static final String SALES_PROFORMA_INVOICE = "SPFI";
    public static final String SALES_INVOICE = "SI";
    public static final String GOODS_RECEIPT = "GR";
    public static final String PURCHASE_ORDER = "PO";
    public static final String PURCHASE_ORDER_CONFIRMATION = "POC";
    public static final String PURCHASE_INVOICE = "PI";
    public static final String CASH_RECONCILE = "CR";

    //
    @Id
    @Basic(optional = false)
    @Column(name = "document_id", nullable = false)
    protected BigInteger documentId;

    @JoinColumn(name = "document_type_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    protected DbResource documentType;

    @Column(name = "document_number")
    @Property(title="Document Number",
        showOnly=true,
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName="documentDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Number:"
            ),
            secondComponent=@Component(
                componentClass=JBIntegerField.class,
                componentProperties={
                    @ComponentProperty(name="numberType", value="LongType")
                }
            )
        )
    )
    protected Long documentNumber;

    @Column(name = "document_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Property(title="Document Date",
        showOnly=true,
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName="documentDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Date: "
            ),
            secondComponent=@Component(
                componentClass=JBDatePicker.class
            )
        )
    )
    protected Date documentDate;

    @JoinColumn(name = "document_status_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Document Status",
        showOnly=true,
        editable=false,
        readOnly=true,
        updateStrategy=UpdateStrategy.READ,
        formComponentPair=@FormComponentPair(
            parentContainerName="documentDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Status:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    protected DbResource documentStatus;

    @Basic(optional = false)
    @Column(name = "discriminator_id", nullable = false, length = 4)
    protected String discriminatorId;

    @JoinColumn(name = "publisher_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Publisher",
        customDisplay="${publisher.organizationName}",
        showOnly=true,
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName="publisherDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Publisher:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    protected Organization publisher;

    @JoinColumn(name = "publisher_branch_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Publisher Branch",
        customDisplay="${publisherBranch.addressName}",
        showOnly=true,
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName="publisherDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Branch:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    protected Address publisherBranch;

    @JoinColumn(name = "publisher_officer_id", referencedColumnName = "partner_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Publisher Officer",
        customDisplay="${publisherOfficer.firstName} ${publisherOfficer.lastName}",
        showOnly=true,
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName="publisherDetails",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Officer:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    protected Person publisherOfficer;

    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date creationTime;

    @JoinColumn(name = "document_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    protected DataObject dataObject;

    @Transient
    private String className;

    public BusinessDocument() {
        throw new UnsupportedOperationException();
    }

    public BusinessDocument(String discriminatorId) {
        this.discriminatorId = discriminatorId;
    }

    public BusinessDocument(String discriminatorId, BigInteger documentId) {
        this(discriminatorId);
        this.documentId = documentId;
    }

    public String getDiscriminatorId() {
        return discriminatorId;
    }

    public void setDiscriminatorId(String discriminatorId) {
        this.discriminatorId = discriminatorId;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public BigInteger getDocumentId() {
        return documentId;
    }

    public void setDocumentId(BigInteger documentId) {
        this.documentId = documentId;
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    @Override
    public DbResource getDocumentStatus() {
        return documentStatus;
    }

    @Override
    public void setDocumentStatus(DbResource documentStatus) {
        this.documentStatus = documentStatus;
    }

    public DbResource getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DbResource documentType) {
        this.documentType = documentType;
    }

    public Organization getPublisher() {
        return publisher;
    }

    public void setPublisher(Organization publisher) {
        this.publisher = publisher;
        if(publisher != null) {
            setParentId(publisher.getId());
        } else {
            setParentId(null);
        }
    }

    public Address getPublisherBranch() {
        return publisherBranch;
    }

    public void setPublisherBranch(Address publisherBranch) {
        this.publisherBranch = publisherBranch;
    }

    public Person getPublisherOfficer() {
        return publisherOfficer;
    }

    public void setPublisherOfficer(Person publisherOfficer) {
        this.publisherOfficer = publisherOfficer;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode() {
        return (documentId != null ? documentId.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessDocument)) {
            return false;
        }

        BusinessDocument other = (BusinessDocument) object;
        if ((documentId == null && other.documentId != null) ||
                (documentId != null && !documentId.equals(other.documentId))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        if(className == null) {
            className = getClass().getSimpleName();
        }

        return className + "[id=" + documentId + "]@" + Integer.toHexString(super.hashCode());
    }

    @Override
    public BigInteger getId() {
        return getDocumentId();
    }

    @Override
    public void setId(BigInteger id) {
        setDocumentId(id);
    }

    @Override
    public BigInteger getParentId() {
        Organization organization;
        if((organization = getPublisher()) != null) {
            return organization.getId();
        }

        if(dataObject != null) {
            return dataObject.getParentDataObjectId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        return toString();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
