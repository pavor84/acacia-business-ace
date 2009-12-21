package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormContainer;
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
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTabbedPane;
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
        query = "select t from UserOrganization t" +
                " where" +
                "  t.user = :user" +
                "  and t.organization.businessPartnerId != t.organization.parentBusinessPartnerId"
    ),
    @NamedQuery(
        name = UserOrganization.NQ_FIND_ORGANIZATIONS_BY_USER,
        query = "select t.organization from UserOrganization t" +
                " where" +
                "  t.user = :user" +
                "  and t.organization.businessPartnerId != t.organization.parentBusinessPartnerId"
    ),
    @NamedQuery(
        name = UserOrganization.NQ_FIND_BY_ACTIVE_USER,
        query = "select t from UserOrganization t" +
                " where" +
                "  t.user = :user" +
                "  and t.userActive = true" +
                "  and t.organization.active = true" +
                "  and t.organization.businessPartnerId != t.organization.parentBusinessPartnerId"
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
    ),
    @NamedQuery(
        name = UserOrganization.NQ_UPDATE_DEFAULT_ORGANIZATIONS,
        query = "update UserOrganization t" +
                " set" +
                "  defaultOrganization = false" +
                " where" +
                "  t.organization = :organization" +
                "  and t.userOrganizationId != :userOrganizationId"
    )
})
@Form(
    formContainers={
        @FormContainer(
            name=UserOrganization.ROLES,
            title="Roles",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=UserSecurityRole.class
        ),
        @FormContainer(
            name=UserOrganization.GROUPS,
            title="Groups",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
            container=@Component(
                componentClass=JBTabbedPane.class
            )
        ),
        @FormContainer(
            name=UserOrganization.WORK_HOURS,
            title="Work Hours",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
            container=@Component(
                componentClass=JBTabbedPane.class
            )
        )
    },
    serviceClass=UsersServiceRemote.class
)
public class UserOrganization extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    public static final String ROLES = "roles";
    public static final String WORK_HOURS = "workHours";
    public static final String GROUPS = "groups";
    //
    protected static final String CLASS_NAME = "UserOrganization";
    public static final String NQ_FIND_BY_USER =
            CLASS_NAME + ".findByUser";
    public static final String NQ_FIND_ORGANIZATIONS_BY_USER =
            CLASS_NAME + ".findOrganizationsByUser";
    public static final String NQ_FIND_BY_ACTIVE_USER =
            CLASS_NAME + ".findByActiveUser";
    public static final String NQ_FIND_BY_ORGANIZATION =
            CLASS_NAME + ".findByOrganization";
    public static final String NQ_FIND_BY_USER_AND_ORGANIZATION =
            CLASS_NAME + ".findByUserAndOrganization";
    public static final String NQ_FIND_BY_BUSINESS_UNITS_AND_FUNCTIONAL_HIERARCHY =
            CLASS_NAME + ".findByBusinessUnitsAndFunctionalHierarchy";
    public static final String NQ_UPDATE_DEFAULT_ORGANIZATIONS =
            CLASS_NAME + ".updateDefaultOrganizations";

    @Id
    @Basic(optional = false)
    @Column(name = "user_organization_id", nullable = false)
    @Type(type="uuid")
    private UUID userOrganizationId;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="User", useEntityAttributes=false)
    private User user;

    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Organization")
    private Organization organization;

    @JoinColumn(name = "branch_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Branch",
        selectableList=@SelectableList(
            constructorParameters={@PropertyName(getter="organization", setter="businessPartner")}
        )
    )
    private Address branch;

    @Column(name = "email_address", length = 64)
    @Property(title="Email")
    private String emailAddress;

    @JoinColumn(name = "business_unit_id", referencedColumnName = "business_unit_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(
        selectableList=@SelectableList(
            constructorParameters={@PropertyName(getter="organization")}
        )
    )
    private BusinessUnit businessUnit;

    @JoinColumn(name = "job_title_id", referencedColumnName = "job_title_id")
    @ManyToOne
    @Property(
        selectableList=@SelectableList(constructorParameters={@PropertyName(getter="${this}")}),
        depends={"businessUnit"}
    )
    private JobTitle jobTitle;

    @JoinColumn(name = "manager_id", referencedColumnName = "user_id")
    @ManyToOne
    @Property(title="Manager",
        selectableList=@SelectableList(constructorParameters={@PropertyName(getter="manager", setter="user")}),
        depends={"jobTitle"}
    )
    private User manager;

    @Basic(optional = false)
    @Column(name = "is_user_active", nullable = false)
    @Property(title="Active")
    private boolean userActive;

    @Basic(optional = false)
    @Column(name = "default_organization", nullable = false)
    @Property(title="Default")
    private boolean defaultOrganization;

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

    public boolean isDefaultOrganization() {
        return defaultOrganization;
    }

    public void setDefaultOrganization(boolean defaultOrganization) {
        this.defaultOrganization = defaultOrganization;
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("; ").append(user);
        sb.append(", ").append(organization);
        sb.append(", ").append(branch);
        sb.append(", ").append(businessUnit);

        return sb.toString();
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
        sb.append(":");
        if(businessUnit != null) {
            sb.append(businessUnit.getBusinessUnitName());
        }

        return sb.toString();
    }

    @Override
    public String toShortText() {
        return getInfo();
    }

    @Override
    public String toText() {
        return getInfo();
    }
}
