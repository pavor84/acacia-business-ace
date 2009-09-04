/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.ComponentProperty;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObject;
import java.io.Serializable;
import java.util.UUID;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTabbedPane;
import com.cosmos.swingb.JBTextField;
import javax.persistence.Basic;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "users", catalog = "acacia", schema = "public"/*,
CREATE UNIQUE INDEX uix_users_username
   ON users (lower(user_name));
CREATE UNIQUE INDEX uix_users_email_address
   ON users (lower(email_address));
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email_address"}),
        @UniqueConstraint(columnNames = {"user_name"})
    }*/
)
@NamedQueries({
    @NamedQuery(
            name = "User.login",
            query = "select u from User u where u.userName=:username and u.userPassword=:password"
    ),
    @NamedQuery(
            name = "User.temporaryLogin",
            query = "select u from User u where u.userName=:username and u.systemPassword=:password"
    ),
    @NamedQuery(
            name = "User.findCreatedUsers",
            query = "select u from User u where u.creator=:creator"
    ),
    @NamedQuery(
        name = User.NQ_FIND_BY_EMAIL,
        query = "select u from User u where u.emailAddress = :emailAddress"
    ),
    @NamedQuery(
            name = "User.findByUserName",
            query = "select u from User u where u.userName=:userName"
    ),
    @NamedQuery(
            name = "User.findAll",
            query = "select u from User u"
    ),
    @NamedQuery(
            name = "User.findByPerson",
            query = "select u from User u where u.person=:person"
    )
})
@Form(
    mainContainer=@FormContainer(
        name=DataObjectBean.MAIN_TABBED_PANE,
        container=@Component(
            componentClass=JBTabbedPane.class,
            componentProperties={
                @ComponentProperty(name="tabPlacement", value=ComponentProperty.JTabbedPane_TabPlacement_LEFT)
            }
        )
    ),
    formContainers={
        @FormContainer(
            name=DataObjectBean.PRIMARY_INFO,
            title="Primary Info",
            container=@Component(
                componentClass=JBTabbedPane.class
            ),
            componentIndex=1
        ),
        @FormContainer(
            name=User.ROLES,
            title="Roles",
            depends={FormContainer.DEPENDS_ENTITY_FORM},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=UserSecurityRole.class
        ),
        @FormContainer(
            name=User.GROUPS,
            title="Groups",
            container=@Component(
                componentClass=JBTabbedPane.class
            )
        ),
        @FormContainer(
            name=User.WORK_HOURS,
            title="Work Hours",
            container=@Component(
                componentClass=JBTabbedPane.class
            )
        ),
        @FormContainer(
            name=User.INFO_GENERAL,
            title="General",
            container=@Component(
                componentClass=JBPanel.class
            ),
            parentContainerName=DataObjectBean.PRIMARY_INFO
        ),
        @FormContainer(
            name=User.INFO_ADDRESSES,
            title="Addresses",
            container=@Component(
                componentClass=JBPanel.class
            ),
            parentContainerName=DataObjectBean.PRIMARY_INFO
        )
    },
    serviceClass=UsersServiceRemote.class
)
public class User extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "User";
    public static final String NQ_FIND_BY_EMAIL = CLASS_NAME + ".findByEmail";
    //
    public static final String INFO_GENERAL = "informationGeneral";
    public static final String INFO_ADDRESSES = "informationAddresses";
    public static final String WORK_HOURS = "workHours";
    public static final String GROUPS = "groups";
    public static final String ROLES = "roles";
    //
    public static final String SUPERVISOR_USER_NAME = "supervisor";
    public static final String SUPERVISOR_USER_PASSWORD = "Superv1s0r";

    @Id
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false, precision = 19, scale = 0)
    @Type(type="uuid")
    @Property(title="User ID", visible=false, hidden=true)
    private UUID userId;

    @JoinColumn(name = "system_organization_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    private Organization systemOrganization;

    @Basic(optional = false)
    @Column(name = "version", nullable = false)
    @Property(title="Version", visible=false, hidden=true)
    private int version;

    @Basic(optional = false)
    @Column(name = "user_name", nullable = false, length = 32, unique = true)
    @Property(title="Username",
        editable=false,
        readOnly=true,
        formComponentPair=@FormComponentPair(
            parentContainerName=INFO_GENERAL,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Username:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        ),
        propertyValidator=@PropertyValidator(
            validationType=ValidationType.LENGTH, minLength=3, maxLength=32, regex="[^,]+", tooltip="username.tooltip")
    )
    private String userName;

    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    @Property(title="Person",
        customDisplay="${person.displayName}",
        editable=false,
        readOnly=true,
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.contactbook.PersonsListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=INFO_GENERAL,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Person:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private Person person;

    @Basic(optional = false)
    @Column(name = "email_address", nullable = false, length = 64, unique = true)
    @Property(title="Email address",
        formComponentPair=@FormComponentPair(
            parentContainerName=INFO_GENERAL,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Email:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String emailAddress;

    @Basic(optional = false)
    @Column(name = "user_password", nullable = false, length = 64)
    @Property(title="Password",
        formComponentPair=@FormComponentPair(
            parentContainerName=INFO_GENERAL,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Password:"
            ),
            secondComponent=@Component(
                componentClass=JBButton.class,
                text="Change"
            )
        ),
        visible=false,
        propertyValidator=@PropertyValidator(
        validationType=ValidationType.LENGTH, minLength=6, maxLength=32, tooltip="password.tooltip")
    )
    private String userPassword;

    @Column(name = "system_password", length = 64)
    @Property(title="System password", visible=false, hidden=true)
    private String systemPassword;

    @Column(name = "system_password_validity")
    @Temporal(TemporalType.TIMESTAMP)
    @Property(title="System password validity", visible=false, hidden=true)
    private Date systemPasswordValidity;

    @Basic(optional = false)
    @Column(name = "is_new", nullable = false)
    private boolean isNew;

    @Basic(optional = false)
    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Property(title="Creation time")
    private Date creationTime;

    @Column(name = "next_action_after_login", length = 1024)
    @Property(title="Next action after login")
    private String nextActionAfterLogin;

    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    @ManyToOne
    @Property(title="Creator", customDisplay="${creator.userName}")
    private User creator;

    @JoinColumn(name = "user_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public User() {
    }

    public User(UUID userId) {
        this.userId = userId;
    }

    public User(Organization systemOrganization) {
        setSystemOrganization(systemOrganization);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSystemPassword() {
        return systemPassword;
    }

    public void setSystemPassword(String systemPassword) {
        this.systemPassword = systemPassword;
    }

    public Date getSystemPasswordValidity() {
        return systemPasswordValidity;
    }

    public void setSystemPasswordValidity(Date systemPasswordValidity) {
        this.systemPasswordValidity = systemPasswordValidity;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getNextActionAfterLogin() {
        return nextActionAfterLogin;
    }

    public void setNextActionAfterLogin(String nextActionAfterLogin) {
        this.nextActionAfterLogin = nextActionAfterLogin;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Organization getSystemOrganization() {
        return systemOrganization;
    }

    public void setSystemOrganization(Organization systemOrganization) {
        this.systemOrganization = systemOrganization;
        if(systemOrganization != null) {
            setParentId(systemOrganization.getBusinessPartnerId());
        } else {
            setParentId(null);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("; username=").append(userName);
        sb.append(", email=").append(emailAddress);
        return sb.toString();
    }

    @Override
    public UUID getId() {
        return userId;
    }

    @Override
    public UUID getParentId() {
        if(systemOrganization != null) {
            return systemOrganization.getBusinessPartnerId();
        }

        return null;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public void setId(UUID id) {
        this.userId = id;

    }

    @Override
    public void setParentId(UUID parentId) {
        //
    }

    @Override
    public String getInfo() {
        return getUserName();
    }
}
