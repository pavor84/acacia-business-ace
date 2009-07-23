/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.entity;

import com.cosmos.acacia.annotation.BorderType;
import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.ComponentBorder;
import com.cosmos.acacia.annotation.ComponentProperty;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponent;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Layout;
import com.cosmos.acacia.annotation.LogicUnitType;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.Unit;
import com.cosmos.acacia.annotation.UnitType;
import com.cosmos.util.BeanUtils;
import java.awt.LayoutManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.Scrollable;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.ResourceMap;
import static com.cosmos.acacia.annotation.Component.NullJComponent;
import static com.cosmos.acacia.entity.EntityService.NullEntityService;

/**
 *
 * @author Miro
 */
public class EntityFormProcessor {

    private static final String OBJECT_CLASS_NAME =
            Object.class.getName();
    private static final String ACACIA_ANNOTATION_PREFIX =
            "com.cosmos.acacia.annotation.";
    private static final String FORM_ANNOTATION_CLASS_NAME = Form.class.getName();
    public static final int INITIAL_INDEX_VALUE = 0x1FFFFFFF;
    public static final int CUSTOM_INDEX_VALUE = 0x3FFFFFFF;
    //
    private Class entityClass;
    private ResourceMap resourceMap;
    //
    private Class<? extends EntityService> entityServiceClass;
    private Map<String, JComponent> containersMap;
    private Map<String, JComponent> componentsMap;
    private Map<String, Property> propertiesMap;
    private Map<String, String> propertyNamesMap;
    private List<ContainerEntity> containerEntities;
    private Map<String, Set<String>> containerDependenciesMap;
    private Map<UnitType, List<Unit>> entityListLogicUnitsMap;
    private Map<UnitType, List<Unit>> entityLogicUnitsMap;

    public EntityFormProcessor(Class entityClass, ResourceMap resourceMap) {
        this.entityClass = entityClass;
        this.resourceMap = resourceMap;
        init();
    }

    private void init() {
        containersMap = new TreeMap<String, JComponent>();
        componentsMap = new TreeMap<String, JComponent>();
        propertiesMap = new TreeMap<String, Property>();
        propertyNamesMap = new TreeMap<String, String>();
        containerEntities = new ArrayList<ContainerEntity>();
        containerDependenciesMap = new TreeMap<String, Set<String>>();
        entityListLogicUnitsMap = new EnumMap<UnitType, List<Unit>>(UnitType.class);
        entityLogicUnitsMap = new EnumMap<UnitType, List<Unit>>(UnitType.class);

        List<Form> forms = getAnnotations(entityClass);
        for (Form form : forms) {
            JComponent container = null;
            String containerName = "";
            if (!containersMap.containsKey("")) {
                container = getContainer(form.mainContainer());
            }

            if (container != null) {
                getContainerType(container); // Check for compatibility
                if (!containersMap.containsKey(containerName)) {
                    containersMap.put(containerName, container);
                }
            }

            if (entityServiceClass == null && form.serviceClass() != NullEntityService.class) {
                entityServiceClass = form.serviceClass();
            }

            addUnits(entityListLogicUnitsMap, form.logic().entityListLogic().units());
            addUnits(entityLogicUnitsMap, form.logic().entityLogic().units());
        }
        if (entityServiceClass == null) {
            throw new EntityFormException("The Entity Service for entity '" + entityClass + "' is not initialized.");
        }

        List<FormContainer> formContainers = getAnnotations(forms);
        for (FormContainer formContainer : formContainers) {
            JComponent container = getContainer(formContainer);
            String containerName = container.getName();
            getContainerType(container); // Check for compatibility
            if (!containersMap.containsKey(containerName)) {
                containersMap.put(containerName, container);
            }
        }

        if (!containersMap.containsKey("")) {
            throw new EntityFormException("The mainContainer can not be null. The com.cosmos.acacia.annotation.Form is required.");
        }

        if (containersMap.size() > 0) {
            for (FormContainer formContainer : formContainers) {
                Component component = formContainer.container();
                String componentConstraints = component.componentConstraints();
                String containerName = formContainer.name();
                String parentContainerName = formContainer.parentContainerName();
                JComponent jContainer = containersMap.get(containerName);
                JComponent parentContainer = containersMap.get(parentContainerName);
                if (parentContainer == null) {
                    throw new EntityFormException("Missing container with name: " + parentContainerName);
                }
                if(parentContainer instanceof JTabbedPane) {
                    String title;
                    String name = jContainer.getName();
                    if((title = resourceMap.getString(name + ".TabConstraints.tabTitle")) == null ||
                            title.trim().length() == 0) {
                        title = formContainer.title();
                    }
                    Icon icon = null;
                    String tooltip = null;
                    ((JTabbedPane)parentContainer).addTab(title, jContainer);
                } else if ("".equals(componentConstraints)) {
                    parentContainer.add(jContainer);
                } else {
                    parentContainer.add(jContainer, componentConstraints);
                }
            }
        }

        List<Field> fields = getAnnotatedFields(entityClass);
        for (Field field : fields) {
            for (Annotation annotation : getAnnotations(field)) {
                if (annotation instanceof FormComponent) {
                    FormComponent formComponent = (FormComponent) annotation;
                    String parentContainerName = formComponent.parentContainerName();

                    Component component = formComponent.component();
                    getJComponent(component, parentContainerName, field);
                } else if (annotation instanceof FormComponentPair) {
                    FormComponentPair componentPair = (FormComponentPair) annotation;
                    String parentContainerName = componentPair.parentContainerName();

                    Component component = componentPair.firstComponent();
                    getJComponent(component, parentContainerName, field);

                    component = componentPair.secondComponent();
                    getJComponent(component, parentContainerName, field);
                }
            }
        }
    }

    protected JComponent getJComponent(Component component, String parentContainerName, Field field) {
        Property property = field.getAnnotation(Property.class);
        String propertyName = field.getName();
        JComponent jComponent = getComponent(component, field);

        initJComponent(jComponent, property, propertyName);
        if(jComponent instanceof JScrollPane) {
            initJComponent(getJComponent((JScrollPane)jComponent), property, propertyName);
        }

        String componentConstraints = component.componentConstraints();
        JComponent container = getContainer(parentContainerName);
        if ("".equals(componentConstraints)) {
            container.add(jComponent);
        } else {
            container.add(jComponent, componentConstraints);
        }

        return jComponent;
    }

    protected JComponent getJComponent(JScrollPane scrollPane) {
        return (JComponent)scrollPane.getViewport().getView();
    }

    protected void initJComponent(JComponent jComponent, Property property, String propertyName) {
        String componentName = jComponent.getName();
        propertiesMap.put(componentName, property);
        propertyNamesMap.put(componentName, propertyName);
        componentsMap.put(componentName, jComponent);
    }

    protected void addUnits(Map<UnitType, List<Unit>> unitsMap, Unit... units) {
        if(units == null || units.length == 0) {
            return;
        }

        for(Unit unit : units) {
            UnitType unitType = unit.unitType();
            List<Unit> list;
            if((list = unitsMap.get(unitType)) == null) {
                list = new ArrayList<Unit>();
                unitsMap.put(unitType, list);
            }
            list.add(unit);
        }
    }

    public Map<UnitType, List<Unit>> getEntityListLogicUnitsMap() {
        return entityListLogicUnitsMap;
    }

    public Map<UnitType, List<Unit>> getEntityLogicUnitsMap() {
        return entityLogicUnitsMap;
    }

    public Set<JComponent> getJComponentsByPropertyName(String propertyName) {
        Set<JComponent> set = new HashSet<JComponent>();
        for(String componentName : propertyNamesMap.keySet()) {
            if(propertyName.equals(propertyNamesMap.get(componentName))) {
                set.add(componentsMap.get(componentName));
            }
        }

        return set;
    }

    public <C extends JComponent> C getJComponentByPropertyName(String propertyName, Class<C> componentClass) {
        Set<JComponent> set = getJComponentsByPropertyName(propertyName);
        if((set = getJComponentsByPropertyName(propertyName)) != null && set.size() > 0) {
            for(JComponent jComponent : set) {
                if(componentClass.isAssignableFrom(jComponent.getClass())) {
                    return (C) jComponent;
                }
            }
        }

        return null;
    }

    protected List<Unit> getUnits(Map<UnitType, List<Unit>> unitsMap, UnitType unitType) {
        List<Unit> units;
        if((units = unitsMap.get(unitType)) != null) {
            return units;
        }

        return Collections.emptyList();
    }

    public List<Unit> getEntityListLogicUnits(UnitType unitType) {
        return getUnits(entityListLogicUnitsMap, unitType);
    }

    public List<Unit> getEntityListLogicUnits(UnitType unitType, LogicUnitType logicUnitType) {
        return getUnits(getEntityListLogicUnits(unitType), logicUnitType);
    }

    public List<Unit> getEntityLogicUnits(UnitType unitType) {
        return getUnits(entityLogicUnitsMap, unitType);
    }

    public List<Unit> getEntityLogicUnits(UnitType unitType, LogicUnitType logicUnitType) {
        return getUnits(getEntityLogicUnits(unitType), logicUnitType);
    }

    protected List<Unit> getUnits(List<Unit> list, LogicUnitType logicUnitType) {
        int size;
        if(list == null || (size = list.size()) == 0) {
            return Collections.emptyList();
        }

        List<Unit> units = new ArrayList<Unit>(size);
        for(Unit unit : list) {
            if(logicUnitType.equals(unit.logicUnitType())) {
                units.add(unit);
            }
        }

        return units;
    }

    private String getComponentName(Field field, Class<? extends JComponent> componentClass) {
        return field.getName() + componentClass.getSimpleName();
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public Class<? extends EntityService> getEntityServiceClass() {
        return entityServiceClass;
    }

    public List<ContainerEntity> getContainerEntities() {
        return containerEntities;
    }

    public Map<String, JComponent> getContainersMap() {
        return containersMap;
    }

    public Map<String, JComponent> getJComponentsMap() {
        return componentsMap;
    }

    public Map<String, Property> getPropertiesMap() {
        return propertiesMap;
    }

    public Map<String, String> getPropertyNamesMap() {
        return propertyNamesMap;
    }

    public String getPropertyName(String componentName) {
        return propertyNamesMap.get(componentName);
    }

    public Property getProperty(String componentName) {
        return propertiesMap.get(componentName);
    }

    public JComponent getJComponent(String componentName) {
        return componentsMap.get(componentName);
    }

    protected ResourceMap getResourceMap() {
        return resourceMap;
    }

    private ContainerType getContainerType(JComponent container) {
        if (container instanceof JPanel) {
            return ContainerType.Panel;
        } else if (container instanceof JTabbedPane) {
            return ContainerType.TabbedPanel;
        } else if (container instanceof JSplitPane) {
            return ContainerType.SplitPane;
        } else if (container instanceof JScrollPane) {
            return ContainerType.ScrollPane;
        } else if (container instanceof JToolBar) {
            return ContainerType.ToolBar;
        } else if (container instanceof JDesktopPane) {
            return ContainerType.DesktopPane;
        } else if (container instanceof JInternalFrame) {
            return ContainerType.InternalFrame;
        } else if (container instanceof JLayeredPane) {
            return ContainerType.LayeredPane;
        }

        throw new RuntimeException("Unsupported container type: " + container);
    }

    public Map<String, Set<String>> getContainerDependenciesMap() {
        return containerDependenciesMap;
    }

    public Set<String> getContainerDependencies(String containerName) {
        return containerDependenciesMap.get(containerName);
    }

    private JComponent getComponent(Component component, Field field) {
        JComponent jComponent;
        Class<? extends JComponent> componentClass = component.componentClass();
        try {
            jComponent = componentClass.newInstance();
        } catch (Exception ex) {
            throw new EntityFormException("Can not create instance of " + componentClass, ex);
        }

        String text;
        if(!"".equals(text = component.text())) {
            if (jComponent instanceof JLabel) {
                ((JLabel) jComponent).setText(text);
            } else if (jComponent instanceof AbstractButton) {
                ((AbstractButton) jComponent).setText(text);
            }
        }

        String componentName = getComponentName(field, componentClass);
        jComponent.setName(componentName);

        initComponentProperties(jComponent, component.componentProperties());

        initJComponentResources(jComponent);

        if(jComponent instanceof Scrollable && component.scrollable()) {
            JScrollPane scrollPane = new JScrollPane();
            componentName = getComponentName(field, JScrollPane.class);
            scrollPane.setName(componentName);
            scrollPane.setViewportView(jComponent);
            componentsMap.put(scrollPane.getName(), scrollPane);
            return scrollPane;
        }

        return jComponent;
    }

    protected void initComponentProperties(JComponent jComponent, ComponentProperty[] componentProperties) {
        BeanUtils beanUtils = BeanUtils.getInstance();
        for (ComponentProperty componentProperty : componentProperties) {
            try {
                beanUtils.setProperty(jComponent, componentProperty.name(), componentProperty.value());
            } catch (Exception ex) {
                throw new EntityFormException("Can not set propery " + componentProperty.name() +
                        " with value " + componentProperty.value() +
                        " of " + jComponent, ex);
            }
        }
    }

    protected void initJComponentResources(JComponent jComponent) {
        resourceMap.injectComponent(jComponent);
    }

    public JComponent getContainer(String containerName) {
        return containersMap.get(containerName);
    }

    private JComponent getContainer(FormContainer formContainer) {
        Component container = formContainer.container();

        Class<? extends JComponent> componentClass;
        if ((componentClass = container.componentClass()) == NullJComponent.class) {
            return null;
        }

        JComponent jContainer;
        try {
            jContainer = componentClass.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        String containerName = formContainer.name();
        jContainer.setName(containerName);

        Border border;
        if ((border = getBorder(container.componentBorder(), jContainer)) != null) {
            jContainer.setBorder(border);
        }

        RelationshipType relationshipType = formContainer.relationshipType();
        String listPanelClassName;
        Class listPanelClass;
        if((listPanelClassName = formContainer.listPanelClassName()).length() > 0) {
            try {
                listPanelClass = Class.forName(listPanelClassName);
            } catch(Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            listPanelClass = null;
        }

        Class containerEntityClass;
        if((!RelationshipType.None.equals(relationshipType)) &&
                (containerEntityClass = formContainer.entityClass()) != void.class) {
            ContainerEntity containerEntity = new ContainerEntity(jContainer, relationshipType,
                    containerEntityClass, listPanelClass);
            containerEntities.add(containerEntity);
        }

        initJComponentResources(jContainer);

        String[] values;
        if((values = formContainer.depends()) != null && values.length > 0) {
            Set<String> dependencies;
            if((dependencies = containerDependenciesMap.get(containerName)) == null) {
                dependencies = new TreeSet<String>();
                containerDependenciesMap.put(containerName, dependencies);
            }
            dependencies.addAll(Arrays.asList(values));
        }

        initComponentProperties(jContainer, container.componentProperties());

        if(jContainer instanceof JTabbedPane) {
            return jContainer;
        }

        LayoutManager layoutManager = getLayoutManager(formContainer.layout());
        jContainer.setLayout(layoutManager);

        return jContainer;
    }

    private LayoutManager getLayoutManager(Layout layout) {
        LayoutManager layoutManager;
        Class<? extends LayoutManager> layoutClass = layout.layoutClass();
        if (MigLayout.class.getName().equals(layoutClass.getName())) {
            layoutManager = getMigLayout(layout);
        } else {
            try {
                layoutManager = layoutClass.newInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return layoutManager;
    }

    private MigLayout getMigLayout(Layout layout) {
        String constraints = layout.constraints();

        List<String> columnsConstraints;
        if (layout.columnsConstraints().length > 0) {
            columnsConstraints = Arrays.asList(layout.columnsConstraints());
        } else {
            columnsConstraints = new ArrayList<String>();
        }

        List<String> rowsConstraints;
        if (layout.rowsConstraints().length > 0) {
            rowsConstraints = Arrays.asList(layout.rowsConstraints());
        } else {
            rowsConstraints = new ArrayList<String>();
        }

        int size;
        List<Integer> columnsGaps;
        if ((size = layout.columnsGaps().length) > 0) {
            columnsGaps = new ArrayList<Integer>(size);
            for (int value : layout.columnsGaps()) {
                if (value < 0) {
                    columnsGaps.add(null);
                } else {
                    columnsGaps.add(value);
                }
            }
        } else {
            columnsGaps = new ArrayList<Integer>();
        }

        List<Integer> rowsGaps;
        if ((size = layout.rowsGaps().length) > 0) {
            rowsGaps = new ArrayList<Integer>(size);
            for (int value : layout.rowsGaps()) {
                if (value < 0) {
                    rowsGaps.add(null);
                } else {
                    rowsGaps.add(value);
                }
            }
        } else {
            rowsGaps = new ArrayList<Integer>();
        }

        int columnsPairs = layout.columnsPairs();
        int columnCount = layout.columnCount();
        if (columnsPairs > 0) {
            if ("".equals(constraints)) {
                constraints = MessageFormat.format(Layout.DEFAULT_CONSTRAINTS, columnsPairs << 1);
            }

            if (columnsConstraints.size() == 0) {
                for (int i = 0; i < columnsPairs; i++) {
                    int pos = i << 1;

                    Integer preferredWidth;
                    Integer maximumWidth;
                    if((preferredWidth = layout.preferredLabelWidth()) < 0) {
                        preferredWidth = null;
                    }
                    if((maximumWidth = layout.maxLabelWidth()) < 0) {
                        maximumWidth = null;
                    }
                    put(columnsConstraints, pos, 
                            MessageFormat.format(Layout.DEFAULT_COLUMNS_CONSTRAINTS_PAIR[0],
                                preferredWidth, maximumWidth));

                    if((preferredWidth = layout.preferredFieldWidth()) < 0) {
                        preferredWidth = null;
                    }
                    if((maximumWidth = layout.maxFieldWidth()) < 0) {
                        maximumWidth = null;
                    }
                    put(columnsConstraints, pos + 1,
                            MessageFormat.format(Layout.DEFAULT_COLUMNS_CONSTRAINTS_PAIR[1],
                                preferredWidth, maximumWidth));
                }
            }

            if (columnsGaps.size() == 0) {
                for (int i = 0, length = columnsPairs - 1; i < length; i++) {
                    int pos = i << 1;
                    put(columnsGaps, pos + 1, Layout.DEFAULT_COLUMNS_PAIR_GAP);
                }
            }
        } else if (columnCount > 0) {
            if ("".equals(constraints)) {
                constraints = MessageFormat.format(Layout.DEFAULT_CONSTRAINTS, columnCount);
            }
        }

        String str;
        if ((str = layout.extraConstraints()).length() > 0) {
            if (constraints.length() > 0) {
                constraints += ", " + str;
            } else {
                constraints = str;
            }
        }

        merge(columnsConstraints, layout.extraColumnsConstraints());
        merge(rowsConstraints, layout.extraRowsConstraints());

        return new MigLayout(constraints,
                toString(columnsConstraints, columnsGaps),
                toString(rowsConstraints, rowsGaps));
    }

    private void merge(List<String> list, String[] array) {
        if (array != null && array.length > 0) {
            int size = Math.max(array.length, list.size());
            for (int i = 0; i < size; i++) {
                String str1 = null;
                String str2 = null;
                if (list.size() > i) {
                    str1 = list.get(i);
                }
                if (array.length > i) {
                    str2 = array[i];
                }
                if (str1 == null) {
                    str1 = str2;
                } else if (str2 != null) {
                    str1 += ", " + str2;
                }
                put(list, i, str1);
            }
        }
    }

    private void put(List list, int pos, Object item) {
        while (list.size() <= pos) {
            list.add(null);
        }

        list.set(pos, item);
    }

    private String toString(List<String> array, List<Integer> gaps) {
        int size;
        if (array == null || (size = array.size()) == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        size = Math.max(size, gaps.size());
        for (int i = 0; i < size; i++) {
            String item;
            if (i < array.size()) {
                item = array.get(i);
            } else {
                item = null;
            }
            Integer gap;
            if (i < gaps.size()) {
                gap = gaps.get(i);
            } else {
                gap = null;
            }

            if (item != null && (item = item.trim()).length() > 0) {
                if (!item.startsWith("[")) {
                    sb.append('[');
                }
                sb.append(item);
                if (!item.endsWith("]")) {
                    sb.append(']');
                }
            } else {
                sb.append("[]");
            }

            if (gap != null) {
                sb.append(gap);
            }
        }

        if (sb.length() > 0) {
            return sb.toString();
        }

        return "";
    }

    private Border getBorder(ComponentBorder componentBorder, JComponent jContainer) {
        BorderType borderType;
        if (BorderType.NONE.equals((borderType = componentBorder.borderType()))) {
            return null;
        }

        Border border;
        switch (borderType) {
            case TitledBorder:
                String title;
                if((title = resourceMap.getString(jContainer.getName() + ".border.title")) == null ||
                        title.trim().length() == 0) {
                    title = componentBorder.title();
                }
                border = BorderFactory.createTitledBorder(title);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported borderType: " + borderType);
        }

        return border;
    }

    private List<Form> getAnnotations(Class entityClass) {
        List<Form> annotations = new ArrayList<Form>();
        do {
            Annotation[] declaredAnnotations = entityClass.getDeclaredAnnotations();
            if (declaredAnnotations != null && declaredAnnotations.length > 0) {
                for (Annotation annotation : declaredAnnotations) {
                    if (annotation.annotationType().getName().equals(FORM_ANNOTATION_CLASS_NAME)) {
                        annotations.add((Form) annotation);
                    }
                }
            }

            entityClass = entityClass.getSuperclass();
        } while (entityClass != null && !OBJECT_CLASS_NAME.equals(entityClass.getName()));

        return annotations;
    }

    private List<FormContainer> getAnnotations(List<Form> forms) {
        Map<Integer, FormContainer> map = new TreeMap<Integer, FormContainer>();
        int counter = INITIAL_INDEX_VALUE;
        for (Form form : forms) {
            for (FormContainer formContainer : form.formContainers()) {
                int index;
                if ((index = formContainer.componentIndex()) < 0) {
                    map.put(++counter, formContainer);
                } else {
                    
                    if(map.containsKey(index)) {
                        if(!formContainer.name().equals(map.get(index).name())) {
                            throw new DuplicateComponentIndexException(index,
                                    map.get(index),
                                    formContainer);
                        }
                    } else {
                        map.put(index, formContainer);
                    }
                }
            }
        }

        return new ArrayList<FormContainer>(map.values());
    }

    private List<Field> getAnnotatedFields(Class entityClass) {
        int counter = INITIAL_INDEX_VALUE;
        Map<StringIntegerComparator, Field> map = new TreeMap<StringIntegerComparator, Field>();
        do {
            Field[] declaredFields = entityClass.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    for (Annotation annotation : field.getAnnotations()) {
                        if (annotation.annotationType().getName().startsWith(ACACIA_ANNOTATION_PREFIX)) {
                            if((annotation = getFormComponentAnnotation(annotation)) == null) {
                                continue;
                            }

                            int index = -1;
                            String str = "";
                            if (annotation instanceof FormComponentPair) {
                                index = ((FormComponentPair) annotation).componentIndex();
                                str = ((FormComponentPair) annotation).parentContainerName();
                            } else if (annotation instanceof FormComponent) {
                                index = ((FormComponent) annotation).componentIndex();
                                str = ((FormComponent) annotation).parentContainerName();
                            }
                            StringIntegerComparator key;
                            if (index < 0) {
                                key = new StringIntegerComparator(str, ++counter);
                            } else {
                                key = new StringIntegerComparator(str, index);
                            }
                            map.put(key, field);
                            break;
                        }
                    }
                }
            }

            entityClass = entityClass.getSuperclass();
        } while (entityClass != null && !OBJECT_CLASS_NAME.equals(entityClass.getName()));

        return new ArrayList<Field>(map.values());
    }

    private List<Annotation> getAnnotations(Field field) {
        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
        if (declaredAnnotations == null || declaredAnnotations.length == 0) {
            return Collections.emptyList();
        }

        int counter = INITIAL_INDEX_VALUE;
        Map<Integer, Annotation> map = new TreeMap<Integer, Annotation>();
        for (Annotation annotation : declaredAnnotations) {
            if (annotation.annotationType().getName().startsWith(ACACIA_ANNOTATION_PREFIX)) {
                if((annotation = getFormComponentAnnotation(annotation)) == null) {
                    continue;
                }

                int index = -1;
                if (annotation instanceof FormComponentPair) {
                    index = ((FormComponentPair) annotation).componentIndex();
                } else if (annotation instanceof FormComponent) {
                    index = ((FormComponent) annotation).componentIndex();
                }
                if (index < 0) {
                    map.put(++counter, annotation);
                } else {
                    map.put(index, annotation);
                }
            }
        }

        return new ArrayList<Annotation>(map.values());
    }

    private Annotation getFormComponentAnnotation(Annotation annotation) {
        if(!(annotation instanceof Property)) {
            return null;
        }

        Property property = (Property)annotation;
        if(property.formComponentPair().firstComponent().componentClass() != NullJComponent.class &&
                property.formComponentPair().secondComponent().componentClass() != NullJComponent.class) {
            return property.formComponentPair();
        } else if(property.formComponent().component().componentClass() != NullJComponent.class) {
            return property.formComponent();
        }

        return null;
    }

    public JComponent getMainComponent() {
        return getContainer("");
    }

    private static class StringIntegerComparator implements Comparator<StringIntegerComparator>,
            Comparable<StringIntegerComparator> {

        private String strValue;
        private Integer intValue;

        public StringIntegerComparator(String strValue, Integer intValue) {
            this.strValue = strValue;
            this.intValue = intValue;
        }

        @Override
        public int compare(StringIntegerComparator first, StringIntegerComparator second) {
            int result;
            if ((result = first.strValue.compareTo(second.strValue)) != 0) {
                return result;
            }

            return first.intValue.compareTo(second.intValue);
        }

        @Override
        public int compareTo(StringIntegerComparator object) {
            return compare(this, object);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            return compare(this, (StringIntegerComparator) obj) == 0;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + (this.strValue != null ? this.strValue.hashCode() : 0);
            hash = 53 * hash + (this.intValue != null ? this.intValue.hashCode() : 0);
            return hash;
        }
    }
}
