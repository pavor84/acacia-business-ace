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
    implements Serializable
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

    @Column(name = "message_text", nullable = false)
    @Property(
        title="Message Text",
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH,
            maxLength=100,
            required=true))
    private String messageText;

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

}
