/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.enums.DatabaseResource;
import javax.ejb.Local;

/**
 *
 * @author miro
 */
@Local
public interface DatabaseResourceLocal {

    void initDatabaseResource(Class<? extends DatabaseResource> dbResourceClass);
    
}
