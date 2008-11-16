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
    Open("Open", "The document is created and can be changed."),
    WaitForPayment("Wait For Payment", "The documents awaits for payment"),
    Reopen("Reopen", "The document was reopen for modification"),
    Cancelled("Cancelled", "The document was cancelled"),
    Paid("Paid", "The document was confirmed and payments were received.");

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
