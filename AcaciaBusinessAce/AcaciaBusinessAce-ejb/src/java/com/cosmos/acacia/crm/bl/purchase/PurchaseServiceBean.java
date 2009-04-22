/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.purchase;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.DataObjectTypeLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoiceItem;
import com.cosmos.acacia.crm.enums.DocumentStatus;
import com.cosmos.acacia.crm.enums.DocumentType;
import com.cosmos.beansbinding.EntityProperties;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author Miro
 */
@Stateless
public class PurchaseServiceBean implements PurchaseServiceRemote, PurchaseServiceLocal {

    private static final String PURCHASE_INVOICE_CLASS_NAME =
            PurchaseInvoice.class.getName();

    @PersistenceContext
    private EntityManager em;
    @EJB
    private AcaciaSessionLocal acaciaSession;
    @EJB
    private EntityStoreManagerLocal esm;
    @EJB
    private DataObjectTypeLocal dotLocal;

    @Override
    public EntityProperties getEntityProperties(Class entityClass) {
        EntityProperties entityProperties = esm.getEntityProperties(entityClass);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public List<PurchaseInvoice> getPurchaseInvoices() {
        Query q = em.createNamedQuery(PurchaseInvoice.NQ_FIND_ALL);
        q.setParameter("consignee", acaciaSession.getOrganization());
        q.setParameter("consigneeBranch", acaciaSession.getBranch());
        return new ArrayList<PurchaseInvoice>(q.getResultList());
    }

    @Override
    public PurchaseInvoice newPurchaseInvoice() {
        return esm.newBusinessDocument(DocumentType.PurchaseInvoice);
    }

    @Override
    public PurchaseInvoice savePurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        esm.persist(em, purchaseInvoice);
        return purchaseInvoice;
    }

    @Override
    public PurchaseInvoice completePurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        if(purchaseInvoice.getDocumentNumber() != null ||
                purchaseInvoice.getDocumentDate() != null) {
            throw new PurchaseInvoiceException("The Purchase Invoice is already completed.");
        }

        esm.setDocumentNumber(em, purchaseInvoice);

        return savePurchaseInvoice(purchaseInvoice);
    }

    @Override
    public boolean deletePurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PurchaseInvoiceItem> getPurchaseInvoiceItems(PurchaseInvoice purchaseInvoice) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PurchaseInvoiceItem newPurchaseInvoiceItem(PurchaseInvoice purchaseInvoice) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PurchaseInvoiceItem savePurchaseInvoiceItem(PurchaseInvoiceItem invoiceItem) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deletePurchaseInvoiceItem(PurchaseInvoiceItem invoiceItem) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
 
}
