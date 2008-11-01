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

    @Column(name = "selection_text", nullable = false)
    @Property(
        title="Selection Text",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=100,
            required=true))
    private String selectionText;

    @Column(name = "selection_title", nullable = false)
    @Property(
        title="Selection Title",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=100,
            required=true))
    private String selectionTitle;

    @Column(name = "input_text", nullable = false)
    @Property(
        title="Input Text",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=100,
            required=true))
    private String inputText;

    @Column(name = "input_title", nullable = false)
    @Property(
        title="Input Title",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=100,
            required=true))
    private String inputTitle;

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
        Width(
            "width",
            "Width",
            "Please select the width",
            "Width selection",
            "Please enter the width",
            "Width input"),
        Height(
            "height",
            "Height",
            "Please select the height",
            "Height selection",
            "Please enter the height",
            "Height input"),
        Length(
            "length",
            "Length",
            "Please select the length",
            "Length selection",
            "Please enter the length",
            "Length input"),
        Weight(
            "weight",
            "Weight",
            "Please select the weight",
            "Weight selection",
            "Please enter the weight",
            "Weight input"),
        Color(
            "color",
            "Color",
            "Please select the color",
            "Color selection",
            "Please enter the color",
            "Color input"),
        Product(
            "product",
            "Product",
            "Please select the product",
            "Product selection",
            "Please enter the product",
            "Product input"),
        Schema(
            "schema",
            "Schema",
            "Please select the schema",
            "Schema selection",
            "Please enter the schema",
            "Schema input"),
        Material(
            "material",
            "Material",
            "Please select the material",
            "Material selection",
            "Please enter the material",
            "Material input"),
        Direction(
            "direction",
            "Direction",
            "Please select the direction",
            "Direction selection",
            "Please enter the direction",
            "Direction input"),
        Profile(
            "profile",
            "Profile",
            "Please select the profile",
            "Profile selection",
            "Please enter the profile",
            "Profile input"),
        Orientation(
            "orientation",
            "Orientation",
            "Please select the orientation",
            "Orientation selection",
            "Please enter the orientation",
            "Orientation input"),
        Specification(
            "specification",
            "Specification",
            "Please select the specification",
            "Specification selection",
            "Please enter the specification",
            "Specification input"),
        ;

        private Unit(
            String messageCode,
            String messageLabel,
            String selectionText,
            String selectionTitle,
            String inputText,
            String inputTitle)
        {
            this(messageCode,
                    messageLabel,
                    selectionText,
                    selectionTitle,
                    inputText,
                    inputTitle,
                    null);
        }

        private Unit(
            String messageCode,
            String messageLabel,
            String selectionText,
            String selectionTitle,
            String inputText,
            String inputTitle,
            String description)
        {
            this.messageCode = messageCode;
            this.messageLabel = messageLabel;
            this.selectionText = selectionText;
            this.selectionTitle = selectionTitle;
            this.inputText = inputText;
            this.inputTitle = inputTitle;
            this.description = description;
        }

        private String messageCode;
        private String messageLabel;
        private String selectionText;
        private String selectionTitle;
        private String inputText;
        private String inputTitle;
        private String description;

        public void initAssemblingMessage(AssemblingMessage message)
        {
            message.messageCode = messageCode;
            message.messageLabel = messageLabel;
            message.selectionText = selectionText;
            message.selectionTitle = selectionTitle;
            message.inputText = inputText;
            message.inputTitle = inputTitle;
            message.description = description;
        }

        public String getDescription() {
            return description;
        }

        public String getInputText() {
            return inputText;
        }

        public String getInputTitle() {
            return inputTitle;
        }

        public String getMessageCode() {
            return messageCode;
        }

        public String getMessageLabel() {
            return messageLabel;
        }

        public String getSelectionText() {
            return selectionText;
        }

        public String getSelectionTitle() {
            return selectionTitle;
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
        this.selectionText = messageText;
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

    public String getSelectionText()
    {
        return selectionText;
    }

    public void setSelectionText(String selectionText)
    {
        this.selectionText = selectionText;
    }

    public String getMessageLabel()
    {
        return messageLabel;
    }

    public void setMessageLabel(String messageLabel)
    {
        this.messageLabel = messageLabel;
    }

    public String getSelectionTitle()
    {
        return selectionTitle;
    }

    public void setSelectionTitle(String selectionTitle)
    {
        this.selectionTitle = selectionTitle;
    }

    public String getInputText()
    {
        return inputText;
    }

    public void setInputText(String inputText)
    {
        this.inputText = inputText;
    }

    public String getInputTitle()
    {
        return inputTitle;
    }

    public void setInputTitle(String inputTitle)
    {
        this.inputTitle = inputTitle;
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
        return getMessageCode();
    }

    @Override
    public int compareTo(AssemblingMessage other)
    {
        return messageId.compareTo(other.messageId);
    }

}
