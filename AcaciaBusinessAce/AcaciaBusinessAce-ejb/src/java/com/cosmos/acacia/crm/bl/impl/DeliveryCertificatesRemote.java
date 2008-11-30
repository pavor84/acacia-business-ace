/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.data.DeliveryCertificateAssignment;
import com.cosmos.acacia.crm.data.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber;
import com.cosmos.beansbinding.EntityProperties;

/**
 *
 * @author daniel
 */
@Remote
public interface DeliveryCertificatesRemote {

	/**
	 * Create entity properties for details screen
	 */
    EntityProperties getDeliveryCertificateEntityProperties();
    
    /**
     * Create entity properties for a DeliveryCertificates List displayed for a given warehouse 
     */
    EntityProperties getDeliveryCertificateListEntityProperties();
    
    /**
     * Create entity properties for a DeliveryCertificateItem listed for a certificate
     */
    EntityProperties getDeliveryCertificateItemsEntityProperties();
        
    /**
     * Create entity properties for a DeliveryCertificateItem displayed in details screen
     */
    EntityProperties getDeliveryCertificateItemDetailsEntityProperties();
    
    /**
     * Create entity properties for a certificate assignment.
     */
    EntityProperties getDeliveryCertificateAssignmentEntityProperties();
    
    /**
     * Create entity properties for the list of serial numbers for given DeliveryCertificateItem
     */
    EntityProperties getDeliveryCertificateSerialNumberListEntityProperties();
    
    /**
     * Get all not deleted certificates for a specified Warehouse
     * @param parentId - identifier of a Warehouse
     * @return list of Entities
     */
    List<DeliveryCertificate> getDeliveryCertificates(BigInteger parentId);
    
    /**
     * Get DeliveryCertificateItems for a specified DeliveryCertificate 
     * @param parentId - identifier of a DeliveryCertificate
     * @return
     */
    List<DeliveryCertificateItem> getDeliveryCertificateItems(BigInteger parentId);

    /**
     * 
     * @param itemId
     * @return
     */
    DeliveryCertificateItem getDeliveryCertificateItemById(BigInteger itemId);
    
    /**
     * Get Serial Numbers for a specified Delivery Certificate Item 
     * @param parentId - identifier of a DeliveryCertificateItem
     * @return
     */
    List<DeliveryCertificateSerialNumber> getDeliveryCertificateItemSerialNumbers(BigInteger parentId);
    
    /**
     * Create a fresh new instance of a DeliveryCertificate. Some of the properties are initialized.
     * @param parentId - identifier of a Warehouse
     * @return new Entity object
     */
    DeliveryCertificate newDeliveryCertificate(BigInteger parentId);
    
    /**
     * Create a fresh new instance of a DeliveryCertificate. Some of the properties are initialized.
     * @param parentId - identifier of a DeliveryCertificateItem 
     * @return new Entity object
     */
    DeliveryCertificateSerialNumber newDeliveryCertificateSerialNumber(BigInteger parentId);

    List<DbResource> getReasons();

    List<DbResource> getDeliveryTypes();

    /**
     * This method just persist the DeliveryCertificate object. No products are delivered yet.
     */
    DeliveryCertificate saveDeliveryCertificate(DeliveryCertificate deliveryCertificate, DeliveryCertificateAssignment assignment, List<DeliveryCertificateItem> items);
    
    /**
     * This method persist the DeliveryCertificate and the products from warehouses are delivered. The certificate becomes in readonly mode.
     */
    DeliveryCertificate deliverDeliveryCertificate(DeliveryCertificate deliveryCertificate, DeliveryCertificateAssignment assignment, List<DeliveryCertificateItem> items);
    
    int deleteDeliveryCertificate(DeliveryCertificate deliveryCertificate);
    
    DeliveryCertificateAssignment newDeliveryCertificateAssignment();
    
    DeliveryCertificateAssignment newDeliveryCertificateAssignment(DataObjectBean document);
    
    DeliveryCertificateItem newDeliveryCertificateItem(DataObjectBean source);
    
    
}
