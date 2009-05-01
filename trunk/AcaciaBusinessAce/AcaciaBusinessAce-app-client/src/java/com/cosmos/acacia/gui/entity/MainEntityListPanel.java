/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.entity.EntityFormProcessor;
import com.cosmos.acacia.entity.EntityService;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.util.List;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Miro
 */
public class MainEntityListPanel<E extends DataObjectBean> extends AbstractTablePanel {

    private EntityFormProcessor entityFormProcessor;
    private EntityService entityService;

    private BindingGroup bindingGroup;
    private EntityProperties entityProperties;

    public MainEntityListPanel(Class<E> entityClass) {
        super(entityClass);
    }

    protected EntityFormProcessor getEntityFormProcessor() {
        if(entityFormProcessor == null) {
            entityFormProcessor = new EntityFormProcessor(getEntityClass());
        }

        return entityFormProcessor;
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        getEntityService().delete((E)rowObject);
        return true;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        return editRow((E)rowObject);
    }

    @Override
    protected Object newRow() {
        return editRow((E)getEntityService().newEntity(PurchaseInvoice.class));
    }

    protected E editRow(E entity) {
        if(entity != null) {
            MainEntityPanel entityPanel = new MainEntityPanel(entity);
            DialogResponse response = entityPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (E) entityPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected void initData() {
        super.initData();

        bindingGroup = getBindingGroup();
        AcaciaTable table = getDataTable();
        JTableBinding tableBinding = table.bind(
                bindingGroup,
                getEntities(),
                getEntityProperties(),
                UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    public BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    protected EntityProperties getEntityProperties() {
        if(entityProperties == null) {
            entityProperties = getEntityService().getEntityProperties(PurchaseInvoice.class);
        }

        return entityProperties;
    }

    @Override
    protected List<PurchaseInvoice> getEntities() {
        return getEntityService().getEntities(PurchaseInvoice.class);
    }

    protected EntityService getEntityService() {
        if(entityService == null) {
            Class<? extends EntityService> entityServiceClass =
                    getEntityFormProcessor().getEntityServiceClass();
            entityService = getBean(entityServiceClass);
        }

        return entityService;
    }



}
