package com.cosmos.acacia.crm.enums;

import java.util.ArrayList;
import java.util.List;

import com.cosmos.acacia.crm.data.DbResource;

public enum DeliveryCertificateStatus implements DatabaseResource {

	Draft("Draft", "The warehouses items are not delivered."),
	Delivered("Delivered", "The warehouse items are delivered and the delivery certificate is read-only.");
    
	DeliveryCertificateStatus(String name, String description){
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
            dbResources = new ArrayList<DbResource>(DeliveryCertificateStatus.values().length);

            for(DeliveryCertificateStatus unit : DeliveryCertificateStatus.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }

}
