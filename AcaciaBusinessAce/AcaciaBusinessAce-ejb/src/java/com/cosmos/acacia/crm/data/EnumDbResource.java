/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;

/**
 *
 * @author miro
 */
public class EnumDbResource
    implements Serializable
{

    private Enum enumValue;

    public Enum getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(Enum enumValue) {
        this.enumValue = enumValue;
    }

}
