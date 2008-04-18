/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "assembling_algorithms")
@NamedQueries({})
public class AssemblingAlgorithm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "algorithm_id", nullable = false)
    private Integer algorithmId;
    @Column(name = "algorithm_code", nullable = false)
    private String algorithmCode;
    @Column(name = "algorithm_name", nullable = false)
    private String algorithmName;
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "algorithmId")
    private Collection<AssemblingSchemaItem> assemblingSchemaItemCollection;

    public AssemblingAlgorithm() {
    }

    public AssemblingAlgorithm(Integer algorithmId) {
        this.algorithmId = algorithmId;
    }

    public AssemblingAlgorithm(Integer algorithmId, String algorithmCode, String algorithmName) {
        this.algorithmId = algorithmId;
        this.algorithmCode = algorithmCode;
        this.algorithmName = algorithmName;
    }

    public Integer getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(Integer algorithmId) {
        this.algorithmId = algorithmId;
    }

    public String getAlgorithmCode() {
        return algorithmCode;
    }

    public void setAlgorithmCode(String algorithmCode) {
        this.algorithmCode = algorithmCode;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<AssemblingSchemaItem> getAssemblingSchemaItemCollection() {
        return assemblingSchemaItemCollection;
    }

    public void setAssemblingSchemaItemCollection(Collection<AssemblingSchemaItem> assemblingSchemaItemCollection) {
        this.assemblingSchemaItemCollection = assemblingSchemaItemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (algorithmId != null ? algorithmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssemblingAlgorithm)) {
            return false;
        }
        AssemblingAlgorithm other = (AssemblingAlgorithm) object;
        if ((this.algorithmId == null && other.algorithmId != null) || (this.algorithmId != null && !this.algorithmId.equals(other.algorithmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.test.AssemblingAlgorithm[algorithmId=" + algorithmId + "]";
    }

}
