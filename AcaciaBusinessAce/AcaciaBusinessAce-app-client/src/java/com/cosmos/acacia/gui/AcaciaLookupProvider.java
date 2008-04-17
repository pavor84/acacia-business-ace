/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

/**
 * 
 * The interface defines the operations needed for the 
 * AcaciaLookup component.
 * Every form that uses the component should provide such
 * instance to handle the interaction between the two.
 *
 * @author jchan
 */
public interface AcaciaLookupProvider {

    /**
     * The provider should initialize all needed visual components and
     * synchronously return the selected object or null.
     * 
     * @return
     */
    public Object showSelectionControl();
}
