package com.cosmos.test.bl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.app.AppSession;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class AcaciaSessionTest {
    @Before
    public void setUp() {
        //nothing
    }
    
    @Test
    public void testStatefulSession(){
        Integer value = 34;
        String valueName = "AcaciaSessionTest.value.x";
        
        //set a value
        AppSession.get().setValue(valueName, value);
        
        //get a value
        Object valueX = AppSession.get().getValue(valueName);
        
        Assert.assertEquals(valueX, value);
    }
}
