/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Miro
 */
public class EntityListPanel<E extends DataObjectBean> extends AbstractEntityListPanel<E> {

    public EntityListPanel(Class<E> entityClass) {
        super(entityClass);
    }

    @Override
    protected List<E> getEntities() {
        return getEntityService().getEntities(getEntityClass());
    }

    @Override
    protected E newEntity() {
        //return (E) getEntityService().newEntity(getEntityClass());
        E e = (E) getEntityService().newEntity(getEntityClass());
        if(e instanceof PurchaseInvoice) {
            PurchaseInvoice pi = (PurchaseInvoice)e;
            pi.setSupplier(pi.getPublisher());
            pi.setSupplierBranch(pi.getPublisherBranch());
            pi.setSupplierContact(pi.getPublisherOfficer());
            pi.setInvoiceNumber("Some Number");
            pi.setInvoiceDate(new Date());
            pi.setTotalGrossAmount(BigDecimal.ZERO);
            pi.setTotalNetAmount(BigDecimal.ZERO);
            pi.setTotalQuantity(BigDecimal.ZERO);
            pi.setTotalTax(BigDecimal.ZERO);
            pi.setPaymentDeadline(new Date());
            DbResource resource = getResources(MeasurementUnit.class).get(0);
            pi.setPaymentTerms(resource);
            pi.setDeliveryTerms(resource);
        }

        return e;
    }

    public List<DbResource> getResources(Class<? extends Enum> enumClass) {
        return getEntityService().getResources(enumClass);
    }

    @Override
    public boolean isDetailEntity() {
        return false;
    }
}
