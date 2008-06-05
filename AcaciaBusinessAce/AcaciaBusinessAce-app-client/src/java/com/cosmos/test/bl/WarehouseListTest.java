package com.cosmos.test.bl;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.app.AppSession;
import com.cosmos.acacia.crm.bl.contactbook.impl.AddressesListRemote;
import com.cosmos.acacia.crm.bl.impl.WarehouseListBean;
import com.cosmos.acacia.crm.bl.impl.WarehouseListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	06.04.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Business logic test for
 * {@link WarehouseListBean}
 *
 */
public class WarehouseListTest {
    
    @EJB
    private WarehouseListRemote formSession;
    
    @EJB
    private AddressesListRemote addressListSession;
    
    @Before
    public void setUp() {
        if ( formSession==null ){
            try {
                formSession = InitialContext.doLookup(WarehouseListRemote.class.getName());
                addressListSession = InitialContext.doLookup(AddressesListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
    }
    
    @Test
    public void methodsTest(){
        EntityProperties entityProperties = 
            formSession.getWarehouseEntityProperties();
        Assert.assertNotNull(entityProperties);
    }
    
    /**
     * Test - create, retrieve, update, delete operations
     * over Warehouse
     * @throws UncompleteUnitTestException 
     */
    @SuppressWarnings("unchecked")
    @Test
    public void crudTest() throws UncompleteUnitTestException{
        //create
        Warehouse inserted = null;
        for (int i = 0; i < 5; i++) {
            inserted = createNew();
            if ( inserted !=null )
                break;
        }
        
        if ( inserted!=null ){
            Assert.assertNotNull(inserted.getWarehouseId());
            //delete
            formSession.deleteWarehouse(inserted);
        }
        
        //retrieve
        List l = list();
        Assert.assertNotNull(l);
    }
    

    private List<Warehouse> list() {
        return formSession.listWarehousesByName();
    }

    private Warehouse createNew() throws UncompleteUnitTestException {
        AppSession.get().login("sdfs", "sdfsdf");
        
        DataObject branchParent = AppSession.get().getLoginOrganizationDataObject();
        List<Address> addresses = addressListSession.getAddresses(branchParent);
        
        if ( addresses.size()==0 )
            throw new UncompleteUnitTestException("Not available branches to select from for branchParent= dataobject.id"+branchParent.getDataObjectId());
        
        Address branch = addresses.get(TestUtils.nextInteger(addresses.size()));
        Person p = null;
        for (Address address : addresses) {
            List<Person> persons = formSession.getWarehouseMenForBranch(address.getDataObject());
            if ( persons.size()>0 ){
                p  = persons.get(TestUtils.nextInteger(persons.size()));
            }
        }

        if ( p==null )
            throw new UncompleteUnitTestException("Not available persons to select from. ");
        
        Warehouse result = formSession.newWarehouse(null);
        result.setAddress(branch);
        result.setWarehouseman(p);
        
        try{
            return
            formSession.saveWarehouse(result);
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