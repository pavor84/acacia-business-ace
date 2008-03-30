/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.listeners;

/**
 * A Listener that is added to nested forms, when they depend on
 * some actions/confirmations from the parent form.
 * 
 * Usage:
 * Add the listener to the nested form, and on dependent actions
 * invoke the listeners applied to that nested form.
 * Proceed only if "true" is returned
 *
 * @author Bozhidar Bozhanov
 */
public interface NestedFormListener {

    public boolean actionPerformed();
    
}
