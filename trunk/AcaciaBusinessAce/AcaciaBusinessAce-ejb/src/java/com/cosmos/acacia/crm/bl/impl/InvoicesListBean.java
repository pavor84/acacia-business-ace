package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.DeliveryType;
import com.cosmos.acacia.crm.enums.DocumentDeliveryMethod;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.crm.enums.InvoiceType;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.enums.PaymentTerm;
import com.cosmos.acacia.crm.enums.PaymentType;
import com.cosmos.acacia.crm.enums.TransportationMethod;
import com.cosmos.acacia.crm.enums.VatCondition;
import com.cosmos.beansbinding.EntityProperties;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author Radoslav Lozanov
 */
@Stateless
public class InvoicesListBean implements InvoicesListRemote, InvoicesListLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    @EJB
    private DatabaseResourceLocal databaseResource;

    @PostConstruct
    private void postConstruct()
    {
        System.out.println("postConstruct()");
    }

    public List<Invoice> getInvoices(DataObject parent) {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("Invoice.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parent.getDataObjectId());
        }
        else
        {
            q = em.createNamedQuery("Invoice.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<Invoice>(q.getResultList());
    }

    public List<InvoiceItem> getInvoiceItems(DataObject parent) {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("InvoiceItem.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parent.getDataObjectId());
        }
        else
        {
            q = em.createNamedQuery("InvoiceItem.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<InvoiceItem>(q.getResultList());
    }

    public Invoice newInvoice() {
        return new Invoice();
    }

    public Invoice saveInvoice(Invoice invoice) {
        esm.persist(em, invoice);
        return invoice;
    }

    public int deleteInvoice(Invoice invoice) {
        return esm.remove(em, invoice);
    }

    public EntityProperties getInvoiceEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(Invoice.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public List<DbResource> getCurrencies() {
        return Currency.getDbResources();
    }

    public List<DbResource> getDeliveryTypes() {
        return DeliveryType.getDbResources();
    }

    public List<DbResource> getDocumentDeliveryMethods() {
        return DocumentDeliveryMethod.getDbResources();
    }

    public List<DbResource> getInvoiceStatuses() {
        return InvoiceStatus.getDbResources();
    }

    public List<DbResource> getInvoiceTypes() {
        return InvoiceType.getDbResources();
    }

    public List<DbResource> getPaymentTerms() {
        return PaymentTerm.getDbResources();
    }

    public List<DbResource> getPaymentTypes() {
        return PaymentType.getDbResources();
    }

    public List<DbResource> getTransportationMethods() {
        return TransportationMethod.getDbResources();
    }

    public List<DbResource> getVatConditions() {
        return VatCondition.getDbResources();
    }

}
