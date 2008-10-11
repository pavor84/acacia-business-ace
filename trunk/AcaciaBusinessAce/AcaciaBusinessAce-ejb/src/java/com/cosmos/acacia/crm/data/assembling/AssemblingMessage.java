/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Organization;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "assembling_messages")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "AssemblingMessage.findByOrganization",
                query = "select t1 from AssemblingMessage t1" +
                    " where" +
                    "  t1.organization = :organization" +
                    "  and t1.dataObject.deleted = false"
            )
    })
public class AssemblingMessage
    extends DataObjectBean
    implements Serializable, Comparable<AssemblingMessage>
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "message_id", nullable = false)
    @Property(title="Message Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger messageId;

    @Column(name = "message_code", nullable = false)
    @Property(
        title="Message Code",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=50,
            required=true))
    private String messageCode;

    @Column(name = "message_label", nullable = false)
    @Property(
        title="Message Label",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=50,
            required=true))
    private String messageLabel;

    @Column(name = "message_text", nullable = false)
    @Property(
        title="Message Text",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=100,
            required=true))
    private String messageText;

    @Column(name = "message_title", nullable = false)
    @Property(
        title="Message Title",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=100,
            required=true))
    private String messageTitle;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id")
    @ManyToOne
    @Property(title="Organization owner", editable=false, readOnly=true, visible=false, hidden=true)
    private Organization organization;

    @OneToOne
    @PrimaryKeyJoinColumn
    private DataObject dataObject;

    public enum Unit
    {
        WidthSelection(
            "widthSelection",
            "Width",
            "Please select the width",
            "Width selection"),
        WidthInput(
            "widthInput",
            "Width",
            "Please enter the width",
            "Width input"),
        HeightSelection(
            "heightSelection",
            "Height",
            "Please select the height",
            "Height selection"),
        HeightInput(
            "heightInput",
            "Height",
            "Please enter the height",
            "Height input"),
        LengthSelection(
            "lengthSelection",
            "Length",
            "Please select the length",
            "Length selection"),
        LengthInput(
            "lengthInput",
            "Length",
            "Please enter the length",
            "Length input"),
        WeightSelection(
            "weightSelection",
            "Weight",
            "Please select the weight",
            "Weight selection"),
        WeightInput(
            "weightInput",
            "Weight",
            "Please enter the weight",
            "Weight input"),
        ColorSelection(
            "colorSelection",
            "Color",
            "Please select the color",
            "Color selection"),
        ProductSelection(
            "productSelection",
            "Product",
            "Please select the product",
            "Product selection"),
        SchemaSelection(
            "schemaSelection",
            "Schema",
            "Please select the schema",
            "Schema selection"),
        MaterialSelection(
            "materialSelection",
            "Material",
            "Please select the material",
            "Material selection"),
        DirectionSelection(
            "directionSelection",
            "Direction",
            "Please select the direction",
            "Direction selection"),
        ProfileSelection(
            "profileSelection",
            "Profile",
            "Please select the profile",
            "Profile selection"),
        OrientationSelection(
            "orientationSelection",
            "Orientation",
            "Please select the orientation",
            "Orientation selection"),
        SpecificationSelection(
            "specificationSelection",
            "Specification",
            "Please select the specification",
            "Specification selection"),
        ;

        private Unit(
            String messageCode,
            String messageLabel,
            String messageText,
            String messageTitle)
        {
            this(messageCode, messageLabel, messageText, messageTitle, null);
        }

        private Unit(
            String messageCode,
            String messageLabel,
            String messageText,
            String messageTitle,
            String description)
        {
            this.messageCode = messageCode;
            this.messageLabel = messageLabel;
            this.messageText = messageText;
            this.messageTitle = messageTitle;
            this.description = description;
        }

        private String messageCode;
        private String messageLabel;
        private String messageText;
        private String messageTitle;
        private String description;

        public void initAssemblingMessage(AssemblingMessage message)
        {
            message.messageCode = messageCode;
            message.messageLabel = messageLabel;
            message.messageText = messageText;
            message.messageTitle = messageTitle;
            message.description = description;
        }
    }


    public AssemblingMessage()
    {
    }

    public AssemblingMessage(BigInteger messageId)
    {
        this.messageId = messageId;
    }

    public AssemblingMessage(BigInteger messageId, String messageCode, String messageText)
    {
        this.messageId = messageId;
        this.messageCode = messageCode;
        this.messageText = messageText;
    }

    public BigInteger getMessageId()
    {
        return messageId;
    }

    public void setMessageId(BigInteger messageId)
    {
        this.messageId = messageId;
    }

    public String getMessageCode()
    {
        return messageCode;
    }

    public void setMessageCode(String messageCode)
    {
        this.messageCode = messageCode;
    }

    public String getMessageText()
    {
        return messageText;
    }

    public void setMessageText(String messageText)
    {
        this.messageText = messageText;
    }

    public String getMessageLabel()
    {
        return messageLabel;
    }

    public void setMessageLabel(String messageLabel)
    {
        this.messageLabel = messageLabel;
    }

    public String getMessageTitle()
    {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle)
    {
        this.messageTitle = messageTitle;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Organization getOrganization()
    {
        return organization;
    }

    public void setOrganization(Organization organization)
    {
        this.organization = organization;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof AssemblingMessage))
        {
            return false;
        }
        AssemblingMessage other = (AssemblingMessage) object;
        if((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.cosmos.acacia.crm.data.assembling.AssemblingMessage[messageId=" + messageId +
            ", code=" + messageCode + "]";
    }

    @Override
    public DataObject getDataObject()
    {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject)
    {
        this.dataObject = dataObject;
    }

    @Override
    public BigInteger getId()
    {
        return getMessageId();
    }

    @Override
    public void setId(BigInteger id)
    {
        setMessageId(id);
    }

    @Override
    public BigInteger getParentId()
    {
        if(organization == null)
            return null;

        return organization.getId();
    }

    @Override
    public void setParentId(BigInteger parentId)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInfo()
    {
        return getMessageText();
    }

    @Override
    public int compareTo(AssemblingMessage other)
    {
        return messageId.compareTo(other.messageId);
    }

}
