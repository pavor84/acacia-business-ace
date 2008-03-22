package com.cosmos.acacia.crm.data;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
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
@NamedQueries({@NamedQuery(name = "BankDetail.findByBankDetailId", query = "SELECT b FROM BankDetail b WHERE b.bankDetailId = :bankDetailId"), @NamedQuery(name = "BankDetail.findByIsDefault", query = "SELECT b FROM BankDetail b WHERE b.isDefault = :isDefault"), @NamedQuery(name = "BankDetail.findByBankId", query = "SELECT b FROM BankDetail b WHERE b.bankId = :bankId"), @NamedQuery(name = "BankDetail.findByBankAccount", query = "SELECT b FROM BankDetail b WHERE b.bankAccount = :bankAccount")})
public class BankDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "bank_detail_id", nullable = false)
    private Long bankDetailId;
    @Column(name = "is_default", nullable = false)
    private boolean isDefault;
    @Column(name = "bank_id", nullable = false)
    private long bankId;
    @Column(name = "bank_account", nullable = false)
    private String bankAccount;
    @JoinColumn(name = "bank_branch_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address bankBranchId;
    @JoinColumn(name = "parent_id", referencedColumnName = "address_id")
    @ManyToOne
    private Address parentId;
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
    @ManyToOne
    private Currency currencyId;
    @JoinColumn(name = "bank_detail_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;
    @JoinColumn(name = "bank_contact_id")
    @ManyToOne
    private Person bankContactId;

    public BankDetail() {
    }

    public BankDetail(Long bankDetailId) {
        this.bankDetailId = bankDetailId;
    }

    public BankDetail(Long bankDetailId, boolean isDefault, long bankId, String bankAccount) {
        this.bankDetailId = bankDetailId;
        this.isDefault = isDefault;
        this.bankId = bankId;
        this.bankAccount = bankAccount;
    }

    public Long getBankDetailId() {
        return bankDetailId;
    }

    public void setBankDetailId(Long bankDetailId) {
        this.bankDetailId = bankDetailId;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Address getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(Address bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public Address getParentId() {
        return parentId;
    }

    public void setParentId(Address parentId) {
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
        return bankContactId;
    }

    public void setBankContactId(Person bankContactId) {
        this.bankContactId = bankContactId;
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

}
