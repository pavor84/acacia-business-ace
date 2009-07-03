/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui.entity;

import javax.swing.ComboBoxModel;

/**
 *
 * @author Miro
 */
public interface ExpressionComboBoxModel extends ComboBoxModel {

    boolean isExpression(Object anItem);

    boolean isExpression(int index);

    Object getExpression(Object anItem);

    Object getExpression(int index);
}
