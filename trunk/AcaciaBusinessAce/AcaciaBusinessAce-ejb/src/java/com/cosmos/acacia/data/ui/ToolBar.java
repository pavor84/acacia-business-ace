/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.data.ui;

/**
 *
 * @author Miro
 */
public class ToolBar extends Widget {

    public static final String ELEMENT_NAME = "toolBar";

    public ToolBar() {
        super(false, true);
    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }
}
