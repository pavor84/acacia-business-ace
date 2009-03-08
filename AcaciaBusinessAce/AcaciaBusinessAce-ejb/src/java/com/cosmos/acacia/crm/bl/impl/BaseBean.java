package com.cosmos.acacia.crm.bl.impl;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.validation.EntityValidator;
import com.cosmos.beansbinding.EntityProperties;
import java.util.Arrays;

/**
 * 
 * Created	:	04.01.2009
 * @author	Petar Milev
 *
 */
public abstract class BaseBean<T extends DataObjectBean, I extends DataObjectBean> implements BaseLocal<T, I> {
    @PersistenceContext
    protected EntityManager em;

    @EJB
    protected EntityStoreManagerLocal esm;
    
    protected Class<T> entityClass;
    protected Class<I> itemClass;
    
    public BaseBean(){
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.itemClass = (Class<I>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        if ( entityClass==null ){
            throw new IllegalArgumentException("Please specify at least the <T> generic parameters of your base bean!");
        }
    }
    
    public EntityProperties getListingEntityProperties() {

        EntityProperties entityProperties = esm.getEntityProperties(entityClass);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    public EntityProperties getDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(entityClass);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    public EntityProperties getItemsListEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(itemClass);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        
        return entityProperties;
    }

    public EntityProperties getItemDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(itemClass);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public void delete(T entity) {
        if (entity == null)
            throw new IllegalArgumentException("null: 'entity'");
        esm.remove(em, entity);
    }

    @Override
    public void deleteItem(I item) {
        if (item == null)
            throw new IllegalArgumentException("null: 'item'");
        esm.remove(em, item);
    }

    @Override
    public List<T> list(BigInteger parentDataObjectId) {
        if (parentDataObjectId == null)
            throw new IllegalArgumentException("parentDataObjectId can't be null");
        
        if ( entityClass!=null ){
            Query q = em.createNamedQuery(entityClass.getSimpleName()+".findForParent");
            q.setParameter("parentDataObjectId", parentDataObjectId);
    
            List<T> result = q.getResultList();
            return result;
        }else
            return new ArrayList<T>();
    }

    @Override
    public List<I> listItems(BigInteger parentEntityId) {
        if (parentEntityId == null)
            throw new IllegalArgumentException("parentEntityId can't be null");

        Query q = em.createNamedQuery(itemClass.getSimpleName()+".findForParent");
        q.setParameter("parentDataObjectId", parentEntityId);

        List<I> result = q.getResultList();
        return result;
    }

    @Override
    public T newEntity(BigInteger parentDataObjectId) {
        T c;
        try {
            c = entityClass.newInstance();
            c.setParentId(parentDataObjectId);
            return c;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public I newItem(BigInteger parentEntityId) {
        I item;
        try {
            item = itemClass.newInstance();
            item.setParentId(parentEntityId);
            return item;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T save(T entity) {
        EntityValidator<T> validator = getEntityValidator();
        if ( validator!=null )
            validator.validate(entity);
        
        esm.persist(em, entity);

        return entity;
    }
    
    @Override
    public I saveItem(I item) {
        EntityValidator<I> validator = getItemValidator();
        if ( validator!=null )
            validator.validate(item);
        
        esm.persist(em, item);

        return item;
    }

    /**
     * Override to specify validator. 
     * @return
     */
    protected EntityValidator<T> getEntityValidator(){
        return null;
    }
    
    /**
     * Override to specify item validator. 
     * @return
     */
    protected EntityValidator<I> getItemValidator(){
        return null;
    }
    
    /**
     * 
     * @param queryName - named query
     * @param parameters - the parameters are name/value pairs (every odd object is parameter name, and
     * every even object is parameter value).
     * @return - null if not found
     */
    protected Object getSingleResult(String queryName, Object... parameters) {
        System.out.println("queryName: " + queryName);
        System.out.println("parameters: " + Arrays.asList(parameters));
        try {
            Query q = getQuery(queryName, parameters);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * 
     * @param queryName - named query
     * @param parameters - the parameters are name/value pairs (every odd object is parameter name, and
     * every even object is parameter value).
     * @return
     */
    protected Object getResultList(String queryName, Object ... parameters) {
        Query q = getQuery(queryName, parameters);
        return q.getResultList();
    }

    protected Query getQuery(String queryName, Object[] parameters) {
        Query q = em.createNamedQuery(queryName);
        System.out.println("q.toString(): " + ((org.hibernate.ejb.QueryImpl)q).getHibernateQuery().getQueryString());
        if ( parameters!=null ){
            boolean odd = true;
            Object parName = null;
            for (Object object : parameters) {
                if ( odd ){
                    parName = object;
                    odd = false;
                }
                else{
                    q.setParameter(parName.toString(), object);
                    odd = true;
                }
            }
        }
        return q;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EntityStoreManagerLocal getEsm() {
        return esm;
    }

    public void setEsm(EntityStoreManagerLocal esm) {
        this.esm = esm;
    }
}
