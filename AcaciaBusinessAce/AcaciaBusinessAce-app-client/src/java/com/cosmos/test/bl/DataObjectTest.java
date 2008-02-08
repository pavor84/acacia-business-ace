/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.test.bl;

import com.cosmos.acacia.crm.bl.impl.DataObjectTypeRemote;
import com.cosmos.acacia.crm.data.DataObjectType;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
            try
            {
                doh = InitialContext.doLookup(DataObjectTypeRemote.class.getName());
            }
            catch(NamingException ex)
            {
                throw new RuntimeException(ex);
            }
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
