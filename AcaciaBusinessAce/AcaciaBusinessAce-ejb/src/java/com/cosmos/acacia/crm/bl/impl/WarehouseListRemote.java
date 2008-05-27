package com.cosmos.acacia.crm.bl.impl;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.data.WarehouseProduct;
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
     * @return not-empty list
     */
    List<Warehouse> listWarehousesByName();

    /**
     * Remove the object.
     * If impossible throws exception.
     * @param rowObject
     */
    void deleteWarehouse(Warehouse rowObject);

    /**
     * Create new empty (not persisted) instance.
     * @param object
     * @return not null Warehouse
     */
    Warehouse newWarehouse(Object object);

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
    List<WarehouseProduct> listWarehouseProducts();

    /**
     * Remove the supplied product.
     * Throw exception if not possible.
     * @param rowObject
     */
    void deleteWarehouseProduct(WarehouseProduct rowObject);

    /**
     * Create empty entity which is not persisted.
     * @return not null
     */
    WarehouseProduct newWarehouseProduct();

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
     * @param dataObject
     * @return
     */
    List<Person> getWarehouseMenForBranch(DataObject dataObject);
}
