/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.annotation.LogicUnitType;
import com.cosmos.acacia.annotation.OperationRow;
import com.cosmos.acacia.annotation.OperationType;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.Unit;
import com.cosmos.acacia.annotation.UnitType;
import com.cosmos.acacia.annotation.UpdateOperation;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.entity.ContainerEntity;
import com.cosmos.acacia.entity.EntityFormProcessor;
import com.cosmos.acacia.entity.EntityService;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beans.PropertyChangeNotificationBroadcaster;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetail;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.SelectableListDialog;
import com.cosmos.swingb.binding.EntityBinder;
import com.cosmos.swingb.binding.EntityListBinder;
import com.cosmos.swingb.binding.EnumerationBinder;
import com.cosmos.swingb.binding.Refreshable;
import com.cosmos.util.BeanUtils;
import com.cosmos.util.BooleanUtils;
import java.awt.BorderLayout;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.swingx.JXNumericField;

/**
 *
 * @author Miro
 */
public class EntityPanel<E extends DataObjectBean> extends BaseEntityPanel {

    private AbstractEntityListPanel entityListPanel;
    private E entity;
    private Class<E> entityClass;
    private EntityFormProcessor entityFormProcessor;
    private EntityService entityService;
    private EntityFormButtonPanel buttonPanel;
    private BindingGroup bindingGroup;
    private EntityProperties entityProperties;
    private Map<String, PropertyDependencies> propertyDependenciesMap;

    public EntityPanel(AbstractEntityListPanel entityListPanel, E entity) {
        super(entity);
        this.entityListPanel = entityListPanel;
        this.entity = entity;
        this.entityClass = (Class<E>) entity.getClass();
        init();
    }

    @Override
    public E getEntity() {
        return entity;
    }

    protected Class<E> getEntityClass() {
        return entityClass;
    }

    protected EntityFormProcessor getEntityFormProcessor() {
        if (entityFormProcessor == null) {
            entityFormProcessor = new EntityFormProcessor(getEntityClass(), getResourceMap());
        }

        return entityFormProcessor;
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
    protected void init() {
        initComponents();
        super.init();
    }

    @Override
    protected void initData() {
        super.initData();

        EntityProperties entityProps = getEntityProperties();
        BindingGroup bg = getBindingGroup();
        for (JComponent jComponent : getJComponentsMap().values()) {
            if (jComponent instanceof JLabel) {
                continue;
            }

            String componentName = jComponent.getName();
            String propertyName = getPropertyName(componentName);
            PropertyDetails propertyDetails = entityProps.getPropertyDetails(propertyName);
            addDependencies(jComponent.getName(), propertyDetails.getPropertyDetailsDependencies());

            if (jComponent instanceof EntityBinder) {
                ((EntityBinder) jComponent).bind(bindingGroup, entity, propertyDetails);
            } else if (jComponent instanceof EntityListBinder) {
                ((EntityListBinder) jComponent).bind(bindingGroup,
                        getSelectableListDialog(propertyDetails, entityProps, jComponent),
                        entity, propertyDetails);
            } else if (jComponent instanceof EnumerationBinder) {
                List listData = getEntityService().getResources(getSelectableListDialogClass(propertyDetails));
                ((EnumerationBinder) jComponent).bind(bindingGroup, listData, entity, propertyDetails);
            }
        }

        bg.bind();

        validateForm();
    }

    @Override
    protected void entityChanged(Binding binding, PropertyStateEvent event) {
        super.entityChanged(binding, event);

        List<Unit> units;
        if((units = getUnits(UnitType.Variable, LogicUnitType.Verification)) != null && units.size() > 0) {
            for(Unit unit : units) {
                for(OperationRow operationRow : unit.operations()) {
                    if(OperationType.Update.equals(operationRow.operationType())) {
                        UpdateOperation update = operationRow.update();
                        if(!evaluateBooleanExpression(update.condition(), binding, event)) {
                            continue;
                        }
                        String variableExpression = update.variable();
                        updateVariable(variableExpression, update.with());
                        refreshComponents(getJComponentsByExpression(variableExpression));
                    }
                }
            }
        }

        refreshRelatedComponents(getJComponent(binding), event.getNewValue());
        validateForm();
    }

    protected Set<JComponent> getJComponentsByExpression(String expression) {
        if((expression = expression.trim()).length() == 0) {
            return Collections.emptySet();
        }

        if(!expression.startsWith("entity.")) {
            return Collections.emptySet();
        }

        expression = expression.substring(7);
        return getJComponentsByPropertyName(expression);
    }

    protected boolean evaluateBooleanExpression(String expression, Binding binding, PropertyStateEvent event) {
        if(BooleanUtils.parseBoolean(expression)) {
            return true;
        }

        if((expression = expression.trim()).startsWith("onChange(")) {
            expression = expression.substring(9);
            int index = expression.indexOf(')');
            expression = expression.substring(0, index);
            return onChange(expression, binding, event);
        }

        return BooleanUtils.evaluateExpression(this, expression);
    }

    protected boolean onChange(String expression, Binding binding, PropertyStateEvent event) {
        JComponent jComponent = getJComponent(binding);
        String propertyName = getPropertyName(jComponent.getName());

        for(String name : expression.split(",")) {
            name = name.trim();
            if(!name.startsWith("entity.")) {
                continue;
            }
            name = name.substring("entity.".length());
            if(propertyName.equals(name)) {
                return true;
            }
        }

        return false;
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
        value = BeanUtils.getInstance().normalizeSetterValue(bean, name, value);
        try {
            PropertyUtils.setProperty(bean, name, value);
        } catch(Exception ex) {
            StringBuilder sb = new StringBuilder();
            sb.append("bean=").append(bean);
            sb.append(", name=").append(name);
            sb.append(", value=").append(value);
            if(value != null) {
                sb.append(", value class=").append(value.getClass().getName());
            }
            throw new EntityPanelException(sb.toString(), ex);
        }
    }

    public void refreshComponent(String propertyName) {
        Set<JComponent> components = getJComponentsByPropertyName(propertyName);
        refreshComponents(components);
    }

    protected void refreshComponents(Collection<JComponent> components) {
        for(JComponent jComponent : components) {
            if(jComponent instanceof Refreshable) {
                ((Refreshable)jComponent).refresh();
            }
        }
    }

    protected void refreshRelatedComponents(JComponent jComponent, Object newValue) {
        PropertyChangeHandler handler = getPropertyChangeHandler();
        String componentName = getJComponentName(jComponent);

        String propertyName;
        if((propertyName = getPropertyName(componentName)) == null) {
            return;
        }

        List<PropertyBean> propertyBeans = handler.getPropertyBeansMap().get(propertyName);
        if (propertyBeans != null && propertyBeans.size() > 0) {
            for (PropertyBean propertyBean : propertyBeans) {
                Object bean = propertyBean.getBean();
                String setterName = propertyBean.getSetterName();
                jComponent = propertyBean.getJComponent();
                setProperty(bean, setterName, newValue);
                if (jComponent instanceof Refreshable) {
                    ((Refreshable) jComponent).refresh();
                }
            }
        }
    }

    protected void validateForm() {
        Map<String, PropertyDependencies> pdMap = getPropertyDependenciesMap();
        for(String propertyName : pdMap.keySet()) {
            boolean ready = true;
            for(String pn : pdMap.get(propertyName).getDependencies()) {
                if(getPropertyValue(pn) == null) {
                    ready = false;
                    break;
                }
            }

            getJComponent(propertyName).setEnabled(ready);
        }

        Map<String, Set<String>> cdMap = getContainerDependenciesMap();
        for(String containerName : cdMap.keySet()) {
            for(String dependency : cdMap.get(containerName)) {
                if(Property.ENTITY_FORM_NAME.equals(dependency)) {
                    JComponent container = getContainer(containerName);
                    JComponent parentContainer = (JComponent)container.getParent();
                    boolean ready = getBindingGroup().isContentValid();
                    container.setEnabled(ready);
                    if(parentContainer instanceof JTabbedPane) {
                        JTabbedPane tabbedPane = (JTabbedPane)parentContainer;
                        int index;
                        if((index = tabbedPane.indexOfComponent(container)) >= 0) {
                            tabbedPane.setEnabledAt(index, ready);
                        }
                    }
                }
            }
        }
    }

    protected Class getSelectableListDialogClass(PropertyDetails propertyDetails) {
        String className;
        if ((className = propertyDetails.getSelectableListDialogClassName()) == null) {
            return null;
        }

        try {
            return Class.forName(className);
        } catch(ClassNotFoundException ex) {
            throw new EntityPanelException(ex);
        }
    }

    protected SelectableListDialog getSelectableListDialog(PropertyDetails propertyDetails,
            EntityProperties entityProps, JComponent jComponent) {
        Class<? extends SelectableListDialog> cls;
        try {
            if ((cls = (Class<? extends SelectableListDialog>) getSelectableListDialogClass(propertyDetails)) != null) {
                List<PropertyDetail> parameters;
                if ((parameters = propertyDetails.getSelectableListDialogConstructorParameters()) == null || parameters.size() == 0) {
                    return cls.newInstance();
                }

                addDependenciesByParameters(jComponent.getName(), parameters);
                Class[] parameterTypes = getParameterTypes(parameters, entityProps);
                Object[] parameterValues = getParameters(parameters);
                SelectableListDialog listDialog = (SelectableListDialog) ConstructorUtils.invokeConstructor(cls, parameterValues, parameterTypes);
                PropertyChangeHandler handler = getPropertyChangeHandler();
                handler.addPropertyBean(parameters, listDialog, jComponent);

                return listDialog;
            }
        } catch (Exception ex) {
            throw new EntityPanelException("propertyDetails: " + propertyDetails, ex);
        }

        return new EntityListPanel(propertyDetails.getPropertyClass());
    }

    protected String getJComponentName(JComponent jComponent) {
        return normalizeJComponent(jComponent).getName();
    }

    protected JComponent getJComponent(Binding binding) {
        return normalizeJComponent((JComponent)binding.getTargetObject());
    }

    protected JComponent normalizeJComponent(JComponent jComponent) {
        JComponent parent = (JComponent)jComponent.getParent();
        if(jComponent instanceof JXNumericField && parent instanceof EntityBinder) {
            return parent;
        } else if(jComponent instanceof JComboBox && parent instanceof JBComboList) {
            return parent;
        } else if(jComponent instanceof JScrollPane) {
            return parent;
        }

        return jComponent;
    }

    protected Object[] getParameters(List<PropertyDetail> parameters) {
        int size;
        Object[] parameterValues = new Object[size = parameters.size()];
        for (int i = 0; i < size; i++) {
            String parameterName = parameters.get(i).getGetter();
            parameterValues[i] = getPropertyValue(parameterName);
        }

        return parameterValues;
    }

    protected Class[] getParameterTypes(List<PropertyDetail> parameters, EntityProperties entityProps) {
        int size;
        Class[] parameterTypes = new Class[size = parameters.size()];
        for (int i = 0; i < size; i++) {
            String parameterName = parameters.get(i).getGetter();
            PropertyDetails propertyDetails = entityProps.getPropertyDetails(parameterName);
            if (propertyDetails == null) {
                throw new EntityPanelException("Missing PropertyDetails with name '" + parameterName + "'");
            }
            parameterTypes[i] = propertyDetails.getPropertyClass();
        }

        return parameterTypes;
    }

    protected Object getPropertyValue(String propertyName) {
        try {
            return PropertyUtils.getProperty(entity, propertyName);
        } catch (Exception ex) {
            throw new EntityPanelException("propertyName: " + propertyName, ex);
        }
    }
    private PropertyChangeHandler propertyChangeHandler;

    private PropertyChangeHandler getPropertyChangeHandler() {
        if (propertyChangeHandler == null && entity instanceof PropertyChangeNotificationBroadcaster) {
            propertyChangeHandler = new PropertyChangeHandler();
        }

        return propertyChangeHandler;
    }

    @Override
    public void performSave(boolean closeAfter) {
        try {
            entity = getEntityService().save(entity);
            //entity = getEntityService().confirm(entity);
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValue(entity);
            if (closeAfter) {
                close();
            } else {
                rebind();
            }
        } catch (Exception ex) {
            handleException("entity: " + entity, ex);
        }
    }

    protected void rebind() {
        getBindingGroup().unbind();
        bindingGroup = null;
        initData();
    }

    @Override
    public BindingGroup getBindingGroup() {
        if (bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new EntityFormButtonPanel();
        }

        return buttonPanel;
    }

    private void initComponents() {
        setName("Form"); // NOI18N
        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(920, 480));

        EntityFormProcessor formProcessor = getEntityFormProcessor();
        add(formProcessor.getMainComponent(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.PAGE_END);

        for (ContainerEntity containerEntity : formProcessor.getContainerEntities()) {
            JComponent jContainer = containerEntity.getJContainer();
            Class itemEntityClass = containerEntity.getEntityClass();
            RelationshipType relationshipType;
            if (RelationshipType.OneToMany.equals(relationshipType = containerEntity.getRelationshipType())) {
                DetailEntityListPanel<E, ?> listPanel =
                        new DetailEntityListPanel(this, itemEntityClass);
                jContainer.setLayout(new BorderLayout());
                jContainer.add(listPanel, BorderLayout.CENTER);
            }
        }

        String title;
        if (getEntityListPanel().isDetailEntity()) {
            title = getResourceMap().getString("form.detailEntity.title");
        } else {
            title = getResourceMap().getString("form.entity.title");
        }
        if (title != null && title.trim().length() > 0) {
            setTitle(title);
        }
    }

    @Override
    public ResourceMap getResourceMap() {
        return entityListPanel.getResourceMap();
    }

    protected AbstractEntityListPanel getEntityListPanel() {
        return entityListPanel;
    }

    public JComponent getContainer(String name) {
        return entityFormProcessor.getContainer(name);
    }

    public JComponent getJComponent(String name) {
        return entityFormProcessor.getJComponent(name);
    }

    public Map<String, JComponent> getContainersMap() {
        return entityFormProcessor.getContainersMap();
    }

    public Map<String, JComponent> getJComponentsMap() {
        return entityFormProcessor.getJComponentsMap();
    }

    public Map<String, Property> getPropertiesMap() {
        return entityFormProcessor.getPropertiesMap();
    }

    public Map<String, String> getPropertyNamesMap() {
        return entityFormProcessor.getPropertyNamesMap();
    }

    public String getPropertyName(String componentName) {
        return entityFormProcessor.getPropertyName(componentName);
    }

    public Property getProperty(String componentName) {
        return entityFormProcessor.getProperty(componentName);
    }

    public Set<JComponent> getJComponentsByPropertyName(String propertyName) {
        return entityFormProcessor.getJComponentsByPropertyName(propertyName);
    }

    public Map<String, PropertyDependencies> getPropertyDependenciesMap() {
        if(propertyDependenciesMap == null) {
            propertyDependenciesMap = new TreeMap<String, PropertyDependencies>();
        }

        return propertyDependenciesMap;
    }

    public PropertyDependencies getPropertyDependencies(String propertyName) {
        return getPropertyDependenciesMap().get(propertyName);
    }

    public PropertyDependencies putPropertyDependencies(String propertyName, PropertyDependencies propertyDependencies) {
        return getPropertyDependenciesMap().put(propertyName, propertyDependencies);
    }

    public void addDependency(String propertyName, String dependencyPropertyName) {
        PropertyDependencies propertyDependencies;
        if((propertyDependencies = getPropertyDependencies(propertyName)) == null) {
            propertyDependencies = new PropertyDependencies(propertyName);
            putPropertyDependencies(propertyName, propertyDependencies);
        }
        propertyDependencies.addDependency(dependencyPropertyName);
    }

    public void addDependencies(String propertyName, List<PropertyDetails> pdList) {
        if(pdList == null || pdList.size() == 0) {
            return;
        }

        PropertyDependencies propertyDependencies;
        if((propertyDependencies = getPropertyDependencies(propertyName)) == null) {
            propertyDependencies = new PropertyDependencies(propertyName);
            putPropertyDependencies(propertyName, propertyDependencies);
        }

        for(PropertyDetails pd : pdList) {
            propertyDependencies.addDependency(pd.getPropertyName());
        }
    }

    public void addDependenciesByParameters(String propertyName, List<PropertyDetail> pdList) {
        if(pdList == null || pdList.size() == 0) {
            return;
        }

        PropertyDependencies propertyDependencies;
        if((propertyDependencies = getPropertyDependencies(propertyName)) == null) {
            propertyDependencies = new PropertyDependencies(propertyName);
            putPropertyDependencies(propertyName, propertyDependencies);
        }

        for(PropertyDetail pd : pdList) {
            propertyDependencies.addDependency(pd.getGetter());
        }
    }

    public Map<String, Set<String>> getContainerDependenciesMap() {
        return entityFormProcessor.getContainerDependenciesMap();
    }

    protected List<Unit> getUnits(UnitType unitType, LogicUnitType logicUnitType) {
        return entityFormProcessor.getEntityLogicUnits(unitType, logicUnitType);
    }

    protected List<Unit> getUnits(UnitType unitType) {
        return entityFormProcessor.getEntityLogicUnits(unitType);
    }
}