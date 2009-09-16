package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.Organization;
import java.io.Serializable;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

/**
 * The user participate in the following organizations:
 * @author Miroslav Nachev
 *
 */

@Entity
@Table(name = "user_organizations", catalog = "acacia", schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "organization_id"})
    }
)
@NamedQueries({
    @NamedQuery(
        name = UserOrganization.NQ_FIND_BY_USER,
        query = "select uo from UserOrganization uo where uo.user=:user"
    ),
    @NamedQuery(
        name = UserOrganization.NQ_FIND_BY_ORGANIZATION,
        query = "select uo from UserOrganization uo where uo.organization=:organization"
    ),
    @NamedQuery(
        name = UserOrganization.NQ_FIND_BY_USER_AND_ORGANIZATION,
        query = "select t1 from UserOrganization t1" +
                " WHERE" +
                "  t1.user = :user" +
                "  and t1.organization = :organization"
    ),
    @NamedQuery(
        name = UserOrganization.NQ_FIND_BY_BUSINESS_UNITS_AND_FUNCTIONAL_HIERARCHY,
        query = "select t1 from UserOrganization t1" +
                " WHERE" +
                "  t1.organization = :organization" +
                "  and t1.businessUnit in (:businessUnits)" +
                "  and t1.jobTitle.functionalHierarchy in (:functionalHierarchies)"
    )
})
public class UserOrganization extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "UserOrganization";
    public static final String NQ_FIND_BY_USER =
            CLASS_NAME + ".findByUser";
    public static final String NQ_FIND_BY_ORGANIZATION =
            CLASS_NAME + ".findByOrganization";
    public static final String NQ_FIND_BY_USER_AND_ORGANIZATION =
            CLASS_NAME + ".findByUserAndOrganization";
    public static final String NQ_FIND_BY_BUSINESS_UNITS_AND_FUNCTIONAL_HIERARCHY =
            CLASS_NAME + ".findByBusinessUnitsAndFunctionalHierarchy";

    @Id
    @Basic(optional = false)
    @Column(name = "user_organization_id", nullable = false, precision = 19, scale = 0)
    @Type(type="uuid")
    private UUID userOrganizationId;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="User")
    private User user;

    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Organization")
    private Organization organization;

    @Basic(optional = false)
    @Column(name = "is_user_active", nullable = false)
    private boolean userActive;

    @Column(name = "email_address", length = 64)
    private String emailAddress;

    @JoinColumn(name = "branch_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Branch", customDisplay="${branch.addressName}"
    )
    private Address branch;

    @JoinColumn(name = "business_unit_id", referencedColumnName = "business_unit_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Business Unit",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.users.BusinessUnitListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Business Unit:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private BusinessUnit businessUnit;

    @JoinColumn(name = "job_title_id", referencedColumnName = "job_title_id")
    @ManyToOne
    @Property(title="Job Title",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.users.JobTitleListPanel",
            constructorParameters={
                @PropertyName(getter="${this}")
            }
        ),
        depends={"businessUnit"},
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Job Title:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private JobTitle jobTitle;

    @JoinColumn(name = "manager_id", referencedColumnName = "user_id")
    @ManyToOne
    @Property(title="Manager",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.users.UserListPanel",
            constructorParameters={
                @PropertyName(getter="${entity}", setter="user")
            }
        ),
        depends={"jobTitle"},
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Manager:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private User manager;

    @JoinColumn(name = "user_organization_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public UserOrganization() {
    }

    public UserOrganization(UUID userOrganizationId) {
        this.userOrganizationId = userOrganizationId;
    }

    public UserOrganization(User user, Organization organization) {
        this.user = user;
        this.organization = organization;
    }

    public boolean isUserActive() {
        return userActive;
    }

    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public UUID getUserOrganizationId() {
        return userOrganizationId;
    }

    public void setUserOrganizationId(UUID userOrganizationId) {
        this.userOrganizationId = userOrganizationId;
    }

    public Address getBranch() {
        return branch;
    }

    public void setBranch(Address branch) {
        this.branch = branch;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @Override
    public UUID getId() {
        return getUserOrganizationId();
    }

    @Override
    public void setId(UUID id) {
        setUserOrganizationId(id);
    }

    @Override
    public UUID getParentId() {
        if(dataObject != null) {
            return dataObject.getParentDataObjectId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        if(user != null) {
            sb.append(user.getUserName());
        }
        sb.append(":");
        if(organization != null) {
            sb.append(organization.getOrganizationName());
        }
        sb.append(":");
        if(branch != null) {
            sb.append(branch.getAddressName());
        }
        return sb.toString();
    }
}
