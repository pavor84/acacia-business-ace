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
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.util.BeanUtils;
import java.awt.LayoutManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
    private static final String ACACIA_FORM_ANNOTATION_PREFIX =
            ACACIA_ANNOTATION_PREFIX + "Form";
    private static final String FORM_ANNOTATION_CLASS_NAME = Form.class.getName();
    private static final int INITIAL_VALUE = 1000000000;
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
                    if((title = resourceMap.getString(jContainer.getName() + ".TabConstraints.tabTitle")) == null ||
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
                Property property = field.getAnnotation(Property.class);
                String propertyName = field.getName();
                if (annotation instanceof FormComponent) {
                    FormComponent formComponent = (FormComponent) annotation;
                    Component component = formComponent.component();
                    JComponent jComponent = getComponent(component, field);
                    String componentName = jComponent.getName();
                    propertiesMap.put(componentName, property);
                    propertyNamesMap.put(componentName, propertyName);
                    String componentConstraints = component.componentConstraints();
                    String parentContainerName = formComponent.parentContainerName();

                } else if (annotation instanceof FormComponentPair) {
                    FormComponentPair componentPair = (FormComponentPair) annotation;
                    Component component = componentPair.firstComponent();
                    JComponent jComponent = getComponent(component, field);
                    String componentName = jComponent.getName();
                    propertiesMap.put(componentName, property);
                    propertyNamesMap.put(componentName, propertyName);
                    String componentConstraints = component.componentConstraints();
                    String parentContainerName = componentPair.parentContainerName();
                    JComponent container = getContainer(parentContainerName);
                    if ("".equals(componentConstraints)) {
                        container.add(jComponent);
                    } else {
                        container.add(jComponent, componentConstraints);
                    }
                    componentsMap.put(jComponent.getName(), jComponent);

                    component = componentPair.secondComponent();
                    jComponent = getComponent(component, field);
                    componentName = jComponent.getName();
                    propertiesMap.put(componentName, property);
                    propertyNamesMap.put(componentName, propertyName);
                    componentConstraints = component.componentConstraints();
                    if ("".equals(componentConstraints)) {
                        container.add(jComponent);
                    } else {
                        container.add(jComponent, componentConstraints);
                    }
                    componentsMap.put(jComponent.getName(), jComponent);
                }
            }
        }
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

    protected JComponent getComponent(String componentName) {
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

    private JComponent getComponent(Component component, Field field) {
        JComponent jComponent;
        Class<? extends JComponent> componentClass = component.componentClass();
        try {
            jComponent = componentClass.newInstance();
        } catch (Exception ex) {
            throw new EntityFormException("Can not create instance of " + componentClass, ex);
        }

        String text;
        if ((jComponent instanceof JLabel || jComponent instanceof AbstractButton)
                && (!"".equals(text = component.text()))) {
            ((JLabel) jComponent).setText(text);
        }

        String componentName = getComponentName(field, componentClass);
        jComponent.setName(componentName);

        BeanUtils beanUtils = BeanUtils.getInstance();
        for (ComponentProperty componentProperty : component.componentProperties()) {
            try {
                beanUtils.setProperty(jComponent, componentProperty.name(), componentProperty.value());
            } catch (Exception ex) {
                throw new EntityFormException("Can not set propery " + componentProperty.name() +
                        " with value " + componentProperty.value() +
                        " of " + jComponent, ex);
            }
        }

        initJComponentResources(jComponent);

        return jComponent;
    }

    protected void initJComponentResources(JComponent jComponent) {
        resourceMap.injectComponent(jComponent);
//        if (!(jComponent instanceof JLabel || jComponent instanceof AbstractButton)) {
//            return;
//        }
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

        jContainer.setName(formContainer.name());

        Border border;
        if ((border = getBorder(container.componentBorder(), jContainer)) != null) {
            jContainer.setBorder(border);
        }

        RelationshipType relationshipType = formContainer.relationshipType();
        Class containerEntityClass;
        if((!RelationshipType.None.equals(relationshipType)) &&
                (containerEntityClass = formContainer.entityClass()) != void.class) {
            ContainerEntity containerEntity = new ContainerEntity(jContainer, relationshipType, containerEntityClass);
            containerEntities.add(containerEntity);
        }

        initJComponentResources(jContainer);

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
        int counter = INITIAL_VALUE;
        for (Form form : forms) {
            for (FormContainer formContainer : form.formContainers()) {
                int index;
                if ((index = formContainer.componentIndex()) < 0) {
                    map.put(++counter, formContainer);
                } else {
                    if(map.containsKey(index)) {
                        throw new DuplicateComponentIndexException(index,
                                map.get(index),
                                formContainer);
                    }
                    map.put(index, formContainer);
                }
            }
        }

        return new ArrayList<FormContainer>(map.values());
    }

    private List<Field> getAnnotatedFields(Class entityClass) {
        int counter = INITIAL_VALUE;
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

        int counter = INITIAL_VALUE;
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