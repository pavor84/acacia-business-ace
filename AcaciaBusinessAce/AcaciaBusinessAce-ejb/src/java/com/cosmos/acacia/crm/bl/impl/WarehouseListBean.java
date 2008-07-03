package com.cosmos.acacia.crm.bl.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.contactbook.AddressesListLocal;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.SimpleProduct;
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
    @EJB
    private AddressesListLocal addressesList;

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

//    @SuppressWarnings("unchecked")
//    @Override
//    public DataObject getDataObjectWithAddresses() {
//        List<Address> allAddresses =
//            em.createQuery("select a from Address a where a.dataObject.parentDataObjectId is not null")
//            .getResultList();
//
//        //add-hoc temporary logic, to consider the parent data object with most addresses
//        Map<DataObject, Long> addressesCount = new HashMap<DataObject, Long>();
//
//        for (Address address : allAddresses) {
//            DataObject parent = null;
//            if ( address.getDataObject()!=null && address.getDataObject().getParentDataObject()!=null )
//                parent = address.getDataObject().getParentDataObject();
//            if ( parent!=null ){
//                Long curValue = addressesCount.get(parent);
//                if ( curValue==null )
//                    curValue = new Long(1);
//                else
//                    curValue++;
//                addressesCount.put(parent, curValue);
//            }
//        }
//
//        //find the one with most addresses
//        Long biggestCount = new Long(0);
//        DataObject choosen = null;
//        for (Map.Entry<DataObject, Long> parentEntry : addressesCount.entrySet()) {
//            if ( parentEntry.getValue()>biggestCount ){
//                biggestCount = parentEntry.getValue();
//                choosen = parentEntry.getKey();
//            }
//        }
//
////        if ( allAddresses!=null && allAddresses.size()>0 ){
////            try{
////                return allAddresses.get(0).getDataObject().getParentDataObject();
////            }catch (NullPointerException npe){
////                npe.printStackTrace();
////                return null;
////            }
////        }
//
//        return choosen;
//    }

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

        entity = em.merge(entity);
        esm.persist(em, entity);
        return entity;
    }
    
    /**
     * @see com.cosmos.acacia.crm.bl.impl.WarehouseListRemote#getWarehouseProductsTotals()
     */
    public Map<WarehouseProduct, List<WarehouseProduct>> getWarehouseProductsTotals(){
       List<WarehouseProduct> products = listWarehouseProducts();
       
       Map<WarehouseProduct, List<WarehouseProduct>> result = new HashMap<WarehouseProduct, List<WarehouseProduct>>();
       
       Map<SimpleProduct, WarehouseProduct> simpleProductToSummaryProduct = new HashMap<SimpleProduct, WarehouseProduct>();
       
       for (WarehouseProduct warehouseProduct : products) {
           SimpleProduct simpleProduct = warehouseProduct.getProduct();
           WarehouseProduct summaryProduct = simpleProductToSummaryProduct.get(simpleProduct);
           
           List<WarehouseProduct> productsList = null;
               
           //make sure we have entry for the summary product
           //also create or get the products list
           if ( summaryProduct==null ){
               summaryProduct = createNewSummaryProduct(warehouseProduct);
               simpleProductToSummaryProduct.put(simpleProduct, summaryProduct);
               
               productsList = new ArrayList<WarehouseProduct>();
               result.put(summaryProduct, productsList);
           }else{
               productsList = result.get(summaryProduct);
           }
           
           //modify the current summary product by adding the new encountered quantities
           accumulateQuantities(summaryProduct, warehouseProduct);
           
           //register the encountered product in the list
           productsList.add(warehouseProduct);
       }
       
       return result;
    }
    
    private static class SummaryWarehouseProduct extends WarehouseProduct{
        private BigDecimal prefferedDefaultQuantity;
        private BigDecimal prefferedMinQuantity;
        private BigDecimal prefferedMaxQuantity;
        private int index;
        public BigDecimal getPrefferedDefaultQuantity() {
            return prefferedDefaultQuantity;
        }
        public void setPrefferedDefaultQuantity(BigDecimal prefferedDefaultQuantity) {
            this.prefferedDefaultQuantity = prefferedDefaultQuantity;
        }
        public BigDecimal getPrefferedMinQuantity() {
            return prefferedMinQuantity;
        }
        public void setPrefferedMinQuantity(BigDecimal prefferedMinQuantity) {
            this.prefferedMinQuantity = prefferedMinQuantity;
        }
        public BigDecimal getPrefferedMaxQuantity() {
            return prefferedMaxQuantity;
        }
        public void setPrefferedMaxQuantity(BigDecimal prefferedMaxQuantity) {
            this.prefferedMaxQuantity = prefferedMaxQuantity;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        @Override
        public boolean equals(Object object) {
            if ( object instanceof SummaryWarehouseProduct ){}
            else return false;
            return getProduct().equals(((SummaryWarehouseProduct)object).getProduct());
        }
        @Override
        public int hashCode() {
            return getProduct().hashCode();
        }
    }
    
    private void accumulateQuantities(WarehouseProduct acc, WarehouseProduct value) {
        //retrieve the appropriate values
        BigDecimal defaultQuantity = value.getPrefferedDefaultQuantity()==null?BigDecimal.ZERO:value.getPrefferedDefaultQuantity();
        BigDecimal minQuantity = value.getPrefferedMinQuantity()==null?BigDecimal.ZERO:value.getPrefferedMinQuantity();
        BigDecimal maxQuantity = value.getPrefferedMaxQuantity()==null?BigDecimal.ZERO:value.getPrefferedMaxQuantity();
        
        BigDecimal quantityInStock = value.getQuantityInStock()==null?BigDecimal.ZERO:value.getQuantityInStock();
        BigDecimal quantityDue = value.getQuantityDue()==null?BigDecimal.ZERO:value.getQuantityDue();
        BigDecimal reservedQuantity = value.getReservedQuantity()==null?BigDecimal.ZERO:value.getReservedQuantity();
        BigDecimal soldQuantity = value.getSoldQuantity()==null?BigDecimal.ZERO:value.getSoldQuantity();
        BigDecimal orderedQuantity = value.getOrderedQuantity()==null?BigDecimal.ZERO:value.getOrderedQuantity();
        
        //set them up
        SummaryWarehouseProduct accumulator = (SummaryWarehouseProduct) acc;
        accumulator.setPrefferedDefaultQuantity(accumulator.getPrefferedDefaultQuantity().add(defaultQuantity));
        accumulator.setPrefferedMinQuantity(accumulator.getPrefferedMinQuantity().add(minQuantity));
        accumulator.setPrefferedMaxQuantity(accumulator.getPrefferedMaxQuantity().add(maxQuantity));
        
        accumulator.setQuantityInStock(accumulator.getQuantityInStock().add(quantityInStock));
        accumulator.setQuantityDue(accumulator.getQuantityDue().add(quantityDue));
        accumulator.setReservedQuantity(accumulator.getReservedQuantity().add(reservedQuantity));
        accumulator.setSoldQuantity(accumulator.getSoldQuantity().add(soldQuantity));
        accumulator.setOrderedQuantity(accumulator.getOrderedQuantity().add(orderedQuantity));
    }

    private WarehouseProduct createNewSummaryProduct(WarehouseProduct template) {
        SummaryWarehouseProduct result = new SummaryWarehouseProduct();
        result.setProduct(template.getProduct());
        result.setPrefferedDefaultQuantity(BigDecimal.ZERO);
        result.setPrefferedMinQuantity(BigDecimal.ZERO);
        result.setPrefferedMaxQuantity(BigDecimal.ZERO);
        result.setQuantityInStock(BigDecimal.ZERO);
        result.setQuantityDue(BigDecimal.ZERO);
        result.setReservedQuantity(BigDecimal.ZERO);
        result.setSoldQuantity(BigDecimal.ZERO);
        result.setOrderedQuantity(BigDecimal.ZERO);
        return result;
    }

    @Override
    public List<Person> getWarehouseMenForBranch(BigInteger dataObjectId) {
        Set<Person> persons = new HashSet<Person>();
        List<ContactPerson> contactPersons = addressesList.getContactPersons(dataObjectId);
        for (ContactPerson contactPerson : contactPersons) {
            persons.add(contactPerson.getContact());
        }
        List<Person> result = new ArrayList<Person>(persons);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WarehouseProduct> listWarehouseProducts(Warehouse warehouse) {
        if ( warehouse==null )
            throw new IllegalArgumentException("Please supply not null warehouse!");
        
        Query q = em.createNamedQuery("WarehouseProduct.findForWarehouse");
        q.setParameter("warehouse", warehouse);
        
        List<WarehouseProduct> result = q.getResultList();
        
        return result;
    }

    @Override
    public EntityProperties getWarehouseProductTableProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(WarehouseProduct.class);
        //the next two prices - are security restricted values
        entityProperties.removePropertyDetails("purchasePrice");
        //the next tree quantities will be retrieved from synthetic accessors rather the default getters
        entityProperties.removePropertyDetails("minimumQuantity");
        entityProperties.removePropertyDetails("maximumQuantity");
        entityProperties.removePropertyDetails("defaultQuantity");
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }
}
