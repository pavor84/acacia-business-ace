/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import javax.persistence.Transient;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "users")
@NamedQueries(
        {
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
                    name = "User.findByEmail",
                    query = "select u from User u where u.emailAddress=:email"
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
        }
)
public class User extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id", nullable = false)
    @Property(title="User ID", visible=false, hidden=true)
    private BigInteger userId;

    @Column(name = "version", nullable = false)
    @Property(title="Version", visible=false, hidden=true)
    private int version;

    @Column(name = "user_name", nullable = false)
    @Property(title="Username", propertyValidator=@PropertyValidator(
        validationType=ValidationType.LENGTH, minLength=3, maxLength=32, regex="[^,]+", tooltip="username.tooltip"))
    private String userName;

    @Column(name = "email_address", nullable = false)
    @Property(title="Email address")
    private String emailAddress;

    @Column(name = "user_password", nullable = false)
    @Property(title="Password", visible=false, propertyValidator=@PropertyValidator(
        validationType=ValidationType.LENGTH, minLength=6, maxLength=32, tooltip="password.tooltip"))
    private String userPassword;

    @Column(name = "system_password")
    @Property(title="System password", visible=false, hidden=true)
    private String systemPassword;

    @Column(name = "system_password_validity")
    @Temporal(TemporalType.TIMESTAMP)
    @Property(title="System password validity", visible=false, hidden=true)
    private Date systemPasswordValidity;

//    @Transient
//    @Property(title="Person")
//    private String personName;

    @JoinColumn(name = "person_id", referencedColumnName = "partner_id")
    @ManyToOne
    @Property(title="Person", customDisplay="${person.displayName}")
    private Person person;

    @Transient
    @Property(title="Branch")
    private String branchName;

    /**
     * The property is used to temporarily store activeness of a user
     * for the current organization
     */
    @Transient
    @Property(title="Active")
    private boolean active;

    @Column(name = "is_new", nullable = false)
    private boolean isNew;

    @Column(name = "creation_time", nullable = false)
    @Property(title="Creation time")
    @Temporal(TemporalType.TIME)
    private Date creationTime;

    @Column(name = "description")
    @Property(title="Description", visible=false, hidden=true)
    private String description;

    @Column(name = "small_image_uri")
    private String smallImageUri;

    @Lob
    @Column(name = "small_image")
    private byte[] smallImage;

    @Column(name = "medium_image_uri")
    private String mediumImageUri;

    @Lob
    @Column(name = "medium_image")
    private byte[] mediumImage;

    @Column(name = "user_uri")
    @Property(title="User uri")
    private String userUri;

    @Column(name = "next_action_after_login")
    @Property(title="Next action after login")
    private String nextActionAfterLogin;

    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    @ManyToOne
    @Property(title="Creator", customDisplay="${creator.userName}")
    private User creator;

    @JoinColumn(name = "user_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public User() {
    }

    public User(BigInteger userId) {
        this.userId = userId;
    }

    public User(BigInteger userId, int version, String userName, String emailAddress,
                String userPassword, boolean active, boolean isNew,
                Date creationTime) {
        this.userId = userId;
        this.version = version;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.userPassword = userPassword;
        this.active = active;
        this.isNew = isNew;
        this.creationTime = creationTime;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmallImageUri() {
        return smallImageUri;
    }

    public void setSmallImageUri(String smallImageUri) {
        this.smallImageUri = smallImageUri;
    }

    public byte[] getSmallUserImage() {
        return smallImage;
    }

    public void setSmallUserImage(byte[] smallImage) {
        this.smallImage = smallImage;
    }

    public String getMediumImageUri() {
        return mediumImageUri;
    }

    public void setMediumImageUri(String mediumImageUri) {
        this.mediumImageUri = mediumImageUri;
    }

    public byte[] getMediumUserImage() {
        return mediumImage;
    }

    public void setMediumUserImage(byte[] mediumImage) {
        this.mediumImage = mediumImage;
    }

    public String getUserUri() {
        return userUri;
    }

    public void setUserUri(String userUri) {
        this.userUri = userUri;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) ||
            (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.User[userId=" + userId + ", username=" + userName + ", email=" + emailAddress + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return userId;
    }

    @Override
    public BigInteger getParentId() {
       return null;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public void setId(BigInteger id) {
        this.userId = id;

    }

    @Override
    public void setParentId(BigInteger parentId) {
        //
    }

    @Override
    public String getInfo() {
        return getUserName();
    }
}
