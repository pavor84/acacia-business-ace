/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.entity;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.document.business.BusinessDocumentException;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.BusinessDocument;
import com.cosmos.acacia.crm.data.ChildEntityBean;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.beansbinding.EntityProperties;
import java.lang.reflect.Method;
import java.util.List;
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

    @PersistenceContext
    protected EntityManager em;
    @EJB
    protected AcaciaSessionLocal session;
    @EJB
    protected EntityStoreManagerLocal esm;

    protected <E> void initEntity(E entity) {
    }

    protected <E, I> void initItem(E entity, I item) {
    }

    protected <E> void preSave(E entity) {
    }

    protected <E> void postSave(E entity) {
    }

    protected <E> void preConfirm(E entity) {
    }

    protected <E> void postConfirm(E entity) {
    }

    protected <E> void preDelete(E entity) {
    }

    protected <E> void postDelete(E entity) {
    }

    protected <E> boolean canRead(Class<E> entityClass, Object... extraParameters) {
        return true;
    }

    protected <E, I> boolean canRead(E entity, Class<I> itemClass, Object... extraParameters) {
        return true;
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
        I item;
        try {
            item = itemClass.newInstance();
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        if(item instanceof ChildEntityBean && entity instanceof DataObjectBean) {
            ((ChildEntityBean)item).setParentEntity((DataObjectBean)entity);
        } else {
            Class entityClass = entity.getClass();
            String entityClassName = "set" + entityClass.getSimpleName();
            try {
                Method method = itemClass.getMethod(entityClassName, entityClass);
                method.invoke(item, entity);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        initItem(entity, item);

        return item;
    }

    @Override
    public <E> E save(E entity) {
        preSave(entity);
        esm.persist(em, entity);
        postSave(entity);
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
    public <E> E confirm(E entity) {
        if(!(entity instanceof BusinessDocument)) {
            throw new UnsupportedOperationException("Not supported entity: " + entity);
        }

        preConfirm(entity);

        if(entity instanceof BusinessDocument) {
            confirmBusinessDocument((BusinessDocument)entity);
        }

        postConfirm(entity);

        return entity;
    }

    @Override
    public <E> E cancel(E entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <E> void delete(E entity) {
        preDelete(entity);
        esm.remove(em, entity);
        postDelete(entity);
    }

    @Override
    public <E> EntityProperties getEntityProperties(Class<E> entityClass) {
        EntityProperties entityProperties = esm.getEntityProperties(entityClass);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public List<DbResource> getResources(Class<? extends Enum> enumClass, Class<? extends Enum>... enumCategoryClasses) {
        return esm.getResources(em, enumClass, enumCategoryClasses);
    }
}
