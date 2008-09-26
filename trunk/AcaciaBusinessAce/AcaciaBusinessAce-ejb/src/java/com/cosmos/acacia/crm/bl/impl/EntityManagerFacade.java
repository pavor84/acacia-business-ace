package com.cosmos.acacia.crm.bl.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class EntityManagerFacade implements EntityManagerFacadeRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Object entity) {
        em.persist(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getNamedQueryResult(String queryName, Map<String, Object> params) {
        Query q = em.createNamedQuery(queryName);
        for (String key : params.keySet()) {
            q.setParameter(key, params.get(key));
        }
        return new ArrayList(q.getResultList());
    }

    @Override
    public <T> T find(Class<T> clazz, Serializable id) {
        return em.find(clazz, id);
    }
}
