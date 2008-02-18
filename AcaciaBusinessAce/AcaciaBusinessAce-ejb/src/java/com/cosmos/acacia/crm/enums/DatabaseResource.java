/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.enums;

/**
 *
 * @author miro
 */
public interface DatabaseResource<E extends Enum> {

    int getResourceId();
    void setResourceId(int resourceId);
    boolean isInitialized();
    void setInitialized(boolean initialized);

}
