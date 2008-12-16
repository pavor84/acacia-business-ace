/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.util;

import java.util.Set;

/**
 *
 * @author Miro
 */
public interface ManageableAcaciaProperties
    extends AcaciaProperties
{
    boolean isChanged();
    void clearChanges();
    Set<String> getDeletedItems();
    Set<String> getNewItems();
    void setParentProperties(AcaciaProperties parentProperties);
}
