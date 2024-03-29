/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.util.PersistentEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Miro
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "data_object_types")
@NamedQueries(
    {
        @NamedQuery(
            name = "DataObjectType.findByDataObjectTypeId",
            query = "SELECT d FROM DataObjectType d WHERE d.dataObjectTypeId = :dataObjectTypeId",
            hints={
                @QueryHint(name="org.hibernate.cacheable", value="true")
            }
        ),
        @NamedQuery(
            name = "DataObjectType.findByDataObjectType",
            query = "SELECT d FROM DataObjectType d WHERE d.dataObjectType = :dataObjectType",
            hints={
                @QueryHint(name="org.hibernate.cacheable", value="true")
            }
        ),
        @NamedQuery(
            name = "DataObjectType.listAll",
            query = "SELECT dot FROM DataObjectType dot"
        )
    }
)
public class DataObjectType implements Serializable, PersistentEntity<DataObjectType, Integer> {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="DOTSequenceGenerator", sequenceName="data_object_type_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOTSequenceGenerator")
    @Column(name = "data_object_type_id", nullable = false)
    @Property(title="Id", editable=false, readOnly=true, visible=false, hidden=true)
    private Integer dataObjectTypeId;
    
    @Column(name = "data_object_type", nullable = false)
    @Property(title="Type")
    private String dataObjectType;
    
    @Column(name = "notes")
    @Property(title="Notes")
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

    public DataObjectType() {
    }

    public DataObjectType(Integer dataObjectTypeId) {
        this.dataObjectTypeId = dataObjectTypeId;
    }

    public DataObjectType(Integer dataObjectTypeId, String dataObjectType) {
        this.dataObjectTypeId = dataObjectTypeId;
        this.dataObjectType = dataObjectType;
    }

    public Integer getDataObjectTypeId() {
        return dataObjectTypeId;
    }

    public void setDataObjectTypeId(Integer dataObjectTypeId) {
        this.dataObjectTypeId = dataObjectTypeId;
    }

    public String getDataObjectType() {
        return dataObjectType;
    }

    public void setDataObjectType(String dataObjectType) {
        this.dataObjectType = dataObjectType;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataObjectTypeId != null ? dataObjectTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataObjectType)) {
            return false;
        }
        DataObjectType other = (DataObjectType) object;
        if ((this.dataObjectTypeId == null && other.dataObjectTypeId != null) ||
            (this.dataObjectTypeId != null &&
             !this.dataObjectTypeId.equals(other.dataObjectTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("[id=").append(dataObjectTypeId).append("]@");
        sb.append(Integer.toHexString(super.hashCode()));
        sb.append("; dataObjectType=").append(dataObjectType);
        
        return sb.toString();
    }

    @Override
    public DataObjectType clone() {
        try {
            return (DataObjectType) super.clone();
        } catch(CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Integer getId() {
        return getDataObjectTypeId();
    }
}
