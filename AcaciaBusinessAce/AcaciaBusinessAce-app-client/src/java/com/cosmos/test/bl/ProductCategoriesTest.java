package com.cosmos.test.bl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.impl.ProductsListBean;
import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	06.04.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Business logic test for methods of {@link ProductsListBean} which
 * serve the product categories presentation logic 
 *
 */
public class ProductCategoriesTest {
    
    @EJB
    private ProductsListRemote formSession;
    
    @Before
    public void setUp() {
        if ( formSession==null ){
            try {
                formSession = InitialContext.doLookup(ProductsListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
    }
    
    @Test
    public void methodsTest(){
        EntityProperties entityProperties = formSession.getProductCategoryEntityProperties();
        Assert.assertNotNull(entityProperties);
        
        ProductCategory newChild = createNew(TestUtils.getRandomString(15));
        ProductCategory newParent = createNew(TestUtils.getRandomString(17));
        
        ProductCategory updatedChild = formSession.updateParents(newParent, newChild);
        Assert.assertEquals(updatedChild.getParentCategory(), newParent);
        
        List<ProductCategory> toDel = new ArrayList<ProductCategory>();
        toDel.add(newChild);
        toDel.add(newParent);
        boolean result = formSession.deleteProductCategories(toDel);
        Assert.assertTrue(result);
    }
    
    /**
     * Test - create, retrieve, update, delete operations
     * over ProductCategory
     */
    @Test
    public void crudTest(){
        String nameInsert = TestUtils.getRandomString(16);
        
        //create
        ProductCategory inserted = createNew(nameInsert);
        Assert.assertNotNull(inserted);
        Assert.assertNotNull(inserted.getProductCategoryId());
        
        businessValidationTest(inserted);
        
        //retrieve
        List<ProductCategory> entities = 
            list();
        Assert.assertNotNull(entities);
        
        //find
        ProductCategory found = find(entities, inserted);
        Assert.assertNotNull(found);
        
        //update
        String nameUpdate = TestUtils.getRandomString(16);
        found.setCategoryName(nameUpdate);
        ProductCategory updated = update(found);
        Assert.assertEquals(nameUpdate, updated.getCategoryName());
        
        //delete
        boolean deleted = formSession.deleteProductCategory(updated);
        Assert.assertTrue(deleted);
    }
    
    private ProductCategory update(ProductCategory found) {
        return
        formSession.saveProductCategory(found);
    }

    private ProductCategory find(List<ProductCategory> list, ProductCategory inserted) {
        for (ProductCategory entity : list) {
            if ( inserted.getProductCategoryId().equals(entity.getProductCategoryId()))
                return entity;
        }
        
        Assert.fail(" with id: "+inserted.getProductCategoryId()+" not found after insertion!");
        return null;
    }

    private List<ProductCategory> list() {
        return formSession.getProductsCategories(null);
    }

    private ProductCategory createNew(String nameInsert) {
        ProductCategory result = formSession.newProductCategory(null);
        result.setCategoryName(nameInsert);
        
        return
        formSession.saveProductCategory(result);
    }
    
    /**
     * Test inserting/updating invalid instances
     * @param existing - test duplication errors against
     * an existing record.
     * For example test inserting new object that has the same 'name'
     * property (which should be unique) as existing
     */
    private void businessValidationTest(ProductCategory existing){
        try{
            createNew(existing.getCategoryName());
            Assert.fail("Remote exeption, caused by BusinessValidationException expected.");
        }catch (Exception re){
            ValidationException e = TestUtils.extractValidationException(re);
            Assert.assertNotNull(e);
        }
        
        ProductCategory currentParent = existing.getParentCategory();
        try{
            //set the parent to itself, and expect to fail
            existing.setParentCategory(existing);
            formSession.saveProductCategory(existing);
            Assert.fail("Remote exeption, caused by BusinessValidationException expected.");
        }catch (Exception re){
            ValidationException e = TestUtils.extractValidationException(re);
            Assert.assertNotNull(e);
        }
        //restore the old parent - thus living it unchanged after the method returns
        existing.setParentCategory(currentParent);
    }
}
