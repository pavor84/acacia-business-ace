package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

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
import com.cosmos.acacia.crm.enums.Currency;

@Entity
@Table(name = "banknote_quantity")
@NamedQueries({
    /**
     * Parameters:
     * - :parentId
     */
    @NamedQuery(
        name = BanknoteQuantity.NQ_BY_PARENT,
        query = "SELECT e FROM BanknoteQuantity e" +
            " where" +
            " e.dataObject.parentDataObjectId = :parentId " +
            " and e.dataObject.deleted = false " +
            " order by e.currencyNominal.currency, e.currencyNominal.nominal"),
    /**
     * Parameters:
     * - parentId
     * - currencyNominal
     */
    @NamedQuery(
        name = BanknoteQuantity.NQ_BY_CURR_NOMINAL,
        query = "SELECT e FROM BanknoteQuantity e" +
            " where" +
            " e.dataObject.parentDataObjectId = :parentId " +
            " and e.dataObject.deleted = false " +
            " and e.currencyNominal = :currencyNominal")
})
public class BanknoteQuantity extends DataObjectBean implements Serializable {
    public static final String NQ_BY_PARENT = "BanknoteQuantity.findForParent";

    public static final String NQ_BY_CURR_NOMINAL = "NQ_BY_CURR_NOMINAL";

    @Id
    @Column(name = "banknote_amt_id", nullable = false)
    private BigInteger banknoteAmountId;
    
    @ManyToOne
    @Property(title="Currency Nominal", propertyValidator=@PropertyValidator(required=true,
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d), editable=false, customDisplay="${currencyNominal.nominal}")
    private CurrencyNominal currencyNominal;
    
    @Property(title="Quantity", propertyValidator=@PropertyValidator(required=true,
        validationType=ValidationType.NUMBER_RANGE, minValue=1d, maxValue=1000000000000d))
    @Column(name = "quantity", nullable = false)
    private BigInteger quantity;
    
    @Column(name = "parent_id")
    private BigInteger parentId;
    
    @JoinColumn(name = "banknote_amt_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;
    
    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (banknoteAmountId != null ? banknoteAmountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BanknoteQuantity)) {
            return false;
        }
        BanknoteQuantity other = (BanknoteQuantity) object;
        if ((this.banknoteAmountId == null && other.banknoteAmountId != null) || (this.banknoteAmountId != null && !this.banknoteAmountId.equals(other.banknoteAmountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.BanknoteAmount[banknoteAmountId=" + banknoteAmountId + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return banknoteAmountId;
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
    public void setId(BigInteger id) {
        setBanknoteAmountId(id);
    }

    public BigInteger getBanknoteAmountId() {
        return banknoteAmountId;
    }

    public void setBanknoteAmountId(BigInteger banknoteAmountId) {
        this.banknoteAmountId = banknoteAmountId;
    }

    public CurrencyNominal getCurrencyNominal() {
        return currencyNominal;
    }

    public void setCurrencyNominal(CurrencyNominal currencyNominal) {
        this.currencyNominal = currencyNominal;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        if ( getCurrencyNominal()!=null && getCurrencyNominal().getNominal()!=null && getQuantity()!=null )
            return getCurrencyNominal().getNominal().multiply(new BigDecimal(getQuantity()));
        return null;
    }
    
    public BigDecimal getAmount(Currency targetCurrency) {
        BigDecimal valueBase = getAmount();
        if ( valueBase!=null )
            return Currency.convertAmount(targetCurrency, 
                (Currency)getCurrencyNominal().getCurrency().getEnumValue(), valueBase);
        return null;
    }
}
