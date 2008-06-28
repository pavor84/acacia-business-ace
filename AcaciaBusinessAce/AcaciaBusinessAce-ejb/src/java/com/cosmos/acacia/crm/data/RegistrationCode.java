package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "registration_codes")
@NamedQueries( {
    @NamedQuery (
            name = "RegistrationCode.findByCode",
            query = "select rc from RegistrationCode rc where rc.registrationCode=:code"
    )
})
public class RegistrationCode implements Serializable {

    private static final long serialVersionUID = -8941834239569076907L;

    @Id
    @Column(name = "registration_code", nullable=false)
    private BigInteger registrationCode;

    @Column(name = "email")
    private String email;

    public BigInteger getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(BigInteger registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
