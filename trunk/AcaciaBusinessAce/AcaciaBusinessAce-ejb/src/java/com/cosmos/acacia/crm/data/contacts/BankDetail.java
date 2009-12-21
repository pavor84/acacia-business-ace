package com.cosmos.acacia.crm.data.contacts;

import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.crm.data.*;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.acacia.crm.bl.contacts.ContactsServiceRemote;
import com.cosmos.resource.TextResource;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "bank_details",
    uniqueConstraints={
        @UniqueConstraint(columnNames={"address_id", "currency_id", "bank_id", "bank_branch_id"})
    }
)
@NamedQueries({
    @NamedQuery(
        name = BankDetail.NQ_FIND_ALL,
        query = "select t from BankDetail t" +
                " where" +
                "  t.address = :address" +
                " order by t.currency, t.bank, t.bankBranch"
    )
})
@Form(
    formContainers={
    },
    serviceClass=ContactsServiceRemote.class
)
public class BankDetail extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "BankDetail";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    //
    @Id
    @Column(name = "bank_detail_id", nullable = false)
    @Property(title="Bank Detail ID", editable=false, readOnly=true, visible=false, hidden=true)
    @Type(type="uuid")
    private UUID bankDetailId;

    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Address", editable=false, readOnly=true, visible=false, hidden=true)
    private Address address;

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

    @JoinColumn(name = "bank_detail_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;

    public BankDetail() {
    }

    public BankDetail(UUID bankDetailId) {
        this.bankDetailId = bankDetailId;
    }

    public BankDetail(UUID bankDetailId, boolean isDefault, String bankAccount) {
        this.bankDetailId = bankDetailId;
        this.isDefault = isDefault;
        this.bankAccount = bankAccount;
    }

    public UUID getBankDetailId() {
        return bankDetailId;
    }

    public void setBankDetailId(UUID bankDetailId) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        if(address != null) {
            setParentId(address.getAddressId());
        } else {
            setParentId(null);
        }
    }

    @Override
    public UUID getParentId() {
        if(address != null) {
            return address.getAddressId();
        }

        return null;
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
    public UUID getId() {
        return getBankDetailId();
    }

    @Override
    public void setId(UUID id) {
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
