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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "position_types")
public class PositionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "position_type_id", nullable = false)
    private BigInteger positionTypeId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @Column(name = "owner_type", nullable = false)
    private char ownerType;

    @Column(name = "position_type_name", nullable = false)
    private String positionTypeName;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "position_type_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public PositionType() {
    }

    public PositionType(BigInteger positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    public BigInteger getPositionTypeId() {
        return positionTypeId;
    }

    public void setPositionTypeId(BigInteger positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public char getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(char ownerType) {
        this.ownerType = ownerType;
    }

    public String getPositionTypeName() {
        return positionTypeName;
    }

    public void setPositionTypeName(String positionTypeName) {
        this.positionTypeName = positionTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (positionTypeId != null ? positionTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PositionType)) {
            return false;
        }
        PositionType other = (PositionType) object;
        if ((this.positionTypeId == null && other.positionTypeId != null) || (this.positionTypeId != null && !this.positionTypeId.equals(other.positionTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.PositionType[positionTypeId=" + positionTypeId + "]";
    }

}
