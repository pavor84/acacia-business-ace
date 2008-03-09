package com.cosmos.acacia.crm.data;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "data_object_details")
public class DataObjectDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "data_object_id", nullable = false)
    private BigInteger dataObjectId;

    @Column(name = "detail_code", nullable = false)
    private String detailCode;

    @Column(name = "detail_value", nullable = false)
    private String detailValue;

    @Column(name = "notes")
    private String notes;


    public DataObjectDetail() {
    }

    public DataObjectDetail(BigInteger dataObjectId) {
        this.dataObjectId = dataObjectId;
    }

    public BigInteger getDataObjectId() {
        return dataObjectId;
    }

    public void setDataObjectId(BigInteger dataObjectId) {
        this.dataObjectId = dataObjectId;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        if (!(object instanceof DataObjectDetail)) {
            return false;
        }
        DataObjectDetail other = (DataObjectDetail) object;
        if ((this.dataObjectId == null && other.dataObjectId != null) || (this.dataObjectId != null && !this.dataObjectId.equals(other.dataObjectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DataObjectDetail[dataObjectId=" + dataObjectId + "]";
    }

}
