/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miro
 */
public enum DocumentType implements DatabaseResource {

    // Sales
    DeliveryCertificate("Delivery Certificate", "The delivery certificate"),
    SalesOffer("Sales Offer", "The Sales Offer or Sales Quotation"),
    SalesProformaInvoice("Sales Proforma Invoice", "The Sales Proforma Invoice"),
    SalesInvoice("Sales Invoice", "The Sales Invoice"),
    CustomerPayment("Customer Payment", "The Customer Payment"),
    //
    // Purchase
    GoodsReceipt("Goods Receipt", "The Goods Receipt"),
    PurchaseOrder("Purchase Order", "The Purchase Order"),
    PurchaseOrderConfirmation("Purchase Order Confirmation", "The Purchase Order Confirmation"),
    PurchaseInvoice("Purchase Invoice", "The Purchase Invoice"),
    ;

    private DocumentType(String name, String description) {
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
        return this.dbResource;
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
