package com.cosmos.acacia.crm.data.users;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "registration_codes", catalog = "acacia", schema = "public"
/*CREATE UNIQUE INDEX uix_registration_codes_email_address
  ON registration_codes
  USING btree
  (lower(email_address::text));*/
)
@NamedQueries({
    @NamedQuery(
        name = RegistrationCode.NQ_FIND_BY_CODE,
        query = "select t from RegistrationCode t where t.registrationCode = :code"
    ),
    @NamedQuery(
        name = RegistrationCode.NQ_FIND_BY_EMAIL,
        query = "select t from RegistrationCode t where t.emailAddress = :emailAddress"
    )
})
public class RegistrationCode implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "RegistrationCode";
    public static final String NQ_FIND_BY_CODE = CLASS_NAME + ".findByCode";
    public static final String NQ_FIND_BY_EMAIL = CLASS_NAME + ".findByEmail";

    @Id
    @Basic(optional = false)
    @Column(name = "registration_code", nullable=false)
    @Type(type="uuid")
    private UUID registrationCode;

    @Basic(optional = false)
    @Column(name = "email_address", nullable = false, length = 64)
    private String emailAddress;

    @Basic(optional = false)
    @Column(name = "registration_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTime = new Date();

    public RegistrationCode() {
    }

    public RegistrationCode(UUID registrationCode) {
        this.registrationCode = registrationCode;
    }

    public UUID getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(UUID registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RegistrationCode other = (RegistrationCode) obj;
        if (this.registrationCode != other.registrationCode && (this.registrationCode == null || !this.registrationCode.equals(other.registrationCode))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if(registrationCode != null) {
            return registrationCode.hashCode();
        }

        return 0;
    }

    @Override
    public String toString() {
        return CLASS_NAME + "[" + registrationCode + "; " + emailAddress + "; " + registrationTime + "]";
    }
}
