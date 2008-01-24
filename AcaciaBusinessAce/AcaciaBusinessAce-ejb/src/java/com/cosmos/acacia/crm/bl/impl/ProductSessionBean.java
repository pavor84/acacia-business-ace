/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.test.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Miro
 */
@Stateless
public class ProductSessionBean implements ProductSessionRemote, ProductSessionLocal {
    //@PersistenceContext(unitName="AcaciaPU")
    //@PersistenceContext(unitName="AcaciaBusinessLogicPU")
    @PersistenceContext
    private EntityManager em;

    public List<Product> getProducts() {
        System.out.println("em: " + em);
        if(em != null)
        {
            List data = em.createQuery("select p from Product p").getResultList();
            if(data != null)
                System.out.println("data.className: " + data.getClass().getName());
            System.out.println("data: " + data);
            return new ArrayList<Product>(data);
        }
        return null;
    }

    //public void persist(Object object) {
    //    em.persist(object);
    //}
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
