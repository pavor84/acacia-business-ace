/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

/**
 *
 * @author Miro
 */
public abstract class ChildEntityBean extends DataObjectBean {

    public abstract DataObjectBean getParentEntity();

    public abstract void setParentEntity(DataObjectBean parentEntity);
}
