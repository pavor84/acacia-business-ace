package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Created	:	16.07.2008
 * @author	Petar Milev
 *
 */
public enum PurchaseOrderStatus implements DatabaseResource {
    Open("Open", "The purchase order is created and can be changed."),
    Sent("Sent", "The purchase order is sent, no changes are permitted."),
    Confirmed("Confirmed", "The purchase order is totally confirmed by the supplier."),
    PartlyConfirmed("Partly Confirmed", "The purchase order is partly confirmed by the supplier."),
    PartlyDelivered("Partly Delivered", "The purchase order is partly delivered."),
    Delivered("Delivered", "The purchase order is delivered."),
    Rejected("Rejected", "The purchase order is rejected by the supplier."),
    Canceled("Canceled", "The purchase order is cancelled by it's author.")
    ;
    private PurchaseOrderStatus(String name, String description) {
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
            dbResources = new ArrayList<DbResource>(PurchaseOrderStatus.values().length);

            for(PurchaseOrderStatus unit : PurchaseOrderStatus.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
}
