/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.test.bl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.ejb.EJB;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.impl.DataObjectTypeRemote;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.gui.AcaciaPanel;

/**
 *
 * @author Miro
 */
public class DataObjectTest
{
    @EJB
    private DataObjectTypeRemote doh;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        if(doh == null)
        {
            doh = AcaciaPanel.getRemoteBean(this, DataObjectTypeRemote.class);
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getDataObjectType()
    {
        String dotName = "Miro";
        DataObjectType dot = doh.getDataObjectType(dotName);
        System.out.println("");
        assertNull(dot);

        dot = new DataObjectType();
        dot.setDataObjectType(dotName);
        dot = doh.persist(dot);

        DataObjectType dotCache = doh.getDataObjectType(dotName);
        assertNotNull(dotCache);

        doh.remove(dot);
        dotCache = doh.getDataObjectType(dotName);
        assertNull(dotCache);
    }

}
