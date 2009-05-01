/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.util;

import org.apache.commons.beanutils.ConvertUtilsBean;

/**
 *
 * @author Miro
 */
public class BeanConvertUtils extends ConvertUtilsBean {

    @Override
    public Object convert(String value, Class cls) {
        if (cls.isEnum()) {
            return Enum.valueOf(cls, value);
        }

        return super.convert(value, cls);
    }
}
