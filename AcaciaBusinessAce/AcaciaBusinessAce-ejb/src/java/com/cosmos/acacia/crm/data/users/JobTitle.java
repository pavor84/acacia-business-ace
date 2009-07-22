/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Layout;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTabbedPane;
import java.awt.BorderLayout;
import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "job_titles", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"business_unit_id", "job_title"})}
)
@NamedQueries({
    @NamedQuery(
        name = JobTitle.NQ_FIND_ALL,
        query = "SELECT t FROM JobTitle t" +
                " WHERE" +
                "  t.businessUnit = :businessUnit"
    ),
    @NamedQuery(
        name = JobTitle.NQ_DELETE_ALL,
        query = "DELETE FROM JobTitle t" +
                " WHERE" +
                "  t.businessUnit = :businessUnit"
    )
})
@Form(
    mainContainer=@FormContainer(
        name="mainTabbedPane",
        container=@Component(
            componentClass=JBTabbedPane.class
        )
    ),
    formContainers={
        @FormContainer(
            name="primaryInfo",
            title="Primary Info",
            container=@Component(
                componentClass=JBPanel.class
            )
        ),
        @FormContainer(
            name="notes",
            title="Notes",
            container=@Component(
                componentClass=JBPanel.class
            ),
            layout=@Layout(layoutClass=BorderLayout.class)
        )
    },
    serviceClass=UsersServiceRemote.class
)
public class JobTitle extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "JobTitle";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_DELETE_ALL = CLASS_NAME + ".deleteAll";

    @Id
    @Basic(optional = false)
    @Column(name = "job_title_id", nullable = false, precision = 19, scale = 0)
    private BigInteger jobTitleId;

    @JoinColumn(name = "business_unit_id", referencedColumnName = "business_unit_id", nullable = false)
    @ManyToOne(optional = false)
    private BusinessUnit businessUnit;

    @Basic(optional = false)
    @Column(name = "job_title", nullable = false, length = 100)
    @Property(title="Job Title"
    )
    private String jobTitle;

    @JoinColumn(name = "functional_hierarchy_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Functional Hierarchy"
    )
    private DbResource functionalHierarchy;

    @JoinColumn(name = "security_role_id", referencedColumnName = "security_role_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Security Role"
    )
    private SecurityRole securityRole;

    @JoinColumn(name = "job_title_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public JobTitle() {
    }

    public JobTitle(BigInteger jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public JobTitle(BigInteger jobTitleId, String jobTitle) {
        this.jobTitleId = jobTitleId;
        this.jobTitle = jobTitle;
    }

    public BigInteger getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(BigInteger jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
        if(businessUnit != null) {
            setParentId(businessUnit.getBusinessUnitId());
        } else {
            setParentId(null);
        }
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DbResource getFunctionalHierarchy() {
        return functionalHierarchy;
    }

    public void setFunctionalHierarchy(DbResource functionalHierarchy) {
        this.functionalHierarchy = functionalHierarchy;
    }

    public SecurityRole getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(SecurityRole securityRole) {
        this.securityRole = securityRole;
    }

    @Override
    public BigInteger getId() {
        return getJobTitleId();
    }

    @Override
    public void setId(BigInteger id) {
        setJobTitleId(id);
    }

    @Override
    public BigInteger getParentId() {
        if(businessUnit != null) {
            return businessUnit.getBusinessUnitId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        return getJobTitle();
    }
}
