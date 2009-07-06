package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Enumeration listing all special permissions
 *
 * @author Bozhidar Bozhanov
 *
 */
public enum SpecialPermission implements DatabaseResource {

    SystemAdministrator(PermissionCategory.Administration),
    OrganizationAdministrator(PermissionCategory.Administration),

    BranchAdministrator(PermissionCategory.BranchAdministration),

    CanViewDataFromAllBranches(PermissionCategory.Miscellaneous),

    // Contacts
    Contact(PermissionCategory.Contacts),
    Organization(PermissionCategory.Contacts),
    Person(PermissionCategory.Contacts),

    // Core Records
    // Account - Organization
    // Contact - Personal Contact
    Lead(PermissionCategory.CoreRecords),
    Opportunity(PermissionCategory.CoreRecords),
    Activity(PermissionCategory.CoreRecords),
    Note(PermissionCategory.CoreRecords),
    EmailTemplate(PermissionCategory.CoreRecords),
    Announcement(PermissionCategory.CoreRecords),
    Subject(PermissionCategory.CoreRecords),
    Queue(PermissionCategory.CoreRecords),
    SavedView(PermissionCategory.CoreRecords),
    Report(PermissionCategory.CoreRecords),
    DuplicateDetectionRule(PermissionCategory.CoreRecords),
    DataImport(PermissionCategory.CoreRecords),
    DataExport(PermissionCategory.CoreRecords),
    DataMap(PermissionCategory.CoreRecords),
    OpportunityRelationship(PermissionCategory.CoreRecords),
    RelationshipRole(PermissionCategory.CoreRecords),
    CustomerRelationship(PermissionCategory.CoreRecords),
    PublishEmailTemplate(PermissionCategory.CoreRecords),
    AddReportingServicesReports(PermissionCategory.CoreRecords),
    PublishReports(PermissionCategory.CoreRecords),
    PublishDuplicateDetectionRules(PermissionCategory.CoreRecords),

    // Marketing
    MarketingList(PermissionCategory.Marketing),
    Campaign(PermissionCategory.Sales),
    CreateQuickCampaign(PermissionCategory.Sales),

    // Sales
    Product(PermissionCategory.Sales),
    ProductPricing(PermissionCategory.Sales),
    Competitor(PermissionCategory.Sales),
    SalesLiterature(PermissionCategory.Sales),
    Quote(PermissionCategory.Sales),
    SalesOrder(PermissionCategory.Sales),
    SalesInvoice(PermissionCategory.Sales),
    Territory(PermissionCategory.Sales),
    OverrideQuotePricing(PermissionCategory.Sales),
    OverrideOrderPricing(PermissionCategory.Sales),
    OverrideInvoicePricing(PermissionCategory.Sales),

    //
    PurchaseOrder(PermissionCategory.Supplies),
    PurchaseInvoice(PermissionCategory.Supplies),

    // Service
    Article(PermissionCategory.Service),
    ArticleTemplate(PermissionCategory.Service),
    Case(PermissionCategory.Service),
    Contract(PermissionCategory.Service),
    ContractTemplate(PermissionCategory.Service),
    PublishArticles(PermissionCategory.Service),

    // Business Management
    OrganizationSettings(PermissionCategory.BusinessManagement),
    BusinessUnit(PermissionCategory.BusinessManagement),
    User(PermissionCategory.BusinessManagement),
    UserSettings(PermissionCategory.BusinessManagement),
    Team(PermissionCategory.BusinessManagement),
    Role(PermissionCategory.BusinessManagement),
    License(PermissionCategory.BusinessManagement),
    Currency(PermissionCategory.BusinessManagement),
    AssignRole(PermissionCategory.BusinessManagement),
    AssignTerritoryToUser(PermissionCategory.BusinessManagement),
    BulkEdit(PermissionCategory.BusinessManagement),
    GoMobile(PermissionCategory.BusinessManagement),
    Print(PermissionCategory.BusinessManagement),
    Export(PermissionCategory.BusinessManagement),
    Merge(PermissionCategory.BusinessManagement),
    GoOffline(PermissionCategory.BusinessManagement),
    AddressBook(PermissionCategory.BusinessManagement),
    UpdateBusinessClosures(PermissionCategory.BusinessManagement),
    SendEmailAsAnotherUser(PermissionCategory.BusinessManagement),
    LanguageSettings(PermissionCategory.BusinessManagement),
    SendInvitation(PermissionCategory.BusinessManagement),

    // Service Management
    Calendar(PermissionCategory.ServiceManagement),
    MyWorkHours(PermissionCategory.ServiceManagement),
    Service(PermissionCategory.ServiceManagement),
    FacilityEquipment(PermissionCategory.ServiceManagement),
    Site(PermissionCategory.ServiceManagement),
    SearchAvailability(PermissionCategory.ServiceManagement),
    BrowseAvailability(PermissionCategory.ServiceManagement),

    // Customization
    Entity(PermissionCategory.Customization),
    EntityAttribute(PermissionCategory.Customization),
    EntityOperation(PermissionCategory.Customization),
    EntityNotification(PermissionCategory.Customization),
    Relationship(PermissionCategory.Customization),
    Form(PermissionCategory.Customization),
    View(PermissionCategory.Customization),
    Workflow(PermissionCategory.Customization),
    SystemJob(PermissionCategory.Customization),
    // ISV - Independent Software Vendor. A company (or organization) that
    // builds and sells products (as opposed to a Systems Integrator (SI)
    // who implements products)
    ISVExtensions(PermissionCategory.Customization),
    ExecuteWorkflowJob(PermissionCategory.Customization),
    ExportCustomization(PermissionCategory.Customization),
    ImportCustomization(PermissionCategory.Customization),
    PublishCustomization(PermissionCategory.Customization),
    ;

    public static final Set<SpecialPermission> ProductPermissions =
            EnumSet.of(Product, ProductPricing);


    private SpecialPermission(PermissionCategory category) {
        this.category = category;
    }

    private PermissionCategory category;
    private DbResource dbResource;

    public PermissionCategory getCategory() {
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
