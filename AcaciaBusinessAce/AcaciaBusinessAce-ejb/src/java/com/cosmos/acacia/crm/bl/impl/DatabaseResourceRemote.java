/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.DatabaseResource;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author miro
 */
@Remote
public interface DatabaseResourceRemote {

    List<DbResource> getDbResources(Class<? extends DatabaseResource> dbResourceClass);
    
}
