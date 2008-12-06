package com.cosmos.acacia.crm.reports;

import java.util.List;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.bl.contactbook.BankDetailsListRemote;
import com.cosmos.acacia.crm.bl.contactbook.PassportsListRemote;
import com.cosmos.acacia.crm.client.LocalSession;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.BankDetail;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Passport;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.enums.PassportType;
import com.cosmos.acacia.gui.AcaciaPanel;

public class DocumentUtil {

    public static PrintableDocumentHeader createDocumentHeaderExecutorPart(Address branch) {
        return createDocumentHeaderExecutorPart(branch, new PrintableDocumentHeader());
    }

    public static PrintableDocumentHeader createDocumentHeaderExecutorPart(Address branch, PrintableDocumentHeader header) {

        Organization organization = LocalSession.instance().getOrganization();

        Address address = organization.getRegistrationAddress();
        if (address == null)
            address = organization.getAdministrationAddress();
        if (address == null)
            address = branch;

        if (address != null)
            header.setExecutorAddress(address.getPostalAddress());

        BankDetail executorBankDetail = getBankDetail(branch);
        if (executorBankDetail == null)
            executorBankDetail = getBankDetail(organization.getRegistrationAddress());

        if (executorBankDetail == null)
            executorBankDetail = getBankDetail(organization.getAdministrationAddress());


        header.setExecutorBank(executorBankDetail.getBank().getOrganizationName());

        //Getting the "bank account"
        String bankAccountString = executorBankDetail.getIban();
        if (bankAccountString == null || bankAccountString.length() == 0)
            bankAccountString = executorBankDetail.getBankAccount();

        header.setExecutorBankAccount(bankAccountString);

        String bankCode = executorBankDetail.getBic();
        if (bankCode == null || bankCode.length() > 0)
            bankCode = executorBankDetail.getSwiftCode();

        header.setExecutorBankCode(bankCode);

        header.setExecutorUniqueIdentifier(organization.getUniqueIdentifierCode());
        header.setExecutorMOL(null); //TODO get the MOL
        header.setExecutorOrganizationName(organization.getOrganizationName());
        header.setExecutorVATNumber(organization.getVatNumber());

        return header;
    }

    //Note: almost copy-pasted :( (reflection also not the best choice), using a list also
    public static PrintableDocumentHeader createDocumentHeaderRecipientPart(BusinessPartner partner, Address branch, ContactPerson contact, PrintableDocumentHeader header) {

        PassportsListRemote passportsSession = AcaciaPanel.getBean(PassportsListRemote.class);
        AddressesListRemote session = AcaciaPanel.getBean(AddressesListRemote.class);

        Organization org = null;
        Person person = null;
        if (partner instanceof Organization)
            org = (Organization) partner;
        if (partner instanceof Person)
            person = (Person) partner;

        if (header == null)
            header = new PrintableDocumentHeader();

        Address address = null;
        if (org != null) {
            address = org.getRegistrationAddress();
            if (address == null)
                address = org.getAdministrationAddress();
            if (address == null)
                address = branch;

            if (address != null)
                header.setRecipientAddress(address.getPostalAddress());
        }

        if (person != null) {
            if (branch != null)
                header.setRecipientAddress(branch.getPostalAddress());
        }


        BankDetail recipientBankDetail = getBankDetail(branch);
        if (recipientBankDetail == null)
            recipientBankDetail = getBankDetail(address);


        header.setRecipientBank(recipientBankDetail.getBank().getOrganizationName());

        //Getting the "bank account"
        String bankAccountString = recipientBankDetail.getIban();
        if (bankAccountString == null || bankAccountString.length() == 0)
            bankAccountString = recipientBankDetail.getBankAccount();

        header.setRecipientBankAccount(bankAccountString);

        String bankCode = recipientBankDetail.getBic();
        if (bankCode == null || bankCode.length() > 0)
            bankCode = recipientBankDetail.getSwiftCode();

        header.setRecipientBankCode(bankCode);

        if (org != null) {
            header.setRecipientUniqueIdentifier(org.getUniqueIdentifierCode());
            header.setRecipientMOL(null); //TODO get the MOL
            header.setRecipientName(org.getOrganizationName());
            header.setRecipientVATNumber(org.getVatNumber());
        }

        if (person != null) {
            header.setRecipientUniqueIdentifier(person.getPersonalUniqueId());
            header.setRecipientName(person.getDisplayName());
        }


        List<Passport> passports = passportsSession.getPassports(contact.getContact().getId());
        Passport recipientPassport = null;
        for (Passport passport : passports) {
            if (passport.getPassportType().getEnumValue() == PassportType.National)
                recipientPassport = passport;
        }
        header.setRecipientPassport(recipientPassport);

        return header;
    }

    private static BankDetailsListRemote bankSession;

    public static BankDetail getBankDetail(Address branch) {
        bankSession = AcaciaPanel.getBean(BankDetailsListRemote.class);

        BankDetail bankDetail = null;
        List<BankDetail> bankDetailsList =
                bankSession.getBankDetails(branch.getAddressId());

        for (BankDetail d : bankDetailsList) {
            if (d.getIsDefault()) {
                bankDetail = d;
                break;
            }
        }

        if (bankDetail == null && bankDetailsList.size() > 0)
            bankDetail = bankDetailsList.get(0);

        return bankDetail;
    }
}
