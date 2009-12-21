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
import com.cosmos.acacia.crm.enums.Currency;
import javax.persistence.Basic;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "banknote_quantity", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"cash_reconcile_id", "currency_nominal_id"})
})
@NamedQueries({
    @NamedQuery(
        name = BanknoteQuantity.NQ_FIND_ALL,
        query = "SELECT t FROM BanknoteQuantity t" +
                " where" +
                "  t.cashReconcile = :cashReconcile " +
                " order by t.currencyNominal.currency, t.currencyNominal.nominalValue"
    )
})
public class BanknoteQuantity extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "BanknoteQuantity";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    //
    @Id
    @Basic(optional = false)
    @Type(type = "uuid")
    @Column(name = "banknote_quantity_id", nullable = false)
    private UUID banknoteQuantityId;

    @JoinColumn(name = "cash_reconcile_id", referencedColumnName = "cash_reconcile_id", nullable = false)
    @ManyToOne(optional = false)
    private CashReconcile cashReconcile;

    @JoinColumn(name = "currency_nominal_id", referencedColumnName = "currency_nominal_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title = "Currency Nominal", propertyValidator = @PropertyValidator(required = true,
    validationType = ValidationType.NUMBER_RANGE, minValue = 0d, maxValue = 1000000000000d), editable = false, customDisplay = "${currencyNominal.nominal}")
    private CurrencyNominal currencyNominal;

    @Basic(optional = false)
    @Column(name = "quantity", nullable = false, precision = 19, scale = 4)
    @Property(title = "Quantity", propertyValidator = @PropertyValidator(required = true,
    validationType = ValidationType.NUMBER_RANGE, minValue = 1d, maxValue = 1000000000000d))
    private int quantity;

    @JoinColumn(name = "banknote_quantity_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public UUID getBanknoteQuantityId() {
        return banknoteQuantityId;
    }

    public void setBanknoteQuantityId(UUID banknoteQuantityId) {
        this.banknoteQuantityId = banknoteQuantityId;
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
        return getBanknoteQuantityId();
    }

    @Override
    public void setId(UUID id) {
        setBanknoteQuantityId(id);
    }

    @Override
    public UUID getParentId() {
        if (cashReconcile != null) {
            return cashReconcile.getDocumentId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        if (cashReconcile != null) {
            sb.append(cashReconcile.getDocumentId());
        }
        sb.append(':');
        if (currencyNominal != null) {
            sb.append(currencyNominal.getCurrencyNominalId());
        }
        sb.append(':');
        sb.append(quantity);

        return sb.toString();
    }

    public CurrencyNominal getCurrencyNominal() {
        return currencyNominal;
    }

    public void setCurrencyNominal(CurrencyNominal currencyNominal) {
        this.currencyNominal = currencyNominal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        CurrencyNominal currNominal;
        if ((currNominal = getCurrencyNominal()) != null && currNominal.getNominalValue() != null) {
            return currNominal.getNominalValue().multiply(BigDecimal.valueOf(getQuantity()));
        }

        return null;
    }

    public BigDecimal getAmount(Currency targetCurrency) {
        BigDecimal valueBase = getAmount();
        if (valueBase != null) {
            return Currency.convertAmount(targetCurrency,
                    (Currency) getCurrencyNominal().getCurrency().getEnumValue(), valueBase);
        }
        return null;
    }
}
