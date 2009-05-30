/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.RelationshipType;
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
import com.cosmos.swingb.JBIntegerField;
import com.cosmos.swingb.SelectableListDialog;
import com.cosmos.swingb.binding.EntityBinder;
import com.cosmos.swingb.binding.EntityListBinder;
import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;

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
            } else if (jComponent instanceof JBIntegerField) {
                JBIntegerField integerField = (JBIntegerField) jComponent;
                integerField.bind(bg, entity, propertyDetails);
            }
        }

        bg.bind();

        validateForm();
    }

    @Override
    protected void entityChanged(Binding binding, PropertyStateEvent event) {
        super.entityChanged(binding, event);
        refreshRelatedComponents((JComponent)binding.getTargetObject(), event.getNewValue());
        validateForm();
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
                try {
                    PropertyUtils.setProperty(bean, setterName, newValue);
                    if (jComponent instanceof EntityListBinder) {
                        ((EntityListBinder) jComponent).refresh();
                    }
                } catch (Exception ex) {
                    throw new EntityPanelException("propertyName=" + propertyName +
                            ", newValue=" + newValue +
                            ", bean=" + bean + ", setterName=" + setterName +
                            ", jComponent=" + jComponent, ex);
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

    protected SelectableListDialog getSelectableListDialog(PropertyDetails propertyDetails,
            EntityProperties entityProps, JComponent jComponent) {
        String className;
        if ((className = propertyDetails.getSelectableListDialogClassName()) != null) {
            try {
                Class<? extends SelectableListDialog> cls =
                        (Class<? extends SelectableListDialog>) Class.forName(className);
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
            } catch (Exception ex) {
                throw new EntityPanelException("className: " + className +
                        ", propertyDetails: " + propertyDetails, ex);
            }
        }

        return new EntityListPanel(propertyDetails.getPropertyClass());
    }

    protected String getJComponentName(JComponent jComponent) {
        if(jComponent instanceof JComboBox) {
            Container parent;
            if((parent = jComponent.getParent()) instanceof JBComboList) {
                return parent.getName();
            }
        }

        return jComponent.getName();
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
            //entity = getEntityService().save(entity);
            entity = getEntityService().confirm(entity);
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValue(entity);
            if (closeAfter) {
                close();
            }
        } catch (Exception ex) {
            handleException("entity: " + entity, ex);
        }
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
}
