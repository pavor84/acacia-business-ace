/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.beans.TestBeanA;
import com.cosmos.acacia.beans.TestListPanel;
import com.cosmos.util.ClassHelper;
import java.lang.reflect.AccessibleObject;
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
public class PropertyProxyTest {

    private static Property property;

//    public static void main(String[] args) throws Exception {
//        //Field field = ClassHelper.getField(ContactPerson.class, "positionType");
//        for(Field field : ContactPerson.class.getDeclaredFields()) {
//            System.out.println(field);
//            if(!"positionType".equals(field.getName())) {
//                continue;
//            }
//            Property property = PropertyProxy.newInstance(field, EntityForm.PositionType);
//            System.out.println("property.title(): " + property.title());
//        }
//    }

    public PropertyProxyTest() {
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
     * Test of newInstance method, of class PropertyProxy.
     */
    @Test
    public void testNewInstance() {
        System.out.println("newInstance");
        try {
            AccessibleObject field = ClassHelper.getAccessibleObject(TestBeanA.class, "testBeanB");
            property = PropertyProxy.newInstance(field, EntityForm.TestBeanB);
        } catch(Exception ex) {
            fail(ex.getMessage());
        }

        assertNotNull(property);
    }

    /**
     * Test of getConstantAnnotation method, of class PropertyProxy.
     */
    @Test
    public void testGetConstantAnnotation() {
    }

    /**
     * Test of getDefaultAnnotation method, of class PropertyProxy.
     */
    @Test
    public void testGetDefaultAnnotation() {
    }

    /**
     * Test of getAnnotation method, of class PropertyProxy.
     */
    @Test
    public void testTitle() {
        System.out.println("title");
        String value = property.title();
        assertNotNull(value);
        assertEquals("Test Bean", value);
    }

    @Test
    public void testParentContainerName() {
        System.out.println("parentContainerName");
        String value = property.parentContainerName();
        assertNotNull(value);
        assertEquals("primaryInfo", value);
    }

    @Test
    public void testFormComponentPair() {
        System.out.println("formComponentPair");
        FormComponentPair formComponentPair = property.formComponentPair();
        System.out.println("formComponentPair=" + formComponentPair);
    }

    @Test
    public void testFormComponentPairParentContainerName() {
        System.out.println("formComponentPair.parentContainerName");
        FormComponentPair formComponentPair = property.formComponentPair();
        String value = formComponentPair.parentContainerName();
        assertNotNull(value);
        assertEquals("primaryInfo", value);
    }

    @Test
    public void testSelectableList() {
        System.out.println("selectableList");
        SelectableList selectableList = property.selectableList();
        assertNotNull(selectableList);
        String className = selectableList.className();
        assertEquals(TestListPanel.class.getName(), className);
    }

    @Test
    public void testCustomDisplay() {
        System.out.println("customDisplay");
        String value = property.customDisplay();
        assertNotNull(value);
        assertEquals("${testBeanB.name}", value);
    }
}