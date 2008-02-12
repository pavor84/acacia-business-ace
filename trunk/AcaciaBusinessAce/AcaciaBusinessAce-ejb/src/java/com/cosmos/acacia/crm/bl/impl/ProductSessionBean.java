/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.Product;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigDecimal;
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
import javax.swing.JTable;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingbinding.SwingBindings;

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

    public List<Product> getProducts() {
        System.out.println("em: " + em);
        System.out.println("ems: " + esm);
        try
        {
            String sql = "select p from Product p where p.dataObject.parentDataObject = null and p.dataObject.deleted = false";
            Query q = em.createQuery(sql);
            List<Product> data = q.getResultList();
            if(data != null)
            {
                System.out.println("data.className: " + data.getClass().getName());
                if(data.isEmpty())
                {
                    Product product = Product.newTestProduct("1st Product", "p1");
                    esm.persist(em, product);
                    product = Product.newTestProduct("2nd Product", "p2");
                    esm.persist(em, product);
                    product = Product.newTestProduct("3rd Product", "p3");
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
            return new ArrayList<Product>(data);
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public EntityProperties getProductEntityProperties()
    {
        System.out.println("getProductEntityProperties()");
        EntityProperties entityProperties = esm.getEntityProperties(Product.class);
        System.out.println("entityProperties: " + entityProperties);
        return entityProperties;
    }

    //public void persist(Object object) {
    //    em.persist(object);
    //}
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
