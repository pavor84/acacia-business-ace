/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miro
 */
public class ProductTest
{
    private final static Logger logger = Logger.getLogger(ProductTest.class.getName());

    private static EntityManagerFactory emf;
    private EntityManager em;
    
    public ProductTest() {
    }

    @BeforeClass
    public static void setUpClass()
        throws Exception
    {
        emf = Persistence.createEntityManagerFactory("ProductAssemblingPU");
    }

    @AfterClass
    public static void tearDownClass()
        throws Exception
    {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
    }

    @Test
    public void persistProducts()
    {
        SimpleProduct sp = new SimpleProduct();
        sp.setProductCode("SP");
        sp.setProductPrice(BigDecimal.valueOf(12.3));
        em.getTransaction().begin();
        em.persist(sp);
        em.getTransaction().commit();

        ComplexProduct cp = new ComplexProduct();
        cp.setProductCode("CP");
        cp.setProductPrice(BigDecimal.valueOf(98.7));
        em.getTransaction().begin();
        em.persist(cp);
        em.getTransaction().commit();

        Query q = em.createQuery("select p from Product p");
        List<Product> results = q.getResultList();
        for(Product result : results)
        {
            logger.info("result=" + result.toString());
        }

        em.getTransaction().begin();
        em.remove(sp);
        em.remove(cp);
        em.getTransaction().commit();
    }

}