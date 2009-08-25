/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.test;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
//@TypeDef(name = "uuid", typeClass = UuidUserType.class)
@Table(name = "uuid_test", catalog = "acacia", schema = "public")
@NamedQueries({
    @NamedQuery(name = "UuidTest.findAll", query = "SELECT u FROM UuidTest u"),
    @NamedQuery(name = "UuidTest.findByTestName", query = "SELECT u FROM UuidTest u WHERE u.testName = :testName")
})
public class UuidTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "test_id", nullable = false)
    private UUID testId;

    @Column(name = "test_name", length = 100)
    private String testName;

    public UuidTest() {
        this(UUID.randomUUID());
    }

    public UuidTest(UUID testId) {
        this.testId = testId;
    }

    public UUID getTestId() {
        return testId;
    }

    public void setTestId(UUID testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testId != null ? testId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UuidTest)) {
            return false;
        }
        UuidTest other = (UuidTest) object;
        if ((this.testId == null && other.testId != null) || (this.testId != null && !this.testId.equals(other.testId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UuidTest[testId=" + testId + "]";
    }
}
