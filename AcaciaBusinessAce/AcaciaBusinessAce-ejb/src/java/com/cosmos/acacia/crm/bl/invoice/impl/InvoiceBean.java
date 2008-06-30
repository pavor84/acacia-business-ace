/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.invoice.impl;

import com.cosmos.acacia.crm.bl.impl.DataObjectLinkLocal;
import com.cosmos.acacia.crm.bl.impl.DatabaseResourceLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.enums.InvoiceStatus;
import com.cosmos.acacia.crm.enums.InvoiceType;
import com.cosmos.acacia.crm.enums.VatCondition;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author rlozanov
 */
@Stateless
public class InvoiceBean implements InvoiceLocal {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    @EJB
    private DatabaseResourceLocal databaseResource;
    
    @EJB
    private DataObjectLinkLocal dataObjectLinkBean;

    public Invoice newInvoice() {
        Invoice invoice = new Invoice();
        invoice.setStatusId(InvoiceStatus.Open.getDbResource());
        
        // Dumy invoice number
        // TODO: invoice number calculation
        invoice.setInvoiceNumber(calculateNextInvoiceNumber().longValue());
        
        // set creation Time Note: for now server time will be used
        invoice.setCreationTime(new Date());
        
        invoice.setVatPercent(new BigDecimal(20));
        invoice.setVatValue(calculateVatValue(invoice));

        return invoice;
    }

    public Invoice saveInvoice(Invoice invoice) {
        if (invoice.getShippingAgentLink() != null &&
            invoice.getShippingAgentLink().getDataObjectLinkId() == null) {
            dataObjectLinkBean.saveDataObjectLink(invoice.getShippingAgentLink());
        }
        
        if (invoice.getInvoiceType().equals(InvoiceType.VatInvoice.getDbResource())) {
            invoice.setVatCondition(VatCondition.VatPayable.getDbResource());
        } else {
            invoice.setVatCondition(VatCondition.NoVat.getDbResource());
        }
        // Ugly hack TODO: fix with real sub value and value, vat percent
        invoice.setInvoiceValue(invoice.getTotalInvoiceValue());
        invoice.setInvoiceSubValue(invoice.getTotalInvoiceValue());
        
        esm.persist(em, invoice);
        return invoice;
    }

    public EntityProperties getInvoiceEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(Invoice.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public BigDecimal calculateVatValue(Invoice invoice) {
        
        return new BigDecimal(30);
    }

    /**
     * TODO: invoice number calculation implementation. Need to be organization/company specific
     * @return invoice number
     */
    public Long calculateNextInvoiceNumber() {
        return new Long(System.currentTimeMillis());
    }
    
}
