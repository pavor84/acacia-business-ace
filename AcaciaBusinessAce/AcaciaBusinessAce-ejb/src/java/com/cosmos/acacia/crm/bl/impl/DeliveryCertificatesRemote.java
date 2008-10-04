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
import com.cosmos.beansbinding.EntityProperties;

/**
 *
 * @author daniel
 */
@Remote
public interface DeliveryCertificatesRemote {

    EntityProperties getDeliveryCertificateEntityProperties();

    List<DeliveryCertificate> getDeliveryCertificates(BigInteger parentId);

    DeliveryCertificate newDeliveryCertificate(BigInteger parentId);

    List<DbResource> getReasons();

    List<DbResource> getDeliveryTypes();

    DeliveryCertificate saveDeliveryCertificate(DeliveryCertificate deliveryCertificate, DeliveryCertificateAssignment assignment);

    int deleteDeliveryCertificate(DeliveryCertificate deliveryCertificate);
    
    DeliveryCertificateAssignment newDeliveryCertificateAssignment();
    
    DeliveryCertificateAssignment newDeliveryCertificateAssignment(DeliveryCertificate ds, DataObjectBean document);
    
    EntityProperties getDeliveryCertificateAssignmentEntityProperties();
}
