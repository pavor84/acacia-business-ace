package com.cosmos.test.bl.contactbook;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.contactbook.impl.AddressesListRemote;
import com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote;
import com.cosmos.acacia.crm.bl.impl.ProductsListBean;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.City;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.test.bl.TestUtils;
import com.cosmos.test.bl.UncompleteUnitTestException;

/**
 *
 * Created	:	06.04.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 * Business logic test for
 * {@link ProductsListBean}
 *
 */
public class AddressesListTest {

    @EJB
    private AddressesListRemote formSession;

    @EJB
    private PatternMaskListRemote patternMaskListSession;

    @Before
    public void setUp() {
        if (formSession == null){
            try {
                formSession = InitialContext.doLookup(AddressesListRemote.class.getName());
                patternMaskListSession = InitialContext.doLookup(PatternMaskListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
    }

    @Test
    public void methodsTest(){
        List<Address> possibleAddresses = formSession.getAddresses(null);
        Assert.assertNotNull(possibleAddresses);

        List<DbResource> ct = formSession.getCommunicationTypes();
        Assert.assertNotNull(ct);

        List<City> cities = formSession.getCities();
        Assert.assertNotNull(cities);


        EntityProperties entityProperties =
            formSession.getAddressEntityProperties();

        Assert.assertNotNull(entityProperties);
    }

    /**
     * Test - create, retrieve, update, delete operations
     * over PatternMaskFormat
     */
    @Test
    /*public void crudTest(){
        String nameInsert = TestUtils.getRandomString(16);
        String codeInsert = TestUtils.getRandomString(16);

        //create
        Address inserted = null;
        try {
            inserted = createNew(nameInsert, codeInsert);
        } catch (Exception e) {

        }
        Assert.assertNotNull(inserted);
        Assert.assertNotNull(inserted.getProductId());

        businessValidationTest(inserted);

        //retrieve
        List<SimpleProduct> products =
            list();
        Assert.assertNotNull(products);

        //find
        SimpleProduct found = find(products, inserted);
        Assert.assertNotNull("Product with id: "+inserted.getProductId()+" not found after insertion!", found);

        //update
        String nameUpdate = TestUtils.getRandomString(16);
        found.setProductName(nameUpdate);
        SimpleProduct updated = updateFormat(found);
        Assert.assertEquals(nameUpdate, updated.getProductName());

        //delete
        formSession.deleteProduct(updated);

        List<SimpleProduct> productsAgain =
            list();

        //here updated is actually deleted
        SimpleProduct foundAgain = find(productsAgain, updated);
        //shoudn't be here since its deleted
        Assert.assertNull(foundAgain);
    }
    */

    /**
     * @param products
     * @param inserted
     * @return
     */
    private SimpleProduct find(List<SimpleProduct> products, SimpleProduct inserted) {
        for (SimpleProduct product : products) {
            if ( inserted.getProductId().equals(product.getProductId()))
                return product;
        }

        return null;
    }

    private Address createNew(String nameInsert) throws UncompleteUnitTestException{
        Address result = formSession.newAddress();
        result.setAddressName(nameInsert);


        return formSession.saveAddress(result, new DataObject());
    }

    /**
     * Test inserting/updating invalid objects
     * @param existing - test duplication errors against
     * an existing record.
     * For example test inserting new object that has the same 'name'
     * property (which should be unique) as existing
     */
    private void businessValidationTest(SimpleProduct existing){
        try{
            //createNew(existing.getProductName(), existing.getProductCode());
            Assert.fail("Remote exeption, caused by BusinessValidationException expected.");
        }catch (Exception re){
            ValidationException e = TestUtils.extractValidationException(re);
            //at least 2 messages should be present
            Assert.assertNotNull(e);
            Assert.assertNotNull(e.getMessages());
            Assert.assertTrue(e.getMessages().size()>=2);
        }
    }
}
