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
    private String getterName;
    private String setterName;
    private JComponent jComponent;

    public PropertyBean(Object bean, String getterName, String setterName, JComponent jComponent) {
        this.bean = bean;
        this.getterName = getterName;
        this.setterName = setterName;
        this.jComponent = jComponent;
    }

    public Object getBean() {
        return bean;
    }

    public String getGetterName() {
        return getterName;
    }

    public String getSetterName() {
        if(setterName != null) {
            return setterName;
        }

        return getterName;
    }

    public JComponent getJComponent() {
        return jComponent;
    }
}
