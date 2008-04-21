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
 * @author Bozhidar Bozhanov
 */
public enum PassportType implements DatabaseResource {

    National("National"),
    International("International");

    private String name;
    private DbResource dbResource;

    private PassportType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public DbResource getDbResource() {
        return dbResource;
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
            dbResources = new ArrayList<DbResource>(PassportType.values().length);

            for(PassportType passportType : PassportType.values())
            {
                dbResources.add(passportType.getDbResource());
            }
        }

        return dbResources;
    }
}
