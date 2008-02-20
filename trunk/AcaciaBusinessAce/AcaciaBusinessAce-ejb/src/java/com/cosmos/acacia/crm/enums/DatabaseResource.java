/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;

/**
 *
 * @author miro
 */
public interface DatabaseResource {

    DbResource getDbResource();
    void setDbResource(DbResource resource);
    String getShortName();
    String getName();
}
