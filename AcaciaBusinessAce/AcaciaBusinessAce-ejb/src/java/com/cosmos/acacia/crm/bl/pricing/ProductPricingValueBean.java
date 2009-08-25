package com.cosmos.acacia.crm.bl.pricing;

import java.util.UUID;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.product.ProductPercentValue;
import com.cosmos.acacia.crm.data.product.ProductPercentValue.Type;
import com.cosmos.acacia.crm.validation.impl.ProductPricingValueValidatorLocal;
import com.cosmos.beansbinding.EntityProperties;

/**
 * 
 * Created	:	04.01.2009
 * @author	Petar Milev
 *
 */
@Stateless
public class ProductPricingValueBean implements ProductPricingValueLocal, ProductPricingValueRemote{
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    
    @EJB
    private ProductPricingValueValidatorLocal validator;
    
    public EntityProperties getListingEntityProperties() {

        EntityProperties entityProperties = esm.getEntityProperties(ProductPercentValue.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;

    }

    @SuppressWarnings("unchecked")
    public List<ProductPercentValue> listProductPricingValues(UUID parentDataObjectId, Type type) {
        if (parentDataObjectId == null)
            throw new IllegalArgumentException("parentDataObjectId can't be null");

        Query q = em.createNamedQuery("ProductPercentValue.findForParentAndDeleted");
        q.setParameter("parentDataObjectId", parentDataObjectId);
        q.setParameter("deleted", false);
        q.setParameter("type", type);

        List<ProductPercentValue> result = q.getResultList();
        return result;
    }

    public void deleteProductPricingValue(ProductPercentValue entity) {
        if (entity == null)
            throw new IllegalArgumentException("null: 'ProductPricingValue'");
        esm.remove(em, entity);
    }

    public ProductPercentValue newProductPricingValue(UUID parentDataObjectId, Type type) {
        ProductPercentValue c = new ProductPercentValue();
        c.setParentId(parentDataObjectId);
        c.setType(type);
        return c;
    }

    public EntityProperties getDetailEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(ProductPercentValue.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    public ProductPercentValue saveProductPricingValue(ProductPercentValue p) {
        validator.validate(p);
        
        esm.persist(em, p);

        return p;
    }
}
