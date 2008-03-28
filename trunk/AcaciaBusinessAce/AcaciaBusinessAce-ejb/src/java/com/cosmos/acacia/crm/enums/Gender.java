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
public enum Gender implements DatabaseResource {

    Male("male"),
    Female("female");
    
    private String gender;
    private DbResource dbResource;
     
    private Gender(String gender){
        this.gender = gender;
    }
    
    public String getGender(){
        return gender;
    }
    
    public DbResource getDbResource() {
        return dbResource;
    }

    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    public String toShortText() {
        return getGender();
    }

    public String toText() {
        return getGender();
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
            dbResources = new ArrayList<DbResource>(ProductColor.values().length);

            for(Gender gender : Gender.values())
            {
                dbResources.add(gender.getDbResource());
            }
        }

        return dbResources;
    }
}
