package com.cosmos.acacia.crm.bl.impl;

import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.ELProperty;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.CustomerPayment;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.sales.Invoice;
import com.cosmos.acacia.crm.data.purchase.PurchaseOrder;
import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import com.cosmos.acacia.crm.data.cash.CashReconcile;

/**
 * Created	:	10.03.2009
 * @author	Petar Milev
 */
@Stateless
public class DocumentNumberBean implements DocumentNumberLocal{
    @PersistenceContext
    private EntityManager em; 
 
    @EJB
    private WarehouseListLocal warehouseListLocal;

    @Override
    public void setDocumentNumber(DataObjectBean entity) {
        Class<? extends DataObjectBean> entityClass = entity.getClass();
        
        Query q = createMaxNumberQuery(entity);
        BigInteger documentNumber = getNextDocumentNumber(entityClass, getBranch(entity), q);
        setNumber(entity, documentNumber);
    }
    
    public BigInteger getNextDocumentNumber(Class<? extends DataObjectBean> entityClass, Address branch, Query maxNumberQuery) {
        Number number = (Number) maxNumberQuery.getSingleResult();
        // no orders for this warehouse
        if (number == null) {
            Warehouse warehouse = warehouseListLocal.getWarehouseForAddress(branch);
            if (warehouse == null) {
                throw new IllegalStateException("No warehouse found for address: " + branch == null
                        ? "null"
                        : branch.getAddressName());
            }

            if (warehouse.getIndex() == null || warehouse.getIndex().equals(new Long(0))) {
                throw new IllegalStateException("No warehouse index set for warehouse: "
                        + warehouse.getAddress().getAddressName()
                        + ". This is needed to generate document numbers!");
            }

            BigInteger result = new BigInteger("" + warehouse.getIndex());
            result = result.multiply(DOCUMENT_INDEX_MULTIPLICATOR);
            return result;
        } else {
            return new BigInteger(""+(number.longValue()+1));
        }
    }

    public BigInteger getNextDocumentNumber(Class<? extends DataObjectBean> entityClass, Address branch, String maxNumberQueryName, Object[] queryParameters) {
        
        Query q = em.createNamedQuery(maxNumberQueryName);
        String parName = null;
        for (Object object : queryParameters) {
            if (parName==null){
                parName = object.toString();
            }else{
                q.setParameter(parName, object);
                parName = null;
            }
        }
        
        return getNextDocumentNumber(entityClass, branch, q);
    }

    //add custom support for every document entity here
    private void setNumber(DataObjectBean entity, BigInteger number) {
        if ( entity instanceof Invoice ){
            Invoice e = (Invoice) entity;
            e.setInvoiceNumber(number);
        }else if ( entity instanceof CustomerPayment ){
            CustomerPayment e = (CustomerPayment) entity;
            e.setDocumentNumber(number);
        }else if ( entity instanceof PurchaseOrder ){
            PurchaseOrder e = (PurchaseOrder) entity;
            e.setOrderNumber(number);
        }else if ( entity instanceof CashReconcile ){
            CashReconcile e = (CashReconcile) entity;
            e.setDocumentNumber(number.longValue());
        }
    }

    //add custom support for every document entity here
    private Query createMaxNumberQuery(DataObjectBean entity) {
        Query q = null;
        
        if ( entity instanceof Invoice ){
            Invoice i = (Invoice) entity;
            q = em.createNamedQuery("Invoice.maxInvoiceNumberForBranch");
            q.setParameter("branch", getBranch(entity));
            q.setParameter("proformaInvoice", i.getProformaInvoice());
        }else if ( entity instanceof CustomerPayment ){
            q = em.createNamedQuery("CustomerPayment.maxDocumentNumberForBranch");
            q.setParameter("branch", getBranch(entity));
        }else if ( entity instanceof PurchaseOrder ){
            q = em.createNamedQuery("PurchaseOrder.maxOrderNumberForBranch");
            q.setParameter("branch", getBranch(entity));
        }else if ( entity instanceof CashReconcile ){
                q = em.createNamedQuery(CashReconcile.NQ_MAX_NUMBER);
                q.setParameter("branch", getBranch(entity));
        }
        
        return q;
    }

    //add custom support for every document entity here
    private Address getBranch(DataObjectBean entity) {
        if ( entity instanceof CashReconcile ){
            CashReconcile e = (CashReconcile) entity;
            return e.getPublisherBranch();
        }else
            return (Address) ELProperty.create("${branch}").getValue(entity);
    }
}

    