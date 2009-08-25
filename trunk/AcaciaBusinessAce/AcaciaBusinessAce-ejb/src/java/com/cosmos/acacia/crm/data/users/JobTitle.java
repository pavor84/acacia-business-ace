/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBTextField;
import java.io.Serializable;
import java.util.UUID;
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
import org.hibernate.annotations.Type;

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
    @Type(type="uuid")
    private UUID jobTitleId;

    @JoinColumn(name = "business_unit_id", referencedColumnName = "business_unit_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Business Unit")
    private BusinessUnit businessUnit;

    @Basic(optional = false)
    @Column(name = "job_title", nullable = false, length = 100)
    @Property(title="Job Title",
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Job Title:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String jobTitle;

    @JoinColumn(name = "functional_hierarchy_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Functional Hierarchy",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.FunctionalHierarchy"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Functional Hierarchy:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource functionalHierarchy;

    @JoinColumn(name = "security_role_id", referencedColumnName = "security_role_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Security Role",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.security.SecurityRoleListPanel",
            constructorParameters={
                @PropertyName(getter="businessUnit")
            }
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName="primaryInfo",
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Security Role:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private SecurityRole securityRole;

    @JoinColumn(name = "job_title_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public JobTitle() {
    }

    public JobTitle(UUID jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public JobTitle(UUID jobTitleId, String jobTitle) {
        this.jobTitleId = jobTitleId;
        this.jobTitle = jobTitle;
    }

    public UUID getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(UUID jobTitleId) {
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
    public UUID getId() {
        return getJobTitleId();
    }

    @Override
    public void setId(UUID id) {
        setJobTitleId(id);
    }

    @Override
    public UUID getParentId() {
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
