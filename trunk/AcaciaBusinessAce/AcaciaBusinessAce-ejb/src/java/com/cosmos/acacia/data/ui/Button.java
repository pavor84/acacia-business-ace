/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.data.ui;

/**
 *
 * @author Miro
 */
public class Button extends Widget {

    public static final String ELEMENT_NAME = "button";

    public Button() {
        super(true, false);
    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }
}
