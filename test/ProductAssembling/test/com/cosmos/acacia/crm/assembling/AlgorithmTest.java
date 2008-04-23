/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.AppCallbackHandler;
import com.cosmos.acacia.crm.assembling.Algorithm.Type;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import java.util.List;
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
public class AlgorithmTest {

    public AlgorithmTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getType method, of class Algorithm.
     */
    @Test
    public void getType() {
        System.out.println("getType");
        Algorithm instance = null;
        Type expResult = null;
        Type result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxSelections method, of class Algorithm.
     */
    @Test
    public void getMaxSelections() {
        System.out.println("getMaxSelections");
        Algorithm instance = null;
        int expResult = 0;
        int result = instance.getMaxSelections();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinSelections method, of class Algorithm.
     */
    @Test
    public void getMinSelections() {
        System.out.println("getMinSelections");
        Algorithm instance = null;
        int expResult = 0;
        int result = instance.getMinSelections();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAssemblingSchemaItem method, of class Algorithm.
     */
    @Test
    public void getAssemblingSchemaItem() {
        System.out.println("getAssemblingSchemaItem");
        Algorithm instance = null;
        AssemblingSchemaItem expResult = null;
        AssemblingSchemaItem result = instance.getAssemblingSchemaItem();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCallbackHandler method, of class Algorithm.
     */
    @Test
    public void getCallbackHandler() {
        System.out.println("getCallbackHandler");
        Algorithm instance = null;
        AppCallbackHandler expResult = null;
        AppCallbackHandler result = instance.getCallbackHandler();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCallbackHandler method, of class Algorithm.
     */
    @Test
    public void setCallbackHandler() {
        System.out.println("setCallbackHandler");
        AppCallbackHandler callbackHandler = null;
        Algorithm instance = null;
        instance.setCallbackHandler(callbackHandler);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of apply method, of class Algorithm.
     */
    @Test
    public void apply() throws Exception {
        System.out.println("apply");
        Object valueAgainstConstraints = null;
        Algorithm instance = null;
        List expResult = null;
        List result = instance.apply(valueAgainstConstraints);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyRangeCondition method, of class Algorithm.
     */
    @Test
    public void applyRangeCondition() throws Exception {
        System.out.println("applyRangeCondition");
        List<ConstraintRow> constraintRows = null;
        Comparable valueAgainstConstraints = null;
        Algorithm instance = null;
        List<ConstraintRow> expResult = null;
        List<ConstraintRow> result = instance.applyRangeCondition(constraintRows, valueAgainstConstraints);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyEqualsCondition method, of class Algorithm.
     */
    @Test
    public void applyEqualsCondition() throws Exception {
        System.out.println("applyEqualsCondition");
        List<ConstraintRow> constraintRows = null;
        Object valueAgainstConstraints = null;
        Algorithm instance = null;
        List<ConstraintRow> expResult = null;
        List<ConstraintRow> result = instance.applyEqualsCondition(constraintRows, valueAgainstConstraints);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyUserSelection method, of class Algorithm.
     */
    @Test
    public void applyUserSelection() throws Exception {
        System.out.println("applyUserSelection");
        List<ConstraintRow> constraintRows = null;
        Algorithm instance = null;
        List<ConstraintRow> expResult = null;
        List<ConstraintRow> result = instance.applyUserSelection(constraintRows);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResultList method, of class Algorithm.
     */
    @Test
    public void getResultList() {
        System.out.println("getResultList");
        List<ConstraintRow> constraintRows = null;
        Algorithm instance = null;
        List expResult = null;
        List result = instance.getResultList(constraintRows);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addItem method, of class Algorithm.
     */
    @Test
    public void addItem() {
        System.out.println("addItem");
        Object item = null;
        Algorithm instance = null;
        instance.addItem(item);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addItems method, of class Algorithm.
     */
    @Test
    public void addItems() {
        System.out.println("addItems");
        List<ConstraintRow> constraintRows = null;
        Algorithm instance = null;
        instance.addItems(constraintRows);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}