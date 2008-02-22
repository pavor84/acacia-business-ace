/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.TextResource;

/**
 *
 * @author miro
 */
public interface DatabaseResource
    extends TextResource
{
    DbResource getDbResource();
    void setDbResource(DbResource resource);
}
