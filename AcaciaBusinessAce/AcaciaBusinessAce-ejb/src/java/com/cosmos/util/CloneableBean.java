/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util;

/**
 *
 * @author Miro
 */
public interface CloneableBean<T> extends Cloneable {

    T clone();
}
