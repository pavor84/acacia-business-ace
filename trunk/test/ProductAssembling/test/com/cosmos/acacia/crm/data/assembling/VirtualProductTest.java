/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.assembling;

import com.cosmos.acacia.crm.data.SimpleProduct;
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
public class VirtualProductTest
{
    private final static Logger logger = Logger.getLogger(VirtualProductTest.class.getName());

    private EntityManagerFactory emf;
    private EntityManager em;


    public VirtualProductTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("ProductAssemblingPU");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void persistProducts()
    {
        em.getTransaction().begin();
        SimpleProduct sp = new SimpleProduct();
        sp.setProductCode("SP");
        sp.setProductPrice(BigDecimal.valueOf(12.3));
        em.persist(sp);

        RealProduct rp = new RealProduct();
        rp.setSimpleProduct(sp);
        em.persist(rp);

        AssemblingCategory ac = new AssemblingCategory();
        ac.setCategoryCode("AC");
        ac.setCategoryName("AC");
        em.persist(ac);

        AssemblingSchema as = new AssemblingSchema();
        as.setAssemblingCategory(ac);
        as.setSchemaCode("AS");
        as.setSchemaName("AS");
        em.persist(as);
        em.getTransaction().commit();

        Query q = em.createQuery("select p from VirtualProduct p");
        List<VirtualProduct> results = q.getResultList();
        for(VirtualProduct result : results)
        {
            logger.info("result=" + result.toString());
        }

        em.getTransaction().begin();
        em.remove(rp);
        em.remove(sp);
        em.remove(as);
        em.remove(ac);
        em.getTransaction().commit();
    }

}