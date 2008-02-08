/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "version", nullable = false)
    private int version;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "email_address", nullable = false)
    private String emailAddress;
    @Column(name = "user_password", nullable = false)
    private String userPassword;
    @Column(name = "system_password")
    private String systemPassword;
    @Column(name = "system_password_validity")
    @Temporal(TemporalType.DATE)
    private Date systemPasswordValidity;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @Column(name = "is_new", nullable = false)
    private boolean isNew;
    @Column(name = "creation_time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date creationTime;
    @Column(name = "description")
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
    @JoinColumn(name = "person_id", referencedColumnName = "data_object_id")
    @ManyToOne
    private DataObject personId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creatorId")
    private Collection<User> userCollection;
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    @ManyToOne
    private User creatorId;

    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public User(Long userId, int version, String userName, String emailAddress,
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public byte[] getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(byte[] smallImage) {
        this.smallImage = smallImage;
    }

    public String getMediumImageUri() {
        return mediumImageUri;
    }

    public void setMediumImageUri(String mediumImageUri) {
        this.mediumImageUri = mediumImageUri;
    }

    public byte[] getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(byte[] mediumImage) {
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

    public DataObject getPersonId() {
        return personId;
    }

    public void setPersonId(DataObject personId) {
        this.personId = personId;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public User getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(User creatorId) {
        this.creatorId = creatorId;
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

}
