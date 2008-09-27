package com.cosmos.test.bl;

import java.util.List;

import javax.ejb.EJB;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.contactbook.BusinessPartnersListRemote;
import com.cosmos.acacia.crm.bl.contactbook.PersonsListRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Business logic test
 * for {@link BusinessPartnerListBean}
 *
 * Created	:	29.07.2008
 * @author	Petar Milev
 *
 */
public class BusinessPartnerListTest extends BaseTest{

    @EJB
    private BusinessPartnersListRemote formSession = AcaciaPanel.getBean(BusinessPartnersListRemote.class, false);

    @EJB
    private PersonsListRemote personFormSession = AcaciaPanel.getBean(PersonsListRemote.class, false);

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void methodsTest(){
        EntityProperties entityProperties =
            formSession.getListingEntityProperties();
        Assert.assertNotNull(entityProperties);
    }

    /**
     * Test - create, retrieve, update, delete operations
     * over BusinessPartner
     * @throws UncompleteUnitTestException
     */
    @SuppressWarnings("unchecked")
    @Test
    public void crudTest() throws UncompleteUnitTestException{
        //create
        BusinessPartner inserted = null;
        for (int i = 0; i < 5; i++) {
            inserted = createNew();
            if ( inserted !=null )
                break;
        }

        if ( inserted!=null ){
            Assert.assertNotNull(inserted.getId());
            //delete
            formSession.deleteBusinessPartner(inserted);
        }

        //listing
        list();
    }


    private List<BusinessPartner> list() {
        return formSession.getBusinessPartners(getOrganizationId());
    }

    private BusinessPartner createNew() throws UncompleteUnitTestException {

        Person person = personFormSession.newPerson(getOrganizationId());
        person.setFirstName(""+random.nextInt(100000000));
        person.setSecondName(""+random.nextInt(100000000));
        person.setLastName(""+random.nextInt(100000000));
        person.setExtraName(""+random.nextInt(100000000));


        try{
            person = personFormSession.savePerson(person);
            return person;
        }catch ( Exception e ){
            //its ok - validation exception is thrown, so we have the same product already
            if ( TestUtils.extractValidationException(e)!=null ){
                return null;
            }
            else
                throw new RuntimeException(e);
        }
    }
}
