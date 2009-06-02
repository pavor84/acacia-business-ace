/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.annotation.LogicUnitType;
import com.cosmos.acacia.annotation.OperationRow;
import com.cosmos.acacia.annotation.OperationType;
import com.cosmos.acacia.annotation.Unit;
import com.cosmos.acacia.annotation.UnitType;
import com.cosmos.acacia.annotation.UpdateOperation;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.entity.EntityFormProcessor;
import com.cosmos.acacia.entity.EntityService;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.util.BooleanUtils;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
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
    private E entity;

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

    public final E getEntity() {
        if(entity != null) {
            return entity;
        }

        return (E)getDataTable().getSelectedRowObject();
    }

    public final void setEntity(E entity) {
        this.entity = entity;
    }

    public void rowChanging(AlterationType alterationType, E rowObject) {
    }

    public void rowChanged(AlterationType alterationType, E oldRowObject, E newRowObject) {
        List<Unit> units;
        if((units = getUnits(UnitType.Record, LogicUnitType.Suffix)) != null && units.size() > 0) {
            for(Unit unit : units) {
                for(OperationRow operationRow : unit.operations()) {
                    if(!AlterationType.Nothing.equals(alterationType) &&
                            OperationType.Update.equals(operationRow.operationType())) {
                        UpdateOperation update = operationRow.update();
                        if(!evaluateBooleanExpression(update.condition())) {
                            continue;
                        }

                        String variableExpression = update.variable();
                        String withExpression = update.with();
                        try {
                            if(AlterationType.Create.equals(alterationType)) {
                                setEntity(newRowObject);
                                if(update.incremental()) {
                                    withExpression = variableExpression + " + (" + update.with() + ")";
                                }
                            } else if(AlterationType.Modify.equals(alterationType)) {
                                setEntity(oldRowObject);
                                if(update.incremental()) {
                                    withExpression = variableExpression + " - (" + update.with() + ")";
                                }
                            } else if(AlterationType.Delete.equals(alterationType)) {
                                setEntity(oldRowObject);
                                if(update.incremental()) {
                                    withExpression = variableExpression + " - (" + update.with() + ")";
                                }
                            } else {
                                throw new EntityPanelException("Unsupported alteration type: " + alterationType);
                            }

                            updateVariable(variableExpression, withExpression);
                            if(update.incremental() && AlterationType.Modify.equals(alterationType)) {
                                setEntity(newRowObject);
                                withExpression = variableExpression + " + (" + update.with() + ")";
                                updateVariable(variableExpression, withExpression);
                            }
                        } finally {
                            setEntity(null);
                        }
                    }
                }
            }
        }
    }

    protected boolean evaluateBooleanExpression(String expression) {
        if(true) {
            return true;
        }

        return BooleanUtils.evaluateExpression(this, expression);
    }

    protected List<Unit> getUnits(UnitType unitType, LogicUnitType logicUnitType) {
        return entityFormProcessor.getEntityListLogicUnits(unitType, logicUnitType);
    }

    protected List<Unit> getUnits(UnitType unitType) {
        return entityFormProcessor.getEntityListLogicUnits(unitType);
    }

    public void updateVariable(String variable, String expression) {
        ELProperty elProperty = ELProperty.create("${" + expression + "}");
        Object value = elProperty.getValue(this);
        setProperty(variable, value);
    }

    protected void setProperty(String name, Object value) {
        setProperty(this, name, value);
    }

    protected void setProperty(Object bean, String name, Object value) {
        try {
            PropertyUtils.setProperty(bean, name, value);
        } catch(Exception ex) {
            throw new EntityPanelException("bean=" + bean +
                    ", name=" + name + ", value=" + value, ex);
        }
    }

    protected Object getPropertyValue(Object bean, String propertyName) {
        try {
            return PropertyUtils.getProperty(bean, propertyName);
        } catch (Exception ex) {
            throw new EntityPanelException("propertyName: " + propertyName, ex);
        }
    }

    protected Object getPropertyValue(String propertyName) {
        return getPropertyValue(this, propertyName);
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        E oldRowObject = (E) rowObject;
        rowChanging(AlterationType.Modify, oldRowObject);
        getEntityService().delete(oldRowObject);
        rowChanged(AlterationType.Delete, oldRowObject, null);

        return true;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        E oldRowObject = (E)((E) rowObject).clone();
        E newRowObject = (E) rowObject;
        rowChanging(AlterationType.Modify, newRowObject);
        if((newRowObject = editRow(newRowObject)) != null) {
            rowChanged(AlterationType.Modify, oldRowObject, newRowObject);
        } else {
            rowChanged(AlterationType.Nothing, oldRowObject, newRowObject);
        }

        return newRowObject;
    }

    @Override
    protected Object newRow() {
        E oldRowObject = newEntity();
        E newRowObject = (E)oldRowObject.clone();
        rowChanging(AlterationType.Create, newRowObject);
        if((newRowObject = editRow(newRowObject)) != null) {
            rowChanged(AlterationType.Create, oldRowObject, newRowObject);
        } else {
            rowChanged(AlterationType.Nothing, oldRowObject, newRowObject);
        }

        return newRowObject;
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

        String title;
        if(isDetailEntity()) {
            title = getResourceMap().getString("form.detailEntityList.title");
        } else {
            title = getResourceMap().getString("form.entityList.title");
        }
        if(title != null && title.trim().length() > 0) {
            setTitle(title);
        }

        setVisible(Button.Classify, false);
        setVisible(Button.Close, false);
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

    public abstract boolean isDetailEntity();
}
