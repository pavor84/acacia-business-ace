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
public enum CommunicationType implements DatabaseResource {

    Phone("Phone"),
    Mobile("Mobile"),
    ICQ("ICQ"),
    Skype("Skype");

    private String communicationType;
    private DbResource dbResource;

    private CommunicationType(String communicationType){
        this.communicationType = communicationType;
    }

    public String getCommunicationType(){
        return communicationType;
    }

    public DbResource getDbResource() {
        return dbResource;
    }

    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    public String toShortText() {
        return getCommunicationType();
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
            dbResources = new ArrayList<DbResource>(CommunicationType.values().length);

            for(CommunicationType communicationType : CommunicationType.values())
            {
                dbResources.add(communicationType.getDbResource());
            }
        }

        return dbResources;
    }
}
