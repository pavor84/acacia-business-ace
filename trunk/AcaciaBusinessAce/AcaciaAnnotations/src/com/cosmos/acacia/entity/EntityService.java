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

    <E> List<E> getEntities(Class<E> entityClass, List classifiers, Object... extraParameters);

    <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, List classifiers, Object... extraParameters);

    <E, I> boolean canDo(Operation operation, E entity, Class<I> itemClass, Object... extraParameters);

    <E> E save(E entity, Object... extraParameters);

    <E> E confirm(E entity, Object... extraParameters);

    <E> E cancel(E entity);

    <E> void delete(E entity);

    <E> EntityProperties getEntityProperties(Class<E> entityClass);

    List getResources(Class<? extends Enum> enumClass, Object... categoryClassifiers);

    public static final class NullEntityService implements EntityService {

        @Override
        public <E> E newEntity(Class<E> entityClass) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <E, I> I newItem(E entity, Class<I> itemClass) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <E> List<E> getEntities(Class<E> entityClass, List classifiers, Object... extraParameters) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <E, I> List<I> getEntityItems(E entity, Class<I> itemClass, List classifiers, Object... extraParameters) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <E, I> boolean canDo(Operation operation, E entity, Class<I> itemClass, Object... extraParameters) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <E> E save(E entity, Object... extraParameters) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <E> E confirm(E entity, Object... extraParameters) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <E> E cancel(E entity) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <E> void delete(E entity) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public <E> EntityProperties getEntityProperties(Class<E> entityClass) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List getResources(Class<? extends Enum> enumClass, Object... categoryClassifiers) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
