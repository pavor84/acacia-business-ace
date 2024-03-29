/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.entity.AcaciaEntityAttributes;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.gui.DataMode;
import com.cosmos.acacia.annotation.LogicUnitType;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.Unit;
import com.cosmos.acacia.annotation.UnitType;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.entity.ContainerEntity;
import com.cosmos.acacia.entity.EntityAttributes;
import com.cosmos.acacia.entity.EntityFormProcessor;
import com.cosmos.acacia.entity.EntityService;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.service.ServiceManager;
import com.cosmos.acacia.util.SystemUtils;
import com.cosmos.beans.PropertyChangeNotificationBroadcaster;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetail;
import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.SelectableListDialog;
import com.cosmos.swingb.binding.Clearable;
import com.cosmos.swingb.binding.EntityBinder;
import com.cosmos.swingb.binding.EntityListBinder;
import com.cosmos.swingb.binding.EnumerationBinder;
import com.cosmos.swingb.binding.Refreshable;
import com.cosmos.util.BeanUtils;
import com.cosmos.util.BooleanUtils;
import com.cosmos.util.PersistentEntity;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.DefaultELContext;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.el.ELContext;
import org.jdesktop.swingx.JXNumericField;

/**
 *
 * @author Miro
 */
public class EntityPanel<E extends PersistentEntity> extends BaseEntityPanel {

    //
    private static final String ON_ENTITY_CHANGE_FUNCTION = "onEntityChange";
    private static final String IS_SYSTEM_ORGANIZATION_FUNCTION = "isSystemOrganization";
    private static final String ENTITY_PREFIX = "entity.";
    private static final Object[] EMPTY_ARRAY = new Object[0];
    //
    private AbstractEntityListPanel entityListPanel;
    private Class<E> entityClass;
    private EntityFormProcessor entityFormProcessor;
    private EntityService entityService;
    private EntityFormButtonPanel entityFormButtonPanel;
    private BindingGroup bindingGroup;
    private EntityProperties entityProperties;
    private Map<String, PropertyDependencies> propertyDependenciesMap;
    private ELContext elContext;
    protected DataMode dataMode;
    private boolean initialization;

    public EntityPanel(AbstractEntityListPanel entityListPanel, E entity, List<Classifier> classifiers, Object... parameters) {
        super(entity, classifiers, parameters);
        this.entityListPanel = entityListPanel;
        this.entityClass = (Class<E>) getEntity().getClass();
        init();
    }

    @Override
    public E getEntity() {
        return (E) super.getEntity();
    }

    protected Class<E> getEntityClass() {
        return entityClass;
    }

    protected EntityFormProcessor getEntityFormProcessor() {
        if (entityFormProcessor == null) {
            entityFormProcessor = new EntityFormProcessor(getEntityClass(), getResourceMap(), AcaciaEntityAttributes.getEntityAttributesMap());
//            EntityProperties entityProperties = getEntityProperties();
//            //entityProperties.re
//            entityFormProcessor.getJComponentsByPropertyName(ENTITY_PREFIX)
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
        initialization = true;
        try {
            super.initData();

            E entity = getEntity();

            EntityProperties entityProps = getEntityProperties();
            BindingGroup bg = getBindingGroup();
            AutoBinding.UpdateStrategy updateStrategy;
            if(isQueryMode()) {
                updateStrategy = AutoBinding.UpdateStrategy.READ;
            } else {
                updateStrategy = null;
            }
            for (JComponent jComponent : getJComponentsMap().values()) {
                if (jComponent instanceof JLabel) {
                    continue;
                }

                String componentName = jComponent.getName();
                String propertyName = getPropertyName(componentName);
                EntityProperty propertyDetails = entityProps.getEntityProperty(propertyName);
                if(propertyDetails == null) {
                    throw new EntityPanelException("Missing PropertyDetails of property '" + propertyName +
                            "' for componentName=" + componentName + " and jComponent=" + jComponent +
                            ",\n entityProps.getKeys(): " + entityProps.getKeys());
                }
                addDependencies(jComponent.getName(), propertyDetails.getPropertyDetailsDependencies());

                if (jComponent instanceof EntityBinder) {
                    if(updateStrategy != null) {
                        ((EntityBinder) jComponent).bind(bg, entity, propertyDetails, updateStrategy);
                    } else {
                        ((EntityBinder) jComponent).bind(bg, entity, propertyDetails);
                    }
                } else if (jComponent instanceof EntityListBinder) {
                    if(updateStrategy != null) {
                        ((EntityListBinder) jComponent).bind(bg,
                                getSelectableListDialog(propertyDetails, entityProps, jComponent),
                                entity, propertyDetails, updateStrategy);
                    } else {
                        ((EntityListBinder) jComponent).bind(bg,
                                getSelectableListDialog(propertyDetails, entityProps, jComponent),
                                entity, propertyDetails);
                    }
                } else if (jComponent instanceof EnumerationBinder) {
                    if(DbResource.class == propertyDetails.getPropertyClass()) {
                        Object[] params = getParameters(propertyDetails);
                        Class selectableListDialogClass = getSelectableListDialogClass(propertyDetails);
                        List listData = getResources(selectableListDialogClass, params);
                        if(updateStrategy != null) {
                            ((EnumerationBinder) jComponent).bind(bg, listData,
                                    entity, propertyDetails, updateStrategy);
                        } else {
                            ((EnumerationBinder) jComponent).bind(bg, listData,
                                    entity, propertyDetails);
                        }
                    } else {
                        SelectableListDialog selectableListDialog = getSelectableListDialog(propertyDetails, entityProps, jComponent);
                        if(updateStrategy != null) {
                            ((EnumerationBinder) jComponent).bind(bg, selectableListDialog,
                                    entity, propertyDetails, updateStrategy);
                        } else {
                            ((EnumerationBinder) jComponent).bind(bg, selectableListDialog,
                                    entity, propertyDetails);
                        }
                    }
                } else {
                    if(!(jComponent instanceof JScrollPane || jComponent instanceof JButton)) {
                        System.out.println("Unknown binder for jComponent: " + jComponent);
                    }
                }
            }

            bg.bind();
        } finally {
            initialization = false;
        }

        validateForm();
        initDataMode(getDataMode());
    }

    protected Object[] getParameters(EntityProperty propertyDetails) {
        int size;
        List<PropertyDetail> pdList;
        if((pdList = propertyDetails.getSelectableListDialogConstructorParameters()) == null || (size = pdList.size()) == 0) {
            return EMPTY_ARRAY;
        }

        Object[] params = new Object[size];
        for(int i = 0; i < size; i++) {
            PropertyDetail pd = pdList.get(i);
            String getter = pd.getGetter();
            Object bean;
            if(isExpressionLanguage(getter)) {
                bean = getPropertyValue(getter);
            } else {
                bean = getPropertyValue(this, getter);
            }
            String setter;
            if((setter = pd.getSetter()) != null && setter.length() > 0) {
                params[i] = getPropertyValue(bean, setter);
            } else {
                params[i] = bean;
            }
        }

        return params;
    }

    protected List getResources(Class<? extends Enum> enumClass, Object... categoryClassifiers) {
        return getEntityService().getResources(enumClass, categoryClassifiers);
    }

    public void initDataMode(DataMode dataMode) {
        if(DataMode.Query.equals(dataMode)) {
            for(Component component : getAllComponents(getMainComponent())) {
                if(component instanceof JLabel) {
                    continue;
                }

                if(component instanceof AbstractEntityListPanel) {
                    ((AbstractEntityListPanel)component).setDataMode(dataMode);
                    continue;
                }

                try {
                    Method method = component.getClass().getMethod("setEditable", boolean.class);
                    method.invoke(component, false);
                    if(component instanceof JComboBox || component instanceof JBComboList) {
                        component.setEnabled(false);
                    }
                } catch(Exception ex) {
                    component.setEnabled(false);
                }
            }

            EntityFormButtonPanel buttonPanel = getButtonPanel();
            buttonPanel.setVisible(EntityFormButtonPanel.Button.Save, false);
        }
    }

    public void setDataMode(DataMode dataMode) {
        this.dataMode = dataMode;
        reinit();
    }

    public DataMode getDataMode() {
        if(dataMode == null) {
            dataMode = DataMode.Modify;
        }

        return dataMode;
    }

    public boolean isQueryMode() {
        return DataMode.Query.equals(getDataMode());
    }

    @Override
    protected void entityChanged(Binding binding, PropertyStateEvent event) {
        super.entityChanged(binding, event);

        JComponent jComponent = getJComponent(binding);
        String propertyName = getPropertyName(jComponent.getName());

        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if(oldValue == null && newValue != null || oldValue != null && newValue == null ||
                newValue != null && !newValue.equals(oldValue)) {
            entityChanged(propertyName, jComponent, event);
        }

        /*List<Unit> units;
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
        }*/

        refreshRelatedComponents(jComponent, event.getNewValue());
        validateForm();
    }

    protected void entityChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
    }

    protected Set<JComponent> getJComponentsByExpression(String expression) {
        if((expression = expression.trim()).length() == 0) {
            return Collections.emptySet();
        }

        if(!expression.startsWith(ENTITY_PREFIX)) {
            return Collections.emptySet();
        }

        expression = expression.substring(7);
        return getJComponentsByPropertyName(expression);
    }

    protected boolean evaluateBooleanExpression(String expression) {
        return evaluateBooleanExpression(expression, null, null);
    }

    protected boolean evaluateBooleanExpression(String expression, Binding binding, PropertyStateEvent event) {
        if(BooleanUtils.parseBoolean(expression)) {
            return true;
        }

        if(binding != null) {
            if((expression = expression.trim()).startsWith(ON_ENTITY_CHANGE_FUNCTION + "(")) {
                expression = expression.substring(ON_ENTITY_CHANGE_FUNCTION.length() + 1);
                int index = expression.indexOf(')');
                expression = expression.substring(0, index);
                return onEntityChanged(expression, binding, event);
            }
        }

        String functionName;
        if((functionName = getFunctionName(expression)) != null) {
            return (Boolean) executeFunction(functionName, getFunctionExpressionParameters(expression));
        }

        return BooleanUtils.evaluateExpression(this, expression);
    }

    protected String getFunctionName(String expression) {
        int endIndex;
        if((endIndex = (expression = expression.trim()).lastIndexOf(')')) <= 0) {
            return null;
        }

        int beginIndex;
        if((beginIndex = expression.indexOf('(')) < 0 || beginIndex > endIndex) {
            return null;
        }

        String functionName = expression.substring(0, beginIndex);
        for(Method method : getClass().getMethods()) {
            if(method.getName().equals(functionName)) {
                return functionName;
            }
        }

        return null;
    }

    protected List<String> getFunctionExpressionParameters(String expression) {
        int endIndex;
        if((endIndex = (expression = expression.trim()).lastIndexOf(')')) <= 0) {
            return Collections.emptyList();
        }

        int beginIndex;
        if((beginIndex = expression.indexOf('(')) < 0 || beginIndex > endIndex) {
            return Collections.emptyList();
        }

        expression = expression.substring(beginIndex + 1, endIndex);
        String[] array = expression.split(",");
        List<String> list = new ArrayList<String>(array.length);
        for(String item : array) {
            list.add(item.trim());
        }
        return list;
    }

    protected Object executeFunction(String functionName, List<String> parameters) {
        int size = parameters.size();
        Object[] parameterValues = new Object[size];
        for(int i = 0; i < size; i++) {
            parameterValues[i] = getPropertyValue(parameters.get(i));
        }

        Class[] parameterTypes = new Class[size];
        for(int i = 0; i < size; i++) {
            parameterTypes[i] = parameterValues[i].getClass();
        }

        try {
            Method method = getClass().getMethod(functionName, parameterTypes);
            return method.invoke(this, parameterValues);
        } catch(Exception ex) {
            throw new EntityPanelException(ex);
        }
    }

    protected boolean onEntityChanged(String expression, Binding binding, PropertyStateEvent event) {
        JComponent jComponent = getJComponent(binding);
        String propertyName = getPropertyName(jComponent.getName());

        for(String name : expression.split(",")) {
            name = name.trim();
            if(!name.startsWith(ENTITY_PREFIX)) {
                continue;
            }
            name = name.substring(ENTITY_PREFIX.length());
            if(propertyName.equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void updateVariable(String variable, String expression) {
        ELProperty elProperty = create("${" + expression + "}");
        Object value = elProperty.getValue(this);
        setProperty(variable, value);
    }

    protected void setProperty(String name, Object value) {
        setProperty(this, name, value);
    }

    protected void setProperty(Object bean, String name, Object value) {
        try {
            BeanUtils.getInstance().setPropertyValue(bean, name, value);
        } catch(RuntimeException ex) {
            throw new RuntimeException("panel=" + getClass().getName() + ", panelName=" + getName(), ex);
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
//        new Throwable("refreshRelatedComponents()").printStackTrace();
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
        if(isQueryMode()) {
            return;
        }

        if(!initialization) {
//            new Throwable("validateForm()").printStackTrace();
            Map<String, PropertyDependencies> pdMap = getPropertyDependenciesMap();
            for(String propertyName : pdMap.keySet()) {
                boolean ready = true;
                for(String pn : pdMap.get(propertyName).getDependencies()) {
                    if(isConstantParameter(pn)) {
                        continue;
                    }
                    if(getPropertyValue(pn) == null) {
                        ready = false;
                        break;
                    }
                }

                JComponent jComponent = getJComponent(propertyName);
                jComponent.setEnabled(ready);
                if(ready) {
                    if(jComponent instanceof Refreshable) {
//                        System.out.println("validateForm().refresh(): " + jComponent);
//                        ((Refreshable) jComponent).refresh();
                    }
                } else {
                    if(jComponent instanceof Clearable) {
//                        System.out.println("validateForm().clear(): " + jComponent);
//                        ((Clearable) jComponent).clear();
                    }
                }
            }
        }

        Map<String, Set<String>> cdMap = getContainerDependenciesMap();
        for(String containerName : cdMap.keySet()) {
            for(String dependency : cdMap.get(containerName)) {
                if(FormContainer.DEPENDS_ENTITY_FORM.equals(dependency)) {
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

    protected Class getSelectableListDialogClass(EntityProperty entityProperty) {
        String className;
        if ((className = entityProperty.getSelectableListDialogClassName()) == null) {
            return getListPanelClass(entityProperty.getPropertyClass());
        }

        try {
            return Class.forName(className);
        } catch(ClassNotFoundException ex) {
            throw new EntityPanelException(ex);
        }
    }

    protected SelectableListDialog getSelectableListDialog(EntityProperty entityProperty,
            EntityProperties entityProps, JComponent jComponent) {
        Class<? extends SelectableListDialog> cls = null;
        Class[] parameterTypes = null;
        Object[] parameterValues = null;
        try {
            cls = (Class<? extends SelectableListDialog>) getSelectableListDialogClass(entityProperty);
            List<PropertyDetail> params;
            if ((params = entityProperty.getSelectableListDialogConstructorParameters()) == null || params.size() == 0) {
                return cls.newInstance();
            }

            addDependenciesByParameters(jComponent.getName(), params);
            parameterTypes = getParameterTypes(params, entityProps, cls);
            parameterValues = getParameters(params, parameterTypes);
            SelectableListDialog listDialog = (SelectableListDialog) ConstructorUtils.invokeConstructor(cls, parameterValues, parameterTypes);
            PropertyChangeHandler handler = getPropertyChangeHandler();
            handler.addPropertyBean(params, listDialog, jComponent);

            return listDialog;
        } catch (Exception ex) {
            throw new EntityPanelException("propertyDetails: " + entityProperty +
                    ", cls: " + cls +
                    ", parameterValues: " + asList(parameterValues) +
                    ", parameterTypes: " + asList(parameterTypes), ex);
        }
    }

    protected <T> List<T> asList(T... values) {
        if(values == null || values.length == 0) {
            return Collections.emptyList();
        }

        return Arrays.asList(values);
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

    protected Object[] getParameters(List<PropertyDetail> parameters, Class[] parameterTypes) {
        int size;
        Object[] parameterValues = new Object[size = parameters.size()];
        ConvertUtilsBean convertUtils = BeanUtils.getInstance().getConvertUtils();
        for (int i = 0; i < size; i++) {
            PropertyDetail pd = parameters.get(i);
            String getter = pd.getGetter();
            if(isConstantParameter(getter)) {
                Class parameterType = parameterTypes[i];
                if(Classifier.class == parameterType) {
                    parameterValues[i] = getAcaciaSession().getClassifier(normalizeConstant(getter));
                } else {
                    parameterValues[i] = convertUtils.convert(normalizeConstant(getter), parameterType);
                }
            } else {
                parameterValues[i] = getPropertyValue(getter);
            }
        }

        return parameterValues;
    }

    protected String normalizeConstant(String constant) {
        int length = constant.length();
        int beginIndex = 0;
        char ch;
        while(beginIndex < length && ((ch = constant.charAt(beginIndex)) == '\'' || ch == '"')) {
            beginIndex++;
        }
        ch = '\'';
        int endIndex = length - 1;
        while(endIndex > beginIndex && ((ch = constant.charAt(endIndex)) == '\'' || ch == '"')) {
            endIndex--;
        }
        if(ch != '\'' && ch != '"') {
            endIndex++;
        }
        return constant.substring(beginIndex, endIndex);
    }

    protected boolean isExpressionLanguage(String parameter) {
        return parameter.startsWith("${") || parameter.startsWith("#{");
    }

    protected boolean isConstantParameter(String parameter) {
        return parameter.startsWith("'") || parameter.startsWith("\"");
    }

    protected Class[] getParameterTypes(
            List<PropertyDetail> parameters,
            EntityProperties entityProps,
            Class<? extends SelectableListDialog> cls) {
        int size;
        Class[] parameterTypes = new Class[size = parameters.size()];
        for (int i = 0; i < size; i++) {
            PropertyDetail pd = parameters.get(i);
            String getter = pd.getGetter().trim();
            if(isExpressionLanguage(getter)) {
                parameterTypes[i] = getPropertyValue(getter).getClass();
            } else if(isConstantParameter(getter)) {
                String setter;
                if((setter = pd.getSetter().trim()).length() == 0) {
                    throw new EntityPanelException("The setter is required when the getter (" + getter + ") is constant.");
                }
                String methodName = "set" + Character.toUpperCase(setter.charAt(0)) + setter.substring(1);
                Method method = null;
                Class[] paramTypes = null;
                try {
                    for(Method m : cls.getMethods()) {
                        if(methodName.equals(m.getName()) && (paramTypes = m.getParameterTypes()).length == 1) {
                            method = m;
                            break;
                        }
                    }
                } catch(Exception ex) {
                    throw new EntityPanelException("Missing method with name " + methodName +
                            " in class " + cls, ex);
                }
                if(method == null || paramTypes == null || paramTypes.length != 1) {
                    throw new EntityPanelException("Missing method with name " + methodName +
                            " in class " + cls);
                }
                parameterTypes[i] = paramTypes[0];
            } else {
                EntityProperty propertyDetails = entityProps.getEntityProperty(getter);
                if (propertyDetails == null) {
                    throw new EntityPanelException("Missing PropertyDetails with name '" + getter + "'");
                }
                parameterTypes[i] = propertyDetails.getPropertyClass();
            }
        }

        return parameterTypes;
    }

    protected Object getELPropertyValue(String expression) {
            if(expression.endsWith("{this}")) {
                return this;
            }

            ELProperty elProperty = create(expression);
            return elProperty.getValue(this);
    }

    protected Object getPropertyValue(String propertyName) {
        if(isExpressionLanguage(propertyName)) {
            return getELPropertyValue(propertyName);
        }

        return getPropertyValue(getEntity(), propertyName);
    }

    protected Object getPropertyValue(Object bean, String propertyName) {
        try {
            return PropertyUtils.getProperty(bean, propertyName);
        } catch (Exception ex) {
            throw new EntityPanelException("propertyName: " + propertyName, ex);
        }
    }
    private PropertyChangeHandler propertyChangeHandler;

    private PropertyChangeHandler getPropertyChangeHandler() {
        if (propertyChangeHandler == null && getEntity() instanceof PropertyChangeNotificationBroadcaster) {
            propertyChangeHandler = new PropertyChangeHandler();
        }

        return propertyChangeHandler;
    }

    @Override
    public void performSave(boolean closeAfter) {
        setEntity(save());
        //entity = confirm();
        if (closeAfter) {
            setDialogResponse(DialogResponse.SAVE);
            close();
        } else {
            reinit();
        }
    }

    public E save() {
        E entity = getEntity();
        try {
            E result = getEntityService().save(entity, getClassifiers());
            setSelectedValue(result);
            return result;
        } catch (Exception ex) {
            setSelectedValue(null);
            handleException("entity: " + entity, ex);
        }

        return entity;
    }

    public E confirm() {
        E entity = getEntity();
        try {
            E result = getEntityService().confirm(entity, getClassifiers());
            setSelectedValue(result);
            return result;
        } catch (Exception ex) {
            setSelectedValue(null);
            handleException("entity: " + entity, ex);
        }

        return entity;
    }

    protected void reinit() {
        getBindingGroup().unbind();
        bindingGroup = null;
        initData();
    }

    protected void rebind(List<Binding> bindings) {
        for(Binding binding : bindings) {
            rebind(binding);
        }
    }

    protected void rebind(Binding binding) {
        binding.unbind();
        binding.bind();
    }

    public void saveAndRefreshUI(List<Binding> bindings) {
        save();
        rebind(bindings);
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
        if (entityFormButtonPanel == null) {
            entityFormButtonPanel = new EntityFormButtonPanel();
        }

        return entityFormButtonPanel;
    }

    private void initComponents() {
        setName("Form"); // NOI18N
        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(920, 480));

        EntityFormProcessor formProcessor = getEntityFormProcessor();
        add(getMainComponent(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.PAGE_END);

        Map<String, FormContainer> formContainerMap = getFormContainerMap();
        for (ContainerEntity containerEntity : formProcessor.getContainerEntities()) {
            JComponent jContainer = containerEntity.getJContainer();
            FormContainer formContainer = formContainerMap.get(jContainer.getName());
            if(!evaluateBooleanExpression(formContainer.showCondition())) {
                removeContainer(jContainer);
                continue;
            }
            AbstractEntityListPanel listPanel = getListPanel(containerEntity);
            jContainer.setLayout(new BorderLayout());
            jContainer.add(listPanel, BorderLayout.CENTER);
        }

        String title;
        if ((title = getResourceString("form.title")) != null && title.trim().length() > 0) {
            setTitle(title);
        }
    }

    protected void removeContainer(JComponent jContainer) {
        JComponent parent = (JComponent) jContainer.getParent();
        parent.remove(jContainer);
    }

    protected Class getListPanelClass(Class entityClass) {
        EntityAttributes<Property> entityAttributes;
        if((entityAttributes = AcaciaEntityAttributes.getEntityAttributesMap().get(entityClass.getName())) == null) {
            return null;
        }

        try {
            return Class.forName(entityAttributes.getListFormClassName());
        } catch(ClassNotFoundException ex) {
            throw new RuntimeException("Can not find list panel class for entity: " + entityClass, ex);
        }
    }

    protected AbstractEntityListPanel getListPanel(ContainerEntity containerEntity) {
        Class containerEntityClass = containerEntity.getEntityClass();
        Class listPanelClass = getListPanelClass(containerEntityClass);
        RelationshipType relationshipType;
        if (RelationshipType.OneToMany.equals(relationshipType = containerEntity.getRelationshipType())) {
            AbstractEntityListPanel<E> listPanel;
            if(listPanelClass != null) {
                Object[] args = new Object[] {this};
                try {
                    listPanel = (AbstractEntityListPanel<E>)ConstructorUtils.invokeConstructor(
                            listPanelClass, args);
                } catch(Exception ex) {
                    try {
                        args = new Object[] {getEntity()};
                        listPanel = (AbstractEntityListPanel<E>)ConstructorUtils.invokeConstructor(
                                listPanelClass, args);
                    } catch(Exception ex1) {
                        throw new EntityPanelException("listPanelClass=" + listPanelClass +
                                ", args=" + Arrays.asList(args), ex1);
                    }
                }
                return listPanel;
            }
        }

        throw new IllegalStateException("Can not find list panel for containerEntityClass=" + containerEntityClass +
                " of containerEntity=" + containerEntity);
    }

    protected AbstractEntityListPanel getEntityListPanel() {
        return entityListPanel;
    }

    public JComponent getMainComponent() {
        return getEntityFormProcessor().getMainComponent();
    }

    public JComponent getContainer(String name) {
        return getEntityFormProcessor().getContainer(name);
    }

    public JComponent getJComponent(String name) {
        return getEntityFormProcessor().getJComponent(name);
    }

    public Map<String, JComponent> getContainersMap() {
        return getEntityFormProcessor().getContainersMap();
    }

    public Map<String, JComponent> getJComponentsMap() {
        return getEntityFormProcessor().getJComponentsMap();
    }

    public Map<String, Property> getPropertiesMap() {
        return getEntityFormProcessor().getPropertiesMap();
    }

    public Map<String, String> getPropertyNamesMap() {
        return getEntityFormProcessor().getPropertyNamesMap();
    }

    public String getPropertyName(String componentName) {
        return getEntityFormProcessor().getPropertyName(componentName);
    }

    public Property getProperty(String componentName) {
        return getEntityFormProcessor().getProperty(componentName);
    }

    public Set<JComponent> getJComponentsByPropertyName(String propertyName) {
        return getEntityFormProcessor().getJComponentsByPropertyName(propertyName);
    }

    public <C extends JComponent> C getJComponentByPropertyName(String propertyName, Class<C> componentClass) {
        return getEntityFormProcessor().getJComponentByPropertyName(propertyName, componentClass);
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

    public void addDependencies(String propertyName, List<EntityProperty> pdList) {
        if(pdList == null || pdList.size() == 0) {
            return;
        }

        PropertyDependencies propertyDependencies;
        if((propertyDependencies = getPropertyDependencies(propertyName)) == null) {
            propertyDependencies = new PropertyDependencies(propertyName);
            putPropertyDependencies(propertyName, propertyDependencies);
        }

        for(EntityProperty pd : pdList) {
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
        return getEntityFormProcessor().getContainerDependenciesMap();
    }

    protected List<Unit> getUnits(UnitType unitType, LogicUnitType logicUnitType) {
        return getEntityFormProcessor().getEntityLogicUnits(unitType, logicUnitType);
    }

    protected List<Unit> getUnits(UnitType unitType) {
        return getEntityFormProcessor().getEntityLogicUnits(unitType);
    }

    public Object getService(String serviceName) {
        return ServiceManager.getService(serviceName);
    }

    protected ELProperty create(String expression) {
        return ELProperty.create(getELContext(), expression);
    }

    protected ELContext getELContext() {
        if(elContext == null) {
            ELContext systemELContext = SystemUtils.getSystemELContext();
            elContext = new DefaultELContext(systemELContext);
        }

        return elContext;
    }

    protected Binding getBinding(String propertyName, Class<? extends JComponent> componentClass) {
        return getBinding(getJComponentByPropertyName(propertyName, componentClass).getName());
    }

    protected Binding getBinding(String componentName) {
        return getBindingGroup().getBinding(componentName);
    }

    public Component getComponent(String componentName) {
        return getComponent(this, componentName);
    }

    protected Component getComponent(Container parent, String componentName) {
        for(Component component : parent.getComponents()) {
            if(componentName.equals(component.getName())) {
                return component;
            }
            if(component instanceof Container) {
                Component result;
                if((result = getComponent((Container)component, componentName)) != null) {
                    return result;
                }
            }
        }

        return null;
    }

    public List<Component> getAllComponents() {
        return getAllComponents(this);
    }

    protected List<Component> getAllComponents(Container parent) {
        List<Component> components = new ArrayList<Component>();
        for(Component component : parent.getComponents()) {
            components.add(component);
            if(component instanceof Container) {
                List<Component> result;
                if((result = getAllComponents((Container)component)) != null && result.size() > 0) {
                    components.addAll(result);
                }
            }
        }

        return components;
    }

    protected <T extends JComponent> T getJComponent(Container parent, Class<T> componentClass) {
        for(Component component : parent.getComponents()) {
            if(componentClass.equals(component.getClass())) {
                return (T)component;
            }
            if(component instanceof Container) {
                T result;
                if((result = getJComponent((Container)component, componentClass)) != null) {
                    return result;
                }
            }
        }

        return null;
    }

    protected <T extends JComponent> T getJComponent(String parentContainerName, Class<T> componentClass) {
        Component parentContainer;
        if((parentContainer = getComponent(parentContainerName)) == null) {
            return null;
        }

        return getJComponent((Container)parentContainer, componentClass);
    }

    protected Map<String, FormContainer> getFormContainerMap() {
        return getEntityFormProcessor().getFormContainerMap();
    }
}
