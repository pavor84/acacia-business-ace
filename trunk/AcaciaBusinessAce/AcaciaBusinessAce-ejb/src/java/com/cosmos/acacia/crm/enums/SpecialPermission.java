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

    CanViewDataFromAllBranches(Category.Miscellaneous),
    SystemAdministrator(Category.Miscellaneous),
    OrganizationAdministrator(Category.Miscellaneous),
    BranchAdministrator(Category.Miscellaneous),

    // Sales
    Product(Category.Sales),
    Pricing(Category.Sales),
    Competitor(Category.Sales),
    SalesLiterature(Category.Sales),
    Quote(Category.Sales),
    Order(Category.Sales),
    Invoice(Category.Sales),
    Territory(Category.Sales),
    OverrideQuotePricing(Category.Sales),
    OverrideOrderPricing(Category.Sales),
    OverrideInvoicePricing(Category.Sales),
    ;

    public enum Category
    {
        CoreRecords,
        Marketing,
        Sales,
        Service,
        BusinessManagement,
        ServiceManagement,
        Customization,
        CustomEntities,
        Miscellaneous,
        ;
    };

    private SpecialPermission(Category category)
    {
        this.category = category;
    }

    private Category category;
    private DbResource dbResource;

    public Category getCategory()
    {
        return category;
    }

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
    public static List<DbResource> getDbResources() {
        if(dbResources == null) {
            dbResources = new ArrayList<DbResource>(SpecialPermission.values().length);

            for(SpecialPermission specialPermission : SpecialPermission.values()) {
                dbResources.add(specialPermission.getDbResource());
            }
        }

        return dbResources;
    }

    public boolean matches(DbResource dbResource) {
        return toString().equals(dbResource.getEnumName());
    }
}
