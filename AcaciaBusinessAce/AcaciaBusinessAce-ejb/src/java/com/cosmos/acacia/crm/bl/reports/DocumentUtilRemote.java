package com.cosmos.acacia.crm.bl.reports;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;

@Remote
public interface DocumentUtilRemote {

    PrintableDocumentHeader createDocumentHeaderRecipientPart(
            BusinessPartner partner,
            Address branch,
            ContactPerson contact,
            PrintableDocumentHeader header);

    PrintableDocumentHeader createDocumentHeaderExecutorPart(Address branch);

    PrintableDocumentHeader createDocumentHeaderExecutorPart(
            Address branch,
            PrintableDocumentHeader header);
}
