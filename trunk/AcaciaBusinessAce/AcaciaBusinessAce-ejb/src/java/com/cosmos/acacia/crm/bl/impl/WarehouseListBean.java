package com.cosmos.acacia.crm.bl.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.data.WarehouseProduct;
import com.cosmos.acacia.crm.validation.impl.WarehouseProductValidatorLocal;
import com.cosmos.acacia.crm.validation.impl.WarehouseValidatorLocal;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Implement business logic behind the Warehouse module functionality
 */
@Stateless
public class WarehouseListBean implements WarehouseListRemote {
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private EntityStoreManagerLocal esm;
    
    @EJB
    private WarehouseValidatorLocal warehouseValidator;
    @EJB
    private WarehouseProductValidatorLocal warehouseProductValidator;
    
    @Override
    public EntityProperties getWarehouseEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(Warehouse.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Warehouse> listWarehousesByName() {
        Query q = em.createNamedQuery("Warehouse.findByAddressName");
        
        List<Warehouse> result = q.getResultList();
        
        return result;
    }

    @Override
    public void deleteWarehouse(Warehouse rowObject) {
        esm.remove(em, rowObject);
    }

    @Override
    public Warehouse newWarehouse(Object object) {
        Warehouse w = new Warehouse();
        return w;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataObject getDataObjectWithAddresses() {
        List<Address> allAddresses =
            em.createQuery("select a from Address a where a.dataObject.parentDataObject is not null")
            .getResultList();
        
        if ( allAddresses!=null && allAddresses.size()>0 ){
            try{
                return allAddresses.get(0).getDataObject().getParentDataObject();
            }catch (NullPointerException npe){
                npe.printStackTrace();
                return null;
            }
        }
            
        return null;
    }

    @Override
    public Warehouse saveWarehouse(Warehouse entity) {
        warehouseValidator.validate(entity); 
        
        esm.persist(em, entity);
        return entity; 
    }

    @Override
    public void deleteWarehouseProduct(WarehouseProduct rowObject) {
        esm.remove(em, rowObject);
    }

    @Override
    public EntityProperties getWarehouseProductEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(WarehouseProduct.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WarehouseProduct> listWarehouseProducts() {
        Query q = em.createNamedQuery("WarehouseProduct.findAll");
        
        List<WarehouseProduct> result = q.getResultList();
        
        return result;
    }

    @Override
    public WarehouseProduct newWarehouseProduct() {
        WarehouseProduct wp = new WarehouseProduct();
        return wp;
    }

    @Override
    public WarehouseProduct saveWarehouseProduct(WarehouseProduct entity) {
        warehouseProductValidator.validate(entity); 
        
        esm.persist(em, entity);
        return entity; 
    }
}
