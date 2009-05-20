/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import javax.swing.JComponent;

/**
 *
 * @author Miro
 */
public class PropertyBean {

    private Object bean;
    private String setterName;
    private JComponent jComponent;

    public PropertyBean(Object bean, String setterName, JComponent jComponent) {
        this.bean = bean;
        this.setterName = setterName;
        this.jComponent = jComponent;
    }

    public Object getBean() {
        return bean;
    }

    public String getSetterName() {
        return setterName;
    }

    public JComponent getJComponent() {
        return jComponent;
    }
}
