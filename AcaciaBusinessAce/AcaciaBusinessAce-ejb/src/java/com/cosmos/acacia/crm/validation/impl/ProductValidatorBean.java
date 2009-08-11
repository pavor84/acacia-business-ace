package com.cosmos.acacia.crm.validation.impl;

import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.validation.ValidationException;

import static com.cosmos.acacia.crm.validation.ValidationUtil.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created	:	17.03.2008 
 * @author	Petar Milev
 * @version $Id: $
 *
 * Implement complicated 'business' validation for the Product ( @see {@link SimpleProduct} ) here.
 */
@Stateless
public class ProductValidatorBean implements ProductValidatorLocal {
    
    @PersistenceContext  
    private EntityManager em;

    public void validate(SimpleProduct entity) throws ValidationException {
        ValidationException ve = new ValidationException();
        
        //required name  
        if ( isEmpty(entity.getProductName()) )
            ve.addMessage("productName", "Product.err.nameRequired");
        
        //unique name 
        Query getByName = em.createNamedQuery("SimpleProduct.findByProductName");
        getByName.setParameter("productName", entity.getProductName());
        if ( !checkUnique(getByName.getResultList(), entity))
            ve.addMessage("productName", "Product.err.nameInUse");
        
        //required code
        if ( isEmpty(entity.getProductCode()) )
            ve.addMessage("productCode", "Product.err.codeRequired");
        
        //unique code 
        Query getByCode = em.createNamedQuery("SimpleProduct.findByProductCode");
        getByCode.setParameter("productCode", entity.getProductCode());
        if ( !checkUnique(getByCode.getResultList(), entity))
            ve.addMessage("productCode", "Product.err.codeInUse");
        
        //required category
        if ( entity.getCategory()==null )
            ve.addMessage( "category", "Product.err.categoryRequired");
        
        //required measure unit
        if ( entity.getMeasureUnit()==null )
            ve.addMessage("measureUnit", "Product.err.measureUnitRequired");
        
        //required minimum quantity
        if ( entity.getMinimumQuantity()==null )
            ve.addMessage("minimumQuantity", "Product.err.minimumQuantity");
        
        //required quantity per package
        if ( entity.getQuantityPerPackage()==0 )
            ve.addMessage("quantityPerPackage", "Product.err.quantityPerPackage");
        
        //if we have validation messages - throw the exception since not everything is OK
        if ( !ve.getMessages().isEmpty() )
            throw ve;
    }
 
}
