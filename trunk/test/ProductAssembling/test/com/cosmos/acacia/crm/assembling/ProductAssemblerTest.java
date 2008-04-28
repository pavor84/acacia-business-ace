/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.assembling;

import com.cosmos.acacia.callback.ApplicationCallbackHandler;
import com.cosmos.acacia.callback.assembling.ChoiceCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class ProductAssemblerTest {

    public ProductAssemblerTest() {
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
     * Test of assemblе method, of class ProductAssembler.
     */
    @Test
    public void assemblе()
        throws Exception
    {
    }

    private static class AcaciaCallbackHandler
        implements ApplicationCallbackHandler
    {
        private int[] selectItems;
        private boolean invoked;

        public AcaciaCallbackHandler(int[] selectItems)
        {
            this.selectItems = selectItems;
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