/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.entity;

import com.cosmos.beansbinding.EntityProperties;
import java.util.List;

/**
 * E - The Entity (Master)
 * I - The Items of Entity (Detail)
 */
/**
 *
 * @author Miro
 */
public interface EntityService {

    <E> E newEntity(Class<E> entityClass);

    <E, I> I newItem(E entity, Class<I> itemClass);

    <E> List<E> getEntities(Class<E> entityClass);

    <E, I> List<I> getEntityItems(E entity, Class<I> itemClass);

    <E> E save(E entity);

    <E> E confirm(E entity);

    <E> E cancel(E entity);

    <E> void delete(E entity);

    <E> EntityProperties getEntityProperties(Class<E> entityClass);

    List getResources(Class<? extends Enum> enumClass, Class<? extends Enum>... enumCategoryClasses);

    public static final class NullEntityService implements EntityService {

        public NullEntityService() {
            throw new UnsupportedOperationException("The NULL class can not be used.");
        }

        public Object newEntity(Class entityClass) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Object newItem(Object entity, Class itemClass) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public List getEntities(Class entityClass) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public List getEntityItems(Object entity, Class itemClass) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Object save(Object entity) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Object confirm(Object entity) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Object cancel(Object entity) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void delete(Object entity) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public EntityProperties getEntityProperties(Class entityClass) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public List getResources(Class<? extends Enum> enumClass, Class<? extends Enum>... enumCategoryClasses) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
