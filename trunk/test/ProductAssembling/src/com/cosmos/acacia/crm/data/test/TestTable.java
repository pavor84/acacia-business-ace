/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.test;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "test_table")
@NamedQueries({})
public class TestTable
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name="TestTableSequenceGenerator",
        sequenceName="test_table_seq",
        allocationSize=1)
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="TestTableSequenceGenerator")
    @Column(name = "test_id", nullable = false)
    private Integer testId;

    //@Lob
    @Column(name = "data_column")
    private Serializable dataColumn;

    @Column(name = "data_type")
    private String dataType;

    public TestTable() {
    }

    public TestTable(Integer testId) {
        this.testId = testId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Object getDataColumn() {
        return dataColumn;
    }

    public void setDataColumn(Serializable dataColumn) {
        this.dataColumn = dataColumn;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
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
        if (!(object instanceof TestTable)) {
            return false;
        }
        TestTable other = (TestTable) object;
        if ((this.testId == null && other.testId != null) || (this.testId != null && !this.testId.equals(other.testId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.TestTable[testId=" + testId + "]";
    }

}
