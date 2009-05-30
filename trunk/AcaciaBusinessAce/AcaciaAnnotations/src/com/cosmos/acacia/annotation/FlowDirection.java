/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation;

/**
 * The insertion point direction by selecting
 * @author Miro
 */
public enum FlowDirection {

    /**
     * When tabbing, the insertion point moves from control to control from the
     * top of the form down.
     */
    Forward,

    /**
     * When tabbing, the insertion point moves from control to control for the
     * bottom of the form up.
     */
    Backward,

    /**
     * You can move forward or backward
     */
    Combine;
}
