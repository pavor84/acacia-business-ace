/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.test.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
    //@PersistenceContext(unitName="AcaciaPU")
    //@PersistenceContext(unitName="AcaciaBusinessLogicPU")
    @PersistenceContext
    private EntityManager em;

    public void beginTransaction()
    {
        em.getTransaction().begin();
    }

    public void commitTransaction()
    {
        em.getTransaction().begin();
    }

    public void rollbackTransaction()
    {
        em.getTransaction().rollback();
    }

    public EntityTransaction getTransaction()
    {
        return em.getTransaction();
    }

    @PostConstruct
    public void init()
    {
        System.out.println("init()");
    }

    @PreDestroy
    public void destroy()
    {
        EntityTransaction tx = getTransaction();
        System.out.println("tx.isActive(): " + tx.isActive());
        if(tx.isActive())
            tx.rollback();
        System.out.println("destroy()");
    }

    public List<Product> getProducts() {
        System.out.println("em: " + em);
        if(em != null)
        {
            //beginTransaction();
            try
            {
                Query q = em.createQuery("select p from Product p");
                List data = q.getResultList();
                if(data != null)
                {
                    System.out.println("data.className: " + data.getClass().getName());
                    if(data.isEmpty())
                    {
                        System.out.println("beginTransaction()");
                        Product product = new Product(new BigDecimal(1), "1st Product");
                        em.persist(product);
                        product = new Product(new BigDecimal(2), "2nd Product");
                        em.persist(product);
                        product = new Product(new BigDecimal(3), "3rd Product");
                        em.persist(product);
                        System.out.println("commitTransaction()");
                        //commitTransaction();

                        //beginTransaction();
                        System.out.println("q.getResultList()");
                        data = q.getResultList();
                    }
                }

                System.out.println("data: " + data);
                return new ArrayList<Product>(data);
            }
            catch(Exception ex)
            {
                System.out.println("rollbackTransaction()");
                //rollbackTransaction();
                ex.printStackTrace();
            }
        }

        return null;
    }

    public JTableBinding getProductTableBinding(JTable targetTable) {
        AutoBinding.UpdateStrategy updateStrategy = AutoBinding.UpdateStrategy.READ_WRITE;
        JTableBinding tableBinding = SwingBindings.createJTableBinding(updateStrategy, getProducts(), targetTable);

        // Determine the access for the Table depending on user, role, current object and hidden columns
        // Restore the columns state depending on the previous session (visible, size, position)

        ColumnBinding columnBinding = tableBinding.addColumnBinding(ELProperty.create("${productId}"));
        columnBinding.setColumnName("Product Id");
        columnBinding.setColumnClass(BigDecimal.class);
        columnBinding.setEditable(false);
        //columnBinding.setVisible(false);

        columnBinding = tableBinding.addColumnBinding(ELProperty.create("${productName}"));
        columnBinding.setColumnName("Product Name");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(true);
        //columnBinding.setVisible(true);

        return tableBinding;
    }


    //public void persist(Object object) {
    //    em.persist(object);
    //}
    
    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
 
}
