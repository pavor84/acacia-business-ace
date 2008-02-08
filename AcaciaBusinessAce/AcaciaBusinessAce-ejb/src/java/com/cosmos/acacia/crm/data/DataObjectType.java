/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "data_object_types")
@NamedQueries({@NamedQuery(name = "DataObjectType.findByDataObjectTypeId",
                query = "SELECT d FROM DataObjectType d WHERE d.dataObjectTypeId = :dataObjectTypeId"),
               @NamedQuery(name = "DataObjectType.findByDataObjectType",
                query = "SELECT d FROM DataObjectType d WHERE d.dataObjectType = :dataObjectType")})
public class DataObjectType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="DOTSequenceGenerator", sequenceName="data_object_type_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOTSequenceGenerator")
    @Column(name = "data_object_type_id", nullable = false)
    private Integer dataObjectTypeId;
    @Column(name = "data_object_type", nullable = false)
    private String dataObjectType;
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
        return "com.cosmos.acacia.crm.data.DataObjectType[dataObjectTypeId=" +
               dataObjectTypeId + "]";
    }

}
