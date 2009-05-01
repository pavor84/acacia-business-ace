/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.entity.EntityFormProcessor;
import com.cosmos.acacia.gui.AcaciaPanel;
import java.awt.BorderLayout;

/**
 *
 * @author Miro
 */
public class AbstractEntityPanel<E extends DataObjectBean> extends AcaciaPanel {

    private E entity;

    public AbstractEntityPanel(E entity) {
        this.entity = entity;
        initComponents();
        initData();
    }

    public E getEntity() {
        return entity;
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        EntityFormProcessor formProcessor = new EntityFormProcessor(entity.getClass());
        add(formProcessor.getMainComponent(), BorderLayout.CENTER);
    }

    @Override
    protected void initData() {
    }

    public static void main(String[] args) throws Exception {

        PurchaseInvoice invoice = new PurchaseInvoice();
        AbstractEntityPanel<PurchaseInvoice> entityPanel =
                new AbstractEntityPanel<PurchaseInvoice>(invoice);
        entityPanel.showDialog();
        entityPanel.close();
    }
}
