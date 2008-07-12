package com.cosmos.test.bl.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.bl.contactbook.PositionTypesListRemote;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.test.bl.TestUtils;

/**
 * Business logic test for position types
 *
 * @author Bozhidar Bozhanov
 */
public class PositionTypesTest {

    @EJB
    private PositionTypesListRemote formSession;

    @Before
    public void setUp() {
        if ( formSession==null ){
            formSession = AcaciaPanel.getRemoteBean(this, PositionTypesListRemote.class);;
        }
    }

    @Test
    public void methodsTest() throws Exception {
        List<PositionType> personPositions = formSession.getPositionTypes(Person.class, null);
        Assert.assertNotNull(personPositions);

        List<PositionType> organizationPositions = formSession.getPositionTypes(Organization.class, null);
        Assert.assertNotNull(organizationPositions);

        EntityProperties entityProperties =
            formSession.getPositionTypeEntityProperties();

        Assert.assertNotNull(entityProperties);
    }

    /**
     * Test - create, retrieve, update, delete operations
     * over PatternMaskFormat
     */
    @Test
    public void crudTest() throws Exception {
        String nameInsert = TestUtils.getRandomString(16);

        //create
        PositionType inserted = createNew(nameInsert);
        Assert.assertNotNull(inserted);
        Assert.assertNotNull(inserted.getPositionTypeId());

        businessValidationTest(inserted);

        //retrieve
        List<PositionType> types = list();
        Assert.assertNotNull(types);

        //find
        PositionType found = find(types, inserted);
        Assert.assertNotNull(found);

        //update
        String nameUpdate = TestUtils.getRandomString(16);
        found.setPositionTypeName(nameUpdate);
        PositionType updated = updateFormat(found);
        Assert.assertEquals(nameUpdate, updated.getPositionTypeName());

        //delete
        formSession.deletePositionType(updated);

        Assert.assertNull(find(list(), updated));
    }

    private PositionType updateFormat(PositionType found) {
        return formSession.savePositionType(found, null);
    }

    private PositionType find(List<PositionType> types, PositionType inserted) {
        for (PositionType positionType : types) {
            if ( inserted.getPositionTypeId().equals(positionType.getPositionTypeId()))
                return positionType;
        }

        return null;
    }

    private List<PositionType> list() throws Exception {
        return formSession.getPositionTypes(Organization.class, null);
    }

    private PositionType createNew(String nameInsert) {
        PositionType result = formSession.newPositionType(null);
        result.setPositionTypeName(nameInsert);

        return formSession.savePositionType(result, Organization.class);
    }

    /**
     * Test inserting/updating invalid pattern mask formats
     * @param existing - test duplication errors against
     * an existing record.
     * For example test inserting new object that has the same 'name'
     * property (which should be unique) as existing
     */
    private void businessValidationTest(PositionType existing){
        try{
            createNew(existing.getPositionTypeName());
            Assert.fail("Remote exeption, caused by BusinessValidationException expected.");
        } catch (Exception re) {
            ValidationException e = TestUtils.extractValidationException(re);
            Assert.assertNotNull(e);
        }
    }
}
