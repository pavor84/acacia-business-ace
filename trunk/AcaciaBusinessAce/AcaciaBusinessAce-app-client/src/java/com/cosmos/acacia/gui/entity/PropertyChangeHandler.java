/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.beansbinding.PropertyDetail;
import com.cosmos.swingb.binding.EntityListBinder;
import com.cosmos.util.BeanUtils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JComponent;

/**
 *
 * @author Miro
 */
public class PropertyChangeHandler implements PropertyChangeListener {

    private TreeMap<String, List<PropertyBean>> propertyBeansMap;

    public PropertyChangeHandler() {
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        List<PropertyBean> propertyBeans = getPropertyBeansMap().get(propertyName);
        if (propertyBeans == null || propertyBeans.size() == 0) {
            return;
        }

        BeanUtils beanUtils = BeanUtils.getInstance();
        Object value = event.getNewValue();
        for (PropertyBean propertyBean : propertyBeans) {
            Object bean = propertyBean.getBean();
            String setterName = propertyBean.getSetterName();
            JComponent jComponent = propertyBean.getJComponent();
            try {
                beanUtils.setProperty(bean, setterName, value);
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

    public TreeMap<String, List<PropertyBean>> getPropertyBeansMap() {
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
        addPropertyBean(getterName, new PropertyBean(bean, setterName, jComponent));
    }

    public void addPropertyBean(String getterName, PropertyBean propertyBean) {
        TreeMap<String, List<PropertyBean>> map = getPropertyBeansMap();
        List<PropertyBean> propertyBeans = map.get(getterName);
        if (propertyBeans == null) {
            propertyBeans = new ArrayList<PropertyBean>();
            map.put(getterName, propertyBeans);
        }
        propertyBeans.add(propertyBean);
    }
}
