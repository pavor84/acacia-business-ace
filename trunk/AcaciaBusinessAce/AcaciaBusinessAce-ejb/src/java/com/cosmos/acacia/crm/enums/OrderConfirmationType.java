package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Created	:	22.07.2008
 * @author	Petar Milev
 *
 */
public enum OrderConfirmationType implements DatabaseResource {
    VatInvoice("Vat Invoice", "VAT refundable invoice"),
    SimpleInvoice("Simple Invoice", "Non VAT enabled invoice"),
    DebitNoteInvoice("Debit Note", "Invoice with debit note"),
    CretidNoteInvoice("Cretid Note", "Invoice with credit note")
    ;
    
    private OrderConfirmationType (String typeName, String typeDescription) {
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
        return getName();
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
            dbResources = new ArrayList<DbResource>(OrderConfirmationType.values().length);

            for(OrderConfirmationType unit : OrderConfirmationType.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }

}
