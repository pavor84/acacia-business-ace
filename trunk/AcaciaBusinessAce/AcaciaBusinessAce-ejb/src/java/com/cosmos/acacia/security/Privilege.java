/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.security;

import com.cosmos.acacia.crm.enums.BusinessActivity;
import java.util.Map;

/**
 *
 * @author Miro
 */
public class Privilege
{
    // Contacts
    public static final Privilege Contact =
            new Privilege("Contact", BusinessActivity.Contacts);

    public static final Privilege Organization =
            new Privilege("Organization", BusinessActivity.Contacts);

    public static final Privilege Person =
            new Privilege("Person", BusinessActivity.Contacts);

    // Core Records
    // Account - Organization
    // Contact - Personal Contact
    public static final Privilege Lead =
            new Privilege("Lead", BusinessActivity.CoreRecords);

    public static final Privilege Opportunity =
            new Privilege("Opportunity", BusinessActivity.CoreRecords);

    public static final Privilege Activity =
            new Privilege("Activity", BusinessActivity.CoreRecords);

    public static final Privilege Note =
            new Privilege("Note", BusinessActivity.CoreRecords);

    public static final Privilege EmailTemplate =
            new Privilege("EmailTemplate", BusinessActivity.CoreRecords);

    public static final Privilege Announcement =
            new Privilege("Announcement", BusinessActivity.CoreRecords);

    public static final Privilege Subject =
            new Privilege("Subject", BusinessActivity.CoreRecords);

    public static final Privilege Queue =
            new Privilege("Queue", BusinessActivity.CoreRecords);

    public static final Privilege SavedView =
            new Privilege("SavedView", BusinessActivity.CoreRecords);

    public static final Privilege Report =
            new Privilege("Report", BusinessActivity.CoreRecords);

    public static final Privilege DuplicateDetectionRule =
            new Privilege("DuplicateDetectionRule", BusinessActivity.CoreRecords);

    public static final Privilege DataImport =
            new Privilege("DataImport", BusinessActivity.CoreRecords);

    public static final Privilege DataExport =
            new Privilege("DataExport", BusinessActivity.CoreRecords);

    public static final Privilege DataMap =
            new Privilege("DataMap", BusinessActivity.CoreRecords);

    public static final Privilege OpportunityRelationship =
            new Privilege("OpportunityRelationship", BusinessActivity.CoreRecords);

    public static final Privilege RelationshipRole =
            new Privilege("RelationshipRole", BusinessActivity.CoreRecords);

    public static final Privilege CustomerRelationship =
            new Privilege("CustomerRelationship", BusinessActivity.CoreRecords);

    public static final Privilege PublishEmailTemplate =
            new Privilege("PublishEmailTemplate", BusinessActivity.CoreRecords);

    public static final Privilege AddReportingServicesReports =
            new Privilege("AddReportingServicesReports", BusinessActivity.CoreRecords);

    public static final Privilege PublishReports =
            new Privilege("PublishReports", BusinessActivity.CoreRecords);

    public static final Privilege PublishDuplicateDetectionRules =
            new Privilege("PublishDuplicateDetectionRules", BusinessActivity.CoreRecords);

    // Marketing
    public static final Privilege MarketingList =
            new Privilege("MarketingList", BusinessActivity.Marketing);

    public static final Privilege Campaign =
            new Privilege("Campaign", BusinessActivity.Sales);

    public static final Privilege CreateQuickCampaign =
            new Privilege("CreateQuickCampaign", BusinessActivity.Sales);

    // Sales
    public static final Privilege Product =
            new Privilege("Product", BusinessActivity.Sales);

    public static final Privilege ProductPricing =
            new Privilege("ProductPricing", BusinessActivity.Sales);

    public static final Privilege Competitor =
            new Privilege("Competitor", BusinessActivity.Sales);

    public static final Privilege SalesLiterature =
            new Privilege("SalesLiterature", BusinessActivity.Sales);

    public static final Privilege Quote =
            new Privilege("Quote", BusinessActivity.Sales);

    public static final Privilege Order =
            new Privilege("Order", BusinessActivity.Sales);

    public static final Privilege Invoice =
            new Privilege("Invoice", BusinessActivity.Sales);

    public static final Privilege Territory =
            new Privilege("Territory", BusinessActivity.Sales);

    public static final Privilege OverrideQuotePricing =
            new Privilege("OverrideQuotePricing", BusinessActivity.Sales);

    public static final Privilege OverrideOrderPricing =
            new Privilege("OverrideOrderPricing", BusinessActivity.Sales);

    public static final Privilege OverrideInvoicePricing =
            new Privilege("OverrideInvoicePricing", BusinessActivity.Sales);

    // Service
    public static final Privilege Article =
            new Privilege("Article", BusinessActivity.Service);

    public static final Privilege ArticleTemplate =
            new Privilege("ArticleTemplate", BusinessActivity.Service);

    public static final Privilege Case =
            new Privilege("Case", BusinessActivity.Service);

    public static final Privilege Contract =
            new Privilege("Contract", BusinessActivity.Service);

    public static final Privilege ContractTemplate =
            new Privilege("ContractTemplate", BusinessActivity.Service);

    public static final Privilege PublishArticles =
            new Privilege("PublishArticles", BusinessActivity.Service);

    // Business Management
    public static final Privilege OrganizationSettings =
            new Privilege("OrganizationSettings", BusinessActivity.BusinessManagement);

    public static final Privilege BusinessUnit =
            new Privilege("BusinessUnit", BusinessActivity.BusinessManagement);

    public static final Privilege User =
            new Privilege("User", BusinessActivity.BusinessManagement);

    public static final Privilege UserSettings =
            new Privilege("UserSettings", BusinessActivity.BusinessManagement);

    public static final Privilege Team =
            new Privilege("Team", BusinessActivity.BusinessManagement);

    public static final Privilege Role =
            new Privilege("Role", BusinessActivity.BusinessManagement);

    public static final Privilege License =
            new Privilege("License", BusinessActivity.BusinessManagement);

    public static final Privilege Currency =
            new Privilege("Currency", BusinessActivity.BusinessManagement);

    public static final Privilege AssignRole =
            new Privilege("AssignRole", BusinessActivity.BusinessManagement);

    public static final Privilege AssignTerritoryToUser =
            new Privilege("AssignTerritoryToUser", BusinessActivity.BusinessManagement);

    public static final Privilege BulkEdit =
            new Privilege("BulkEdit", BusinessActivity.BusinessManagement);

    public static final Privilege GoMobile =
            new Privilege("GoMobile", BusinessActivity.BusinessManagement);

    public static final Privilege Print =
            new Privilege("Print", BusinessActivity.BusinessManagement);

    public static final Privilege Export =
            new Privilege("Export", BusinessActivity.BusinessManagement);

    public static final Privilege Merge =
            new Privilege("Merge", BusinessActivity.BusinessManagement);

    public static final Privilege GoOffline =
            new Privilege("GoOffline", BusinessActivity.BusinessManagement);

    public static final Privilege AddressBook =
            new Privilege("AddressBook", BusinessActivity.BusinessManagement);

    public static final Privilege UpdateBusinessClosures =
            new Privilege("UpdateBusinessClosures", BusinessActivity.BusinessManagement);

    public static final Privilege SendEmailAsAnotherUser =
            new Privilege("SendEmailAsAnotherUser", BusinessActivity.BusinessManagement);

    public static final Privilege LanguageSettings =
            new Privilege("LanguageSettings", BusinessActivity.BusinessManagement);

    public static final Privilege SendInvitation =
            new Privilege("SendInvitation", BusinessActivity.BusinessManagement);

    // Service Management
    public static final Privilege Calendar =
            new Privilege("Calendar", BusinessActivity.ServiceManagement);

    public static final Privilege MyWorkHours =
            new Privilege("MyWorkHours", BusinessActivity.ServiceManagement);

    public static final Privilege Service =
            new Privilege("Service", BusinessActivity.ServiceManagement);

    public static final Privilege FacilityEquipment =
            new Privilege("FacilityEquipment", BusinessActivity.ServiceManagement);

    public static final Privilege Site =
            new Privilege("Site", BusinessActivity.ServiceManagement);

    public static final Privilege SearchAvailability =
            new Privilege("SearchAvailability", BusinessActivity.ServiceManagement);

    public static final Privilege BrowseAvailability =
            new Privilege("BrowseAvailability", BusinessActivity.ServiceManagement);

    // Customization
    public static final Privilege Entity =
            new Privilege("Entity", BusinessActivity.Customization);

    public static final Privilege EntityAttribute =
            new Privilege("EntityAttribute", BusinessActivity.Customization);

    public static final Privilege EntityOperation =
            new Privilege("EntityOperation", BusinessActivity.Customization);

    public static final Privilege EntityNotification =
            new Privilege("EntityNotification", BusinessActivity.Customization);

    public static final Privilege Relationship =
            new Privilege("Relationship", BusinessActivity.Customization);

    public static final Privilege Form =
            new Privilege("Form", BusinessActivity.Customization);

    public static final Privilege View =
            new Privilege("View", BusinessActivity.Customization);

    public static final Privilege Workflow =
            new Privilege("Workflow", BusinessActivity.Customization);

    public static final Privilege SystemJob =
            new Privilege("SystemJob", BusinessActivity.Customization);

    // ISV - Independent Software Vendor. A company (or organization) that
    // builds and sells products (as opposed to a Systems Integrator (SI)
    // who implements products)
    public static final Privilege ISVExtensions =
            new Privilege("ISVExtensions", BusinessActivity.Customization);

    public static final Privilege ExecuteWorkflowJob =
            new Privilege("ExecuteWorkflowJob", BusinessActivity.Customization);

    public static final Privilege ExportCustomization =
            new Privilege("ExportCustomization", BusinessActivity.Customization);

    public static final Privilege ImportCustomization =
            new Privilege("ImportCustomization", BusinessActivity.Customization);

    public static final Privilege PublishCustomization =
            new Privilege("PublishCustomization", BusinessActivity.Customization);


    private Role role;
    private String privilegeName;
    private BusinessActivity privilegeCategory;
    private PrivilegeType privilegeType;
    //private Set<AccessRight> inapplicableRights;
    private Map<AccessRight, AccessLevel> accessRights;

    private Object entity;
    private Object entityType;

    public Privilege() {
    }

    public Privilege(
            String privilegeName,
            BusinessActivity privilegeCategory)
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

    /*public Set<AccessRight> getInapplicableRights() {
        return inapplicableRights;
    }

    public void setInapplicableRights(Set<AccessRight> inapplicableRights) {
        this.inapplicableRights = inapplicableRights;
    }*/

    public BusinessActivity getPrivilegeCategory() {
        return privilegeCategory;
    }

    public void setPrivilegeCategory(BusinessActivity privilegeCategory) {
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
