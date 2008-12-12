/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.security;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Miro
 */
public class Privilege
{
    // Contacts
    public static final Privilege Contact =
            new Privilege("Contact", PrivilegeCategory.Contacts);

    public static final Privilege Organization =
            new Privilege("Organization", PrivilegeCategory.Contacts);

    public static final Privilege Person =
            new Privilege("Person", PrivilegeCategory.Contacts);

    // Core Records
    // Account - Organization
    // Contact - Personal Contact
    public static final Privilege Lead =
            new Privilege("Lead", PrivilegeCategory.CoreRecords);

    public static final Privilege Opportunity =
            new Privilege("Opportunity", PrivilegeCategory.CoreRecords);

    public static final Privilege Activity =
            new Privilege("Activity", PrivilegeCategory.CoreRecords);

    public static final Privilege Note =
            new Privilege("Note", PrivilegeCategory.CoreRecords);

    public static final Privilege EmailTemplate =
            new Privilege("EmailTemplate", PrivilegeCategory.CoreRecords);

    public static final Privilege Announcement =
            new Privilege("Announcement", PrivilegeCategory.CoreRecords);

    public static final Privilege Subject =
            new Privilege("Subject", PrivilegeCategory.CoreRecords);

    public static final Privilege Queue =
            new Privilege("Queue", PrivilegeCategory.CoreRecords);

    public static final Privilege SavedView =
            new Privilege("SavedView", PrivilegeCategory.CoreRecords);

    public static final Privilege Report =
            new Privilege("Report", PrivilegeCategory.CoreRecords);

    public static final Privilege DuplicateDetectionRule =
            new Privilege("DuplicateDetectionRule", PrivilegeCategory.CoreRecords);

    public static final Privilege DataImport =
            new Privilege("DataImport", PrivilegeCategory.CoreRecords);

    public static final Privilege DataExport =
            new Privilege("DataExport", PrivilegeCategory.CoreRecords);

    public static final Privilege DataMap =
            new Privilege("DataMap", PrivilegeCategory.CoreRecords);

    public static final Privilege OpportunityRelationship =
            new Privilege("OpportunityRelationship", PrivilegeCategory.CoreRecords);

    public static final Privilege RelationshipRole =
            new Privilege("RelationshipRole", PrivilegeCategory.CoreRecords);

    public static final Privilege CustomerRelationship =
            new Privilege("CustomerRelationship", PrivilegeCategory.CoreRecords);

    public static final Privilege PublishEmailTemplate =
            new Privilege("PublishEmailTemplate", PrivilegeCategory.CoreRecords);

    public static final Privilege AddReportingServicesReports =
            new Privilege("AddReportingServicesReports", PrivilegeCategory.CoreRecords);

    public static final Privilege PublishReports =
            new Privilege("PublishReports", PrivilegeCategory.CoreRecords);

    public static final Privilege PublishDuplicateDetectionRules =
            new Privilege("PublishDuplicateDetectionRules", PrivilegeCategory.CoreRecords);

    // Marketing
    public static final Privilege MarketingList =
            new Privilege("MarketingList", PrivilegeCategory.Marketing);

    public static final Privilege Campaign =
            new Privilege("Campaign", PrivilegeCategory.Sales);

    public static final Privilege CreateQuickCampaign =
            new Privilege("CreateQuickCampaign", PrivilegeCategory.Sales);

    // Sales
    public static final Privilege Product =
            new Privilege("Product", PrivilegeCategory.Sales);

    public static final Privilege ProductPricing =
            new Privilege("ProductPricing", PrivilegeCategory.Sales);

    public static final Privilege Competitor =
            new Privilege("Competitor", PrivilegeCategory.Sales);

    public static final Privilege SalesLiterature =
            new Privilege("SalesLiterature", PrivilegeCategory.Sales);

    public static final Privilege Quote =
            new Privilege("Quote", PrivilegeCategory.Sales);

    public static final Privilege Order =
            new Privilege("Order", PrivilegeCategory.Sales);

    public static final Privilege Invoice =
            new Privilege("Invoice", PrivilegeCategory.Sales);

    public static final Privilege Territory =
            new Privilege("Territory", PrivilegeCategory.Sales);

    public static final Privilege OverrideQuotePricing =
            new Privilege("OverrideQuotePricing", PrivilegeCategory.Sales);

    public static final Privilege OverrideOrderPricing =
            new Privilege("OverrideOrderPricing", PrivilegeCategory.Sales);

    public static final Privilege OverrideInvoicePricing =
            new Privilege("OverrideInvoicePricing", PrivilegeCategory.Sales);

    // Service
    public static final Privilege Article =
            new Privilege("Article", PrivilegeCategory.Service);

    public static final Privilege ArticleTemplate =
            new Privilege("ArticleTemplate", PrivilegeCategory.Service);

    public static final Privilege Case =
            new Privilege("Case", PrivilegeCategory.Service);

    public static final Privilege Contract =
            new Privilege("Contract", PrivilegeCategory.Service);

    public static final Privilege ContractTemplate =
            new Privilege("ContractTemplate", PrivilegeCategory.Service);

    public static final Privilege PublishArticles =
            new Privilege("PublishArticles", PrivilegeCategory.Service);

    // Business Management
    public static final Privilege OrganizationSettings =
            new Privilege("OrganizationSettings", PrivilegeCategory.BusinessManagement);

    public static final Privilege BusinessUnit =
            new Privilege("BusinessUnit", PrivilegeCategory.BusinessManagement);

    public static final Privilege User =
            new Privilege("User", PrivilegeCategory.BusinessManagement);

    public static final Privilege UserSettings =
            new Privilege("UserSettings", PrivilegeCategory.BusinessManagement);

    public static final Privilege Team =
            new Privilege("Team", PrivilegeCategory.BusinessManagement);

    public static final Privilege Role =
            new Privilege("Role", PrivilegeCategory.BusinessManagement);

    public static final Privilege License =
            new Privilege("License", PrivilegeCategory.BusinessManagement);

    public static final Privilege Currency =
            new Privilege("Currency", PrivilegeCategory.BusinessManagement);

    public static final Privilege AssignRole =
            new Privilege("AssignRole", PrivilegeCategory.BusinessManagement);

    public static final Privilege AssignTerritoryToUser =
            new Privilege("AssignTerritoryToUser", PrivilegeCategory.BusinessManagement);

    public static final Privilege BulkEdit =
            new Privilege("BulkEdit", PrivilegeCategory.BusinessManagement);

    public static final Privilege GoMobile =
            new Privilege("GoMobile", PrivilegeCategory.BusinessManagement);

    public static final Privilege Print =
            new Privilege("Print", PrivilegeCategory.BusinessManagement);

    public static final Privilege Export =
            new Privilege("Export", PrivilegeCategory.BusinessManagement);

    public static final Privilege Merge =
            new Privilege("Merge", PrivilegeCategory.BusinessManagement);

    public static final Privilege GoOffline =
            new Privilege("GoOffline", PrivilegeCategory.BusinessManagement);

    public static final Privilege AddressBook =
            new Privilege("AddressBook", PrivilegeCategory.BusinessManagement);

    public static final Privilege UpdateBusinessClosures =
            new Privilege("UpdateBusinessClosures", PrivilegeCategory.BusinessManagement);

    public static final Privilege SendEmailAsAnotherUser =
            new Privilege("SendEmailAsAnotherUser", PrivilegeCategory.BusinessManagement);

    public static final Privilege LanguageSettings =
            new Privilege("LanguageSettings", PrivilegeCategory.BusinessManagement);

    public static final Privilege SendInvitation =
            new Privilege("SendInvitation", PrivilegeCategory.BusinessManagement);

    // Service Management
    public static final Privilege Calendar =
            new Privilege("Calendar", PrivilegeCategory.ServiceManagement);

    public static final Privilege MyWorkHours =
            new Privilege("MyWorkHours", PrivilegeCategory.ServiceManagement);

    public static final Privilege Service =
            new Privilege("Service", PrivilegeCategory.ServiceManagement);

    public static final Privilege FacilityEquipment =
            new Privilege("FacilityEquipment", PrivilegeCategory.ServiceManagement);

    public static final Privilege Site =
            new Privilege("Site", PrivilegeCategory.ServiceManagement);

    public static final Privilege SearchAvailability =
            new Privilege("SearchAvailability", PrivilegeCategory.ServiceManagement);

    public static final Privilege BrowseAvailability =
            new Privilege("BrowseAvailability", PrivilegeCategory.ServiceManagement);

    // Customization
    public static final Privilege Entity =
            new Privilege("Entity", PrivilegeCategory.Customization);

    public static final Privilege EntityAttribute =
            new Privilege("EntityAttribute", PrivilegeCategory.Customization);

    public static final Privilege EntityOperation =
            new Privilege("EntityOperation", PrivilegeCategory.Customization);

    public static final Privilege EntityNotification =
            new Privilege("EntityNotification", PrivilegeCategory.Customization);

    public static final Privilege Relationship =
            new Privilege("Relationship", PrivilegeCategory.Customization);

    public static final Privilege Form =
            new Privilege("Form", PrivilegeCategory.Customization);

    public static final Privilege View =
            new Privilege("View", PrivilegeCategory.Customization);

    public static final Privilege Workflow =
            new Privilege("Workflow", PrivilegeCategory.Customization);

    public static final Privilege SystemJob =
            new Privilege("SystemJob", PrivilegeCategory.Customization);

    // ISV - Independent Software Vendor. A company (or organization) that
    // builds and sells products (as opposed to a Systems Integrator (SI)
    // who implements products)
    public static final Privilege ISVExtensions =
            new Privilege("ISVExtensions", PrivilegeCategory.Customization);

    public static final Privilege ExecuteWorkflowJob =
            new Privilege("ExecuteWorkflowJob", PrivilegeCategory.Customization);

    public static final Privilege ExportCustomization =
            new Privilege("ExportCustomization", PrivilegeCategory.Customization);

    public static final Privilege ImportCustomization =
            new Privilege("ImportCustomization", PrivilegeCategory.Customization);

    public static final Privilege PublishCustomization =
            new Privilege("PublishCustomization", PrivilegeCategory.Customization);


    private Role role;
    private String privilegeName;
    private PrivilegeCategory privilegeCategory;
    private PrivilegeType privilegeType;
    private Set<AccessRight> inapplicableRights;
    private Map<AccessRight, AccessLevel> accessRights;
    private Object entityType;

    public Privilege()
    {
    }

    public Privilege(
            String privilegeName,
            PrivilegeCategory privilegeCategory)
    {
        this.privilegeCategory = privilegeCategory;
    }

    public Map<AccessRight, AccessLevel> getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(Map<AccessRight, AccessLevel> accessRights) {
        this.accessRights = accessRights;
    }

    public Object getEntityType() {
        return entityType;
    }

    public void setEntityType(Object entityType) {
        this.entityType = entityType;
    }

    public Set<AccessRight> getInapplicableRights() {
        return inapplicableRights;
    }

    public void setInapplicableRights(Set<AccessRight> inapplicableRights) {
        this.inapplicableRights = inapplicableRights;
    }

    public PrivilegeCategory getPrivilegeCategory() {
        return privilegeCategory;
    }

    public void setPrivilegeCategory(PrivilegeCategory privilegeCategory) {
        this.privilegeCategory = privilegeCategory;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public PrivilegeType getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(PrivilegeType privilegeType) {
        this.privilegeType = privilegeType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
