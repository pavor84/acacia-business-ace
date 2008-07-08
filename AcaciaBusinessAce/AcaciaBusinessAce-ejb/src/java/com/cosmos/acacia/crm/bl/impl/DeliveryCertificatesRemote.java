/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.beansbinding.EntityProperties;
import javax.ejb.Remote;
import com.cosmos.acacia.crm.data.DbResource;
import java.util.List;

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
    
}
