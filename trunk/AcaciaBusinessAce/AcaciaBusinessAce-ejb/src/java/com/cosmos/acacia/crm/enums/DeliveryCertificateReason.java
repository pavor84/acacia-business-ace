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
 * @author daniel
 */
public enum DeliveryCertificateReason implements DatabaseResource {
    
    Invoice("Invoice", "One or more invoices"),
    ProformaInvoice("ProformaInvoice", "One or more proform invoices"),
    Offer("Offer", "One or more offers"),
    Test("Test", "For tesing purposes. As a reason document is used an offer. The offer should claim return date."),
    Replacement("Replacement", "Replacing for a item. As a reason document is used a reciept certificate which is accepted the old item.");
    
    DeliveryCertificateReason(String name, String description){
        this.name = name;
        this.description = description;
    }
    
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

     public static List<DbResource> getDbResources()
    {
        if(dbResources == null)
        {
            dbResources = new ArrayList<DbResource>(DeliveryCertificateReason.values().length);

            for(DeliveryCertificateReason unit : DeliveryCertificateReason.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
        

}
