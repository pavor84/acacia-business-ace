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
import java.util.UUID;

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
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "classifiers"
/*
CREATE UNIQUE INDEX uix_classifiers_business_partner_classifier_code
  ON classifiers
  USING btree
  (business_partner_id, lower(classifier_code::text));
*/
)
@NamedQueries({
    @NamedQuery(
        name =  Classifier.NQ_FIND_ALL,
        query = "select t from Classifier t" +
                " where" +
                "  t.businessPartnerId = :businessPartnerId" +
                " order by t.classifierGroup, t.classifierCode"
    ),
    @NamedQuery(
        name =  Classifier.NQ_FIND_BY_GROUP,
        query = "select t from Classifier t" +
                " where" +
                "  t.businessPartnerId = :businessPartnerId" +
                "  and t.classifierGroup = :classifierGroup" +
                " order by t.classifierCode"
    ),
    @NamedQuery(
        name =  Classifier.NQ_FIND_BY_CODE,
        query = "select t from Classifier t" +
                " where" +
                "  t.businessPartnerId = :businessPartnerId" +
                "  and lower(t.classifierCode) = lower(:classifierCode)"
    )
})
public class Classifier extends DataObjectBean implements Serializable, TextResource {

    private static final long serialVersionUID = 1L;
    //
    private static final String CLASS_NAME = "Classifier";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_CODE = CLASS_NAME + ".findByCode";
    public static final String NQ_FIND_BY_GROUP = CLASS_NAME + ".findByGroup";
    //
    public static final Classifier Employee = new Classifier();
    public static final Classifier Cashier = new Classifier();
    public static final Classifier Customer = new Classifier();
    public static final Classifier Supplier = new Classifier();
    public static final Classifier Producer = new Classifier();
    public static final Classifier ShippingAgent = new Classifier();
    public static final Classifier Courier = new Classifier();
    public static final Classifier Bank = new Classifier();
    public static final Classifier Police = new Classifier();
    public static final Classifier PassportOffice = new Classifier();
    public static final Classifier Customs = new Classifier();
    public static final Classifier Court = new Classifier();
    public static final Classifier Prosecutor = new Classifier();
    public static final Classifier Lawyer = new Classifier();
    public static final Classifier SolicitorAssociation = new Classifier();
    public static final Classifier RegistryAgency = new Classifier();
    public static final Classifier NationalRevenueAgency = new Classifier();
    public static final Classifier NationalSocialSecurityAgency = new Classifier();
    public static final Classifier PensionFund = new Classifier();
    public static final Classifier NationalHealthInsuranceFund = new Classifier();
    public static final Classifier HealthInsuranceFund = new Classifier();
    public static final Classifier Municipality = new Classifier();
    public static final Classifier ServiceProvider = new Classifier();
    public static final Classifier InternetServiceProvider = new Classifier();
    public static final Classifier ITServiceProvider = new Classifier();
    public static final Classifier InsuranceCompany = new Classifier();
    public static final Classifier InsuranceAgent = new Classifier();
    public static final Classifier Hospital = new Classifier();
    public static final Classifier Polyclinic = new Classifier();
    public static final Classifier MedicalCenter = new Classifier();
    public static final Classifier DoctorOfMedicine = new Classifier();
    public static final Classifier Dentist = new Classifier();
    public static final Classifier PrimarySchool = new Classifier();
    public static final Classifier SecondarySchool = new Classifier();
    public static final Classifier HighSchool = new Classifier();
    public static final Classifier HighTechnicalSchool = new Classifier();
    public static final Classifier LanguageSecondarySchool = new Classifier();
    public static final Classifier College = new Classifier();
    public static final Classifier University = new Classifier();
    public static final Classifier TechnicalUniversity = new Classifier();
    public static final Classifier AdvertisementAgency = new Classifier();
    public static final Classifier Entertainment = new Classifier();
    public static final Classifier Hotel = new Classifier();
    public static final Classifier HotelApartment = new Classifier();
    public static final Classifier RealEstateAgency = new Classifier();
    public static final Classifier RentACar = new Classifier();

    public static final Map<String, Classifier> ConstantsMap =
            new TreeMap<String, Classifier>();

    static {
        setClassifier(Employee, "Employee");
        setClassifier(Cashier, "Cashier");
        setClassifier(Customer, "Customer", "Customer/Client", "With this classifier are classified the customers.");
        setClassifier(Supplier, "Supplier");
        setClassifier(Producer, "Producer");
        setClassifier(ShippingAgent, "ShippingAgent", "Shipping Agent");
        setClassifier(Courier, "Courier");
        setClassifier(Bank, "Bank");
        setClassifier(Police, "Police");
        setClassifier(PassportOffice, "Passport Office");
        setClassifier(Customs, "Customs");
        setClassifier(Court, "Court");
        setClassifier(Prosecutor, "Prosecutor");
        setClassifier(Lawyer, "Lawyer");
        setClassifier(SolicitorAssociation, "SolicitorAssociation", "Solicitor Association");
        setClassifier(RegistryAgency, "RegistryAgency", "Registry Agency");
        setClassifier(NationalRevenueAgency, "NationalRevenueAgency", "National Revenue Agency");
        setClassifier(NationalSocialSecurityAgency, "NationalSocialSecurityAgency", "National Social Security Agency");
        setClassifier(PensionFund, "PensionFund", "Pension Fund");
        setClassifier(NationalHealthInsuranceFund, "NationalHealthInsuranceFund", "National Health Insurance Fund");
        setClassifier(HealthInsuranceFund, "HealthInsuranceFund", "Health Insurance Fund");
        setClassifier(Municipality, "Municipality");
        setClassifier(ServiceProvider, "ServiceProvider", "Service Provider");
        setClassifier(InternetServiceProvider, "InternetServiceProvider", "Internet Service Provider");
        setClassifier(ITServiceProvider, "ITServiceProvider", "IT Service Provider");
        setClassifier(InsuranceCompany, "InsuranceCompany", "Insurance Company");
        setClassifier(InsuranceAgent, "InsuranceAgent", "Insurance Agent");
        setClassifier(Hospital, "Hospital");
        setClassifier(Polyclinic, "Polyclinic");
        setClassifier(MedicalCenter, "MedicalCenter", "Medical Center");
        setClassifier(DoctorOfMedicine, "DoctorOfMedicine", "Doctor of Medicine");
        setClassifier(Dentist, "Dentist");
        setClassifier(PrimarySchool, "PrimarySchool", "Primary School");
        setClassifier(SecondarySchool, "SecondarySchool", "Secondary School");
        setClassifier(HighSchool, "HighSchool", "High School");
        setClassifier(HighTechnicalSchool, "HighTechnicalSchool", "High Technical School");
        setClassifier(LanguageSecondarySchool, "LanguageSecondarySchool", "Language Secondary School");
        setClassifier(College, "College");
        setClassifier(University, "University");
        setClassifier(TechnicalUniversity, "TechnicalUniversity", "Technical University");
        setClassifier(AdvertisementAgency, "AdvertisementAgency", "Advertisement Agency");
        setClassifier(Entertainment, "Entertainment");
        setClassifier(Hotel, "Hotel");
        setClassifier(HotelApartment, "HotelApartment", "Hotel Apartment");
        setClassifier(RealEstateAgency, "RealEstateAgency", "Real Estate Agency");
        setClassifier(RentACar, "RentACar", "Rent a Car");
    }

    private static final void setClassifier(
            Classifier classifier,
            String classifierCode) {
        setClassifier(classifier, classifierCode, classifierCode);
    }

    private static final void setClassifier(
            Classifier classifier,
            String classifierCode,
            String classifierName) {
        setClassifier(classifier, classifierCode, classifierName, classifierName);
    }

    private static final void setClassifier(
            Classifier classifier,
            String classifierCode,
            String classifierName,
            String description) {
        classifier.setClassifierCode(classifierCode);
        classifier.setClassifierName(classifierName);
        classifier.setDescription(description);
        ConstantsMap.put(classifierCode, classifier);
    }


    @Id
    @Column(name = "classifier_id", nullable = false)
    @Type(type="uuid")
    @Property(title="Address Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID classifierId;

    @Column(name = "business_partner_id", nullable=false)
    @Type(type="uuid")
    @Property(title="Business Partner Id", editable=false, readOnly=true, visible=false, hidden=true)
    private UUID businessPartnerId;

    @Column(name = "classifier_code", nullable = false)
    @Property(title="Code", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=32))
    private String classifierCode;

    @Column(name = "classifier_name", nullable = false)
    @Property(title="Name", propertyValidator=
        @PropertyValidator(validationType=ValidationType.LENGTH, maxLength=128))
    private String classifierName;

    @JoinColumn(
        name = "classifier_group_id",
        referencedColumnName = "classifier_group_id",
        nullable=false)
    @ManyToOne(optional = false)
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

    public Classifier(UUID classifierId) {
        this.classifierId = classifierId;
    }

    public Classifier(UUID classifierId, String classifierCode, String classifierName) {
        this.classifierId = classifierId;
        this.classifierCode = classifierCode;
        this.classifierName = classifierName;
    }

    public UUID getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(UUID classifierId) {
        this.classifierId = classifierId;
    }

    public UUID getBusinessPartnerId() {
        return businessPartnerId;
    }

    public void setBusinessPartnerId(UUID businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
    }

    @Override
    public UUID getParentId() {
        return getBusinessPartnerId();
    }

    @Override
    public void setParentId(UUID parentId) {
        setBusinessPartnerId(parentId);
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("; code=").append(classifierCode);
        sb.append(", group=").append(classifierGroup);
        return sb.toString();
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
    public UUID getId() {
        return getClassifierId();
    }

    @Override
    public void setId(UUID id) {
        setClassifierId(id);
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
