package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rlozanov
 */
public enum PaymentType implements DatabaseResource {
    Cash("Cash", "Cash payment when product is received"),
    BankTransfer("Bank Transfer", "Wire transfer payment"),
    CasheOnDelivery("Cashe on delivery", "Cash payment when product is received to customer address")
    ;
    
    private PaymentType (String typeName, String typeDescription) {
        this.name = typeName;
        this.desc = typeDescription;
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
            dbResources = new ArrayList<DbResource>(PaymentType.values().length);

            for(PaymentType unit : PaymentType.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }

}
