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
                    name = "User.findCreatedUsers",
                    query = "select u from User u where u.creator=:creator"
            ),
            @NamedQuery(
                    name = "User.findByEmail",
                    query = "select u from User u where u.emailAddress=:email"
            ),
            @NamedQuery(
                    name = "User.findByUsername",
                    query = "select u from User u where u.userName=:username"
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
    @Property(title="Username")
    private String userName;

    @Column(name = "email_address", nullable = false)
    @Property(title="Email address")
    private String emailAddress;

    @Column(name = "user_password", nullable = false)
    @Property(title="Password", visible=false, hidden=true)
    private String userPassword;

    @Column(name = "system_password")
    @Property(title="System password", visible=false, hidden=true)
    private String systemPassword;

    @Column(name = "system_password_validity")
    @Temporal(TemporalType.DATE)
    @Property(title="System password validity", visible=false, hidden=true)
    private Date systemPasswordValidity;

    @Column(name = "is_active", nullable = false)
    @Property(title="Active")
    private boolean isActive;

    @Column(name = "is_new", nullable = false)
    @Property(title="New")
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
    private String userUri;

    @Column(name = "next_action_after_login")
    private String nextActionAfterLogin;

    @JoinColumn(name = "person_id", referencedColumnName = "partner_id")
    @ManyToOne
    private Person person;

    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    @ManyToOne
    private User creator;

    @JoinColumn(name = "user_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    /**
     * Used to store the chosen organization during registration; persisted manually
     */
    private Organization organization;

    /**
     * Used to store the chosen branch during registration; persisted manually
     */
    private Address branch;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Address getBranch() {
        return branch;
    }

    public void setBranch(Address branch) {
        this.branch = branch;
    }

    public User() {
    }

    public User(BigInteger userId) {
        this.userId = userId;
    }

    public User(BigInteger userId, int version, String userName, String emailAddress,
                String userPassword, boolean isActive, boolean isNew,
                Date creationTime) {
        this.userId = userId;
        this.version = version;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.userPassword = userPassword;
        this.isActive = isActive;
        this.isNew = isNew;
        this.creationTime = creationTime;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
        return "com.cosmos.acacia.crm.data.User[userId=" + userId + "]";
    }

    @Override
    public DataObject getDataObject() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BigInteger getId() {
        return userId;
    }

    @Override
    public BigInteger getParentId() {
       return dataObject.getDataObjectId();
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
}
