package com.cosmos.test.bl;

import java.util.List;

import javax.ejb.EJB;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListRemote;
import com.cosmos.acacia.crm.bl.contactbook.OrganizationsListRemote;
import com.cosmos.acacia.crm.bl.purchaseorder.PurchaseOrderListBean;
import com.cosmos.acacia.crm.bl.purchaseorder.PurchaseOrderListRemote;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.purchase.PurchaseOrder;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Business logic test
 * for {@link PurchaseOrderListBean}
 *
 * Created	:	29.07.2008
 * @author	Petar Milev
 *
 */
public class PurchaseOrderListTest extends BaseTest{

    @EJB
    private PurchaseOrderListRemote formSession = AcaciaPanel.getBean(PurchaseOrderListRemote.class, false);

    @EJB
    private AddressesListRemote addressListSession = AcaciaPanel.getBean(AddressesListRemote.class, false);

    @EJB
    private OrganizationsListRemote organizationsListRemote = AcaciaPanel.getBean(OrganizationsListRemote.class, false);

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void methodsTest(){
        EntityProperties entityProperties =
            formSession.getDetailEntityProperties();
        Assert.assertNotNull(entityProperties);

        entityProperties = formSession.getListingEntityProperties();
        Assert.assertNotNull(entityProperties);

        entityProperties = formSession.getItemDetailEntityProperties();
        Assert.assertNotNull(entityProperties);

        entityProperties = formSession.getItemsListEntityProperties();
        Assert.assertNotNull(entityProperties);

        Assert.assertNotNull(formSession.getCurrencies());

        Assert.assertNotNull(formSession.getDeliveryMethods());

        Assert.assertNotNull(formSession.getStatuses());

        Assert.assertNotNull(formSession.getPendingPurchaseOrders());
    }

    /**
     * Test - create, retrieve, update, delete operations
     * over PurchaseOrder
     * @throws UncompleteUnitTestException
     */
    @SuppressWarnings("unchecked")
    @Test
    public void crudTest() throws UncompleteUnitTestException{
        //create
        PurchaseOrder inserted = null;
        for (int i = 0; i < 5; i++) {
            inserted = createNew();
            if ( inserted !=null )
                break;
        }

        if ( inserted!=null ){
            Assert.assertNotNull(inserted.getPurchaseOrderId());
            //delete
            formSession.deletePurchaseOrder(inserted);
        }

        //retrieve
        List l = list();
        Assert.assertNotNull(l);
    }


    private List<PurchaseOrder> list() {
        return formSession.getPurchaseOrders();
    }

    private PurchaseOrder createNew() throws UncompleteUnitTestException {

        DataObject branchParent = getOrganization().getDataObject();
        List<Address> addresses = addressListSession.getAddresses(branchParent.getDataObjectId());

        if ( addresses.size()==0 )
            throw new UncompleteUnitTestException("Not available branches to select from for branchParent= dataobject.id"+branchParent.getDataObjectId());

        PurchaseOrder result = formSession.newPurchaseOrder();
        List<Organization> orgs = organizationsListRemote.getOrganizations(getOrganizationId());
        Organization o = null;
        List<ContactPerson> persons = null;
        int oIdx = 0;
        while ((persons==null || persons.isEmpty()) && oIdx<orgs.size()){
            o = orgs.get(oIdx++);
            persons = formSession.getSupplierContacts(o);
        }

        if ( persons==null || persons.isEmpty() ){
            throw new UncompleteUnitTestException("Not available contact persons to select from. ");
        }

        result.setSupplier(o);
        result.setSupplierName(o.getDisplayName());
        result.setSupplierContact(persons.get(0));
        result.setSupplierContactName(persons.get(0).getPerson().getDisplayName());

        //mean while do unrelated to createNew test, but do it here, because we found a supplier
        Assert.assertNotNull(formSession.getPendingPurchaseOrders(o));

        try{
            return
            formSession.savePurchaseOrder(result);
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
