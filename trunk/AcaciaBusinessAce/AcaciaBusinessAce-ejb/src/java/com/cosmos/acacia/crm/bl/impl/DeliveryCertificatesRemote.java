/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.beansbinding.EntityProperties;
import javax.ejb.Remote;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.DeliveryCertificateAssignment;
import com.cosmos.acacia.crm.data.Invoice;
import java.util.List;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.enums.DeliveryCertificateReason;
import java.math.BigInteger;

/**
 *
 * @author daniel
 */
@Remote
public interface DeliveryCertificatesRemote {

    DeliveryCertificate createStubDeliveryCert();

    EntityProperties getDeliveryCertificateEntityProperties();

    java.util.List<com.cosmos.acacia.crm.data.DeliveryCertificate> getDeliveryCertificates(String parentId);

    DeliveryCertificate newDeliveryCertificate(Object parent);

    List<DbResource> getReasons();

    List<DbResource> getDeliveryTypes();

    List<Organization> getForwarders();

    List<DeliveryCertificateAssignment> getDocuments(DeliveryCertificateReason reason);

    void mapDeliveryCertificateToInvoice(BigInteger deliveryCertificateId, BigInteger documentId);
    
}
