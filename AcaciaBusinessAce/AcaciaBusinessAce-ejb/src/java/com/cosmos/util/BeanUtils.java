/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util;

import org.apache.commons.beanutils.BeanUtilsBean;

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
        if((beanUtils = instance.get()) == null) {
            beanUtils = new BeanUtils();
            instance.set(beanUtils);
        }

        return beanUtils;
    }

    public static void removeInstance() {
        instance.remove();
    }
}
