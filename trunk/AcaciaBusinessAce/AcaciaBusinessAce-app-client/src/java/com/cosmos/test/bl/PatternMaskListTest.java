package com.cosmos.test.bl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.contactbook.BusinessPartnersListRemote;
import com.cosmos.acacia.crm.bl.impl.PatternMaskListBean;
import com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AcaciaPanel;
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
public class PatternMaskListTest extends BaseTest{

    private PatternMaskListRemote formSession = AcaciaPanel.getBean(PatternMaskListRemote.class, false);

    private BusinessPartnersListRemote businessPartnersListRemote = AcaciaPanel.getBean(BusinessPartnersListRemote.class, false);

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void methodsTest(){
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
        return formSession.listPatternsByName(getOrganizationId());
    }

    private PatternMaskFormat createNew(String nameInsert) {
        PatternMaskFormat result = formSession.newPatternMaskFormat(getOrganizationId());
        result.setPatternName(nameInsert);
        result.setFormat("1234");

        Random r = new SecureRandom();

        List<BusinessPartner> owners = businessPartnersListRemote.getBusinessPartners(getOrganizationId());
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
