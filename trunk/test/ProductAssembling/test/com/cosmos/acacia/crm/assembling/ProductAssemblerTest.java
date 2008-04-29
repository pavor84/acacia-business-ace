/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.ApplicationCallbackHandler;
import com.cosmos.acacia.callback.assembling.ChoiceCallback;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Miro
 */
public class ProductAssemblerTest
{
    private final static Logger logger = Logger.getLogger(ProductAssemblerTest.class.getName());

    private static EntityManagerFactory emf;
    private EntityManager em;

    private static Map<Algorithm.Type, int[]> algorithmSelectItems;
    private static Map<String, Object> parameters;


    public ProductAssemblerTest() {
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
        if(emf != null)
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
        if(em != null)
            em.close();
    }

    /**
     * Test of assemblе method, of class ProductAssembler.
     */
    @Test
    public void assemblе()
        throws Exception
    {
        AssemblingSchema assemblingSchema;
        em.getTransaction().begin();
        Query q = em.createNamedQuery("AssemblingSchema.findBySchemaCode");
        q.setParameter("schemaCode", AlgorithmTest.schemaCode02);
        assemblingSchema = (AssemblingSchema)q.getSingleResult();
        em.getTransaction().commit();

        ProductAssembler assembler = new ProductAssembler(assemblingSchema);
        AcaciaCallbackHandler cllbackHandler = new AcaciaCallbackHandler(getAlgorithmSelectItems());
        assembler.setCallbackHandler(cllbackHandler);
        ComplexProduct complexProduct = assembler.assemblе(getParameters());
        System.out.println("complexProduct: " + complexProduct);
    }

    private static Map<String, Object> getParameters()
    {
        if(parameters == null)
        {
            parameters = new HashMap<String, Object>();

            parameters.put(
                    Algorithm.Type.UnconditionalSelection.name(),
                    null);

            parameters.put(
                    Algorithm.Type.UserSelection.name(),
                    null);

            parameters.put(
                    Algorithm.Type.UserSingleSelection.name(),
                    null);

            parameters.put(
                    Algorithm.Type.UserMultipleSelection.name(),
                    null);

            parameters.put(
                    Algorithm.Type.RangeSelection.name(),
                    3);

            parameters.put(
                    Algorithm.Type.RangeSingleSelection.name(),
                    3);

            parameters.put(
                    Algorithm.Type.RangeMultipleSelection.name(),
                    4);

            parameters.put(
                    Algorithm.Type.EqualsSelection.name(),
                    2);

            parameters.put(
                    Algorithm.Type.EqualsSingleSelection.name(),
                    2);

            parameters.put(
                    Algorithm.Type.EqualsMultipleSelection.name(),
                    2);
        }

        return parameters;
    }

    private static Map<Algorithm.Type, int[]> getAlgorithmSelectItems()
    {
        if(algorithmSelectItems == null)
        {
            algorithmSelectItems = new TreeMap<Algorithm.Type, int[]>();

            algorithmSelectItems.put(
                    Algorithm.Type.UnconditionalSelection,
                    new int[] {});

            algorithmSelectItems.put(
                    Algorithm.Type.UserSelection,
                    new int[] {0, 2});

            algorithmSelectItems.put(
                    Algorithm.Type.UserSingleSelection,
                    new int[] {1});

            algorithmSelectItems.put(
                    Algorithm.Type.UserMultipleSelection,
                    new int[] {1, 2});

            algorithmSelectItems.put(
                    Algorithm.Type.RangeSelection,
                    new int[] {0, 1});

            algorithmSelectItems.put(
                    Algorithm.Type.RangeSingleSelection,
                    new int[] {1});

            algorithmSelectItems.put(
                    Algorithm.Type.RangeMultipleSelection,
                    new int[] {0, 1});

            algorithmSelectItems.put(
                    Algorithm.Type.EqualsSelection,
                    new int[] {0, 1});

            algorithmSelectItems.put(
                    Algorithm.Type.EqualsSingleSelection,
                    new int[] {0});

            algorithmSelectItems.put(
                    Algorithm.Type.EqualsMultipleSelection,
                    new int[] {1, 2});
        }

        return algorithmSelectItems;
    }
 
    private static class AcaciaCallbackHandler
        implements ApplicationCallbackHandler
    {
        private Map<Algorithm.Type, int[]> algorithmSelectItems;
        private boolean invoked;

        public AcaciaCallbackHandler(Map<Algorithm.Type, int[]> algorithmSelectItems)
        {
            this.algorithmSelectItems = algorithmSelectItems;
        }

        public void handle(Callback[] callbacks)
                throws IOException,
                UnsupportedCallbackException
        {
            invoked = true;
            for(Callback callback : callbacks)
            {
                if(callback instanceof ChoiceCallback)
                {
                    ChoiceCallback choiceCallback = (ChoiceCallback)callback;
                    Algorithm.Type algorithmType;
                    algorithmType = Algorithm.Type.valueOf(choiceCallback.getAssemblingSchemaItem().getAssemblingAlgorithm().getAlgorithmCode());
                    int[] selectItems = algorithmSelectItems.get(algorithmType);
                    List<ConstraintRow> constraintRows = choiceCallback.getChoices();
                    List<ConstraintRow> result = new ArrayList<ConstraintRow>(selectItems.length);
                    for(int i = 0; i < selectItems.length; i++)
                    {
                        result.add(constraintRows.get(selectItems[i]));
                    }

                    choiceCallback.setSelectedRows(result);
                }
                else
                {
                    throw new UnsupportedCallbackException(callback);
                }
            }
        }

        public boolean isInvoked() {
            return invoked;
        }
    }
}