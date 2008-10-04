package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rlozanov
 */
public enum PaymentTerm implements DatabaseResource {
    InAdvance("In Advance", "Payment should be payed in advance"),
    AfterDelivery("After Delivery", "Payment should be payed in after delivery"),
    BeforeAndAfter("Before And After", "Payment should be payed partially in advance"),
    Leasing("Leasing", "Leasing"),
    InstalmentPlan("InstalmentPlan", "InstalmentPlan"),
    None("None", "No Special payment terms")
    ;
    
    private PaymentTerm (String typeName, String typeDescription) {
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
            dbResources = new ArrayList<DbResource>(PaymentTerm.values().length);

            for(PaymentTerm unit : PaymentTerm.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }

}
