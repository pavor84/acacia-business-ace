/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObjectBean;
import javax.ejb.Remote;

/**
 *
 * @author miro
 */
@Remote
public interface EntityStoreManagerRemote {

    void prePersist(DataObjectBean entity);

}
