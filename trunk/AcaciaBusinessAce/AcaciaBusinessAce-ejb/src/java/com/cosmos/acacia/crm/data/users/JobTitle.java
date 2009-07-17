/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.crm.data.security.*;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "job_titles", catalog = "acacia", schema = "public")
@NamedQueries({@NamedQuery(name = "JobTitle.findAll", query = "SELECT j FROM JobTitle j"), @NamedQuery(name = "JobTitle.findByJobTitleId", query = "SELECT j FROM JobTitle j WHERE j.jobTitleId = :jobTitleId"), @NamedQuery(name = "JobTitle.findByJobTitle", query = "SELECT j FROM JobTitle j WHERE j.jobTitle = :jobTitle")})
public class JobTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "job_title_id", nullable = false, precision = 19, scale = 0)
    private BigDecimal jobTitleId;

    @Basic(optional = false)
    @Column(name = "job_title", nullable = false, length = 100)
    private String jobTitle;

    @JoinColumn(name = "business_unit_id", referencedColumnName = "business_unit_id", nullable = false)
    @ManyToOne(optional = false)
    private BusinessUnit businessUnitId;

    @JoinColumn(name = "job_title_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    @JoinColumn(name = "functional_hierarchy_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    private DbResource functionalHierarchyId;

    @JoinColumn(name = "security_role_id", referencedColumnName = "security_role_id", nullable = false)
    @ManyToOne(optional = false)
    private SecurityRole securityRoleId;

    public JobTitle() {
    }

    public JobTitle(BigDecimal jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public JobTitle(BigDecimal jobTitleId, String jobTitle) {
        this.jobTitleId = jobTitleId;
        this.jobTitle = jobTitle;
    }

    public BigDecimal getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(BigDecimal jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BusinessUnit getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(BusinessUnit businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DbResource getFunctionalHierarchyId() {
        return functionalHierarchyId;
    }

    public void setFunctionalHierarchyId(DbResource functionalHierarchyId) {
        this.functionalHierarchyId = functionalHierarchyId;
    }

    public SecurityRole getSecurityRoleId() {
        return securityRoleId;
    }

    public void setSecurityRoleId(SecurityRole securityRoleId) {
        this.securityRoleId = securityRoleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobTitleId != null ? jobTitleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JobTitle)) {
            return false;
        }
        JobTitle other = (JobTitle) object;
        if ((this.jobTitleId == null && other.jobTitleId != null) || (this.jobTitleId != null && !this.jobTitleId.equals(other.jobTitleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.JobTitle[jobTitleId=" + jobTitleId + "]";
    }
}
