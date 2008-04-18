/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.test;

import com.cosmos.acacia.crm.data.Product;
import java.util.List;
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
public class TestTableTest
{
    private EntityManagerFactory emf;
    private EntityManager em;

    public TestTableTest() {
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

    /**
     * Test of setDataColumn method, of class TestTable.
     */
    @Test
    public void setDataColumn()
    {
        em.getTransaction().begin();

        TestTable tt = new TestTable();
        tt.setDataColumn(1);
        tt.setDataType("Integer");
        em.persist(tt);

        tt = new TestTable();
        tt.setDataColumn((long)2);
        tt.setDataType("Long");
        em.persist(tt);

        tt = new TestTable();
        tt.setDataColumn("Miro");
        tt.setDataType("String");
        em.persist(tt);

        em.getTransaction().commit();
    }

    /**
     * Test of getDataColumn method, of class TestTable.
     */
    @Test
    public void getDataColumn()
    {
        Query q = em.createQuery("select tt from TestTable tt");
        List<TestTable> results = q.getResultList();
        for(TestTable tt : results)
        {
            Object value = tt.getDataColumn();
            System.out.println("tt.getDataType(): " + tt.getDataType() + ", value type: " + value.getClass().getName() + ", value: " + value);
        }
    }


}