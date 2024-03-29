/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.entity;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.document.business.BusinessDocumentException;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.security.SecurityServiceLocal;
import com.cosmos.acacia.crm.data.document.BusinessDocument;
import com.cosmos.acacia.crm.data.ChildEntityBean;
import com.cosmos.acacia.crm.data.ClassifiedObject;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectEntity;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.util.BooleanUtils;
import com.cosmos.util.PersistentEntity;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author Miro
 */
//@Stateless
public abstract class AbstractEntityService implements EntityService {

    protected static final String IS_NEW_ENTITY = "IS_NEW_ENTITY";
    protected static final String CLASSIFIERS = "CLASSIFIERS";

    @PersistenceContext
    protected EntityManager em;
    @EJB
    protected AcaciaSessionLocal session;
    @EJB
    protected EntityStoreManagerLocal esm;
    @EJB
    protected SecurityServiceLocal securityService;

    protected <E> void initEntity(E entity) {
    }

    protected <E, I> void initItem(E entity, I item) {
    }

    protected <E> void preSave(E entity, Map<String, Object> parameters) {
    }

    protected <E> void postSave(E entity, Map<String, Object> parameters) {
        setEntityClassifiers(entity, parameters);
    }

    protected <E> void preConfirm(E entity, Map<String, Object> parameters) {
    }

    protected <E> void postConfirm(E entity, Map<String, Object> parameters) {
        setEntityClassifiers(entity, parameters);
    }

    protected <E> void preDelete(E entity, Map<String, Object> parameters) {
    }

    protected <E> void postDelete(E entity, Map<String, Object> parameters) {
    }

    protected <E> void setEntityClassifiers(E entity, Map<String, Object> parameters) {
        List<Classifier> classifiers;
        if((classifiers = getClassifiers(parameters)) == null
                || classifiers.size() == 0
                || !isNewEntity(parameters)
                || !(entity instanceof DataObjectEntity)) {
            return;
        }

        for(Classifier classifier : classifiers) {
            ClassifiedObject classifiedObject = new ClassifiedObject(classifier, (DataObjectEntity) entity);
            esm.persist(em, classifiedObject);
        }
    }

    @Override
    public <E, I> boolean canDo(Operation operation, E entity, Class<I> itemClass, Object... extraParameters) {
        return true;
    }

    @Override
    public <E> List<E> getEntities(Class<E> entityClass, List classifiers, Object... extraParameters) {
        throw new UnsupportedOperationException("Not supported operation for entityClass=" + entityClass +
                " with parameters " + Arrays.asList(extraParameters));
    }

    @Override
    public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, List classifiers, Object... extraParameters) {
        throw new UnsupportedOperationException("Not supported operation for itemClass=" + itemClass +
                " for entity=" + entity +
                " with parameters " + Arrays.asList(extraParameters));
    }

    @Override
    public <E> E newEntity(Class<E> entityClass) {
        E entity;
        try {
            entity = entityClass.newInstance();
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        initEntity(entity);

        return entity;
    }

    @Override
    public <E, I> I newItem(E entity, Class<I> itemClass) {
        if(entity == null) {
            throw new RuntimeException("Null Entity value for item class: " + itemClass);
        }
        I item;
        try {
            item = itemClass.newInstance();
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        if(item instanceof ChildEntityBean && entity instanceof DataObjectBean) {
            ((ChildEntityBean)item).setParentEntity((DataObjectBean)entity);
        } else {
            Method method;
            if((method = getMethod(itemClass, entity.getClass())) != null) {
                try {
                    method.invoke(item, entity);
                } catch(Exception ex) {
                    throw new RuntimeException("method=" + method + ", item=" + item + ", entity=" + entity, ex);
                }
            }
        }

        initItem(entity, item);

        return item;
    }

    protected Method getMethod(Class itemClass, Class entityClass) {
        while(entityClass != null && entityClass != Object.class) {
            String entityClassName = "set" + entityClass.getSimpleName();
            try {
                return itemClass.getMethod(entityClassName, entityClass);
            } catch(Exception ex) {
                entityClass = entityClass.getSuperclass();
            }
        }

        return null;
    }

    protected <E> Map<String, Object> getParameters(E entity, Object... extraParameters) {
        TreeMap<String, Object> parameters = new TreeMap<String, Object>();

        if(entity instanceof PersistentEntity && ((PersistentEntity) entity).getId() == null) {
            setNewEntity(parameters);
        }

        if(extraParameters != null && extraParameters.length > 0) {
            for(Object object : extraParameters) {
                if(object instanceof List) {
                    List list = (List) object;
                    if(list.size() > 0) {
                        Object item = list.get(0);
                        if(item instanceof Classifier) {
                            parameters.put(CLASSIFIERS, list);
                        }
                    }
                }
            }
        }

        return parameters;
    }

    protected List<Classifier> getClassifiers(Map<String, Object> parameters) {
        return (List<Classifier>) parameters.get(CLASSIFIERS);
    }

    @Override
    public <E> E save(E entity, Object... extraParameters) {
        Map<String, Object> parameters = getParameters(entity, extraParameters);
        preSave(entity, parameters);
        esm.persist(em, entity);
        postSave(entity, parameters);
        return entity;
    }

    protected <E extends BusinessDocument> void confirmBusinessDocument(E businessDocument) {
        if(businessDocument.getDocumentNumber() != null ||
                businessDocument.getDocumentDate() != null) {
            throw new BusinessDocumentException("The Purchase Invoice is already completed.");
        }

        esm.setDocumentNumber(em, businessDocument);

        save(businessDocument);
    }

    @Override
    public <E> E confirm(E entity, Object... extraParameters) {
        if(!(entity instanceof BusinessDocument)) {
            throw new UnsupportedOperationException("Not supported entity: " + entity);
        }

        Map<String, Object> parameters = getParameters(entity, extraParameters);
        preConfirm(entity, parameters);

        if(entity instanceof BusinessDocument) {
            confirmBusinessDocument((BusinessDocument)entity);
        }

        postConfirm(entity, parameters);

        return entity;
    }

    @Override
    public <E> E cancel(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> void delete(E entity) {
        TreeMap<String, Object> parameters = new TreeMap<String, Object>();
        preDelete(entity, parameters);
        esm.remove(em, entity);
        postDelete(entity, parameters);
    }

    @Override
    public <E> EntityProperties getEntityProperties(Class<E> entityClass) {
        EntityProperties entityProperties = esm.getEntityProperties(entityClass);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public List<DbResource> getResources(Class<? extends Enum> enumClass, Object... categoryClassifiers) {
        return esm.getResources(em, enumClass, categoryClassifiers);
    }

    protected List getUnusedItems(List items, List usedItems) {
        if(usedItems == null || usedItems.size() == 0) {
            return items;
        }

        Iterator iterator = items.iterator();
        while(iterator.hasNext()) {
            if(usedItems.contains(iterator.next())) {
                iterator.remove();
            }
        }

        return items;
    }

    protected boolean isNewEntity(Map<String, Object> parameters) {
        Object value;
        if((value = parameters.get(IS_NEW_ENTITY)) != null) {
            if(value instanceof Boolean) {
                return (Boolean) value;
            }

            return BooleanUtils.parseBoolean(String.valueOf(value));
        }

        return false;
    }

    protected void setNewEntity(Map<String, Object> parameters) {
        setNewEntity(parameters, Boolean.TRUE);
    }

    protected void setNewEntity(Map<String, Object> parameters, Boolean value) {
        parameters.put(IS_NEW_ENTITY, value);
    }

    protected void inactiveEntityProperty(EntityProperties entityProperties, String propertyName) {
        inactiveEntityProperty(entityProperties.getEntityProperty(propertyName));
    }

    protected void inactiveEntityProperty(EntityProperty entityProperty) {
        entityProperty.setEditable(false);
        entityProperty.setHiden(true);
        entityProperty.setReadOnly(true);
        entityProperty.setVisible(false);
    }
}
