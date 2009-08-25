/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import java.util.UUID;

import javax.ejb.Local;

import com.cosmos.acacia.crm.data.DeliveryCertificate;

/**
 *
 * @author daniel
 */
@Local
public interface DeliveryCertificatesLocal extends DeliveryCertificatesRemote {

	/**
	 * Get the entity by its ID
	 * @param deliveryCertificateId
	 * @return
	 */
	DeliveryCertificate getDeliveryCertificateById(UUID deliveryCertificateId);
	
}
