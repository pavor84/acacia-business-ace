/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.purchase;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.DataObjectTypeLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
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

    private List<PurchaseInvoice> getPurchaseInvoices() {
        Query q = em.createNamedQuery(PurchaseInvoice.NQ_FIND_ALL);
        q.setParameter("publisher", acaciaSession.getOrganization());
        q.setParameter("publisherBranch", acaciaSession.getBranch());
        return new ArrayList<PurchaseInvoice>(q.getResultList());
    }

    private PurchaseInvoice newPurchaseInvoice() {
        return esm.newBusinessDocument(DocumentType.PurchaseInvoice);
    }

    private PurchaseInvoice savePurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        esm.persist(em, purchaseInvoice);
        return purchaseInvoice;
    }

    private PurchaseInvoice confirmPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        if(purchaseInvoice.getDocumentNumber() != null ||
                purchaseInvoice.getDocumentDate() != null) {
            throw new PurchaseInvoiceException("The Purchase Invoice is already completed.");
        }

        esm.setDocumentNumber(em, purchaseInvoice);

        return savePurchaseInvoice(purchaseInvoice);
    }

    private boolean deletePurchaseInvoice(PurchaseInvoice purchaseInvoice) {
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

    @Override
    public <E> E newEntity(Class<E> entityClass) {
        if(PURCHASE_INVOICE_CLASS_NAME.equals(entityClass.getName())) {
            return (E)newPurchaseInvoice();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E, I> I newItem(E entity, Class<I> itemClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass) {
        if(PURCHASE_INVOICE_CLASS_NAME.equals(entityClass.getName())) {
            return (List<E>)getPurchaseInvoices();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> E save(E entity) {
        if(entity instanceof PurchaseInvoice) {
            return (E)savePurchaseInvoice((PurchaseInvoice)entity);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> E confirm(E entity) {
        if(entity instanceof PurchaseInvoice) {
            return (E)confirmPurchaseInvoice((PurchaseInvoice)entity);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> E cancel(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> void delete(E entity) {
        if(entity instanceof PurchaseInvoice) {
            deletePurchaseInvoice((PurchaseInvoice)entity);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> EntityProperties getEntityProperties(Class<E> entityClass) {
        EntityProperties entityProperties = esm.getEntityProperties(entityClass);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }
}
