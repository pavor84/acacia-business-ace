/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.ApplicationCallbackHandler;
import com.cosmos.acacia.callback.assembling.ChoiceCallback;
import com.cosmos.acacia.callback.assembling.LessSelectedItemsThanAllowed;
import com.cosmos.acacia.callback.assembling.MoreSelectedItemsThanAllowed;
import com.cosmos.acacia.crm.assembling.Algorithm.Type;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingAlgorithm;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.RealProduct;
import com.cosmos.acacia.crm.data.assembling.VirtualProduct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
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

    private static final String schemaCode01 = "Test Schema Code 01";
    private static final String schemaCode02 = "Test Schema Code 02";

    private static AssemblingSchema assemblingSchema01;
    private static AssemblingSchema assemblingSchema02;
    private static List<AssemblingSchemaItem> assemblingSchemaItems;

    private enum SchemaItemValue
    {
        UnconditionalSelection_Item1(Type.UnconditionalSelection),
        UnconditionalSelection_Item2(Type.UnconditionalSelection),
        UserSelection_Item1(Type.UserSelection),
        UserSelection_Item2(Type.UserSelection),
        UserSelection_Item3(Type.UserSelection),
        UserSingleSelection_Item1(Type.UserSingleSelection),
        UserSingleSelection_Item2(Type.UserSingleSelection),
        UserSingleSelection_Item3(Type.UserSingleSelection),
        UserMultipleSelection_Item1(Type.UserMultipleSelection),
        UserMultipleSelection_Item2(Type.UserMultipleSelection),
        UserMultipleSelection_Item3(Type.UserMultipleSelection),
        UserMultipleSelection_Item4(Type.UserMultipleSelection),
        RangeSelection_From1_To5_Item1(Type.RangeSelection, 1, 5),
        RangeSelection_From2_To4_Item2(Type.RangeSelection, 2, 4),
        RangeSelection_From4_To5_Item3(Type.RangeSelection, 4, 5),
        RangeSelection_From7_To8_Item4(Type.RangeSelection, 7, 8),
        RangeSingleSelection_From1_To5_Item1(Type.RangeSingleSelection, 1, 5),
        RangeSingleSelection_From2_To4_Item2(Type.RangeSingleSelection, 2, 4),
        RangeSingleSelection_From4_To5_Item3(Type.RangeSingleSelection, 4, 5),
        RangeSingleSelection_From7_To8_Item4(Type.RangeSingleSelection, 7, 8),
        RangeMultipleSelection_From1_To5_Item1(Type.RangeMultipleSelection, 1, 5),
        RangeMultipleSelection_From2_To4_Item2(Type.RangeMultipleSelection, 2, 4),
        RangeMultipleSelection_From4_To5_Item3(Type.RangeMultipleSelection, 4, 5),
        RangeMultipleSelection_From7_To8_Item4(Type.RangeMultipleSelection, 7, 8),
        EqualsSelection_Value1_Item1(Type.EqualsSelection, 1),
        EqualsSelection_Value2_Item2(Type.EqualsSelection, 2),
        EqualsSelection_Value2_Item3(Type.EqualsSelection, 2),
        EqualsSelection_Value4_Item4(Type.EqualsSelection, 4),
        EqualsSingleSelection_Value1_Item1(Type.EqualsSingleSelection, 1),
        EqualsSingleSelection_Value2_Item2(Type.EqualsSingleSelection, 2),
        EqualsSingleSelection_Value2_Item3(Type.EqualsSingleSelection, 2),
        EqualsSingleSelection_Value4_Item4(Type.EqualsSingleSelection, 4),
        EqualsMultipleSelection_Value1_Item1(Type.EqualsMultipleSelection, 1),
        EqualsMultipleSelection_Value2_Item2(Type.EqualsMultipleSelection, 2),
        EqualsMultipleSelection_Value2_Item3(Type.EqualsMultipleSelection, 2),
        EqualsMultipleSelection_Value4_Item4(Type.EqualsMultipleSelection, 4),
        ;

        private SchemaItemValue(Type algorithmType)
        {
            this(algorithmType, null);
        }

        private SchemaItemValue(Type algorithmType, Integer from)
        {
            this(algorithmType, from, null);
        }

        private SchemaItemValue(Type algorithmType, Integer from, Integer to)
        {
            this.algorithmType = algorithmType;
            this.from = from;
            this.to = to;
        }

        private Type algorithmType;
        private Integer from;
        private Integer to;

        public Type getAlgorithmType() {
            return algorithmType;
        }

        public Integer getFrom() {
            return from;
        }

        public Integer getTo() {
            return to;
        }

        private static TreeMap<Algorithm.Type, List<SchemaItemValue>> valuesByAlgorithmType;
        public static List<SchemaItemValue> getValuesByAlgorithmType(Algorithm.Type algorithmType)
        {
            if(valuesByAlgorithmType == null)
            {
                valuesByAlgorithmType = new TreeMap<Type, List<AlgorithmTest.SchemaItemValue>>();
                for(SchemaItemValue siv : SchemaItemValue.values())
                {
                    Algorithm.Type type = siv.getAlgorithmType();
                    List<SchemaItemValue> sivList = valuesByAlgorithmType.get(type);
                    if(sivList == null)
                    {
                        sivList = new ArrayList<AlgorithmTest.SchemaItemValue>();
                        valuesByAlgorithmType.put(type, sivList);
                    }
                    sivList.add(siv);
                }
            }

            return valuesByAlgorithmType.get(algorithmType);
        }
    }


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
            populateAssemblingAlgorithms(em);

            AssemblingCategory aCategory = populateAssemblingCategory(em, categoryCode);

            assemblingSchema01 = populateAssemblingSchema(em, schemaCode01, aCategory);
            assemblingSchema02 = populateAssemblingSchema(em, schemaCode02, aCategory);

            List<VirtualProduct> virtualProducts = getRealProducts(em, populateSimpleProducts(em));

            populateAssemblingSchemaItems(em, assemblingSchema01, virtualProducts, 1);

            int productIndex = SchemaItemValue.values().length + 1;
            virtualProducts.set(productIndex, assemblingSchema01);
            populateAssemblingSchemaItems(em, assemblingSchema02, virtualProducts, 2);
        }
        finally
        {
            if(em != null)
                em.close();
        }
    }

    private static void populateAssemblingAlgorithms(EntityManager em)
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
    }

    private static AssemblingCategory populateAssemblingCategory(
            EntityManager em,
            String categoryCode)
    {
        AssemblingCategory aCategory;
        em.getTransaction().begin();
        Query q = em.createNamedQuery("AssemblingCategory.findByCategoryCode");
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

        return aCategory;
    }

    private static AssemblingSchema populateAssemblingSchema(
            EntityManager em,
            String schemaCode,
            AssemblingCategory aCategory)
    {
        AssemblingSchema aSchema;
        em.getTransaction().begin();
        Query q = em.createNamedQuery("AssemblingSchema.findBySchemaCode");
        q.setParameter("schemaCode", schemaCode);
        try
        {
            aSchema = (AssemblingSchema)q.getSingleResult();
        }
        catch(NoResultException ex)
        {
            aSchema = new AssemblingSchema();
            aSchema.setSchemaCode(schemaCode);
            aSchema.setSchemaName(schemaCode);
            aSchema.setAssemblingCategory(aCategory);
            em.persist(aSchema);
        }
        em.getTransaction().commit();

        return aSchema;
    }

    private static void populateAssemblingSchemaItems(
            EntityManager em,
            AssemblingSchema aSchema,
            List<VirtualProduct> virtualProducts,
            int multiplier)
    {
        for(Type type : Type.values())
        {
            Integer minSelections;
            Integer maxSelections;
            if(Type.SingleSelectionAlgorithms.contains(type))
            {
                minSelections = 1;
                maxSelections = 1;
            }
            else if(Type.MultipleSelectionAlgorithms.contains(type))
            {
                minSelections = 2;
                maxSelections = 3;
            }
            else
            {
                minSelections = null;
                maxSelections = null;
            }

            populateAssemblingSchemaItem(
                    em,
                    aSchema,
                    type,
                    virtualProducts, multiplier,
                    minSelections, maxSelections);
        }
    }

    private static AssemblingSchemaItem populateAssemblingSchemaItem(
            EntityManager em,
            AssemblingSchema aSchema,
            Algorithm.Type algorithmType,
            List<VirtualProduct> virtualProducts,
            int multiplier,
            Integer minSelections,
            Integer maxSelections)
    {
        AssemblingSchemaItem schemaItem;
        em.getTransaction().begin();
        Query q = em.createNamedQuery("AssemblingSchemaItem.findBySchemaAndMessageCode");
        String messageCode = algorithmType.name();
        q.setParameter("assemblingSchema", aSchema);
        q.setParameter("messageCode", messageCode);
        try
        {
            schemaItem = (AssemblingSchemaItem)q.getSingleResult();
        }
        catch(NoResultException ex)
        {
            schemaItem = new AssemblingSchemaItem();
            schemaItem.setAssemblingSchema(aSchema);
            AssemblingAlgorithm aAlgorithm = em.find(AssemblingAlgorithm.class, algorithmType.getAlgorithmId());
            schemaItem.setAssemblingAlgorithm(aAlgorithm);
            schemaItem.setDataType("Integer");
            schemaItem.setMessageCode(messageCode);
            schemaItem.setMessageText(messageCode);
            schemaItem.setMinSelections(minSelections);
            schemaItem.setMaxSelections(maxSelections);

            List<SchemaItemValue> sivList = SchemaItemValue.getValuesByAlgorithmType(algorithmType);
            List<AssemblingSchemaItemValue> itemValues = new ArrayList<AssemblingSchemaItemValue>(sivList.size());
            for(SchemaItemValue siv : SchemaItemValue.getValuesByAlgorithmType(algorithmType))
            {
                AssemblingSchemaItemValue itemValue = new AssemblingSchemaItemValue();
                itemValue.setAssemblingSchemaItem(schemaItem);
                itemValue.setMinConstraint(siv.getFrom());
                itemValue.setMaxConstraint(siv.getTo());
                int productIndex = siv.ordinal() * multiplier;
                itemValue.setVirtualProduct(virtualProducts.get(productIndex));
                itemValues.add(itemValue);
            }

            schemaItem.setItemValues(itemValues);
            em.persist(schemaItem);
        }
        em.getTransaction().commit();

        return schemaItem;
    }

    private static List<SimpleProduct> populateSimpleProducts(EntityManager em)
    {
        int productSize = SchemaItemValue.values().length * 2;
        List<SimpleProduct> products = new ArrayList<SimpleProduct>(productSize);
        SimpleProduct sp;
        em.getTransaction().begin();
        Query q = em.createNamedQuery("SimpleProduct.findByProductCode");

        for(int i = 1; i <= productSize; i++)
        {
            String productCode = "sp-pc-" + i;
            q.setParameter("productCode", productCode);
            try
            {
                sp = (SimpleProduct)q.getSingleResult();
            }
            catch(NoResultException ex)
            {
                sp = new SimpleProduct();
                sp.setProductCode(productCode);
                sp.setProductPrice(BigDecimal.valueOf(i));
                em.persist(sp);
            }
            products.add(sp);
        }
        em.getTransaction().commit();

        return products;
    }

    private static List<VirtualProduct> getRealProducts(
            EntityManager em,
            List<SimpleProduct> products)
    {
        List<VirtualProduct> realProducts = new ArrayList<VirtualProduct>(products.size());
        RealProduct rp;
        em.getTransaction().begin();
        Query q = em.createNamedQuery("RealProduct.findBySimpleProduct");
        for(SimpleProduct sp : products)
        {
            q.setParameter("simpleProduct", sp);
            try
            {
                rp = (RealProduct)q.getSingleResult();
            }
            catch(NoResultException ex)
            {
                rp = new RealProduct();
                rp.setSimpleProduct(sp);
                em.persist(rp);
            }
            realProducts.add(rp);
        }
        em.getTransaction().commit();

        return realProducts;
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

    private static List<AssemblingSchemaItem> getAssemblingSchemaItems()
    {
        if(assemblingSchemaItems == null)
        {
            EntityManager em = emf.createEntityManager();
            try
            {
                em.getTransaction().begin();
                Query q = em.createNamedQuery("AssemblingSchemaItem.findByAssemblingSchema");
                q.setParameter("assemblingSchema", assemblingSchema02);
                assemblingSchemaItems = q.getResultList();
                em.getTransaction().commit();
            }
            finally
            {
                if(em != null)
                {
                    em.close();
                    em = null;
                }
            }
        }

        return assemblingSchemaItems;
    }
    

    private AssemblingSchemaItem getAssemblingSchemaItem(Algorithm.Type algorithmType)
    {
        List<AssemblingSchemaItem> asiList = getAssemblingSchemaItems();
        for(AssemblingSchemaItem asi : asiList)
        {
            AssemblingAlgorithm assemblingAlgorithm = asi.getAssemblingAlgorithm();
            if(assemblingAlgorithm.getAlgorithmId().equals(algorithmType.getAlgorithmId()))
                return asi;
        }

        return null;
    }

    @Test
    public void UnconditionalSelection()
    {
        AssemblingSchemaItem asi = getAssemblingSchemaItem(Type.UnconditionalSelection);
        Algorithm algorithm = new Algorithm(asi);
        try
        {
            List result = algorithm.apply(null);
            assertNotNull(result);
            assertTrue(result.size() == 2);
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void UserSelection()
    {
        AssemblingSchemaItem asi = getAssemblingSchemaItem(Type.UserSelection);
        List<AssemblingSchemaItemValue> itemValues = asi.getItemValues();
        Algorithm algorithm = new Algorithm(asi);
        try
        {
            int[] selectItems = new int[] {};
            AcaciaCallbackHandler callbackHandler = new AcaciaCallbackHandler(selectItems);
            algorithm.setCallbackHandler(callbackHandler);
            List results = algorithm.apply(null);
            assertNotNull(results);
            assertTrue(results.size() == selectItems.length);

            selectItems = new int[] {0, 2};
            callbackHandler = new AcaciaCallbackHandler(selectItems);
            algorithm.setCallbackHandler(callbackHandler);
            results = algorithm.apply(null);
            assertNotNull(results);
            assertTrue(results.size() == selectItems.length);
            for(int i = 0; i < selectItems.length; i++)
            {
                AssemblingSchemaItemValue itemValue = itemValues.get(selectItems[i]);
                AssemblingSchemaItemValue result = (AssemblingSchemaItemValue)results.get(i);
                assertTrue(itemValue.equals(result));
            }
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void UserSingleSelection()
    {
        AssemblingSchemaItem asi = getAssemblingSchemaItem(Type.UserSingleSelection);
        List<AssemblingSchemaItemValue> itemValues = asi.getItemValues();
        Algorithm algorithm = new Algorithm(asi);
        try
        {
            int selectItems[] = new int[] {1};
            AcaciaCallbackHandler callbackHandler = new AcaciaCallbackHandler(selectItems);
            algorithm.setCallbackHandler(callbackHandler);
            List results = algorithm.apply(null);
            assertNotNull(results);
            assertTrue(results.size() == selectItems.length);
            for(int i = 0; i < selectItems.length; i++)
            {
                AssemblingSchemaItemValue itemValue = itemValues.get(selectItems[i]);
                AssemblingSchemaItemValue result = (AssemblingSchemaItemValue)results.get(i);
                assertTrue(itemValue.equals(result));
            }

            selectItems = new int[] {};
            callbackHandler = new AcaciaCallbackHandler(selectItems);
            algorithm.setCallbackHandler(callbackHandler);
            try
            {
                algorithm.apply(null);
                assertTrue(false);
            }
            catch(LessSelectedItemsThanAllowed ex) {}

            selectItems = new int[] {0, 2};
            callbackHandler = new AcaciaCallbackHandler(selectItems);
            algorithm.setCallbackHandler(callbackHandler);
            try
            {
                algorithm.apply(null);
                assertTrue(false);
            }
            catch(MoreSelectedItemsThanAllowed ex) {}
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
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


    private static class AcaciaCallbackHandler
        implements ApplicationCallbackHandler
    {
        private int[] selectItems;

        public AcaciaCallbackHandler(int[] selectItems)
        {
            this.selectItems = selectItems;
        }

        public void handle(Callback[] callbacks)
                throws IOException,
                UnsupportedCallbackException
        {
            for(Callback callback : callbacks)
            {
                if(callback instanceof ChoiceCallback)
                {
                    ChoiceCallback choiceCallback = (ChoiceCallback)callback;
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
        
    }
}