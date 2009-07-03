/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Comparator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Miro
 */
@Embeddable
public class ExpressionPK implements Serializable, Comparable<ExpressionPK>, Comparator<ExpressionPK> {

    @Basic(optional = false)
    @Column(name = "organization_id", nullable = false, precision = 18, scale = 0)
    private BigInteger organizationId;

    @Basic(optional = false)
    @Column(name = "expression_key", nullable = false, length = 255)
    private String expressionKey;

    public ExpressionPK() {
    }

    public ExpressionPK(BigInteger organizationId, String expressionKey) {
        this.organizationId = organizationId;
        this.expressionKey = expressionKey;
    }

    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(BigInteger organizationId) {
        this.organizationId = organizationId;
    }

    public String getExpressionKey() {
        return expressionKey;
    }

    public void setExpressionKey(String expressionKey) {
        this.expressionKey = expressionKey;
    }

    @Override
    public int hashCode() {
        int hash;
        if (organizationId != null) {
            hash = organizationId.hashCode();
        } else {
            hash = 0;
        }
        hash += (expressionKey != null ? expressionKey.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExpressionPK)) {
            return false;
        }

        ExpressionPK other = (ExpressionPK) object;
        if ((organizationId == null && other.organizationId != null) || (organizationId != null && !organizationId.equals(other.organizationId))) {
            return false;
        }
        if ((expressionKey == null && other.expressionKey != null) || (expressionKey != null && !expressionKey.equals(other.expressionKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExpressionPK[organizationId=" + organizationId + ", expressionKey=" + expressionKey + "]";
    }

    @Override
    public int compareTo(ExpressionPK other) {
        return compare(this, other);
    }

    @Override
    public int compare(ExpressionPK pk1, ExpressionPK pk2) {
        int result;
        if ((result = pk1.organizationId.compareTo(pk2.organizationId)) != 0) {
            return result;
        }
        return pk1.expressionKey.compareTo(pk2.expressionKey);
    }
}
