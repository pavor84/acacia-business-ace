package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.crm.data.*;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.resource.TextResource;

import java.io.Serializable;
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

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "bank_details")
@NamedQueries(
    {
          @NamedQuery
             (
                name = "BankDetail.findByParentDataObjectAndDeleted",
                query = "select bd from BankDetail bd where bd.dataObject.parentDataObjectId = :parentDataObjectId and bd.dataObject.deleted = :deleted"
             ),
        @NamedQuery
             (
                name = "BankDetail.findByParentDataObjectIsNullAndDeleted",
                query = "select bd from BankDetail bd where bd.dataObject.parentDataObjectId is null and bd.dataObject.deleted = :deleted"
              )
    }
)
public class BankDetail
        extends DataObjectBean
        implements Serializable, TextResource
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "bank_detail_id", nullable = false)
    @Property(title="Bank Detail ID", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger bankDetailId;

    @Column(name = "bank_account")
    @Property(title="Bank Account", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=22))
    private String bankAccount;

    @Column(name = "iban")
    @Property(title="IBAN",propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=22))
    private String iban;

    @Column(name = "bic")
    @Property(title="BIC", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=12))
    private String bic;

    @Column(name = "swift_code")
    @Property(title="SWIFT code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=50))
    private String swiftCode;

    @JoinColumn(name = "currency_id", referencedColumnName = "resource_id")
    @ManyToOne
    @Property(title="Currency")
    private DbResource currency;

    @JoinColumn(name = "bank_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne
    @Property(title="Bank", customDisplay="${bank.organizationName}")
    private Organization bank;

    @JoinColumn(name = "bank_branch_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne
    @Property(title="Bank Branch", customDisplay="${bankBranch.addressName}")
    private Address bankBranch;

    @JoinColumn(name = "bank_contact_id")
    @ManyToOne
    @Property(title="Contact",
        customDisplay="${bankContact.firstName} ${bankContact.secondName} ${bankContact.lastName} ${bankContact.extraName}")
    private Person bankContact;

    @Column(name = "is_default", nullable = false)
    @Property(title="Default")
    private boolean isDefault;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @JoinColumn(name = "bank_detail_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public BankDetail() {
    }

    public BankDetail(BigInteger bankDetailId) {
        this.bankDetailId = bankDetailId;
    }

    public BankDetail(BigInteger bankDetailId, boolean isDefault, String bankAccount) {
        this.bankDetailId = bankDetailId;
        this.isDefault = isDefault;
        this.bankAccount = bankAccount;
    }

    public BigInteger getBankDetailId() {
        return bankDetailId;
    }

    public void setBankDetailId(BigInteger bankDetailId) {
        this.bankDetailId = bankDetailId;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        firePropertyChange("isDefault", this.isDefault, isDefault);
        this.isDefault = isDefault;
    }

    public Organization getBank() {
        return bank;
    }

    public void setBank(Organization bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Address getBankBranchId() {
        return bankBranch;
    }

    public void setBankBranchId(Address bankBranchId) {
        this.bankBranch = bankBranchId;
    }

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
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

    public Person getBankContactId() {
        return bankContact;
    }

    public void setBankContactId(Person bankContactId) {
        this.bankContact = bankContactId;
    }

    public Address getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(Address bankBranch) {
        this.bankBranch = bankBranch;
    }

    public Person getBankContact() {
        return bankContact;
    }

    public void setBankContact(Person bankContact) {
        this.bankContact = bankContact;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankDetailId != null ? bankDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BankDetail)) {
            return false;
        }
        BankDetail other = (BankDetail) object;
        if ((this.bankDetailId == null && other.bankDetailId != null) || (this.bankDetailId != null && !this.bankDetailId.equals(other.bankDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.BankDetail[bankDetailId=" + bankDetailId + "]";
    }

    @Override
    public BigInteger getId() {
        return getBankDetailId();
    }

    @Override
    public void setId(BigInteger id) {
        setBankDetailId(id);
    }
    
    @Override
    public String getInfo() {
        return getBankAccount();
    }

    @Override
    public String toShortText() {
        if ( getBankAccount()!=null )
            return getBankAccount();
        return getIban();
    }

    @Override
    public String toText() {
        String bank = "";
        if ( getBank()!=null )
            bank = getBank().getDisplayName();
        return bank+" ["+toShortText()+"]";
    }
}
