/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.data.ui;

import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class Menu extends AbstractMenu implements Serializable {

    public static final String ELEMENT_NAME = "menu";

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }
}
