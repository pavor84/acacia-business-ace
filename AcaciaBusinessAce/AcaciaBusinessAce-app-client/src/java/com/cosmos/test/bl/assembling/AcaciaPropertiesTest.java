/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.test.bl.assembling;

import com.cosmos.acacia.util.AcaciaProperties;
import com.cosmos.test.bl.BaseTest;
import javax.ejb.EJB;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miro
 */
public class AcaciaPropertiesTest
{
    @EJB
    private static BaseTest util;


    @BeforeClass
    public static void setUpClass() throws Exception
    {
        util = new BaseTest();
        util.setUp();
    }

    @Test
    public void testSaveAcaciaProperties()
    {
        AcaciaProperties properties = util.getSession().getProperties();
        properties.setProperty("test.c1", "Current c1");

        AcaciaProperties.Level level = AcaciaProperties.Level.User;
        AcaciaProperties userProps = (AcaciaProperties)properties.getProperties(level);
        userProps.setProperty("test.u1", "User u1");
        userProps.setProperty("test.u2", "User u2");
        userProps.setProperty("test.u3", "User u3");
        properties.setProperty(level, "test.u4", "User u4");
        properties.setProperty(level, "test.u5", "User u5");
        properties.setProperty(level, "test.u6", "User u6");
        util.getSession().saveProperties(properties);

        assertEquals(properties.size(), 7);
        assertEquals(userProps.size(), 6);
    }

    @Test
    public void testRemoveAcaciaProperties()
    {
        AcaciaProperties properties = util.getSession().getProperties();
        AcaciaProperties.Level level = AcaciaProperties.Level.User;
        AcaciaProperties userProps = (AcaciaProperties)properties.getProperties(level);
        System.out.println("properties.size(): " + properties.size());
        System.out.println("userProps.size(): " + userProps.size());
        assertEquals(properties.size(), 7);
        assertEquals(userProps.size(), 6);

        userProps.removeProperty("test.u3");
        properties.removeProperty(level, "test.u5");
        util.getSession().saveProperties(properties);

        properties = util.getSession().getProperties();
        assertEquals(properties.size(), 5);
        assertFalse(properties.containsKey("test.u3"));
        assertFalse(properties.containsKey("test.u5"));

        
    }
}
