/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.crm.data.assembling.AssemblingAlgorithm;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
public class AlgorithmTest
{
    private final static Logger logger = Logger.getLogger(AlgorithmTest.class.getName());

    private static EntityManagerFactory emf;
    private EntityManager em;

    private static final String categoryCode = "Test Assembling Category";


    public AlgorithmTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
        throws Exception
    {
        emf = Persistence.createEntityManagerFactory("ProductAssemblingPU");
        initDatabase();
    }

    public static void initDatabase()
        throws Exception
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("AssemblingAlgorithm.findByAlgorithmCode");
            AssemblingAlgorithm aAlgorithm;
            for(Algorithm.Type algorithmType : Algorithm.Type.values())
            {
                String algorithmName = algorithmType.name();
                q.setParameter("algorithmCode", algorithmName);
                try
                {
                    aAlgorithm = (AssemblingAlgorithm)q.getSingleResult();
                }
                catch(NoResultException ex)
                {
                    aAlgorithm = new AssemblingAlgorithm();
                    aAlgorithm.setAlgorithmCode(algorithmName);
                    aAlgorithm.setAlgorithmName(algorithmName);
                    em.persist(aAlgorithm);
                }
                algorithmType.setAlgorithmId(aAlgorithm.getAlgorithmId());
            }
            em.getTransaction().commit();

            AssemblingCategory aCategory;
            em.getTransaction().begin();
            q = em.createNamedQuery("AssemblingCategory.findByCategoryCode");
            q.setParameter("categoryCode", categoryCode);
            try
            {
                aCategory = (AssemblingCategory)q.getSingleResult();
            }
            catch(NoResultException ex)
            {
                aCategory = new AssemblingCategory();
                aCategory.setCategoryCode(categoryCode);
                aCategory.setCategoryName(categoryCode);
                em.persist(aCategory);
            }
            em.getTransaction().commit();

        }
        finally
        {
            if(em != null)
                em.close();
        }
    }

    @AfterClass
    public static void tearDownClass()
        throws Exception
    {
        emf.close();
    }

    @Before
    public void setUp()
    {
        em = emf.createEntityManager();
    }

    @After
    public void tearDown()
    {
        em.close();
    }

    @Test
    public void UnconditionalSelection()
    {
    }

    @Test
    public void UserSelection()
    {
    }

    @Test
    public void UserSingleSelection()
    {
    }

    @Test
    public void UserMultipleSelection()
    {
    }

    @Test
    public void RangeSelection()
    {
    }

    @Test
    public void RangeSingleSelection()
    {
    }

    @Test
    public void RangeMultipleSelection()
    {
    }

    @Test
    public void EqualsSelection()
    {
    }

    @Test
    public void EqualsSingleSelection()
    {
    }

    @Test
    public void EqualsMultipleSelection()
    {
    }

}