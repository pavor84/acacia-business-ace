/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 *
 * @author Miro
 */
@Entity
@Table(name = "expressions", catalog = "acacia", schema = "public")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
    @NamedQuery(
        name = "Expression.findAll",
        query = "SELECT t1 FROM Expression t1",
        hints={
            @QueryHint(name="org.hibernate.cacheable", value="true")
        }
    ),
    @NamedQuery(
        name = "Expression.findByExpressionKey",
        query =
            "SELECT t1 FROM Expression t1" +
            " where" +
            "  t1.expressionPK.organizationId = :organizationId" +
            "  and t1.expressionPK.expressionKey = :expressionKey",
        hints={
            @QueryHint(name="org.hibernate.cacheable", value="true")
        }
    )
})
public class Expression implements Serializable, Comparable<Expression>, Comparator<Expression> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected ExpressionPK expressionPK;

    @Column(name = "expression_value", length = 1024)
    private String expressionValue;

    public Expression() {
    }

    public Expression(ExpressionPK expressionPK) {
        this.expressionPK = expressionPK;
    }

    public Expression(BigInteger organizationId, String expressionKey) {
        this.expressionPK = new ExpressionPK(organizationId, expressionKey);
    }

    public ExpressionPK getExpressionPK() {
        return expressionPK;
    }

    public void setExpressionPK(ExpressionPK expressionPK) {
        this.expressionPK = expressionPK;
    }

    public String getExpressionValue() {
        return expressionValue;
    }

    public void setExpressionValue(String expressionValue) {
        this.expressionValue = expressionValue;
    }

    @Override
    public int hashCode() {
        if(expressionPK != null) {
            return expressionPK.hashCode();
        }

        return 0;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expression)) {
            return false;
        }

        Expression other = (Expression) object;
        if ((expressionPK == null && other.expressionPK != null) || (expressionPK != null && !expressionPK.equals(other.expressionPK))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Expression[pk=" + expressionPK + "]; value=" + expressionValue;
    }

    @Override
    public int compareTo(Expression expression) {
        return compare(this, expression);
    }

    @Override
    public int compare(Expression e1, Expression e2) {
        return e1.expressionPK.compareTo(e2.expressionPK);
    }
}
