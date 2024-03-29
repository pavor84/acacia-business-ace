package com.cosmos.acacia.crm.bl.impl;

import java.util.UUID;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import com.cosmos.acacia.crm.data.warehouse.WarehouseProduct;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	04.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Remote
public interface WarehouseListRemote {

    /**
     * get Entity properties for type warehouse
     * @return not null value
     */
    EntityProperties getWarehouseEntityProperties();

    /**
     * All warehouses sorted by name
     * @param UUID 
     * @return not-empty list
     */
    List<Warehouse> listWarehousesByName(UUID UUID);

    /**
     * Remove the object.
     * If impossible throws exception.
     * @param rowObject
     */
    void deleteWarehouse(Warehouse rowObject);

    /**
     * Create new empty (not persisted) instance.
     * @param parentId
     * @return not null Warehouse
     */
    Warehouse newWarehouse(UUID parentId);

    /**
     * Save operation.
     * @param entity - an entity to save (may be new, or already persisted)
     * @return the updated entity
     */
    Warehouse saveWarehouse(Warehouse entity);

    /**
     * Get entity properties for warehouse product
     * @return not null
     */
    EntityProperties getWarehouseProductEntityProperties();

    /**
     * Return all warehouse products
     * @return not null list
     */
    List<WarehouseProduct> listWarehouseProducts(UUID parentId);

    /**
     * Remove the supplied product.
     * Throw exception if not possible.
     * @param rowObject
     */
    void deleteWarehouseProduct(WarehouseProduct rowObject);

    /**
     * Create empty entity which is not persisted.
     * @param parentId - the organization
     * @return not null
     */
    WarehouseProduct newWarehouseProduct(UUID parentId, Warehouse warehouse);

    /**
     * Try to save a given warehouse product. If the entity is invalid,
     * throws {@link ValidationException}. On other error throws exception.
     * @param entity
     * @return an updated instance
     */
    WarehouseProduct saveWarehouseProduct(WarehouseProduct entity);

    /**
     * Return only the persons that can be warehouse man of the given branch.
     * The branch (Address instance) is identified by it's data object.
     * @param dataObjectId
     * @return
     */
    List<Person> getWarehouseMenForBranch(UUID dataObjectId);

    /**
     * List warehouse products for a given warehouse
     * @param warehouse - not null warehouse
     * @return - not null list of products
     */
    List<WarehouseProduct> listWarehouseProducts(Warehouse warehouse);

    /**
     * Returns the entity properties of warehouse product prepared for showing in table.
     * @return
     */
    EntityProperties getWarehouseProductTableProperties();

    /**
     * Returns map matching a summed warehouse product (quantities for all warehouses) against
     * a list of the actual warehouse products participating in the sum.
     * Thus the key of the map is synthetic {@link WarehouseProduct} while the value is a List
     * of actual persisted {@link WarehouseProduct}s
     * @param parentId - the parent id - shouldn't be null
     * @return not null map
     */
    Map<WarehouseProduct, List<WarehouseProduct>> getWarehouseProductsTotals(UUID parentId);
    
    /**
     * Get the warehouse seated at the specified address
     * @param branch
     * @return warehouse or null if not found
     */
    Warehouse getWarehouseForAddress(Address address);
}
