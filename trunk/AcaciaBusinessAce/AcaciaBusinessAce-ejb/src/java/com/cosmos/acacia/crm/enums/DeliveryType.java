package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rlozanov
 */
public enum DeliveryType implements DatabaseResource {
    InPlace("In Place", "Product is delivered when invoice is created."),
    Courier("Courier", "Product is sent to recipient address via courier organization"),
    PostMail("Post Mail", "Product is sent to recipient via post mail"),
    Email("E-Mail", "Product is sent via e-mail (when this is possible)"),
    RailwayCargo("Railway Cargo", "Product is sent via train"),
    AirCargo("Air Cargo", "Product is sent via plain"),
    AutoCargo("Auto Cargo", "Product is sent via auto"),
    InternalCargo("Internal Cargo", "Product is sent via own cargo service"),
    OtherTransports("Other transports", "Other transports - in this type transportation price should be written")
    ;
    private DeliveryType(String name, String description) {
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
            dbResources = new ArrayList<DbResource>(DeliveryType.values().length);

            for(DeliveryType unit : DeliveryType.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
}
