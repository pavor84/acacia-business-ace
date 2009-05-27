/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.beansbinding.PropertyDetail;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JComponent;

/**
 *
 * @author Miro
 */
public class PropertyChangeHandler { //implements PropertyChangeListener {

    private Map<String, List<PropertyBean>> propertyBeansMap;

    public PropertyChangeHandler() {
    }

    /*@Override
    public void propertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        List<PropertyBean> propertyBeans = getPropertyBeansMap().get(propertyName);
        if (propertyBeans != null && propertyBeans.size() > 0) {
            Object value = event.getNewValue();
            for (PropertyBean propertyBean : propertyBeans) {
                Object bean = propertyBean.getBean();
                String setterName = propertyBean.getSetterName();
                JComponent jComponent = propertyBean.getJComponent();
                try {
                    PropertyUtils.setProperty(bean, setterName, value);
                    if (jComponent instanceof EntityListBinder) {
                        ((EntityListBinder) jComponent).refresh();
                    }
                } catch (Exception ex) {
                    throw new EntityPanelException("propertyName=" + propertyName +
                            ", value=" + value +
                            ", bean=" + bean + ", setterName=" + setterName +
                            ", jComponent=" + jComponent, ex);
                }
            }
        }
    }*/

    public Map<String, List<PropertyBean>> getPropertyBeansMap() {
        if (propertyBeansMap == null) {
            propertyBeansMap = new TreeMap<String, List<PropertyBean>>();
        }

        return propertyBeansMap;
    }

    public void addPropertyBean(List<PropertyDetail> propertyDetails, Object bean, JComponent jComponent) {
        for (PropertyDetail propertyDetail : propertyDetails) {
            addPropertyBean(propertyDetail, bean, jComponent);
        }
    }

    public void addPropertyBean(PropertyDetail propertyDetail, Object bean, JComponent jComponent) {
        addPropertyBean(propertyDetail.getGetter(), propertyDetail.getSetter(), bean, jComponent);
    }

    public void addPropertyBean(String getterName, String setterName, Object bean, JComponent jComponent) {
        addPropertyBean(new PropertyBean(bean, getterName, setterName, jComponent));
    }

    public void addPropertyBean(PropertyBean propertyBean) {
        Map<String, List<PropertyBean>> pbMap = getPropertyBeansMap();
        String getterName = propertyBean.getGetterName();
        List<PropertyBean> propertyBeans = pbMap.get(getterName);
        if (propertyBeans == null) {
            propertyBeans = new ArrayList<PropertyBean>();
            pbMap.put(getterName, propertyBeans);
        }
        propertyBeans.add(propertyBean);
    }
}
