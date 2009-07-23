/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import java.util.List;

/**
 *
 * @author Miro
 */
public interface EnumClassifier<T> extends DatabaseResource {

    List<T> getEnums(Object... classifiers);
}
