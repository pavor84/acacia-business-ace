/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.purchase;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoiceItem;
import com.cosmos.acacia.crm.enums.DocumentType;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigDecimal;
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
    private static final String PURCHASE_INVOICE_ITEM_CLASS_NAME =
            PurchaseInvoiceItem.class.getName();

    @PersistenceContext
    private EntityManager em;
    @EJB
    private AcaciaSessionLocal acaciaSession;
    @EJB
    private EntityStoreManagerLocal esm;

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

    private void deletePurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        esm.remove(em, purchaseInvoice);
    }

    private List<PurchaseInvoiceItem> getPurchaseInvoiceItems(PurchaseInvoice purchaseInvoice) {
        Query q = em.createNamedQuery(PurchaseInvoiceItem.NQ_FIND_ALL);
        q.setParameter("invoice", purchaseInvoice);
        return new ArrayList<PurchaseInvoiceItem>(q.getResultList());
    }

    private PurchaseInvoiceItem newPurchaseInvoiceItem(PurchaseInvoice purchaseInvoice) {
        PurchaseInvoiceItem invoiceItem = new PurchaseInvoiceItem();
        invoiceItem.setInvoice(purchaseInvoice);
        invoiceItem.setMeasureUnit(MeasurementUnit.Piece.getDbResource());
        invoiceItem.setReceivedQuantity(BigDecimal.ZERO);
        return invoiceItem;
    }

    private PurchaseInvoiceItem savePurchaseInvoiceItem(PurchaseInvoiceItem invoiceItem) {
        esm.persist(em, invoiceItem);
        return invoiceItem;
    }

    private boolean deletePurchaseInvoiceItem(PurchaseInvoiceItem invoiceItem) {
        esm.remove(em, invoiceItem);
        return true;
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
        if(PURCHASE_INVOICE_ITEM_CLASS_NAME.equals(itemClass.getName())) {
            return (I)newPurchaseInvoiceItem((PurchaseInvoice)entity);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass) {
        String entityClassName;
        if(PURCHASE_INVOICE_CLASS_NAME.equals(entityClassName = entityClass.getName())) {
            return (List<E>)getPurchaseInvoices();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass) {
        String itemClassName;
        if(PURCHASE_INVOICE_ITEM_CLASS_NAME.equals(itemClassName = itemClass.getName())) {
            return (List<I>)getPurchaseInvoiceItems((PurchaseInvoice)entity);
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> E save(E entity) {
        if(entity instanceof PurchaseInvoice) {
            return (E)savePurchaseInvoice((PurchaseInvoice)entity);
        } else if(entity instanceof PurchaseInvoiceItem) {
            return (E)savePurchaseInvoiceItem((PurchaseInvoiceItem)entity);
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
        } else if(entity instanceof PurchaseInvoiceItem) {
            deletePurchaseInvoiceItem((PurchaseInvoiceItem)entity);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public <E> EntityProperties getEntityProperties(Class<E> entityClass) {
        EntityProperties entityProperties = esm.getEntityProperties(entityClass);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public List<DbResource> getResources(Class<? extends Enum> enumClass, Class<? extends Enum>... enumCategoryClasses) {
        return esm.getResources(em, enumClass, enumCategoryClasses);
    }
}
