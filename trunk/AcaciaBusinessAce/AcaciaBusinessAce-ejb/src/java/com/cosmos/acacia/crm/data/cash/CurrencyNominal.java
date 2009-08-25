package com.cosmos.acacia.crm.data.cash;

import com.cosmos.acacia.crm.data.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.resource.TextResource;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "currency_nominal")
@NamedQueries({
    /**
     * Parameters:
     * - currency
     * - nominal
     */
    @NamedQuery(
        name = CurrencyNominal.NQ_FIND_BY_CUR,
        query = "SELECT e FROM CurrencyNominal e" +
            " where" +
            " e.dataObject.deleted = false" +
            " and e.currency = :currency" ),
    /**
     * Parameters:
     * - currency
     */
    @NamedQuery(
        name = CurrencyNominal.NQ_FIND_NOMINAL_VALUES,
        query = "SELECT e FROM CurrencyNominal e" +
            " where" +
            " e.currency = :currency" +
            " order by e.nominal "),
    /**
     * Parameters:
     * - currency
     * - nominal
     */
    @NamedQuery(
        name = CurrencyNominal.NQ_FIND_BY_NOM_AND_CURR,
        query = "SELECT e FROM CurrencyNominal e" +
            " where" +
            " e.dataObject.deleted = false" +
            " and e.dataObject.parentDataObjectId is null " +
            " and e.currency = :currency" +
            " and e.nominal = :nominal ")
})
public class CurrencyNominal extends DataObjectBean implements Serializable, TextResource {
    public static final String NQ_FIND_BY_CUR = "CurrencyNominal.findByCurrency";

    public static final String NQ_FIND_NOMINAL_VALUES = "CurrencyNominal.findNominalValues";

    public static final String NQ_FIND_BY_NOM_AND_CURR = "NQ_FIND_BY_NOM_AND_CURR";

    @Id
    @Column(name = "nominal_id", nullable = false)
    @Type(type="uuid")
    private UUID nominalId;
    
    @Property(title="Nominal", propertyValidator=@PropertyValidator(required=true,
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "nominal", nullable = false, precision=20, scale=2)
    private BigDecimal nominal;
    
    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;
    
    @JoinColumn(name = "nominal_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;
    
    @Transient
    private UUID parentId;
    
    public CurrencyNominal(BigDecimal nominal, DbResource currency) {
        super();
        this.nominal = nominal;
        this.currency = currency;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nominalId != null ? nominalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurrencyNominal)) {
            return false;
        }
        CurrencyNominal other = (CurrencyNominal) object;
        if ((this.nominalId == null && other.nominalId != null) || (this.nominalId != null && !this.nominalId.equals(other.nominalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.CurrencyNominal[nominalId=" + nominalId + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public UUID getId() {
        return nominalId;
    }

    @Override
    public String getInfo() {
        return toString();
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public void setId(UUID id) {
        setNominalId(id);
    }

    public UUID getNominalId() {
        return nominalId;
    }

    public void setNominalId(UUID nominalId) {
        this.nominalId = nominalId;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public CurrencyNominal() {
        super();
    }

    @Override
    public String toShortText() {
        if ( nominal!=null )
            return AcaciaUtils.getDecimalFormat().format(nominal);
        return "";
    }

    @Override
    public String toText() {
        if ( nominal!=null )
            return AcaciaUtils.getDecimalFormat().format(nominal);
        return "";
    }
}
