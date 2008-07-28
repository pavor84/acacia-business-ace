package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration listing all special permissions
 *
 * @author Bozhidar Bozhanov
 *
 */
public enum SpecialPermission implements DatabaseResource {
    SpecialPermission1,
    SpecialPermission2
    ;

    private DbResource dbResource;
    
    @Override
    public DbResource getDbResource() {
        return dbResource;
    }

    @Override
    public void setDbResource(DbResource resource) {
        this.dbResource = resource;

    }

    @Override
    public String toShortText() {
        return null;
    }

    @Override
    public String toText() {
        return null;
    }
    
    
    private static List<DbResource> dbResources;
    public static List<DbResource> getDbResources()
    {
        if(dbResources == null)
        {
            dbResources = new ArrayList<DbResource>(SpecialPermission.values().length);

            for(SpecialPermission specialPermission : SpecialPermission.values())
            {
                dbResources.add(specialPermission.getDbResource());
            }
        }

        return dbResources;
    }
}
