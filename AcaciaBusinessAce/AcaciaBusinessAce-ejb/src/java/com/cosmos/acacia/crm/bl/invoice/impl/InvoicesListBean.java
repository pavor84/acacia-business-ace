package com.cosmos.acacia.crm.bl.invoice.impl;

import com.cosmos.acacia.crm.bl.impl.*;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectLink;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.DeliveryType;
import com.cosmos.acacia.crm.enums.DocumentDeliveryMethod;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.crm.enums.InvoiceType;
import com.cosmos.acacia.crm.enums.PaymentTerm;
import com.cosmos.acacia.crm.enums.PaymentType;
import com.cosmos.acacia.crm.enums.TransportationMethod;
import com.cosmos.acacia.crm.enums.VatCondition;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
    
    @EJB
    private DataObjectLinkLocal dataObjectLinkBean;
    
    @EJB
    private InvoiceItemsListLocal invoiceItemsListBean;
    @EJB
    private InvoiceLocal invoiceBean;

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
        return invoiceItemsListBean.getInvoiceItems(parent);
    }

    public Invoice newInvoice() {
        return invoiceBean.newInvoice();
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceBean.saveInvoice(invoice);
    }

    public int deleteInvoice(Invoice invoice) {
        return esm.remove(em, invoice);
    }

    public EntityProperties getInvoiceEntityProperties() {
        return invoiceBean.getInvoiceEntityProperties();
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

    public DataObjectLink newDataObjectLink(DataObjectBean linkeObject) {
        return dataObjectLinkBean.newDataObjectLink(linkeObject);
    }

    public BigDecimal calculateVatValue(Invoice invocie) {
        return invoiceBean.calculateVatValue(invocie);
    }

}
