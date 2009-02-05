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

    SystemAdministrator(Category.Administration),
    OrganizationAdministrator(Category.Administration),

    BranchAdministrator(Category.BranchAdministration),

    CanViewDataFromAllBranches(Category.Miscellaneous),

    // Contacts
    Contact(Category.Contacts),
    Organization(Category.Contacts),
    Person(Category.Contacts),

    // Core Records
    // Account - Organization
    // Contact - Personal Contact
    Lead(Category.CoreRecords),
    Opportunity(Category.CoreRecords),
    Activity(Category.CoreRecords),
    Note(Category.CoreRecords),
    EmailTemplate(Category.CoreRecords),
    Announcement(Category.CoreRecords),
    Subject(Category.CoreRecords),
    Queue(Category.CoreRecords),
    SavedView(Category.CoreRecords),
    Report(Category.CoreRecords),
    DuplicateDetectionRule(Category.CoreRecords),
    DataImport(Category.CoreRecords),
    DataExport(Category.CoreRecords),
    DataMap(Category.CoreRecords),
    OpportunityRelationship(Category.CoreRecords),
    RelationshipRole(Category.CoreRecords),
    CustomerRelationship(Category.CoreRecords),
    PublishEmailTemplate(Category.CoreRecords),
    AddReportingServicesReports(Category.CoreRecords),
    PublishReports(Category.CoreRecords),
    PublishDuplicateDetectionRules(Category.CoreRecords),

    // Marketing
    MarketingList(Category.Marketing),
    Campaign(Category.Sales),
    CreateQuickCampaign(Category.Sales),

    // Sales
    Product(Category.Sales),
    ProductPricing(Category.Sales),
    Competitor(Category.Sales),
    SalesLiterature(Category.Sales),
    Quote(Category.Sales),
    Order(Category.Sales),
    Invoice(Category.Sales),
    Territory(Category.Sales),
    OverrideQuotePricing(Category.Sales),
    OverrideOrderPricing(Category.Sales),
    OverrideInvoicePricing(Category.Sales),

    // Service
    Article(Category.Service),
    ArticleTemplate(Category.Service),
    Case(Category.Service),
    Contract(Category.Service),
    ContractTemplate(Category.Service),
    PublishArticles(Category.Service),

    // Business Management
    OrganizationSettings(Category.BusinessManagement),
    BusinessUnit(Category.BusinessManagement),
    User(Category.BusinessManagement),
    UserSettings(Category.BusinessManagement),
    Team(Category.BusinessManagement),
    Role(Category.BusinessManagement),
    License(Category.BusinessManagement),
    Currency(Category.BusinessManagement),
    AssignRole(Category.BusinessManagement),
    AssignTerritoryToUser(Category.BusinessManagement),
    BulkEdit(Category.BusinessManagement),
    GoMobile(Category.BusinessManagement),
    Print(Category.BusinessManagement),
    Export(Category.BusinessManagement),
    Merge(Category.BusinessManagement),
    GoOffline(Category.BusinessManagement),
    AddressBook(Category.BusinessManagement),
    UpdateBusinessClosures(Category.BusinessManagement),
    SendEmailAsAnotherUser(Category.BusinessManagement),
    LanguageSettings(Category.BusinessManagement),
    SendInvitation(Category.BusinessManagement),

    // Service Management
    Calendar(Category.ServiceManagement),
    MyWorkHours(Category.ServiceManagement),
    Service(Category.ServiceManagement),
    FacilityEquipment(Category.ServiceManagement),
    Site(Category.ServiceManagement),
    SearchAvailability(Category.ServiceManagement),
    BrowseAvailability(Category.ServiceManagement),

    // Customization
    Entity(Category.Customization),
    EntityAttribute(Category.Customization),
    EntityOperation(Category.Customization),
    EntityNotification(Category.Customization),
    Relationship(Category.Customization),
    Form(Category.Customization),
    View(Category.Customization),
    Workflow(Category.Customization),
    SystemJob(Category.Customization),
    // ISV - Independent Software Vendor. A company (or organization) that
    // builds and sells products (as opposed to a Systems Integrator (SI)
    // who implements products)
    ISVExtensions(Category.Customization),
    ExecuteWorkflowJob(Category.Customization),
    ExportCustomization(Category.Customization),
    ImportCustomization(Category.Customization),
    PublishCustomization(Category.Customization),

    ;

    public enum Category
    {
        CoreRecords,
        Contacts,
        Marketing,
        Sales,
        Service,
        BusinessManagement,
        ServiceManagement,
        Customization,
        CustomEntities,
        Administration,
        BranchAdministration,
        Miscellaneous,
        ;

        private Category() {
        }

        private Set<SpecialPermission> categorizedPermissions;

        public Set<SpecialPermission> getCategorizedPermissions() {
            if(categorizedPermissions == null) {
                categorizedPermissions = new TreeSet<SpecialPermission>();
                for(SpecialPermission permission : SpecialPermission.values()) {
                    if(permission.getCategory().equals(this))
                        categorizedPermissions.add(permission);
                }

                categorizedPermissions = EnumSet.copyOf(categorizedPermissions);
            }

            return categorizedPermissions;
        }
    };

    public static final Set<SpecialPermission> ProductPermissions =
            EnumSet.of(Product, ProductPricing);


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
