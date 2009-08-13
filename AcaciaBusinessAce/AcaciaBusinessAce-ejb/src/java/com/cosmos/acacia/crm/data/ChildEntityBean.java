/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

/**
 *
 * @author Miro
 */
public interface ChildEntityBean<P> {

    P getParentEntity();

    void setParentEntity(P parentEntity);
}
