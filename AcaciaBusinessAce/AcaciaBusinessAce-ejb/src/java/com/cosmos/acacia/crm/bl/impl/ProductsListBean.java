/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.beansbinding.EntityProperties;
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
    private EntityStoreManagerLocal esm;

    public List<Product> getProducts(DataObject parent) {
        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("Product.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObject", parent);
        }
        else
        {
            q = em.createNamedQuery("Product.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<Product>(q.getResultList());
    }

    public EntityProperties getProductEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(Product.class);
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

    public Product newProduct() {
        return Product.newTestProduct(null, null);
    }

    public Product saveProduct(Product product) {
        esm.persist(em, product);
        return product;
    }

    public int deleteProduct(Product product) {
        return esm.remove(em, product);
    }
    


}
