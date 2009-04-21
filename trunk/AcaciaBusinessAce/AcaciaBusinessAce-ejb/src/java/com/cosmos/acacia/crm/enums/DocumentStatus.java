/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Miro
 */
public enum DocumentStatus implements DatabaseResource {

    Draft("Draft", "The document is in draft stage."),
    Sent("Sent", "The document is sent, no changes are permitted."),
    Completed("Completed", "The document is completed."),
    WaitForPayment("Wait For Payment", "The documents awaits for payment"),
    Confirmed("Confirmed", "The document is confirmed."),
    Accepted("Accepted", "The document is accepted."),
    PartlyConfirmed("Partly Confirmed", "The document is partly confirmed by the recipient."),
    Reopen("Reopen", "The document is reopened for changes."),
    Rejected("Rejected", "The document is rejected."),
    Cancelled("Cancelled", "The document is cancelled"),
    Paid("Paid", "The document was confirmed and payments were received."),
    Delivered("Delivered", "The items of the documents are delivered."),
    NotDelivered("NotDelivered", "The delivery is pending."),
    PartlyDelivered("Partly Delivered", "Partly delivered."),
    ;

    private static Map<DocumentType, List<DbResource>> dbResourcesByType;
    public static List<DbResource> getDbResourcesByType(DocumentType docType)
    {
        if(dbResourcesByType == null)
        {
            dbResourcesByType = new EnumMap<DocumentType, List<DbResource>>(DocumentType.class);

            // DeliveryCertificate
            List<DbResource> list = Arrays.asList(Draft.dbResource, Delivered.dbResource);
            dbResourcesByType.put(DocumentType.DeliveryCertificate, list);

            // CustomerPayment
            list = Arrays.asList(Draft.dbResource, Completed.dbResource);
            dbResourcesByType.put(DocumentType.CustomerPayment, list);

            // SalesInvoice
            list = Arrays.asList(Draft.dbResource, WaitForPayment.dbResource,
                    Reopen.dbResource, Cancelled.dbResource, Paid.dbResource);
            dbResourcesByType.put(DocumentType.SalesInvoice, list);

            // PurchaseOrder
            list = Arrays.asList(Draft.dbResource, Sent.dbResource, Confirmed.dbResource,
                    PartlyConfirmed.dbResource, Rejected.dbResource, Cancelled.dbResource);
            dbResourcesByType.put(DocumentType.PurchaseOrder, list);

            // PurchaseInvoice
            list = Arrays.asList(Draft.dbResource, Completed.dbResource);
            dbResourcesByType.put(DocumentType.PurchaseInvoice, list);

            // GoodsReceipt
            list = Arrays.asList(Draft.dbResource, Accepted.dbResource);
            dbResourcesByType.put(DocumentType.GoodsReceipt, list);
        }

        return dbResourcesByType.get(docType);
    }

    private DocumentStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }
    //
    private String name;
    private String description;
    private DbResource dbResource;
    private static List<DbResource> dbResources;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DbResource getDbResource() {
        return dbResource;
    }

    @Override
    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    @Override
    public String toShortText() {
        return getName();
    }

    @Override
    public String toText() {
        return getDescription();
    }

    public static List<DbResource> getDbResources() {
        if (dbResources == null) {
            DocumentStatus[] enums = DocumentStatus.values();
            dbResources = new ArrayList<DbResource>(enums.length);

            for (DocumentStatus item : enums) {
                dbResources.add(item.getDbResource());
            }
        }

        return dbResources;
    }
}
