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
     * Create a fresh new instance of a DeliveryCertificate. Some of the properties are initialized.
     * @param parentId - identifier of a Warehouse
     * @return new Entity object
     */
    DeliveryCertificate newDeliveryCertificate(BigInteger parentId);

    List<DbResource> getReasons();

    List<DbResource> getDeliveryTypes();

    DeliveryCertificate saveDeliveryCertificate(DeliveryCertificate deliveryCertificate, DeliveryCertificateAssignment assignment, List<DeliveryCertificateItem> items);
    
    int deleteDeliveryCertificate(DeliveryCertificate deliveryCertificate);
    
    DeliveryCertificateAssignment newDeliveryCertificateAssignment();
    
    DeliveryCertificateAssignment newDeliveryCertificateAssignment(DataObjectBean document);
    
    DeliveryCertificateItem newDeliveryCertificateItem(DataObjectBean source);
    
    
}
