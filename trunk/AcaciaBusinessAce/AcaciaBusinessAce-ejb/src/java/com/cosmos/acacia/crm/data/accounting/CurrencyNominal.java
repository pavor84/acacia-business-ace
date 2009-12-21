package com.cosmos.acacia.crm.data.accounting;

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

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.resource.TextResource;
import javax.persistence.Basic;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "currency_nominal", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"currency_id", "nominal_value"})
})
@NamedQueries({
    @NamedQuery(
        name = CurrencyNominal.NQ_FIND_BY_CURRENCY,
        query = "SELECT t FROM CurrencyNominal t" +
                " where" +
                "  t.currency = :currency" +
                "  and t.dataObject.deleted = false" +
                " order by t.nominalValue"
    ),
    @NamedQuery(
        name = CurrencyNominal.NQ_FIND_BY_CURRENCY_AND_NOMINAL_VALUE,
        query = "SELECT t FROM CurrencyNominal t" +
            " where" +
            "  t.currency = :currency" +
            "  and t.nominalValue = :nominalValue"
    )
})
public class CurrencyNominal extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "CurrencyNominal";
    public static final String NQ_FIND_BY_CURRENCY = CLASS_NAME + ".findByCurrency";
    public static final String NQ_FIND_BY_CURRENCY_AND_NOMINAL_VALUE =
            CLASS_NAME + ".findByCurrencyAndNominalValue";

    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "currency_nominal_id", nullable = false)
    private UUID currencyNominalId;
    
    @Basic(optional = false)
    @Column(name = "nominal_value", nullable = false, precision = 19, scale = 4)
    @Property(title="Nominal", propertyValidator=@PropertyValidator(required=true,
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal nominalValue;
    
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    private DbResource currency;
    
    @JoinColumn(name = "currency_nominal_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;
    
    public CurrencyNominal() {
    }

    public CurrencyNominal(UUID currencyNominalId) {
        this.currencyNominalId = currencyNominalId;
    }

    public CurrencyNominal(BigDecimal nominalValue, DbResource currency) {
        this.nominalValue = nominalValue;
        this.currency = currency;
    }

    public UUID getCurrencyNominalId() {
        return currencyNominalId;
    }

    public void setCurrencyNominalId(UUID currencyNominalId) {
        this.currencyNominalId = currencyNominalId;
    }

    public BigDecimal getNominalValue() {
        return nominalValue;
    }

    public void setNominalValue(BigDecimal nominalValue) {
        this.nominalValue = nominalValue;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public UUID getId() {
        return getCurrencyNominalId();
    }

    @Override
    public void setId(UUID id) {
        setCurrencyNominalId(id);
    }

    @Override
    public UUID getParentId() {
        return null;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        if(currency != null) {
            sb.append(currency.getEnumValue().name());
        }
        sb.append(':');
        if(nominalValue != null) {
            sb.append(nominalValue);
        }

        return sb.toString();
    }

    @Override
    public String toShortText() {
        return getInfo();
    }

    @Override
    public String toText() {
        return getInfo();
    }
}
