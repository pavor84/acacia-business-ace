/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.data;

import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.ComplexProduct;
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

    private EntityManagerFactory emf;
    private EntityManager em;
    
    /*public void persist(Object object) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("ProductAssemblingPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }*/

    public ProductTest() {
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