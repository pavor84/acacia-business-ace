/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author miro
 */
@Stateless
public class ProductsListBean implements ProductsListRemote, ProductsListLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal ems;

    public List<Product> getProducts(DataObjectBean parent) {
        Query q = em.createNamedQuery("Product.findByParentDataObjectAndDeleted");
        q.setParameter("parentDataObject", parent.getDataObject());
        q.setParameter("deleted", false);
        return new ArrayList<Product>(q.getResultList());
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
