/**
 * 
 */
package com.cosmos.acacia.crm.data.sales;

import com.cosmos.acacia.crm.data.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;

/**
 * Created	:	28.12.2008
 * @author	Petar Milev
 *
 */
@Entity
@Table(name = "pricelists")
@NamedQueries(
    {
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        @NamedQuery
            (
                name = "Pricelist.findForParentAndDeleted",
                query = "select p from Pricelist p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "Pricelist.findById",
                query = "select p from Pricelist p where p.dataObject.dataObjectId = :pricelistId"
            ),
        /**
         * Parameters:
         *  - parentDataObjectId - not null
         *  - deleted - should be Boolean.FALSE
         *  - name - not null
         *  
         */
        @NamedQuery
            (
                name = "Pricelist.findByNameNotDeleted",
                query = "select p from Pricelist p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                "and p.dataObject.deleted = :deleted and p.name like :name"
            ),
        /**
         * Parameters:
         *  - parentDataObjectId - not null, the organization
         */
        @NamedQuery
            (
                name = "Pricelist.findGeneralPricelistForParent",
                query = "select p from Pricelist p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                "and p.dataObject.deleted = false and p.generalPricelist = true"
            )  
    })
public class Pricelist extends DataObjectBean implements Serializable {
    
    @Column(name = "name", nullable = false)
    @Property(title="Pricelist Name", propertyValidator=@PropertyValidator(required=true, validationType=ValidationType.LENGTH, minLength=2, maxLength=50))
    private String name;
    
    @Column(name = "forPeriod" )
    @Property(title="For Period")
    private boolean forPeriod;
    
    @Property(title="Valid From")
    @Column(name = "active_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeFrom;
    
    @Property(title="Valid To")
    @Column(name = "active_to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activeTo;
    
    @Property(title="Min. Turnover", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    @Column(name = "min_turnover", precision=20, scale=4)
    private BigDecimal minTurnover;
    
    @Property(title="Currency", editable=false)
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    private DbResource currency;
    
    @Property(title="Turnover Months", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=1d, maxValue=36d))
    @Column(name = "months")
    private Integer months;
    
    @Column(name = "Active" )
    @Property(title="Active")
    private boolean active;
    
    @Property(title="Def. Discount", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    @Column(name = "default_discount", precision=20, scale=4)
    private BigDecimal defaultDiscount;
    
    @Property(title="General Pricelist")
    private boolean generalPricelist;
    
    @Id
    @Column(name = "pricelist_id", nullable = false)
    private BigInteger pricelistId;

    @Column(name = "parent_id")
    private BigInteger parentId;

    @JoinColumn(name = "pricelist_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
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
        hash += (pricelistId != null ? pricelistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pricelist)) {
            return false;
        }
        Pricelist other = (Pricelist) object;
        if ((this.pricelistId == null && other.pricelistId != null) || (this.pricelistId != null && !this.pricelistId.equals(other.pricelistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Pricelist[pricelistId=" + pricelistId + "]";
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public BigInteger getId() {
        return pricelistId;
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
        setPricelistId(id);
    }

    public BigInteger getPricelistId() {
        return pricelistId;
    }

    public void setPricelistId(BigInteger pricelistId) {
        this.pricelistId = pricelistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isForPeriod() {
        return forPeriod;
    }

    public void setForPeriod(boolean forPeriod) {
        this.forPeriod = forPeriod;
    }

    public Date getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(Date activeTo) {
        this.activeTo = activeTo;
    }

    public Date getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(Date activeFrom) {
        this.activeFrom = activeFrom;
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        this.currency = currency;
    }

    public BigDecimal getMinTurnover() {
        return minTurnover;
    }

    public void setMinTurnover(BigDecimal minTurnover) {
        this.minTurnover = minTurnover;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public BigDecimal getDefaultDiscount() {
        return defaultDiscount;
    }

    public void setDefaultDiscount(BigDecimal defaultDiscount) {
        this.defaultDiscount = defaultDiscount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isGeneralPricelist() {
        return generalPricelist;
    }

    public void setGeneralPricelist(boolean generalPricelist) {
        this.generalPricelist = generalPricelist;
    }
}
