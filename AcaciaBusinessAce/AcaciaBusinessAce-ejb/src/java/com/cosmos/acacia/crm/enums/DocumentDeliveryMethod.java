package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rlozanov
 */
public enum DocumentDeliveryMethod implements DatabaseResource {
    InPlace("In Place", "Invoice is delivered when it is created and printed."),
    Email("E-Mail", "Invoice is sent via e-mail"),
    WebServices("Web Services", "Invoice is sent via connection to authomatic web services"),
    Fax("Fax", "Invoice is printed and sent manualy via fax."),
    FaxAuto("Fax Auto", "Authomaticaly sent via fax server"),
    PostMail("Post Mail", "Invoice is sent to recipient via post mail"),
    Courier("Courier", "Sent to recipient address via courier organization"),
    Combined("Combined", "More then one delivery method")
    ;
    private DocumentDeliveryMethod(String name, String description) {
        this.name = name;
        this.desc = description;
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
            dbResources = new ArrayList<DbResource>(DocumentDeliveryMethod.values().length);

            for(DocumentDeliveryMethod unit : DocumentDeliveryMethod.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
}
