/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "data_object_links")
@NamedQueries({})
public class DataObjectLink
    extends DataObjectBean
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "data_object_link_id", nullable = false)
    private BigInteger dataObjectLinkId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "linked_data_object_id", referencedColumnName = "data_object_id")
    @ManyToOne
    private DataObject linkedDataObject;

    @Transient
    private DataObjectBean linkedDataObjectBean;

    @Column(name = "link_name", nullable = false)
    private String linkName;

    @JoinColumn(name = "data_object_link_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public DataObjectLink() {
    }

    public DataObjectLink(BigInteger dataObjectLinkId) {
        this.dataObjectLinkId = dataObjectLinkId;
    }

    public DataObjectLink(BigInteger dataObjectLinkId, String linkName) {
        this.dataObjectLinkId = dataObjectLinkId;
        this.linkName = linkName;
    }

    public BigInteger getDataObjectLinkId() {
        return dataObjectLinkId;
    }

    public void setDataObjectLinkId(BigInteger dataObjectLinkId) {
        this.dataObjectLinkId = dataObjectLinkId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public DataObject getLinkedDataObject() {
        return linkedDataObject;
    }

    public void setLinkedDataObject(DataObject linkedDataObject) {
        checkForUpdatePermission();
        this.linkedDataObject = linkedDataObject;
    }

    public DataObjectBean getLinkedDataObjectBean() {
        return linkedDataObjectBean;
    }

    public void setLinkedDataObjectBean(DataObjectBean linkedDataObjectBean) {
        checkForUpdatePermission();
        this.linkedDataObjectBean = linkedDataObjectBean;
        if(linkedDataObjectBean != null)
            setLinkedDataObject(linkedDataObjectBean.getDataObject());
        else
            setLinkedDataObject(null);
    }

    private void checkForUpdatePermission()
    {
        if(linkedDataObjectBean != null && dataObjectLinkId != null)
            throw new RuntimeException("The Linked Object can not be chanded. Remove whole Link and create new Link.");
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
        hash += (dataObjectLinkId != null ? dataObjectLinkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataObjectLink)) {
            return false;
        }
        DataObjectLink other = (DataObjectLink) object;
        if ((this.dataObjectLinkId == null && other.dataObjectLinkId != null) || (this.dataObjectLinkId != null && !this.dataObjectLinkId.equals(other.dataObjectLinkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.DataObjectLink[dataObjectLinkId=" + dataObjectLinkId + "]";
    }


    @Override
    public BigInteger getId() {
        return getDataObjectLinkId();
    }

    @Override
    public void setId(BigInteger id) {
        setDataObjectLinkId(id);
    }

}
