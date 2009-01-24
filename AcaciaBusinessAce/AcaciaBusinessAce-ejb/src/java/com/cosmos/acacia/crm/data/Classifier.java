/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;
import com.cosmos.resource.TextResource;
import java.io.Serializable;
import java.math.BigInteger;

import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "classifiers")
@NamedQueries(
    {
        @NamedQuery
             (
                name = "Classifier.findByParentDataObjectAndDeleted",
                query = "select c from Classifier c where c.parentId = :groupId" +
                        " and c.dataObject.deleted = :deleted" +
                        " and (c.classifierGroup.dataObject.parentDataObjectId = :parentId" +
                        " or c.classifierGroup.isSystemGroup=true or c.classifierGroup.classifierGroupCode='system')"
             ),
        @NamedQuery
             (
                name = "Classifier.findAllAndDeleted",
                query = "select c from Classifier c where" +
                        " c.dataObject.deleted = :deleted" +
                        " and (c.classifierGroup.dataObject.parentDataObjectId = :parentId" +
                        " or c.classifierGroup.isSystemGroup=true or c.classifierGroup.classifierGroupCode='system')"
              ),
        @NamedQuery
            (
                name = "Classifier.findByCode",
                query = "select c from Classifier c where c.classifierCode = :code" +
                        " and c.dataObject.deleted = :deleted" +
                        " and (c.classifierGroup.dataObject.parentDataObjectId = :parentId" +
                        " or c.classifierGroup.isSystemGroup=true or c.classifierGroup.classifierGroupCode='system')"
            )
    }
)
public class Classifier extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;

    public static final Classifier Customer =
            new Classifier();
    public static final Classifier Supplier =
            new Classifier();
    public static final Classifier Producer =
            new Classifier();
    public static final Classifier ShippingAgent =
            new Classifier();
    public static final Classifier Courier =
            new Classifier();
    public static final Classifier Bank =
            new Classifier();
    public static final Classifier Police =
            new Classifier();
    public static final Classifier Customs =
            new Classifier();
    public static final Classifier Court =
            new Classifier();
    public static final Classifier Prosecutor =
            new Classifier();
    public static final Classifier Lawyer =
            new Classifier();
    public static final Classifier SolicitorAssociation =
            new Classifier();
    public static final Classifier RegistryAgency =
            new Classifier();
    public static final Classifier NationalRevenueAgency =
            new Classifier();
    public static final Classifier NationalSocialSecurityAgency =
            new Classifier();
    public static final Classifier PensionFund =
            new Classifier();
    public static final Classifier NationalHealthInsuranceFund =
            new Classifier();
    public static final Classifier HealthInsuranceFund =
            new Classifier();
    public static final Classifier Municipality =
            new Classifier();
    public static final Classifier ServiceProvider =
            new Classifier();
    public static final Classifier InternetServiceProvider =
            new Classifier();
    public static final Classifier ITServiceProvider =
            new Classifier();
    public static final Classifier InsuranceCompany =
            new Classifier();
    public static final Classifier InsuranceAgent =
            new Classifier();
    public static final Classifier Hospital =
            new Classifier();
    public static final Classifier Polyclinic =
            new Classifier();
    public static final Classifier MedicalCenter =
            new Classifier();
    public static final Classifier DoctorOfMedicine =
            new Classifier();
    public static final Classifier Dentist =
            new Classifier();
    public static final Classifier PrimarySchool =
            new Classifier();
    public static final Classifier SecondarySchool =
            new Classifier();
    public static final Classifier HighSchool =
            new Classifier();
    public static final Classifier HighTechnicalSchool =
            new Classifier();
    public static final Classifier LanguageSecondarySchool =
            new Classifier();
    public static final Classifier College =
            new Classifier();
    public static final Classifier University =
            new Classifier();
    public static final Classifier TechnicalUniversity =
            new Classifier();
    public static final Classifier AdvertisementAgency =
            new Classifier();
    public static final Classifier Entertainment =
            new Classifier();
    public static final Classifier Hotel =
            new Classifier();
    public static final Classifier HotelApartment =
            new Classifier();
    public static final Classifier RealEstateAgency =
            new Classifier();
    public static final Classifier RentACar =
            new Classifier();

    public static final Map<String, Classifier> ConstantsMap =
            new TreeMap<String, Classifier>();

    static {
        Customer.setClassifierCode("Customer");
        Customer.setClassifierName("Customer/Client classifier.");
        Customer.setDescription("With this classifier are classified the customers.");

        Supplier.setClassifierCode("Supplier");
        Supplier.setClassifierName("Supplier");
        Supplier.setDescription("Supplier");

        Producer.setClassifierCode("Producer");
        Producer.setClassifierName("Producer");
        Producer.setDescription("Producer");

        ShippingAgent.setClassifierCode("Shipping Agent");
        ShippingAgent.setClassifierName("Shipping Agent");
        ShippingAgent.setDescription("Shipping Agent");

        Courier.setClassifierCode("Courier");
        Courier.setClassifierName("Courier");
        Courier.setDescription("Courier");

        Bank.setClassifierCode("Bank");
        Bank.setClassifierName("Bank");
        Bank.setDescription("Bank");

        Police.setClassifierCode("Police");
        Police.setClassifierName("Police");
        Police.setDescription("Police");

        Customs.setClassifierCode("Customs");
        Customs.setClassifierName("Customs");
        Customs.setDescription("Customs");

        Court.setClassifierCode("Court");
        Court.setClassifierName("Court");
        Court.setDescription("Court");

        Prosecutor.setClassifierCode("Prosecutor");
        Prosecutor.setClassifierName("Prosecutor");
        Prosecutor.setDescription("Prosecutor");

        Lawyer.setClassifierCode("Lawyer");
        Lawyer.setClassifierName("Lawyer");
        Lawyer.setDescription("Lawyer");

        SolicitorAssociation.setClassifierCode("SolicitorAssociation");
        SolicitorAssociation.setClassifierName("Solicitor Association");
        SolicitorAssociation.setDescription("Solicitor Association");

        RegistryAgency.setClassifierCode("RegistryAgency");
        RegistryAgency.setClassifierName("Registry Agency");
        RegistryAgency.setDescription("Registry Agency");

        NationalRevenueAgency.setClassifierCode("NationalRevenueAgency");
        NationalRevenueAgency.setClassifierName("National Revenue Agency");
        NationalRevenueAgency.setDescription("National Revenue Agency");

        NationalSocialSecurityAgency.setClassifierCode("NationalSocialSecurityAgency");
        NationalSocialSecurityAgency.setClassifierName("National Social Security Agency");
        NationalSocialSecurityAgency.setDescription("National Social Security Agency");

        PensionFund.setClassifierCode("PensionFund");
        PensionFund.setClassifierName("Pension Fund");
        PensionFund.setDescription("Pension Fund");

        NationalHealthInsuranceFund.setClassifierCode("NationalHealthInsuranceFund");
        NationalHealthInsuranceFund.setClassifierName("National Health Insurance Fund");
        NationalHealthInsuranceFund.setDescription("National Health Insurance Fund");

        HealthInsuranceFund.setClassifierCode("HealthInsuranceFund");
        HealthInsuranceFund.setClassifierName("Health Insurance Fund");
        HealthInsuranceFund.setDescription("Health Insurance Fund");

        Municipality.setClassifierCode("Municipality");
        Municipality.setClassifierName("Municipality");
        Municipality.setDescription("Municipality");

        ServiceProvider.setClassifierCode("ServiceProvider");
        ServiceProvider.setClassifierName("Service Provider");
        ServiceProvider.setDescription("Service Provider");

        InternetServiceProvider.setClassifierCode("InternetServiceProvider");
        InternetServiceProvider.setClassifierName("Internet Service Provider");
        InternetServiceProvider.setDescription("Internet Service Provider");

        ITServiceProvider.setClassifierCode("ITServiceProvider");
        ITServiceProvider.setClassifierName("IT Service Provider");
        ITServiceProvider.setDescription("IT Service Provider");

        InsuranceCompany.setClassifierCode("InsuranceCompany");
        InsuranceCompany.setClassifierName("Insurance Company");
        InsuranceCompany.setDescription("Insurance Company");

        InsuranceAgent.setClassifierCode("InsuranceAgent");
        InsuranceAgent.setClassifierName("Insurance Agent");
        InsuranceAgent.setDescription("Insurance Agent");

        Hospital.setClassifierCode("Hospital");
        Hospital.setClassifierName("Hospital");
        Hospital.setDescription("Hospital");

        Polyclinic.setClassifierCode("Polyclinic");
        Polyclinic.setClassifierName("Polyclinic");
        Polyclinic.setDescription("Polyclinic");

        MedicalCenter.setClassifierCode("MedicalCenter");
        MedicalCenter.setClassifierName("Medical Center");
        MedicalCenter.setDescription("Medical Center");

        DoctorOfMedicine.setClassifierCode("DoctorOfMedicine");
        DoctorOfMedicine.setClassifierName("Doctor of Medicine");
        DoctorOfMedicine.setDescription("Doctor of Medicine");

        Dentist.setClassifierCode("Dentist");
        Dentist.setClassifierName("Dentist");
        Dentist.setDescription("Dentist");

        PrimarySchool.setClassifierCode("PrimarySchool");
        PrimarySchool.setClassifierName("Primary School");
        PrimarySchool.setDescription("Primary School");

        SecondarySchool.setClassifierCode("SecondarySchool");
        SecondarySchool.setClassifierName("Secondary School");
        SecondarySchool.setDescription("Secondary School");

        HighSchool.setClassifierCode("HighSchool");
        HighSchool.setClassifierName("High School");
        HighSchool.setDescription("High School");

        HighTechnicalSchool.setClassifierCode("HighTechnicalSchool");
        HighTechnicalSchool.setClassifierName("High Technical School");
        HighTechnicalSchool.setDescription("High Technical School");

        LanguageSecondarySchool.setClassifierCode("LanguageSecondarySchool");
        LanguageSecondarySchool.setClassifierName("Language Secondary School");
        LanguageSecondarySchool.setDescription("Language Secondary School");

        College.setClassifierCode("College");
        College.setClassifierName("College");
        College.setDescription("College");

        University.setClassifierCode("University");
        University.setClassifierName("University");
        University.setDescription("University");

        TechnicalUniversity.setClassifierCode("TechnicalUniversity");
        TechnicalUniversity.setClassifierName("Technical University");
        TechnicalUniversity.setDescription("Technical University");

        AdvertisementAgency.setClassifierCode("AdvertisementAgency");
        AdvertisementAgency.setClassifierName("Advertisement Agency");
        AdvertisementAgency.setDescription("Advertisement Agency");

        Entertainment.setClassifierCode("Entertainment");
        Entertainment.setClassifierName("Entertainment");
        Entertainment.setDescription("Entertainment");

        Hotel.setClassifierCode("Hotel");
        Hotel.setClassifierName("Hotel");
        Hotel.setDescription("Hotel");

        HotelApartment.setClassifierCode("HotelApartment");
        HotelApartment.setClassifierName("Hotel Apartment");
        HotelApartment.setDescription("Hotel Apartment");

        RealEstateAgency.setClassifierCode("RealEstateAgency");
        RealEstateAgency.setClassifierName("Real Estate Agency");
        RealEstateAgency.setDescription("Real Estate Agency");

        RentACar.setClassifierCode("RentACar");
        RentACar.setClassifierName("Rent a Car");
        RentACar.setDescription("Rent a Car");

        setClassifier(Customer);
        setClassifier(Customer);
        setClassifier(Supplier);
        setClassifier(Producer);
        setClassifier(ShippingAgent);
        setClassifier(Courier);
        setClassifier(Bank);
        setClassifier(Police);
        setClassifier(Customs);
        setClassifier(Court);
        setClassifier(Prosecutor);
        setClassifier(Lawyer);
        setClassifier(SolicitorAssociation);
        setClassifier(RegistryAgency);
        setClassifier(NationalRevenueAgency);
        setClassifier(NationalSocialSecurityAgency);
        setClassifier(PensionFund);
        setClassifier(NationalHealthInsuranceFund);
        setClassifier(HealthInsuranceFund);
        setClassifier(Municipality);
        setClassifier(ServiceProvider);
        setClassifier(InternetServiceProvider);
        setClassifier(ITServiceProvider);
        setClassifier(InsuranceCompany);
        setClassifier(InsuranceAgent);
        setClassifier(Hospital);
        setClassifier(Polyclinic);
        setClassifier(MedicalCenter);
        setClassifier(DoctorOfMedicine);
        setClassifier(Dentist);
        setClassifier(PrimarySchool);
        setClassifier(SecondarySchool);
        setClassifier(HighSchool);
        setClassifier(HighTechnicalSchool);
        setClassifier(LanguageSecondarySchool);
        setClassifier(College);
        setClassifier(University);
        setClassifier(TechnicalUniversity);
        setClassifier(AdvertisementAgency);
        setClassifier(Entertainment);
        setClassifier(Hotel);
        setClassifier(HotelApartment);
        setClassifier(RealEstateAgency);
        setClassifier(RentACar);
    }

    private static final void setClassifier(Classifier classifier) {
        ConstantsMap.put(classifier.getClassifierCode(), classifier);
    }


    @Id
    @Column(name = "classifier_id", nullable = false)
    @Property(title="Address Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger classifierId;

    @Column(name = "parent_id")
    @Property(title="Parent Id", editable=false, readOnly=true, visible=false, hidden=true)
    private BigInteger parentId;

    @Column(name = "classifier_code", nullable = false)
    @Property(title="Code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=32))
    private String classifierCode;

    @Column(name = "classifier_name", nullable = false)
    @Property(title="Name", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=128))
    private String classifierName;

    @JoinColumn(name = "parent_id", referencedColumnName = "classifier_group_id", insertable=false, updatable=false, nullable=false)
    @ManyToOne
    @Property(title="Group", customDisplay="${classifierGroup.classifierGroupName}")
    private ClassifierGroup classifierGroup;

    @Column(name = "description")
    @Property(title="Description")
    private String description;

    @JoinColumn(name = "classifier_id", referencedColumnName = "data_object_id", insertable = false, updatable = false)
    @OneToOne
    private DataObject dataObject;


    public Classifier() {
    }

    public Classifier(BigInteger classifierId) {
        this.classifierId = classifierId;
    }

    public Classifier(BigInteger classifierId, String classifierCode, String classifierName) {
        this.classifierId = classifierId;
        this.classifierCode = classifierCode;
        this.classifierName = classifierName;
    }

    public BigInteger getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(BigInteger classifierId) {
        this.classifierId = classifierId;
    }

    @Override
    public BigInteger getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getClassifierCode() {
        return classifierCode;
    }

    public void setClassifierCode(String classifierCode) {
        this.classifierCode = classifierCode;
    }

    public String getClassifierName() {
        return classifierName;
    }

    public void setClassifierName(String classifierName) {
        this.classifierName = classifierName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClassifierGroup getClassifierGroup() {
        return classifierGroup;
    }

    public void setClassifierGroup(ClassifierGroup classifierGroup) {
        this.classifierGroup = classifierGroup;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classifierId != null ? classifierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classifier)) {
            return false;
        }
        Classifier other = (Classifier) object;
        if ((this.classifierId == null && other.classifierId != null) || (this.classifierId != null && !this.classifierId.equals(other.classifierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.Classifier[classifierId=" + classifierId +
                ", code=" + classifierCode + "]";
    }

    @Override
    public BigInteger getId() {
        return classifierId;
    }

    @Override
    public void setId(BigInteger id) {
        this.classifierId = id;
    }

    @Override
    public String getInfo() {
        return getClassifierName();
    }

    @Override
    public String toShortText() {
        return getClassifierName();
    }

    @Override
    public String toText() {
        return null;
    }
}
