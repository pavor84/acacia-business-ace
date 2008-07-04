package com.cosmos.test.bl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.impl.PatternMaskListBean;
import com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	06.04.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Business logic test for
 * {@link PatternMaskListBean}
 *
 */
public class PatternMaskListTest {
    
    @EJB
    private PatternMaskListRemote formSession;
    
    @Before
    public void setUp() {
        if ( formSession==null ){
            try {
                formSession = InitialContext.doLookup(PatternMaskListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
    }
    
    @Test
    public void methodsTest(){
        List<BusinessPartner> possibleOwners = formSession.getOwnersList();
        Assert.assertNotNull(possibleOwners);
        
        EntityProperties entityProperties = 
            formSession.getPatternMaskEntityProperties();
        Assert.assertNotNull(entityProperties);
    }
    
    /**
     * Test - create, retrieve, update, delete operations
     * over PatternMaskFormat
     */
    @Test
    public void crudTest(){
        String nameInsert = TestUtils.getRandomString(16);
        
        //create
        PatternMaskFormat inserted = createNew(nameInsert);
        Assert.assertNotNull(inserted);
        Assert.assertNotNull(inserted.getPatternMaskFormatId());
        
        businessValidationTest(inserted);
        
        //retrieve
        List<PatternMaskFormat> formats = 
            list();
        Assert.assertNotNull(formats);
        
        //find
        PatternMaskFormat found = find(formats, inserted);
        Assert.assertNotNull(found);
        
        //update
        String nameUpdate = TestUtils.getRandomString(16);
        found.setPatternName(nameUpdate);
        PatternMaskFormat updated = updateFormat(found);
        Assert.assertEquals(nameUpdate, updated.getPatternName());
        
        //delete
        boolean deleted = formSession.deletePatternMaskFormat(updated);
        Assert.assertTrue(deleted);
    }
    
    private PatternMaskFormat updateFormat(PatternMaskFormat found) {
        return
        formSession.savePatternMaskFormat(found);
    }

    private PatternMaskFormat find(List<PatternMaskFormat> formats, PatternMaskFormat inserted) {
        for (PatternMaskFormat patternMaskFormat : formats) {
            if ( inserted.getPatternMaskFormatId().equals(patternMaskFormat.getPatternMaskFormatId()))
                return patternMaskFormat;
        }
        
        Assert.fail("Format with id: "+inserted.getPatternMaskFormatId()+" not found after insertion!");
        return null;
    }

    private List<PatternMaskFormat> list() {
        return formSession.listPatternsByName();
    }

    private PatternMaskFormat createNew(String nameInsert) {
        PatternMaskFormat result = formSession.newPatternMaskFormat();
        result.setPatternName(nameInsert);
        result.setFormat("1234");
        
        Random r = new SecureRandom();
        
        List<BusinessPartner> owners = formSession.getOwnersList();
        Assert.assertNotNull(owners);
        if ( !owners.isEmpty() ){
            int randomIdx = r.nextInt(owners.size());
            BusinessPartner owner = owners.get(randomIdx);
            result.setOwner(owner);
        }
        
        return
        formSession.savePatternMaskFormat(result);
    }
    
    /**
     * Test inserting/updating invalid pattern mask formats
     * @param existing - test duplication errors against
     * an existing record.
     * For example test inserting new object that has the same 'name'
     * property (which should be unique) as existing
     */
    private void businessValidationTest(PatternMaskFormat existing){
        try{
            createNew(existing.getPatternName());
            Assert.fail("Remote exeption, caused by BusinessValidationException expected.");
        }catch (Exception re){
            ValidationException e = TestUtils.extractValidationException(re);
            Assert.assertNotNull(e);
        }
    }
}