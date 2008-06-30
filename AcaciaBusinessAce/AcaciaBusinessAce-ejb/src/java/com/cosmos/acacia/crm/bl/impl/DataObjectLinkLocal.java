/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectLink;
import javax.ejb.Local;

/**
 *
 * @author rlozanov
 */
@Local
public interface DataObjectLinkLocal {

    DataObjectLink newDataObjectLink(DataObjectBean linkedObject);

    DataObjectLink saveDataObjectLink(DataObjectLink dataObjectLink);
    
}
