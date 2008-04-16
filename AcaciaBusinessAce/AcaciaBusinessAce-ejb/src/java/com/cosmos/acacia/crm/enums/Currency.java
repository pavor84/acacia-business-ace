package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rlozanov
 */
public enum Currency implements DatabaseResource {
    Euro("Euro", "European currency.", "EUR"),
    Leva("Bulgarian Leva", "Bulgarian currency", "BGN"),
    Dollar("US Dollar", "Unated States dollar", "USD")
    ;
    private Currency(String name, String description, String code) {
        this.name = name;
        this.desc = description;
        this.code = code;
    }
    
    private String name;
    private String desc;
    private String code;
    private DbResource dbResource;
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.desc;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public DbResource getDbResource() {
        return this.dbResource;
    }

    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    public String toShortText() {
        return getCode();
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
            dbResources = new ArrayList<DbResource>(Currency.values().length);

            for(Currency unit : Currency.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
}
