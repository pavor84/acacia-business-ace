/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.entity;

import java.lang.annotation.Annotation;

/**
 *
 * @author Miro
 */
public interface EntityAttributes<T extends Annotation> {

    Class getEntityClass();

    String getFormClassName();

    String getListFormClassName();

    String getPluralLabelName();

    String getPluralTitle();

    String getCustomDisplaySuffix();

    T getAnnotation();
}
