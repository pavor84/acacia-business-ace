/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.document.BusinessDocument;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miro
 */
public enum DocumentType implements DatabaseResource {

    // Sales
    DeliveryCertificate("Delivery Certificate", "The delivery certificate", null),
    SalesOffer("Sales Offer", "The Sales Offer or Sales Quotation", null),
    SalesProformaInvoice("Sales Proforma Invoice", "The Sales Proforma Invoice", null),
    SalesInvoice("Sales Invoice", "The Sales Invoice", null),
    CustomerPayment("Customer Payment", "The Customer Payment", null),
    //
    // Purchase
    GoodsReceipt("Goods Receipt", "The Goods Receipt", null),
    PurchaseOrder("Purchase Order", "The Purchase Order", null),
    PurchaseOrderConfirmation("Purchase Order Confirmation", "The Purchase Order Confirmation", null),
    PurchaseInvoice("Purchase Invoice", "The Purchase Invoice", PurchaseInvoice.class),
    //
    // Cash
    CashReconcile("Cash Reconcile", "The Cash Reconcile", com.cosmos.acacia.crm.data.accounting.CashReconcile.class),
    ;

    private DocumentType(String name, String description, Class<? extends BusinessDocument> documentClass) {
        this.name = name;
        this.description = description;
        this.documentClass = documentClass;
    }
    //
    private String name;
    private String description;
    private Class<? extends BusinessDocument> documentClass;
    private DbResource dbResource;
    private static List<DbResource> dbResources;

    public Class<? extends BusinessDocument> getDocumentClass() {
        if(documentClass == null)
            throw new IllegalStateException("The class variable is not initialized.");
        return documentClass;
    }

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
