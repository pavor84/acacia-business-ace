/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.document;

import com.cosmos.acacia.crm.data.DbResource;


/**
 *
 * @author Miro
 */
public interface StatusEnabledDocument {

    DbResource getDocumentStatus();

    void setDocumentStatus(DbResource documentStatus);
}
