package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
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
        @NamedQuery(
            name = "BankDetail.findByBankDetailId",
            query = "SELECT b FROM BankDetail b WHERE b.bankDetailId = :bankDetailId"
        ),
        @NamedQuery(
            name = "BankDetail.findByIsDefault",
            query = "SELECT b FROM BankDetail b WHERE b.isDefault = :isDefault"
        ),
        @NamedQuery(
            name = "BankDetail.findByBankId",
            query = "SELECT b FROM BankDetail b WHERE b.bankId = :bankId"
        ),
        @NamedQuery(
            name = "BankDetail.findByBankAccount",
            query = "SELECT b FROM BankDetail b WHERE b.bankAccount = :bankAccount"
        )
    }
)
public class BankDetail
        extends DataObjectBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "bank_detail_id", nullable = false)
    @Property(title="Bank Detail ID", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger bankDetailId;
      
    @Column(name = "bank_account", nullable = false)
    @Property(title="Bank Account")
    private String bankAccount;
    
    @Column(name = "iban", nullable = false)
    @Property(title="IBAN")
    private String iban;
    
    @Column(name = "bic", nullable = false)
    @Property(title="BIC")
    private String bic;
    
    @Column(name = "swift_code", nullable = false)
    @Property(title="SWIFT code")
    private String swiftCode;
    
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    @ManyToOne
    @Property(title="Currency")
    private Currency currencyId;
        
    @Column(name = "bank_id", nullable = false)
    @Property(title="Bank")
    private BigInteger bankId;
        
    @JoinColumn(name = "bank_branch_id", referencedColumnName = "address_id")
    @ManyToOne
    @Property(title="Bank Branch")
    private Address bankBranch;
    
    @JoinColumn(name = "bank_contact_id")
    @ManyToOne
    @Property(title="Contact")
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

    public BankDetail(BigInteger bankDetailId, boolean isDefault, BigInteger bankId, String bankAccount) {
        this.bankDetailId = bankDetailId;
        this.isDefault = isDefault;
        this.bankId = bankId;
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
        this.isDefault = isDefault;
    }

    public BigInteger getBankId() {
        return bankId;
    }

    public void setBankId(BigInteger bankId) {
        this.bankId = bankId;
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

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
