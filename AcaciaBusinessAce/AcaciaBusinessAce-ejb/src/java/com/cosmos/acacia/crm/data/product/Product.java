/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;


/**
 *
 * @author miro
 */
@Entity
@Table(name = "products", catalog = "acacia", schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"parent_id", "product_code"}),
        @UniqueConstraint(columnNames = {"parent_id", "product_name"})
    }
)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, length=2, name="product_type")
@NamedQueries(
    {
        @NamedQuery
            (
                name = "Product.findByParentDataObjectIsNullAndDeleted",
                query = "select p from Product p" +
                        " where p.dataObject.parentDataObjectId is NULL" +
                        " and p.dataObject.deleted = :deleted"
            ),
        @NamedQuery
            (
                name = "Product.findByParentDataObjectAndDeleted",
                query = "select p from Product p" +
                        " where p.dataObject.parentDataObjectId = :parentDataObjectId" +
                        " and p.dataObject.deleted = :deleted"
            )
    })
public abstract class Product extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String DISCRIMINATOR_COMPLEX_PRODUCT = "C";
    public static final String DISCRIMINATOR_SIMPLE_PRODUCT = "S";

    @Id
    @Basic(optional = false)
    @Column(name = "product_id", nullable = false)
    @Type(type="uuid")
    @Property(title="Product Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID productId;

    @Basic(optional = false)
    @Column(name = "parent_id", nullable = false)
    @Type(type="uuid")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID parentId;

    @Basic(optional = false)
    @Column(name = "product_type", nullable = false, length = 2)
    private String productType;

    @Basic(optional = false)
    @Column(name = "product_code", nullable = false, length = 50)
    @Property(title="Product Code",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=50, required=true))
    private String productCode;

    @Basic(optional = false)
    @Column(name = "product_name", nullable = false, length = 100)
    @Property(title="Product Name",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2, maxLength=100))
    private String productName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "measure_unit_id", nullable=false, referencedColumnName = "resource_id")
    @Property(title="Measure Unit")
    private DbResource measureUnit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", nullable=false, referencedColumnName = "resource_id")
    @Property(title="Currency", propertyValidator=@PropertyValidator(required=true))
    private DbResource currency;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn()
    private DataObject dataObject;

    public Product() {
    }

    public Product(String productType) {
        this.productType = productType;
    }

    public Product(String productType, UUID productId) {
        this(productType);
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public abstract BigDecimal getSalesPrice();
    public abstract void setSalesPrice(BigDecimal salesPrice);

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        String oldValue = this.productCode;
        this.productCode = productCode;
        firePropertyChange("productCode", oldValue, productCode);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        String oldValue = this.productName;
        this.productName = productName;
        firePropertyChange("productName", oldValue, productName);
    }

    public DbResource getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(DbResource measureUnit) {
        DbResource oldValue = this.measureUnit;
        this.measureUnit = measureUnit;
        firePropertyChange("measureUnit", oldValue, measureUnit);
    }

    public DbResource getCurrency() {
        return currency;
    }

    public void setCurrency(DbResource currency) {
        DbResource oldValue = this.currency;
        this.currency = currency;
        firePropertyChange("currency", oldValue, currency);
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
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
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append("[productId=");
        sb.append(productId);
        sb.append(", parentId=").append(parentId);
        sb.append(", productName=").append(getProductName());
        sb.append(", productCode=").append(getProductCode());
        DataObject dataObj;
        if((dataObj = getDataObject()) != null)
            sb.append(":v.").append(dataObj.getDataObjectVersion());
        return sb.toString();
    }

    // DataObjectBean

    @Override
    public UUID getId() {
        return getProductId();
    }

    @Override
    public void setId(UUID id) {
        setProductId(id);
    }
    
}
