/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.entity.EntityFormProcessor;
import com.cosmos.acacia.entity.EntityService;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Miro
 */
public abstract class AbstractEntityListPanel<E extends DataObjectBean> extends AbstractTablePanel {

    private EntityFormProcessor entityFormProcessor;
    private EntityService entityService;
    private BindingGroup bindingGroup;
    private EntityProperties entityProperties;

    protected AbstractEntityListPanel(Class<E> entityClass) {
        super(entityClass);
    }

    protected AbstractEntityListPanel(EntityPanel mainEntityPanel, Class<E> itemEntityClass) {
        super(mainEntityPanel, itemEntityClass);
    }

    protected EntityPanel getMainEntityPanel() {
        return (EntityPanel)getParentPanel();
    }

    protected EntityFormProcessor getEntityFormProcessor() {
        if (entityFormProcessor == null) {
            entityFormProcessor = new EntityFormProcessor(getEntityClass(), getResourceMap());
        }

        return entityFormProcessor;
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        getEntityService().delete((E) rowObject);
        return true;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        return editRow((E) rowObject);
    }

    @Override
    protected Object newRow() {
        return editRow(newEntity());
    }

    protected E editRow(E entity) {
        if (entity != null) {
            BaseEntityPanel entityPanel = getEntityPanel(entity);
            DialogResponse response = entityPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (E) entityPanel.getSelectedValue();
            }
        }

        return null;
    }

    protected abstract E newEntity();

    protected EntityPanel getEntityPanel(E entity) {
        return new EntityPanel(this, entity);
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
        if (bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    protected EntityProperties getEntityProperties() {
        if (entityProperties == null) {
            entityProperties = getEntityService().getEntityProperties(getEntityClass());
        }

        return entityProperties;
    }

    protected EntityService getEntityService() {
        if (entityService == null) {
            Class<? extends EntityService> entityServiceClass =
                    getEntityFormProcessor().getEntityServiceClass();
            entityService = getBean(entityServiceClass);
        }

        return entityService;
    }

    @Override
    public ResourceMap getResourceMap() {
        EntityPanel mainEntityPanel;
        if((mainEntityPanel = getMainEntityPanel()) != null) {
            return mainEntityPanel.getResourceMap();
        }

        return super.getResourceMap();
    }
}
