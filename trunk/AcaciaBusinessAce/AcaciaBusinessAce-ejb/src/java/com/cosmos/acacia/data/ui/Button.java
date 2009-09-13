/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.data.ui;

/**
 *
 * @author Miro
 */
public class Button extends AbstractMenu {

    public static final String ELEMENT_NAME = "button";

    public Button() {
        super(true);
    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }
}
