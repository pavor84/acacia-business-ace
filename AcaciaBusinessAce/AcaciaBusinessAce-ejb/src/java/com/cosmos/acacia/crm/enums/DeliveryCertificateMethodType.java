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
 * @author daniel
 */
public enum DeliveryCertificateMethodType implements DatabaseResource {
    
    InPlace("In Place", "The Delivery Certificate is delivered at the warehouse"),
    Forwarder("Forwarder", "The Delivery Certificate is delivered to a forwarder");
    
    DeliveryCertificateMethodType(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    private String name;
    private String description;
    private DbResource dbResource;
    private static List<DbResource> dbResources;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public DbResource getDbResource() {
        return this.dbResource;
    }

    @Override
    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    @Override
    public String toShortText() {
        return getName();
    }

    @Override
    public String toText() {
        return getDescription();
    }

     public static List<DbResource> getDbResources()
    {
        if(dbResources == null)
        {
            dbResources = new ArrayList<DbResource>(DeliveryCertificateMethodType.values().length);

            for(DeliveryCertificateMethodType unit : DeliveryCertificateMethodType.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
        

}
