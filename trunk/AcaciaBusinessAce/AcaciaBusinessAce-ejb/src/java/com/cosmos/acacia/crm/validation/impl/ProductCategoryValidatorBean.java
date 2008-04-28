package com.cosmos.acacia.crm.validation.impl;

import static com.cosmos.acacia.crm.validation.ValidationUtil.checkUnique;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	21.04.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Stateless
public class ProductCategoryValidatorBean implements ProductCategoryValidatorLocal{
    @PersistenceContext  
    private EntityManager em;

    public void validate(ProductCategory entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //unique name 
        Query getByName = em.createNamedQuery("ProductCategory.findByNameNotDeleted");
        getByName.setParameter("categoryName", entity.getCategoryName());
        if ( !checkUnique(getByName.getResultList(), entity))
            ve.addMessage("categoryName", "ProductCategory.err.nameInUse");
        
        //check cyclic parents
        ProductCategory ancestor = entity.getParentCategory();
        while ( ancestor!=null ){
            if ( ancestor.equals(entity) ){
                ve.addMessage("parentCategory", "ProductCategory.err.cyclicParent");
                break;
            }
            ancestor = ancestor.getParentCategory();
        }
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
 
}
