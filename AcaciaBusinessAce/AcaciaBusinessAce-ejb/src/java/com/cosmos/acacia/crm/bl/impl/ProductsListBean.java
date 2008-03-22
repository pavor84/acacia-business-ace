/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.crm.validation.impl.ProductValidatorLocal;
import com.cosmos.beansbinding.EntityProperties;

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
    @EJB
    private DatabaseResourceLocal databaseResource;
    @EJB
    private ProductValidatorLocal productValidator;

    @PostConstruct
    private void postConstruct()
    {
        System.out.println("postConstruct()");
//        DataObject dataObject = em.find(DataObject.class, BigInteger.ONE);
//        Organization company = new Organization();
//        company.setDataObject(dataObject);
//        company.setOrganizationId(BigInteger.ONE);
//        company.setOrganizationType();
    }

    public List<Product> getProducts(DataObject parent) {
        System.out.println("databaseResource: " + databaseResource);
        if(databaseResource != null)
            databaseResource.getDbResources(MeasurementUnit.class);

        System.out.println("MeasurementUnit.Piece: " + MeasurementUnit.Piece);

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

    public List<ProductCategory> getProductsCategories(DataObject parent) {
        System.out.println("databaseResource: " + databaseResource);
        if(databaseResource != null)
            databaseResource.getDbResources(MeasurementUnit.class);

        Query q;
        if(parent != null)
        {
            q = em.createNamedQuery("ProductCategory.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObject", parent);
        }
        else
        {
            q = em.createNamedQuery("ProductCategory.findByParentDataObjectIsNullAndDeleted");
        }
        q.setParameter("deleted", false);
        return new ArrayList<ProductCategory>(q.getResultList());
    }

    public EntityProperties getProductEntityProperties()
    {
        EntityProperties entityProperties = esm.getEntityProperties(Product.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
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
         
        productValidator.validate(product); 
           
        esm.persist(em, product);
        return product; 
    }

    public int deleteProduct(Product product) {
        return esm.remove(em, product);
    }

    public List<DbResource> getMeasureUnits() {
        return MeasurementUnit.getDbResources();
    }
    
    public List<DbResource> getMeasureUnits(MeasurementUnit.Category category) {
        return MeasurementUnit.getDbResourcesByCategory(category);
    }

    /**
     * @see com.cosmos.acacia.crm.bl.impl.ProductsListRemote#getProducers()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<?> getProducers() {
        Query qPersons = em.createNamedQuery("Person.getAllNotDeleted");
        Query qOrganizations = em.createNamedQuery("Organization.getAllNotDeleted");
        
        return new ArrayList<Object>();
//        List<?> result = new ArrayList<Object>();
//        
//        result.addAll(qPersons.getResultList());
//        result.addAll(qOrganizations.getResultList());
//        return result;
    }
}
