/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author miro
 */
public enum OrganizationType
    implements DatabaseResource
{
    NationalAgency("National Agency", "Government Organization"),
    Agency("Agency", "Non-government Organization"),
    PrivateLimitedCompany(
            "Private Limited Company",
            "liability limited by share capital",
            "Ltd."),
    PublicLimitedCompany(
            "Public Limited Company",
            "liability limited by shares which are traded publicly",
            "PLC"),
    CompanyWithLimitedLiability(
            "Company with Limited Liability",
            "Similar to Limited Liability Company (LLC)",
            "GmbH"),
    StockCorporation(
            "Stock Corporation",
            "Equivalent to Public Limited Company (PLC) or incorporated company",
            "AG"),
    Incorporated(
            "Incorporated",
            "Incorporated and the abbreviations Inc. are the only terms universally accepted by all 51 corporation chartering agencies in the United States.",
            "Inc."),
    Corporation(
            "Corporation",
            "Corporation and the abbreviations Corp. are the only terms universally accepted by all 51 corporation chartering agencies in the United States.",
            "Corp."),
    SoleTrader(
            "Sole Trader",
            "Used mainly for small businesses, or for a single person (who has liability). The company is not a separate legal entity from the individual",
            "ET"),
    GovernmentOrganization(
            "Government Organization",
            "Police, Army, etc."),
    MunicipalOrganization(
            "Municipal Organization",
            "Municipal Organization"),
    GeneralPartnership(
            "General Partnership",
            "In Bulgaria, this is a legal person",
            "SD"),
    LimitedPartnership(
            "Limited Partnership",
            "Since it is not added to the name of a company",
            "KD"),
    SingleMemberLimitedLiabilityCompany(
            "Single-Member Limited Liability Company",
            "Similar to Ltd and GmbH but with only a single owner",
            "EOOD", "LLC"),
    LimitedLiabilityCompany(
            "Limited Liability Company",
            "Similar to Ltd and GmbH",
            "OOD", "LLC"),
    JointStockCompany(
            "Joint Stock Company",
            "Similar to PLC and AG",
            "AD", "JSCO"),
    SingleShareholderJointStockCompany(
            "Single-Shareholder Joint-Stock Company",
            "Similar to PLC and AG, but where only one person holds all the shares. If they decide to sell any shares, the company becomes a normal AD",
            "EAD"),
    PartnershipLimited(
            "Partnership Limited",
            "Partnership Limited by Shares",
            "KDA")
    ;

    static
    {
        CompanyWithLimitedLiability.similarOrganizations = Arrays.asList(LimitedLiabilityCompany, SingleMemberLimitedLiabilityCompany);
        SingleMemberLimitedLiabilityCompany.similarOrganizations = Arrays.asList(PrivateLimitedCompany, CompanyWithLimitedLiability);
        LimitedLiabilityCompany.similarOrganizations = Arrays.asList(PrivateLimitedCompany, CompanyWithLimitedLiability);
        JointStockCompany.similarOrganizations = Arrays.asList(PublicLimitedCompany, StockCorporation);
        SingleShareholderJointStockCompany.similarOrganizations = Arrays.asList(PublicLimitedCompany, StockCorporation);
    }

    private OrganizationType(
            String organizationName,
            String description,
            String ... abbreviations)
    {
        this.organizationName = organizationName;
        this.description = description;
        if(abbreviations != null && abbreviations.length > 0)
            this.abbreviations = Arrays.asList(abbreviations);
        else
            this.abbreviations = Collections.emptyList();
        similarOrganizations = Collections.emptyList();
    }

    private String organizationName;
    private String description;
    private List<String> abbreviations;
    private DbResource dbResource;
    private List<OrganizationType> similarOrganizations;

    public String getOrganizationName() {
        return organizationName;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAbbreviations()
    {
        return abbreviations;
    }

    public List<OrganizationType> getSimilarOrganizations() {
        return similarOrganizations;
    }

    public DbResource getDbResource() {
        return dbResource;
    }

    public void setDbResource(DbResource dbResource) {
        this.dbResource = dbResource;
    }

    public String toShortText()
    {
        if(abbreviations != null && abbreviations.size() > 0)
            return abbreviations.get(0);

        return getOrganizationName();
    }

    public String toText()
    {
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
            dbResources = new ArrayList<DbResource>(OrganizationType.values().length);

            for(OrganizationType unit : OrganizationType.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
}
