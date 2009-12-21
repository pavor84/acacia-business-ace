/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation;

/**
 *
 * @author Bozhidar Bozhanov
 */
public enum ValidationType {

    NONE,
    LENGTH,
    REGEX,
    NUMBER,
    NUMBER_RANGE,
    DATE,
    DATE_RANGE,
    MASK_FORMATTER,
    CUSTOM;
}
