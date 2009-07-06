/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Miro
 */
public class BeanUtils extends BeanUtilsBean {

    private static InheritableThreadLocal<BeanUtils> instance = new InheritableThreadLocal<BeanUtils>();

    protected BeanUtils() {
        super(new BeanConvertUtils());
    }

    public static BeanUtils getInstance() {
        BeanUtils beanUtils;
        if ((beanUtils = instance.get()) == null) {
            beanUtils = new BeanUtils();
            instance.set(beanUtils);
        }

        return beanUtils;
    }

    public static void removeInstance() {
        instance.remove();
    }

    public Object normalizeSetterValue(Object bean, String name, Object value) {
        if (value == null || bean == null || name == null) {
            return value;
        }

        if (value instanceof Number) {
            int index;
            if ((index = name.lastIndexOf('.')) >= 0) {
                String beanName = name.substring(0, index);
                String propertyName = name.substring(index + 1);
                try {
                    bean = PropertyUtils.getProperty(bean, beanName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (bean == null) {
                    return value;
                }

                String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                Class beanClass = bean.getClass();
                try {
                    List<Method> methods = new ArrayList<Method>();
                    for (Method method : beanClass.getDeclaredMethods()) {
                        if (methodName.equals(method.getName())) {
                            methods.add(method);
                        }
                    }

                    if (methods.size() == 0) {
                        return value;
                    }

                    Class valueClass = value.getClass();
                    Iterator<Method> iterator = methods.iterator();
                    while (iterator.hasNext()) {
                        Method method = iterator.next();
                        Class[] parameterTypes;
                        if ((parameterTypes = method.getParameterTypes()).length != 1) {
                            iterator.remove();
                            continue;
                        }

                        Class parameterType = parameterTypes[0];
                        if (parameterType == valueClass || parameterType.isAssignableFrom(valueClass)) {
                            return value;
                        } else if (!(parameterType == String.class || Number.class.isAssignableFrom(parameterType))) {
                            iterator.remove();
                        }
                    }

                    if (methods.size() == 0) {
                        return value;
                    }

                    for (Method method : methods) {
                        if (method.getParameterTypes()[0] == String.class) {
                            return value.toString();
                        }
                    }

                    Class parameterType = methods.get(0).getParameterTypes()[0];
                    value = ConstructorUtils.invokeConstructor(parameterType, value.toString());
                    return value;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return value;
    }

    public void setPropertyValue(Object bean, String name, Object value) {
        value = normalizeSetterValue(bean, name, value);
        try {
            PropertyUtils.setProperty(bean, name, value);
        } catch (Exception ex) {
            StringBuilder sb = new StringBuilder();
            sb.append("bean=").append(bean);
            sb.append(", name=").append(name);
            sb.append(", value=").append(value);
            if (value != null) {
                sb.append(", value class=").append(value.getClass().getName());
            }
            throw new RuntimeException(sb.toString(), ex);
        }
    }
}
