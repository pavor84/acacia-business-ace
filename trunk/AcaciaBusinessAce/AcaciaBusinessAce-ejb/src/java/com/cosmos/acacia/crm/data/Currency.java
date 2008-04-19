package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.resource.TextResource;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "currencies")
@NamedQueries(
    {
        @NamedQuery(
            name = "Currency.fetchAll",
            query = "select c from Currency c"
        )
    }
)
public class Currency
    implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "currency_id", nullable = false)
    @Property(title="Currency Id", editable=false, readOnly=true, visible=false, hidden=true)
    private Integer currencyId;

    @Column(name = "currency_name", nullable = false)
    @Property(title="Name")
    private String currencyName;

    @Column(name = "currency_code_a3")
    @Property(title="Currency Code A3")
    private String currencyCodeA3;

    @Column(name = "currency_code_n3")
    @Property(title="Currency Code N3")
    private String currencyCodeN3;

    @Column(name = "description")
    @Property(title="Description")
    private String description;


    public Currency() {
    }

    public Currency(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Currency(Integer currencyId, String currencyName) {
        this.currencyId = currencyId;
        this.currencyName = currencyName;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCodeA3() {
        return currencyCodeA3;
    }

    public void setCurrencyCodeA3(String currencyCodeA3) {
        this.currencyCodeA3 = currencyCodeA3;
    }

    public String getCurrencyCodeN3() {
        return currencyCodeN3;
    }

    public void setCurrencyCodeN3(String currencyCodeN3) {
        this.currencyCodeN3 = currencyCodeN3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currencyId != null ? currencyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currency)) {
            return false;
        }
        Currency other = (Currency) object;
        if ((this.currencyId == null && other.currencyId != null) || (this.currencyId != null && !this.currencyId.equals(other.currencyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Currency[currencyId=" + currencyId + "]";
    }

    public String toShortText() {
        if (getCurrencyCodeA3() != null)
            return getCurrencyCodeA3();
        
        return getCurrencyCodeN3();
    }

    public String toText() {
        return getCurrencyName();
    }

}
