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
public interface EntityService<T> {

    <E extends T> E newEntity(Class<E> entityClass);

    <E extends T, I extends T> I newItem(E entity, Class<I> itemClass);

    <E extends T> List<E> getEntities(Class<E> entityClass);

    <E extends T, I extends T> List<I> getEntityItems(E entity, Class<I> itemClass);

    <E extends T> E save(E entity);

    <E extends T> E confirm(E entity);

    <E extends T> E cancel(E entity);

    <E extends T> void delete(E entity);

    <E extends T> EntityProperties getEntityProperties(Class<E> entityClass);

    public static class NULL implements EntityService {

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
    }
}
