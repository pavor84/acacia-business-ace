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
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetail;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBIntegerField;
import com.cosmos.swingb.JBTextField;
import com.cosmos.swingb.SelectableListDialog;
import com.cosmos.swingb.binding.EntityBinder;
import com.cosmos.swingb.binding.EntityListBinder;
import com.cosmos.util.BeanUtils;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLabel;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.BindingGroup;

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
        for(JComponent jComponent : getJComponentsMap().values()) {
            if(jComponent instanceof JLabel) {
                continue;
            }

            String componentName = jComponent.getName();
            String propertyName = getPropertyName(componentName);
            PropertyDetails propertyDetails = entityProps.getPropertyDetails(propertyName);

            if(jComponent instanceof EntityBinder) {
                ((EntityBinder)jComponent).bind(bindingGroup, entity, propertyDetails);
            } else if(jComponent instanceof EntityListBinder) {
                ((EntityListBinder)jComponent).bind(bindingGroup,
                        getSelectableListDialog(propertyDetails, entityProps),
                        entity, propertyDetails);
            } else if(jComponent instanceof JBIntegerField) {
                JBIntegerField integerField = (JBIntegerField)jComponent;
                integerField.bind(bg, entity, propertyDetails);
            }
        }

        bg.bind();
    }

    protected SelectableListDialog getSelectableListDialog(PropertyDetails propertyDetails,
            EntityProperties entityProps) {
        System.out.println("getSelectableListDialog(propertyDetails=" + propertyDetails +
                ", entityProps=" + entityProps + ")");
        String className;
        if((className = propertyDetails.getSelectableListDialogClassName()) != null) {
            System.out.println("className: " + className);
            try {
                Class<? extends SelectableListDialog> cls =
                        (Class<? extends SelectableListDialog>)Class.forName(className);
                List<PropertyDetail> parameters;
                if((parameters = propertyDetails.getSelectableListDialogConstructorParameters()) == null
                        || parameters.size() == 0) {
                    return cls.newInstance();
                }

                Constructor<? extends SelectableListDialog> constructor =
                        cls.getConstructor(getParameterTypes(parameters, entityProps));
                return constructor.newInstance(getParameters(parameters));
            } catch(Exception ex) {
                throw new EntityPanelException("className: " + className +
                        ", propertyDetails: " + propertyDetails, ex);
            }
        }

        System.out.println("new EntityListPanel(propertyDetails.getPropertyClass()): " + propertyDetails.getPropertyClass());
        return new EntityListPanel(propertyDetails.getPropertyClass());
    }

    protected Object[] getParameters(List<PropertyDetail> parameters) {
        int size;
        Object[] parameterValues = new Object[size = parameters.size()];
        for(int i = 0; i < size; i++) {
            String parameterName = parameters.get(i).getGetter();
            parameterValues[i] = getPropertyValue(parameterName);
        }

        return parameterValues;
    }

    protected Class[] getParameterTypes(List<PropertyDetail> parameters, EntityProperties entityProps) {
        int size;
        Class[] parameterTypes = new Class[size = parameters.size()];
        for(int i = 0; i < size; i++) {
            String parameterName = parameters.get(i).getGetter();
            PropertyDetails propertyDetails = entityProps.getPropertyDetails(parameterName);
            if(propertyDetails == null) {
                throw new EntityPanelException("Missing PropertyDetails with name '" + parameterName + "'");
            }
            parameterTypes[i] = propertyDetails.getPropertyClass();
        }

        return parameterTypes;
    }

    protected Object getPropertyValue(String propertyName) {
        try {
            return BeanUtils.getInstance().getProperty(entity, propertyName);
        } catch(Exception ex) {
            throw new EntityPanelException("propertyName: " + propertyName, ex);
        }
    }

    private class TextPropertyChangeHandler implements PropertyChangeListener {

        private JBTextField textField;
        private String propertyName;

        public TextPropertyChangeHandler(JBTextField textField, String propertyName) {
            this.textField = textField;
            this.propertyName = propertyName;
        }

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            textField.setText((String)getPropertyValue(propertyName));
        }
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

        for(ContainerEntity containerEntity : formProcessor.getContainerEntities()) {
            JComponent jContainer = containerEntity.getJContainer();
            Class itemEntityClass = containerEntity.getEntityClass();
            RelationshipType relationshipType;
            if(RelationshipType.OneToMany.equals(relationshipType = containerEntity.getRelationshipType())) {
                DetailEntityListPanel<E, ?> listPanel =
                        new DetailEntityListPanel(this, itemEntityClass);
                jContainer.setLayout(new BorderLayout());
                jContainer.add(listPanel, BorderLayout.CENTER);
            }
        }

        String title;
        if(getEntityListPanel().isDetailEntity()) {
            title = getResourceMap().getString("form.detailEntity.title");
        } else {
            title = getResourceMap().getString("form.entity.title");
        }
        if(title != null && title.trim().length() > 0) {
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
        return entityFormProcessor.getContainer(name);
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
}
