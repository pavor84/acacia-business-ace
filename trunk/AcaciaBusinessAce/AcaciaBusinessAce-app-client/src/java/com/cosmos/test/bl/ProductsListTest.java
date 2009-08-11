package com.cosmos.test.bl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote;
import com.cosmos.acacia.crm.bl.impl.ProductsListBean;
import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.crm.data.product.ProductCategory;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.enums.MeasurementUnit.Category;
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
 * {@link ProductsListBean}
 *
 */
public class ProductsListTest extends BaseTest {

    private ProductsListRemote formSession = AcaciaPanel.getBean(ProductsListRemote.class, false);

    private PatternMaskListRemote patternMaskListSession = AcaciaPanel.getBean(PatternMaskListRemote.class, false);;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void methodsTest(){
        List<DbResource> mu = formSession.getMeasureUnits();
        Assert.assertNotNull(mu);

        List<DbResource> muc = formSession.getMeasureUnits(Category.MassWeight);
        Assert.assertNotNull(muc);

        List<DbResource> pc = formSession.getProductColors();
        Assert.assertNotNull(pc);

        List<ProductCategory> cats = formSession.getProductsCategories(null);
        Assert.assertNotNull(cats);

        EntityProperties entityProperties =
            formSession.getProductEntityProperties();
        Assert.assertNotNull(entityProperties);
    }

    /**
     * Test - create, retrieve, update, delete operations
     * over PatternMaskFormat
     */
    @Test
    public void crudTest(){
        String nameInsert = TestUtils.getRandomString(16);
        String codeInsert = TestUtils.getRandomString(16);

        //create
        SimpleProduct inserted = null;
        try{
            inserted = createNew(nameInsert, codeInsert);
        }catch (Exception e){

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

    private SimpleProduct updateFormat(SimpleProduct found) {
        return
        formSession.saveProduct(found);
    }

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

    private List<SimpleProduct> list() {
        return formSession.getProducts(getOrganizationId());
    }

    private SimpleProduct createNew(String nameInsert, String codeInsert) throws UncompleteUnitTestException{
        SimpleProduct result = formSession.newProduct();
        result.setProductName(nameInsert);
        result.setProductCode(codeInsert);

        List<ProductCategory> cats =
            formSession.getProductsCategories(null);

        if ( cats.size()>0 )
            result.setCategory(cats.get(TestUtils.nextInteger(cats.size())));
        else{
            throw new UncompleteUnitTestException("At least one ProductCategory needed in database!");
        }

        List<PatternMaskFormat> formats = patternMaskListSession.listPatternsByName(getOrganizationId());
        if ( formats.size()>0 )
            result.setPatternMaskFormat(formats.get(TestUtils.nextInteger(formats.size())));
        else{
            throw new UncompleteUnitTestException("At least one PatternMaskFormat needed in database!");
        }

        return
        formSession.saveProduct(result);
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
            createNew(existing.getProductName(), existing.getProductCode());
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
