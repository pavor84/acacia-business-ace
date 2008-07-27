/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface DataObjectTypeRemote {

    DataObjectType getDataObjectType(int id);
    DataObjectType getDataObjectType(String name);

    DataObjectType persist(DataObjectType dataObjectType);

    void remove(DataObjectType dataObjectType);
    
    /**
     * Lists all objects for the specified data object types
     * 
     * @param dataObjectType
     * @return list of DataObjectBeans
     */
    List<DataObjectBean> getDataObjectBeans(DataObjectType dataObjectType);
    
}
