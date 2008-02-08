/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private EntityStoreManagerLocal ems;

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
        System.out.println("ems: " + ems);
        try
        {
            Query q = em.createQuery("select p from Product p");
            List data = q.getResultList();
            if(data != null)
            {
                System.out.println("data.className: " + data.getClass().getName());
                if(data.isEmpty())
                {
                    Product product = Product.newTestProduct("1st Product", "p1");
                    ems.persist(em, product);
                    product = Product.newTestProduct("2nd Product", "p2");
                    ems.persist(em, product);
                    product = Product.newTestProduct("3rd Product", "p3");
                    ems.persist(em, product);
                    System.out.println("commitTransaction()");

                    System.out.println("q.getResultList()");
                    data = q.getResultList();

                }
            }

            try
            {
                /*DataObjectType dot = new DataObjectType();
                dot.setDataObjectTypeId(123);
                dot.setDataObjectType("Miro");
                em.persist(dot);
                System.out.println("dot: " + dot);*/
            }
            catch(Throwable ex)
            {
                ex.printStackTrace();
            }

            System.out.println("data: " + data);
            return new ArrayList<Product>(data);
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
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
