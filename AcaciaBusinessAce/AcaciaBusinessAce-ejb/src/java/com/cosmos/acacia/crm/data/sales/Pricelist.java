/**
 * 
 */
package com.cosmos.acacia.crm.data.sales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import javax.persistence.Basic;
import org.hibernate.annotations.Type;

/**
 * Created	:	28.12.2008
 * @author	Petar Milev
 *
 */
@Entity
@Table(name = "pricelists", catalog = "acacia", schema = "public")
@NamedQueries(
    {
        /**
         * Parameters: 
         *  - parentDataObjectId - not null, the parent object id
         *  - deleted - not null - true/false
         */
        /*@NamedQuery
            (
                name = "Pricelist.findForParentAndDeleted",
                query = "select p from Pricelist p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                        "and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "Pricelist.findById",
                query = "select p from Pricelist p where p.dataObject.dataObjectId = :pricelistId"
            ),*/
        /**
         * Parameters:
         *  - parentDataObjectId - not null
         *  - deleted - should be Boolean.FALSE
         *  - name - not null
         *  
         */
        /*@NamedQuery
            (
                name = "Pricelist.findByNameNotDeleted",
                query = "select p from Pricelist p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                "and p.dataObject.deleted = :deleted and p.name like :name"
            ),*/
        /**
         * Parameters:
         *  - parentDataObjectId - not null, the organization
         */
        /*@NamedQuery
            (
                name = "Pricelist.findGeneralPricelistForParent",
                query = "select p from Pricelist p where p.dataObject.parentDataObjectId = :parentDataObjectId " +
                "and p.dataObject.deleted = false and p.generalPricelist = true"
            )  */
    })
public class Pricelist extends DataObjectBean implements Serializable {
    
    @Id
    @Basic(optional = false)
    @Type(type="uuid")
    @Column(name = "pricelist_id", nullable = false)
    private UUID pricelistId;

    @Basic(optional = false)
    @Column(name = "pricelist_name", nullable = false, length = 128)
    @Property(title="Pricelist Name",
        propertyValidator=@PropertyValidator(required=true, validationType=ValidationType.LENGTH, minLength=2, maxLength=128)
    )
    private String pricelistName;
    
    @Column(name = "active")
    @Property(title="Active")
    private boolean active;

    @Column(name = "for_period")
    @Property(title="For Period")
    private boolean forPeriod;
    
    @Column(name = "valid_from")
    @Temporal(TemporalType.TIMESTAMP)
    @Property(title="Valid From")
    private Date activeFrom;
    
    @Column(name = "valid_to")
    @Temporal(TemporalType.TIMESTAMP)
    @Property(title="Valid To")
    private Date activeTo;
    
    @Column(name = "min_turnover", precision = 19, scale = 4)
    @Property(title="Min. Turnover", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=1000000000000d))
    private BigDecimal minTurnover;
    
    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Currency", editable=false)
    private DbResource currency;
    
    @Column(name = "turnover_months")
    @Property(title="Turnover Months", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=1d, maxValue=36d))
    private Integer turnoverMonths;
    
    @Column(name = "default_discount", precision = 19, scale = 4)
    @Property(title="Def. Discount", propertyValidator=@PropertyValidator(
        validationType=ValidationType.NUMBER_RANGE, minValue=0d, maxValue=100d))
    private BigDecimal defaultDiscount;
    
    @Column(name = "general_pricelist")
    @Property(title="General Pricelist")
    private boolean generalPricelist;
    
    @Type(type="uuid")
    @Column(name = "parent_id")
    private UUID parentId;

    @JoinColumn(name = "pricelist_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public Pricelist() {
    }

    public Pricelist(UUID pricelistId) {
        this.pricelistId = pricelistId;
    }

    @Override
    public UUID getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(UUID parentId) {
        this.parentId = parentId;
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
        return pricelistId;
    }

    @Override
    public void setId(UUID id) {
        setPricelistId(id);
    }

    public UUID getPricelistId() {
        return pricelistId;
    }

    public void setPricelistId(UUID pricelistId) {
        this.pricelistId = pricelistId;
    }

    public String getPricelistName() {
        return pricelistName;
    }

    public void setPricelistName(String pricelistName) {
        this.pricelistName = pricelistName;
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

    public Integer getTurnoverMonths() {
        return turnoverMonths;
    }

    public void setTurnoverMonths(Integer months) {
        this.turnoverMonths = months;
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

    @Override
    public String getInfo() {
        return toString();
    }
}
