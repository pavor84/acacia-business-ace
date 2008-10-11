package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Created	:	10.09.2008
 * @author	Petar Milev
 *
 */
public enum InvoiceStatus implements DatabaseResource {
    Open("Open", "The invoice is created and can be changed."),
    Published("Published", "The invoice is considered published and legally active. Can not be edited."),
    Sent("Sent", "The invoice is sent (and legally activated), no changes are permitted."),
    PartlyDelivered("Partly Delivered", "Invoice is partly delivered."),
    Delivered("Delivered", "Invoice is received from recipient."),
    PartlyCanceled("Partly Canceled", "Part(s) of the invoice are canceled"),
    Canceled("Canceled", "The invoice is fully canceled"),
    Finalized("Finalized", "The invoice is in finalized state")
    ;
    private InvoiceStatus(String name, String description) {
        this.name = name;
        this.desc = description;
    }
    
    private String name;
    private String desc;
    private DbResource dbResource;
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.desc;
    }
    
    public DbResource getDbResource() {
        return this.dbResource;
    }

    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    public String toShortText() {
        return getName();
    }

    public String toText() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + dbResource;
    }

    private static List<DbResource> dbResources;
    public static List<DbResource> getDbResources()
    {
        if(dbResources == null)
        {
            dbResources = new ArrayList<DbResource>(InvoiceStatus.values().length);

            for(InvoiceStatus unit : InvoiceStatus.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
}
