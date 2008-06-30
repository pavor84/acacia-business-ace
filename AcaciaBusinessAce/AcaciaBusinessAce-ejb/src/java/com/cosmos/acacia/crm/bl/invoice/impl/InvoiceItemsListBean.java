package com.cosmos.acacia.crm.bl.invoice.impl;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigInteger;
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
 * @author rlozanov
 */
@Stateless
public class InvoiceItemsListBean implements InvoiceItemsListRemote, InvoiceItemsListLocal {
    
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @Override
    public EntityProperties getInvoiceItemsEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(InvoiceItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public List<InvoiceItem> getInvoiceItems(DataObject parentDataObject) {
        Query q;
        if(parentDataObject != null)
        {
            q = em.createNamedQuery("InvoiceItem.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parentDataObject.getDataObjectId());
        }
        else
        {
            q = em.createNamedQuery("InvoiceItem.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<InvoiceItem>(q.getResultList());
    }

    @Override
    public int deleteInvoiceItem(Object invoiceItem) {
        return esm.remove(em, invoiceItem);
    }

    @Override
    public List<DbResource> getMeasureUnits() {
        return MeasurementUnit.getDbResources();
    }

    @Override
    public InvoiceItem saveInvoiceItem(InvoiceItem item) {
        esm.persist(em, item);
        return item;
    }

    @Override
    public InvoiceItem newInvoiceItem(BigInteger invoiceId) {
        return new InvoiceItem(invoiceId);
    }

}
