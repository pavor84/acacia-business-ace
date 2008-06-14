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
 * @author Miro
 */
public enum AssemblingSchemaItemDataType
    implements DatabaseResource
{
    Integer("Integer"),
    Decimal("Decimal"),
    Date("Date"),
    String("String");

    private AssemblingSchemaItemDataType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public DbResource getDbResource() {
        return dbResource;
    }

    @Override
    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    private String name;
    private DbResource dbResource;

    @Override
    public String toShortText()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toText()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static List<DbResource> dbResources;
    public static List<DbResource> getDbResources()
    {
        if(dbResources == null)
        {
            dbResources = new ArrayList<DbResource>(AssemblingSchemaItemDataType.values().length);

            for(AssemblingSchemaItemDataType item : AssemblingSchemaItemDataType.values())
            {
                dbResources.add(item.getDbResource());
            }
        }

        return dbResources;
    }
}
