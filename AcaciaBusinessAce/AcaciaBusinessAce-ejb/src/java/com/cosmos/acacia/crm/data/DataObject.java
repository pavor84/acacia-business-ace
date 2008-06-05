/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "data_objects")
public class DataObject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "data_object_id", nullable = false)
    @GenericGenerator
    (
        name="DOSequenceGenerator",
        strategy = "com.cosmos.acacia.persistence.id.GUIDSequenceGenerator",
        parameters =
        {
            @Parameter(name = "sequence", value = "xyz_id_sequence")
        }
    )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "DOSequenceGenerator")
    private BigInteger dataObjectId;

    @Column(name = "data_object_version", nullable = false)
    private int dataObjectVersion;

    @JoinColumn(name = "data_object_type_id", referencedColumnName = "data_object_type_id")
    @ManyToOne
    private DataObjectType dataObjectType;

    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime = new Date();

    @Column(name = "creator_id", nullable = false)
    private long creatorId;

    @Column(name = "owner_id", nullable = false)
    private long ownerId;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    @Column(name = "is_read_only", nullable = false)
    private boolean readOnly;

    @Column(name = "is_system", nullable = false)
    private boolean isSystem;

    @Column(name = "is_folder", nullable = false)
    private boolean isFolder;

    @Column(name = "is_link", nullable = false)
    private boolean isLink;

    @Column(name = "order_position")
    private String orderPosition;

    @Column(name = "child_counter")
    private Integer childCounter;

    @Column(name = "notes")
    private String notes;

    @Column(name = "small_image_uri")
    private String smallImageUri;

    //@Lob
    @Column(name = "small_image")
    private byte[] smallImage;

    @Column(name = "medium_image_uri")
    private String mediumImageUri;

    //@Lob
    @Column(name = "medium_image")
    private byte[] mediumImage;

    @Column(name = "data_object_uri")
    private String dataObjectUri;

    @Column(name = "parent_data_object_id")
    private BigInteger parentDataObjectId;

    @Column(name = "linked_data_object_id")
    private BigInteger linkedDataObjectId;


    public DataObject() {
    }

    public DataObject(BigInteger dataObjectId) {
        this.dataObjectId = dataObjectId;
    }

    public BigInteger getDataObjectId() {
        return dataObjectId;
    }

    public void setDataObjectId(BigInteger dataObjectId) {
        this.dataObjectId = dataObjectId;
    }

    public int getDataObjectVersion() {
        return dataObjectVersion;
    }

    public void setDataObjectVersion(int version) {
        this.dataObjectVersion = version;
    }

    public DataObjectType getDataObjectType() {
        return dataObjectType;
    }

    public void setDataObjectType(DataObjectType dataObjectType) {
        this.dataObjectType = dataObjectType;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public boolean getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(boolean isFolder) {
        this.isFolder = isFolder;
    }

    public boolean getIsLink() {
        return isLink;
    }

    public void setIsLink(boolean isLink) {
        this.isLink = isLink;
    }

    public String getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(String orderPosition) {
        this.orderPosition = orderPosition;
    }

    public Integer getChildCounter() {
        return childCounter;
    }

    public void setChildCounter(Integer childCounter) {
        this.childCounter = childCounter;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSmallImageUri() {
        return smallImageUri;
    }

    public void setSmallImageUri(String smallImageUri) {
        this.smallImageUri = smallImageUri;
    }

    public byte[] getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(byte[] smallImage) {
        this.smallImage = smallImage;
    }

    public String getMediumImageUri() {
        return mediumImageUri;
    }

    public void setMediumImageUri(String mediumImageUri) {
        this.mediumImageUri = mediumImageUri;
    }

    public byte[] getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(byte[] mediumImage) {
        this.mediumImage = mediumImage;
    }

    public String getDataObjectUri() {
        return dataObjectUri;
    }

    public void setDataObjectUri(String dataObjectUri) {
        this.dataObjectUri = dataObjectUri;
    }

    public BigInteger getParentDataObjectId() {
        return parentDataObjectId;
    }

    public void setParentDataObjectId(BigInteger parentDataObjectId) {
        this.parentDataObjectId = parentDataObjectId;
    }

    public BigInteger getLinkedDataObjectId() {
        return linkedDataObjectId;
    }

    public void setLinkedDataObjectId(BigInteger linkedDataObjectId) {
        this.linkedDataObjectId = linkedDataObjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataObjectId != null ? dataObjectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataObject)) {
            return false;
        }
        DataObject other = (DataObject) object;
        if ((this.dataObjectId == null && other.dataObjectId != null) ||
            (this.dataObjectId != null &&
             !this.dataObjectId.equals(other.dataObjectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DataObject[dataObjectId=" +
               dataObjectId + "]";
    }

}
