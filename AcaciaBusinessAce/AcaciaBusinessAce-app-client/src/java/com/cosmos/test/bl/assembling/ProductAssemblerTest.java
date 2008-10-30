/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.test.bl.assembling;

import com.cosmos.acacia.crm.assembling.Algorithm;
import com.cosmos.acacia.crm.assembling.AlgorithmException;
import com.cosmos.acacia.crm.assembling.ProductAssembler;
import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.enums.DataType;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.test.bl.BaseTest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.UUID;
import javax.ejb.EJB;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//import static org.junit.Assert.*;


/**
 *
 * @author Miro
 */
public class ProductAssemblerTest
{
    @EJB
    private static AssemblingRemote formSession;
    private static BaseTest util;
    private static AssemblingCategory assemblingCategory;
    private static TreeMap<Algorithm.Type, AssemblingSchema> assemblingSchemas;


    @BeforeClass
    public static void setUpClass() throws Exception
    {
        assemblingSchemas = new TreeMap<Algorithm.Type, AssemblingSchema>();
        formSession = AcaciaPanel.getBean(AssemblingRemote.class, false);
        util = new BaseTest();
        util.setUp();

        assemblingCategory = formSession.newAssemblingCategory(null);
        String code = UUID.randomUUID().toString();
        assemblingCategory.setCategoryCode(code);
        assemblingCategory.setCategoryName(code);
        assemblingCategory.setDescription(ProductAssemblerTest.class.getName());
        assemblingCategory = formSession.saveCategory(assemblingCategory);
        System.out.println("assemblingCategory: " + assemblingCategory);
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        List<AssemblingSchema> schemas = formSession.getAssemblingSchemas(
                assemblingCategory, false);
        for(AssemblingSchema schema : schemas)
        {
            List<AssemblingSchemaItem> schemaItems =
                formSession.getAssemblingSchemaItems(schema);
            for(AssemblingSchemaItem schemaItem : schemaItems)
            {
                List<AssemblingSchemaItemValue> itemValues =
                    formSession.getAssemblingSchemaItemValues(schemaItem);
                for(AssemblingSchemaItemValue itemValue : itemValues)
                {
                    formSession.deleteAssemblingSchemaItemValue(itemValue);
                }

                formSession.deleteAssemblingSchemaItem(schemaItem);
            }

            formSession.deleteAssemblingSchema(schema);
        }

        formSession.deleteAssemblingCategory(assemblingCategory);
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUnconditionalSelection()
        throws AlgorithmException
    {
        Algorithm.Type algorithmType = Algorithm.Type.UnconditionalSelection;
        ProductAssembler assembler = getProductAssembler(algorithmType);
        ComplexProduct complexProduct =
            assembler.assemble(getParameters(algorithmType));
    }

    @Test
    public void testUserSelection()
    {
    }


    @Test
    public void testUserSingleSelection()
    {
    }

    @Test
    public void testUserMultipleSelection()
    {
    }

    @Test
    public void testRangeSelection()
    {
    }

    @Test
    public void testRangeSingleSelection()
    {
    }

    @Test
    public void testRangeMultipleSelection()
    {
    }

    @Test
    public void testEqualsSelection()
    {
    }

    @Test
    public void testEqualsSingleSelection()
    {
    }

    @Test
    public void testEqualsMultipleSelection()
    {
    }


    private Map getParameters(Algorithm.Type algorithmType)
    {
        return new Properties();
    }

    private ProductAssembler getProductAssembler(Algorithm.Type algorithmType)
    {
        ProductAssembler assembler =
            new ProductAssembler(getAssemblingSchema(algorithmType),
            formSession,
            new AssemblerCallbackHandler(algorithmType));

        return assembler;
    }

    private AssemblingSchema getAssemblingSchema(Algorithm.Type algorithmType)
    {
        AssemblingSchema schema = assemblingSchemas.get(algorithmType);
        if(schema != null)
            return schema;

        String name = algorithmType.name();
        schema = formSession.newAssemblingSchema(assemblingCategory);
        schema.setSchemaCode(name);
        schema.setSchemaName(name);
        schema = formSession.saveSchema(schema);
        assemblingSchemas.put(algorithmType, schema);

        populateAssemblingSchemaItems(schema, algorithmType);

        return schema;
    }

    private void populateAssemblingSchemaItems(
        AssemblingSchema schema,
        Algorithm.Type algorithmType)
    {
        List<AssemblingSchemaItem> schemaItems =
            formSession.getAssemblingSchemaItems(schema);
        if(schemaItems != null && schemaItems.size() > 0)
            return;

        AssemblingSchemaItem schemaItem;
        DbResource dataType =
            getDataType(DataType.IntegerType);
        System.out.println("dataType: " + dataType);
        
        switch(algorithmType)
        {
            case UnconditionalSelection:
                schemaItem = formSession.newAssemblingSchemaItem(schema);
                //schemaItem.set
                break;
        }
    }

    private List<DbResource> getDataTypes()
    {
        return formSession.getDataTypes();
    }

    private DbResource getDataType(DataType dataType)
    {
        return formSession.getDbResource(dataType);
    }

    private static class AssemblerCallbackHandler
        implements CallbackHandler
    {
        private Algorithm.Type algorithmType;

        public AssemblerCallbackHandler(Algorithm.Type algorithmType)
        {
            this.algorithmType = algorithmType;
        }

        @Override
        public void handle(Callback[] callbacks)
            throws IOException, UnsupportedCallbackException
        {
        }
    }
}
