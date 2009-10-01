/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.util.PersistentEntity;
import java.util.UUID;

/**
 *
 * @author Miro
 */
public interface DataObjectEntity extends PersistentEntity<DataObjectBean, UUID> {

    DataObject getDataObject();
}
