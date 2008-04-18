/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Miro
 */
@Stateless
public class ProductSessionBean implements ProductSessionRemote, ProductSessionLocal {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @PostConstruct
    public void init()
    {
        System.out.println("init()");
    }

    @PreDestroy
    public void destroy()
    {
    }

    @EJB
    private DataObjectTypeLocal dotLocal;

    public List<SimpleProduct> getProducts() {
        System.out.println("em: " + em);
        System.out.println("ems: " + esm);
        try
        {
            String sql = "select p from Product p where p.dataObject.parentDataObject = null and p.dataObject.deleted = false";
            Query q = em.createQuery(sql);
            List<SimpleProduct> data = q.getResultList();
            if(data != null)
            {
                System.out.println("data.className: " + data.getClass().getName());
                if(data.isEmpty())
                {
                    SimpleProduct product = SimpleProduct.newTestProduct("1st Product", "p1");
                    esm.persist(em, product);
                    product = SimpleProduct.newTestProduct("2nd Product", "p2");
                    esm.persist(em, product);
                    product = SimpleProduct.newTestProduct("3rd Product", "p3");
                    esm.persist(em, product);
                    System.out.println("commitTransaction()");

                    System.out.println("q.getResultList()");
                    data = q.getResultList();

                    product = data.get(0);
                    product.setProductName(product.getProductName() + " - " + new Date());
                    esm.persist(em, product);

                    product = data.get(1);
                    esm.remove(em, product);
                }
            }

            System.out.println("data: " + data);
            return new ArrayList<SimpleProduct>(data);
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    //int counter = 0;
    //int stop = 99; //8
    public EntityProperties getProductEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(SimpleProduct.class);
        // TODO: Check which columns to be shown, visible, editable, etc.
        // depending of User Roles, Current Object, etc.
        /*for(Object key : entityProperties.getKeys().toArray())
        {
            if(!("productName".equals(key) || "productId".equals(key)))
            {
                PropertyDetails property = entityProperties.getPropertyDetails((String)key);
                boolean isBoolean = boolean.class.getName().equals(property.getPropertyClassName());
                if(!isBoolean)
                    isBoolean = Boolean.class.getName().equals(property.getPropertyClassName());
                isBoolean = false;
                if(isBoolean || counter++ >= stop)
                    entityProperties.removePropertyDetails((String)key);
                else
                    System.out.println("Property Name: " + key);
            }
        }*/

        return entityProperties;
    }

    //public void persist(Object object) {
    //    em.persist(object);
    //}
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
